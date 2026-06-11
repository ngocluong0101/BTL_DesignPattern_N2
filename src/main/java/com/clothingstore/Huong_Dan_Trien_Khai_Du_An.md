# Hướng dẫn chi tiết triển khai ứng dụng desktop Java quản lý bán quần áo

## I. Tổng quan dự án

**1. Giới thiệu dự án**
Dự án "Xây dựng ứng dụng desktop quản lý bán quần áo" là phần mềm quản lý toàn diện cho cửa hàng thời trang. Cung cấp công cụ quản lý sản phẩm, hóa đơn, khách hàng và tích hợp quy trình vận hành.

**2. Lý do chọn đề tài**
Áp dụng lập trình hướng đối tượng (OOP) và Design Pattern giải quyết bài toán quản lý thực tế, rèn luyện tư duy thiết kế kiến trúc phần mềm.

**3. Mục tiêu của phần mềm**
Số hóa và tự động hóa quy trình bán hàng, quản lý kho. Tối ưu hóa hệ thống source code dễ bảo trì và mở rộng theo chuẩn mực SOLID.

**4. Đối tượng sử dụng**
- **Quản lý cửa hàng:** Quản lý danh mục, khuyến mãi, nhà cung cấp, sự kiện.
- **Nhân viên bán hàng:** Tạo hóa đơn, tìm sản phẩm, thanh toán.
- **Khách hàng:** Thụ hưởng tích điểm, khuyến mãi.

**5. Phạm vi chức năng**
Quản lý đăng nhập, Sản phẩm, Hóa đơn, Khách hàng, Nhà cung cấp, Voucher, Khuyến mãi, Đặt hàng, Tích điểm, Sự kiện.

**6. Lý do chọn Java Desktop thuần (không dùng framework)**
Sử dụng ứng dụng Desktop (POS) đem lại độ trễ thấp, giao tiếp thiết bị ngoại vi dễ dàng. Việc sử dụng Java thuần (Swing + JDBC) mà không dùng framework (Spring, Hibernate) nhằm mục đích rèn luyện kiến thức nền tảng, tự thiết kế cấu trúc lớp và tự xử lý luồng dữ liệu ứng dụng.

## II. Lựa chọn công nghệ

- **Ngôn ngữ:** Java
- **Giao diện:** Java Swing
- **Database:** MySQL hoặc SQL Server
- **Kết nối CSDL:** JDBC (MySQL Connector/J hoặc mssql-jdbc)
- **Design Pattern:** Vận dụng nhiều mẫu thiết kế (Creational, Structural, Behavioral).

## III. Mô hình kiến trúc

Dự án áp dụng mô hình 3 lớp (3-Tier) kết hợp MVC, chia làm 5 tầng cốt lõi:
- **Presentation (View):** Chứa class Java Swing (JFrame, JPanel).
- **Controller:** Cầu nối nhận sự kiện từ View, gọi Service.
- **Service:** Xử lý logic nghiệp vụ, tính toán, validate dữ liệu.
- **DAO (Data Access Object):** Giao tiếp trực tiếp với CSDL (truy vấn SQL).
- **Model (Entity):** Đại diện dữ liệu nghiệp vụ (Product, Customer...).

## IV. Cấu trúc thư mục

```text
ClothingStoreManagement/
  src/
    main/
      java/
        com/
          clothingstore/
            Main.java
            config/
            database/
            model/
            view/
            controller/
            service/
            dao/
            pattern/
              factory/
              abstractfactory/
              builder/
              adapter/
              decorator/
              observer/
              state/
              strategy/
              singleton/
            util/
```

## V. Thiết kế cơ sở dữ liệu (Tổng quan)

- `users`: Quản lý tài khoản đăng nhập, phân quyền.
- `products`: Lưu thông tin hàng hóa, tồn kho.
- `customers`: Lưu thông tin khách hàng, tích điểm.
- `suppliers`: Đối tác cung cấp sản phẩm.
- `invoices` & `invoice_details`: Lưu hóa đơn bán hàng trực tiếp.
- `orders` & `order_details`: Lưu đơn đặt hàng nợ / giao hàng.
- `vouchers` & `promotions`: Khuyến mãi và mã giảm giá.
- `events` & `notifications`: Gửi thông báo sự kiện cho khách.

## VI. Class Diagram

### Nhóm Model
- Product
- BasicProduct
- PremiumProduct
- Customer
- Invoice
- Order
- Voucher
- Supplier

### Nhóm View
- LoginView
- MainFrame
- ProductView
- InvoiceView
- CustomerView
- VoucherView
- OrderView
- SupplierView

### Nhóm Controller
- AuthController
- ProductController
- InvoiceController
- VoucherController
- OrderController

### Nhóm Service
- AuthService
- ProductService
- InvoiceService
- VoucherService
- OrderService
- NotificationService

### Nhóm DAO
- ProductDAO
- CustomerDAO
- InvoiceDAO
- VoucherDAO
- OrderDAO
- SupplierDAO

### Nhóm Design Pattern
- ConnectionManager
- UserSession
- InvoiceFactory
- ProductFactory
- ProductBuilder
- ProductServiceAdapter
- ProductDecorator
- EventManager
- OrderState
- DiscountStrategy
- CheckoutFacade

## VII. Chi tiết áp dụng Design Pattern

