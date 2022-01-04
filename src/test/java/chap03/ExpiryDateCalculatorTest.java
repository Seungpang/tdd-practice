package chap03;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	@Test
	void 만원_납부하면_한달_뒤가_만료일이_됨() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2022, 1, 1))
				.payAmount(10_000)
				.build(),
			LocalDate.of(2022, 2, 1)
		);
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2022, 3, 3))
				.payAmount(10_000)
				.build(),
			LocalDate.of(2022, 4, 3)
		);
	}

	@Test
	void 납부일과_한달_뒤_일자가_같지_않음() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2022, 1, 31))
				.payAmount(10_000)
				.build(),
			LocalDate.of(2022, 2, 28)
		);
	}

	private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator cal = new ExpiryDateCalculator();
		LocalDate realExpiryDate = cal.calculdateExpiryDate(payData);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}
}
