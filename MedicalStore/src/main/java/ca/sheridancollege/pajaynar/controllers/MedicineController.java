package ca.sheridancollege.pajaynar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@GetMapping("/newMedicine")
	public String newMedicine(Model model) {
		model.addAttribute("medicine" , new Medicine());
		return "newMedicine";
	} 
	
	@PostMapping("/addMedicine")
	public String addMedicine(Model model, @ModelAttribute Medicine medicine) {
		da.addMedicine(medicine);
		model.addAttribute("medicine",  new Medicine());
		return "newMedicine";
	}
}