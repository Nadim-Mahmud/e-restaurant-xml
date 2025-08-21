-- Ensure schema exists
CREATE SCHEMA IF NOT EXISTS restaurant;

-- User table
CREATE TABLE restaurant."user"
(
    id            INT                NOT NULL PRIMARY KEY,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    joining_date  TIMESTAMP,
    date_of_birth TIMESTAMP,
    email         VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(50)        NOT NULL,
    type          VARCHAR(50),
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

-- Restaurant table
CREATE TABLE restaurant.restaurant_table
(
    id            INT NOT NULL PRIMARY KEY,
    name          VARCHAR(50),
    user_id       INT,
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES restaurant."user" (id)
);

-- Category table
CREATE TABLE restaurant.category
(
    id            INT NOT NULL PRIMARY KEY,
    name          VARCHAR(50),
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

-- Item table
CREATE TABLE restaurant.item
(
    id            INT NOT NULL PRIMARY KEY,
    name          VARCHAR(50),
    description   VARCHAR(3000),
    price         DOUBLE PRECISION,
    availability  VARCHAR(50),
    category_id   INT,
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES restaurant.category (id)
);

-- Chef Item (many-to-many)
CREATE TABLE restaurant.chef_item
(
    user_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (user_id, item_id),
    FOREIGN KEY (user_id) REFERENCES restaurant."user"(id),
    FOREIGN KEY (item_id) REFERENCES restaurant.item(id)
);

-- Order table
CREATE TABLE restaurant.order_table
(
    id                  INT NOT NULL PRIMARY KEY,
    status              VARCHAR(50),
    restaurant_table_id INT,
    access_status       VARCHAR(50) DEFAULT 'ACTIVE',
    version             INT,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant.restaurant_table (id)
);

-- Order line items
CREATE TABLE restaurant.order_line_item
(
    id               INT NOT NULL PRIMARY KEY,
    quantity         INT,
    accepted_at      TIMESTAMP,
    est_cooking_time INT,
    order_status     VARCHAR(50),
    item_id          INT,
    order_id         INT,
    access_status    VARCHAR(50) DEFAULT 'ACTIVE',
    version          INT,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES restaurant.item (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES restaurant.order_table (id) ON DELETE CASCADE
);


-- Sequence objects
-- User table sequence
CREATE SEQUENCE restaurant.user_seq
    START WITH 1
    INCREMENT BY 1;

-- Restaurant table sequence
CREATE SEQUENCE restaurant.restaurant_table_seq
    START WITH 1
    INCREMENT BY 1;

-- Category table sequence
CREATE SEQUENCE restaurant.category_seq
    START WITH 1
    INCREMENT BY 1;

-- Item table sequence
CREATE SEQUENCE restaurant.item_seq
    START WITH 1
    INCREMENT BY 1;

-- Order table sequence
CREATE SEQUENCE restaurant.order_table_seq
    START WITH 1
    INCREMENT BY 1;

-- Order line item table sequence
CREATE SEQUENCE restaurant.order_line_seq
    START WITH 1
    INCREMENT BY 1;


-- Initial user insert ndm123
INSERT INTO restaurant."user"(id, first_name, last_name, joining_date, date_of_birth, email, password, type, access_status, version, created_at, updated_at)
VALUES
    (0, 'Nadim', 'Mahmud', TO_TIMESTAMP('12-12-12', 'YY-MM-DD'), TO_TIMESTAMP('12-12-12', 'YY-MM-DD'),
     'ndm@gmail.com', '00jkrDK3aMAz3S145KKAsQ==', 'ADMIN', 'ACTIVE', 0,
     TO_TIMESTAMP('12-12-12', 'YY-MM-DD'), TO_TIMESTAMP('12-12-12', 'YY-MM-DD'));