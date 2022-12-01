CREATE TABLE Product
(
    id          VARCHAR(255) NOT NULL,
    productName VARCHAR(255),
    price       DECIMAL(10,2),
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE PercentageDiscount
(
    id             VARCHAR(255) NOT NULL,
    discountName   VARCHAR(255),
    percentage     INT,
    expirationDate TIMESTAMP,
    createdDate    TIMESTAMP,
    CONSTRAINT pk_percentagediscount PRIMARY KEY (id)
);

CREATE TABLE AmountDiscount
(
    id                              VARCHAR(255) NOT NULL,
    discountName                    VARCHAR(255),
    discountPercentage              INT,
    minimumAmount                   INT,
    productAmountDiscountMultiplier INT,
    expirationDate                  TIMESTAMP,
    createdDate                     TIMESTAMP,
    CONSTRAINT pk_amountdiscount PRIMARY KEY (id)
);

CREATE TABLE ProductDiscount
(
    product_id         VARCHAR(255) NOT NULL,
    discount_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_product_discounts PRIMARY KEY (product_id, discount_id)
);
