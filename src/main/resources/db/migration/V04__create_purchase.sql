-- -----------------------------------------------------
-- Table `tb_purchaseLocal`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_purchase_local` (
  `id_purchase_local` INT NOT NULL AUTO_INCREMENT,
  `st_name` VARCHAR(45) NOT NULL,
  `point_location` POINT NOT NULL,

  PRIMARY KEY (`id_purchase_local`)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tb_purchase`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_purchase` (
  `id_purchase` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_list` INT NULL,
  `id_purchase_local` INT NULL,
  `nr_purchase_price` DECIMAL NOT NULL,
  `dt_purchase_date` DATE NOT NULL,

  PRIMARY KEY (`id_purchase`),

  INDEX `fk_purchase_user` (`id_user` ASC) VISIBLE,
  INDEX `fk_purchase_list` (`id_list` ASC) VISIBLE,
  INDEX `fk_purchase_groceryLocal` (`id_purchase_local` ASC) VISIBLE,

  CONSTRAINT `fk_compra_usuario1` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT `fk_compra_lista1` FOREIGN KEY (`id_list`) REFERENCES `tb_list` (`id_list`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT `fk_compra_localDaCompra1` FOREIGN KEY (`id_purchase_local`) REFERENCES `tb_purchase_local` (`id_purchase_local`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tb_itemOfPurchase`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tb_item_of_purchase` (
  `id_item_of_purchase` INT NOT NULL AUTO_INCREMENT,
  `id_purchase` INT NOT NULL,
  `id_product` INT NULL,
  `st_name` VARCHAR(45) NOT NULL,
  `nr_price` DECIMAL NOT NULL,
  `nr_amount` INT NOT NULL,
  `nr_measure_value` DECIMAL NOT NULL,
  `en_measure_type` INT NOT NULL,

  INDEX `fk_itemOfPurchase_purchase` (`id_purchase` ASC) VISIBLE,
  INDEX `fk_itemOfPurchase_product` (`id_product` ASC) VISIBLE,
  INDEX `idx_itemOfPurchase_name` (`st_name` ASC) VISIBLE,

  PRIMARY KEY (`id_item_of_purchase`),

  CONSTRAINT `fk_itensDaCompra_compra1` FOREIGN KEY (`id_purchase`) REFERENCES `tb_purchase` (`id_purchase`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,

  CONSTRAINT `fk_itensDaCompra_produto1` FOREIGN KEY (`id_product`) REFERENCES `tb_product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;