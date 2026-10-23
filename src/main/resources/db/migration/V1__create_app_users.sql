
CREATE TABLE app_users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT pk_app_users PRIMARY KEY (id),
    CONSTRAINT uk_app_users_email UNIQUE (email),
    CONSTRAINT chk_app_users_role
        CHECK (role IN ('ADMIN', 'CANDIDATE', 'EVALUATOR'))
);
