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

import ca.sheridancollege.pajaynar.beans.Medicine;
import ca.sheridancollege.pajaynar.database.MedicinesDatabaseAccess;

@Controller
public class MedicineController {
	
	@Autowired
	private MedicinesDatabaseAccess mda;
	
	private List<Medicine> medicineCart = new CopyOnWriteArrayList<Medicine>();	
	
	/**
	 * 
	 * @param model
	 */
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/allMedicines")
	public String showMedicines(Model model) {
		System.out.println("Inside medicine cotrollr - /allMedicines");
		model.addAttribute("medicineList" , mda.getMedicineList());
		return "allMedicines";
	}

	@GetMapping("/aMedicine/{name}")
	public String showAMedicine(Model model, @PathVariable("name") String name) {
		System.out.println("Inside Medicine cotrollr - /aMedicine/name");
		model.addAttribute("medicine" , mda.getMedicineByName(name).get(0));
		return "aMedicine";
	}
	
	
	/**
	 * 
	 * @param model
	 */
	@GetMapping("/newMedicine")
	public String newMedicine(Model model) {
		System.out.println("Inside Medicine cotrollr - /newMedicine");
		model.addAttribute("medicine" , new Medicine());
		return "newMedicine";
	} 
	
	@GetMapping("suggestMedicinesForInvoice")
	public String addMedicineInventoryToInvoice(Model model) {
		model.addAttribute("medicine", new Medicine());
		model.addAttribute("medicineInventory", mda.getMedicineList());
		model.addAttribute("medicineCart", medicineCart);
		return "newInvoice";
	}
	
	@PostMapping("/addMedicineToCart")
	public String addMedicineToCart (RedirectAttributes redirectAttributes, @ModelAttribute Medicine medicine) {
		medicineCart.add(medicine);
		mda.updateMedicine(medicine);
		return ("redirect:/suggestMedicinesForInvoice");
	}
	
	/**
	 * 
	 * @param model
	 * @param medicine
	 */
	@PostMapping("/addMedicine")
	public String addMedicine(Model model, @ModelAttribute Medicine medicine) {
		System.out.println("Inside Medicine cotrollr - /addMedicine");
		mda.addMedicineToDatabase(medicine);
		model.addAttribute("medicine",  new Medicine());
		return "newMedicine";
	}
	
	@GetMapping("/storeMedicinesInInvoice")
	public String storeMedicinesInInvoice(Model model) {
		System.out.println("store medcines in invoice.");
		mda.addMedicinesToInvoice(
				medicineCart, 
				(LocalDate)model.getAttribute("invoiceCreatedDate"), 
				(LocalTime)model.getAttribute("invoiceCreatedTime"), 
				(Long)model.getAttribute("customerPhone")
			);
		medicineCart.clear();
		return "redirect:/index";
	}
}