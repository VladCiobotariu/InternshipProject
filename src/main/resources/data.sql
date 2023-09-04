-- Categories
INSERT INTO Category (id, name, image_name) VALUES (1, 'Fruits', '/images/fruits.svg');
INSERT INTO Category (id, name, image_name) VALUES (2, 'Vegetables', '/images/vegetables.svg');
INSERT INTO Category (id, name, image_name) VALUES (3, 'Dairy', '/images/dairy.svg');
INSERT INTO Category (id, name, image_name) VALUES (4, 'Nuts', '/images/nuts.svg');
INSERT INTO Category (id, name, image_name) VALUES (5, 'Honey', '/images/honey.svg');
INSERT INTO Category (id, name, image_name) VALUES (6, 'Sweets', '/images/sweets.svg');
INSERT INTO Category (id, name, image_name) VALUES (7, 'Oils', '/images/oil.svg');
INSERT INTO Category (id, name, image_name) VALUES (8, 'Tea', '/images/tea.svg');

-- Accounts
INSERT INTO User_Account (id, email, first_name, image_name, last_name, password_hash, telephone) VALUES (1, 'erika.rusznak@gmail.com', 'Erika', '/images/erika.png', 'Rusznak', null, '0747871208');
INSERT INTO User_Account (id, email, first_name, image_name, last_name, password_hash, telephone) VALUES (2, 'giulia.lucaciu@gmail.com', 'Giulia', '/images/giulia.jpg', 'Lucaciu', null, '0744578415');
INSERT INTO User_Account (id, email, first_name, image_name, last_name, password_hash, telephone) VALUES (3, 'alex.dulfu@gmail.com', 'Alex', '/images/alex.png', 'Dulfu', null, '0756425478');

-- Sellers
INSERT INTO Seller (id, alias, address_line_1, address_line_2, city, country, state, zip_code, cui, company_name, company_type, date_of_registration, numeric_code_by_state, serial_number, seller_type, account_id) VALUES (1, 'Mega Fresh SRL', 'Str Vasile Lucaciu', 'Bloc 6 Ap 8', 'Baia Mare', 'Romania', 'Maramures', '305413', null, null, null, null, null, null, 'LOCAL_FARMER', 1);
INSERT INTO Seller (id, alias, address_line_1, address_line_2, city, country, state, zip_code, cui, company_name, company_type, date_of_registration, numeric_code_by_state, serial_number, seller_type, account_id) VALUES (2, 'Local Plaza', 'Str Gutinului', 'Nr 11 Ap 26', 'Baia Sprie', 'Romania', 'Maramures', '310105', null, null, null, null, null, null, 'LOCAL_FARMER', 2);
INSERT INTO Seller (id, alias, address_line_1, address_line_2, city, country, state, zip_code, cui, company_name, company_type, date_of_registration, numeric_code_by_state, serial_number, seller_type, account_id) VALUES (3, 'Company Plaza', 'Aleea Minis', 'Sc B Nr 2 Ap 9', 'Timisoara', 'Romania', 'Timis', '320120', 'RO35215201', 'Company Plaza SA', 'F', NOW(), 41, 34, 'COMPANY', 3);

-- Products
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (1, 'This is an apple! It is a fruit!', '/images/apple.jpeg', 'Apple', 2.5, 1, 1);
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (2, 'This is a pear! It is a fruit!', '/images/pear.png', 'Pear', 4, 1, 1);
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (3, 'This is a kiwi! It is a fruit!', 'images/kiwi.jpg', 'Kiwi', 5, 1, 1);
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (4, 'This is a banana! It is a fruit!', '/images/banana.jpg', 'Banana', 4, 1, 1);
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (5, 'This is a mango! It is a fruit!', '/images/mango.png', 'Mango', 6, 1, 1);
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (6, 'This is a peach! It is a fruit!', '/images/peach.png', 'Peach', 3.5, 1, 1);
INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES (7, 'This is an orange! It is a fruit!', '/images/orange.png', 'Orange', 3, 1, 1);
-- INSERT INTO Product (id, description, image_name, name, price, category_id, seller_id) VALUES ()
