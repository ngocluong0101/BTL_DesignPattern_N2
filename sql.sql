-- Tạo database nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS cvdtt;
USE cvdtt;

-- -------------------------------------------------------------------------
-- 1. CÁC BẢNG DANH MỤC ĐỘC LẬP (Không phụ thuộc khóa ngoại)
-- -------------------------------------------------------------------------

-- Bảng admin
CREATE TABLE admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(15)
);

-- Bảng customers
CREATE TABLE customers (
    id_customers INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(15),
    address TEXT,
    date_of_birth DATE,
    points INT DEFAULT 0,
    level VARCHAR(20) DEFAULT 'BRONZE' -- Phục vụ State Pattern (Tích điểm)
);

-- -------------------------------------------------------------------------
-- 2. CÁC BẢNG DỮ LIỆU CỐT LÕI
-- -------------------------------------------------------------------------

-- Bảng suppliers
CREATE TABLE suppliers (
    id_suppliers INT AUTO_INCREMENT PRIMARY KEY,
    id_admin INT,
    phone VARCHAR(15),
    address VARCHAR(255),
    email VARCHAR(100),
    name VARCHAR(100),
    state VARCHAR(20) DEFAULT 'ACTIVE', -- Phục vụ State Pattern (Nhà cung cấp)
    FOREIGN KEY (id_admin) REFERENCES admin(id_admin)
);

-- Bảng products
CREATE TABLE products (
    id_products INT AUTO_INCREMENT PRIMARY KEY,
    id_suppliers INT,
    name_products VARCHAR(100),
    product_type VARCHAR(50) DEFAULT 'BASIC', -- Phục vụ Abstract Factory Pattern (VD: BASIC, PREMIUM)
    description TEXT,
    price DECIMAL(10, 2),
    state VARCHAR(20),
    id_admin INT,
    FOREIGN KEY (id_admin) REFERENCES admin(id_admin),
    FOREIGN KEY (id_suppliers) REFERENCES suppliers(id_suppliers)
);

-- Bảng events (Sự kiện khuyến mãi chung)
CREATE TABLE events (
    id_events INT AUTO_INCREMENT PRIMARY KEY,
    id_admin INT,
    name_events VARCHAR(100),
    start_date DATE,
    end_date DATE,
    content TEXT,
    FOREIGN KEY (id_admin) REFERENCES admin(id_admin)
);

-- Bảng vouchers (Mã giảm giá độc lập - Phục vụ Strategy Pattern)
CREATE TABLE vouchers (
    id_vouchers INT AUTO_INCREMENT PRIMARY KEY,
    id_admin INT,
    name_vouchers VARCHAR(100),
    start_value DECIMAL(10, 2),
    end_value DECIMAL(10, 2),
    is_active BOOLEAN DEFAULT 1,
    code VARCHAR(20) UNIQUE,
    discount_type VARCHAR(20),
    discount_value DOUBLE,
    FOREIGN KEY (id_admin) REFERENCES admin(id_admin)
);

-- -------------------------------------------------------------------------
-- 3. CÁC BẢNG NGHIỆP VỤ (Mua bán, Giỏ hàng, Đơn hàng)
-- -------------------------------------------------------------------------

-- Bảng carts (Giỏ hàng tạm thời)
CREATE TABLE carts (
    id_carts INT AUTO_INCREMENT PRIMARY KEY,
    id_customers INT,
    FOREIGN KEY (id_customers) REFERENCES customers(id_customers)
);

-- Bảng product_carts (Chi tiết giỏ hàng - Đã bổ sung Quantity)
CREATE TABLE product_carts (
    id_carts INT,
    id_product INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (id_carts, id_product),
    FOREIGN KEY (id_carts) REFERENCES carts(id_carts),
    FOREIGN KEY (id_product) REFERENCES products(id_products)
);

