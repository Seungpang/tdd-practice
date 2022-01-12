package junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("integration")
public class TagTest {

	@Test
	void plusTest() {
		Assertions.assertEquals(1+1, 2);
	}

	@Tag("very-slow")
	@Test
	void verySlow() throws InterruptedException {
		int result = someVerySlowOp();
		Assertions.assertEquals(result, 0);
	}

	private int someVerySlowOp() throws InterruptedException {
		Thread.sleep(5000);
		return 0;
	}
}
