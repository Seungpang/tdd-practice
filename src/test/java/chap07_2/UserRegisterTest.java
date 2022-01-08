package chap07_2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {
	private UserRegister userRegister;
	private StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
	private MemoryUserRepository fakeRepository = new MemoryUserRepository();

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(stubWeakPasswordChecker, fakeRepository);
	}

	@DisplayName("약한 암호면 가입 실패")
	@Test
	void weakPassword() {
		stubWeakPasswordChecker.setWeak(true); //암호가 약하다고 응답하도록 설정

		assertThrows(WeakPasswordExceptions.class, () -> {
			userRegister.register("id", "pw", "email");
		});
	}

	@DisplayName("이미 같은 ID가 존재하면 가입 실패")
	@Test
	void dupIdExists() {
		//이미 같은 ID 존재하는 상황 만들기
		fakeRepository.save(new User("id", "pw1", "email@email.com"));

		assertThrows(DupIdException.class, () -> {
			userRegister.register("id", "pw2", "email");
		});
	}

	@DisplayName("같은 ID가 없으면 가입 성공함")
	@Test
	void noDupId_RegisterSuccess() {
		userRegister.register("id", "pw", "email");

		User savedUser = fakeRepository.findById("id"); //가입 결과 확인
		assertEquals("id", savedUser.getId());
		assertEquals("email", savedUser.getEmail());
	}
}
