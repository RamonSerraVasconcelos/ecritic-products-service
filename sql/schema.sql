CREATE TABLE brand
(
    id          BIGSERIAL NOT NULL,
    name        varchar(200),
    description varchar(2000),
    PRIMARY KEY (id)
);

CREATE TABLE category
(
    id          BIGSERIAL NOT NULL,
    name        VARCHAR(100),
    image       VARCHAR(400),
    description VARCHAR(2000),
    parent_id   INT,
    PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          BIGSERIAL NOT NULL,
    name        VARCHAR(500),
    category_id INT,
    description VARCHAR(1000),
    brand_id    INT,
    about       VARCHAR(200),
    PRIMARY KEY (id),
    CONSTRAINT fk_prod_brand FOREIGN KEY (brand_id) REFERENCES brand (id),
    CONSTRAINT fk_prod_prodcategory FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE product_item
(
    id         BIGSERIAL NOT NULL,
    product_id INT,
    price      NUMERIC(10, 2),
    PRIMARY KEY (id),
    CONSTRAINT fk_proditem_product FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE product_image
(
    id              BIGSERIAL NOT NULL,
    image_url       VARCHAR(400),
    product_id      INT,
    product_item_id INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_prodimage_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_prodimage_productitem FOREIGN KEY (product_item_id) REFERENCES product_item (id)
);

CREATE TABLE variation
(
    id          BIGSERIAL NOT NULL,
    category_id INT,
    name        VARCHAR(200),
    PRIMARY KEY (id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE variation_option
(
    id           BIGSERIAL NOT NULL,
    variation_id INT,
    value        VARCHAR(400),
    PRIMARY KEY (id),
    CONSTRAINT fk_variation_id FOREIGN KEY (variation_id) REFERENCES variation (id)
);

CREATE TABLE product_configuration
(
    product_item_id     INT,
    variation_option_id INT,
    PRIMARY KEY (product_item_id, variation_option_id),
    CONSTRAINT fk_product_item_id FOREIGN KEY (product_item_id) REFERENCES product_item (id),
    CONSTRAINT fk_variation_option_id FOREIGN KEY (variation_option_id) REFERENCES variation_option (id)
);