package chap03;

import java.time.LocalDate;

public class ExpiryDateCalculator {
	public LocalDate calculdateExpiryDate(PayData payData) {
		return payData.getBillingDate().plusMonths(1);
	}
}
