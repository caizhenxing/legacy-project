package excellence.common.key;

public interface KeyService {
	/**
	 * �õ���һ������ֵ
	 * 
	 * @param keyName
	 *            ����
	 * @return ��һ������ֵ
	 */
	public String getNext(String keyName);

	/**
	 * �õ��绰����ˮ��
	 * 
	 * @param keyName
	 *            ����
	 * @param type
	 *            ��� 1 InComing 2 OutComing
	 * @param phoneNum
	 *            �绰��
	 * @return ��һ���绰��ˮ�� ��:IC_22511711_key
	 */
	public String getNextPhone(String keyName, String type, String phoneNum);
}