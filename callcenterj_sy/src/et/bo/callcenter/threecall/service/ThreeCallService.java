/**
 * 
 */
package et.bo.callcenter.threecall.service;

/**
 * @author zf
 * 
 */
public interface ThreeCallService {

	/**
	 * �õ���������ͨ�������ߵ��б� ר��֮����:�ָ� key��value֮����,�ָ�
	 * 
	 * @return ר���б����Ϣ
	 */
	String getThreeList();
	
	/**
	 * �õ����е���Ϣ��������ϯ��ר�Һ����ߵ���Ϣ
	 * @param agentUser ר��
	 * @param threePort �������ĺ�
	 * @return �����ܵ��б����Ϣ
	 */
	String getAllInfo(String agentUser,String threePort);
	
	/**
	 * �õ����߶�Ӧ��ͨ����
	 * @param ip
	 * @return
	 */
	String getInnerPort(String ip);

}
