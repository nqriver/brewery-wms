INSERT INTO beer_styles (name)
VALUES ('Lager'),
       ('Ale'),
       ('Stout'),
       ('IPA'),
       ('Witbier'),
       ('Pilsner'),
       ('Weizen'),
       ('Porter'),
       ('Lambic'),
       ('Gose'),
       ('Saison'),
       ('Kölsch'),
       ('Dunkel'),
       ('Bock'),
       ('Hefeweizen');


INSERT INTO breweries (name, city, internal_code, postal_code, surface_area)
VALUES ('Brewery 1', 'City 1', 'IC1', '12345', 1000),
       ('Brewery 2', 'City 2', 'IC2', '23456', 2000),
       ('Brewery 3', 'City 3', 'IC3', '34567', 1500),
       ('Brewery 4', 'City 4', 'IC4', '45678', 1800),
       ('Brewery 5', 'City 5', 'IC5', '56789', 1200),
       ('Brewery 6', 'City 6', 'IC6', '67890', 2500),
       ('Brewery 7', 'City 7', 'IC7', '78901', 2200),
       ('Brewery 8', 'City 8', 'IC8', '89012', 1900),
       ('Brewery 9', 'City 9', 'IC9', '90123', 1700),
       ('Brewery 10', 'City 10', 'IC10', '01234', 1400);

INSERT INTO beers (beer_name, style_id, ibu, bottle_capacity, alcohol_percentage, color, description, price)
VALUES ('Beer 5', 5, 5.2, 330, 4.8, 'Light', 'Description 5', 2.59),
       ('Beer 6', 6, 6.5, 500, 6.8, 'Brown', 'Description 6', 3.79),
       ('Beer 7', 7, 4.3, 330, 3.7, 'Blonde', 'Description 7', 2.49),
       ('Beer 8', 8, 7.8, 500, 8.2, 'Black', 'Description 8', 4.29),
       ('Beer 9', 9, 5.9, 330, 5.5, 'Amber', 'Description 9', 2.89),
       ('Beer 10', 10, 4.5, 500, 4.0, 'Golden', 'Description 10', 3.19);

INSERT INTO brewery_images (brewery_id, image)
VALUES (1, E '\\x'),
       (2, E '\\x'),
       (3, E '\\x'),
       (4, E '\\x'),
       (5, E '\\x'),
       (6, E '\\x'),
       (7, E '\\x'),
       (8, E '\\x'),
       (9, E '\\x'),
       (10, E '\\x');


INSERT INTO brewery_beer (brewery_id, beer_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);


