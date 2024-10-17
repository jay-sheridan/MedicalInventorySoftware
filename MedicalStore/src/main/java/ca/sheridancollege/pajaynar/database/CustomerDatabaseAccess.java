package ca.sheridancollege.pajaynar.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pajaynar.beans.Customer;

@Repository
public class CustomerDatabaseAccess {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	public void insertCustomer(Customer customer) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("phone_number", customer.getPhoneNumber());
		namedParameters.addValue("name", customer.getName());
		namedParameters.addValue("address", customer.getAddress());
		
		String query = "INSERT INTO customers(phone_number, name, address) VALUES (:phone_number,:name,:address)";
		if( (jdbc.update(query, namedParameters)) > 0) {
			System.out.println("customer "+ customer.getName()+" is added to database");
		}
	}
	
	public List<Customer> getCustomerList(){
		System.out.println("Inside method");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM customers";
		return (jdbc.query(query, namedParameters , new BeanPropertyRowMapper<Customer>(Customer.class)));
	}
	
	public List<Customer> getCustomerByPhoneNumber(Long phoneNumber){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("phone_number", phoneNumber);
		String query = "SELECT * FROM customers WHERE phone_number = :phone_number";
		
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Customer>(Customer.class)));
	}
	
	public void deleteCustomerByPhoneNumber(Long phoneNumber) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("phone_number", phoneNumber);
		
		String query = "DELETE FROM customers WHERE phone_number = :phone_number";
		
		if ( (jdbc.update(query, namedParameters)) > 0) {
			System.out.println("Customer deleted from database.");
		}
	}
}
