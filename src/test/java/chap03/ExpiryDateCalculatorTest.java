package chap03;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	@Test
	void 만원_납부하면_한달_뒤가_만료일이_됨() {
		assertExpiryDate(
			LocalDate.of(2022, 1, 1), 10_000,
			LocalDate.of(2022, 2, 1)
		);
		assertExpiryDate(
			LocalDate.of(2022, 3, 3), 10_000,
			LocalDate.of(2022, 4, 3))
		;
	}

	private void assertExpiryDate(LocalDate billingDate, int payAmount, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator cal = new ExpiryDateCalculator();
		LocalDate realExpiryDate = cal.calculdateExpiryDate(billingDate, payAmount);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}
}
