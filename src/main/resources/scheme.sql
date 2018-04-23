CREATE TABLE IF NOT EXISTS users (
  id serial PRIMARY KEY NOT NULL,
  username TEXT NOT NULL,
  email TEXT NOT NULL,
  password CHARACTER(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS projects (
  id serial PRIMARY KEY NOT NULL,
  project_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS projects_to_users (
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL,
  project_id INTEGER REFERENCES projects(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks (
  id serial PRIMARY KEY NOT NULL,
  title TEXT NOT NULL,
  task_text TEXT,
  created TIMESTAMPTZ DEFAULT now(),
  status INTEGER NOT NULL DEFAULT 0,
  project_id INTEGER REFERENCES projects(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS comments (
  id serial PRIMARY KEY NOT NULL,
  created TIMESTAMPTZ DEFAULT now(),
  comment_text TEXT NOT NULL,
  task_id INTEGER REFERENCES tasks(id) ON DELETE CASCADE NOT NULL,
  author_id INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL,
  author_login TEXT
);

