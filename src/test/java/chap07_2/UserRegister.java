package chap07_2;

public class UserRegister {
	private WeakPasswordChecker passwordChecker;

	public UserRegister(WeakPasswordChecker passwordChecker) {
		this.passwordChecker = passwordChecker;
	}

	public void register(String id, String pw, String email) {
		if (passwordChecker.checkPasswordWeak(pw)) {
			throw new WeakPasswordExceptions();
		}
	}
}
