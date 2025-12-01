INSERT INTO role (id, role_name) VALUES
    (1, 'ADMIN'),
    (2, 'PARTNER'),
    (3, 'SAGBEHANDLER');

INSERT INTO users (user_id, username, name, email, password, created_date) VALUES
        (1, 'admin', 'System Admin', 'admin@example.com',
        '$2b$10$EeZg8T58OWK0avOXdKTUd.6Y..m1RRZgzJdOLq1XJUhWs8cXmBtDm', '2024-01-01'),
        (2, 'partner01', 'Partner One', 'partner01@example.com',
        '$2b$10$sNAEc88AUC2MP4q7DeT2eOXsobi7enuU1KgEmO8KyIlC30dhPIm.K', '2024-01-05'),
        (3, 'worker01', 'Case Worker', 'worker01@example.com',
        '$2b$10$.6TqdMZDbXmx.Y/wy9ZYf.9IShXFr8u3vk7LoIJiINrApanl0Bavu', '2024-01-10');

INSERT INTO user_roles (role_id, user_id) VALUES
        (1, 1),
        (2, 2),
        (3, 3);

INSERT INTO client (id, name, idprefix) VALUES
        (1, 'Kromann Reumert', 1000),
        (2, 'AlphaSolution', 2000);

INSERT INTO casee (id, name, client_id, id_prefix) VALUES
        (1, 'Contract Review', 1, 1100),
        (2, 'System Rollout', 2, 2200);

INSERT INTO to_do (id, description, case_id, start_date, end_date, archived, priority, status) VALUES
          (1, 'Draft NDA', 1, '2024-02-01', '2024-02-05', FALSE, 'HIGH', 'OPEN'),
        (2, 'Prepare implementation plan', 2, '2024-02-10', NULL, FALSE, 'MEDIUM', 'IN_PROGRESS');

INSERT INTO logging (id, actor, action, details, timestamp) VALUES
        (1, 'admin', 'CREATE_CLIENT', 'Created client Kromann Reumert', '2024-02-01 09:00:00'),
        (2, 'partner01', 'CREATE_CASE', 'Created Contract Review for client 1', '2024-02-01 10:00:00'),
        (3, 'worker01', 'ASSIGN_TODO', 'Assigned ToDo 1 to worker01', '2024-02-02 11:00:00');

INSERT INTO client_assignee (user_id, client_id) VALUES
        (2, 1),
        (2, 2),
        (3, 1);

INSERT INTO case_assignee (user_id, case_id) VALUES
        (2, 1),
        (3, 2);

INSERT INTO todo_assignee (user_id, todo_id) VALUES
        (3, 1),
        (2, 2);
