package chap07_2;

public class UserRegister {
	private WeakPasswordChecker passwordChecker;
	private UserRepository userRepository;

	public UserRegister(WeakPasswordChecker passwordChecker, UserRepository userRepository) {
		this.passwordChecker = passwordChecker;
		this.userRepository = userRepository;

	}

	public void register(String id, String pw, String email) {
		if (passwordChecker.checkPasswordWeak(pw)) {
			throw new WeakPasswordExceptions();
		}
		User user = userRepository.findById(id);
		if (user != null) {
			throw new DupIdException();
		}
		userRepository.save(new User(id, pw, email));
	}
}
