package chap07;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PaymethodRegisterTest {
	PaymethdRegReqValidator mockValidator = mock(PaymethdRegReqValidator.class);
	UserChecker mockUserChecker = Mockito.mock(UserChecker.class);
	CardValidityChecker mockCardChecker = mock(CardValidityChecker.class);
	
	private PaymethodRegister pr;
	private PayMethodRepository payMethodRepository = new MemoryPayMethodRepository();

	@BeforeEach
	void setUp() {
		pr = new PaymethodRegister(mockValidator, mockUserChecker, mockCardChecker, payMethodRepository);
	}

	@Test
	void invalidRegReq() {
		given(mockValidator.validate(Mockito.any(PaymethodRegReq.class))).willReturn(
			Arrays.asList("some error")
		);

		PaymethodRegReq regReq = new PaymethodRegReq(null, null, null);
		assertThatCode(() -> pr.register(regReq))
			.isInstanceOf(PayValidationException.class);
	}

	@Test
	void noUser() {
		given(mockValidator.validate(Mockito.any(PaymethodRegReq.class))).willReturn(
			Arrays.asList()
		);
		given(mockUserChecker.exists("111")).willReturn(false);

		PaymethodRegReq regReq = new PaymethodRegReq("111", null, null);
		assertThatCode(() -> pr.register(regReq))
			.isInstanceOf(NoUserException.class);
	}

	@Test
	void invalidCardNo() {
		given(mockValidator.validate(Mockito.any(PaymethodRegReq.class))).willReturn(
			Arrays.asList()
		);
		given(mockUserChecker.exists("111")).willReturn(true);
		given(mockCardChecker.checkValidity("1234")).willReturn(false);

		PaymethodRegReq regReq = new PaymethodRegReq("111", "1234", null);
		assertThatCode(() -> pr.register(regReq))
			.isInstanceOf(InvalidCardException.class);
	}

	@Test
	void dupCardNumber() {
		given(mockValidator.validate(Mockito.any(PaymethodRegReq.class))).willReturn(
			Arrays.asList()
		);
		given(mockUserChecker.exists("111")).willReturn(true);
		given(mockCardChecker.checkValidity("1234")).willReturn(true);
		//동일한 카드번호로 이미 등록되어 있는 상황
		payMethodRepository.save(new PayMethod(1, "111", "1234", "소유자"));
		PaymethodRegReq regReq = new PaymethodRegReq("111", "1234", "holder");
		assertThatCode(() -> pr.register(regReq))
			.isInstanceOf(DupCardNoException.class);
	}

	@Test
	void registered() {
		given(mockValidator.validate(Mockito.any(PaymethodRegReq.class))).willReturn(
			Arrays.asList()
		);
		given(mockUserChecker.exists("111")).willReturn(true);
		given(mockCardChecker.checkValidity("1234")).willReturn(true);
		PaymethodRegReq regReq = new PaymethodRegReq("111", "1234", "holder");

		Integer id = pr.register(regReq);
		assertThat(id).isNotNull();

		PayMethod pay = payMethodRepository.findById(id);
		assertThat(pay.getCardNo()).isEqualTo("1234");
		assertThat(pay.getUserId()).isEqualTo("111");
		assertThat(pay.getHolder()).isEqualTo("holder");
	}

	private class PaymethdRegReqValidator {
		public List<String> validate(PaymethodRegReq regReq) {
			return null;
		}
	}

	private class PaymethodRegister {

		private PaymethdRegReqValidator validator;
		private UserChecker userChecker;
		private CardValidityChecker cardValidityChecker;
		private PayMethodRepository payMethodRepository;

		public PaymethodRegister(PaymethdRegReqValidator validator, UserChecker userChecker,
			CardValidityChecker cardValidityChecker, PayMethodRepository payMethodRepository) {
			this.validator = validator;
			this.userChecker = userChecker;
			this.cardValidityChecker = cardValidityChecker;
			this.payMethodRepository = payMethodRepository;
		}

		public Integer register(PaymethodRegReq regReq) {
			List<String> errors = validator.validate(regReq);
			if (!errors.isEmpty()) {
				throw new PayValidationException();
			}
			if (!userChecker.exists(regReq.getUserId())) {
				throw new NoUserException();
			}
			if (!cardValidityChecker.checkValidity(regReq.getCardNo())) {
				throw new InvalidCardException();
			}
			PayMethod pay = payMethodRepository.findByUserIdAndCardNo(regReq.getUserId(), regReq.getCardNo());
			if (pay != null) {
				throw new DupCardNoException();
			}
			PayMethod newPay = new PayMethod(null, regReq.getUserId(), regReq.getCardNo(), regReq.getHolderName());
			payMethodRepository.save(newPay);
			return newPay.getId();
		}
	}

	private class PaymethodRegReq {
		private String userId;
		private String cardNo;
		private String holderName;

		public PaymethodRegReq(String userId, String cardNo, String holderName) {
			this.userId = userId;
			this.cardNo = cardNo;
			this.holderName = holderName;
		}

		public String getUserId() {
			return userId;
		}

		public String getCardNo() {
			return cardNo;
		}

		public String getHolderName() {
			return holderName;
		}
	}

	private class PayValidationException extends RuntimeException {
	}
}
