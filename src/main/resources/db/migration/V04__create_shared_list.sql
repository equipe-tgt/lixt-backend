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

