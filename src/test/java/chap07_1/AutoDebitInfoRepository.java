package chap07_1;

public interface AutoDebitInfoRepository {
	public AutoDebitInfo findOne(String userId);
	public void save(AutoDebitInfo newInfo);
}
