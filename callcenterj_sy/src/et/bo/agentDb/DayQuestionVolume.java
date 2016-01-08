/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.RowSet;

import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dao.BaseDAO;
/**
 * <p>得到座席面板信息 日资询量统计</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class DayQuestionVolume extends AgentInfoBean {
	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>得到座席面板信息 日资询量统计</p>
	 * @param String ymd 时间yyyy-MM-dd
	 * @param String agent 人员id
	 * @param Map paramMap 其他条件 
	 * @return String ajax为座席面板呈现数据 显示座席日资讯量
	 */
	@Override
	public String getAgentInfo(String ymd, String agent,Map paramMap) {
		// TODO Auto-generated method stub
		//dao = (BaseDAO)SpringRunningContainer.getInstance().getBean("BaseDao");
		//do something
		String dateStr = TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd");
		if(ymd!=null&&"".equals(ymd)==false)
		{
			dateStr = ymd;
		}
//		String sql = "select count(*) sumNum " 
//			+" from "
//			+" ( "
//			+" 	select distinct crs " 
//			+"  from easy_cdr_agc "
//			+"  where agcworkid = '"+agent+"' and convert(varchar(10),trantime,120) = '"+dateStr+"' "
//			+" 	group by crs "
//			+" ) a, "
//			+" easy_cdr_trk b "
//			+" where a.crs = b.crs and b.dir = 1 ";
		String sql = "select count(*) sumNum from Oper_Question oq " 
			+" where oq.answer_agent = '"+agent+"' and  Convert(varchar(10),oq.addtime,120) like '"+dateStr+"%'";
		//System.out.println(sql);
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			rs.beforeFirst();
			while (rs.next()) {
				int count = rs.getInt("sumNum");
				return count+"";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//hql查询不准 先注释  
//		String hql = "select count(*) from CcTalk a where Convert(varchar(10),a.touchBegintime,120) like '"+ymd+"%' and a.respondent = '"+agent+"'";
//		MyQuery mq = new MyQueryImpl();
//		mq.setHql(hql);
//		Object[] oarr = dao.findEntity(mq);
//		String dayQuestionNum = "0";
//		if(oarr.length>0)
//		{
//			dayQuestionNum = oarr[0].toString();
//		}
		return  "0";
	}
	/**
	 * 得到大屏幕各个质询量
	 * @param ymd
	 * @return 532291@1202@94@68@19@9@49 话务总量 当日总量 生产质询 市场质询 政策质询 等大屏幕相关信息
	 */
	public String getScreenQuestions(String ymd)
	{
//		 TODO Auto-generated method stub
		String sql = "select [dbo].[screen_getHuawu]('"+ymd+"') as questions";
		//System.out.println(sql);
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			rs.beforeFirst();
			while (rs.next()) {
				return rs.getString("questions");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  "";
	}
	public static void main(String [] args)
	{
		DayQuestionVolume dq = 	(DayQuestionVolume)SpringContainer.getInstance().getBean("DayQuestionVolumeService");
		//dq.getAgentInfo("2008-06-12","13");
		//System.out.println(dq.getAgentInfo("2008-06-12","13",new HashMap()));
	}

}
