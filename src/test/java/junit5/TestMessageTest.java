package junit5;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMessageTest {

	@Test
	void messageTest() {
		List<Integer> ret = Arrays.asList(1, 2, 3, 4, 4, 6);
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6);
		for (int i = 0; i < expected.size(); i++) {
			Assertions.assertEquals(expected.get(i), ret.get(i), "ret[" + i + "]");
		}
	}
}
