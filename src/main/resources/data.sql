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