-- Bảng bookings (Đặt trước/Giữ chỗ)
CREATE TABLE bookings (
    id_bookings INT AUTO_INCREMENT PRIMARY KEY,
    id_customers INT,
    id_carts INT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(20),
    amount DECIMAL(10, 2),
    FOREIGN KEY (id_customers) REFERENCES customers(id_customers),
    FOREIGN KEY (id_carts) REFERENCES carts(id_carts)
);

-- Bảng orders (Đơn hàng chính thức - Đã link với Voucher)
CREATE TABLE orders (
    id_orders INT AUTO_INCREMENT PRIMARY KEY,
    id_bookings INT,
    id_voucher INT NULL, -- Cho phép null nếu khách không dùng mã
    order_date DATE,
    total_amount DECIMAL(10, 2),
    status VARCHAR(50), -- Phục vụ State Pattern (Trạng thái đơn hàng)
    payment_method VARCHAR(50),
    note TEXT,
    FOREIGN KEY (id_bookings) REFERENCES bookings(id_bookings),
    FOREIGN KEY (id_voucher) REFERENCES vouchers(id_vouchers)
);

-- Bảng order_details (BẢNG MỚI BỔ SUNG - Chốt dữ liệu đơn hàng cố định)
CREATE TABLE order_details (
    id_order INT,
    id_product INT,
    quantity INT DEFAULT 1,
    unit_price DECIMAL(10, 2), -- Giá bán tại thời điểm chốt đơn
    PRIMARY KEY (id_order, id_product),
    FOREIGN KEY (id_order) REFERENCES orders(id_orders),
    FOREIGN KEY (id_product) REFERENCES products(id_products)
);

-- Bảng buy_bills (Hóa đơn nhập hàng)
CREATE TABLE buy_bills (
    id_buy_bills INT AUTO_INCREMENT PRIMARY KEY,
    id_admin INT,
    amount DECIMAL(10, 2),
    buy_date DATE,
    state VARCHAR(50),
    FOREIGN KEY (id_admin) REFERENCES admin(id_admin)
);

-- Bảng buy_bill_products (Chi tiết hóa đơn nhập - Đã bổ sung Quantity, Price)
CREATE TABLE buy_bill_products (
    id_buy_bills INT,
    id_product INT,
    quantity INT DEFAULT 1,
    unit_price DECIMAL(10, 2),
    PRIMARY KEY (id_product, id_buy_bills),
    FOREIGN KEY (id_product) REFERENCES products(id_products),
    FOREIGN KEY (id_buy_bills) REFERENCES buy_bills(id_buy_bills)
);

-- Bảng sales_bills (Hóa đơn bán hàng)
CREATE TABLE sales_bills (
    id_sales_bills INT AUTO_INCREMENT PRIMARY KEY,
    id_orders INT,
    amount INT,
    booking_date DATE,
    state VARCHAR(50),
    FOREIGN KEY (id_orders) REFERENCES orders(id_orders)
);

-- -------------------------------------------------------------------------
-- 4. CÁC BẢNG PHỤ TRỢ (Khuyến mãi, Thẻ thành viên, Thông báo)
-- -------------------------------------------------------------------------

-- Bảng earning_cards (Thẻ tích điểm)
CREATE TABLE earning_cards (
    id_earning_cards INT AUTO_INCREMENT PRIMARY KEY,
    id_customers INT,
    amount DECIMAL(10, 2),
    card_class INT,
    FOREIGN KEY (id_customers) REFERENCES customers(id_customers)
);

-- Bảng discount (Khuyến mãi sản phẩm - Phục vụ Decorator Pattern)
CREATE TABLE discount (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    discount_type ENUM('percent', 'amount') DEFAULT 'percent',
    value FLOAT NOT NULL,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (product_id) REFERENCES products(id_products) ON DELETE CASCADE
);

-- Bảng customer_event_subscriptions (Đăng ký nhận thông báo sự kiện - Observer Pattern)
CREATE TABLE customer_event_subscriptions (
    id_subscription INT AUTO_INCREMENT PRIMARY KEY,
    id_customer INT,
    FOREIGN KEY (id_customer) REFERENCES customers(id_customers)
);

