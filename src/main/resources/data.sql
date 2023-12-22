DELETE FROM VOTE;
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
VALUES ('Risotto', 56812, 1, '2023-09-28'),
       ('Appetizers', 50000, 1, current_date),
       ('Cold platter', 32000, 1, current_date),
       ('Beverages', 20000, 1, current_date),
       ('Hamburger', 15100, 1, '2023-09-28'),
       ('Deep-fried potatoes', 13200, 1, '2023-09-28'),
       ('Coca-Cola', 5000, 1, '2023-09-27'),
       ('Soups', 56000, 2, current_date),
       ('Meat', 50000, 2, current_date),
       ('Pizza', 80000, 2, current_date),
       ('Beverages', 20000, 2, current_date),
       ('Pel`meni', 10100, 2, '2023-07-26');

INSERT INTO VOTE (date_time, rest_id, user_id)
VALUES ('2023-09-28', 2, 1),
       ('2023-07-26', 1, 1);
/*((current_timestamp - INTERVAL '2' hour), 1, 1),*/