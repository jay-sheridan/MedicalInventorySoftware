CREATE TABLE medicines (
	name VARCHAR(255) NOT NULL,
	qty BIGINT,
	mfg_lic_no VARCHAR(255) NOT NULL,
	batch_no VARCHAR(255) NOT NULL,
	mfg_date DATE,
	exp_date DATE,
	price BIGINT,
	is_active BOOLEAN DEFAULT TRUE,
	PRIMARY KEY (name , batch_no)
);

CREATE TABLE customers(
	phone_number BIGINT PRIMARY KEY,
	name VARCHAR(255),
	address VARCHAR(255),
	firstPurchaseDate DATE DEFAULT CURRENT_DATE
);

CREATE TABLE invoices(
	invoice_id BIGINT NOT NULL AUTO_INCREMENT,
	invoice_created_date DATE NOT NULL,
	invoice_created_time TIME NOT NULL, 
	customer_phone BIGINT NOT NULL,
	total_amount FLOAT NOT NULL,
	FOREIGN KEY (customer_phone) REFERENCES customers(phone_number),
	PRIMARY KEY (invoice_id)
);

CREATE TABLE invoice_medicines(
	invoice_id BIGINT,
	name VARCHAR(255) NOT NULL,
	batch_no VARCHAR(255) NOT NULL,	
	qty BIGINT,
	price BIGINT,
	FOREIGN KEY (invoice_id) 
        REFERENCES invoices(invoice_id),
    FOREIGN KEY (name, batch_no) 
        REFERENCES medicines(name , batch_no),
    PRIMARY KEY (invoice_id,name,batch_no)
);