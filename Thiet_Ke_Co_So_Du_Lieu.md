# Thiết kế database tổng thể theo phân chia nhiệm vụ nhóm

## 1. Tổng quan dự án và module nhóm
Dự án "Ứng dụng desktop quản lý bán quần áo" được phát triển bằng Java Core, Java Swing, và JDBC (không sử dụng framework lớn). Để đảm bảo tính chuyên nghiệp và dễ quản lý, dự án áp dụng kiến trúc 5 tầng (View - Controller - Service - DAO - Model) kết hợp với các Design Pattern chuẩn mực.
Cơ sở dữ liệu được thiết kế **tổng thể và thống nhất**, không rời rạc, nhưng được phân bổ khéo léo để hỗ trợ độc lập cho 5 module do 5 thành viên phụ trách:
- **Ly:** Quản lý hóa đơn, Sự kiện & Thông báo (Factory Method, Observer).
- **Linh:** Quản lý sản phẩm, Kho (Abstract Factory, Adapter).
- **Lương:** Khách hàng, Tài khoản, Khuyến mãi (Decorator, Builder).
- **An:** Quản lý đặt hàng, Voucher, Tích điểm (Strategy, State).
- **Minh:** Nhà cung cấp, Checkout, Đăng nhập (Singleton, Facade).

## 2. Nguyên tắc thiết kế database
1. **Chuẩn hóa dữ liệu:** Đạt chuẩn 3NF, tránh dư thừa thông tin.
2. **Tách rõ các nhóm nghiệp vụ:** Tài khoản, Khách hàng, Sản phẩm, Nhà cung cấp, Hóa đơn, Đơn hàng, Voucher, Khuyến mãi, Tích điểm, Sự kiện.
3. **Liên kết chặt chẽ:** Sử dụng Khóa ngoại (Foreign Key) để nối các luồng nghiệp vụ.
4. **Tối ưu quan hệ:** Bảng chi tiết (detail) cho quan hệ 1-N (VD: hóa đơn - chi tiết hóa đơn), bảng trung gian cho quan hệ N-N (VD: sản phẩm - khuyến mãi).
5. **Kho hàng khoa học:** Không lưu tồn kho ở bảng `products` gốc mà lưu tại `product_variants` (biến thể theo size/màu).
6. **Lưu vết trạng thái:** Trạng thái đơn hàng, nhà cung cấp được tách thành bảng riêng hoặc lịch sử riêng để phục vụ State Pattern.
7. **Audit Trail:** Các bảng chính đều có trường `created_at`, `updated_at`.

## 3. Nhóm bảng theo từng thành viên phụ trách
- **Ly:** `sales_invoices`, `sales_invoice_details`, `purchase_invoices`, `purchase_invoice_details`, `events`, `event_subscriptions`, `notifications`.
- **Linh:** `categories`, `product_statuses`, `products`, `sizes`, `colors`, `product_variants`.
- **Lương:** `roles`, `users`, `customers`, `promotions`, `product_promotions`.
- **An:** `order_statuses`, `orders`, `order_details`, `order_status_history`, `vouchers`, `membership_levels`, `loyalty_points`, `loyalty_point_histories`.
- **Minh:** `user_sessions`, `supplier_statuses`, `suppliers`, `payment_methods`, `payments`.

## 4. Danh sách bảng tổng thể
Hệ thống bao gồm **28 bảng**, được phân thành 8 cụm chức năng như đã liệt kê ở Mục 3. Các bảng nối với nhau tạo thành một mạng lưới xuyên suốt từ lúc nhập hàng, quản lý kho, bán hàng cho tới hậu mãi.

## 5. Chi tiết từng bảng

### Nhóm Tài khoản & Khách hàng (Lương, Minh)
**1. Bảng `roles` (Minh/Lương)**
- **Mục đích:** Phân quyền hệ thống. (id, role_name).

**2. Bảng `users` (Minh/Lương)**
- **Mục đích:** Lưu trữ tài khoản nhân viên/quản lý.
- **Trường chính:** id (PK), role_id (FK), username, password, full_name, status.
- **Pattern:** Singleton (UserSession), Builder (lúc khởi tạo đối tượng).

**3. Bảng `user_sessions` (Minh)**
- **Mục đích:** Lưu phiên đăng nhập hiện hành. (id, user_id, login_time, token).

