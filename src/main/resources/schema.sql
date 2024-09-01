DROP TABLE IF EXISTS MEMBER_ROLE;
DROP TABLE IF EXISTS ROLE_AUTHORITY;
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS AUTHORITY;
DROP TABLE IF EXISTS REFRESH_TOKEN;
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
    `CREATED_BY` VARCHAR(256) NOT NULL,
    `CREATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `UPDATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `IS_DELETED` BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE AUTHORITY (
    `ID` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `NAME` VARCHAR(255) NOT NULL,
    `CREATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `UPDATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ROLE (
    `ID` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `NAME` VARCHAR(255) NOT NULL,
    `CREATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `UPDATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ROLE_AUTHORITY (
    `ROLE_ID` BIGINT,
    `AUTHORITY_ID` BIGINT,
    PRIMARY KEY (ROLE_ID, AUTHORITY_ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE(id),
    FOREIGN KEY (AUTHORITY_ID) REFERENCES AUTHORITY(id)
);

CREATE TABLE MEMBER (
    `ID` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `USER_ID` VARCHAR(255) NOT NULL,
    `PASSWORD` VARCHAR(255) NOT NULL,
    `NAME` VARCHAR(255) NOT NULL,
    `CREATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `UPDATED_AT` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE MEMBER_ROLE (
    `MEMBER_ID` BIGINT,
    `ROLE_ID` BIGINT,
    PRIMARY KEY (MEMBER_ID, ROLE_ID),
    FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(id),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE(id)
);

CREATE TABLE REFRESH_TOKEN (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    REFRESH_TOKEN VARCHAR(255) NOT NULL,
    USER_ID VARCHAR(255) NOT NULL,
    EXPIRATION_TIME DATETIME NOT NULL
);
