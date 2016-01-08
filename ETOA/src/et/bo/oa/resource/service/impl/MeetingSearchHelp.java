package et.bo.oa.resource.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.ResourceInfo;
import et.po.ResourceUse;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class MeetingSearchHelp {

	/**
	 * <p> 查询会议室名称 </p>
	 */
	public MyQuery searchMeetingRoom(){

		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Restrictions.eq("resourceType", "40"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);		
		return myQuery;
	}
	/**
	 * <p> 查询是否会议室有同名 </p>
	 */
	public MyQuery searchSameResourceName(String resourceName){

		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Restrictions.eq("resourceName", resourceName));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);		
		return myQuery;
	}
	
	/**
	 * <p> 查询是车辆是否有同名 </p>
	 */
	public MyQuery searchSameCarCode(String resourceCode){

		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Restrictions.eq("resourceCode", resourceCode));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);		
		return myQuery;
	}
}
