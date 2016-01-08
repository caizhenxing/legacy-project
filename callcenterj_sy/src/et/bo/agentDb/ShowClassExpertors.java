/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.util.Map;

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * <p>来电弹屏窗口根据大类动态显示专家列表</p>
 * 
 * @version 2008-07-09
 * @author wangwenquan
 */
public class ShowClassExpertors extends AgentInfoBean {
	private BaseDAO dao = null;
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>来电弹屏窗口根据大类动态显示专家列表</p>
	 * @param String ymd 备用
	 * @param String agent 备用
	 * @param Map paramMap 放些需要的信息 
	 * @return String
	 */
	@Override
	public String getAgentInfo(String ymd, String agent, Map otherParam) {
		// TODO Auto-generated method stub
		String hql = "select custName from OperCustinfo a where a.expertType = '"+otherParam.get("expertType")+"' order by a.custName asc";
		
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		StringBuffer sb = new StringBuffer();
		try
		{
			Object[] oarr = dao.findEntity(mq);
			
			for(int i=0; i<oarr.length; i++)
			{
				//CcTalk ct = (CcTalk)oarr[0];
				String custName = (String)oarr[i];
				if(custName != null)
				{
					if(i>0)
					{
						sb.append(":"+custName);
					}
					else
					{
						sb.append(custName);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return sb.toString();
	}

}
