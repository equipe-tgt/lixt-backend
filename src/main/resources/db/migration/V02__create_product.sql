-- -----------------------------------------------------
-- Table `tb_category`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_category` (
  `id_category` INT NOT NULL AUTO_INCREMENT,
  `st_name` VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (`id_category`)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tb_product`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_product` (
  `id_product` INT NOT NULL AUTO_INCREMENT,
  `st_name` VARCHAR(45) NOT NULL,
  `nr_price` DECIMAL(12,2) NULL,
  `id_user` INT NULL,
  `id_category` INT NOT NULL,
  `nr_measure_value` DECIMAL NOT NULL,
  `st_barcode` VARCHAR(20) NULL,
  `en_measure_type` INT NOT NULL,
  
  PRIMARY KEY (`id_product`),
  
  INDEX `fk_product_user` (`id_user` ASC) VISIBLE,
  INDEX `fk_product_category` (`id_category` ASC) VISIBLE,
  INDEX `idx_product_name` (`st_name` ASC) VISIBLE,
  INDEX `idx_product_barcode` (`st_barcode` ASC) VISIBLE,
  
  CONSTRAINT `fk_produto_usuario1` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,
    
  CONSTRAINT `fk_produto_categoria1` FOREIGN KEY (`id_category`) REFERENCES `tb_category` (`id_category`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;
