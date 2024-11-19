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
		model.addAttribute("medicine" , mda.getMedicineByName(name));
		System.out.println(mda.getMedicineByName(name));
		return "medicineView/aMedicine";
	}
	
	@GetMapping("/newMedicine")
	public String newMedicine(Model model) {
		System.out.println("Inside Medicine cotrollr - /newMedicine");
		System.out.println("Flash attributes: " + model.getAttribute("message"));
		model.addAttribute("medicine" , new Medicine());
		model.addAttribute("medicineInventory", mda.getMedicineList());
		return "medicineView/newMedicine";
	} 
	
	@PostMapping("/editMedicine")
	public String editMedicine(RedirectAttributes redirectAttributes, @ModelAttribute Medicine medicine) {
        System.out.println("Inside Medicine cotrollr - /editMedicine");
        mda.deleteMedicineByName(medicine.getName());
        mda.addMedicineToDatabase(medicine);
        redirectAttributes.addFlashAttribute("message", "Medicine updated successfully");
        return "redirect:/allMedicines";
        
	}
	
	@PostMapping("/addMedicine")
	public String addMedicine(RedirectAttributes redirectAttributes, @ModelAttribute Medicine medicine) {
		System.out.println("Inside Medicine cotrollr - /addMedicine");
		if(mda.getMedicineByName(medicine.getName())!= null) {
			System.out.println("Inside If block");
			mda.updateMedicineToDatabase(medicine);
		} else {
			System.out.println("Inside Else block");
		mda.addMedicineToDatabase(medicine);
		}
		redirectAttributes.addFlashAttribute("message", "Medicine added successfully");
		return "redirect:/newMedicine";
	}
}