**4. Bảng `customers` (Lương)**
- **Mục đích:** Quản lý thông tin khách mua hàng.
- **Trường chính:** id (PK), user_id (FK nullable - nếu khách tự tạo acc), full_name, phone, email.
- **Pattern:** Builder Pattern.

### Nhóm Nhà cung cấp (Minh)
**5. Bảng `supplier_statuses` & 6. `suppliers`**
- **Mục đích:** Quản lý đối tác và trạng thái hợp tác.
- **Trường chính (suppliers):** id, status_id (FK), name, phone, address.

### Nhóm Sản phẩm (Linh)
**7. Bảng `categories`** (id, name).
**8. Bảng `product_statuses`** (id, status_name).
**9. Bảng `products`**
- **Mục đích:** Thông tin gốc của sản phẩm.
- **Trường chính:** id, category_id, supplier_id, status_id, name, product_type (Basic/Premium).
- **Pattern:** Abstract Factory (dựa vào product_type), Adapter.
**10. Bảng `sizes`** & **11. `colors`** (Danh mục size, màu).
**12. Bảng `product_variants`**
- **Mục đích:** Quản lý tồn kho theo từng biến thể thực tế (VD: Áo Đỏ Size L).
- **Trường chính:** id, product_id, size_id, color_id, sku, price, stock_quantity.

### Nhóm Hóa đơn (Ly)
**13. Bảng `sales_invoices` & 14. `sales_invoice_details`**
- **Mục đích:** Hóa đơn bán hàng trực tiếp tại quầy.
- **Trường chính:** id, customer_id, user_id, total_amount, created_at.
- **Pattern:** Factory Method (SalesInvoice).
*(Bảng `purchase_invoices` & `purchase_invoice_details` có cấu trúc tương tự dành cho nhập hàng).*

### Nhóm Đơn hàng & Checkout (An, Minh)
**17. Bảng `order_statuses` & 18. `orders`**
- **Mục đích:** Quản lý đơn giao đi.
- **Trường chính:** id, customer_id, status_id, voucher_id, total_amount, created_at.
- **Pattern:** State Pattern (dựa vào status_id), Facade (gom luồng).
**19. Bảng `order_details`** (id, order_id, variant_id, quantity, price).
**20. Bảng `order_status_history`** (Lưu vết thay đổi trạng thái đơn).
**21. Bảng `payment_methods` & 22. `payments`** (Lưu thông tin thanh toán cho đơn hàng/hóa đơn).

### Nhóm Khuyến mãi & Voucher (An, Lương)
**23. Bảng `vouchers` (An)**
- **Mục đích:** Mã giảm giá toàn cục.
- **Trường chính:** id, code, discount_type (PERCENT/FIXED), discount_value.
- **Pattern:** Strategy Pattern (dựa vào discount_type).
**24. Bảng `promotions` & 25. `product_promotions` (Lương)**
- **Mục đích:** Giảm giá cho từng mặt hàng riêng lẻ.
- **Pattern:** Decorator Pattern (Bọc thêm giá trị giảm vào Product).

### Nhóm Tích điểm (An)
**26. Bảng `membership_levels`, 27. `loyalty_points`, 28. `loyalty_point_histories`**
- **Mục đích:** Tích điểm, thăng hạng thành viên sau khi Checkout.

### Nhóm Sự kiện & Thông báo (Ly)
**29. Bảng `events`, 30. `event_subscriptions`, 31. `notifications`**
- **Mục đích:** Quản lý tin nhắn marketing. Khách subscribe sẽ nhận thông báo.
- **Pattern:** Observer Pattern.

