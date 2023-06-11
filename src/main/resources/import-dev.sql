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
VALUES ('8ce58c70-147f-4ebc-b6b9-ed274d5f3d3b', 'Browar Koniczynka Lublin', 'Lublin', 'IC1', '20-001', 1000),
       ('28f2fb8b-4e7a-41f2-b27a-1f79797a0b76', 'Browar Koniczynka Zwierzyniec', 'Zwierzyniec', 'IC2', '22-220', 2000),
       ('a1c8d2bd-aa6d-460c-96c0-67c4e5f98c9e', 'Browar Koniczynka Poznań', 'Poznań', 'IC3', '61-001', 1500),
       ('7ec87df0-b353-409e-a980-03e8f9070c24', 'Browar Koniczynka Kraków', 'Kraków', 'IC4', '30-001', 1800),
       ('f21fcbd0-cc5a-417a-ba04-2b9d0a464de1', 'Browar Koniczynka Wrocław', 'Wrocław', 'IC5', '50-001', 1200),
       ('70dd3b65-8ec5-4d12-980b-9dc8c7f0133d', 'Browar Koniczynka Gdańsk', 'Gdańsk', 'IC6', '80-001', 2500),
       ('08fc84f8-c36c-4c54-aafb-1c9a39c1719a', 'Browar Koniczynka Warszawa', 'Warszawa', 'IC7', '00-001', 2200),
       ('4f0c2ad1-83ba-4e37-8d20-79a1bfc8a6d3', 'Browar Koniczynka Łódź', 'Łódź', 'IC8', '90-001', 1900),
       ('152ca5d9-1ea3-4b6d-bc18-f0e613b18876', 'Browar Koniczynka Katowice', 'Katowice', 'IC9', '40-001', 1700),
       ('a49141d2-34d4-4359-8cb2-d91efbe24c1e', 'Browar Koniczynka Białystok', 'Białystok', 'IC10', '15-001', 1400),
       ('b2f8fd7f-8d2b-46d6-8f2f-54f6be7a7b1f', 'Browar Koniczynka Rzeszów', 'Rzeszów', 'IC11', '35-001', 1600),
       ('12fd5a7d-2e2a-4f5a-8d5c-1a80b9d3fd25', 'Browar Koniczynka Gdynia', 'Gdynia', 'IC12', '81-001', 2100),
       ('5432a8e1-7b3f-4d9a-b631-fa7ee11a57a9', 'Browar Koniczynka Szczecin', 'Szczecin', 'IC13', '70-001', 1800),
       ('8fba73d6-c2a9-45b7-9424-5e6f8be01a9b', 'Browar Koniczynka Bydgoszcz', 'Bydgoszcz', 'IC14', '85-001', 2300),
       ('45c7a7d8-8b64-49d1-b9f7-6814c7e10b9d', 'Browar Koniczynka Toruń', 'Toruń', 'IC15', '87-100', 2000),
       ('9d7df32d-0c3a-4a86-a47e-1d94fe3c71c1', 'Browar Koniczynka Olsztyn', 'Olsztyn', 'IC16', '10-001', 1700),
       ('8d07bc1e-f87a-4a82-a6fe-fa54f07139e9', 'Browar Koniczynka Zamość', 'Zamość', 'IC17', '22-400', 2200),
       ('b7f49b79-534e-46d7-bb7f-275fdea02cc4', 'Browar Koniczynka Zakopane', 'Zakopane', 'IC18', '34-500', 1600),
       ('07d3b3ca-7925-4f99-9d41-8143a7e0ee25', 'Browar Koniczynka Sopot', 'Sopot', 'IC19', '81-701', 1800),
       ('2f785e1a-37e8-43c5-9e1a-69ea9747d8e5', 'Browar Koniczynka Częstochowa', 'Częstochowa', 'IC20', '42-200', 2000);


