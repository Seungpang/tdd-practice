package junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

public class SystemPropertyTest {
	@Test
	@EnabledIfSystemProperty(named = "java.vm.name", matches = ".*HotSpot.*")
	void openjdk() {
		Assertions.assertEquals(2, 1 + 1);
	}
}
