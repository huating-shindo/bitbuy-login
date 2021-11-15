INSERT INTO role  VALUES
  (1, 'USER'),
  (2, 'ADMIN');

INSERT INTO user (id, username, password ) VALUES
  (1, 'user', '$2a$10$JWGGpmgfqOQm9SjfxS8mNOgtsU8TVNJ3qbH7CfM3LLj2uTKZPC/Um'),
  (2, 'admin', '$2a$10$LvJBeDy6llOErUgJGhYnqeQh5aCrz0trLiaHOit8BWMmnnsAub6li');

INSERT INTO user_role  VALUES
  (1, 1),
  (2, 1),
  (2, 2);