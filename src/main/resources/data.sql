-- Populating Roles
INSERT INTO role (role_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_VENDOR') ON DUPLICATE KEY
UPDATE role_id=role_id;

INSERT INTO user (user_id, email, password, user_status, role_id) -- password: test and test
VALUES (1, 'admin@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 2),
       (2, 'admin1@gmail.com', '$2a$10$bGyGVholFvN93tqon7LQeeTdOA6VFibsCbmFroFZ4RkeGHMJ7Fh9e', 'ACTIVE', 2) ON DUPLICATE KEY
UPDATE user_id=user_id;


INSERT INTO email_template (template_id, from_email, mail_type, subject, template)
VALUES (1, 'abc@abc.com', 'ORDER_CONFIRM', 'test', 'template djdj') ON DUPLICATE KEY
UPDATE template_id=template_id;

insert into product_category(category_id, category)
values
       (1, 'Fashion'),
       (2, 'Electronic'),
       (3, 'Clothing') ON DUPLICATE KEY
update category_id=category_id;

insert into address(address_id, address1, city,country,state,zip_code)
values(1,'1000N 4Th ST', 'Fairfield','USA','IOWA','52557') ON DUPLICATE KEY
update address_id=address_id;

insert into shipping(shopping_id, delivery_instruction,shipping_status,address_address_id)
values(1,'Leave infront of door',1,1) ON DUPLICATE KEY
update shopping_id=shopping_id;

insert into vendor(vendor_id, description, user_id)
values(1, 'Online Market', 1) ON DUPLICATE KEY
update vendor_id=vendor_id;

insert into product(product_id, description,name,quantity,category_id, vendor_id)
values(1, 'Leather jacket with good quality', 'Jacket', 120,1,1),
      (2, 'Leather jacket with good quality', 'Jacket', 120,2,1),
      (3, 'Leather jacket with good quality', 'Jacket', 120,3,1) ON DUPLICATE KEY
update product_id=product_id;

insert into orders(order_id, shipping_shopping_id)
values(1,1) ON DUPLICATE KEY
update order_id=order_id;

insert into order_item(order_item_id, discount, price, quantity, tax, is_commissioned, order_id, product_id)
values
       (1, 12, 200, 2, 10, 0, 1,1),
       (2, 10, 100, 2, 10, 0, 1,2),
       (3, 8, 500, 4, 10, 0, 1,3) ON DUPLICATE KEY
update order_item_id=order_item_id;

insert into payment(payment_id, card_brand, card_holder_name, card_number, pay_amount, payment_status, transaction_id)
values(1, 'Visa', 'Sanjaya koju', '123456', 2600, 2,1) ON DUPLICATE KEY
update payment_id=payment_id;

insert into orders_payments(order_order_id, payments_payment_id)
values(1,1) ON DUPLICATE KEY
update order_order_id=order_order_id and payments_payment_id=payments_payment_id;