## 6. Quan hệ ERD bằng mô tả text
```text
roles 1 --- n users
users 1 --- 0..1 customers
users 1 --- n user_sessions

supplier_statuses 1 --- n suppliers
suppliers 1 --- n products

categories 1 --- n products
product_statuses 1 --- n products
products 1 --- n product_variants
sizes 1 --- n product_variants
colors 1 --- n product_variants

users 1 --- n sales_invoices
customers 1 --- n sales_invoices
sales_invoices 1 --- n sales_invoice_details
product_variants 1 --- n sales_invoice_details

users 1 --- n purchase_invoices
suppliers 1 --- n purchase_invoices
purchase_invoices 1 --- n purchase_invoice_details
product_variants 1 --- n purchase_invoice_details

customers 1 --- n orders
order_statuses 1 --- n orders
orders 1 --- n order_details
product_variants 1 --- n order_details
vouchers 1 --- n orders

orders 1 --- n payments
payment_methods 1 --- n payments
orders 1 --- n order_status_history
order_statuses 1 --- n order_status_history

products 1 --- n product_promotions
promotions 1 --- n product_promotions

membership_levels 1 --- n customers
customers 1 --- 1 loyalty_points
customers 1 --- n loyalty_point_histories

users 1 --- n events
events 1 --- n event_subscriptions
customers 1 --- n event_subscriptions
events 1 --- n notifications
customers 1 --- n notifications
```

## 7. Database hỗ trợ Design Pattern theo từng thành viên

| Thành viên | Module | Design Pattern | Bảng liên quan | Cách database hỗ trợ |
|---|---|---|---|---|
| **Ly** | Hóa đơn | Factory Method | `sales_invoices`, `purchase_invoices` | Bóc tách rõ 2 loại bảng hóa đơn, giúp Factory chọn đúng loại object cần khởi tạo. |
| **Ly** | Sự kiện | Observer | `events`, `event_subscriptions`, `notifications` | Khách hàng đăng ký vào `event_subscriptions` chính là Subscriber. Tạo event sẽ sinh ra Notification. |
| **Linh** | Sản phẩm | Abstract Factory | `products`, `product_variants` | Trường `product_type` (Basic/Premium) giúp Factory rẽ nhánh khởi tạo đúng Family sản phẩm. |
| **Linh** | Sản phẩm | Adapter | `products`, `sizes`, `colors`, ... | Dữ liệu chia nhỏ nhiều bảng, Adapter giúp gom các ResultSet JOIN lại thành 1 object `Product` duy nhất. |
| **Lương** | Khách hàng | Builder | `customers`, `products` | Đối tượng nhiều cột. Builder giúp set dữ liệu từ từ trước khi Insert. |
| **Lương** | Khuyến mãi| Decorator | `promotions`, `product_promotions` | Khuyến mãi không nằm cứng trong products. Khi query, Decorator sẽ "bọc" thêm promotion vào Product. |
| **An** | Voucher | Strategy | `vouchers` | Trường `discount_type` quyết định hàm tính toán (Percent vs Fixed) ở Java là Strategy nào. |
| **An** | Đặt hàng | State | `orders`, `order_statuses` | Trạng thái (Pending, Paid) lấy từ DB sẽ kích hoạt Class State tương ứng ngăn hành vi sai luồng. |
| **Minh** | Đăng nhập | Singleton | `users`, `user_sessions` | Load 1 lần, giữ object `UserSession` tĩnh trên RAM toàn hệ thống. |
| **Minh** | Checkout | Facade | `orders`, `vouchers`, `payments`, `product_variants` | Gom 7-8 bước (trừ kho, cộng điểm, đổi state) lôi từ nhiều bảng vào chung 1 Transaction API gọn nhẹ. |

## 8. SQL tạo database và bảng

