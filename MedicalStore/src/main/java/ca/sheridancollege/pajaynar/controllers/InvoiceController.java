package ca.sheridancollege.pajaynar.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.beans.Medicine;
import ca.sheridancollege.pajaynar.database.DatabaseAccess;

@Controller
public class InvoiceController {
	
	@Autowired
	private DatabaseAccess da;
	
	private List<Medicine> medicineList = new CopyOnWriteArrayList<Medicine>();	
	
	@GetMapping("/newInvoice")
	public String newInvoice(Model model) {
		model.addAttribute("invoice" , new Invoice());
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("customer", new Customer());
		model.addAttribute("medicineList", medicineList);
		return "newInvoice";
	}
	
	@PostMapping("/newInvoice/addMedicine")
	public String addMedicine(Model model, @ModelAttribute Medicine medicine) {
		medicineList.add(medicine);
		return "redirect:/newInvoice";
	}
	
	@PostMapping("/generateInvoice")
	public String generateInvoice(Model model, @ModelAttribute Customer customer) {
		da.addCustomer(customer);
		Invoice invoice= new Invoice();
		invoice.setCustomer(customer);
		invoice.setMedicineList(medicineList);
		System.out.println(invoice);
		da.generateInvoice(invoice);
		return "finalInvoice";
	}
};
