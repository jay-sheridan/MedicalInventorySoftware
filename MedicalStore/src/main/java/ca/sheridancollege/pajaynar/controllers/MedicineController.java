package ca.sheridancollege.pajaynar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.pajaynar.beans.Medicine;
import ca.sheridancollege.pajaynar.database.DatabaseAccess;


@Controller
public class MedicineController {
	
	@Autowired
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/aMedicine/{name}")
	public String showAMedicine(Model model, @PathVariable("name") String name) {
		System.out.println("Inside Medicine cotrollr - /aMedicine/name");
		model.addAttribute("medicine" , da.getMedicineByName(name).get(0));
		return "aMedicine";
	}
	
	@GetMapping("/allMedicines")
	public String showMedicines(Model model) {
		System.out.println("Inside medicine cotrollr - /allMedicines");
		model.addAttribute("medicineList" , da.getMedicineList());
		return "allMedicines";
	}
	
	@GetMapping("/newMedicine")
	public String newMedicine(Model model) {
		System.out.println("Inside Medicine cotrollr - /newMedicine");
		model.addAttribute("medicine" , new Medicine());
		return "newMedicine";
	} 
	
	@PostMapping("/addMedicine")
	public String addMedicine(Model model, @ModelAttribute Medicine medicine) {
		System.out.println("Inside Medicine cotrollr - /addMedicine");
		da.addMedicine(medicine);
		model.addAttribute("medicine",  new Medicine());
		return "newMedicine";
	}
}