DELETE FROM user_roles;
DELETE FROM users;
DELETE from meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(user_id, date_time, description, calories) VALUES
(100000, '2020-10-16 09:00:01', 'Завтрак', 500),
(100000, '2020-10-16 15:00:02', 'Обед', 1000),
(100000, '2020-10-16 20:00:03', 'Ужин', 500),
(100000, '2020-10-17 10:00:04', 'Завтрак', 1000),
(100000, '2020-10-17 16:00:05', 'Обед', 500),
(100000, '2020-10-17 21:00:06', 'Ужин', 510),
(100001,'2020-10-17 11:00:07', 'Завтрак админа', 510),
(100001,'2020-10-17 21:00:08', 'Ужин админа', 1500);

