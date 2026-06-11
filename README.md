
I. Tổng quan dự án

1. Giới thiệu dự án
Dự án "Xây dựng ứng dụng desktop quản lý bán quần áo" là một phần mềm quản lý toàn diện dành cho các cửa hàng kinh doanh thời trang. Ứng dụng cung cấp các công cụ cần thiết để quản lý thông tin sản phẩm, xử lý giao dịch bán hàng, theo dõi khách hàng và tích hợp nhiều nghiệp vụ quản lý khác nhằm tối ưu hóa quy trình vận hành của cửa hàng.

2. Lý do chọn đề tài
Kinh doanh thời trang luôn đòi hỏi việc quản lý số lượng lớn mẫu mã, kích thước, màu sắc và sự biến động liên tục của tồn kho, cũng như các chương trình khuyến mãi phức tạp. Đề tài này được lựa chọn nhằm áp dụng các kiến thức về lập trình hướng đối tượng (OOP) và Design Pattern để giải quyết một bài toán quản lý thực tế, từ đó rèn luyện khả năng tư duy thiết kế kiến trúc phần mềm một cách bài bản.

3. Mục tiêu của phần mềm
Mục tiêu chính của phần mềm là số hóa và tự động hóa các quy trình bán hàng, quản lý kho và chăm sóc khách hàng. Phần mềm hướng tới sự chính xác, tốc độ xử lý nhanh, giao diện thân thiện và đặc biệt là hệ thống source code được thiết kế chuẩn mực, dễ dàng bảo trì và mở rộng trong tương lai.

4. Đối tượng sử dụng
- Quản lý cửa hàng: Sử dụng phần mềm để thiết lập các danh mục sản phẩm, quản lý nhà cung cấp, thiết lập các chương trình khuyến mãi/voucher, quản lý sự kiện và theo dõi báo cáo.
- Nhân viên bán hàng: Thao tác tạo hóa đơn, tìm kiếm sản phẩm, áp dụng voucher, quản lý đơn đặt hàng và thao tác với thông tin điểm thưởng của khách hàng.
- Khách hàng: Dù không trực tiếp thao tác trên ứng dụng, khách hàng là đối tượng thụ hưởng thông qua việc được tích điểm, nhận thông báo sự kiện và các chương trình khuyến mãi.

5. Phạm vi chức năng
Ứng dụng bao gồm 11 module chính:
1. Đăng nhập
2. Quản lý sản phẩm
3. Quản lý hóa đơn
4. Quản lý khách hàng
5. Quản lý nhà cung cấp
6. Quản lý voucher
7. Quản lý khuyến mãi / giảm giá sản phẩm
8. Quản lý đặt hàng
9. Quản lý tích điểm thành viên
10. Quản lý thông báo sự kiện
11. Quản lý session người dùng sau đăng nhập

6. Lý do chọn ứng dụng desktop thay vì web/mobile
Đối với môi trường cửa hàng (Point of Sale - POS), ứng dụng desktop mang lại hiệu năng xử lý tốt, độ trễ thấp và khả năng hoạt động ổn định kể cả khi mạng internet có vấn đề. Hơn nữa, ứng dụng desktop dễ dàng tương tác với các thiết bị ngoại vi của cửa hàng như máy quét mã vạch, máy in hóa đơn.

7. Lý do chọn Java thuần không dùng framework
Việc sử dụng Java thuần (Java Core + Java Swing + JDBC) phục vụ mục đích học thuật cốt lõi: giúp hiểu rõ nguyên lý hoạt động nội tại của ứng dụng. Việc không phụ thuộc vào framework (như Spring, Hibernate) buộc người phát triển phải tự quản lý luồng dữ liệu, tự thiết kế cấu trúc lớp và tự xử lý vòng đời của object, qua đó rèn luyện kỹ năng thực hành thiết kế kiến trúc phần mềm.

8. Vai trò của Design Pattern trong dự án
Design Pattern đóng vai trò là bộ khung xương giúp giải quyết các vấn đề thiết kế lặp đi lặp lại một cách tối ưu. Việc áp dụng Design Pattern giúp cấu trúc mã nguồn trở nên linh hoạt (loose coupling), tăng khả năng tái sử dụng, giúp việc thêm mới chức năng (ví dụ: thêm loại giảm giá mới, thêm trạng thái đơn hàng) mà không làm vỡ kiến trúc hiện tại, tuân thủ chặt chẽ các nguyên lý SOLID.

II. Lựa chọn công nghệ

1. Ngôn ngữ lập trình: Java
Ngôn ngữ Java được lựa chọn do đây là ngôn ngữ thuần hướng đối tượng, hỗ trợ triển khai các Design Pattern một cách tự nhiên và rõ ràng. Java cung cấp bộ công cụ mạnh mẽ bao gồm API đa luồng, xử lý ngoại lệ và hệ sinh thái thư viện phong phú. Nó cũng rất thuận tiện trong việc tổ chức mã nguồn theo mô hình nhiều tầng (Multi-tier Architecture), rất phù hợp cho bài tập lớn yêu cầu khắt khe về kiến trúc và OOP.