INSERT INTO beers (id, beer_name, style_id, ibu, bottle_capacity, alcohol_percentage, color, description, price)
VALUES ('d0aef983-5e0f-42e9-9441-98018cd4edac', 'Koniczynka Classic Lager', 1, 18, 500, 5.0, 'Yellow', 'Crisp and refreshing lager', 2.99),
       ('7a29d9bc-df95-4cf3-9ac5-57ca803f95b5', 'Koniczynka Dark Ale', 2, 30, 500, 5.5, 'Brown', 'Robust and malty ale', 3.29),
       ('96e1419f-2f31-470f-885f-1a14f3922dd9', 'Koniczynka Rich Stout', 3, 40, 500, 6.0, 'Black', 'Rich and creamy stout', 3.59),
       ('b3e9f1e6-3b3d-4e6e-9459-85018c64361a', 'Koniczynka Hoppy IPA', 4, 60, 500, 6.5, 'Amber', 'Hop-forward and bitter IPA', 3.79),
       ('a2b8f4ef-080f-435a-9a7e-525d53c47ee6', 'Koniczynka Belgian Witbier', 5, 15, 500, 5.0, 'Pale', 'Light and citrusy witbier', 2.99),
       ('c8a1757f-67f1-46c3-a666-8151735f5b1c', 'Koniczynka Crisp Pilsner', 6, 20, 500, 4.5, 'Straw', 'Crisp and clear pilsner', 2.89),
       ('6a22a6f6-2ba2-4e17-913f-54508f9b755e', 'Koniczynka Weizen', 7, 10, 500, 5.0, 'Golden', 'Fruity and yeasty weizen', 3.19),
       ('3bc90d1c-b463-4928-a5e6-257c24c4fd5c', 'Koniczynka Smooth Porter', 8, 35, 500, 5.5, 'Dark', 'Smooth and chocolatey porter', 3.49),
       ('caed1aef-d992-4e53-a169-6fa8e1a2fa1e', 'Koniczynka Lambic', 9, 10, 375, 6.0, 'Red', 'Fruity and sour lambic', 4.29),
       ('e7b3dcad-3d52-4c7e-bb3a-4de8d5c67124', 'Koniczynka Gose', 10, 10, 375, 4.5, 'Light', 'Salty and coriander gose', 3.99);


-- Define and use CTEs for the first INSERT INTO operation
-- WITH selected_breweries AS (SELECT id
--                             FROM breweries LIMIT 10
--     )
-- INSERT
-- INTO brewery_images (brewery_id, image)
-- SELECT id, E '\\x'
-- FROM selected_breweries;

-- Define and use CTEs for the second INSERT INTO operation
WITH selected_breweries AS (SELECT id FROM breweries),
     selected_beers AS (SELECT id FROM beers),
     joined_breweries_beers AS (SELECT breweries.id AS brewery_id, beers.id AS beer_id
                                FROM selected_breweries breweries
                                         CROSS JOIN selected_beers beers)

INSERT
INTO brewery_beer (brewery_id, beer_id)
SELECT brewery_id, beer_id
FROM joined_breweries_beers;


INSERT INTO brewery_managers (hire_date, id, email, login, name, password, phone_number)
    VALUES ('2023-06-06 21:28:26.073766 +00:00', '91024ce0-3834-4c39-a3a4-d3c1587fd09a', 'steve', 'steve', 'steve',
        '$2a$10$mGI715P32ItXVr97P3cXlOytutUSkdvLJLRgHesB5pPOZRjsArgqW', '123'),
        ('2023-06-09 21:28:26.073766 +00:00', 'c8a1757f-67f1-46c3-a666-8151735f5b1c', 'rumcajs', 'rumcajs', 'rumcajs',
        '$2a$10$mGI715P32ItXVr97P3cXlOytutUSkdvLJLRgHesB5pPOZRjsArgqW', '123');


WITH selected_breweries AS (SELECT id FROM breweries),
     selected_managers AS (SELECT id FROM brewery_managers),
     joined_brewery_managers AS (SELECT breweries.id AS brewery_id, managers.id AS manager_id
                                FROM selected_breweries breweries
                                         CROSS JOIN selected_managers managers)

INSERT INTO manager_breweries (brewery_id, manager_id)
SELECT brewery_id, manager_id
FROM joined_brewery_managers;