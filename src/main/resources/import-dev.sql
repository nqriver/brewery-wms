CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

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


INSERT INTO breweries (id, name, city, internal_code, postal_code, surface_area)
VALUES (uuid_generate_v4(), 'Brewery 1', 'City 1', 'IC1', '12345', 1000),
       (uuid_generate_v4(), 'Brewery 2', 'City 2', 'IC2', '23456', 2000),
       (uuid_generate_v4(), 'Brewery 3', 'City 3', 'IC3', '34567', 1500),
       (uuid_generate_v4(), 'Brewery 4', 'City 4', 'IC4', '45678', 1800),
       (uuid_generate_v4(), 'Brewery 5', 'City 5', 'IC5', '56789', 1200),
       (uuid_generate_v4(), 'Brewery 6', 'City 6', 'IC6', '67890', 2500),
       (uuid_generate_v4(), 'Brewery 7', 'City 7', 'IC7', '78901', 2200),
       (uuid_generate_v4(), 'Brewery 8', 'City 8', 'IC8', '89012', 1900),
       (uuid_generate_v4(), 'Brewery 9', 'City 9', 'IC9', '90123', 1700),
       (uuid_generate_v4(), 'Brewery 10', 'City 10', 'IC10', '01234', 1400);

INSERT INTO beers (id, beer_name, style_id, ibu, bottle_capacity, alcohol_percentage, color, description, price)
VALUES (uuid_generate_v4(), 'Beer 5', 5, 5.2, 330, 4.8, 'Light', 'Description 5', 2.59),
       (uuid_generate_v4(), 'Beer 6', 6, 6.5, 500, 6.8, 'Brown', 'Description 6', 3.79),
       (uuid_generate_v4(), 'Beer 7', 7, 4.3, 330, 3.7, 'Blonde', 'Description 7', 2.49),
       (uuid_generate_v4(), 'Beer 8', 8, 7.8, 500, 8.2, 'Black', 'Description 8', 4.29),
       (uuid_generate_v4(), 'Beer 9', 9, 5.9, 330, 5.5, 'Amber', 'Description 9', 2.89),
       (uuid_generate_v4(), 'Beer 10', 10, 4.5, 500, 4.0, 'Golden', 'Description 10', 3.19);

-- Define and use CTEs for the first INSERT INTO operation
WITH selected_breweries AS (
    SELECT id
    FROM breweries
             LIMIT 10
    )
INSERT INTO brewery_images (brewery_id, image)
SELECT id, E'\\x' FROM selected_breweries;

-- Define and use CTEs for the second INSERT INTO operation
WITH selected_breweries AS (
    SELECT id
    FROM breweries
             LIMIT 10
    ), selected_beers AS (
SELECT id
FROM beers
    LIMIT 10
    ), joined_breweries_beers AS (
SELECT breweries.id AS brewery_id, beers.id AS beer_id
FROM selected_breweries breweries, selected_beers beers
    LIMIT 10
    )
INSERT INTO brewery_beer (brewery_id, beer_id)
SELECT brewery_id, beer_id FROM joined_breweries_beers;
