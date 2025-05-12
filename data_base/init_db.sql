-- Создание базы данных (если ещё не существует)
CREATE DATABASE android_items_db;

-- Подключение к базе данных
\c android_items_db

-- Создание таблицы items
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Вставка тестовых данных
INSERT INTO items (name, description) VALUES
('Смартфон Samsung Galaxy S21', 'Флагманский смартфон с AMOLED-экраном и тройной камерой'),
('Ноутбук ASUS ZenBook 14', 'Ультрабук с процессором Intel Core i7 и 16 ГБ оперативной памяти'),
('Наушники Sony WH-1000XM4', 'Беспроводные наушники с активным шумоподавлением'),
('Умные часы Apple Watch Series 7', 'Смарт-часы с функцией измерения ЭКГ и SpO2'),
('Фотоаппарат Canon EOS R6', 'Беззеркальная камера с полнокадровым сенсором'),
('Планшет iPad Pro 12.9', 'Планшет с дисплеем Liquid Retina XDR и чипом M1'),
('Электронная книга PocketBook 740', 'Читалка с 7.8-дюймовым экраном E Ink Carta'),
('Игровая консоль PlayStation 5', 'Новейшая игровая консоль от Sony'),
('Внешний SSD Samsung T7', 'Переносной SSD-накопитель объёмом 1 ТБ'),
('Монитор Dell UltraSharp 32', '4K монитор с цветовым охватом 99% sRGB');

-- Создание пользователя для приложения (опционально)
CREATE USER android_app_user WITH PASSWORD 'secure_password_123';
GRANT ALL PRIVILEGES ON DATABASE android_items_db TO android_app_user;
GRANT ALL PRIVILEGES ON TABLE items TO android_app_user;
GRANT USAGE, SELECT ON SEQUENCE items_id_seq TO android_app_user;