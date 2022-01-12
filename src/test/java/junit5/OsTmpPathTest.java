package junit5;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class OsTmpPathTest {

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void windowTmpPath() {
		Path tmpPath = Paths.get("C:\\Temp");
		Assertions.assertTrue(Files.isDirectory(tmpPath));
	}

	@Test
	@EnabledOnOs(OS.MAC)
	void macTmpPath() {
		Path tmpPath = Paths.get("/Users/ksl/Desktop/study");
		Assertions.assertTrue(Files.isDirectory(tmpPath));
	}
}
