-- Insert test data using insert_client procedure
CALL insert_client(
    'John Doe',
    'johndoe@example.com',
    'password123',
    'Doe',
    '123.456.789-00',
    '1980-01-01',
    '555-1234',
    'CityName',
    'StateName',
    'CountryName',
    'StreetName',
    '123'
);

-- Insert another test data for client
CALL insert_client(
    'Jane Smith',
    'janesmith@example.com',
    'password456',
    'Smith',
    '987.654.321-00',
    '1990-02-02',
    '555-5678',
    'AnotherCity',
    'AnotherState',
    'AnotherCountry',
    'AnotherStreet',
    '456'
);

-- Insert test data using insert_seller procedure
CALL insert_seller(
    'Alice Johnson',
    'alicejohnson@example.com',
    'password789',
    'Johnson',
    '111.222.333-44',
    '1975-03-03',
    '555-9101',
    'SellerCity',
    'SellerState',
    'SellerCountry',
    'SellerStreet',
    '789'
);

-- Insert another test data for seller
CALL insert_seller(
    'Bob Williams',
    'bobwilliams@example.com',
    'password012',
    'Williams',
    '555.666.777-88',
    '1985-04-04',
    '555-1112',
    'AnotherSellerCity',
    'AnotherSellerState',
    'AnotherSellerCountry',
    'AnotherSellerStreet',
    '321'
);

-- Insert test data using insert_supplier procedure
CALL insert_supplier(
    'Supplier One',
    'supplierone@example.com',
    'password345',
    '00.000.000/0001-00',
    'SupplierCity',
    'SupplierState',
    'SupplierCountry',
    'SupplierStreet',
    '123'
);

-- Insert another test data for supplier
CALL insert_supplier(
    'Supplier Two',
    'suppliertwo@example.com',
    'password678',
    '11.111.111/1111-11',
    'AnotherSupplierCity',
    'AnotherSupplierState',
    'AnotherSupplierCountry',
    'AnotherSupplierStreet',
    '456'
);

-- Insert test data for Pay
INSERT INTO `thorffin_wears`.`pay` (`id_Pay`, `tipo`) VALUES (1, 'Dinheiro');
INSERT INTO `thorffin_wears`.`pay` (`id_Pay`, `tipo`) VALUES (2, 'Debito');
INSERT INTO `thorffin_wears`.`pay` (`id_Pay`, `tipo`) VALUES (3, 'Credito');

-- Insere dados de teste na tabela Product
-- Assuming existing supplier IDs
CALL insert_product(
    'Product One Description',
    100,
    19.99,
    5  -- Existing supplier ID
);

CALL insert_product(
    'Product Two Description',
    200,
    29.99,
    6  -- Existing supplier ID
);
