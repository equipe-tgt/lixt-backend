-- -----------------------------------------------------
-- Table `tb_list`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_list` (
  `id_list` INT NOT NULL AUTO_INCREMENT,
  `st_name_list` VARCHAR(45) NOT NULL,
  `id_user` INT NOT NULL,
  `st_description` VARCHAR(200) NULL,

  PRIMARY KEY (`id_list`),

  CONSTRAINT `fk_lista_usuario1` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB;

CREATE INDEX fk_list_user ON tb_list (id_user);


-- -----------------------------------------------------
-- Table `tb_productOfList`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_product_of_list` (
  `id_product_of_list` INT NOT NULL AUTO_INCREMENT,
  `id_product` INT NOT NULL,
  `id_list` INT NOT NULL,
  `id_assigned_user` INT NULL,
  `id_user_who_marked` INT NULL,
  `st_name` VARCHAR(45) NOT NULL,
  `bl_is_marked` TINYINT NOT NULL,
  `nr_price` DECIMAL(12,2) NULL,
  `nr_amount` INT NULL,
  `en_measure_type` INT NULL,
  `nr_measure_value` DECIMAL NULL,

  PRIMARY KEY (`id_product_of_list`),

  CONSTRAINT `fk_lista`
    FOREIGN KEY (`id_list`)
    REFERENCES `tb_list` (`id_list`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT `fk_usuarioAtribuido` FOREIGN KEY (`id_assigned_user`) REFERENCES `tb_user` (`id_user`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,
    
  CONSTRAINT `fk_produtoDaLista_produto1` FOREIGN KEY (`id_product`) REFERENCES `tb_product` (`id_product`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,
    
  CONSTRAINT `fk_usuarioMarcou` FOREIGN KEY (`id_user_who_marked`) REFERENCES `tb_user` (`id_user`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;

CREATE INDEX fk_list_user ON tb_product_of_list (id_list);
CREATE INDEX fk_productOfList_userAssined ON tb_product_of_list (id_assigned_user);
CREATE INDEX fk_productOfList_product ON tb_product_of_list (id_product);
CREATE INDEX fk_productOfList_userMarked ON tb_product_of_list (id_user_who_marked);
CREATE INDEX idx_name ON tb_product_of_list (st_name);


-- -----------------------------------------------------
-- Table `tb_listMembers`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_list_members` (
  `id_list_members` INT NOT NULL AUTO_INCREMENT,
  `en_status` INT NOT NULL,
  `id_user` INT NOT NULL,
  `id_list` INT NOT NULL,

  PRIMARY KEY (`id_list_members`),

  CONSTRAINT `fk_membrosLista_usuario1` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,
      
  CONSTRAINT `fk_membrosLista_lista1` FOREIGN KEY (`id_list`) REFERENCES `tb_list` (`id_list`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;

CREATE INDEX fk_listMembers_user ON tb_list_members (id_user);
CREATE INDEX fk_listMembers_list ON tb_list_members (id_list);


-- -----------------------------------------------------
-- Table `tb_comment`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_comment` (
  `id_comment` INT NOT NULL AUTO_INCREMENT,
  `id_product_of_list` INT NOT NULL,
  `id_user` INT NOT NULL,
  `st_content` VARCHAR(200) NOT NULL,
  `dt_created_at` DATE NOT NULL,

  PRIMARY KEY (`id_comment`),

  CONSTRAINT `fk_comentario_produtoDaLista1` FOREIGN KEY (`id_product_of_list`) REFERENCES `tb_product_of_list` (`id_product_of_list`)
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,

  CONSTRAINT `fk_comentario_usuario1` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;

CREATE INDEX fk_comment_productOfList ON tb_comment (id_product_of_list);
CREATE INDEX fk_comment_user ON tb_comment (id_user);


