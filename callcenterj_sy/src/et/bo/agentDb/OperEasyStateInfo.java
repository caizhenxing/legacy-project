/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb;

import java.util.Map;

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * <p>����ͨ��ʱ��ר����Ϣupdate�����ݿ�</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class OperEasyStateInfo extends AgentInfoBean {
	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>����ͨ��ʱ��ר����Ϣupdate�����ݿ�</p>
	 * @param String ymd ����
	 * @param String agent ����
	 * @param Map paramMap ��Щ��Ҫ����Ϣ 
	 * @return String
	 */
	@Override
	public String getAgentInfo(String ymd, String agent, Map paramMap) {
		// TODO Auto-generated method stub
		String threeCall = (String)paramMap.get("threeCall");
		String threePort = (String)paramMap.get("threePort");
		String incommingCall = (String)paramMap.get("incommingCall");
		//System.out.println(threeCall+":"+threePort+":"+incommingCall);
		if(incommingCall!=null && !"".equals(incommingCall.trim()))
		{
			String updateSql = "update EasyState_info set three_call = '"+threeCall+"', three_port = '"+threePort+"' where incomming_call = '"+incommingCall+"'";
			//System.out.println(updateSql);
			dao.execute(updateSql);
		}
		return null;
	}

}
