-- Создаем последовательность
CREATE SEQUENCE USERS_SEQ START WITH 1 INCREMENT BY 50;
--CREATE SEQUENCE PART_AND_SERVICE_SEQ START WITH 1 INCREMENT BY 50;

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    database_identifier VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    verification_code VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT false
);

-- Обновляем существующие записи для использования последовательности
ALTER TABLE users ALTER COLUMN id SET DEFAULT NEXTVAL('USERS_SEQ');

INSERT INTO users (id, username, password, role, email, database_identifier, created_at, is_active)
VALUES (DEFAULT, 'admin', '$2a$12$Auq8SqSbRsoVN9Y0.RxX5ueY1Oi1bYKzcR4CiptY2ls8kPFHKcJKu', 'admin', 'admin@example.com', 'admin_db', CURRENT_TIMESTAMP, TRUE);