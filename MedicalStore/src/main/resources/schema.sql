CREATE TABLE medicines (
	name VARCHAR(255) PRIMARY KEY,
	qty BIGINT,
	mfg_lic_no VARCHAR(255) NOT NULL,
	batch_no VARCHAR(255) NOT NULL,
	mfg_date DATE,
	exp_date DATE,
	price BIGINT
);

CREATE TABLE customers(
	phone_number BIGINT PRIMARY KEY,
	name VARCHAR(255),
	address VARCHAR(255)
);

CREATE TABLE invoices(
	invoice_date DATE NOT NULL UNIQUE,
	customer_phone_number BIGINT NOT NULL,
	total_amount BIGINT NOT NULL,
	FOREIGN KEY (customer_phone_number) REFERENCES customers(phone_number),
	PRIMARY KEY (invoice_date,customer_phone_number)
);

CREATE TABLE invoice_medicines(
	invoice_date DATE NOT NULL,
	customer_phone_number BIGINT NOT NULL,
	medicine_name VARCHAR(255),
	qty BIGINT,
	FOREIGN KEY (invoice_date) REFERENCES invoices(invoice_date),
	FOREIGN KEY (customer_phone_number) REFERENCES customers(phone_number),
	FOREIGN KEY(medicine_name) REFERENCES medicines(name),
	PRIMARY KEY (invoice_date, customer_phone_number, medicine_name)
);