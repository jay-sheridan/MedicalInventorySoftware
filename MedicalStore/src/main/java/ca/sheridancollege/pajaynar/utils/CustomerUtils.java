package ca.sheridancollege.pajaynar.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.database.CustomerDatabaseAccess;

@Component
public class CustomerUtils {
	
	@Autowired
	private CustomerDatabaseAccess cda;
	
	public boolean isOlderCustomer(Customer customer) {
		
		if(cda.getCustomerByPhoneNumber(customer.getPhoneNumber()).isEmpty()) {
			return false;			
		}
		return true;
	}
}