2. Công nghệ giao diện: Java Swing
Java Swing được chọn làm công nghệ xây dựng giao diện người dùng (GUI). 
- Swing là một phần của Java Standard Edition (Java SE), do đó không cần cài đặt thêm framework bên ngoài.
- Nó cung cấp đầy đủ các thành phần (components) cần thiết để xây dựng form CRUD (Create, Read, Update, Delete) như JFrame, JPanel, JTable, JButton, JTextField, JComboBox.
- Mặc dù cổ điển, Swing rất nhẹ và hiệu quả cho các ứng dụng quản lý quy mô nhỏ đến vừa, đồng thời cho phép tùy chỉnh giao diện đủ tốt cho mục đích bài tập.

3. Cơ sở dữ liệu: MySQL
MySQL được sử dụng làm Hệ quản trị cơ sở dữ liệu quan hệ (RDBMS) cho dự án.
- Rất phù hợp để mô hình hóa và lưu trữ các thực thể có cấu trúc rõ ràng như Sản phẩm, Khóa đơn, Khách hàng, Đơn hàng.
- Việc giao tiếp với cơ sở dữ liệu sẽ được thực hiện thông qua JDBC (Java Database Connectivity) nguyên bản, giúp hiểu rõ quá trình thực thi truy vấn SQL và quản lý kết nối.
- MySQL có hiệu suất cao, dễ dàng sao lưu và khôi phục dữ liệu.

4. Thư viện sử dụng
- MySQL Connector/J: JDBC Driver bắt buộc để Java kết nối được với MySQL.
- iText / Apache PDFBox (Tùy chọn): Phục vụ cho chức năng xuất hóa đơn ra file PDF để in ấn.
- JCalendar (Tùy chọn): Cung cấp component chọn ngày tháng (Date picker) trên giao diện Swing.

Nếu không cần thiết, hoàn toàn có thể triển khai dự án này chỉ bằng Java Core + Swing + JDBC mà không cần bất kỳ thư viện ngoài nào khác (ngoại trừ JDBC Driver).

III. Mô hình kiến trúc lựa chọn

Dự án sử dụng Mô hình 3 lớp (3-Tier Architecture) kết hợp mô hình MVC đơn giản. Kiến trúc này chia tách rõ ràng trách nhiệm của các thành phần trong hệ thống:

1. Presentation Layer / View (Giao diện):
- Chứa toàn bộ các class kế thừa từ Java Swing (JFrame, JPanel).
- Chịu trách nhiệm hiển thị dữ liệu và thu thập tương tác từ người dùng.
- Ví dụ: LoginView, ProductView, InvoiceView, VoucherView.

2. Controller Layer (Điều khiển):
- Làm cầu nối giữa View và Service. 
- Nhận các sự kiện (events) từ View (ví dụ: bấm nút Lưu), thu thập dữ liệu trên form, gọi các phương thức tương ứng ở tầng Service và cập nhật lại kết quả lên View.
- Ví dụ: ProductController, InvoiceController, AuthController.

3. Service Layer (Nghiệp vụ):
- Chứa toàn bộ logic tính toán, kiểm tra tính hợp lệ (validation), áp dụng các thuật toán giảm giá, xử lý quy trình.
- Đây là nơi tập trung áp dụng các Behavioral Design Pattern (Strategy, State) nếu phù hợp.
- Ví dụ: ProductService, InvoiceService, VoucherService.

4. DAO Layer (Data Access Object):
- Lớp chịu trách nhiệm giao tiếp trực tiếp với cơ sở dữ liệu thông qua JDBC. Mọi câu lệnh SQL đều nằm ở đây.
- Ví dụ: ProductDAO, CustomerDAO, InvoiceDAO.

5. Model / Entity (Thực thể):
- Đại diện cho dữ liệu nghiệp vụ.
- Ví dụ: Product, Customer, Invoice, Order, Voucher.

Giải thích lý do kiến trúc:
- Ngăn View gọi trực tiếp DAO: Giúp tách biệt logic hiển thị và logic truy xuất. View chỉ quan tâm đến việc nhận đầu vào và hiển thị đầu ra, Controller điều phối, còn thao tác DB thì giao hoàn toàn cho DAO.
- Tách Controller, Service, DAO: Đảm bảo tầng truy xuất dữ liệu (DAO) chỉ làm nhiệm vụ thuần túy là CRUD. Mọi logic phức tạp, ràng buộc nghiệp vụ nằm ở Service, giúp dễ bảo trì, dễ thay đổi (ví dụ đổi logic tính thuế mà không ảnh hưởng Controller/DAO) và dễ viết Unit Test cho Service độc lập với Database.
- Các Design Pattern sẽ được phân bổ hợp lý: Creational cho việc tạo đối tượng ở Model/Service, Structural ở ranh giới các tầng (Adapter), và Behavioral để xử lý logic bên trong tầng Service hoặc Model.

IV. Cấu trúc thư mục dự án

Cấu trúc thư mục Java được tổ chức chi tiết như sau:

ClothingStoreManagement/
  src/
    main/
      java/
        com/
          clothingstore/
            Main.java
            config/
              DatabaseConfig.java
              AppConfig.java
            database/
              ConnectionManager.java
              DatabaseInitializer.java
            model/
              Product.java
              BasicProduct.java
              PremiumProduct.java
              Customer.java
              Invoice.java
              Order.java
              Voucher.java
              Supplier.java
            view/
              LoginView.java
              MainFrame.java
              ProductView.java
              InvoiceView.java
              CustomerView.java
              VoucherView.java
              OrderView.java
              SupplierView.java
            controller/
              AuthController.java
              ProductController.java
              InvoiceController.java
              VoucherController.java
              OrderController.java
            service/
              AuthService.java
              ProductService.java
              InvoiceService.java
              VoucherService.java
              OrderService.java
              NotificationService.java
            dao/
              ProductDAO.java
              CustomerDAO.java
              InvoiceDAO.java
              VoucherDAO.java
              OrderDAO.java
              SupplierDAO.java
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
              ValidationUtil.java
              DateUtil.java
              DialogUtil.java
  lib/
    mysql-connector-j.jar
  resources/
    images/
    sql/
      database.sql

Vai trò của các package:
- config: Chứa các class lưu trữ tham số cấu hình tĩnh của ứng dụng.
- database: Quản lý luồng kết nối CSDL và các script tự khởi tạo dữ liệu.
- model: Chứa các thực thể dữ liệu (Entity) ánh xạ với CSDL.
- view: Chứa toàn bộ giao diện GUI Swing.
- controller: Điều hướng luồng sự kiện từ người dùng.
- service: Xử lý nghiệp vụ lõi (Business Logic).
- dao: Giao tiếp trực tiếp với Database qua các câu lệnh SQL.
- pattern: Nhóm các interface/abstract/class cụ thể đặc thù của Design Pattern, giúp tường minh kiến trúc.
- util: Chứa các tiện ích dùng chung (format chuỗi, ngày tháng, thông báo hộp thoại).
- resources: Lưu trữ tài nguyên không biên dịch (ảnh, icon, script SQL).

V. Thiết kế cơ sở dữ liệu

Hệ thống cơ sở dữ liệu bao gồm các bảng chính sau:

1. users: Lưu thông tin đăng nhập của nhân viên/quản lý.
- Mục đích: Quản lý phân quyền và truy cập.
- Trường chính: user_id (PK), username, password, role.
2. products: Lưu thông tin mặt hàng quần áo.
- Mục đích: Quản lý kho sản phẩm bán ra.
- Trường chính: product_id (PK), name, category, size, color, price, stock, supplier_id (FK).
3. customers: Lưu thông tin khách hàng.
- Mục đích: Chăm sóc khách hàng và tích điểm.
- Trường chính: customer_id (PK), full_name, phone, email.
4. suppliers: Quản lý nhà cung cấp.
- Mục đích: Lưu thông tin nhập hàng.
- Trường chính: supplier_id (PK), name, contact_info, status.
5. invoices: Hóa đơn bán hàng.
- Mục đích: Lưu thông tin tổng quan của một giao dịch thanh toán trực tiếp.
- Trường chính: invoice_id (PK), customer_id (FK), user_id (FK), created_at, total_amount.
6. invoice_details: Chi tiết từng dòng sản phẩm trong hóa đơn.
- Mục đích: Lưu số lượng và giá của từng mặt hàng mua trong một hóa đơn.
- Trường chính: id (PK), invoice_id (FK), product_id (FK), quantity, unit_price, subtotal.
7. orders: Đơn đặt hàng (thường dùng cho bán nợ hoặc giao hàng).
- Mục đích: Theo dõi trạng thái đặt hàng.
- Trường chính: order_id (PK), customer_id (FK), order_date, status (Pending/Paid/Completed).
8. order_details: Chi tiết đơn đặt hàng.
- Mục đích: Danh sách mặt hàng khách đặt.
- Trường chính: id (PK), order_id (FK), product_id (FK), quantity, price.
9. vouchers: Mã giảm giá toàn hệ thống.
- Mục đích: Khuyến mãi giảm giá cho hóa đơn.
- Trường chính: voucher_code (PK), type, value, min_order_value, expiration_date.
10. promotions: Các đợt khuyến mãi gắn nhãn cho sản phẩm cụ thể.
- Mục đích: Làm nổi bật sản phẩm trong sự kiện.
- Trường chính: promotion_id (PK), name, start_date, end_date.
11. events: Thông tin sự kiện của cửa hàng để gửi thông báo.
- Mục đích: Marketing, gửi tin nhắn.
- Trường chính: event_id (PK), title, content, event_date.
12. notifications: Lưu trữ lịch sử thông báo đã gửi.
- Mục đích: Theo dõi xem khách nào đã nhận được tin báo sự kiện.
- Trường chính: id (PK), customer_id (FK), event_id (FK), status.
13. loyalty_points: Tích điểm khách hàng.
- Mục đích: Phân hạng thành viên dựa trên giao dịch mua hàng.
- Trường chính: customer_id (PK, FK), points, tier.

VI. Danh sách Design Pattern áp dụng trong dự án

1. Singleton
- Nhóm: Creational
- Chức năng áp dụng: Quản lý kết nối DB, Session
- Mục đích: Đảm bảo chỉ có một instance duy nhất dùng chung toàn hệ thống

2. Factory Method
- Nhóm: Creational
- Chức năng áp dụng: Tạo hóa đơn, tạo voucher
- Mục đích: Tách rời logic tạo object ra khỏi nơi gọi, dễ mở rộng

3. Abstract Factory
- Nhóm: Creational
- Chức năng áp dụng: Tạo sản phẩm Basic/Premium
- Mục đích: Tạo ra một nhóm (family) các object liên quan mà không chỉ định lớp cụ thể

