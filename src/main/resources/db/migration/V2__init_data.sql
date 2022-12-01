INSERT INTO Product
VALUES ('861eb3ea-2b12-4770-a865-58ebcbc8c816', 'Book', 19.99);
INSERT INTO Product
VALUES ('9e679843-3a5e-4cca-b159-f4ec3846f8f1', 'Pencil', 0.99);
INSERT INTO Product
VALUES ('8a4b4a75-c562-45f3-97c0-7a99051cc78f', 'Xbox controller', 189.99);
INSERT INTO Product
VALUES ('8dfa6398-a1a4-4c95-9d9e-54ba3db7c550', 'Display 27"', 1900.88);
INSERT INTO Product
VALUES ('4a7d3a63-21c5-4372-a22c-a503a99b673e', 'HEALTH album', 100.00);

--
INSERT INTO PercentageDiscount
VALUES ('3feba4fa-7cc9-4813-afb5-418214bbce60', '10% all', 10, '2023-11-30 13:31:43.706', '2022-11-30 15:39:06.799302');

INSERT INTO AmountDiscount
VALUES ('417e78ea-f52f-44f0-830d-97c9d71c6dbc',
        '15% off for first 10 items, then additional discount for 50 more items', 15, 10, 50, '2023-11-30 14:31:30.2',
        '2022-11-30 15:39:06.799302');

--
INSERT INTO ProductDiscount
VALUES ('4a7d3a63-21c5-4372-a22c-a503a99b673e', '3feba4fa-7cc9-4813-afb5-418214bbce60');
INSERT INTO ProductDiscount
VALUES ('4a7d3a63-21c5-4372-a22c-a503a99b673e', '417e78ea-f52f-44f0-830d-97c9d71c6dbc');
INSERT INTO ProductDiscount
VALUES ('8dfa6398-a1a4-4c95-9d9e-54ba3db7c550', '3feba4fa-7cc9-4813-afb5-418214bbce60');
INSERT INTO ProductDiscount
VALUES ('9e679843-3a5e-4cca-b159-f4ec3846f8f1', '417e78ea-f52f-44f0-830d-97c9d71c6dbc');






