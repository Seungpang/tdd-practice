package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {
	public LocalDate calculdateExpiryDate(PayData payData) {
		int addedMonths = payData.getPayAmount() == 100_000 ? 12 : payData.getPayAmount() / 10_000;
		if (payData.getFirstBillingDate() != null) {
			return expiryDateUsingFirstBillingDate(payData, addedMonths);
		} else {
			return payData.getBillingDate().plusMonths(addedMonths);
		}
	}

	private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonths) {
		LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);
		if (isSameDayOfMonth(payData, candidateExp)) {
			final int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
			final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
			if (dayLenOfCandiMon < dayOfFirstBilling) {
				return candidateExp.withDayOfMonth(dayLenOfCandiMon);
			}
			return candidateExp.withDayOfMonth(dayOfFirstBilling);
		} else {
			return candidateExp;
		}
	}

	private boolean isSameDayOfMonth(PayData payData, LocalDate candidateExp) {
		return payData.getFirstBillingDate().getDayOfMonth() != candidateExp.getDayOfMonth();
	}

	private int lastDayOfMonth(LocalDate candidateExp) {
		return YearMonth.from(candidateExp).lengthOfMonth();
	}
}
