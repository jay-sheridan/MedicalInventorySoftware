package ca.sheridancollege.pajaynar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.database.CustomerDatabaseAccess;
import ca.sheridancollege.pajaynar.utils.CustomerUtils;

@Controller
public class CustomerController {

	@Autowired 
	private CustomerUtils utils;
	
	@Autowired
	private CustomerDatabaseAccess cda;
	
	@GetMapping("/customers")
	public String getCustomers(Model model) {
		System.out.println("Inside Customer cotrollr - /customers");
		System.out.println("1");
		System.out.println(cda.getCustomerList());
		
		model.addAttribute("customerList", cda.getCustomerList());
		return "customers";
	}
	
	@GetMapping("/getCustomerByPhoneNumber/{phoneNumber}")
	public String getCustomerByPhoneNumber(Model model, @PathVariable("phoneNumber") Long phoneNumber) {
		System.out.println("Inside Customer cotrollr - /getCustomerByPhonenumber/phone");
		Customer customer = cda.getCustomerByPhoneNumber(phoneNumber).get(0);
		
		model.addAttribute("customer", customer);
		return "aCustomer";
	}
	
	@PostMapping("/insertCustomer")
	public String insertCustomer(Model model, @ModelAttribute Customer customer) {
		System.out.println("Inside Customer cotrollr - /insertCustomer");
		if( !utils.isOlderCustomer(customer)) {
			
			System.out.println("");System.out.println("");System.out.println("");
			System.out.println("is new customer");
			cda.insertCustomer(customer);
		};
		model.addAttribute("phoneNumber",customer.getPhoneNumber());
		return "redirect:/generateInvoice/"+customer.getPhoneNumber();
	}
}
