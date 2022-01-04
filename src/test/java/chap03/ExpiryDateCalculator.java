package chap03;

import java.time.LocalDate;

public class ExpiryDateCalculator {
	public LocalDate calculdateExpiryDate(PayData payData) {
		int addedMonths = payData.getPayAmount() / 10_000;
		if (payData.getFirstBillingDate() != null) {
			LocalDate candidateExp = payData.getBillingDate().plusMonths(1);
			if (payData.getFirstBillingDate().getDayOfMonth() != candidateExp.getDayOfMonth()) {
				return candidateExp.withDayOfMonth(payData.getFirstBillingDate().getDayOfMonth());
			}
		}
		return payData.getBillingDate().plusMonths(addedMonths);
	}
}
