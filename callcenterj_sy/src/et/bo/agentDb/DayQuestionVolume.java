/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.RowSet;

import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dao.BaseDAO;
/**
 * <p>�õ���ϯ�����Ϣ ����ѯ��ͳ��</p>
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
	 * <p>�õ���ϯ�����Ϣ ����ѯ��ͳ��</p>
	 * @param String ymd ʱ��yyyy-MM-dd
	 * @param String agent ��Աid
	 * @param Map paramMap �������� 
	 * @return String ajaxΪ��ϯ���������� ��ʾ��ϯ����Ѷ��
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
		//hql��ѯ��׼ ��ע��  
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
	 * �õ�����Ļ������ѯ��
	 * @param ymd
	 * @return 532291@1202@94@68@19@9@49 �������� �������� ������ѯ �г���ѯ ������ѯ �ȴ���Ļ�����Ϣ
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
