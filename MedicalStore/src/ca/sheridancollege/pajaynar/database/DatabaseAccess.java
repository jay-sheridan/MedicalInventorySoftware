package ca.sheridancollege.pajaynar.database;

import org.springframework.jdbc.core.namedparam.*;
import ca.sheridancollege.pajaynar.beans.*;
import java.util.*;

@Repository()
public class DatabaseAccess {

	@Autowired()
	NamedParameterJdbcTemplate jdbc;

	/**
	 * 
	 * @param medicine
	 */
	public void addMedicine(Medicine medicine) {
		// TODO - implement DatabaseAccess.addMedicine
		throw new UnsupportedOperationException();
	}

	public List<Medicine> getMedicineList() {
		// TODO - implement DatabaseAccess.getMedicineList
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		// TODO - implement DatabaseAccess.addCustomer
		throw new UnsupportedOperationException();
	}

	public List<Customer> getAllCustomers() {
		// TODO - implement DatabaseAccess.getAllCustomers
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param phoneNumber
	 */
	public List<Customer> getCustomerByPhoneNumber(Long phoneNumber) {
		// TODO - implement DatabaseAccess.getCustomerByPhoneNumber
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param invoice
	 */
	public void generateInvoice(Invoice invoice) {
		// TODO - implement DatabaseAccess.generateInvoice
		throw new UnsupportedOperationException();
	}

}