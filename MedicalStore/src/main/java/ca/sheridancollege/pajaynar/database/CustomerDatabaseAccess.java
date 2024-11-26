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
    private NamedParameterJdbcTemplate jdbc; // JDBC template for executing SQL queries with named parameters

    /**
     * Inserts a new customer into the `customers` table.
     *
     * @param customer The customer object containing the details to insert.
     */
    public void insertCustomer(Customer customer) {
        // Map parameters to their respective SQL placeholders
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("phone_number", customer.getPhoneNumber());
        namedParameters.addValue("name", customer.getName());
        namedParameters.addValue("address", customer.getAddress());

        // SQL query to insert customer data
        String query = "INSERT INTO customers(phone_number, name, address) VALUES (:phone_number, :name, :address)";
        
        // Execute the query and log success if rows were affected
        if ((jdbc.update(query, namedParameters)) > 0) {
            System.out.println("Customer " + customer.getName() + " is added to the database.");
        }
    }

    /**
     * Retrieves the list of all customers from the `customers` table.
     *
     * @return A list of Customer objects containing all customer records.
     */
    public List<Customer> getCustomerList() {
        System.out.println("Retrieving customer list from database.");
        
        // Query to select all customers
        String query = "SELECT * FROM customers";
        
        // Execute query and map results to Customer objects
        return jdbc.query(query, new MapSqlParameterSource(), 
                          new BeanPropertyRowMapper<Customer>(Customer.class));
    }

    /**
     * Retrieves a list of customers by their phone number.
     *
     * @param phoneNumber The phone number to search for.
     * @return A list of Customer objects matching the given phone number.
     */
    public List<Customer> getCustomerByPhoneNumber(Long phoneNumber) {
        // Map parameters for SQL query
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("phone_number", phoneNumber);

        // Query to retrieve customers by phone number
        String query = "SELECT * FROM customers WHERE phone_number = :phone_number";
        
        // Execute query and map results to Customer objects
        return jdbc.query(query, namedParameters, 
                          new BeanPropertyRowMapper<Customer>(Customer.class));
    }

    /**
     * Deletes a customer record from the `customers` table by phone number.
     *
     * @param phoneNumber The phone number of the customer to delete.
     */
    public void deleteCustomerByPhoneNumber(Long phoneNumber) {
        // Map parameters for SQL query
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("phone_number", phoneNumber);

        // SQL query to delete a customer by phone number
        String query = "DELETE FROM customers WHERE phone_number = :phone_number";
        
        // Execute the query and log success if rows were affected
        if ((jdbc.update(query, namedParameters)) > 0) {
            System.out.println("Customer deleted from the database.");
        }
    }
}
