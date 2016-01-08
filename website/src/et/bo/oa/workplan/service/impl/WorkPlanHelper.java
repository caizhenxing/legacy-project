/**
 * 	@(#)WorkPlanHelper.java   2006-11-13 ÏÂÎç01:28:42
 *	 ¡£ 
 *	 
 */
package et.bo.oa.workplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.sys.login.UserInfo;
import et.po.WorkPlanInfo;
import et.po.WorkPlanMission;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author yifei zhao
 * @version 2006-11-13
 * @see
 */
public class WorkPlanHelper {

	public MyQuery actionPlan(String user,String view)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		dc.add(Restrictions.le("planBeignTime",TimeUtil.getNowTime()));
		dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		
		dc.add(Restrictions.or(Restrictions.ge("planViewType",view),Restrictions.eq("createUser", user)));
		mq.setDetachedCriteria(dc);

		return mq;
	}
	public MyQuery actionMission(String user)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		dc.add(Restrictions.le("planBeignTime",TimeUtil.getNowTime()));
		dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		
		dc.add(Restrictions.eq("missionClasses", user));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery parentPlan(String[] deps)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		//dc.add(Restrictions.le("planBeignTime",TimeUtil.getNowTime()));
		dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		if(deps.length>0)
		dc.add(Restrictions.in("planClasses",deps));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery subPlan(String dep)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		//dc.add(Restrictions.le("planBeignTime",TimeUtil.getNowTime()));
		
		dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery mission(List<String> users,String name,String key,String type,String begin,String end,String begin1,String end1,PageInfo pi,String missionSign,String missionInfo, String missionComplete,String orderbyasc,String orderbydesc)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanMission.class);
		//dc.add(Restrictions.le("planBeignTime",TimeUtil.getNowTime()));
		if(!"".equals(name))
			dc.add(Restrictions.like("name","%"+name+"%"));
		if(!"".equals(key))
			dc.add(Restrictions.like("keyword","%"+key+"%"));
		if(!"".equals(type))
			dc.add(Restrictions.eq("missionType",type));
		if(!"".equals(missionSign))
		{
			dc.add(Restrictions.eq("missionSign",missionSign));
		}
		if(!"".equals(missionInfo))
		{
			dc.add(Restrictions.like("missionInfo","%"+missionInfo+"%"));
		}
		if(!"".equals(missionComplete))
		{
			dc.add(Restrictions.like("missionComplete","%"+missionComplete+"%"));
		}
		if(users!=null)
		{
		
		dc.add(Restrictions.in("missionClasses",users.toArray()));
		}
		if(begin!=null&&!begin.trim().equals(""))
			dc.add(Restrictions.ge("beginTime",TimeUtil.getTimeByStr(begin,"yyyy-MM-dd")));
		if(end!=null&&!end.trim().equals(""))
			dc.add(Restrictions.le("beginTime",TimeUtil.getTimeByStr(end,"yyyy-MM-dd")));
		if(begin1!=null&&!begin1.trim().equals(""))
			dc.add(Restrictions.ge("endTime",TimeUtil.getTimeByStr(begin1,"yyyy-MM-dd")));
		if(end1!=null&&!end1.trim().equals(""))
			dc.add(Restrictions.le("endTime",TimeUtil.getTimeByStr(end1,"yyyy-MM-dd")));
		
		if(orderbyasc!=null&&!orderbyasc.trim().equals(""))
			dc.addOrder(Order.asc(orderbyasc));
		if(orderbydesc!=null&&!orderbydesc.trim().equals(""))
			dc.addOrder(Order.desc(orderbydesc));
		mq.setDetachedCriteria(dc);
		if(pi!=null)
		{
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		}
		
		return mq;
	}
	public MyQuery plan(String user,String title,String subhead,String begin,String end,String begin1,String end1,PageInfo pi,String checkId)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		if(!"".equals(title))
			dc.add(Restrictions.like("planTitle","%"+title+"%"));
		if(!"".equals(subhead))
			dc.add(Restrictions.like("planSubhead","%"+subhead+"%"));
		if(begin!=null&&!begin.trim().equals(""))
			dc.add(Restrictions.ge("planBeignTime",TimeUtil.getTimeByStr(begin,"yyyy-MM-dd")));
		if(end!=null&&!end.trim().equals(""))
			dc.add(Restrictions.le("planBeignTime",TimeUtil.getTimeByStr(end,"yyyy-MM-dd")));
		
		if(begin1!=null&&!begin1.trim().equals(""))
			dc.add(Restrictions.ge("planEndTime",TimeUtil.getTimeByStr(begin1,"yyyy-MM-dd")));
		if(end1!=null&&!end1.trim().equals(""))
			dc.add(Restrictions.le("planEndTime",TimeUtil.getTimeByStr(end1,"yyyy-MM-dd")));
		if(!"".equals(user))
		dc.add(Restrictions.eq("createUser",user));
		if(!"".equals(checkId))
			dc.add(Restrictions.eq("checkId",checkId));
		dc.addOrder(Order.desc("planBeignTime"));
		mq.setDetachedCriteria(dc);
		if(pi!=null)
		{
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		}
		return mq;
	}
	public MyQuery getSubPlan(String id)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		dc.add(Restrictions.eq("parentId",id));
		//dc.add(Restrictions.in("missionClasses",temp.toArray()));
		dc.addOrder(Order.desc("planBeignTime"));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery myMission(UserInfo ui)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanMission.class);
		//dc.add(Restrictions.le("planBeignTime",TimeUtil.getNowTime()));
		List<String> temp=new ArrayList<String>();
		temp.add(ui.getUserName());
		if(ui.getDeps()!=null)
		temp.addAll(ui.getDeps());
		dc.add(Restrictions.in("missionClasses",temp.toArray()));
		dc.add(Restrictions.ne("missionSign", "1"));
		dc.add(Restrictions.ne("missionSign", "-1"));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery myPlan(UserInfo ui)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		//dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		dc.add(Restrictions.eq("createUser",ui.getUserName()));
		dc.add(Restrictions.eq("checkId","1"));
		//dc.add(Restrictions.in("missionClasses",temp.toArray()));
		dc.addOrder(Order.desc("planBeignTime"));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery myPlan(String user)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(WorkPlanInfo.class);
		//dc.add(Restrictions.ge("planEndTime",TimeUtil.getNowTime()));
		dc.add(Restrictions.eq("createUser",user));
		dc.add(Restrictions.eq("checkId","1"));
		//dc.add(Restrictions.in("missionClasses",temp.toArray()));
		dc.addOrder(Order.desc("planBeignTime"));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
}
