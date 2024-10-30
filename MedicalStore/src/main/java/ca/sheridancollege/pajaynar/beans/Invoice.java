package ca.sheridancollege.pajaynar.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Invoice {
	private static int invoiceId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate invoiceCreatedDate = LocalDate.now();
	private LocalTime invoiceCreatedTime = LocalTime.now();
	private Long customerPhone;
	private List<Medicine> medicineList = new ArrayList<>();
	private Float totalInvoiceAmount = 0f;
	private Customer customer;
	
	
	public void updateTotalInvoiceAmount(Medicine medicine) {
		totalInvoiceAmount += medicine.getPrice();
	}
	
	public List<Medicine> getProcessedMedicineList() {
	        Map<String, Medicine> medicineMap = new HashMap<>();
	        
	        // Iterate through the medicine list and merge quantities
	          for (Medicine medicine : medicineList) {
	            String nameAndBatch = medicine.getName()+medicine.getBatchNo();

	            if (medicineMap.containsKey(nameAndBatch)) {
	                // If the medicine is already in the map, merge the quantities
	                Medicine existingMedicine = medicineMap.get(nameAndBatch);
	                existingMedicine.setQty(existingMedicine.getQty() + medicine.getQty());
	                existingMedicine.setPrice(existingMedicine.getPrice()+medicine.getPrice());
	            } else {
	                // If it's not in the map, add it
	                medicineMap.put(nameAndBatch, createObject(medicine));
	            }
	        }

	        // Return a new list of unique medicines with merged quantities
	        return new ArrayList<>(medicineMap.values());
	    }
	
	private static Medicine createObject(Medicine medicine) {
		Medicine tempMedicine = new Medicine();
		tempMedicine.setName(medicine.getName());
		tempMedicine.setQty(medicine.getQty());
		tempMedicine.setMfgLicNo(medicine.getMfgLicNo());
		tempMedicine.setBatchNo(medicine.getBatchNo());
		tempMedicine.setMfgDate(medicine.getMfgDate());
		tempMedicine.setExpDate(medicine.getExpDate());
		tempMedicine.setPrice(medicine.getPrice());
		return tempMedicine;
	}
	
	public LocalTime getRoundedInvoiceCreatedTime() {
        if (invoiceCreatedTime != null) {
            // Calculate the rounded seconds
            int seconds = invoiceCreatedTime.getSecond();
            int nanoAdjustment = invoiceCreatedTime.getNano() / 1_000_000_000; // Convert nanoseconds to seconds
            
            // Round seconds based on nanoseconds
            if (nanoAdjustment >= 5) { // If nanoseconds are 5 or more, round up
                seconds += 1;
            }
            // Ensure seconds stay within 0-59 range
            seconds = seconds % 60;

            return invoiceCreatedTime.withSecond(seconds).withNano(0);
        }
        return null; // or throw an exception if appropriate
    }
}
