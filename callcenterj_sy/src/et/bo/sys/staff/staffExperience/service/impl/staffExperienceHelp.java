package et.bo.sys.staff.staffExperience.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import et.po.StaffBasic;
import et.po.StaffExperience;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class staffExperienceHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery staffExperienceQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(StaffExperience.class);
		String dictExperienceType =(String)dto.get("dictExperienceType");
		String company = (String)dto.get("company");
		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		
		
		
		if(!dictExperienceType.equals("")){
			dc.add(Restrictions.like("dictExperienceType", dictExperienceType));
		}
	
		if(!company.equals("")){
			dc.add(Restrictions.eq("company", company));
		}

		if((!endTime.equals("")) && (!beginTime.equals("")))
		{
			dc.add(Restrictions.ge("beginTime",TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd")));
			
			dc.add(Restrictions.le("endTime",TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd")));
		}
		
		if(!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("beginTime",TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd")));
			
		}
		if(!endTime.equals(""))
		{
			dc.add(Restrictions.le("endTime",TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd")));
			
		}
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
