CREATE TABLE tb_user (
	id_user int NOT NULL auto_increment,
	st_name varchar (45),
	st_username varchar (45),
	st_email varchar (120),
	st_password varchar (120),
	bl_activated boolean,

	CONSTRAINT pk_user PRIMARY KEY (id_user),
	CONSTRAINT uq_username UNIQUE(st_username),
	CONSTRAINT uq_email UNIQUE(st_email)
);
