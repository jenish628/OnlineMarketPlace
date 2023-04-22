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
       (3,
        'Housewares digital Cool-Touch Rice Grain Cooker and Food Steamer, Stainless, Silver, 4-Cup (Uncooked) / 8-Cup (Cooked)',
        null, true, 'Aroma Rice Cooker',15, 5, 3, 2),
       (4,
        'Xiaomi Pad 5 comes equipped with large 11 screen, slim, stylish design,Qualcomm Snapdragon 860, WQHD+ 120Hz display,8720mAh (typ) high-capacity battery.',
        null, true, 'Xiaomi Pad 5',50, 15, 5, 3)ON DUPLICATE KEY
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

