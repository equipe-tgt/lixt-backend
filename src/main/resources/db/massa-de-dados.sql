-- Usuario com senha 123
insert into lixt.tb_user (id_user, bl_activated, st_email, st_name, st_username, st_password) value
(null, 1, 'leo_narita@hotmail.com', 'leo', 'leo', '$2a$10$DXXYBmXJaM7bMRtmHQ34Feng12PKb5ZJgZnVJ5/3rGY3xqGjjrXNG');

insert into lixt.tb_category (id_category, st_name) value
(null, 'Alimentação'),
(null, 'Limpeza'),
(null, 'Higiene');

insert into lixt.tb_product (id_product, st_name, id_user, id_category, nr_measure_value, st_barcode, en_measure_type) values
(null, "Arroz", null, 1,  5,  null, 3),
(null, "Feijão", null, 1, 1,  null, 3),
(null, "Leite", null, 1, 1,  null, 1),
(null, "Ovo", null, 1, 1,  null, 6),
(null, "Açúcar", null, 1, 1,  null, 3),
(null, "Sal", null, 1, 1,  null, 3),
(null, "Mortadela Sadia", null, 1, 1,  null, 3),
(null, "Mortadela Perdigão", null, 1, 1,  null, 3),
(null, "Sabonete", null, 2, 1,  null, 6);

insert into lixt.tb_list(id_list, st_description, st_name_list, id_user) values
(null, "", "Lista 001", (select id_user from lixt.tb_user where st_username = 'leo'));

use lixt;
insert into lixt.tb_purchase_local (id_purchase_local, st_name, point_location) values
(null, "Mercadinho", ST_GeomFromText('POINT(18.333 -63.333)'));

insert into lixt.tb_purchase (id_purchase, id_user, id_purchase_local, nr_purchase_price, dt_purchase_date) values
(null, (select id_user from lixt.tb_user where st_username = 'leo'), (select id_purchase_local from lixt.tb_purchase_local where st_name = 'Mercadinho'), 200, '2021-10-02'),
(null, (select id_user from lixt.tb_user where st_username = 'leo'), (select id_purchase_local from lixt.tb_purchase_local where st_name = 'Mercadinho'), 200, '2021-09-20');

insert into lixt.tb_purchase_list (id_purchase_list, id_purchase, id_list, nr_partial_purchase_price) values
(null, 1, 1, 200),
(null, 2, 1, 100);

insert into lixt.tb_item_of_purchase (id_item_of_purchase, id_product, id_purchase_list, st_name, nr_amount, nr_price, nr_measure_value, en_measure_type) values
(null, 1, 1, 'Arroz', 3, 60, 5, 3),
(null, 1, 1, 'Feijão', 2, 40, 1, 3),
(null, 1, 1, 'Leite', 50, 60, 1, 1),
(null, 9, 1, 'Sabonetes', 20, 40, 1, 6),
(null, 5, 2, 'Açucar', 10, 50, 1, 3),
(null, 5, 2, 'Sal', 10, 50, 1, 3);