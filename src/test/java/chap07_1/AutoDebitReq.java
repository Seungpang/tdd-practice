package chap07_1;

public class AutoDebitReq {
	private String userId;
	private String cardNumber;

	public AutoDebitReq(String userId, String cardNumber) {
		this.userId = userId;
		this.cardNumber = cardNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getUserId() {
		return userId;
	}
}
