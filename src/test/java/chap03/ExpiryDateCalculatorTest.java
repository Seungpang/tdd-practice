package chap03;

import static java.time.LocalDate.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	@Test
	void 만원_납부하면_한달_뒤가_만료일이_됨() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 1, 1))
				.payAmount(10_000)
				.build(),
			of(2022, 2, 1)
		);
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 3, 3))
				.payAmount(10_000)
				.build(),
			of(2022, 4, 3)
		);
	}

	@Test
	void 납부일과_한달_뒤_일자가_같지_않음() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 1, 31))
				.payAmount(10_000)
				.build(),
			of(2022, 2, 28)
		);
	}

	private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator cal = new ExpiryDateCalculator();
		LocalDate realExpiryDate = cal.calculdateExpiryDate(payData);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}

	@Test
	void 첫_납부일과_만료일_일자가_다를때_만원_납부() {
		PayData payData = PayData.builder()
			.firstBillingDate(of(2022, 1, 31))
			.billingDate(of(2022, 2, 28))
			.payAmount(10_000)
			.build();

		assertExpiryDate(payData, of(2022, 3, 31));

		PayData payData2 = PayData.builder()
			.firstBillingDate(of(2022, 1, 30))
			.billingDate(of(2022, 2, 28))
			.payAmount(10_000)
			.build();

		assertExpiryDate(payData2, of(2022, 3, 30));

		PayData payData3 = PayData.builder()
			.firstBillingDate(of(2022, 5, 31))
			.billingDate(of(2022, 6, 30))
			.payAmount(10_000)
			.build();

		assertExpiryDate(payData3, of(2022, 7, 31));
	}

	@Test
	void 이만원_이상_납부하면_비례해서_만료일_계산() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 3, 1))
				.payAmount(20_000)
				.build(),
			of(2022, 5, 1)
		);
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 3, 1))
				.payAmount(30_000)
				.build(),
			of(2022, 6, 1)
		);
	}

	@Test
	void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
		assertExpiryDate(
			PayData.builder()
				.firstBillingDate(of(2022, 1, 31))
				.billingDate(of(2022, 2, 28))
				.payAmount(20_000)
				.build(),
			of(2022, 4, 30)
		);
	}

	@Test
	void 십만원을_납부하면_1년_제공() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 1, 28))
				.payAmount(100_000)
				.build(),
			of(2023, 1, 28)
		);
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2020, 2, 29))
				.payAmount(100_000)
				.build(),
			of(2021, 2, 28)
		);
		assertExpiryDate(
			PayData.builder()
				.billingDate(of(2022, 1, 1))
				.payAmount(130_000)
				.build(),
			of(2023, 4, 1)
		);
	}
}
