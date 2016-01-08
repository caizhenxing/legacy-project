/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.sql.RowSet;

import et.po.CcTalk;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>座席面板取取服务总时</p>
 * 
 * @version 2008-07-09
 * @author wangwenquan
 */
public class WorkSumTime extends AgentInfoBean {

	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>统计座席面板座席当日工作了多长时间</p>
	 * @param String ymd 备用
	 * @param String agent 备用
	 * @param Map paramMap 放些需要的信息 
	 * @return String 以分秒形式显示座席当日工作时间
	 */
	@Override
	public String getAgentInfo(String ymd, String agent,Map paramMap) {
		// TODO Auto-generated method stub
		//ymd = "2008-06-18";
		
		String dateStr = TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd");
		if(ymd!=null&&"".equals(ymd)==false)
		{
			dateStr = ymd;
		}
		String sql = "select sum(datediff(ss,a.answertime   ,   a.endtime   )) timeSS"
		+" from "
		+" ( "
		+"	select distinct crs,answertime,endtime  " 
		+"    from easy_cdr_agc " 
		+"    where agcworkid = '"+agent+"' and convert(varchar(10),trantime,120) = '"+dateStr+"' "
		+"	group by crs ,answertime,endtime "
		+" ) a, "
		+" easy_cdr_trk b "
		+" where a.crs = b.crs and b.dir = 1 "; 
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			rs.beforeFirst();
			while (rs.next()) {
				long count = rs.getLong("timeSS");
				return formatLongToTimeStr(count*1000);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 
	 * @param l 毫秒数 
	 * @return hour + "时" + minute  + "钟" + second  + "秒"
	 */
	public String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (hour + "时" + minute  + "分"
                + second  + "秒");
	 }


}
