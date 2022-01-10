package mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;

public class GameGenMockTest {
	@Test
	void mockTest() {
		// 모의 객체 생성
		GameNumGen genMock = mock(GameNumGen.class);
		// 스텁 설정
		given(genMock.generate(GameLevel.EASY)).willReturn("123");

		//스텁 설정에 매칭되는 메서드 실행
		String num = genMock.generate(GameLevel.EASY);
		assertEquals("123", num);
	}

	@Test
	void mockThrowTest() {
		GameNumGen genMock = mock(GameNumGen.class);
		given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

		assertThrows(
			IllegalArgumentException.class,
			() -> genMock.generate(null)
		);
	}
}
