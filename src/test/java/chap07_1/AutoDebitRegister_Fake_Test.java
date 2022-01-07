package chap07_1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoDebitRegister_Fake_Test {
	private AutoDebitRegister register;
	private StubCardNumberValidator cardNumberValidator;
	private MemoryAutoDebitInfoRepository repository;

	@BeforeEach
	void setUp() {
		cardNumberValidator = new StubCardNumberValidator();
		repository = new MemoryAutoDebitInfoRepository();
		register = new AutoDebitRegister(cardNumberValidator, repository);
	}

	@Test
	void alreadyRegistered_InfoUpdated() {
		repository.save(new AutoDebitInfo("user1", "111222333444", LocalDateTime.now()));

		AutoDebitReq req = new AutoDebitReq("user1", "123456789012");
		RegisterResult result = this.register.register(req);

		AutoDebitInfo saved = repository.findOne("user1");
		System.out.println(saved.getCardNumber());
		assertEquals("123456789012", saved.getCardNumber());
	}
}