4. Builder
- Nhóm: Creational
- Chức năng áp dụng: Tạo Customer/Product nhiều thuộc tính
- Mục đích: Tránh constructor quá dài, khởi tạo theo từng bước, dễ validate

5. Adapter
- Nhóm: Structural
- Chức năng áp dụng: Kết nối Service với DAO / chuyển đổi dữ liệu
- Mục đích: Làm tương thích các interface khác biệt, che giấu chi tiết ResultSet khỏi Controller

6. Decorator
- Nhóm: Structural
- Chức năng áp dụng: Gắn giảm giá, nhãn sản phẩm
- Mục đích: Bổ sung hành vi động (ví dụ nhãn "NEW", giảm giá) mà không cần sửa class gốc

7. Observer
- Nhóm: Behavioral
- Chức năng áp dụng: Thông báo sự kiện, cập nhật UI
- Mục đích: Tự động thông báo tới các đối tượng đăng ký khi trạng thái/dữ liệu thay đổi

8. State
- Nhóm: Behavioral
- Chức năng áp dụng: Trạng thái đơn hàng, trạng thái nhà cung cấp
- Mục đích: Thay thế if-else phức tạp bằng class trạng thái riêng biệt, phân quyền hành vi

9. Strategy
- Nhóm: Behavioral
- Chức năng áp dụng: Tính giảm giá/voucher
- Mục đích: Đóng gói thuật toán giảm giá vào các class, hoán đổi linh hoạt tại runtime

VII. Chi tiết triển khai từng Design Pattern trong ứng dụng

1. Singleton Pattern

Vấn đề thực tế trong dự án:
Việc khởi tạo kết nối JDBC tới MySQL rất tốn chi phí hệ thống (thời gian và tài nguyên). Tương tự, session đăng nhập trên ứng dụng desktop (lưu trữ thông tin người dùng đang đăng nhập) chỉ nên tồn tại duy nhất một phiên bản tại một thời điểm để tránh lỗi logic.

Vì sao chọn pattern này:
Singleton đảm bảo rằng ConnectionManager và UserSession có duy nhất một instance trong toàn bộ vòng đời và cung cấp một điểm truy cập toàn cục tới nó.

Các class cần tạo:
ConnectionManager, UserSession.

Sơ đồ luồng xử lý:
Các class khác cần kết nối CSDL không được gọi new ConnectionManager(). Thay vào đó, gọi phương thức ConnectionManager.getInstance(). Phương thức này kiểm tra, nếu chưa có instance thì khởi tạo 1 lần duy nhất, nếu đã có thì trả về.

Cách đặt file: src/main/java/com/clothingstore/database/ConnectionManager.java

