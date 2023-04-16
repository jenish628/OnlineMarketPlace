-- Populating Roles
INSERT INTO role (role_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_VENDOR') ON DUPLICATE KEY
UPDATE role_id=role_id;

INSERT INTO user (user_id, email, password, user_status, role_id) -- password: admin and admin1
VALUES (1, 'admin@gmail.com', '$2a$10$q1WklZkp3zkx6gspnQ.BHO.5mJAQ1BV6iMurqfE4xUC6TQhT/YAwy', 'VERIFIED', 2),
       (2, 'admin1@gmail.com', '$2a$10$yGbK6FEUFHKyFlMXJs2SY.9kYCNVLVtCEJHZLINQ3LD5hmkR0bfOS', 'VERIFIED', 2) ON DUPLICATE KEY
UPDATE user_id=user_id;

INSERT INTO email_template (template_id, from_email, mail_type, subject, template)
VALUES (1, 'abc@abc.com', 'ORDER_CONFIRM', 'test', 'template djdj') ON DUPLICATE KEY
UPDATE template_id=template_id;