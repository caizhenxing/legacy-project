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
	 * 得到两个正在通话的外线的列表 专家之间以:分割 key和value之间以,分割
	 * 
	 * @return 专家列表的信息
	 */
	String getThreeList();
	
	/**
	 * 得到所有的信息，包括座席，专家和外线的信息
	 * @param agentUser 专家
	 * @param threePort 第三方的号
	 * @return 各个总的列表的信息
	 */
	String getAllInfo(String agentUser,String threePort);
	
	/**
	 * 得到内线对应的通道号
	 * @param ip
	 * @return
	 */
	String getInnerPort(String ip);

}
