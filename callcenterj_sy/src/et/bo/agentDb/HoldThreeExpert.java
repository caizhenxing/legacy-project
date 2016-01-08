/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.util.Map;

import excellence.framework.base.dao.BaseDAO;
/**
 * <p>三方时更新EasyState_Info表 根据id设置three_state = '4'</p>
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
	 * <p>三方时更新EasyState_Info表 根据id设置three_state = '4'</p>
	 * @param String ymd 时间yyyy-MM-dd
	 * @param String agent 人员id
	 * @param Map paramMap 其他条件 
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
