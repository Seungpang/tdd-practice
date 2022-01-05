package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {
	public LocalDate calculdateExpiryDate(PayData payData) {
		int addedMonths = getAddedMonths(payData);
		if (payData.getFirstBillingDate() != null) {
			return expiryDateUsingFirstBillingDate(payData, addedMonths);
		} else {
			return payData.getBillingDate().plusMonths(addedMonths);
		}
	}

	private int getAddedMonths(PayData payData) {
		int addMonths = 0;
		if (payData.getPayAmount() >= 100_000) {
			addMonths = (payData.getPayAmount() / 100_000) * 12;
		}
		return addMonths + (payData.getPayAmount() % 100_000) / 10_000;
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
