DROP PROCEDURE IF EXISTS insert_admin;
DROP PROCEDURE IF EXISTS insert_client;
DROP PROCEDURE IF EXISTS insert_seller;
DROP PROCEDURE IF EXISTS insert_supplier;
DROP PROCEDURE IF EXISTS insert_product;
DROP PROCEDURE IF EXISTS insert_sale;
DROP PROCEDURE IF EXISTS insert_sale_item;
DROP PROCEDURE IF EXISTS update_admin;
DROP PROCEDURE IF EXISTS update_client;
DROP PROCEDURE IF EXISTS update_seller;
DROP PROCEDURE IF EXISTS update_supplier;
DROP PROCEDURE IF EXISTS update_product;
DROP PROCEDURE IF EXISTS update_sale;
DROP PROCEDURE IF EXISTS update_sale_item;
DROP PROCEDURE IF EXISTS delete_seller;
DROP PROCEDURE IF EXISTS delete_client;
DROP PROCEDURE IF EXISTS delete_supplier;
DROP PROCEDURE IF EXISTS delete_product;
DROP PROCEDURE IF EXISTS fechamento_do_dia;

DELIMITER //

CREATE PROCEDURE insert_admin(
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45)
)
BEGIN
    DECLARE user_id INT;

    -- Insert into User
    INSERT INTO User (name, email, senha)
    VALUES (p_name, p_email, p_senha);
    SET user_id = LAST_INSERT_ID();

    -- Insert into Admin
    INSERT INTO admin (id_admin)
    VALUES (user_id);
END //

CREATE PROCEDURE insert_client(
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45),
    IN p_last_name VARCHAR(45),
    IN p_cpf VARCHAR(45),
    IN p_birth_date DATE,
    IN p_phone_number VARCHAR(45),
    IN p_city VARCHAR(45),
    IN p_state VARCHAR(45),
    IN p_country VARCHAR(45),
    IN p_address VARCHAR(45),
    IN p_address_number VARCHAR(20)
)
BEGIN
    DECLARE user_id INT;
    DECLARE address_id INT;

    -- Insert into User
    INSERT INTO User (name, email, senha)
    VALUES (p_name, p_email, p_senha);
    SET user_id = LAST_INSERT_ID();

    -- Insert into Address
    INSERT INTO Address (city, state, country, address, address_number)
    VALUES (p_city, p_state, p_country, p_address, p_address_number);
    SET address_id = LAST_INSERT_ID();

    -- Insert into Person
    INSERT INTO Person (id_person, last_name, cpf, birth_date, phone_number, address_id)
    VALUES (user_id, p_last_name, p_cpf, p_birth_date, p_phone_number, address_id);

    -- Insert into Client
    INSERT INTO Client (id_client)
    VALUES (user_id);
END//

CREATE PROCEDURE insert_seller(
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45),
    IN p_last_name VARCHAR(45),
    IN p_cpf VARCHAR(45),
    IN p_birth_date DATE,
    IN p_phone_number VARCHAR(45),
    IN p_city VARCHAR(45),
    IN p_state VARCHAR(45),
    IN p_country VARCHAR(45),
    IN p_address VARCHAR(45),
    IN p_address_number VARCHAR(20)
)
BEGIN
    DECLARE user_id INT;
    DECLARE address_id INT;

    -- Insert into User
    INSERT INTO User (name, email, senha)
    VALUES (p_name, p_email, p_senha);
    SET user_id = LAST_INSERT_ID();

    -- Insert into Address
    INSERT INTO Address (city, state, country, address, address_number)
    VALUES (p_city, p_state, p_country, p_address, p_address_number);
    SET address_id = LAST_INSERT_ID();

    -- Insert into Person
    INSERT INTO Person (id_person, last_name, cpf, birth_date, phone_number, address_id)
    VALUES (user_id, p_last_name, p_cpf, p_birth_date, p_phone_number, address_id);

    -- Insert into Seller
    INSERT INTO Seller (id_seller)
    VALUES (user_id);
END//


CREATE PROCEDURE insert_supplier(
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45),
    IN p_cnpj VARCHAR(45),
    IN p_city VARCHAR(45),
    IN p_state VARCHAR(45),
    IN p_country VARCHAR(45),
    IN p_address VARCHAR(45),
    IN p_address_number VARCHAR(20)
)
BEGIN
    DECLARE user_id INT;
    DECLARE address_id INT;

    -- Insert into User
    INSERT INTO User (name, email, senha)
    VALUES (p_name, p_email, p_senha);
    SET user_id = LAST_INSERT_ID();

    -- Insert into Address
    INSERT INTO Address (city, state, country, address, address_number)
    VALUES (p_city, p_state, p_country, p_address, p_address_number);
    SET address_id = LAST_INSERT_ID();

    -- Insert into Supplier
    INSERT INTO Supplier (id_supplier, `name`, cnpj, address_id)
    VALUES (user_id, p_name, p_cnpj, address_id);
