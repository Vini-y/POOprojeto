DROP TRIGGER if exists `person_insert_trigger`;
DROP TRIGGER if exists `supplier_insert_trigger`;
DROP TRIGGER if exists `sale_insert_trigger`;
DROP TRIGGER if exists `ItemTotalValue`;
DROP TRIGGER if exists `SaleTotalValue`;
DROP TRIGGER if exists `update_product_quantity`;


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

DELIMITER //

CREATE TRIGGER SaleTotalValue
AFTER INSERT ON Sale_itens
FOR EACH ROW
BEGIN
    DECLARE numParcelas INT;
    -- Obtém o número de parcelas da venda
    SELECT parcelas INTO numParcelas FROM Sale WHERE id_sale = NEW.sale_id;

    -- Se o número de parcelas for maior que 5, aplica um acréscimo de 5% ao total
    IF numParcelas > 5 THEN
        UPDATE Sale SET total_value = total_value * 1.05 WHERE id_sale = NEW.sale_id;
  END IF;
END //


CREATE TRIGGER update_product_quantity
AFTER INSERT ON Sale_itens
FOR EACH ROW
BEGIN
    UPDATE Product
    SET quantity = quantity - NEW.quantity
    WHERE id_product = NEW.product_id;
END //
DELIMITER ;


DELIMITER ;