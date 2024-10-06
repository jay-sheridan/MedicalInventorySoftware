package ca.sheridancollege.pajaynar.beans;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Invoice {
	
	private LocalDate date = LocalDate.now();
	private Customer customer;
	private List<Medicine> medicineList;
	
	@Setter
	private Float totalAmount = this.getTotalAmount(this.medicineList);
	
	private Float getTotalAmount(List<Medicine> medicineList) {
		Float totalAmount = 0f;
		if(medicineList !=null) {
		for(int i=0; i< medicineList.size(); i++) {
			totalAmount = totalAmount + medicineList.get(i).getPrice();
		}}
		
		return totalAmount;
	}
}
