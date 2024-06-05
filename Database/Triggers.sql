DROP TRIGGER if exists `person_insert_trigger`;
DROP TRIGGER if exists `supplier_insert_trigger`;
DROP TRIGGER if exists `sale_insert_trigger`;


-- Trigger para inserir a data de criação automaticamente na tabela `Person`
DELIMITER //
CREATE TRIGGER `person_insert_trigger` BEFORE INSERT ON `Person` FOR EACH ROW
BEGIN
    SET NEW.registration_date = NOW();
END;
//

-- Trigger para inserir a data de criação automaticamente na tabela `Supplier`
CREATE TRIGGER `supplier_insert_trigger` BEFORE INSERT ON `Supplier` FOR EACH ROW
BEGIN
    SET NEW.registration_date = NOW();
END;
//

-- Trigger para inserir a data de criação automaticamente na tabela `Sale`
CREATE TRIGGER `sale_insert_trigger` BEFORE INSERT ON `Sale` FOR EACH ROW
BEGIN
    SET NEW.sale_date = NOW();
END;
//
DELIMITER ;
