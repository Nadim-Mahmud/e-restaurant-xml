CREATE TABLE restaurant.user
(
    id            INT                NOT NULL PRIMARY KEY,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    joining_date  DATE,
    date_of_birth DATE,
    email         VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(50)        NOT NULL,
    type          VARCHAR(50),
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    DATE,
    updated_at    DATE
);

CREATE TABLE restaurant.restaurant_table
(
    id            INT NOT NULL PRIMARY KEY,
    name          VARCHAR(50),
    user_id       INT,
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    DATE,
    updated_at    DATE,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE restaurant.category
(
    id            INT NOT NULL PRIMARY KEY,
    name          VARCHAR(50),
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    DATE,
    updated_at    DATE
);

CREATE TABLE restaurant.item
(
    id            INT NOT NULL PRIMARY KEY,
    name          VARCHAR(50),
    description   VARCHAR(3000),
    price         DOUBLE,
    availability  VARCHAR(50),
    category_id   INT,
    access_status VARCHAR(50) DEFAULT 'ACTIVE',
    version       INT,
    created_at    DATE,
    updated_at    DATE,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE restaurant.chef_item
(
    user_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (user_id, item_id)
);

CREATE TABLE restaurant.order_table
(
    id                  INT NOT NULL PRIMARY KEY,
    status              VARCHAR(50),
    restaurant_table_id INT,
    access_status       VARCHAR(50) DEFAULT 'ACTIVE',
    version             INT,
    created_at          DATE,
    updated_at          DATE,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table (id)
);

CREATE TABLE restaurant.order_line_item
(
    id               INT NOT NULL PRIMARY KEY,
    quantity         INT,
    accepted_at      DATE,
    est_cooking_time INT,
    order_status           VARCHAR(50),
    item_id          INT,
    order_id         INT,
    access_status    VARCHAR(50) DEFAULT 'ACTIVE',
    version          INT,
    created_at       DATE,
    updated_at       DATE,
    FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES order_table (id) ON DELETE CASCADE
);


INSERT INTO user(id, first_name, last_name, joining_date, date_of_birth, email, password, type, access_status, version, created_at, updated_at)
values(0, "Nadim", "Mahmud", "12-12-12", "12-12-12", "ndm@gmail.com", "hb0Ir6gFtIFtTyBtyqSvVQ==", "ADMIN", "ACTIVE", 0, "12-12-12", "12-12-12");