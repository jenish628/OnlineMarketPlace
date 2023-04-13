-- Populating Roles


INSERT INTO email_template (template_id, from_email, mail_type, subject, template)
VALUES (1, 'abc@abc.com', 'ORDER_CONFIRM', 'test', 'template djdj')
ON DUPLICATE KEY UPDATE template_id=template_id;