### 1. Singleton
**Áp dụng cho:** ConnectionManager, UserSession
**Mục đích:** Quản lý kết nối Database và phiên đăng nhập của người dùng.
**Lợi ích:** Đảm bảo chỉ có một instance duy nhất dùng chung toàn hệ thống, tiết kiệm tài nguyên.

### 2. Factory Method
**Áp dụng cho:** InvoiceFactory, SalesInvoiceFactory, PurchaseInvoiceFactory
**Mục đích:** Khởi tạo các loại hóa đơn (Bán hàng, Nhập hàng).
**Lợi ích:** Tách biệt logic tạo đối tượng khỏi Controller, dễ dàng bổ sung loại hóa đơn mới.

### 3. Abstract Factory
**Áp dụng cho:** ProductFactory, BasicFactory, PremiumFactory
**Mục đích:** Tạo một bộ sản phẩm và bao bì tương ứng (Basic / Premium).
**Lợi ích:** Đảm bảo tính đồng bộ của các đối tượng liên đới, hạn chế rủi ro nhầm lẫn loại sản phẩm.

### 4. Builder
**Áp dụng cho:** ProductBuilder, CustomerBuilder
**Mục đích:** Khởi tạo đối tượng Product, Customer có nhiều thuộc tính.
**Lợi ích:** Ngăn tình trạng Telescoping Constructor, code mạch lạc và dễ validate trước khi tạo.

### 5. Adapter
**Áp dụng cho:** ProductServiceAdapter, ProductDAO
**Mục đích:** Chuyển đổi dữ liệu `ResultSet` từ DAO sang `List<Product>` cho Service và Controller.
**Lợi ích:** Giúp Controller không phải làm việc trực tiếp với dữ liệu thô của thư viện JDBC.

### 6. Decorator
**Áp dụng cho:** ProductComponent, ProductDecorator, DiscountDecorator, BestSellerDecorator
**Mục đích:** Bổ sung nhãn hoặc logic giảm giá trực tiếp vào sản phẩm.
**Lợi ích:** Bổ sung hành vi động mà không cần kế thừa phức tạp, dễ dàng gộp nhiều khuyến mãi.

### 7. Observer
**Áp dụng cho:** EventManager, Observer, CustomerObserver
**Mục đích:** Gửi thông báo đến khách hàng khi có sự kiện mới.
**Lợi ích:** Tạo cơ chế Subscribe/Publish linh hoạt, giảm phụ thuộc cứng giữa các class.

### 8. State
**Áp dụng cho:** Order, OrderState, PendingState, PaidState
**Mục đích:** Quản lý quy trình luân chuyển trạng thái của đơn đặt hàng.
**Lợi ích:** Loại bỏ các câu lệnh if/else cồng kềnh, phân quyền hành vi theo trạng thái đang giữ.

### 9. Strategy
**Áp dụng cho:** OrderService, DiscountStrategy, PercentageStrategy
**Mục đích:** Tính toán số tiền được giảm theo các công thức khác nhau (Phần trăm, Số tiền cố định).
**Lợi ích:** Hoán đổi thuật toán linh hoạt tại runtime.

### 10. Facade
**Áp dụng cho:** CheckoutFacade
**Mục đích:** Gộp quy trình thanh toán phức tạp (kiểm tra kho, tính tiền, áp dụng voucher, luân chuyển OrderState).
**Lợi ích:** Controller chỉ cần gọi một hàm duy nhất `processCheckout()`, giấu sự phức tạp của hệ thống.

## VIII. Quy trình triển khai dự án

1. **Khởi tạo:** Tạo project Java Desktop thuần. Thêm thư viện kết nối (JDBC).
2. **Thiết kế CSDL:** Tạo các bảng MySQL/SQL Server, thiết lập quan hệ.
3. **Cấu hình DB:** Cài đặt ConnectionManager (Singleton) kết nối.
4. **Tạo Model:** Tạo các thực thể Entity và class Builder hỗ trợ.
5. **Viết DAO:** Thực thi truy vấn PreparedStatement thuần cho các thao tác CRUD.
6. **Xây dựng Service:** Đặt quy tắc nghiệp vụ, nhúng các pattern tính toán (Strategy, State).
7. **Tạo Controller:** Phân luồng dữ liệu từ View đến Service.
8. **Thiết kế Giao diện (View):** Tạo màn hình bằng Java Swing, quản lý bằng CardLayout.
9. **Tích hợp Design Pattern:** Áp dụng đầy đủ 10 mẫu Design Pattern vào luồng xử lý.
10. **Kiểm thử:** Đảm bảo luồng chạy ổn định.

## IX. Hướng tiếp cận chuyển đổi CSDL sang SQL Server

Nhờ tuân thủ mô hình 3-Tier và DAO Pattern, hệ thống cô lập được CSDL tại tầng DAO. Nếu cần đổi từ MySQL sang SQL Server:
- **Thay JDBC Driver:** Xóa `mysql-connector-j`, thêm `mssql-jdbc`.
- **Cập nhật cấu hình:** Đổi chuỗi kết nối thành dạng `jdbc:sqlserver://...`
- **Chỉnh sửa script:** Sửa các đặc tả SQL (ví dụ đổi `AUTO_INCREMENT` thành `IDENTITY(1,1)`).
- **Mở rộng bằng Pattern:** Có thể kết hợp *Abstract Factory* để hệ thống tự load `SQLServerDAO` hay `MySQLDAO` từ cấu hình.
