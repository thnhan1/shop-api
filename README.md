# Shop API
> **README này được tạo bởi Copilot Assitive.**

## Giới thiệu

**Shop API** là hệ thống backend RESTful API xây dựng bằng Spring Boot, phục vụ cho các ứng dụng thương mại điện tử. API hỗ trợ quản lý sản phẩm, danh mục, giỏ hàng, đơn hàng và thanh toán. Dự án phù hợp để học tập hoặc triển khai thực tế cho các shop thời trang online vừa và nhỏ.

## Tính năng nổi bật

- CRUD danh mục sản phẩm, sản phẩm và biến thể
- Quản lý giỏ hàng cá nhân, thêm/xóa/sửa sản phẩm trong giỏ
- Đặt hàng, quản lý đơn hàng, trạng thái đơn hàng
- Xử lý thanh toán đơn hàng (tạo link thanh toán bằng PayOS).
- Tài liệu hóa API với Swagger (OpenAPI)
- Triển khai chuẩn, tách biệt các tầng Service - Repository - Controller
- Sử dụng Lombok, MapStruct, Spring Data JPA, Spring Validation

## Công nghệ sử dụng

- Java 21
- Spring Boot 3.3.x
- Spring Data JPA
- PostgreSQL (dev/test dùng H2 database)
- Lombok, MapStruct
- Springdoc OpenAPI (Swagger UI)
- JUnit, Mockito (unit test)
- Maven

## Cài đặt & chạy ứng dụng

### Yêu cầu

- Java 21+
- Maven 3.8+
- PostgreSQL (nếu chạy production)
- Tài khoản PayOS để tích hợp api thanh toán.
- Ngrok để tạo route cho webhook của PayOS hoạt đông.

### Cấu hình

Sửa `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/shopdb
spring.datasource.username=your_db_user
spring.datasource.password=your_db_pass
spring.jpa.hibernate.ddl-auto=update
# payos secret, api key, and checksum
```

### Build & run

```bash
mvn clean install
mvn spring-boot:run
```

App mặc định chạy tại: [http://localhost:8080](http://localhost:8080)

## Tài liệu API

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Cấu trúc thư mục

```
src/
 └── main/
     ├── java/com/nhanab/shop/
     │    ├── controller/   // REST API endpoints
     │    ├── service/      // Business logic
     │    ├── model/        // Entity classes
     │    ├── dto/          // Request/Response DTOs
     │    ├── mapper/       // MapStruct mappers
     │    └── repository/   // JPA repositories
     └── resources/
          ├── application.properties
pom.xml
README.md
```

---

## Entities chính

- **User**: Người dùng
- **Category**: Danh mục sản phẩm
- **Product**: Sản phẩm
- **ProductVariant**: Biến thể sản phẩm (size, màu...)
- **ProductImage**: Ảnh sản phẩm
- **Cart**: Giỏ hàng
- *CartItem** Sản phẩm trong giỏ hàng
- **Order**: Đơn hàng
- **OrderItem**: Sản phẩm trong đơn hàng
- **Payment**: Log Thanh toán

---

## Bảng mô tả API

| Resource   | Endpoint                                   | Method   | Chức năng                                      |
|------------|--------------------------------------------|----------|------------------------------------------------|
| **Category**   | /api/categories                            | GET      | Lấy danh sách tất cả danh mục                   |
|            | /api/categories/{id}                        | GET      | Lấy chi tiết 1 danh mục theo id                 |
|            | /api/categories                              | POST     | Thêm mới danh mục                               |
|            | /api/categories/{id}                        | PUT      | Cập nhật danh mục                               |
|            | /api/categories/{id}                        | DELETE   | Xóa danh mục                                    |
| **Product**    | /api/products                               | GET      | Lấy danh sách sản phẩm (phân trang)             |
|            | /api/products?categoryId={categoryId}       | GET      | Lấy sản phẩm theo category (phân trang)         |
|            | /api/products/{id}                          | GET      | Lấy chi tiết sản phẩm                           |
|            | /api/products                                | POST     | Thêm mới sản phẩm                               |
|            | /api/products/{id}                          | DELETE   | Xóa sản phẩm                                    |
|            | /api/products/variants/{variantId}          | PUT      | Cập nhật biến thể sản phẩm                      |
| **Cart**       | /api/carts/{userId}                          | GET      | Lấy giỏ hàng của user                           |
|            | /api/carts/items                            | POST     | Thêm sản phẩm vào giỏ hàng                      |
|            | /api/carts/{userId}/items/{itemId}          | DELETE   | Xóa 1 sản phẩm khỏi giỏ hàng                    |
|            | /api/carts/{userId}                         | DELETE   | Xóa toàn bộ sản phẩm trong giỏ hàng             |
|            | /api/carts/merge/{guestCartId}              | POST     | Gộp giỏ hàng guest vào user (chưa implement)    |
| **Order**      | /api/orders                                  | POST     | Đặt hàng (tạo mới đơn hàng)                     |
|            | /api/orders/{id}                            | GET      | Lấy chi tiết đơn hàng theo id                   |
|            | /api/orders/user/{userId}                   | GET      | Lấy danh sách đơn hàng theo user                |
|            | /api/orders                                 | PUT      | Cập nhật thông tin đơn hàng                     |
|            | /api/orders/{id}                            | DELETE   | Hủy đơn hàng (nếu còn trạng thái pending)       |

---

## Đóng góp

1. Fork repo, tạo branch từ `main`
2. Commit code rõ ràng, mô tả chi tiết thay đổi
3. Tạo pull request về repo gốc

## License

MIT

---

**Liên hệ:** [GitHub - thnhan1](https://github.com/thnhan1)
