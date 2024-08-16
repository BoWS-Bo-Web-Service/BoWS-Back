INSERT INTO Project
(PROJECT_NAME, DOMAIN, BACKEND_IMAGE_NAME, FRONTEND_IMAGE_NAME, DB_STORAGE_SIZE, DB_SCHEMA, DB_PASSWORD, DB_ENDPOINT, DB_USER_NAME, DB_USER_PASSWORD, CREATED_BY)
VALUES
    ('example', 'alpha.example.com', 'bogyumkim/backend', 'bogyumkim/frontend', 1, "CREATE TABLE 'MEMBER'" ,'password123', 'db.alpha.example.com', 'db_user_alpha', 'user_password_alpha', 'admin');