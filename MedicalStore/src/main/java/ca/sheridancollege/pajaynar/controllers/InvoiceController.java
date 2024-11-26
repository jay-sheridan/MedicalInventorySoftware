package ca.sheridancollege.pajaynar.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.pajaynar.beans.Customer;
import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.beans.Medicine;
import ca.sheridancollege.pajaynar.database.CustomerDatabaseAccess;
import ca.sheridancollege.pajaynar.database.InvoicesDatabaseAccess;
import ca.sheridancollege.pajaynar.database.MedicinesDatabaseAccess;

@Controller
public class InvoiceController {

    @Autowired
    private InvoicesDatabaseAccess ida; // Access for invoice-related database operations

    @Autowired
    private MedicinesDatabaseAccess mda; // Access for medicine-related database operations

    @Autowired
    private CustomerDatabaseAccess cda; // Access for customer-related database operations

    // Store ongoing invoices in memory (mapped by invoice ID)
    Map<Integer, Invoice> onGoingInvoices = new HashMap<>();

    private Invoice invoice; // Currently active invoice instance

    /**
     * Creates a new invoice and redirects to the invoice form for editing.
     *
     * @param model The model object used to pass data to the view.
     * @return Redirect URL to render the new invoice form.
     */
    @GetMapping("/newInvoice")
    public String newInvoice(Model model) {
        System.out.println("Inside Invoice controller - /newInvoice");
        invoice = new Invoice();
        invoice.setInvoiceId(onGoingInvoices.size() + 1); // Assign a new invoice ID
        onGoingInvoices.put(invoice.getInvoiceId(), invoice); // Add to ongoing invoices map
        return "redirect:/renderInvoiceForm/" + invoice.getInvoiceId(); // Redirect to the form
    }

    /**
     * Renders the invoice form for a specific invoice ID.
     *
     * @param model     The model object used to pass data to the view.
     * @param invoiceId The ID of the invoice to render.
     * @return The name of the view for editing the invoice.
     */
    @GetMapping("/renderInvoiceForm/{invoiceId}")
    public String renderInvoiceForm(Model model, @PathVariable("invoiceId") int invoiceId) {
        model.addAttribute("invoice", onGoingInvoices.get(invoiceId)); // Add the invoice to the model
        model.addAttribute("medicine", new Medicine()); // Add an empty medicine for form binding
        model.addAttribute("medicineInventory", mda.getMedicineList()); // Add the medicine inventory
        return "invoiceView/newInvoice"; // Render the invoice form
    }

    /**
     * Adds a medicine to the cart in the ongoing invoice.
     *
     * @param medicine  The medicine details received from the form.
     * @param invoiceId The ID of the invoice to update.
     * @return Redirect URL to refresh the invoice form.
     */
    @PostMapping("/addMedicineToCart/{invoiceId}")
    public String addMedicineToCart(@ModelAttribute Medicine medicine, @PathVariable("invoiceId") int invoiceId) {
        System.out.println(medicine.getMfgDate());
        onGoingInvoices.get(invoiceId).getMedicineList().add(medicine); // Add medicine to the invoice
        onGoingInvoices.get(invoiceId).updateTotalInvoiceAmount(medicine); // Update invoice total
        mda.updateMedicine(medicine); // Update the medicine inventory in the database
        return "redirect:/renderInvoiceForm/" + invoiceId; // Redirect to the updated invoice form
    }

    /**
     * Allows editing of a specific medicine in the cart.
     *
     * @param model      The model object used to pass data to the view.
     * @param invoiceId  The ID of the invoice containing the medicine.
     * @param name       The name of the medicine to edit.
     * @return The name of the view for editing the invoice.
     */
    @GetMapping("/editMedicineToCart/{invoiceId}/{medicineName}")
    public String editMedicineToCart(Model model, @PathVariable("invoiceId") int invoiceId, @PathVariable("medicineName") String name) {
        System.out.println("Inside Invoice controller - /editMedicineToCart - GET");
        invoice = onGoingInvoices.get(invoiceId);
        Medicine medicine = invoice.getMedicineList().stream()
                                   .filter(m -> m.getName().equals(name))
                                   .findFirst()
                                   .orElse(null);
        if (medicine != null) {
            invoice.getMedicineList().remove(medicine); // Remove the medicine from the invoice
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("medicine", medicine); // Pass the medicine for editing
        model.addAttribute("medicineInventory", mda.getMedicineList()); // Add inventory to model
        return "invoiceView/newInvoice";
    }

    /**
     * Deletes a specific medicine from the cart in the ongoing invoice.
     *
     * @param model      The model object used to pass data to the view.
     * @param invoiceId  The ID of the invoice to update.
     * @param name       The name of the medicine to delete.
     * @return Redirect URL to refresh the invoice form.
     */
    @GetMapping("/deleteMedicineFromCart/{invoiceId}/{medicineName}")
    public String deleteMedicineFromCart(Model model, @PathVariable("invoiceId") int invoiceId, @PathVariable("medicineName") String name) {
        System.out.println("Inside Invoice controller - /deleteMedicineFromCart - GET");
        onGoingInvoices.get(invoiceId).getMedicineList().removeIf(m -> m.getName().equals(name)); // Remove the medicine
        return "redirect:/renderInvoiceForm/" + invoiceId; // Redirect to the updated invoice form
    }

    /**
     * Generates a final invoice and associates it with a customer's phone number.
     *
     * @param model       The model object used to pass data to the view.
     * @param phoneNumber The phone number of the customer.
     * @return The final invoice view.
     */
    @GetMapping("/generateInvoice/{phoneNumber}")
    public String generateInvoice(Model model, @PathVariable("phoneNumber") Long phoneNumber) {
        invoice.setCustomerPhone(phoneNumber); // Associate the invoice with the customer's phone
        ida.generateInvoice(invoice); // Save the invoice in the database
        return finalInvoice(model, invoice); // Render the final invoice view
    }

    /**
     * Displays all invoices in the system.
     *
     * @param model The model object used to pass data to the view.
     * @return The name of the view displaying the invoice list.
     */
    @GetMapping("/getInvoices")
    public String getInvoices(Model model) {
        System.out.println("Inside Invoice controller - /getInvoices");
        model.addAttribute("invoices", ida.getInvoiceList()); // Add all invoices to the model
        return "invoiceView/invoices"; // Render the invoice list view
    }

    /**
     * Displays a specific invoice by its ID.
     *
     * @param model The model object used to pass data to the view.
     * @param id    The ID of the invoice to display.
     * @return The name of the view displaying the invoice details.
     */
    @GetMapping("/viewInvoice/{id}")
    public String getInvoiceById(Model model, @PathVariable("id") Long id) {
        System.out.println("Inside Invoice controller - /viewInvoice/{id}");
        model.addAttribute("invoice", ida.getInvoiceById(id)); // Add invoice details
        model.addAttribute("customer", cda.getCustomerByPhoneNumber(ida.getInvoiceById(id).getCustomerPhone()).get(0)); // Add customer details
        return "invoiceView/invoice";
    }

    /**
     * Helper method to finalize the invoice and pass customer details to the model.
     *
     * @param model   The model object used to pass data to the view.
     * @param invoice The invoice to finalize.
     * @return The name of the view for the finalized invoice.
     */
    private String finalInvoice(Model model, Invoice invoice) {
        System.out.println("Inside Invoice controller - /finalInvoice");
        model.addAttribute("invoice", this.invoice);
        model.addAttribute("customer", cda.getCustomerByPhoneNumber(invoice.getCustomerPhone()).get(0));
        return "invoiceView/finalInvoice"; // Render the final invoice view
    }
}
