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
    private MedicinesDatabaseAccess mda; // Access for medicine-related database operations

    // Thread-safe list to store medicines temporarily
    private List<Medicine> medicineCart = new CopyOnWriteArrayList<>();

    /**
     * Displays all medicines available in the inventory.
     *
     * @param model The model object used to pass data to the view.
     * @return The name of the view displaying all medicines.
     */
    @GetMapping("/allMedicines")
    public String showMedicines(Model model) {
        System.out.println("Inside Medicine controller - /allMedicines");
        model.addAttribute("medicineList", mda.getMedicineList()); // Add all medicines to the model
        return "medicineView/allMedicines"; // Render the view for displaying all medicines
    }

    /**
     * Displays details of a specific medicine by its name.
     *
     * @param model The model object used to pass data to the view.
     * @param name  The name of the medicine to display.
     * @return The name of the view displaying the medicine details.
     */
    @GetMapping("/aMedicine/{name}")
    public String showAMedicine(Model model, @PathVariable("name") String name) {
        System.out.println("Inside Medicine controller - /aMedicine/name");
        model.addAttribute("medicine", mda.getMedicineByName(name)); // Add the selected medicine to the model
        System.out.println(mda.getMedicineByName(name)); // Log the medicine details
        return "medicineView/aMedicine"; // Render the view for displaying the medicine
    }

    /**
     * Displays the form for adding a new medicine to the inventory.
     *
     * @param model The model object used to pass data to the view.
     * @return The name of the view for adding a new medicine.
     */
    @GetMapping("/newMedicine")
    public String newMedicine(Model model) {
        System.out.println("Inside Medicine controller - /newMedicine");
        System.out.println("Flash attributes: " + model.getAttribute("message"));
        model.addAttribute("medicine", new Medicine()); // Add an empty Medicine object for form binding
        model.addAttribute("medicineInventory", mda.getMedicineList()); // Add existing inventory to the model
        return "medicineView/newMedicine"; // Render the view for adding a new medicine
    }

    /**
     * Updates an existing medicine's details in the inventory.
     *
     * @param redirectAttributes The object used to pass flash attributes to the redirected view.
     * @param medicine           The medicine details received from the form.
     * @return Redirect URL to display all medicines after updating.
     */
    @PostMapping("/editMedicine")
    public String editMedicine(RedirectAttributes redirectAttributes, @ModelAttribute Medicine medicine) {
        System.out.println("Inside Medicine controller - /editMedicine");
        mda.deleteMedicineByName(medicine.getName()); // Remove the existing medicine record
        mda.addMedicineToDatabase(medicine); // Add the updated medicine record
        redirectAttributes.addFlashAttribute("message", "Medicine updated successfully"); // Flash message
        return "redirect:/allMedicines"; // Redirect to the list of all medicines
    }

    /**
     * Adds a new medicine or updates an existing one in the inventory.
     *
     * @param redirectAttributes The object used to pass flash attributes to the redirected view.
     * @param medicine           The medicine details received from the form.
     * @return Redirect URL to display the form for adding medicines after insertion/update.
     */
    @PostMapping("/addMedicine")
    public String addMedicine(RedirectAttributes redirectAttributes, @ModelAttribute Medicine medicine) {
        System.out.println("Inside Medicine controller - /addMedicine");
        if (mda.getMedicineByName(medicine.getName()) != null) {
            System.out.println("Inside If block");
            mda.updateMedicineToDatabase(medicine); // Update the existing medicine record
        } else {
            System.out.println("Inside Else block");
            mda.addMedicineToDatabase(medicine); // Add the new medicine record
        }
        redirectAttributes.addFlashAttribute("message", "Medicine added successfully"); // Flash message
        return "redirect:/newMedicine"; // Redirect to the new medicine form
    }
}