-- Bảng notifications (Lịch sử gửi thông báo - Observer Pattern)
CREATE TABLE notifications (
    id_notification INT AUTO_INCREMENT PRIMARY KEY,
    id_customer INT,
    id_event INT,
    message TEXT,
    sent_at DATETIME,
    status VARCHAR(20),
    FOREIGN KEY (id_customer) REFERENCES customers(id_customers),
    FOREIGN KEY (id_event) REFERENCES events(id_events)
);

-- =========================================================================
-- DỮ LIỆU MẪU (FAKE DATA FOR DEMO)
-- =========================================================================

-- Admin
INSERT INTO admin (username, password, full_name, phone) VALUES
('admin', 'admin', 'Administrator', '0123456789');

-- Customers
INSERT INTO customers (username, password, full_name, phone, address, date_of_birth, points, level) VALUES
('user1', '123456', 'Lê Văn C', '0911111111', 'Hà Nội', '2000-01-01', 1200, 'SILVER'),
('user2', '123456', 'Phạm Thị D', '0912222222', 'Hồ Chí Minh', '1999-05-10', 6000, 'GOLD'),
('user3', '123456', 'Ngô Văn E', '0913333333', 'Đà Nẵng', '2001-09-15', 300, 'BRONZE');

-- Suppliers
INSERT INTO suppliers (id_admin, phone, address, email, name, state) VALUES
(1, '0909999999', 'Hà Nội', 'supplier1@email.com', 'Nhà cung cấp A', 'ACTIVE'),
(2, '0908888888', 'Hồ Chí Minh', 'supplier2@email.com', 'Nhà cung cấp B', 'INACTIVE');

-- Products
INSERT INTO products (id_suppliers, name_products, product_type, description, price, state, id_admin) VALUES
(1, 'Sản phẩm 1', 'BASIC', 'Mô tả sản phẩm 1', 100000, 'active', 1),
(2, 'Sản phẩm 1', 'PREMIUM', 'Mô tả sản phẩm 2', 500000, 'active', 2);

-- Vouchers
INSERT INTO vouchers (id_admin, name_vouchers, start_value, end_value, is_active, code, discount_type, discount_value) VALUES
(1, 'Voucher 10%', 0, 1000000, 1, 'VOUCHER10', 'percent', 10),
(2, 'Voucher 50k', 500000, 2000000, 1, 'VOUCHER50K', 'amount', 50000);

-- Carts & Product Carts
INSERT INTO carts (id_customers) VALUES (1), (2);
INSERT INTO product_carts (id_carts, id_product, quantity) VALUES
(1, 1, 2),
(2, 2, 1);

-- Bookings & Orders
INSERT INTO bookings (id_customers, id_carts, start_date, end_date, status, amount) VALUES
(1, 1, '2026-06-01', '2026-06-02', 'PENDING', 200000),
(2, 2, '2026-06-01', '2026-06-02', 'PENDING', 500000);

-- Orders (Đơn 1 không dùng mã, Đơn 2 dùng mã số 2)
INSERT INTO orders (id_bookings, id_voucher, order_date, total_amount, status, payment_method, note) VALUES
(1, NULL, '2026-06-02', 200000, 'PENDING', 'CASH', 'Giao hàng nhanh'),
(2, 2, '2026-06-02', 450000, 'PAID', 'CARD', 'Giao hàng tiêu chuẩn');

-- Order Details (Dữ liệu chốt đơn tĩnh)
INSERT INTO order_details (id_order, id_product, quantity, unit_price) VALUES
(1, 1, 2, 100000),
(2, 2, 1, 500000);

-- Earning Cards
INSERT INTO earning_cards (id_customers, amount, card_class) VALUES
(1, 50000, 1),
(2, 100000, 2);