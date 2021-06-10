CREATE TABLE tb_user (
	id_user bigint NOT NULL,
	st_name varchar (50),
	st_username varchar (50),
	st_email varchar (250),
	st_password varchar (500),
	bl_activated boolean default false,

	CONSTRAINT pk_user PRIMARY KEY (id_user),
	CONSTRAINT uq_username UNIQUE(st_username),
	CONSTRAINT uq_email UNIQUE(st_email)
);
