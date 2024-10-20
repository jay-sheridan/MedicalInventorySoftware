package ca.sheridancollege.pajaynar.database;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pajaynar.beans.Medicine;

@Repository
public class MedicinesDatabaseAccess {
	
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public void addMedicineToDatabase(Medicine medicine) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", medicine.getName());
		namedParameters.addValue("qty", medicine.getQty());
		namedParameters.addValue("mfg_lic_no", medicine.getMfgLicNo());
		namedParameters.addValue("batch_no", medicine.getBatchNo());
		namedParameters.addValue("mfg_date", medicine.getMfgDate());
		namedParameters.addValue("exp_date", medicine.getExpDate());
		namedParameters.addValue("price", medicine.getPrice());
		
		String query = "INSERT INTO medicines(name,qty,mfg_lic_no,batch_no,exp_date,mfg_date,price) VALUES (:name,:qty,:mfg_lic_no,:batch_no,:exp_date,:mfg_date,:price)";
		
		System.out.println(jdbc.update(query, namedParameters));
	}
	
	public List<Medicine> getMedicineList(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query="SELECT * FROM medicines";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Medicine>(Medicine.class)));
	}
	
	public List<Medicine> getMedicineByName(String name){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", name);
		String query = "SELECT * FROM medicines WHERE name = :name";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Medicine>(Medicine.class)));
	}
	
	public void updateMedicine(Medicine medicine) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("medicine_name", medicine.getName());
		namedParameters.addValue("remainingQty", this.getRemainingQty(medicine));
		
		String query = "UPDATE medicines SET qty = :remainingQty WHERE name = :medicine_name";
		jdbc.update(query, namedParameters);
	}
	
	private int getRemainingQty(Medicine medicine) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("medicine_name", medicine.getName());
		String query = "SELECT qty FROM medicines WHERE name = :medicine_name";
		return (jdbc.queryForObject(query, namedParameters, Integer.class) - medicine.getQty());
	}
	
	public void addMedicinesToInvoice(List<Medicine> medicineCart , LocalDate invoiceCreatedDate ,LocalTime invoiceCreatedTime, Long customerPhone) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("invoice_created_date", invoiceCreatedDate);
		namedParameters.addValue("invoice_created_time", invoiceCreatedTime);
		namedParameters.addValue("customerPhone", customerPhone);
		for(int i=0; i<medicineCart.size(); i++) {
			namedParameters.addValue("medicine_name", medicineCart.get(i).getName());
			namedParameters.addValue("batch_no", medicineCart.get(i).getBatchNo() );
			namedParameters.addValue("qty",medicineCart.get(i).getQty());
			
			String invoiceMedicinesQuery = "INSERT INTO invoice_medicines(invoice_created_date,invoice_created_time,customer_phone,medicine_name,batch_no,qty) VALUES (:invoice_created_date,:invoice_created_time,:customer_phone, :medicine_name,:batch_no ,:qty)";
			jdbc.update(invoiceMedicinesQuery, namedParameters);
		}
	}
	
	public List<Medicine> retrieveMedicineList(Date invoiceCreatedDate, Time invoiceCreatedTime, Long customerPhone){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("invoice_created_date", invoiceCreatedDate);
		namedParameters.addValue("invoice_created_time", invoiceCreatedTime);
		namedParameters.addValue("customer_phone", customerPhone);
		
		String query = "SELECT medicine_name , qty , batch_no from invoice_medicines where invoice_created_date = :invoice_created_date and invoice_created_time = :invoice_created_time and customer_phone = :customer_phone";
		return (jdbc.query(query, namedParameters , new BeanPropertyRowMapper<Medicine>(Medicine.class)));
	}
}