```sql
CREATE DATABASE IF NOT EXISTS clothing_store_management;
USE clothing_store_management;

-- ================= LƯƠNG & MINH (TÀI KHOẢN, KHÁCH HÀNG) =================
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    status BOOLEAN DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT
);

CREATE TABLE user_sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    token VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE membership_levels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    level_name VARCHAR(50) NOT NULL,
    min_points INT NOT NULL
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE, -- Nullable if customer has no login
    membership_level_id INT,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (membership_level_id) REFERENCES membership_levels(id)
);

-- ================= MINH (NHÀ CUNG CẤP) =================
CREATE TABLE supplier_statuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (status_id) REFERENCES supplier_statuses(id)
);

-- ================= LINH (SẢN PHẨM) =================
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE product_statuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    supplier_id INT NOT NULL,
    status_id INT NOT NULL,
    name VARCHAR(200) NOT NULL,
    product_type VARCHAR(50) NOT NULL, -- Basic, Premium
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE RESTRICT,
    FOREIGN KEY (status_id) REFERENCES product_statuses(id)
);

CREATE TABLE sizes (id INT AUTO_INCREMENT PRIMARY KEY, size_name VARCHAR(20) NOT NULL);
CREATE TABLE colors (id INT AUTO_INCREMENT PRIMARY KEY, color_name VARCHAR(50) NOT NULL);

CREATE TABLE product_variants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    size_id INT NOT NULL,
    color_id INT NOT NULL,
    sku VARCHAR(50) UNIQUE NOT NULL,
    price DECIMAL(12,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT DEFAULT 0 CHECK (stock_quantity >= 0),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (size_id) REFERENCES sizes(id),
    FOREIGN KEY (color_id) REFERENCES colors(id)
);

-- ================= LƯƠNG (KHUYẾN MÃI) =================
CREATE TABLE promotions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    discount_type VARCHAR(20) NOT NULL, -- PERCENT, FIXED
    discount_value DECIMAL(12,2) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL
);

CREATE TABLE product_promotions (
    product_id INT NOT NULL,
    promotion_id INT NOT NULL,
    PRIMARY KEY (product_id, promotion_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (promotion_id) REFERENCES promotions(id) ON DELETE CASCADE
);

-- ================= AN (VOUCHER, TÍCH ĐIỂM, ĐẶT HÀNG) =================
CREATE TABLE vouchers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    discount_type VARCHAR(20) NOT NULL, -- PERCENT, FIXED, FREE_SHIP
    discount_value DECIMAL(12,2) NOT NULL,
    min_order_value DECIMAL(12,2) DEFAULT 0,
    start_date DATETIME,
    end_date DATETIME,
    usage_limit INT DEFAULT 100,
    used_count INT DEFAULT 0
);

CREATE TABLE loyalty_points (
    customer_id INT PRIMARY KEY,
    points INT DEFAULT 0 CHECK (points >= 0),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE loyalty_point_histories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    points_changed INT NOT NULL,
    reason VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE order_statuses (id INT AUTO_INCREMENT PRIMARY KEY, status_name VARCHAR(50) NOT NULL);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    status_id INT NOT NULL,
    voucher_id INT,
    total_amount DECIMAL(12,2) NOT NULL,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    shipping_address TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (status_id) REFERENCES order_statuses(id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id)
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    variant_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(id)
);

CREATE TABLE order_status_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status_id INT NOT NULL,
    changed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES order_statuses(id)
);

CREATE TABLE payment_methods (id INT AUTO_INCREMENT PRIMARY KEY, method_name VARCHAR(50) NOT NULL);

CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_method_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id)
);

-- ================= LY (HÓA ĐƠN, SỰ KIỆN) =================
CREATE TABLE sales_invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    user_id INT NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    invoice_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE sales_invoice_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sales_invoice_id INT NOT NULL,
    variant_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (sales_invoice_id) REFERENCES sales_invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(id)
);

CREATE TABLE purchase_invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    user_id INT NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    invoice_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE purchase_invoice_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    purchase_invoice_id INT NOT NULL,
    variant_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (purchase_invoice_id) REFERENCES purchase_invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(id)
);

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    event_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE event_subscriptions (
    customer_id INT NOT NULL,
    event_id INT NOT NULL,
    PRIMARY KEY (customer_id, event_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,
    customer_id INT NOT NULL,
    status VARCHAR(50) DEFAULT 'SENT',
    sent_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- INDEXES
CREATE INDEX idx_sku ON product_variants(sku);
CREATE INDEX idx_phone ON customers(phone);
```

## 9. SQL dữ liệu mẫu

