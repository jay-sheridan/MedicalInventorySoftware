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
	invoice_created_date DATE NOT NULL,
	invoice_created_time TIME NOT NULL, 
	customer_phone BIGINT NOT NULL,
	total_amount FLOAT NOT NULL,
	FOREIGN KEY (customer_phone) REFERENCES customers(phone_number),
	PRIMARY KEY (invoice_created_date,invoice_created_time,customer_phone)
);

CREATE TABLE invoice_medicines(
	invoice_created_date DATE NOT NULL,
	invoice_created_time TIME NOT NULL,
	customer_phone BIGINT NOT NULL,
	medicine_name VARCHAR(255),
	qty BIGINT,
	FOREIGN KEY (invoice_created_date, invoice_created_time, customer_phone) 
        REFERENCES invoices(invoice_created_date, invoice_created_time, customer_phone),
    FOREIGN KEY (medicine_name) 
        REFERENCES medicines(name)
);