END //

-- Procedure para inserir produtos na tabela Product
CREATE PROCEDURE insert_product(
    IN p_description VARCHAR(45),
    IN p_quantity INT,
    IN p_price FLOAT,
    IN p_id_supplier INT
)
BEGIN
    INSERT INTO Product (description, quantity, price, id_supplier)
    VALUES (p_description, p_quantity, p_price, p_id_supplier);
END //

-- Procedure para inserir vendas na tabela Sale
CREATE PROCEDURE insert_sale (
    IN client_id INT,
    IN seller_id INT,
    IN sale_date TIMESTAMP,
    IN payment_id INT,
    IN total_value FLOAT,
    IN parcelas INT,
    OUT last_insert_id INT
)
BEGIN
    INSERT INTO Sale (id_client, id_seller, sale_date, payment, total_value, parcelas)
    VALUES (client_id, seller_id, sale_date, payment_id, total_value, parcelas);

    SET last_insert_id = LAST_INSERT_ID();
END //


-- Procedure para inserir itens de venda na tabela Sale_itens
CREATE PROCEDURE insert_sale_item(
    IN p_sale_id INT,
    IN p_product_id INT,
    IN p_quantity INT
)
BEGIN
    INSERT INTO Sale_itens (sale_id, product_id, quantity)
    VALUES (p_sale_id, p_product_id, p_quantity);
END //


CREATE PROCEDURE update_client(
    IN p_id_client INT,
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45),
    IN p_last_name VARCHAR(45),
    IN p_cpf VARCHAR(45),
    IN p_birth_date DATE,
    IN p_phone_number VARCHAR(45),
    IN p_city VARCHAR(45),
    IN p_state VARCHAR(45),
    IN p_country VARCHAR(45),
    IN p_address VARCHAR(45),
    IN p_address_number VARCHAR(20)
)
BEGIN
    DECLARE address_id INT;

    -- Update User
    UPDATE User
    SET name = p_name, email = p_email, senha = p_senha
    WHERE id_user = p_id_client;

    -- Get Address ID
    SELECT address_id INTO address_id
    FROM Person
    WHERE id_person = p_id_client;

    -- Update Address
    UPDATE Address
    SET city = p_city, state = p_state, country = p_country, address = p_address, address_number = p_address_number
    WHERE id_address = address_id;

    -- Update Person
    UPDATE Person
    SET last_name = p_last_name, cpf = p_cpf, birth_date = p_birth_date, phone_number = p_phone_number
    WHERE id_person = p_id_client;

END //

CREATE PROCEDURE update_seller(
    IN p_id_seller INT,
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45),
    IN p_last_name VARCHAR(45),
    IN p_cpf VARCHAR(45),
    IN p_birth_date DATE,
    IN p_phone_number VARCHAR(45),
    IN p_city VARCHAR(45),
    IN p_state VARCHAR(45),
    IN p_country VARCHAR(45),
    IN p_address VARCHAR(45),
    IN p_address_number VARCHAR(20)
)
BEGIN
    DECLARE address_id INT;

    -- Update User
    UPDATE User
    SET name = p_name, email = p_email, senha = p_senha
    WHERE id_user = p_id_seller;

    -- Get Address ID by joining User and Person tables
    SELECT p.address_id INTO address_id
    FROM Person p
    JOIN User u ON p.id_person = u.id_user
    WHERE u.id_user = p_id_seller;

    -- Update Address
    UPDATE Address
    SET city = p_city, state = p_state, country = p_country, address = p_address, address_number = p_address_number
    WHERE id_address = address_id;

    -- Update Person
    UPDATE Person
    SET last_name = p_last_name, cpf = p_cpf, birth_date = p_birth_date, phone_number = p_phone_number
    WHERE id_person = p_id_seller;
END //

CREATE PROCEDURE update_supplier(
    IN p_id_supplier INT,
    IN p_name VARCHAR(45),
    IN p_email VARCHAR(45),
    IN p_senha VARCHAR(45),
    IN p_cnpj VARCHAR(45),
    IN p_city VARCHAR(45),
    IN p_state VARCHAR(45),
    IN p_country VARCHAR(45),
    IN p_address VARCHAR(45),
    IN p_address_number VARCHAR(20)
)
BEGIN
    DECLARE address_id INT;

    -- Update User
    UPDATE User
    SET name = p_name, email = p_email, senha = p_senha
    WHERE id_user = p_id_supplier;

    -- Get Address ID by joining User and Supplier tables
    SELECT s.address_id INTO address_id
    FROM Supplier s
    JOIN User u ON s.id_supplier = u.id_user
    WHERE u.id_user = p_id_supplier;

    -- Update Address
    UPDATE Address
    SET city = p_city, state = p_state, country = p_country, address = p_address, address_number = p_address_number
    WHERE id_address = address_id;

    -- Update Supplier
    UPDATE Supplier
    SET name = p_name, cnpj = p_cnpj
    WHERE id_supplier = p_id_supplier;
