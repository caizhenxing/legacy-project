/**
 * 
 */
package et.bo.callcenter.cclog.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.CcMain;
import et.po.CcTalk;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author Administrator
 *
 */
public class CclogMainHelp {
	private String addoneDate(String endTime) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		ca.add(ca.DATE, 1);
		date = ca.getTime();
		endTime = TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss");
		return endTime;
	}

	private String addHMS(String stime) {
		String hmsTime = "";
		hmsTime = stime + " 00:00:00";
		return hmsTime;
	}

	public MyQuery cclogQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// end
		StringBuilder hql = new StringBuilder();
	
		hql.append("select c from CcMain c where c.id = c.id");
		if (!dto.get("telNum").toString().equals("")) {
			hql.append(" and c.telNum like '" + "%"
					+ dto.get("telNum").toString() + "%" + "'");
		}

		if (!dto.get("ringBegintime").toString().equals("")) {
			hql.append(" and c.ringBegintime >= '"
					+ addHMS(dto.get("ringBegintime").toString()) + "'");
		}
		if (!dto.get("processEndtime").toString().equals("")) {
			hql.append(" and c.processEndtime <='"
					+ addoneDate(dto.get("processEndtime").toString()) + "'");
		}
		

		hql.append(" and c.isDelete = '0' ");
		hql.append(" order by c.ringBegintime desc");
