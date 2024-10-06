package ca.sheridancollege.pajaynar.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.beans.Medicine;

@Repository
public class DatabaseAccess {
	
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public void addMedicine(Medicine medicine) {
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
	
	public void addCustomer(Customer customer) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("phone_number", customer.getPhoneNumber());
		namedParameters.addValue("name", customer.getName());
		namedParameters.addValue("address", customer.getAddress());
		
		String query = "INSERT INTO customers(phone_number,name,address) VALUES(:phone_number,:name,:address)";
		jdbc.update(query, namedParameters);
	}
	
	public List<Customer> getAllCustomers(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM customers";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Customer>(Customer.class)));
	}
	
	public List<Customer> getCustomerByPhoneNumber(Long phoneNumber){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("phone_number", phoneNumber);
		
		String query = "SELECT * FROM customers WHERE phone_number = :phone_number";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Customer>(Customer.class)));
	}
	
	public void generateInvoice(Invoice invoice) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("invoice_date", invoice.getDate());
		System.out.println(invoice.getCustomer().getPhoneNumber());
		namedParameters.addValue("customer_phone_number", invoice.getCustomer().getPhoneNumber());
		namedParameters.addValue("total_amount", invoice.getTotalAmount());
		
		String query = "INSERT INTO invoices(invoice_date,customer_phone_number,total_amount) VALUES(:invoice_date,:customer_phone_number,:total_amount)";
		
		jdbc.update(query, namedParameters);
		for(int i=0; i<invoice.getMedicineList().size(); i++) {
			namedParameters.addValue("medicine_name", invoice.getMedicineList().get(i).getName());
			namedParameters.addValue("qty",invoice.getMedicineList().get(i).getQty());
			String query2 = "INSERT INTO invoice_medicines(invoice_date, customer_phone_number, medicine_name, qty) VALUES(:invoice_date, :customer_phone_number, :medicine_name, :qty)";
			jdbc.update(query2, namedParameters);
		}
	
	}
}