-- =====================================================
-- Script tạo database ShoppingDB (Có dữ liệu mẫu phong phú)
-- Khớp đúng với các entity hiện tại: Category, Product, User
-- =====================================================

-- 1. Tạo cơ sở dữ liệu
CREATE DATABASE ShoppingDB;
GO

-- 2. Chỉ định sử dụng DB vừa tạo
USE ShoppingDB;
GO

-- 3. Tạo bảng categories (khớp Category.java)
CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL UNIQUE,
    status INT NOT NULL DEFAULT 1,
    image NVARCHAR(255) NULL
);
GO

-- 4. Tạo bảng products (khớp Product.java)
CREATE TABLE products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    product_name NVARCHAR(150) NOT NULL,
    price FLOAT NOT NULL,
    description NVARCHAR(MAX) NULL,
    image NVARCHAR(255) NULL,
    status INT NOT NULL DEFAULT 1,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
GO

-- 5. Tạo bảng users (khớp User.java)
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role INT NOT NULL,
    status INT NOT NULL DEFAULT 1
);
GO

-- =====================================================
-- CHÈN DỮ LIỆU MẪU (DUMMY DATA)
-- =====================================================

-- 6. Thêm tài khoản người dùng (Admin, Manager, Customer)
-- Lưu ý: Nếu hệ thống dùng BCrypt, password '123' hoặc '123456' dưới đây 
-- cần đổi thành chuỗi hash (VD: $2a$10$...) để đăng nhập thành công.
INSERT INTO users (username, password, email, role, status)
VALUES 
('admin', '123', 'admin@shoppingservice.com', 1, 1)
GO

-- 7. Thêm các danh mục (Categories)
INSERT INTO categories (category_name, status, image)
VALUES 
(N'Điện thoại thông minh', 1, 'phone_cat.jpg'),
(N'Máy tính xách tay (Laptop)', 1, 'laptop_cat.jpg'),
(N'Thời trang Nam', 1, 'menswear_cat.jpg'),
(N'Thời trang Nữ', 1, 'womenswear_cat.jpg'),
(N'Phụ kiện công nghệ', 1, 'accessories_cat.jpg'),
(N'Danh mục ngừng bán', 0, NULL); -- Danh mục ẩn (Status 0)
GO

-- 8. Thêm các sản phẩm (Products)
-- Chú ý: Dùng SELECT id FROM categories để lấy đúng khóa ngoại
INSERT INTO products (product_name, price, description, image, status, category_id)
VALUES 
-- Sản phẩm: Điện thoại
(N'iPhone 15 Pro Max 256GB', 34990000, N'Điện thoại cao cấp nhất của Apple năm nay, chip A17 Pro siêu mạnh.', 'iphone15_promax.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Điện thoại thông minh')),
(N'Samsung Galaxy S24 Ultra', 33990000, N'Tích hợp Galaxy AI đỉnh cao, bút S-Pen xịn xò, camera 200MP.', 's24_ultra.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Điện thoại thông minh')),
(N'Xiaomi 14 Pro 5G', 22990000, N'Snapdragon 8 Gen 3, sạc siêu nhanh 120W, camera hợp tác Leica.', 'xiaomi14_pro.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Điện thoại thông minh')),

-- Sản phẩm: Laptop
(N'MacBook Air M3 13-inch', 27990000, N'Thiết kế mỏng nhẹ, pin trâu, chip M3 xử lý mượt mà mọi tác vụ văn phòng.', 'macbook_air_m3.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Máy tính xách tay (Laptop)')),
(N'Dell XPS 15 9530', 45000000, N'Màn hình OLED 3.5K tuyệt đẹp, Core i9 thế hệ 13, card RTX 4070.', 'dell_xps15.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Máy tính xách tay (Laptop)')),
(N'Asus ROG Zephyrus G14', 39990000, N'Laptop gaming gọn nhẹ, thiết kế chuẩn eSport, tản nhiệt buồng hơi.', 'asus_rog_g14.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Máy tính xách tay (Laptop)')),

-- Sản phẩm: Thời trang Nam
(N'Áo thun nam Cotton Basic', 150000, N'Áo phông nam chất liệu 100% cotton thoáng mát, thấm hút mồ hôi.', 'ao_thun_nam.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Thời trang Nam')),
(N'Quần jean nam ống suông', 350000, N'Form dáng thoải mái, dễ phối đồ, phong cách năng động.', 'quan_jean_nam.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Thời trang Nam')),

-- Sản phẩm: Thời trang Nữ
(N'Váy dạ hội lụa cao cấp', 1200000, N'Thiết kế xẻ tà quyến rũ, chất lụa tơ tằm mềm mại, sang trọng.', 'vay_da_hoi.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Thời trang Nữ')),
(N'Áo sơ mi nữ công sở', 250000, N'Kiểu dáng thanh lịch, phù hợp môi trường văn phòng.', 'ao_somi_nu.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Thời trang Nữ')),

-- Sản phẩm: Phụ kiện công nghệ
(N'Tai nghe AirPods Pro 2', 6500000, N'Chống ồn chủ động xuất sắc, pin 6 giờ, hộp sạc type-C.', 'airpods_pro2.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Phụ kiện công nghệ')),
(N'Củ sạc Anker 65W GaN', 750000, N'Sạc nhanh cho laptop và điện thoại, kích thước siêu nhỏ gọn.', 'anker_65w.jpg', 1, (SELECT id FROM categories WHERE category_name = N'Phụ kiện công nghệ')),

-- Sản phẩm: Hết hàng/Ngừng bán (Status = 0)
(N'Điện thoại Nokia Cục Gạch', 300000, N'Điện thoại đập đá huyền thoại, pin 1 tháng.', 'nokia_1280.jpg', 0, (SELECT id FROM categories WHERE category_name = N'Điện thoại thông minh'));
GO