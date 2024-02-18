BEGIN TRANSACTION;

DROP TABLE IF EXISTS shopping_cart_item;
DROP TABLE IF EXISTS  person;
DROP TABLE IF EXISTS product;

CREATE TABLE person (
	person_id serial,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	CONSTRAINT PK_person_id PRIMARY KEY (person_id)
);

CREATE TABLE product (
	product_id serial,
	name varchar(50) NOT NULL,
	price float,
	CONSTRAINT PK_product_id PRIMARY KEY (product_id)
);

CREATE TABLE shopping_cart_item (
	shopping_cart_item_id serial,
	person_id int NOT NULL,
	product_id int NOT NULL,
	quantity int,
	CONSTRAINT PK_shopping_cart_item_id PRIMARY KEY (shopping_cart_item_id),
	CONSTRAINT FK_person_id FOREIGN KEY (person_id) REFERENCES person (person_id),
	CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES product (product_id)
);

INSERT INTO person (first_name, last_name) VALUES ('Peter', 'Parker');
INSERT INTO person (first_name, last_name) VALUES ('Clark', 'Kent');
INSERT INTO person (first_name, last_name) VALUES ('Bruce', 'Wayne');
INSERT INTO person (first_name, last_name) VALUES ('Luke', 'Skywalker');

INSERT INTO product(name, price) VALUES ('Honey Bunches of Oats', 1.49);
INSERT INTO product(name, price) VALUES ('Fresh Ground Beef', 2.99);
INSERT INTO product(name, price) VALUES ('Ice Cream', 2.99);
INSERT INTO product(name, price) VALUES ('Energy Drink', 1.49);
INSERT INTO product(name, price) VALUES ('Potato Chip Multipack', 8.99);

INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (1, 1, 3);
INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (1, 2, 1);
INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (1, 4, 6);
INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (1, 5, 2);

INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (2, 3, 1);
INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (2, 4, 2);

INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (3, 1, 1);
INSERT INTO shopping_cart_item(person_id, product_id, quantity) VALUES (3, 5, 1);

COMMIT TRANSACTION;