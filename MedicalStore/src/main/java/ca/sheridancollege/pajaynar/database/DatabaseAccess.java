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
	
	public List<Medicine> getMedicineByName(String name){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", name);
		String query = "SELECT * FROM medicines WHERE name = :name";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Medicine>(Medicine.class)));
	}
	
	public void generateInvoice(Invoice invoice) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("invoice_created_date", invoice.getInvoiceCreatedDate());
		namedParameters.addValue("invoice_created_time", invoice.getInvoiceCreatedTime());
		namedParameters.addValue("customer_phone", invoice.getCustomerPhone());
		namedParameters.addValue("total_amount", invoice.getTotalMedicineAmount());
		
		String query = "INSERT INTO invoices(invoice_created_date,invoice_created_time,customer_phone,total_amount) VALUES(:invoice_created_date,:invoice_created_time,:customer_phone,:total_amount);";
		
		jdbc.update(query, namedParameters);
		
		for(int i=0; i<invoice.getMedicineList().size(); i++) {
			namedParameters.addValue("medicine_name", invoice.getMedicineList().get(i).getName());
			namedParameters.addValue("qty",invoice.getMedicineList().get(i).getQty());
			String query2 = "INSERT INTO invoice_medicines(invoice_created_date,invoice_created_time,customer_phone,medicine_name,qty) VALUES (:invoice_created_date,:invoice_created_time,:customer_phone, :medicine_name, :qty)";
			jdbc.update(query2, namedParameters);
		}
	
	}
}