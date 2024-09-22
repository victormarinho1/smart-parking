CREATE TYPE role_type AS ENUM ('CLIENT', 'ADMIN');
 
CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	role role_type NOT NULL DEFAULT 'CLIENT',
	enabled boolean NOT NULL DEFAULT TRUE
);
 
CREATE TABLE parkings(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	enabled boolean NOT NULL DEFAULT TRUE
);
 
CREATE TABLE color(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	enabled boolean NOT NULL DEFAULT TRUE
);

CREATE TABLE make_car(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE
);
 
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    make_id INT NOT NULL,
    model VARCHAR(50) NOT NULL,
    plate VARCHAR(7) NOT NULL UNIQUE,
    year INT NOT NULL CHECK (YEAR > 1885),
    color_id INT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (client_id) REFERENCES users(id),
    FOREIGN KEY (make_id) REFERENCES make_car(id),
    FOREIGN KEY (color_id) REFERENCES color(id)
);
 
CREATE TABLE parking_records(
	id SERIAL PRIMARY KEY,
	vehicles_id INT NOT NULL,
	parking_id INT NOT NULL,
	entry_time TIMESTAMP NOT NULL,
	exit_time TIMESTAMP,
	FOREIGN KEY (vehicles_id) REFERENCES vehicles(id),
    FOREIGN KEY (parking_id) REFERENCES parkings(id)
);
 
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    parking_records_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    transaction_id VARCHAR(100) NOT NULL,
    pix_key VARCHAR(100) NOT NULL,
    pix_code TEXT NOT NULL,    
    CONSTRAINT fk_parking_records
        FOREIGN KEY (parking_records_id) REFERENCES parking_records (id)
);
 
/*///////////////// Logs//////////////// */
CREATE TABLE user_logs (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    action_type VARCHAR(50) NOT NULL, 
    description TEXT, 
    action_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
 
CREATE TABLE vehicle_logs (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    action_type VARCHAR(50) NOT NULL, 
    description TEXT, -- Detalhes sobre a ação
    action_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);
 
 
/* Procedure*/
CREATE OR REPLACE FUNCTION log_user_actions()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO user_logs (user_id, action_type, description, action_time)
    VALUES (NEW.id, TG_OP, 'Registro de ação do usuário', CURRENT_TIMESTAMP);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
 
 
-- Trigger para logar inserções de usuários
CREATE TRIGGER user_after_insert
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION log_user_actions();
 
-- Trigger para logar atualizações de usuários
CREATE TRIGGER user_after_update
AFTER UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION log_user_actions();
 
 
 
SELECT
    tgname AS trigger_name,
    tgenabled AS enabled,
    tgtype AS trigger_type,
    CASE
        WHEN tgenabled = 'O' THEN 'Enabled'
        WHEN tgenabled = 'D' THEN 'Disabled'
        ELSE 'Unknown'
    END AS enabled_status,
    pg_catalog.pg_get_triggerdef(t.oid) AS trigger_definition
FROM
    pg_catalog.pg_trigger t
    JOIN pg_catalog.pg_class c ON t.tgrelid = c.oid
    JOIN pg_catalog.pg_namespace n ON c.relnamespace = n.oid
WHERE
    n.nspname NOT IN ('pg_catalog', 'information_schema')
ORDER BY
    trigger_name;


--Seed

INSERT INTO  users
("name", email, "password", "role")
VALUES('Administrador', 'admin@gmail.com', '$2a$10$1HVGUdEQ1y4DX10DRUKDK.z/i59K9iXQeQty8Pwc8znnuSoua2Ta6', 'ADMIN'::public."role_type");

INSERT INTO users (name, email, password, role) VALUES
('João Silva', 'joao.silva@example.com', 'senha123', 'CLIENT'),
('Maria Oliveira', 'maria.oliveira@example.com', 'senha456', 'CLIENT'),
('Carlos Santos', 'carlos.santos@example.com', 'senha789', 'CLIENT'),
('Ana Pereira', 'ana.pereira@example.com', 'senha321', 'CLIENT'),
('Ricardo Gomes', 'ricardo.gomes@example.com', 'senha654', 'CLIENT');



INSERT INTO color (name) VALUES
('Preto'),
('Branco'),
('Prata'),

('Vermelho'),
('Azul'),
('Verde'),
('Amarelo'),
('Laranja'),
('Cinza'),
('Roxo'),
('Dourado'),
('Marrom'),
('Bege'),
('Turquesa'),
('Vinho');

INSERT INTO make_car (name) VALUES
('Toyota'),
('Ford'),
('Chevrolet'),
('Volkswagen'),
('Honda'),
('Nissan'),
('Hyundai'),
('Kia'),
('BMW'),
('Mercedes-Benz'),
('Audi'),
('Subaru'),
('Mazda'),
('Lexus'),
('Fiat');

INSERT INTO vehicles (client_id, make_id, model, plate, year, color_id, enabled) VALUES
(1, 1, 'Corolla', 'ABC1D23', 2020, 1, TRUE),
(2, 2, 'Fiesta', 'XYZ4E56', 2019, 2, TRUE),
(3, 3, 'Onix', 'JKL7M89', 2021, 5, TRUE),
(1, 4, 'Gol', 'NOP0Q12', 2022, 3, TRUE),
(2, 5, 'Civic', 'RST3U45', 2020, 4, TRUE);

INSERT INTO parkings
(id, "name")
VALUES(1, 'Shopping Bandeiras');

INSERT INTO parking_records
(id, vehicles_id, parking_id, entry_time, exit_time)
VALUES(1, 4, 1, '2024-09-22 19:02:07.960', '2024-09-22 23:02:07.960');

INSERT INTO payments (parking_records_id, amount, payment_date, transaction_id, pix_key, pix_code) 
VALUES (1, 50.00, '2024-09-22 10:00:00', 'TX123456', 'key@example.com', 'PIX123456789');