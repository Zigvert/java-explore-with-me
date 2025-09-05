-- Пользователи
INSERT INTO users (id, name, email) VALUES
(1, 'Crystal Gleason', 'Lillie.Jacobson96@gmail.com'),
(2, 'Jo Shields', 'Pamela.Marks38@gmail.com'),
(3, 'Brandi Fadel', 'Eduardo.Farrell87@gmail.com'),
(4, 'Hector Turner', 'Kristy_Wisoky22@hotmail.com'),
(5, 'Antonio Stehr', 'Misael.Schmidt43@gmail.com');

-- Категории
INSERT INTO categories (id, name) VALUES
(1, 'Concerts'),
(2, 'Sports'),
(3, 'Theatre');

-- События
INSERT INTO events (id, title, annotation, description, category_id, user_id, event_date, created_at, status, is_paid, participant_limit, request_moderation, views, confirmed_requests, lat, lon)
VALUES
(1, 'Rock Concert', 'Awesome rock concert coming soon', 'Full description of rock concert event goes here, more than 20 chars', 1, 1, NOW() + interval '1 day', NOW(), 'PENDING', false, 100, true, 0, 0, 55.75, 37.61),
(2, 'Football Match', 'Exciting football match', 'Detailed description of football match, enough length', 2, 2, NOW() + interval '2 days', NOW(), 'PENDING', false, 50, true, 0, 0, 55.76, 37.62),
(3, 'Theatre Play', 'Amazing theatre play', 'Full description of theatre play event, over 20 characters', 3, 3, NOW() + interval '3 days', NOW(), 'PENDING', true, 30, false, 0, 0, 55.77, 37.63);
