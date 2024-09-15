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
	name VARCHAR(10) NOT NULL,
	enabled boolean NOT NULL DEFAULT TRUE
);
 
CREATE TABLE color(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	enabled boolean NOT NULL DEFAULT TRUE
);
 
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    plate VARCHAR(7) NOT NULL UNIQUE,
    year INT NOT NULL CHECK (YEAR > 1885),
    color_id INT NOT NULL,
    enabled boolean NOT NULL DEFAULT TRUE,
    FOREIGN KEY (client_id) REFERENCES users(id),
    FOREIGN KEY (color_id) REFERENCES color(id)
);
 
CREATE TABLE parking_records(
	id SERIAL PRIMARY KEY,
	vehicles_id INT NOT NULL,
	parking_id INT NOT NULL,
	car_plate VARCHAR(7) NOT NULL,
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
