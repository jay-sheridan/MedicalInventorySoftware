package ca.sheridancollege.pajaynar.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
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
import ca.sheridancollege.pajaynar.database.InvoicesDatabaseAccess;

@Controller
public class InvoiceController {
	
	@Autowired
	private InvoicesDatabaseAccess ida;
	/**
	 * 
	 * @param model
	 */
	@GetMapping("/newInvoice")
	public String newInvoice(Model model) {
		System.out.println(model.getAttribute("medicineInventory"));
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("customer", new Customer());
		return "newInvoice";
	}
	
	@GetMapping("/generateInvoice/{phoneNumber}")
	public String generateInvoice(RedirectAttributes redirectAttributes,@PathVariable("phoneNumber") Long phoneNumber) {
		
		System.out.println("Inside Invoice cotrollr - /generateInvoice");
		Invoice invoice= new Invoice();
		invoice.setCustomerPhone(phoneNumber);
		invoice.setInvoiceCreatedDate(LocalDate.now());
		invoice.setInvoiceCreatedTime(LocalTime.now());
		ida.generateInvoice(invoice);
		
		redirectAttributes.addFlashAttribute("invoiceCreatedDate", invoice.getInvoiceCreatedDate());
		redirectAttributes.addFlashAttribute("invoiceCreatedTime", invoice.getInvoiceCreatedTime());
		redirectAttributes.addFlashAttribute("customerPhone", invoice.getCustomerPhone());
		return "redirect:/storeMedicinesInInvoice";
	}

	
};
