-- ========================================
-- Очистка старых таблиц
-- ========================================
DROP TABLE IF EXISTS event_stats;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS participation_requests;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- ========================================
-- Таблица пользователей
-- ========================================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- ========================================
-- Таблица категорий
-- ========================================
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- ========================================
-- Таблица событий
-- ========================================
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    annotation TEXT NOT NULL,
    description TEXT NOT NULL,
    category_id INT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    event_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    status VARCHAR(50) NOT NULL,
    is_paid BOOLEAN NOT NULL DEFAULT false,
    participant_limit INT,
    request_moderation BOOLEAN NOT NULL DEFAULT true,
    views INT NOT NULL DEFAULT 0,
    confirmed_requests INT NOT NULL DEFAULT 0,
    lat DECIMAL(9,6),
    lon DECIMAL(9,6)
);

-- ========================================
-- Таблица запросов на участие
-- ========================================
CREATE TABLE participation_requests (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(event_id, user_id)
);

-- ========================================
-- Таблица комментариев к событиям
-- ========================================
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ========================================
-- Таблица лайков / просмотров событий
-- ========================================
CREATE TABLE event_stats (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE SET NULL,
    type VARCHAR(50) NOT NULL, -- 'LIKE', 'VIEW'
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ========================================
-- Индексы для ускорения поиска
-- ========================================
CREATE INDEX idx_events_category_id ON events(category_id);
CREATE INDEX idx_events_event_date ON events(event_date);
CREATE INDEX idx_events_status ON events(status);

-- ========================================
-- Данные пользователей
-- ========================================
INSERT INTO users (id, name, email) VALUES
(1, 'Crystal Gleason', 'Lillie.Jacobson96@gmail.com'),
(2, 'Jo Shields', 'Pamela.Marks38@gmail.com'),
(3, 'Brandi Fadel', 'Eduardo.Farrell87@gmail.com'),
(4, 'Hector Turner', 'Kristy_Wisoky22@hotmail.com'),
(5, 'Antonio Stehr', 'Misael.Schmidt43@gmail.com');

-- ========================================
-- Данные категорий
-- ========================================
INSERT INTO categories (id, name) VALUES
(1, 'Concerts'),
(2, 'Sports'),
(3, 'Theatre');

-- ========================================
-- Данные событий
-- ========================================
INSERT INTO events (id, title, annotation, description, category_id, user_id, event_date, created_at, status, is_paid, participant_limit, request_moderation, views, confirmed_requests, lat, lon)
VALUES
(1, 'Rock Concert', 'Awesome rock concert coming soon', 'Full description of rock concert event goes here, more than 20 chars', 1, 1, NOW() + interval '1 day', NOW(), 'PENDING', false, 100, true, 0, 0, 55.75, 37.61),
(2, 'Football Match', 'Exciting football match', 'Detailed description of football match, enough length', 2, 2, NOW() + interval '2 days', NOW(), 'PENDING', false, 50, true, 0, 0, 55.76, 37.62),
(3, 'Theatre Play', 'Amazing theatre play', 'Full description of theatre play event, over 20 characters', 3, 3, NOW() + interval '3 days', NOW(), 'PENDING', true, 30, false, 0, 0, 55.77, 37.63);

-- ========================================
-- Данные запросов на участие
-- ========================================
INSERT INTO participation_requests (event_id, user_id, status)
VALUES
(1, 2, 'PENDING'),
(1, 3, 'CONFIRMED'),
(2, 1, 'PENDING'),
(3, 5, 'REJECTED');

-- ========================================
-- Данные комментариев
-- ========================================
INSERT INTO comments (event_id, user_id, text)
VALUES
(1, 2, 'Can’t wait for this concert!'),
(1, 3, 'I have already bought my tickets.'),
(2, 1, 'Go team!'),
(3, 5, 'Looks like a great play!');

-- ========================================
-- Данные статистики событий
-- ========================================
INSERT INTO event_stats (event_id, user_id, type)
VALUES
(1, 2, 'LIKE'),
(1, 3, 'LIKE'),
(1, NULL, 'VIEW'),
(2, 1, 'VIEW'),
(3, 5, 'LIKE');
