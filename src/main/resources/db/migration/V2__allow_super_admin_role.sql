
ALTER TABLE app_users
    DROP CHECK chk_app_users_role;

ALTER TABLE app_users
    ADD CONSTRAINT chk_app_users_role
    CHECK (
        role IN (
            'SUPER_ADMIN',
            'ADMIN',
            'CANDIDATE',
            'EVALUATOR'
        )
    );
