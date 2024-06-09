DROP SCHEMA IF EXISTS `Thorffin_wears`;
CREATE SCHEMA IF NOT EXISTS `Thorffin_wears` DEFAULT CHARACTER SET utf8 ;
USE `Thorffin_wears`;

CREATE TABLE IF NOT EXISTS `User` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `Address` (
  `id_address` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `address_number` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_address`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `Person` (
  `id_person` INT NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `cpf` VARCHAR(45) NOT NULL,
  `birth_date` DATE NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `registration_date` DATE NOT NULL,
  `address_id` INT NOT NULL,
  PRIMARY KEY (`id_person`),
  FOREIGN KEY (`id_person`) REFERENCES `User` (`id_user`),
  FOREIGN KEY (`address_id`) REFERENCES `Address` (`id_address`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `Client` (
  `id_client` INT NOT NULL,
  PRIMARY KEY (`id_client`),
  FOREIGN KEY (`id_client`) REFERENCES `Person` (`id_person`)
) ENGINE=InnoDB;



CREATE TABLE IF NOT EXISTS `Seller` (
  `id_seller` INT NOT NULL,
  PRIMARY KEY (`id_seller`),
  FOREIGN KEY (`id_seller`) REFERENCES `Person` (`id_person`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `Supplier` (
  `id_supplier` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `cnpj` VARCHAR(45) NOT NULL,
  `registration_date` DATE NOT NULL,
  `address_id` INT NOT NULL,
  PRIMARY KEY (`id_supplier`),
  FOREIGN KEY (`id_supplier`) REFERENCES `User` (`id_user`),
  FOREIGN KEY (`address_id`) REFERENCES `Address` (`id_address`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `admin` (
  `id_admin` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_admin`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Product` (
  `id_product` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  `quantity` INT NOT NULL,
  `price` FLOAT NOT NULL,
  `id_supplier` INT NOT NULL,
  PRIMARY KEY (`id_product`),
  INDEX `id_supplier_idx` (`id_supplier` ASC) VISIBLE,
  CONSTRAINT `id_supplier_product`
    FOREIGN KEY (`id_supplier`)
    REFERENCES `Supplier` (`id_supplier`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Pay` (
  `id_Pay` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_Pay`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Sale` (
  `id_sale` INT NOT NULL AUTO_INCREMENT,
  `id_client` INT NOT NULL,
  `id_seller` INT NOT NULL,
  `sale_date` DATETIME NULL,
  `payment` INT,
  `total_value` FLOAT default 0.00,
  `parcelas` INT NULL,
  PRIMARY KEY (`id_sale`),
  INDEX `id_client_idx` (`id_client` ASC) VISIBLE,
  INDEX `id_seller_idx` (`id_seller` ASC) VISIBLE,
  INDEX `payment_idx` (`payment` ASC) VISIBLE,
  CONSTRAINT `id_client`
    FOREIGN KEY (`id_client`)
    REFERENCES `Client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_seller`
    FOREIGN KEY (`id_seller`)
    REFERENCES `Seller` (`id_seller`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `payment`
    FOREIGN KEY (`payment`)
    REFERENCES `Pay` (`id_Pay`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Sale_itens` (
  `id_sale_itens` INT NOT NULL AUTO_INCREMENT,
  `sale_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `total_value` FLOAT default 0.00,
  PRIMARY KEY (`id_sale_itens`),
  INDEX `id_sale_idx` (`sale_id` ASC) VISIBLE,
  INDEX `id_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `id_sale`
    FOREIGN KEY (`sale_id`)
    REFERENCES `Sale` (`id_sale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `Product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

