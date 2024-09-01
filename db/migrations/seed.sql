CREATE TYPE role AS ENUM ('CLIENT', 'PARKING', 'ADMIN');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role role NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    enabled BOOLEAN NOT NULL DEFAULT TRUE     
);

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    document VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT fk_users
        FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE parking (
    id SERIAL PRIMARY KEY,
    CONSTRAINT fk_users
        FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE parking_records (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    parking_id INT NOT NULL,
    car_plate VARCHAR(7) NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    CONSTRAINT fk_client
        FOREIGN KEY (client_id) REFERENCES client (id),
    CONSTRAINT fk_parking
        FOREIGN KEY (parking_id) REFERENCES parking (id)
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    parking_records_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_parking_records
        FOREIGN KEY (parking_records_id) REFERENCES parking_records (id)
);
