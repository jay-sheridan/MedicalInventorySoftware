package ca.sheridancollege.pajaynar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.database.CustomerDatabaseAccess;
import ca.sheridancollege.pajaynar.database.InvoicesDatabaseAccess;
import ca.sheridancollege.pajaynar.utils.CustomerUtils;

@Controller
public class CustomerController {

    // Utility class for handling customer-related validations or logic
    @Autowired 
    private CustomerUtils utils;
    
    // Database access object for managing customer data
    @Autowired
    private CustomerDatabaseAccess cda;
    
    // Database access object for managing invoice data
    @Autowired
    private InvoicesDatabaseAccess ida;
    
    /**
     * Handles GET requests to "/customers".
     * Fetches a list of customers and adds it to the model to display in the view.
     *
     * @param model The model object used to pass data to the view.
     * @return The name of the view that displays the customer list.
     */
    @GetMapping("/customers")
    public String getCustomers(Model model) {
        System.out.println("Inside Customer controller - /customers");
        System.out.println("Fetching customer list:");
        System.out.println(cda.getCustomerList());
        
        // Add the list of customers to the model
        model.addAttribute("customerList", cda.getCustomerList());
        return "customerView/customers"; // Returns the view for displaying customers
    }
    
    /**
     * Handles GET requests to "/getCustomerFormPage".
     * Displays a form for adding a new customer.
     *
     * @param model The model object used to pass data to the view.
     * @return The name of the view that contains the customer form.
     */
    @GetMapping("/getCustomerFormPage")
    public String getCustomerToInvoice(Model model) {
        // Add a new empty Customer object to the model for form binding
        model.addAttribute("customer", new Customer());
        return "customerView/customerForm"; // Returns the view for the customer form
    }
    
    /**
     * Handles GET requests to "/getCustomerByPhoneNumber/{phoneNumber}".
     * Fetches a customer's details and their associated invoices using their phone number.
     *
     * @param model The model object used to pass data to the view.
     * @param phoneNumber The phone number of the customer to retrieve.
     * @return The name of the view displaying customer details and invoices.
     */
    @GetMapping("/getCustomerByPhoneNumber/{phoneNumber}")
    public String getCustomerByPhoneNumber(Model model, @PathVariable("phoneNumber") Long phoneNumber) {
        System.out.println("Inside Customer controller - /getCustomerByPhoneNumber/{phone}");
        
        // Fetch and add the invoices associated with the phone number
        model.addAttribute("invoiceList", ida.getInvoiceListByPhone(phoneNumber));
        
        // Fetch and add the customer details to the model
        model.addAttribute("customer", cda.getCustomerByPhoneNumber(phoneNumber).get(0));
        return "customerView/aCustomer"; // Returns the view for displaying a single customer
    }
    
    /**
     * Handles POST requests to "/insertCustomer".
     * Adds a new customer to the database if they are not already an existing customer.
     *
     * @param model The model object used to pass data to the view.
     * @param customer The customer object received from the form submission.
     * @return A redirect to the invoice generation page for the customer's phone number.
     */
    @PostMapping("/insertCustomer")
    public String insertCustomer(Model model, @ModelAttribute Customer customer) {
        System.out.println("Inside Customer controller - /insertCustomer");
        
        // Check if the customer is new, and if so, insert them into the database
        if (!utils.isOlderCustomer(customer)) {
            cda.insertCustomer(customer);
        }
        
        // Add the customer's phone number to the model and redirect to generate their invoice
        model.addAttribute("phoneNumber", customer.getPhoneNumber());
        return "redirect:/generateInvoice/" + customer.getPhoneNumber(); // Redirects to invoice generation
    }
}
