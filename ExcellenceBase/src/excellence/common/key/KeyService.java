package excellence.common.key;

public interface KeyService {
	/**
	 * 得到下一个主键值
	 * 
	 * @param keyName
	 *            表名
	 * @return 下一个主键值
	 */
	public String getNext(String keyName);

	/**
	 * 得到电话号流水号
	 * 
	 * @param keyName
	 *            表名
	 * @param type
	 *            类别 1 InComing 2 OutComing
	 * @param phoneNum
	 *            电话号
	 * @return 下一个电话流水号 例:IC_22511711_key
	 */
	public String getNextPhone(String keyName, String type, String phoneNum);
}