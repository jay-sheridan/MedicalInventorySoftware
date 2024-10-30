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
	
	@GetMapping("/allMedicines")
	public String showMedicines(Model model) {
		
		System.out.println("Inside medicine cotrollr - /allMedicines");
		model.addAttribute("medicineList" , mda.getMedicineList());
		return "medicineView/allMedicines";
	}

	@GetMapping("/aMedicine/{name}")
	public String showAMedicine(Model model, @PathVariable("name") String name) {
		System.out.println("Inside Medicine cotrollr - /aMedicine/name");
		model.addAttribute("medicine" , mda.getMedicineByName(name).get(0));
		return "medicineView/aMedicine";
	}
	
	@GetMapping("/newMedicine")
	public String newMedicine(Model model) {
		System.out.println("Inside Medicine cotrollr - /newMedicine");
		model.addAttribute("medicine" , new Medicine());
		return "medicineView/newMedicine";
	} 
	
	@PostMapping("/addMedicine")
	public String addMedicine(Model model, @ModelAttribute Medicine medicine) {
		System.out.println("Inside Medicine cotrollr - /addMedicine");
		mda.addMedicineToDatabase(medicine);
		model.addAttribute("medicine",  new Medicine());
		return "newMedicine";
	}
}