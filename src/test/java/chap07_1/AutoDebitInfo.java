package chap07_1;

import java.time.LocalDateTime;

public class AutoDebitInfo {
	private String userId;
	private String cardNumber;
	private LocalDateTime created;

	public AutoDebitInfo(String userId, String cardNumber, LocalDateTime created) {
		this.userId = userId;
		this.cardNumber = cardNumber;
		this.created = created;
	}


	public void changeCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getUserId() {
		return userId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public LocalDateTime getCreated() {
		return created;
	}
}
