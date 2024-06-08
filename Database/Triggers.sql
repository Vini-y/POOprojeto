DROP TRIGGER if exists `person_insert_trigger`;
DROP TRIGGER if exists `supplier_insert_trigger`;
DROP TRIGGER if exists `sale_insert_trigger`;
DROP TRIGGER if exists `ItemTotalValue`;
DROP TRIGGER if exists `SaleTotalValue`;


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

CREATE TRIGGER ItemTotalValue BEFORE INSERT ON Sale_itens
FOR EACH ROW
BEGIN
    DECLARE productPrice FLOAT;
    SET productPrice = (SELECT price FROM Product WHERE id_product = NEW.product_id);
    SET NEW.total_value = NEW.quantity * productPrice;
END //

CREATE TRIGGER CalculateSaleTotalValue AFTER INSERT ON Sale_itens
FOR EACH ROW
BEGIN
    DECLARE saleTotal FLOAT;
    SET saleTotal = (SELECT SUM(total_value) FROM Sale_itens WHERE sale_id = NEW.sale_id);
    UPDATE Sale SET total_value = saleTotal WHERE id_sale = NEW.sale_id;
END //

DELIMITER ;


