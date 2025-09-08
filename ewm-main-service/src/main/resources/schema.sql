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
    published_at TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    is_paid BOOLEAN NOT NULL DEFAULT false,
    participant_limit INT DEFAULT 0,
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
-- Таблица комментариев
-- ========================================
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ========================================
-- Таблица статистики событий
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
