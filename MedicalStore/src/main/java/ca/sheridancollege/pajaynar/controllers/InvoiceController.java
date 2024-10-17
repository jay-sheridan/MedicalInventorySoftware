package ca.sheridancollege.pajaynar.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.beans.Medicine;
import ca.sheridancollege.pajaynar.database.DatabaseAccess;
import ca.sheridancollege.pajaynar.utils.CustomerUtils;

@Controller
public class InvoiceController {
	
	@Autowired
	private DatabaseAccess da; 
	
	@Autowired 
	private CustomerUtils utils;
	
	private List<Medicine> medicineList = new CopyOnWriteArrayList<Medicine>();	
	
	@GetMapping("/testing")
	public String testing(Model model) {
//		System.out.println(utils.isOldCustomer());
		return "index";
	}
	
	@GetMapping("/newInvoice")
	public String newInvoice(Model model) {
		System.out.println("Inside Invoice cotrollr - /newInvoice");
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("customer", new Customer());
		model.addAttribute("medicineList", medicineList);
		model.addAttribute("medicineSuggestions" , da.getMedicineList());
		return "newInvoice";
	}
	
	@PostMapping("/newInvoice/addMedicine")
	public String addMedicine(Model model, @ModelAttribute Medicine medicine) {
		System.out.println("Inside Invoice cotrollr - /newInvoice/addMedicine");
		medicineList.add(medicine);
		return "redirect:/newInvoice";
	}
	
	
	@GetMapping("/generateInvoice/{phoneNumber}")
	public String generateInvoice(Model model,@PathVariable("phoneNumber") Long phoneNumber) {
		System.out.println("Inside Invoice cotrollr - /generateInvoice");
		if( !medicineList.isEmpty()) {
			Invoice invoice= new Invoice();
			invoice.setCustomerPhone(phoneNumber);
			invoice.setMedicineList(medicineList);
			invoice.setInvoiceCreatedDate(LocalDate.now());
			invoice.setInvoiceCreatedTime(LocalTime.now());
			da.generateInvoice(invoice);
			return "finalInvoice";
		};
		return "index";
	};
};
