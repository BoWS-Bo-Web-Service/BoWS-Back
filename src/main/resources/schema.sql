DROP TABLE IF EXISTS PROJECT;

CREATE TABLE `PROJECT` (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `PROJECT_NAME` VARCHAR(256) NOT NULL,
    `DOMAIN` VARCHAR(256) NOT NULL,
    `BACKEND_IMAGE_NAME` VARCHAR(256) NOT NULL,
    `FRONTEND_IMAGE_NAME` VARCHAR(256) NOT NULL,
    `DB_STORAGE_SIZE` SMALLINT NOT NULL,
    `DB_PASSWORD` VARCHAR(256) NOT NULL,
    `DB_ENDPOINT` VARCHAR(256) NOT NULL,
    `DB_USER_NAME` VARCHAR(256) NOT NULL,
    `DB_USER_PASSWORD` VARCHAR(256) NOT NULL,
    `DB_SCHEMA` TEXT NOT NULL,
    `CREATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `UPDATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);