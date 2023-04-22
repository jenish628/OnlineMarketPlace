-- Populating Roles
INSERT INTO role (role_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_VENDOR') ON DUPLICATE KEY
UPDATE role_id=role_id;


INSERT INTO user (user_id, email, password, user_status, role_id, full_name) -- all passwords are: test
VALUES (1, 'user@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 1, 'Demo User'),
       (2, 'test@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 1, 'Test User'),
       (3, 'admin@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 2, 'Test Admin'),
       (4, 'vendor@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 3, 'Test Vendor'),
       (5, 'apple@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 3, 'Apple Inc'),
       (6, 'miu-shop@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 3, 'MIU Shop') ON DUPLICATE KEY
UPDATE user_id=user_id;

INSERT INTO vendor (vendor_id, vendor_name, description, logo_file_id, user_id)
VALUES (1, 'Apple',
        'Apple Inc. is an American multinational technology manufacturing electronic gadgets and providing services. Manufactures Mac, iPhone, iPad etc. ',
        null, 5),
       (2, 'MIU',
        'Miu Store- Shop your local store for a wide selection of items in electronics, home furniture & appliances, toys, clothing, baby gear, video games, and more',
        null, 6),
       (3, 'MI-Xiaomi',
        'MI Phone, appliances, toys, and more', null, 4)ON DUPLICATE KEY
UPDATE vendor_id=vendor_id;

INSERT INTO product_category (category_id, category)
VALUES (1, 'Computers'),
       (2, 'Books'),
       (3, 'Kitchen'),
       (4, 'Fashion'),
       (5, 'Electronic'),
       (6, 'Clothing') ON DUPLICATE KEY
UPDATE category_id=category_id;

INSERT INTO product (product_id, description, is_deleted, is_verified, name, price, quantity, category_id, vendor_id)
VALUES (1,
        'Apple 2023 MacBook Pro Laptop M2 Pro chip with 12‑core CPU and 19‑core GPU: 16.2-inch Liquid Retina XDR Display, 16GB Unified Memory, 512GB SSD Storage. Works with iPhone/iPad; Silver',
        null, true, 'MacBook Pro 16 (M2 Pro)', 10, 10, 1, 1),
       (2, 'Practical recipes for enterprise Java developers to deliver large scale applications with Jakarta EE', null,
        true, 'Java 17 Recipes', 20, 50, 2, 2),
       (3, 'Housewares digital Cool-Touch Rice Grain Cooker and Food Steamer, Stainless, Silver, 4-Cup (Uncooked) / 8-Cup (Cooked)',
        null, true, 'Aroma Rice Cooker',15, 5, 3, 2),
       (4, 'Xiaomi Pad 5 comes equipped with large 11 screen, slim, stylish design,Qualcomm Snapdragon 860, WQHD+ 120Hz display,8720mAh (typ) high-capacity battery.',
        null, true, 'Xiaomi Pad 5',50, 15, 5, 3),
       (5, ' 70% Nylon, 25% Polyester, 5% Spandex Pull On closureGusseted Crotch: Gusseted crotch for greater freedom of movement, Flatlock seams reduce irritation caused by chafing',
                null, true, 'OQQ Women 3 Piece High Waist Workout Shorts Butt Lifting Tummy Control Ruched Booty Smile Yoga Short Pants', 31, 15, 4, 3),
       (6, 'Samsung Galaxy S24 Plus 5G Smartphone with Exynos 2200 chip: 6.8-inch Dynamic AMOLED 2X Display, 16GB RAM, 512GB Internal Storage, 108MP Camera, 4500mAh Battery. Works with Samsung Galaxy Watch; Phantom Black',
        null, true, 'Samsung Galaxy S24 Plus', 1000, 20, 1, 3),
       (7, 'The Lean Startup: How Today’s Entrepreneurs Use Continuous Innovation to Create Radically Successful Businesses',
        null, true, 'The Lean Startup', 15, 30, 2, 2),
       (8, 'Stainless Steel 3-Piece Mixing Bowl Set with Non-Slip Silicone Base, Easy Pour Spout, Measurement Marks, and Nesting Design',
        null, true, 'Mixing Bowl Set', 25, 8, 3, 1),
       (9, 'Canon EOS R7 Mirrorless Camera with RF24-105mm F4-7.1 IS STM Lens: 30.1 Megapixel Full-frame CMOS Sensor, Dual Pixel CMOS AF, DIGIC X Image Processor, 4K Video Recording. Works with Canon Connect App; Black',
        null, true, 'Canon EOS R7', 1500, 12, 5, 3),
       (10, 'Men’s Slim-Fit Dress Shirt with Spread Collar, French Cuffs, and Non-Iron Fabric. Available in White and Blue.',
        null, true, 'Slim-Fit Dress Shirt', 45, 25, 4, 2),
      (11, 'Samsung Galaxy S22 Ultra 5G with 6.8-inch AMOLED display, Exynos 2200 processor, 12GB RAM, 512GB storage, quad camera setup, 5000mAh battery',
      null, true, 'Samsung Galaxy S22 Ultra 5G', 1299, 20, 3, 1),
      (12, 'Bose QuietComfort 45 Noise Cancelling Headphones with 24-hour battery life, Alexa/Google Assistant voice control, and comfortable ear cushions',
      null, true, 'Bose QuietComfort 45', 329, 30, 1, 1),
      (13, 'Amazon Basics 10-Piece Non-Stick Cookware Set with PFOA-free coating, comfortable grip handles, and easy-to-clean design',
      null, true, 'Amazon Basics 10-Piece Cookware Set', 69, 10, 3, 2),
      (14, 'Sony PlayStation 5 Gaming Console with 4K UHD Blu-Ray Drive and DualSense Wireless Controller',
      null, true, 'Sony PlayStation 5', 499, 5, 6, 1),
      (15, 'Samsung 55-inch QLED QN90A Series Smart TV with 4K UHD resolution, Quantum HDR 32X, and Object Tracking Sound',
      null, true, 'Samsung 55-inch QN90A Smart TV', 1799, 8, 1, 3) ON DUPLICATE KEY
UPDATE product_id=product_id;

INSERT INTO email_template (template_id, from_email, mail_type, subject, template)
VALUES (1, 'abc@abc.com', 'ORDER_CONFIRM', 'test', 'template djdj') ON DUPLICATE KEY
UPDATE template_id=template_id;

insert into address(address_id, address1, city,country,state,zip_code)
values(1,'1000N 4Th ST', 'Fairfield','USA','IOWA','52557') ON DUPLICATE KEY
update address_id=address_id;

insert into shipping(shipping_id, delivery_instruction,shipping_status,address_id)
values(1,'Leave infront of door','DELIVERED',1) ON DUPLICATE KEY
update shipping_id=shipping_id;

insert into orders(order_id, order_code, shipping_id, user_id)
values(1,'12345',1, 1) ON DUPLICATE KEY
update order_id=order_id;

insert into order_item(order_item_id, discount, price, quantity, tax, is_commissioned, order_id, product_id)
values
       (1, 12, 200, 2, 10, 0, 1, 1),
       (2, 10, 100, 2, 10, 0, 1, 2),
       (3, 8, 500, 4, 10, 0, 1, 3),
       (4, 8, 500, 4, 10, 0, 1, 4) ON DUPLICATE KEY
update order_item_id=order_item_id;

insert into payment(payment_id, card_brand, card_holder_name, card_number, pay_amount, payment_status, transaction_id)
values(1, 'Visa', 'Sanjaya koju', '123456', 2600, 'CONFIRMED',1) ON DUPLICATE KEY
update payment_id=payment_id;

insert into orders_payments(order_order_id, payments_payment_id)
values(1,1) ON DUPLICATE KEY
update order_order_id=order_order_id and payments_payment_id=payments_payment_id;

INSERT INTO shopping_cart (cart_id, created_date, quantity, product_id, user_id)
VALUES (1, '2023-04-19', 1, 1, 1)
ON DUPLICATE KEY
update cart_id=cart_id;

