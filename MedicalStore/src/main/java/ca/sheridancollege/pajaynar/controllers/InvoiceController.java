package ca.sheridancollege.pajaynar.controllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	Map<Integer, Invoice> onGoingInvoices= new HashMap<>();
	
	private Invoice invoice;
	
	@GetMapping("/newInvoice")
	public String newInvoice(Model model) {
		System.out.println("Inside Invoice cotrollr - /newInvoice");
		invoice = new Invoice();
		invoice.setInvoiceId(onGoingInvoices.size()+1);
		onGoingInvoices.put(invoice.getInvoiceId(),invoice);
		return "redirect:/renderInvoiceForm/"+invoice.getInvoiceId();
	}
	
	@GetMapping("/renderInvoiceForm/{invoiceId}")
	public String renderInvoiceForm(Model model, @PathVariable("invoiceId") int invoiceId) {
		
		model.addAttribute("invoice", onGoingInvoices.get(invoiceId));
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("medicineInventory", mda.getMedicineList());
		return "invoiceView/newInvoice";
	}
	
	@PostMapping("/addMedicineToCart/{invoiceId}")
	public String addMedicineToCart (@ModelAttribute Medicine medicine, @PathVariable("invoiceId") int invoiceId) {
		System.out.println(medicine.getMfgDate());
//		System.out.println("Inside Invoice cotrollr - /addMedicineToCart - POST");
		onGoingInvoices.get(invoiceId).getMedicineList().add(medicine);
		onGoingInvoices.get(invoiceId).updateTotalInvoiceAmount(medicine);
		System.out.println(onGoingInvoices.get(invoiceId).getMedicineList().get(0));
		mda.updateMedicine(medicine);
//		return renderInvoiceForm(model);
		return "redirect:/renderInvoiceForm/" + invoiceId;
	}
	
	@GetMapping("/editMedicineToCart/{invoiceId}/{medicineName}")
	public String editMedicineToCart(Model model, @PathVariable("invoiceId") int invoiceId, @PathVariable("medicineName") String name)
	{
		System.out.println("Inside Invoice cotrollr - /editMedicineToCard - GET");
		invoice = onGoingInvoices.get(invoiceId);
		Medicine medicine = invoice.getMedicineList().stream().filter(m -> m.getName().equals(name)).findFirst().get();
		if(medicine != null) {
			invoice.getMedicineList().remove(medicine);
		}
		System.out.println(medicine.getExpDate());
		model.addAttribute("invoice", invoice);
		model.addAttribute("medicine", medicine);
		model.addAttribute("medicineInventory", mda.getMedicineList());
		return "invoiceView/newInvoice";
	}
	
	@GetMapping("/deleteMedicineFromCart/{invoiceId}/{medicineName}")
	public String deleteMedicineFromCart(Model model, @PathVariable("invoiceId") int invoiceId, @PathVariable("medicineName") String name)
    {
        System.out.println("Inside Invoice cotrollr - /deleteMedicineFromCart - GET");
        onGoingInvoices.get(invoiceId).getMedicineList().removeIf(m -> m.getName().equals(name));
        return "redirect:/renderInvoiceForm/" + invoiceId;
    }
	
	@GetMapping("/generateInvoice/{phoneNumber}")
	public String generateInvoice(Model model,@PathVariable("phoneNumber") Long phoneNumber) {
		invoice.setCustomerPhone(phoneNumber);
		ida.generateInvoice(invoice);
		return finalInvoice(model, invoice);
	}
	
	@GetMapping("/getInvoices")
	public String getInvoices(Model model) {
        System.out.println("Inside Invoice cotrollr - /getInvoices");
        model.addAttribute("invoices", ida.getInvoiceList());
        System.out.println(model.getAttribute("invoices"));
        return "invoiceView/invoices";
	}
	
	@GetMapping("/viewInvoice/{number}")
	public String getInvoiceById(Model model, @PathVariable("number") Long number) {
        System.out.println("Inside Invoice cotrollr - /invoice/{number}");
        model.addAttribute("invoice", ida.getInvoiceById(number).get(0));
        System.out.println(model.getAttribute("invoice"));
        return "invoiceView/invoice";
	}
	
	private String finalInvoice(Model model, Invoice invoice) {
		System.out.println("Inside Invoice cotrollr - /finalInvoice");
		model.addAttribute("invoice", this.invoice);
		model.addAttribute("customer", cda.getCustomerByPhoneNumber(invoice.getCustomerPhone()).get(0));
		System.out.println(model.getAttribute("customer"));
		return "invoiceView/finalInvoice";
	}	
};
