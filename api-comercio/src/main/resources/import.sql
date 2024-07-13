INSERT INTO tb_category(name) VALUES ('Frutas');
INSERT INTO tb_category(name) VALUES ('Frios');
INSERT INTO tb_category(name) VALUES ('Doces');

INSERT INTO tb_product(name, price, description, img_url) VALUES ('Maçã verde', 6.0, 'Doce e fresca', 'www.img.com.br');
INSERT INTO tb_product(name, price, description, img_url) VALUES ('Picanha', 60.4, 'De primeira qualidade', 'www.img.com.br');
INSERT INTO tb_product(name, price, description, img_url) VALUES ('Filé mignon', 40.90, 'De primeira qualidade', 'www.img.com.br');
INSERT INTO tb_product(name, price, description, img_url) VALUES ('Bolo de chocolate', 20.0, 'Caseiro', 'www.img.com.br');

INSERT INTO tb_product_category(product_id, category_id) VALUES (1, 1);
INSERT INTO tb_product_category(product_id, category_id) VALUES (2, 2);
INSERT INTO tb_product_category(product_id, category_id) VALUES (3, 2);
INSERT INTO tb_product_category(product_id, category_id) VALUES (4, 3);

INSERT INTO tb_role(authority) VALUES ('Admin');
INSERT INTO tb_role(authority) VALUES ('Manager');
INSERT INTO tb_role(authority) VALUES ('Employee');

INSERT INTO tb_user(first_name, last_name, email, password) VALUES ('Vanessa', 'Aparecida', 'vanessa@gmai.com', '123456');
INSERT INTO tb_user(first_name, last_name, email, password) VALUES ('Maria', 'Silva', 'maria@gmai.com', '123456');
INSERT INTO tb_user(first_name, last_name, email, password) VALUES ('Gustavo', 'Barros', 'gustavo@gmai.com', '123456');

INSERT INTO tb_user_role(user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role(user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role(user_id, role_id) VALUES (2, 3);
INSERT INTO tb_user_role(user_id, role_id) VALUES (3, 3);


