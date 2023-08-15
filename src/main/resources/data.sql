DELETE FROM USER_ROLE;
DELETE FROM USERS;
DELETE FROM DISH;
DELETE FROM RESTAURANT;

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES  ('Koza'),
        ('PizzaHut');

INSERT INTO DISH (name, price, rest_id, date_dish)
VALUES ('Risotto', 56812, 1, current_timestamp),
       ('Appetizers', 50000, 1, current_timestamp),
       ('Cold platter', 32000, 1, current_timestamp),
       ('Beverages', 20000, 1, current_timestamp),
       ('Soups', 56000, 2, current_timestamp),
       ('Meat', 50000, 2, current_timestamp),
       ('Pizza', 80000, 2, current_timestamp),
       ('Beverages', 20000, 2, current_timestamp);