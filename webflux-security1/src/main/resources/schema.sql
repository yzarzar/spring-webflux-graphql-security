CREATE TABLE customers
(
    customer_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(225)
);

CREATE TABLE users
(
    user_id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name     VARCHAR(225) NOT NULL,
    password VARCHAR(50)  NOT NULL,
    role     ENUM('USER', 'ADMIN') NOT NULL
);
