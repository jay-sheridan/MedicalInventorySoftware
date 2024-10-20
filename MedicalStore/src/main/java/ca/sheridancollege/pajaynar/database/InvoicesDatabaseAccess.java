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
		namedParameters.addValue("invoice_created_time", invoice.getInvoiceCreatedTime());
		namedParameters.addValue("customer_phone", invoice.getCustomerPhone());
		System.out.print("before calling the method");
		namedParameters.addValue("total_amount", invoice.getTotalInvoiceAmount());
		
		String query = "INSERT INTO invoices(invoice_created_date,invoice_created_time,customer_phone,total_amount) VALUES(:invoice_created_date,:invoice_created_time,:customer_phone,:total_amount);";		
		jdbc.update(query, namedParameters);	
	}
	
	public List<Invoice> getInvoiceList(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM invoices";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Invoice>(Invoice.class)));
	} 
}