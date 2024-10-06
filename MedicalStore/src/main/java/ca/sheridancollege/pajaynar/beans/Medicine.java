package ca.sheridancollege.pajaynar.beans;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Medicine {
	
	private String name;
	private int qty;
	private String mfgLicNo;
	private String batchNo;
	private LocalDate mfgDate;
	private LocalDate expDate;
	private Float price;
};