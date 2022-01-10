package mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import chap07_2.EmailNotifier;
import chap07_2.UserRegister;

public class UserRegisterMockTest {
	private UserRegister userRegister;
	private EmailNotifier mockEmailNotifier = mock(EmailNotifier.class);

	@DisplayName("가입하면 메일을 전송함")
	@Test
	void whenRegisterThenSendMail() {
		userRegister.register("id", "pw", "email@email.com");

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		then(mockEmailNotifier)
			.should().sendRegisterEmail(captor.capture());

		String realEmail = captor.getValue();
		assertEquals("email@email.com", realEmail);
	}
}
