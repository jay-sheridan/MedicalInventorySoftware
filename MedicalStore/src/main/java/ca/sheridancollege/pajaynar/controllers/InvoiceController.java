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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.beans.Medicine;
import ca.sheridancollege.pajaynar.database.CustomerDatabaseAccess;
import ca.sheridancollege.pajaynar.database.InvoicesDatabaseAccess;
import ca.sheridancollege.pajaynar.database.MedicinesDatabaseAccess;

@Controller
public class InvoiceController {
	
	@Autowired
	private InvoicesDatabaseAccess ida;
	
	@Autowired
	private MedicinesDatabaseAccess mda;
	
	@Autowired
	private CustomerDatabaseAccess cda;
	
	private Invoice invoice;
	
	@GetMapping("/newInvoice")
	public String newInvoice(Model model) {
		System.out.println("Inside Invoice cotrollr - /newInvoice");
		invoice = new Invoice();
		return renderInvoiceForm(model);
	}
	

	private String renderInvoiceForm(Model model) {
		System.out.println("Inside Invoice cotrollr - renderInvoiceFrom");
		model.addAttribute("invoice", this.invoice);
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("medicineInventory", mda.getMedicineList());
		return "invoiceView/newInvoice";
	}
	
	@PostMapping("/addMedicineToCart")
	public String addMedicineToCart (Model model, @ModelAttribute Medicine medicine) {
		System.out.println("Inside Invoice cotrollr - /addMedicineToCart - POST");
		invoice.getMedicineList().add(medicine);
		System.out.println(invoice.getMedicineList().get(0));
		invoice.updateTotalInvoiceAmount(medicine);
		mda.updateMedicine(medicine);
		return renderInvoiceForm(model);
	}
	
	@GetMapping("/generateInvoice/{phoneNumber}")
	public String generateInvoice(Model model,@PathVariable("phoneNumber") Long phoneNumber) {
		invoice.setCustomerPhone(phoneNumber);
		ida.generateInvoice(invoice);
		return finalInvoice(model, invoice);
	}
	
	private String finalInvoice(Model model, Invoice invoice) {
		System.out.println("Inside Invoice cotrollr - /finalInvoice");
		model.addAttribute("invoice", this.invoice);
		model.addAttribute("customer", cda.getCustomerByPhoneNumber(invoice.getCustomerPhone()).get(0));
		System.out.println(model.getAttribute("customer"));
		return "invoiceView/finalInvoice";
	}
//	@GetMapping("/storeMedicinesInInvoice")
//	public String storeMedicinesInInvoice(Model model) {
//		System.out.println("store medcines in invoice.");
//		mda.addMedicinesToInvoice(
//				medicineCart, 
//				(LocalDate)model.getAttribute("invoiceCreatedDate"), 
//				(LocalTime)model.getAttribute("invoiceCreatedTime"), 
//				(Long)model.getAttribute("customerPhone")
//			);
//		medicineCart.clear();
//		return "redirect:/";
//	}

	
};
