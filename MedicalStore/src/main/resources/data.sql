INSERT INTO medicines (name, qty, mfg_lic_no, batch_no, mfg_date, exp_date, price)
VALUES
('Paracetamol', 0, 'ML001', 'B001', '2022-06-15', '2024-06-14', 5.99),
('Aspirin', 150, 'ML002', 'B002', '2022-07-01', '2025-06-30', 3.49),
('Ibuprofen', 200, 'ML003', 'B003', '2023-01-20', '2025-01-19', 4.99),
('Amoxicillin', 50, 'ML004', 'B004', '2022-05-12', '2024-05-11', 12.99),
('Cetirizine', 120, 'ML005', 'B005', '2022-04-25', '2024-04-24', 2.99),
('Diclofenac', 180, 'ML006', 'B006', '2023-03-30', '2025-03-29', 6.99),
('Metformin', 90, 'ML007', 'B007', '2022-08-05', '2024-08-04', 8.99),
('Atorvastatin', 60, 'ML008', 'B008', '2022-09-17', '2024-09-16', 14.99),
('Lisinopril', 130, 'ML009', 'B009', '2023-02-10', '2025-02-09', 7.99),
('Omeprazole', 80, 'ML010', 'B010', '2023-05-15', '2025-05-14', 9.49),
('Azithromycin', 100, 'ML011', 'B011', '2022-10-25', '2024-10-24', 19.99),
('Clarithromycin', 40, 'ML012', 'B012', '2022-11-05', '2024-11-04', 18.99),
('Ciprofloxacin', 110, 'ML013', 'B013', '2022-03-18', '2024-03-17', 10.49),
('Doxycycline', 140, 'ML014', 'B014', '2023-06-10', '2025-06-09', 16.49),
('Metoprolol', 70, 'ML015', 'B015', '2023-07-21', '2025-07-20', 5.99),
('Simvastatin', 160, 'ML016', 'B016', '2022-12-12', '2024-12-11', 13.99),
('Prednisolone', 90, 'ML017', 'B017', '2023-01-05', '2025-01-04', 7.49),
('Levothyroxine', 50, 'ML018', 'B018', '2022-02-08', '2024-02-07', 11.49),
('Hydrochlorothiazide', 130, 'ML019', 'B019', '2023-04-22', '2025-04-21', 4.49),
('Clopidogrel', 100, 'ML020', 'B020', '2022-05-14', '2024-05-13', 15.99);

INSERT INTO customers (phone_number, name, address) VALUES
(12345678901, 'Alice Smith', '123 Maple St, Springfield'),
(12345678902, 'Bob Johnson', '456 Oak St, Springfield'),
(12345678903, 'Carol Williams', '789 Pine St, Springfield'),
(12345678904, 'David Brown', '321 Cedar St, Springfield'),
(12345678905, 'Emma Davis', '654 Birch St, Springfield'),
(12345678906, 'Frank Miller', '987 Walnut St, Springfield'),
(12345678907, 'Grace Wilson', '234 Elm St, Springfield'),
(12345678908, 'Henry Moore', '567 Cherry St, Springfield'),
(12345678909, 'Isabella Taylor', '890 Ash St, Springfield'),
(12345678910, 'Jack Anderson', '123 Spruce St, Springfield'),
(12345678911, 'Liam Thomas', '456 Fir St, Springfield'),
(12345678912, 'Mia Martinez', '789 Poplar St, Springfield'),
(12345678913, 'Noah Lee', '321 Willow St, Springfield'),
(12345678914, 'Olivia Gonzalez', '654 Sycamore St, Springfield'),
(12345678915, 'Peter Rodriguez', '987 Maplewood St, Springfield'),
(12345678916, 'Quinn Perez', '234 Oakwood St, Springfield'),
(12345678917, 'Ryan White', '567 Pinewood St, Springfield'),
(12345678918, 'Sophia Harris', '890 Cedarwood St, Springfield'),
(12345678919, 'Tyler Clark', '123 Birchwood St, Springfield'),
(12345678920, 'Victoria Lewis', '456 Elmwood St, Springfield');

INSERT INTO invoices (invoice_created_date, invoice_created_time, customer_phone, total_amount) VALUES
('2024-10-01', '10:00:00', 12345678901, 150.75),
('2024-10-01', '11:00:00', 12345678902, 200.50),
('2024-10-02', '12:00:00', 12345678903, 250.00),
('2024-10-02', '13:00:00', 12345678904, 175.25),
('2024-10-03', '14:00:00', 12345678905, 300.80),
('2024-10-03', '15:00:00', 12345678906, 120.40),
('2024-10-04', '16:00:00', 12345678907, 400.00),
('2024-10-04', '17:00:00', 12345678908, 99.99),
('2024-10-05', '18:00:00', 12345678909, 275.60),
('2024-10-05', '19:00:00', 12345678910, 225.90),
('2024-10-06', '10:30:00', 12345678911, 340.00),
('2024-10-06', '11:30:00', 12345678912, 150.00),
('2024-10-07', '12:30:00', 12345678913, 230.75),
('2024-10-07', '13:30:00', 12345678914, 189.99),
('2024-10-08', '14:30:00', 12345678915, 300.00),
('2024-10-08', '15:30:00', 12345678916, 60.50),
('2024-10-09', '16:30:00', 12345678917, 80.00),
('2024-10-09', '17:30:00', 12345678918, 95.25),
('2024-10-10', '18:30:00', 12345678919, 170.00),
('2024-10-10', '19:30:00', 12345678920, 220.10);

INSERT INTO invoice_medicines (invoice_id, name, batch_no, qty, price)
VALUES
(1, 'Paracetamol', 'B001', 10, 5),
(2, 'Aspirin', 'B002', 15, 10),
(3, 'Ibuprofen', 'B003', 20, 10),
(4, 'Amoxicillin', 'B004', 5, 7),
(5, 'Cetirizine', 'B005', 12, 9),
(6, 'Diclofenac', 'B006', 18, 11),
(7, 'Metformin', 'B007', 9, 14),
(8, 'Atorvastatin', 'B008', 6, 11),
(9, 'Lisinopril', 'B009', 13, 14),
(10,'Omeprazole', 'B010', 8, 10),
(11, 'Azithromycin', 'B011', 10, 15),
(12, 'Clarithromycin', 'B012', 4 , 1),
(13, 'Ciprofloxacin', 'B013', 11, 2),
(14, 'Doxycycline', 'B014', 14, 3),
(15, 'Metoprolol', 'B015', 7, 4),
(16, 'Simvastatin', 'B016', 16, 12),
(17, 'Prednisolone', 'B017', 9, 15),
(18, 'Levothyroxine', 'B018', 5, 18),
(19, 'Hydrochlorothiazide', 'B019', 13, 20),
(20, 'Clopidogrel', 'B020', 10, 11);
