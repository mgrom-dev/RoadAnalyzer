-- prototype schema for user data
CREATE SCHEMA IF NOT EXISTS ${proto_user_db};

CREATE TABLE IF NOT EXISTS
    ${proto_user_db}.expense_type (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        description VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS
    ${proto_user_db}.part_and_service (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        description VARCHAR(255) NOT NULL,
        type BIGINT
    );

CREATE TABLE IF NOT EXISTS
    ${proto_user_db}.part_group (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        description VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS
    ${proto_user_db}.parts_stock (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        spending_id BIGINT,
        date DATE,
        part_description VARCHAR(255),
        description VARCHAR(255),
        OEM VARCHAR(255),
        count DOUBLE,
        price DOUBLE,
        amount DOUBLE,
        status VARCHAR(255),
        part_group_id BIGINT
    );

CREATE TABLE IF NOT EXISTS
    ${proto_user_db}.spending (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        date DATE NOT NULL,
        part_and_service_id BIGINT,
        description VARCHAR(255),
        count DOUBLE,
        amount DOUBLE
    );