/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * <p>得到座席面板当前座席当日来电信息</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class IncommingNote extends AgentInfoBean {
	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>得到座席面板当前座席当日来电信息</p>
	 * @param String ymd 时间yyyy-MM-dd
	 * @param String agent 人员id
	 * @param Map paramMap 其他条件 
	 * @return String 将座席来电信息以":"分隔供js解析
	 */
	@Override
	public String getAgentInfo(String ymd, String agent,Map paramMap) {
		// TODO Auto-generated method stub
		//String hql = "select distinct phoneNum from CcTalk a where Convert(varchar(10),a.touchBegintime,120) like '"+ymd+"%' and a.respondent = '"+agent+"'";
//		String hql = "select phoneNum from CcTalk a where Convert(varchar(10),a.touchBegintime,120) like '"+ymd+"%' and a.respondent = '"+agent+"'";
//		MyQuery mq = new MyQueryImpl();
//		mq.setHql(hql);
//		StringBuffer sb = new StringBuffer();
//		try
//		{
//			//System.out.println("dao is :"+ dao);
//			Object[] oarr = dao.findEntity(mq);
//			
//			for(int i=0; i<oarr.length; i++)
//			{
//				//CcTalk ct = (CcTalk)oarr[0];
//				String curNum = (String)oarr[i];
//				if(curNum != null)
//				{
//					if(i>0)
//					{
//						sb.append(":"+curNum);
//					}
//					else
//					{
//						sb.append(curNum);
//					}
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		StringBuffer sb = new StringBuffer();
		String dateStr = TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd");
		if(ymd!=null&&"".equals(ymd)==false)
		{
			dateStr = ymd;
		}
		//from CcTalk where 1=1  and touchBegintime >= '2009-02-23' and respondent = '104' and respondent is not null  order by touchBegintime desc
//		String sql = "select b.caller"
//		 +" from " 
//		 +" ( " 
//		 +"	select distinct crs " 
//		 +"  from easy_cdr_agc " 
//		 +" where agcworkid = '"+agent+"' and convert(varchar(10),trantime,120) = '"+dateStr+"' " 
//		 +"	group by crs " 
//		 +" ) a, " 
//		 +" easy_cdr_trk b " 
//		 +" where a.crs = b.crs and b.dir = 1 and isnull(caller,'') <> '' ";
		String sql = "select phone_num caller from Cc_Talk where 1=1  and convert(varchar(10),touch_Begintime,120)= '"+dateStr+"' and respondent = '"+agent+"' and respondent is not null  order by touch_Begintime desc";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		int i = 0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				String caller = rs.getString("caller");
				if(i>0)
				{
					sb.append(":"+caller);
				}
				else
				{
					sb.append(caller);
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static void main(String [] args)
	{
		IncommingNote dq = 	(IncommingNote)SpringContainer.getInstance().getBean("IncommingNoteService");
		//dq.getAgentInfo("2008-06-12","13");
		//System.out.println(dq.getAgentInfo("2008-06-12","13",new HashMap()));
	}
}