Code minh họa:
public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;

    private ConnectionManager() {
        // Private constructor ngăn việc gọi từ khóa new từ bên ngoài
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

Lợi ích & Mở rộng: Tiết kiệm bộ nhớ, quản lý tập trung tài nguyên. Đảm bảo luồng dữ liệu an toàn.

2. Factory Method Pattern

Vấn đề thực tế:
Hệ thống cần sinh ra nhiều loại hóa đơn (Hóa đơn bán hàng, Hóa đơn nhập hàng). Nếu không dùng Factory Method, Controller phải dùng một khối switch-case / if-else rất lớn.

Vì sao chọn:
Factory Method ủy quyền việc tạo đối tượng cho các lớp con (Subclass). Logic khởi tạo phức tạp được giấu kín khỏi Controller.

Các class cần tạo:
Invoice, SalesInvoice, PurchaseInvoice, InvoiceFactory, SalesInvoiceFactory, PurchaseInvoiceFactory.

Sơ đồ luồng xử lý:
Controller xác định loại hóa đơn người dùng chọn, khởi tạo Factory tương ứng (SalesInvoiceFactory). Sau đó, Controller gọi factory.createInvoice() để nhận về một thể hiện cụ thể mang giao diện Invoice.

Cách đặt file: src/main/java/com/clothingstore/pattern/factory/

Code minh họa:
public abstract class InvoiceFactory {
    public abstract Invoice createInvoice();
}

public class SalesInvoiceFactory extends InvoiceFactory {
    @Override
    public Invoice createInvoice() {
        return new SalesInvoice();
    }
}

Lợi ích & Mở rộng: Khi cửa hàng cần thêm loại "Hóa đơn hoàn trả", chỉ cần viết thêm ReturnInvoice và ReturnInvoiceFactory mà không đụng chạm đến mã nguồn của Controller (đúng nguyên lý Open/Closed).

3. Abstract Factory Pattern

Vấn đề thực tế:
Cửa hàng kinh doanh phân khúc sản phẩm cơ bản (Basic) và sản phẩm cao cấp (Premium). Đi kèm với mỗi dòng sản phẩm là phụ kiện hoặc cách đóng gói riêng. 

Vì sao chọn:
Abstract Factory khác với Factory Method ở chỗ nó không chỉ tạo một sản phẩm đơn lẻ, mà tạo ra một "nhóm" (family) các đối tượng có quan hệ với nhau (ví dụ: Áo Basic + Hộp Basic, Áo Premium + Hộp Premium) để đảm bảo không bị râu ông nọ cắm cằm bà kia.

Các class cần tạo:
Product, BasicProduct, PremiumProduct, ProductFactory (Abstract), BasicProductFactory, PremiumProductFactory.

Cách đặt file: src/main/java/com/clothingstore/pattern/abstractfactory/

Lợi ích: Giải quyết được yêu cầu đảm bảo tính đồng bộ của các đối tượng liên đới, dễ dàng bổ sung phân khúc mới mà không làm hỏng quy trình.

4. Builder Pattern

Vấn đề thực tế:
Một đối tượng Product hoặc Customer có hàng tá thuộc tính. Nếu dùng Constructor thì quá dài, dùng Setter thì code rời rạc và có rủi ro đối tượng bị sử dụng khi chưa set đầy đủ thông tin bắt buộc.

Vì sao chọn:
Builder cho phép khởi tạo đối tượng qua từng bước rõ ràng, mô tả bằng code (fluent interface), và có thể validate logic trước khi đối tượng thực sự được sinh ra ở bước cuối cùng.

Các class cần tạo:
Customer, CustomerBuilder, Product, ProductBuilder.

Sơ đồ luồng xử lý:
Controller khởi tạo Builder, truyền từ từ các tham số lấy từ form giao diện vào các hàm .set(), cuối cùng gọi hàm .build(). Hàm .build() kiểm tra dữ liệu và trả về object chính.

Cách đặt file: src/main/java/com/clothingstore/model/ProductBuilder.java

Code minh họa:
Product product = new ProductBuilder()
    .setName("Áo phông nam")
    .setPrice(150000)
    .setSize("L")
    .setColor("Đen")
    .build(); // Sẽ ném ngoại lệ nếu thiếu Name hoặc Price

Lợi ích: Đọc code cực kỳ dễ hiểu, khắc phục nhược điểm Telescoping Constructor của Java.

5. Adapter Pattern

Vấn đề thực tế:
Dữ liệu dưới DAO là các tập kết quả ResultSet mang nặng tính kỹ thuật cơ sở dữ liệu. Controller thì chỉ hiểu dữ liệu dưới dạng Product object. Controller không nên phụ thuộc trực tiếp vào DAO vì nó phá vỡ mô hình phân lớp.

Vì sao chọn:
Adapter chuyển đổi giao diện của một lớp thành một giao diện khác mà máy khách muốn. Ở đây, Service đóng vai trò như một Adapter (hoặc sử dụng một Adapter trung gian) để ẩn đi thao tác ResultSet.

Các class cần tạo:
ProductService, ProductServiceAdapter, ProductDAO, ProductController.

Sơ đồ luồng xử lý:
Controller yêu cầu ProductService.getProducts(). Service chuyển yêu cầu đến DAO. DAO trả về ResultSet. Service (hoặc Adapter) duyệt ResultSet và "chuyển đổi" nó thành List<Product>.

Cách đặt file: src/main/java/com/clothingstore/pattern/adapter/ProductServiceAdapter.java

Lợi ích: Cấu trúc ứng dụng tách biệt hoàn toàn giữa CSDL và Giao diện. Controller không hề có chữ SQL hay ResultSet nào, giao diện luôn nhất quán.

6. Decorator Pattern

Vấn đề thực tế:
Có những lúc cửa hàng cần gắn thêm nhãn "Bán chạy" cho một số sản phẩm, hoặc giảm giá trong dịp lễ cho sản phẩm khác. Nếu dùng kế thừa, ta sẽ phải tạo vô số class kết hợp.

Vì sao chọn:
Decorator dùng composition (thành phần) thay vì inheritance (kế thừa), cho phép "bọc" thêm thuộc tính/hành vi mới lên một object tại runtime.

Các class cần tạo:
ProductComponent, ConcreteProduct, ProductDecorator, DiscountDecorator, NewLabelDecorator, BestSellerDecorator.

Sơ đồ luồng xử lý:
Lấy một sản phẩm thông thường. Đưa nó vào trong DiscountDecorator để thay đổi cách tính giá. Sau đó nếu cần, đưa tiếp kết quả vào NewLabelDecorator để ghi đè cách hiển thị tên.

Cách đặt file: src/main/java/com/clothingstore/pattern/decorator/

Code minh họa:
ProductComponent p = new ConcreteProduct("Quần Jean", 300);
p = new DiscountDecorator(p, 20); // Gắn giảm giá 20%
p = new BestSellerDecorator(p);   // Gắn nhãn Best Seller
// Hàm getDescription() của p giờ sẽ in ra: "[Best Seller] Quần Jean"

Lợi ích: Giảm thiểu số lượng class con cần tạo, mở rộng tính năng linh hoạt.

7. Observer Pattern

Vấn đề thực tế:
Khi quản lý phát một thông báo sự kiện, hệ thống cần gửi tin cho những khách hàng đăng ký nhận tin. Hoặc khi danh sách mặt hàng được cập nhật ngầm, giao diện UI cần ngay lập tức thay đổi theo.

Vì sao chọn:
Observer tạo ra cơ chế Subscribe/Publish. Subject không cần quan tâm Observer là ai, chỉ duy trì danh sách và notify khi có sự kiện.

Các class cần tạo:
Subject, Observer, EventManager, CustomerObserver, NotificationService, ProductTableObserver.

Sơ đồ luồng xử lý:
Khách hàng đăng ký với EventManager. Khi quản lý bấm gửi sự kiện mới, EventManager lặp qua danh sách và gọi phương thức update() của từng khách hàng.

Cách đặt file: src/main/java/com/clothingstore/pattern/observer/

Lợi ích: Loose Coupling - Sự kiện được tách rời khỏi người nhận sự kiện, dễ dàng thêm module lắng nghe sự kiện mà không phải sửa lại code phát sự kiện.

8. State Pattern

Vấn đề thực tế:
Đơn hàng có các trạng thái: Pending, Paid, Completed, Canceled. Ở Pending thì được phép thanh toán. Ở Canceled thì không được thao tác gì. Nếu dùng if-else/switch-case kiểm tra trạng thái trong mỗi hàm nghiệp vụ thì code sẽ vô cùng phức tạp.

Vì sao chọn:
State Pattern giúp thay thế khối if-else cồng kềnh bằng các class riêng biệt. Đơn hàng ủy quyền hành vi cho "Trạng thái" hiện tại của nó.

Các class cần tạo:
OrderState, PendingOrderState, PaidOrderState, CompletedOrderState, CanceledOrderState, Order, SupplierState.

Sơ đồ luồng xử lý:
Order gọi state.pay(this). Khối lệnh bên trong PendingOrderState xử lý và gọi this.setOrderState(new PaidOrderState()). Trạng thái đã tự động luân chuyển.

Cách đặt file: src/main/java/com/clothingstore/pattern/state/

Code minh họa:
public class PendingOrderState implements OrderState {
    public void pay(Order order) {
        System.out.println("Thanh toán thành công.");
        order.setState(new PaidOrderState());
    }
}

Lợi ích: Logic xử lý cho mỗi trạng thái được cô lập. Việc phát sinh thêm trạng thái "Đang giao hàng" (ShippingState) không làm hỏng logic của trạng thái cũ.

9. Strategy Pattern

Vấn đề thực tế:
Khuyến mãi/Voucher có nhiều loại thuật toán giảm giá (Phần trăm, Số tiền cố định, Miễn phí vận chuyển). Các thuật toán này khác biệt nhau và có thể thêm/bớt liên tục vào các dịp lễ tết.

Vì sao chọn:
Strategy định nghĩa một bộ các thuật toán, đóng gói chúng lại và giúp chúng có thể thay thế dễ dàng tại runtime.

Các class cần tạo:
DiscountStrategy, PercentageDiscountStrategy, FixedAmountDiscountStrategy, FreeShippingStrategy, Voucher, OrderService.

Sơ đồ luồng xử lý:
Dựa vào loại Voucher người dùng cung cấp, OrderService (Context) sẽ được tiêm (inject) Strategy tương ứng. Dịch vụ chỉ việc gọi hàm calculate() mà không cần quan tâm công thức bên trong.

Cách đặt file: src/main/java/com/clothingstore/pattern/strategy/

Code minh họa:
public interface DiscountStrategy {
    double calculateDiscount(double total);
}
public class PercentageStrategy implements DiscountStrategy {
    public double calculateDiscount(double total) {
        return total * 0.1; // Giảm 10%
    }
}

Lợi ích: Chuyển đổi linh hoạt công thức tính toán vào thời điểm mua hàng mà không làm mã nguồn hóa đơn bị cứng nhắc. Rất dễ thêm loại giảm giá mới.

VIII. Các bước triển khai ứng dụng từ đầu

Bước 1. Tạo project Java
- Tạo project Java Application thuần bằng IntelliJ IDEA hoặc NetBeans.
- Thiết lập cấu trúc thư mục gồm src/main/java, lib, resources.
- Copy thư viện JDBC (ví dụ mysql-connector-j.jar) vào thư mục lib và Add as Library.
- Khởi tạo class Main.java cấu hình điểm chạy.

Bước 2. Thiết kế database
- Tạo database clothing_store.
- Chạy script SQL thiết lập các bảng chính (users, products, invoices, vouchers,...).
- Đưa vào một vài dòng dữ liệu mồi (dummy data) để chuẩn bị kiểm thử.

Bước 3. Tạo lớp kết nối database
- Xây dựng DatabaseConfig để chứa URL, tài khoản kết nối.
- Khởi tạo class ConnectionManager áp dụng Singleton.
- Thực hiện test truy vấn SELECT cơ bản trong hàm main để đảm bảo kết nối thông suốt.

Bước 4. Tạo model/entity
- Khởi tạo các POJO: Product, Customer, Invoice, Order, Voucher, Supplier, User.
- Xây dựng thêm class Builder cho đối tượng Product/Customer nhằm hỗ trợ việc map dữ liệu thuận tiện.

Bước 5. Tạo DAO
- Định nghĩa các lệnh SQL thuần trong DAO.
- Thực thi 5 phép thao tác CRUD cốt lõi cho mỗi bảng: findAll(), findById(), insert(), update(), delete(). Sử dụng PreparedStatement.

Bước 6. Tạo Service
- Viết tầng nghiệp vụ để kiểm tra các ràng buộc thực tế (validate) trước khi gọi tiếp vào DAO.
- Đây là nơi nhúng các class theo mẫu Strategy, State, Decorator vào để tính toán hóa đơn, luân chuyển trạng thái.

Bước 7. Tạo Controller
- Khai báo Controller là nơi nhận sự kiện từ các nút bấm UI.
- Controller sẽ lấy dữ liệu từ các thẻ Text Field, điều phối Service xử lý, rồi đẩy kết quả cập nhật về View.
- Điều phối thông báo Lỗi hoặc Thành công qua Dialog.

Bước 8. Tạo giao diện Swing
Triển khai từng màn hình cụ thể:
1. LoginView: TextField username, PasswordField, Button đăng nhập.
2. MainFrame: Bố cục thanh menu bên trái, CardLayout khu vực hiển thị panel chính ở giữa.
3. ProductManagementView: Có JTable để hiển thị, Form dọc để điền dữ liệu, nút Thêm/Sửa/Xóa.
4. InvoiceManagementView: Bố cục chia đôi: nửa bảng chọn hàng mua, nửa bill thanh toán có Text Field Voucher.
5. CustomerManagementView: Bảng dữ liệu quản lý thông tin điểm thưởng tích lũy.
6. VoucherManagementView: Bảng quản lý tạo mới mã giảm giá.
7. OrderManagementView: Form quản lý và nút nhấn luân chuyển trạng thái đơn hàng.
8. SupplierManagementView: Form điền thông tin nhà cung cấp.
9. NotificationView: Bảng gửi sự kiện cho người đăng ký.

Bước 9. Áp dụng Design Pattern vào từng module
- Đăng nhập: Singleton Session
- Quản lý sản phẩm: Abstract Factory, Builder, Adapter, Decorator, Observer
- Quản lý hóa đơn: Factory Method
- Quản lý voucher: Factory Method, Strategy
- Quản lý đặt hàng: State, Strategy
- Quản lý nhà cung cấp: State
- Hệ thống thông báo/sự kiện: Observer
- Kết nối DB: Singleton

Bước 10. Kiểm thử
Tiến hành chạy thử các Unit / Integration test:
- Test đăng nhập với tài khoản hợp lệ/sai.
- Test CRUD sản phẩm xem có lưu DB đúng không.
- Test tạo Voucher giảm giá phần trăm và số cố định, nhập vào đơn hàng xem giá tiền có bị trừ sai không.
- Nhấp thao tác chuyển trạng thái Order từ Canceled sang Paid xem có bị chặn bởi logic State Pattern hay không.
- Thử gắn Decortator cho sản phẩm xem tên có thay đổi trên bảng JTable không.

IX. Luồng hoạt động chính của hệ thống

1. Luồng đăng nhập
1. Người dùng mở ứng dụng, giao diện hiển thị màn hình Login.
2. Người dùng nhập tên tài khoản, mật khẩu rồi nhấn nút Đăng nhập.
3. Thao tác kích hoạt AuthController, Controller gọi AuthService.
4. AuthService chuyển tiếp xuống UserDAO để truy vấn cơ sở dữ liệu.
5. Nếu khớp, DB trả về thông tin User. Ứng dụng đưa thông tin đó vào UserSession (dùng Singleton).
6. Ứng dụng điều hướng tắt form Login, chuyển sang màn hình làm việc MainFrame.

2. Luồng quản lý sản phẩm
1. Quản lý cửa hàng mở tab Sản phẩm trên MainFrame.
2. ProductController chỉ thị ProductService truy vấn dữ liệu toàn bộ kho.
3. ProductService gọi ProductDAO để lấy danh sách lên dưới dạng List và hiển thị ở JTable.
4. Để tạo mới, quản lý điền form. Logic thêm mới sử dụng ProductBuilder hoặc AbstractFactory để gom thông tin thành đối tượng Product.
5. Đẩy đối tượng này lưu vào DB.
6. Khối Observer nhận biết kho đã thay đổi bèn tự động refresh bảng JTable.

3. Luồng tạo hóa đơn
1. Khách mua đồ, nhân viên chuyển sang màn hình Hóa đơn, nhấn Tạo mới.
2. InvoiceController đánh giá thông tin và chọn InvoiceFactory phù hợp (ví dụ: SalesInvoiceFactory).
3. Dùng Factory khởi tạo object SalesInvoice.
4. Nhân viên chọn sản phẩm bổ sung vào Invoice.
5. Khi hoàn tất, InvoiceService gọi lưu thông tin hóa đơn (invoices) và chi tiết (invoice_details) xuống CSDL. Có thể gọi hàm in hóa đơn PDF.

4. Luồng áp dụng voucher
1. Nhân viên nhập mã Voucher "DISC10" trên khung hóa đơn.
2. Nhấn Áp dụng, hệ thống tìm trong bảng vouchers xem có hợp lệ.
3. Tùy vào đặc tính Voucher, Controller sinh ra một DiscountStrategy phù hợp (VD: Percentage).
4. Tính toán mức trừ tiền. Cập nhật con số tổng thành tiền trên Label của giao diện.

5. Luồng chuyển trạng thái đơn hàng
1. Đơn đặt hàng nợ lưu vào CSDL mang trạng thái Pending.
2. Nhân viên thấy khách đã thanh toán qua thẻ bèn chọn đơn, bấm Thanh toán.
3. Đối tượng Order gọi state.pay().
4. Lớp xử lý hiện tại chuyển Order đó qua PaidOrderState.
5. Khi nhận hàng thực tế, nhấn Hoàn thành, đơn tiếp tục lặp lại quá trình để đổi sang CompletedOrderState.
6. Mọi sai phạm luân chuyển trái nghiệp vụ (Ví dụ Canceled rồi mà vẫn bấm Paid) đều bị ngăn chặn bởi State Pattern.

X. Quy ước đặt tên class và code

Để dự án duy trì tính chuẩn mực, chuyên nghiệp và thuận tiện khi hoạt động nhóm, mã nguồn tuân thủ các quy định sau:

- Tên Class: Dùng PascalCase. Ví dụ: Product, CustomerManager. Cấm sử dụng tiếng Việt có dấu. Giao diện (Interface) phải có tên rõ nghĩa (như DiscountStrategy), không khuyến khích tiền tố I.
- Biến, Phương thức: Dùng camelCase. Ví dụ: totalAmount, calculateDiscount().
- Hằng số: Toàn bộ chữ IN HOA và tách nhau bởi gạch chân. Ví dụ: DEFAULT_PORT.
- Quy ước đuôi theo kiến trúc:
  DAO luôn kết thúc bằng DAO (ví dụ ProductDAO).
  Tầng nghiệp vụ luôn kết thúc bằng Service (ví dụ ProductService).
  Tầng điều hướng luôn kết thúc bằng Controller (ví dụ ProductController).
  Giao diện Swing kết thúc bằng View (ví dụ ProductView).
- Quy ước đuôi theo Design Pattern:
  Việc áp dụng Design Pattern phải được thể hiện rõ ở tên class.
  Factory: InvoiceFactory.
  State: PendingOrderState.
  Strategy: PercentageDiscountStrategy.
  Decorator: DiscountDecorator.

XI. Lưu ý khi không dùng framework

Do không sử dụng các framework web hay desktop cao cấp, sinh viên cần ghi nhớ:

1. Quản lý kết nối Database bằng tay: Mọi thao tác JDBC tạo Connection, PreparedStatement, ResultSet phải được đặt trong try-with-resources để tự động đóng kết nối, nếu không ứng dụng sẽ phình bộ nhớ và kẹt port.
2. Viết DAO thuần túy: Phải viết câu lệnh String SQL thủ công. Tuyệt đối bắt buộc xài PreparedStatement để phòng chống khai thác lỗ hổng SQL Injection.
3. Phải tự validate dữ liệu: Không có framework hỗ trợ validate model tự động, vì vậy phải tự thiết lập các util check NULL, Regex độ dài, bắt lỗi NumberFormatException từ tầng View trước khi truyền xuống CSDL.
4. Tự điều hướng giao diện: Quản lý hàng chục form Swing dễ dẫn đến loạn màn hình. Phải sử dụng CardLayout để switch các Panel trong một cửa sổ MainFrame duy nhất, thay vì tạo quá nhiều JFrame con.
5. Tự tổ chức cấu trúc Package: Phải tuân thủ việc bỏ file vào đúng thư mục controller, view, model theo MVC để tránh code bị rối.
6. Không có Dependency Injection: Lập trình viên phải tự khởi tạo (new) các tầng, tự truyền DAO vào Service, Service vào Controller (có thể dùng Factory để tạo).
7. Tách biệt rõ ràng các tầng: Tuyệt đối tránh thói quen viết câu lệnh SELECT, UPDATE ngay trong khối code sự kiện nút nhấn của JButton. Việc đó phá hỏng toàn bộ kiến trúc.

XII. Kết luận

Dự án Xây dựng ứng dụng desktop quản lý cửa hàng thời trang bằng Java thuần là một bài tập thực tiễn có giá trị học thuật vô cùng to lớn. Việc không lạm dụng các framework giúp sinh viên nhìn thấu bản chất của ngôn ngữ lập trình, hiểu rõ cách dữ liệu di chuyển từ tầng GUI (Swing), qua tầng Logic (Service), đến tầng kết nối phần cứng (JDBC) và vào cơ sở dữ liệu.

Đặc biệt, hệ thống đã ứng dụng triệt để Mô hình 3 lớp (3-tier) kết hợp MVC và 9 mẫu Design Pattern (Singleton, Factory Method, Abstract Factory, Builder, Adapter, Decorator, Observer, State, Strategy) chuẩn mực nhất. Việc này biến một ứng dụng với hàng chục nghiệp vụ đan chéo phức tạp (tích điểm, mã giảm giá, phân quyền trạng thái hóa đơn) trở thành một hệ thống rời rạc an toàn (loose-coupling).

Thiết kế này mang lại khả năng tái sử dụng (reusability) và mở rộng (scalability) xuất sắc. Trong tương lai, dự án hoàn toàn có thể được mở rộng bằng cách phát triển thêm các module báo cáo doanh thu dưới dạng biểu đồ, cấy thêm hệ thống tự in hóa đơn PDF, hoặc trích xuất một phần Service để thiết kế các API giao tiếp với các nền tảng bán hàng thương mại điện tử trực tuyến. Tài liệu hướng dẫn này đóng vai trò kim chỉ nam giúp việc lập trình và bảo trì dự án trở nên có tính hệ thống và tiệm cận với quy trình phát triển sản phẩm doanh nghiệp chuyên nghiệp.
