-- -----------------------------------------------------
-- OPERAR DATABASE
-- -----------------------------------------------------

CREATE DATABASE lixt;
DROP DATABASE lixt;


-- -----------------------------------------------------
-- EXECUTAR SELECTS
-- -----------------------------------------------------

select * from lixt.tb_user;
select * from lixt.tb_list;
select * from lixt.tb_product_of_list;
select * from lixt.tb_product;
select * from lixt.tb_category;
select * from lixt.tb_list_members;
select * from lixt.tb_comment;

select * from lixt.tb_purchase_local;
SELECT id_purchase_local, st_name, st_name, ST_Y(point_location), ST_X(point_location) FROM lixt.tb_purchase_local;

-- Find near 10 meters
SELECT st_name, ST_Y(point_location) AS longitude, ST_X(point_location) AS latitude FROM lixt.tb_purchase_local
	WHERE st_distance_sphere(POINT(20.77777,23.66666), point_location) <= 10;


-- -----------------------------------------------------
-- CRIAR DADOS
-- -----------------------------------------------------

-- Usuario com senha 123
insert into lixt.tb_user (id_user, bl_activated, st_email, st_name, st_username, st_password) value
(null, 1, 'abc@gmail.com', 'abc', 'abc', '$2a$10$DXXYBmXJaM7bMRtmHQ34Feng12PKb5ZJgZnVJ5/3rGY3xqGjjrXNG');

insert into lixt.tb_category (id_category, st_name) value
(null, 'Alimentação');

insert into lixt.tb_product (id_product, st_name, id_user, id_category, nr_measure_value, st_barcode, en_measure_type) values
(null, "VALUE1", 1, 1,  300,  null, 1),
(null, "VALUE2", 2, 1, 300,  null, 1);

insert into lixt.tb_list(id_list, st_description, st_name_list, id_user) values
(null, "", "Lista3", 1);

insert into lixt.tb_list_members(id_list_members, id_list, en_status, id_user) value
(null, 1, 1, 1);

insert into lixt.tb_list_members(id_list_members, id_list, en_status, id_user) values
(null, 4, 1, 2);

insert into lixt.tb_comment (id_comment, st_content, dt_created_at, id_product_of_list, id_user) values
(null, 'Verificar preço', NOW(), 1, 1),
(null, 'Preço local R$ 20,00', NOW(), 1, 2);



