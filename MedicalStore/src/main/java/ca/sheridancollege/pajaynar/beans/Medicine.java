package ca.sheridancollege.pajaynar.beans;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Medicine {
	private String name;
	private int qty;
	private String mfgLicNo;
	private String batchNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate mfgDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expDate;
	private Float price;
	private Boolean isActive;	
};