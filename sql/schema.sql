CREATE TABLE brands
(
    id          BIGSERIAL NOT NULL,
    name        varchar(200),
    description varchar(2000),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id          BIGSERIAL NOT NULL,
    name        VARCHAR(100),
    image       VARCHAR(400),
    description VARCHAR(2000),
    parent_id   INT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE products
(
    id          BIGSERIAL NOT NULL,
    name        VARCHAR(200),
    category_id INT,
    brand_id    INT,
    description VARCHAR(1000),
    active      BOOLEAN,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_prod_prodcategory FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_prod_brand FOREIGN KEY (brand_id) REFERENCES brands (id)
);

CREATE TABLE product_items
(
    id         BIGSERIAL    NOT NULL,
    product_id INT,
    sku        VARCHAR(100) NOT NULL UNIQUE,
    price      NUMERIC(10, 2),
    quantity   INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_proditem_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TYPE attributes AS ENUM (
    'COLOR',
    'SIZE',
    'WEIGHT'
);

CREATE TABLE attribute_options
(
    id        BIGSERIAL NOT NULL,
    attribute attribute,
    value     VARCHAR(400),
    PRIMARY KEY (id)
);

CREATE TABLE product_item_attributes
(
    product_item_id     INT,
    attribute_option_id INT,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    PRIMARY KEY (product_item_id, attribute_option_id),
    CONSTRAINT fk_product_item_id FOREIGN KEY (product_item_id) REFERENCES product_items (id),
    CONSTRAINT fk_attribute_option_id FOREIGN KEY (attribute_option_id) REFERENCES attribute_options (id)
);

CREATE TABLE product_images
(
    id              VARCHAR(50) NOT NULL,
    product_item_id INT,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_prodimage_productitem FOREIGN KEY (product_item_id) REFERENCES product_items (id)
);