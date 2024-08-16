CREATE TABLE client(
	id SERIAL PRIMARY KEY ,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL
);


CREATE TABLE parking(
	id SERIAL PRIMARY KEY ,
	name VARCHAR(100) NOT NULL
);


CREATE TABLE parking_records(
	id SERIAL PRIMARY KEY,
	client_id INT NOT NULL,
	parking_id INT NOT NULL,
	car_plate VARCHAR(7) NOT NULL,
	entry_time TIMESTAMP NOT NULL,
	exit_time TIMESTAMP
);

CREATE TABLE payments(
	id SERIAL PRIMARY KEY,
	parking_records_id INT,
	amount DECIMAL(10,2) NOT NULL,
	payment_date TIMESTAMP NOT NULL
);




INSERT INTO client (name, email, password) VALUES
('Alice Smith', 'alice.smith@example.com', 'password123'),
('Bob Johnson', 'bob.johnson@example.com', 'securepass456'),
('Carol Williams', 'carol.williams@example.com', 'mypassword789');

INSERT INTO parking (name) VALUES
('Main Street Parking'),
('Downtown Garage'),
('Airport Lot');

INSERT INTO parking_records (client_id, parking_id, car_plate, entry_time, exit_time) VALUES
(1, 1, 'ABC1234', '2024-08-15 08:30:00', '2024-08-15 17:00:00'),
(2, 2, 'XYZ5678', '2024-08-15 09:00:00', '2024-08-15 15:30:00'),
(3, 3, 'LMN9012', '2024-08-15 10:00:00', NULL);

INSERT INTO payments (parking_records_id, amount, payment_date) VALUES
(1, 15.75, '2024-08-15 17:05:00'),
(2, 12.50, '2024-08-15 15:35:00');
