package mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class VoidMethodStubTest {
	@Test
	void voidMethodWillThrowTest() {
		List<String> mockList = mock(List.class);
		willThrow(UnsupportedOperationException.class)
			.given(mockList)
			.clear();

		assertThrows(
			UnsupportedOperationException.class,
			() -> mockList.clear()
		);
	}
}
