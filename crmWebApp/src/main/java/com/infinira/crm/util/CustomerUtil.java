package com.infinira.crm.util;

import com.infinira.crm.model.Customer;

public class CustomerUtil {
	public static Customer.Gender getGender(String gender) {
		if (StringUtils.isBlank(gender)) {
			throw new RuntimeException("Gender should not be Null or Blank");
		}

		if ((gender.equalsIgnoreCase("M")) || (gender.equalsIgnoreCase("MALE"))) {
			return Customer.Gender.M;
		}

		if ((gender.equalsIgnoreCase("F")) || (gender.equalsIgnoreCase("FEMALE"))) {
			return Customer.Gender.F;
		}

		if ((gender.equalsIgnoreCase("O")) || (gender.equalsIgnoreCase("OTHERS"))) {
			return Customer.Gender.O;
		} else {
			throw new RuntimeException("Invalid Gender " + gender);
		}
	}
}
