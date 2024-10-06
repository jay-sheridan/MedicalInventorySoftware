package ca.sheridancollege.pajaynar.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
	private String name;
	private Long phoneNumber;
	private String address;
}
