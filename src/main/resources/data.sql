-- Populating Roles
INSERT INTO role (role_id, role)
VALUES (1, 'ROLE_USER')
    ON DUPLICATE KEY UPDATE role_id=role_id;
INSERT INTO role (role_id, role)
VALUES (2, 'ROLE_ADMIN')
    ON DUPLICATE KEY UPDATE role_id=role_id;
INSERT INTO role (role_id, role)
VALUES (3, 'ROLE_VENDOR')
    ON DUPLICATE KEY UPDATE role_id=role_id;

INSERT INTO email_template (template_id, from_email, mail_type, subject, template)
VALUES (1, 'abc@abc.com', 'ORDER_CONFIRM', 'test', 'template djdj')
ON DUPLICATE KEY UPDATE template_id=template_id;