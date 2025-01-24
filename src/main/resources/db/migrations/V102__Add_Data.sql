

-- Добавляем данные в  users
INSERT INTO task_manage_sc.users (email, password,role)
VALUES
    ('admin@example.com', '$2b$12$jhg1nWn3ACVURy1K0Thj.eOy2uL8YdhvBqzwxgCuVA/RjAHG8V9FC','ROLE_ADMIN'),
    ('manager@example.com', '$2b$12$jhg1nWn3ACVURy1K0Thj.eOy2uL8YdhvBqzwxgCuVA/RjAHG8V9FC','ROLE_USER'),
    ('developer@example.com', 'hashedpassword3','ROLE_USER'),
    ('tester@example.com', 'hashedpassword4','ROLE_USER'),
    ('guest@example.com', 'hashedpassword5','ROLE_USER');


INSERT INTO task_manage_sc.tasks (author_id, executor_id, title, description, status, priority, comments)
VALUES
    (1, 2, 'Setup Project', 'Set up the initial project structure', 'PENDING', 'HIGH', 'No comments'),
    (2, 3, 'Develop Feature', 'Develop the login feature', 'IN_PROGRESS', 'HIGH', 'Need assistance with API'),
    (3, 4, 'Write Tests', 'Write unit tests for login feature', 'PENDING', 'MEDIUM', 'Tests are failing'),
    (4, 5, 'Code Review', 'Review code for new feature', 'COMPLETED', 'LOW', 'Well written'),
    (5, 1, 'Deploy to Prod', 'Deploy the new feature to production', 'PENDING', 'LOW', 'Blocked by approval');



-- Добавляем данные в таблицу comments
INSERT INTO task_manage_sc.comments (user_id, task_id, content)
VALUES
    (2, 1, 'Project setup is completed.'),
    (3, 2, 'Login feature is partially implemented.'),
    (4, 3, 'Tests are written but need debugging.'),
    (5, 4, 'Code review is complete, great job.'),
    (1, 5, 'Waiting for approval for deployment.');