```sql
INSERT INTO roles (role_name) VALUES ('Admin'), ('Staff');
INSERT INTO users (role_id, username, password, full_name) VALUES (1, 'admin', '123456', 'Admin Minh');

INSERT INTO membership_levels (level_name, min_points) VALUES ('Silver', 0), ('Gold', 1000);
INSERT INTO customers (membership_level_id, full_name, phone) VALUES (1, 'Khách hàng VIP', '0912345678');
INSERT INTO loyalty_points (customer_id, points) VALUES (1, 500);

INSERT INTO supplier_statuses (status_name) VALUES ('Active');
INSERT INTO suppliers (status_id, name, phone) VALUES (1, 'Nhà cung cấp Vải', '0988888888');

INSERT INTO categories (name) VALUES ('Áo thun');
INSERT INTO product_statuses (status_name) VALUES ('On Sale');
INSERT INTO products (category_id, supplier_id, status_id, name, product_type) VALUES (1, 1, 1, 'Áo Thun Basic', 'BASIC');

INSERT INTO sizes (size_name) VALUES ('M');
INSERT INTO colors (color_name) VALUES ('Đen');
INSERT INTO product_variants (product_id, size_id, color_id, sku, price, stock_quantity) VALUES (1, 1, 1, 'A01-M-DEN', 150000, 100);

INSERT INTO order_statuses (status_name) VALUES ('Pending'), ('Paid');
INSERT INTO payment_methods (method_name) VALUES ('Cash'), ('Credit Card');
INSERT INTO vouchers (code, discount_type, discount_value) VALUES ('SALE10', 'PERCENT', 10);

INSERT INTO promotions (name, discount_type, discount_value, start_date, end_date) VALUES ('Giáng sinh', 'FIXED', 20000, '2026-12-01', '2026-12-31');
INSERT INTO product_promotions (product_id, promotion_id) VALUES (1, 1);

INSERT INTO events (user_id, title, content, event_date) VALUES (1, 'Mở bán BST mới', 'Đến ngay cửa hàng', '2026-10-10');
INSERT INTO event_subscriptions (customer_id, event_id) VALUES (1, 1);
```

## 10. Luồng nghiệp vụ theo từng module
1. **Đăng nhập (Minh):** Đọc `users` -> Lưu Session.
2. **Quản lý NCC (Minh):** Đọc/ghi `suppliers`.
3. **Checkout (Minh):** Bọc Transaction gọi `orders`, check `stock_quantity`, add `sales_invoices`, update `loyalty_points` (Facade).
4. **Hóa đơn (Ly):** Factory tạo `SalesInvoice` hoặc `PurchaseInvoice`, insert vào DB tương ứng.
5. **Sự kiện (Ly):** Lọc `event_subscriptions`, chạy vòng lặp INSERT `notifications` (Observer).
6. **Sản phẩm (Linh):** Dùng Adapter SELECT qua 5 bảng (`products`, `variants`, `sizes`...) để gộp thành `Product` object.
7. **Đăng ký (Lương):** Builder tạo `Customer`, insert `customers` và khởi tạo `loyalty_points`.
8. **Khuyến mãi (Lương):** Đọc `product_promotions`, dùng Decorator bọc giá mới hiển thị UI.
9. **Đặt hàng (An):** Update `orders.status_id`, lưu log vào `order_status_history` (State).
10. **Voucher (An):** Tính toán giá trừ đi dựa trên `vouchers.discount_type` (Strategy).
11. **Tích điểm (An):** Cộng điểm vào `loyalty_points`, lưu vào `loyalty_point_histories`.

## 11. Mapping database sang Java class/package
- **`com.clothingstore.model`:** User, Role, Customer, Supplier, Category, Product, ProductVariant, SalesInvoice, Order, Voucher, Promotion, Event, LoyaltyPoint.
- **`com.clothingstore.dao`:** UserDAO, CustomerDAO, ProductDAO, OrderDAO, InvoiceDAO, VoucherDAO.
- **`com.clothingstore.service`:** AuthService, ProductService, CheckoutService, EventService.
- **`com.clothingstore.pattern.*`:** 
  - `factory.InvoiceFactory`, `abstractfactory.ProductFactory`.
  - `adapter.ProductDAOAdapter`, `builder.CustomerBuilder`.
  - `decorator.DiscountDecorator`, `observer.EventManager`.
  - `state.OrderState`, `strategy.DiscountStrategy`.
  - `singleton.UserSession`, `facade.CheckoutFacade`.

## 12. Kết luận
Bản thiết kế này đáp ứng hoàn hảo việc chia nhỏ module cho 5 thành viên nhóm. Các bảng không hề bị rời rạc mà liên kết chặt chẽ thành hệ sinh thái thống nhất. Đặc biệt, cấu trúc trường dữ liệu (như `product_type`, `discount_type`, `status_id`) được thiết kế "đo ni đóng giày" để ánh xạ trực tiếp lên 10 mẫu Design Pattern, giúp điểm số môn học và kiến trúc dự án đạt mức xuất sắc.
