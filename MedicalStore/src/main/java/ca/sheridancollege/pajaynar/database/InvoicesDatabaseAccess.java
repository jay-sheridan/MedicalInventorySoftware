package ca.sheridancollege.pajaynar.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pajaynar.beans.Invoice;

@Repository
public class InvoicesDatabaseAccess {

	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public void generateInvoice(Invoice invoice) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("invoice_created_date", invoice.getInvoiceCreatedDate());
		namedParameters.addValue("invoice_created_time", invoice.getRoundedInvoiceCreatedTime());
		namedParameters.addValue("customer_phone", invoice.getCustomerPhone());
		namedParameters.addValue("total_amount", invoice.getTotalAmount());
		String query = "INSERT INTO invoices(invoice_created_date,invoice_created_time,customer_phone,total_amount) VALUES(:invoice_created_date,:invoice_created_time,:customer_phone,:total_amount);";		
		jdbc.update(query, namedParameters);
		
		int invoiceId = getInvoiceId(namedParameters);
		namedParameters.addValue("invoice_id", invoiceId);
		System.out.println(namedParameters.getValue("invoice_id"));
		for (int i = 0; i < invoice.getProcessedMedicineList().size(); i++) {
			namedParameters.addValue("medicine_name",invoice.getProcessedMedicineList().get(i).getName());
			namedParameters.addValue("batch_no" , invoice.getProcessedMedicineList().get(i).getBatchNo());
			namedParameters.addValue("qty", invoice.getProcessedMedicineList().get(i).getQty());
			
			query = "INSERT INTO invoice_medicines(invoice_id,medicine_name,batch_no,qty) VALUES(:invoice_id,:medicine_name,:batch_no,:qty)";
			jdbc.update(query, namedParameters);
		}
	}
	
	private int getInvoiceId(MapSqlParameterSource namedParameters) {
		System.out.println("Inside getInvoiceId");
		System.out.println(namedParameters.getValue("invoice_created_time"));
		String query = "SELECT invoice_id FROM invoices WHERE invoice_created_date = :invoice_created_date AND invoice_created_time = :invoice_created_time AND customer_phone = :customer_phone";
		
		return jdbc.queryForObject(query, namedParameters, Integer.class);
	}
	
	public List<Invoice> getInvoiceById(Long number) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("number", number);
		String query = "SELECT * FROM invoices WHERE customer_phone = :number";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Invoice>(Invoice.class)));
	}
	
	
	
	public List<Invoice> getInvoiceList(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM invoices";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Invoice>(Invoice.class)));
	} 
}