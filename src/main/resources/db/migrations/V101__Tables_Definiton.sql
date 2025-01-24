CREATE SCHEMA IF NOT EXISTS task_manage_sc;

CREATE TABLE IF NOT EXISTS task_manage_sc.users
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(355) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS task_manage_sc.tasks
(
    id SERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL REFERENCES task_manage_sc.users,
    executor_id BIGINT NOT NULL REFERENCES task_manage_sc.users,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(55) NOT NULL,
    priority VARCHAR(55) NOT NULL,
    comments text,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE  TABLE IF NOT EXISTS task_manage_sc.comments
(
    id SERIAL PRIMARY KEY,
    user_id bigint not null,
    task_id bigint not null,
    content text,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT  fk_user FOREIGN KEY (user_id) REFERENCES task_manage_sc.users ON DELETE CASCADE,
    CONSTRAINT fl_task FOREIGN KEY (task_id) REFERENCES task_manage_sc.tasks ON DELETE CASCADE
);