END //

CREATE PROCEDURE update_product(
    IN p_id_product INT,
    IN p_description VARCHAR(45),
    IN p_quantity INT,
    IN p_price FLOAT,
    IN p_id_supplier INT
)
BEGIN
    UPDATE Product
    SET description = p_description, quantity = p_quantity, price = p_price, id_supplier = p_id_supplier
    WHERE id_product = p_id_product;
END //

CREATE PROCEDURE update_sale(
    IN p_id_sale INT,
    IN p_id_client INT,
    IN p_id_seller INT,
    IN p_sale_date DATETIME,
    IN p_payment INT,
    IN p_total_value FLOAT,
    IN p_parcelas INT
)
BEGIN
    UPDATE Sale
    SET id_client = p_id_client, id_seller = p_id_seller, sale_date = p_sale_date, payment = p_payment, total_value = p_total_value, parcelas = p_parcelas
    WHERE id_sale = p_id_sale;
END //

CREATE PROCEDURE update_sale_item(
    IN p_sale_item_id INT,
    IN p_sale_id INT,
    IN p_product_id INT,
    IN p_quantity INT
)
BEGIN
    UPDATE Sale_itens
    SET sale_id = p_sale_id, product_id = p_product_id, quantity = p_quantity
    WHERE id_sale_item = p_sale_item_id;
END //


CREATE PROCEDURE delete_seller(IN seller_id INT)
BEGIN
    -- Deletando da tabela Seller
    DELETE FROM Seller WHERE id_seller = seller_id;

    -- Deletando da tabela Person
    DELETE FROM Person WHERE id_person = seller_id;

    -- Deletando da tabela User
    DELETE FROM User WHERE id_user = seller_id;
END;


CREATE PROCEDURE delete_client(IN clientId INT)
BEGIN
    -- Deletando o cliente
    DELETE FROM `Client` WHERE id_client = clientId;
    
    -- Deletando o endereço associado ao cliente
    DELETE FROM Address WHERE id_address = clientId;

	-- Deletando a pessoa associada ao cliente
    DELETE FROM Person WHERE id_person = clientId;

    -- Deletando o usuário associado ao cliente
    DELETE FROM `User` WHERE id_user = clientId;

END //


CREATE PROCEDURE delete_supplier(IN supplierId INT)
BEGIN
    -- Deletando os produtos do fornecedor
    DELETE FROM Product WHERE id_supplier = supplierId;

    -- Deletando o fornecedor
    DELETE FROM Supplier WHERE id_supplier = supplierId;

    -- Deletando o usuário associado ao fornecedor
    DELETE FROM `User` WHERE id_user = supplierId;

    -- Deletando o endereço associado ao fornecedor
    DELETE FROM Address WHERE id_address =  supplierId;
END //



CREATE PROCEDURE delete_product(IN productId INT)
BEGIN
    DELETE FROM Product WHERE id_product = productId;
END //

DELIMITER //

CREATE PROCEDURE fechamento_do_dia()
BEGIN
    -- Variáveis para armazenar os resultados
    DECLARE total_sales_value_debit FLOAT DEFAULT 0;
    DECLARE total_sales_value_money FLOAT DEFAULT 0;

    -- Selecionar todas as vendas realizadas no dia atual
    SELECT
        s.id_sale,
        s.id_client,
        s.id_seller,
        s.sale_date,
        s.payment,
        s.total_value,
        s.parcelas
    FROM
        Sale s
    WHERE
        DATE(s.sale_date) = CURDATE();

    -- Calcular a soma dos valores totais das vendas do dia atual feitas em débito
    SELECT
        SUM(s.total_value) INTO total_sales_value_debit
    FROM
        Sale s
    WHERE
        DATE(s.sale_date) = CURDATE()
        AND s.payment = 2;

    -- Calcular a soma dos valores totais das vendas do dia atual feitas em dinheiro
    SELECT
        SUM(s.total_value) INTO total_sales_value_money
    FROM
        Sale s
    WHERE
        DATE(s.sale_date) = CURDATE()
        AND s.payment = 1;

    -- Exibir os valores totais das vendas do dia atual
    SELECT
        total_sales_value_debit AS total_sales_value_debit_of_the_day,
        total_sales_value_money AS total_sales_value_money_of_the_day;
END //

DELIMITER ;


DELIMITER ;



