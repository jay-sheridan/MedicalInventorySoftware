package ca.sheridancollege.pajaynar.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Invoice {
	
	private LocalDate invoiceCreatedDate;
	private LocalTime invoiceCreatedTime;
	private Long customerPhone;
	private List<Medicine> medicineList;
	private Float totalMedicineAmount = this.getTotalAmount();
	
	
	private Float getTotalAmount() {
		System.out.println("Inside GetTotalAmount method");
		System.out.println(medicineList);
		Float totalAmount = 0f;
		if(this.medicineList == null||this.medicineList.isEmpty()) {
			System.out.println("Inside if");
			return 0.0f;
		} 
		else {
			System.out.println("Inside else");
			for(int i=0; i< medicineList.size(); i++) {
				totalAmount = totalAmount + medicineList.get(i).getPrice();
			}
		}
		return totalAmount;
	}
}
