package junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TimeoutTest {

	@Test
	@Timeout(1)
	void sleep2seconds() throws InterruptedException {
		Thread.sleep(1000);
	}
}
