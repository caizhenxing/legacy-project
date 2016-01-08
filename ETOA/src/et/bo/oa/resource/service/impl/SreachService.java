package et.bo.oa.resource.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import et.po.EmployeeInfo;
import et.po.ResourceInfo;
import et.po.ResourceUse;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


/**
 * <p> 资源管理 MyQuery </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-23
 * 
 */
public class SreachService {

	/**
	 * <p> 查询会议室使用情况 </p>
	 */
	public MyQuery searchResourceUseMyQuery(IBaseDTO dto, PageInfo pageInfo , ResourceInfo ri){
//		ResourceUse resourceUse = new ResourceUse();
		DetachedCriteria dc = DetachedCriteria.forClass(ResourceUse.class);
		    dc.add(Expression.eq("applyState", "40"));	
		 if(!"".equals(dto.get("meetingNames").toString())){
			 dc.add(Expression.eq("resourceInfo", ri));
		 }
		 if(!"".equals(dto.get("applyUser").toString())){
			 dc.add(Expression.like("userName", "%"+dto.get("applyUser").toString()+"%"));
		 }				
		 if(dto.get("startDate").toString()!=null&&dto.get("startDate").toString()!="")
	        {        	        	
	        	dc.add(Expression.ge("dateArea",TimeUtil.getTimeByStr(dto.get("startDate").toString(),"yyyy-MM-dd")));
	        }
	        if(dto.get("endDate").toString()!=null&&dto.get("endDate").toString()!="")
	        {        
	        	dc.add(Expression.le("dateArea",TimeUtil.getTimeByStr(dto.get("endDate").toString(),"yyyy-MM-dd")));
	        }
//		if(!(dto.get("useDate") == null || "".equals(dto.get("useDate").toString()))){
//			resourceUse.setDateArea(TimeUtil.getTimeByStr(dto
//					.get("useDate").toString(), "yyyy-MM-dd"));
//		}		
//		dc.add(Example.create(resourceUse));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);		
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());
		return myQuery;
	}
	
	/**
	 * <p> 查询会议室信息 </p>
	 */
	public MyQuery searhResourceInfoMyQuery(IBaseDTO dto, PageInfo pageInfo){
		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Restrictions.eq("resourceType",dto.get("type").toString()));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());
		return myQuery;
	}
	
	/**
	 * <p>获得资源列表</p>
	 */
	public MyQuery getResourceListMyQuery(String ResourceType,String key){
		ResourceInfo resource = new ResourceInfo();
		resource.setResourceType(ResourceType);
		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Example.create(resource));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}
	
	/**
	 * <p> 获得用户列表 </p>
	 * @return：用户信息（id，name）
	 */
	public MyQuery getUserListMyQuery(String page,IBaseDTO infoDTO){
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		if("selectEmployee".equals(page)){
			EmployeeInfo info = new EmployeeInfo();
			info.setDepartment(infoDTO.get("departList") == null ? "" : infoDTO.get("departList").toString());
			dc.add(Example.create(info));
		}
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}
	
	/**
	 * <p> 获得车辆申请信息 </p>
	 */
	public MyQuery getCarApplyMyQuery(IBaseDTO dto, PageInfo pageInfo){
		DetachedCriteria dc = DetachedCriteria.forClass(ResourceUse.class);
		
		if(!("all".equals(dto.get("approveType").toString()))){
			dc.add(Restrictions.eq("resourceState",dto.get("approveType").toString()));
		}
		dc.add(Restrictions.eq("applyState", "39"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());
		return myQuery;
	}
	
	/**
	 * <p> 查询车辆基本信息 </p>
	 */
	public MyQuery getCarInfoMyQuery(IBaseDTO dto, PageInfo pageInfo){
		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Restrictions.eq("resourceType",dto.get("type").toString()));

		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());
		return myQuery;
	}
	
}
