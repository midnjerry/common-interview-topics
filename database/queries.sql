-- SELECT ALL FROM PERSON
SELECT * FROM person;

-- SELECT ALL FROM PRODUCT
SELECT * FROM product;

-- SELECT ALL FROM SHOPPING_CART_ITEM
SELECT * FROM shopping_cart_item;

-- SELECT ALL SHOPPING_CART_ITEM FOR Person with id of 1
SELECT * FROM shopping_cart_item WHERE person_id = 1;

-- SELECT ALL SHOPPING_CART_ITEM combined with PRODUCT For Person with id of 1
SELECT * FROM shopping_cart_item
JOIN product ON shopping_cart_item.product_id = product.product_id
WHERE person_id = 1;

-- LEFT JOIN EXAMPLE - includes Luke Skywalker
SELECT * FROM person
LEFT JOIN shopping_cart_item ON shopping_cart_item.person_id = person.person_id;


-- RIGHT JOIN EXAMPLE - ALL PRODUCTS included - even products not in shopping cart
SELECT * FROM shopping_cart_item
RIGHT JOIN product ON product.product_id = shopping_cart_item.product_id;

-- JOIN TABLES follow top down order of operations
-- PERSON LEFT JOIN SHOPPING_CART_ITEM  <---  ALL PERSON LISTED ALONG WITH SHOPPING_CART_ITEM
-- THE RESULTS ABOVE ARE TREATED AS ONE TABLE
-- PERSON_SHOPPING_CART_ITEM FULL OUTER JOIN PRODUCT  <---  Takes previous results and combines with ALL Product items
SELECT * FROM person
LEFT JOIN shopping_cart_item ON shopping_cart_item.person_id = person.person_id
FULL OUTER JOIN product ON shopping_cart_item.product_id = product.product_id;

SELECT * FROM shopping_cart_item
RIGHT JOIN person ON shopping_cart_item.person_id = person.person_id
FULL OUTER JOIN product ON shopping_cart_item.product_id = product.product_id;

SELECT * FROM shopping_cart_item
FULL OUTER JOIN person ON shopping_cart_item.person_id = person.person_id
FULL OUTER JOIN product ON shopping_cart_item.product_id = product.product_id
WHERE shopping_cart_item.person_id IS NULL;

-- SELF JOIN EXAMPLES

-- THIS IS DUMB EXAMPLE AND SHOULD NOT BE USED IN PRODUCTION
SELECT * FROM person AS a
JOIN person AS b ON a.person_id = b.person_id;

-- WHEN A PERSON REFERENCES ANOTHER PERSON (mentor, parent, sibling, etc.) - YOU CAN USE THESE
SELECT * FROM person AS a
JOIN person AS b ON a.mentor_id = b.person_id;

SELECT * FROM person AS a
LEFT JOIN person AS b ON a.mentor_id = b.person_id;

SELECT * FROM person AS a
RIGHT JOIN person AS b ON a.mentor_id = b.person_id;