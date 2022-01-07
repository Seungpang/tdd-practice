package chap07_1;

public class StubAutoDebitInfoRepositry implements AutoDebitInfoRepository {
	@Override
	public AutoDebitInfo findOne(String userId) {
		return null;
	}

	@Override
	public void save(AutoDebitInfo newInfo) {

	}
}