//		if (pi.getFieldAsc() == null && pi.getFieldDesc() == null) {
//			hql.append(" order by c.ringBegintime desc");
//		}
//		if (pi.getFieldAsc() != null) {
//			hql.append(" order by " + pi.getFieldAsc() + " asc");
//		}
//		if (pi.getFieldDesc() != null) {
//			hql.append(" order by " + pi.getFieldDesc() + " desc");
//		}
		hql.append(" order by c.ringBegintime desc ");
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	public MyQuery cclogQuerySize(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// end
		StringBuilder hql = new StringBuilder();
		DetachedCriteria dc = DetachedCriteria.forClass(CcMain.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		//hql.append("select c from CcMain c where c.id = c.id");
		if (!dto.get("telNum").toString().equals("")) {
//			hql.append(" and c.telNum like '" + "%"
//					+ dto.get("telNum").toString() + "%" + "'");
			dc.add(Restrictions.ilike("telNum", dto.get("telNum").toString()));
		}

		if (!dto.get("ringBegintime").toString().equals("")) {
//			hql.append(" and c.ringBegintime >= '"
//					+ addHMS(dto.get("ringBegintime").toString()) + "'");
			try {
				dc.add(Restrictions.ge("ringBegintime", sdf.parse(addHMS(dto.get("ringBegintime").toString()))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!dto.get("processEndtime").toString().equals("")) {
//			hql.append(" and c.processEndtime <='"
//					+ addoneDate(dto.get("processEndtime").toString()) + "'");
			try {
				dc.add(Restrictions.le("processEndtime", sdf.parse(addoneDate(dto.get("processEndtime").toString()))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dc.add(Restrictions.eq("isDelete", "0"));

		//hql.append(" and c.isDelete = '0' ");
//		hql.append(" order by c.ringBegintime desc");
//		if (pi.getFieldAsc() == null && pi.getFieldDesc() == null) {
//			hql.append(" order by c.ringBegintime desc");
//		}
//		if (pi.getFieldAsc() != null) {
//			hql.append(" order by " + pi.getFieldAsc() + " asc");
//		}
//		if (pi.getFieldDesc() != null) {
//			hql.append(" order by " + pi.getFieldDesc() + " desc");
//		}

		mq.setDetachedCriteria(dc);

		return mq;
	}

	/**
	 * 得到今天数据信息
	 * 
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public MyQuery queryToday(IBaseDTO dto, PageInfo pi, String begintime,
			String opernum) {
		MyQuery mq = new MyQueryImpl();
//		StringBuilder hql = new StringBuilder();
//	
//		hql.append("select c from CcLog c,PoliceCallin p where c.id=p.id and ");
//		hql.append("c.beginTime>=to_date('" + begintime
//				+ "','yyyy-MM-dd hh24:mi:ss') and ");
//		hql.append("p.operator='" + opernum + "' ");
//		hql.append("order by c.beginTime desc");
//		mq.setHql(hql.toString());
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * 得到今天数据信息
	 * 
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public MyQuery queryTodaySize(IBaseDTO dto, PageInfo pi, String begintime,
			String opernum) {
		MyQuery mq = new MyQueryImpl();
//		DetachedCriteria dc = DetachedCriteria.forClass(CcLog.class);
//		// Calendar c = Calendar.getInstance();
//		// c.setTime(new Date());
//		// c.set(Calendar.HOUR, 0);
//		// c.set(Calendar.MINUTE, 0);
//		// c.set(Calendar.SECOND, 0);
//		// Date beginTime = c.getTime();
//		// c.add(Calendar.DATE, 1);
//		// Date endTime=c.getTime();
//		/*
//		 * dc.add(Expression.between("beginTime", beginTime, endTime));
//		 * dc.addOrder(Order.desc("beginTime")); mq.setDetachedCriteria(dc);
//		 */
//		// Date d = new Date();
//		// java.text.SimpleDateFormat sdf = new
//		// java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		// String endtime = sdf.format(d);
//		StringBuilder hql = new StringBuilder();
//		
//		hql
//				.append("select Count(c.id) from CcLog c,PoliceCallin p where c.id=p.id and ");
//		hql.append("c.beginTime>=to_date('" + begintime
//				+ "','yyyy-MM-dd HH:mm:ss') and ");
//		// hql.append("c.beginTime<=? and ");
//		hql.append("p.operator='" + opernum + "' ");
//		hql.append("order by c.beginTime desc");
//		mq.setHql(hql.toString());
//		// mq.setParameter(0, TimeUtil.getTimeByStr(begintime));
//		// mq.setParameter(1,endtime);
//		// mq.setParameter(1, opernum);
//		// mq.setHql(hql);
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * 得到部门信息
	 * 
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public MyQuery queryDep(IBaseDTO dto, String id) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallin.class);
		// dc.add(Restrictions.eq("department",id));
		// mq.setDetachedCriteria(dc);
		StringBuilder hql = new StringBuilder();
		hql.append("select p from CcLog c,PoliceCallin p where c.id=p.id");
		hql.append(" and p.department = '" + id + "'");
		if (!dto.get("beginTime").toString().equals("")) {
			hql.append(" and c.beginTime>=to_date('"
					+ dto.get("beginTime").toString() + "','yyyy-MM-dd') ");
		}
		if (!dto.get("endTime").toString().equals("")) {
			hql.append(" and c.beginTime<=to_date('"
					+ dto.get("endTime").toString() + "','yyyy-MM-dd') ");
		}
		hql.append(" and c.deleteMark = '0' ");
		mq.setHql(hql.toString());
		return mq;
	}

	/**
	 * 查询信息
	 * 
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchPhoneSize(String phone_date) {

		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(PoliceCallinInfo.class);
		// dc.add(Expression.eq("policeCallin", pc));
		// mq.setDetachedCriteria(dc);
		StringBuilder hql = new StringBuilder();
		// TimeUtil t = new TimeUtil();
		// TimeUtil.getNowSTime();
		// System.out.println(TimeUtil.getTheTimeStr(new Date(), "yyyy-MM-dd"));
		String begintime = addHMS(phone_date);
		String endtime = addoneDate(begintime);
		// System.out.println(begintime);
//		hql
//				.append("select c.id from CcLog c,PoliceCallinInfo pcii where c.id = pcii.policeCallin and c.beginTime >= '"
//						+ begintime + "' and c.beginTime <= '" + endtime + "'");
		
		hql
		.append("select ct.id from CclogTalk ct,CcQuestion cq where ct.id = cq.cclogTalk and ct.touchBegintime >= '"
				+ begintime + "' and ct.touchBegintime <= '" + endtime + "'");

		//System.out.println(hql.toString());
		mq.setHql(hql.toString());
		return mq;
	}
	
	public MyQuery cctalkIdQuery(String id)
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcTalk.class);
		dc.add(Restrictions.eq("cclogMain.id", id));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery searchPoliceInfobyId(String id) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc = DetachedCriteria.forClass(FarmerInfo.class);
		// if (num!=null&&!num.equals("")) {
		// // System.out.println("号码传到这了吗"+num);
		// dc.add(Expression.eq("mobileTel", num));
		// }
		// mq.setDetachedCriteria(dc);
		StringBuffer sb = new StringBuffer();
		sb.append("select pcii from CclogTalk pci,CcQuestion pcii where pci.id = pcii.cclogTalk and pcii.cclogTalk = '"+ id + "'");
		
		//System.out.println(sb.toString()+"%%%%%%%%%%%%%%%%");
		
		mq.setHql(sb.toString());
		return mq;
	}
	public MyQuery ccqIdQuery(String id)
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcMain.class);
		dc.add(Restrictions.eq("id", id));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 查询当天当前座席员接待问题信息
	 * @param dto
	 * @return
	 */
	public MyQuery cclogQuestion(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// end
		StringBuilder hql = new StringBuilder();
		//座席员名
		String agentName = (String)dto.get("userName");
		
//		System.out.println("Help中传过来的userName is "+agentName);
		//时间
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());
		

			hql.append("select pcii from CclogMain c, CclogTalk p,CcQuestion pcii where c.id=p.cclogMain and p.id=pcii.cclogTalk and p.respondentType = 'agent'");
//		else
//			hql.append("select p from CclogMain c,CclogTalk p where c.id=p.cclogMain and p.respondentType = 'agent'");

		hql.append(" and p.respondent = '"+ agentName +"'");
		
		hql.append(" and p.touchBegintime >= to_date('"
				+ addHMS(today) + "','yyyy-mm-dd HH24:mi:ss') ");
		
		hql.append(" and p.touchBegintime < to_date('"
				+ addoneDate(today) + "','yyyy-mm-dd HH24:mi:ss')"); 
		
//		if (!dto.get("ringBegintime").toString().equals("")) {
//			hql.append(" and p.touchBegintime>=to_date('"
//					+ addHMS(dto.get("ringBegintime").toString()) + "','yyyy-mm-dd HH24:mi:ss') ");
//		}
//		if (!dto.get("processEndtime").toString().equals("")) {
//			hql.append(" and p.touchBegintime<=to_date('"
//					+ addoneDate(dto.get("processEndtime").toString()) + "','yyyy-mm-dd HH24:mi:ss')");
//		}
		

		hql.append(" and c.isDelete = '0' ");
		hql.append(" order by pcii.ifAnswerSucceed ");
//		if (pi.getFieldAsc() == null && pi.getFieldDesc() == null) {
//			hql.append(" order by c.ringBegintime desc");
//		}
//		if (pi.getFieldAsc() != null) {
//			hql.append(" order by " + pi.getFieldAsc() + " asc");
//		}
//		if (pi.getFieldDesc() != null) {
//			hql.append(" order by " + pi.getFieldDesc() + " desc");
//		}

		mq.setHql(hql.toString());
		
		return mq;
	}
	
	public MyQuery queryIVRInfo(String cclogId) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select cid from CcIvrDate cid, CcMain cm, CcIvr ci");
		hql.append(" where cid.ccIvr=ci and ci.ccMain=cm");
		hql.append(" and cm.id = '"+ cclogId +"'");
		mq.setHql(hql.toString());
		
		return mq;
	}
	
	public MyQuery queryQuesInfo(String cclogId) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder hql = new StringBuilder();
		
		hql.append("select oq from OperQuestion oq, CcMain cm, CcTalk ct");
		hql.append(" where oq.talkId=ct.id and ct.ccMain=cm");
		hql.append(" and cm.id = '"+ cclogId +"'");
		mq.setHql(hql.toString());
		
		return mq;
	}
	
	public MyQuery queryRecord(String userName, String telNum, String todayTime, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder hql = new StringBuilder();

		System.out.println("cclogMainHelp : userName is "+userName);
//		DetachedCriteria dc = DetachedCriteria.forClass(CcTalk.class);
		
		hql.append("select ct from CcTalk ct where ct.id = ct.id");
		
//		dc.add(Restrictions.eq("respondent", userName));
		//dc.add(arg0)
		
		hql.append(" and ct.respondent = '"+ userName +"'");
		
		if(!"".equals(telNum))
			hql.append(" and ct.phoneNum like '%"+ telNum +"%'");
		
		hql.append(" and convert(varchar(10),ct.touchBegintime,120) like '"+ todayTime +"%'");
//		dc.add(Restrictions.ilike("touchBegintime", todayTime));
//		convert(varchar(10),a.insertDate,120) like '"+TimeUtil.getTheTimeStr(infoBean.getInsertDate(), "yyyy-MM-dd")+"%'"
		hql.append(" order by touchBegintime desc ");
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
//		mq.setDetachedCriteria(dc);
		return mq;
	}
	public MyQuery queryRecord(String userName, String telNum, String beginTime, String endTime, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder hql = new StringBuilder();

		System.out.println("cclogMainHelp : userName is "+userName);
//		DetachedCriteria dc = DetachedCriteria.forClass(CcTalk.class);
		
		hql.append("select ct from CcTalk ct where ct.id = ct.id");
		
//		dc.add(Restrictions.eq("respondent", userName));
		//dc.add(arg0)
		
		hql.append(" and ct.respondent like '"+ userName +"%'");
		
		if(!"".equals(telNum))
			hql.append(" and ct.phoneNum like '%"+ telNum +"%'");
		if(beginTime==null)
		{
			beginTime = "";
		}
		if(endTime==null)
		{
			endTime = "";
		}
		if(!"".equals(beginTime.trim())&&!"".equals(endTime.trim()))
		{
			hql.append(" and convert(varchar(10),ct.touchBegintime,120) between '"+ beginTime +"' and '"+endTime+"' ");
		}
		else if(!"".equals(beginTime.trim()))
		{
			hql.append(" and convert(varchar(10),ct.touchBegintime,120) like '"+ beginTime +"%'");
		}
		else if(!"".equals(endTime.trim()))
		{
			hql.append(" and convert(varchar(10),ct.touchBegintime,120) like '"+ endTime +"%'");
		}
		
//		dc.add(Restrictions.ilike("touchBegintime", todayTime));
//		convert(varchar(10),a.insertDate,120) like '"+TimeUtil.getTheTimeStr(infoBean.getInsertDate(), "yyyy-MM-dd")+"%'"
		
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
//		mq.setDetachedCriteria(dc);
		return mq;
	}
	public MyQuery queryRecordSize(String userName, String telNum, String todayTime, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder hql = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		DetachedCriteria dc = DetachedCriteria.forClass(CcTalk.class);
		
		dc.add(Restrictions.eq("respondent", userName));
		
		if(!"".equals(telNum))
			dc.add(Restrictions.like("phoneNum", telNum));
		//dc.add(arg0)

//		hql.append(" and ct.respondent = '"+ userName +"'");
//		hql.append(" and convert(varchar(10),ct.touchBegintime,120) like '"+ todayTime +"%'");
		try {
			dc.add(Restrictions.ge("touchBegintime", sdf.parse(todayTime)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		convert(varchar(10),a.insertDate,120) like '"+TimeUtil.getTheTimeStr(infoBean.getInsertDate(), "yyyy-MM-dd")+"%'"
		
		
//		mq.setHql(hql.toString());
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
		mq.setDetachedCriteria(dc);
		return mq;
	}
	public MyQuery queryRecordSize(String userName, String telNum, String beginTime,String endTime, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder hql = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		DetachedCriteria dc = DetachedCriteria.forClass(CcTalk.class);
		
		dc.add(Restrictions.like("respondent", userName+"%"));
		
		if(!"".equals(telNum))
			dc.add(Restrictions.like("phoneNum", telNum));
		//dc.add(arg0)

//		hql.append(" and ct.respondent = '"+ userName +"'");
//		hql.append(" and convert(varchar(10),ct.touchBegintime,120) like '"+ todayTime +"%'");
		try {
			if(beginTime == null)
			{
				beginTime = "";
			}
			if(endTime == null)
			{
				endTime = "";
			}
			if(!"".equals(beginTime.trim())&&!"".equals(endTime.trim()))
			{
				dc.add(Restrictions.between("touchBegintime", sdf.parse(beginTime), sdf.parse(endTime)));
			}
			else if(!"".equals(beginTime.trim()))
			{
				System.out.println("**********"+beginTime);
				dc.add(Restrictions.ge("touchBegintime", sdf.parse(beginTime)));
			}
			else if(!"".equals(endTime.trim()))
			{
				dc.add(Restrictions.ge("touchBegintime", sdf.parse(endTime)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		convert(varchar(10),a.insertDate,120) like '"+TimeUtil.getTheTimeStr(infoBean.getInsertDate(), "yyyy-MM-dd")+"%'"
		
		
//		mq.setHql(hql.toString());
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
