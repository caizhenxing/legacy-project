/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb;

import java.util.Map;

import excellence.framework.base.dao.BaseDAO;
/**
 * <p>����ʱ����EasyState_Info�� ����id����three_state = '4'</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class HoldThreeExpert extends AgentInfoBean {

	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>����ʱ����EasyState_Info�� ����id����three_state = '4'</p>
	 * @param String ymd ʱ��yyyy-MM-dd
	 * @param String agent ��Աid
	 * @param Map paramMap �������� 
	 * @return String 
	 */
	@Override
	public String getAgentInfo(String ymd, String agent, Map otherParam) {
		// TODO Auto-generated method stub
		String lineNum = (String)otherParam.get("lineNum");
		if(lineNum!=null&&!"".equals(lineNum))
		{
			String sql = "UPDATE    EasyState_Info SET    three_state = '4', three_port = id where id = '"+lineNum+"'";

			dao.execute(sql);
			//System.out.println(sql);
		}
		return "";
	}

}
