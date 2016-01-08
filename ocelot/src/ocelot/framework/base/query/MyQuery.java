package ocelot.framework.base.query;


import java.util.List;
import java.util.Map;

import ocelot.common.page.PageInfo;
import ocelot.framework.base.dto.IBaseDTO;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;




 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface MyQuery {

	static Logger log=Logger.getLogger(MyQuery.class);
	

	public abstract IBaseDTO getDto();

	public abstract void setDto(IBaseDTO dto);

	public abstract int getFirst();
	public abstract void setFirst(int first);
	
	public abstract int getFetch();
	public abstract void setFetch(int fetch);
	
	public abstract DetachedCriteria getDetachedCriteria(); 
	
	public abstract String getHql();
	
	public abstract String getSql();
	
	public abstract Class getType() ;

	public abstract void setType(Class type) ;
	
	public abstract String getIdentifer();
	
	public abstract void setIdentifer(String identifer);
	
	public abstract void setDetachedCriteria(DetachedCriteria dc);
	
	public abstract void setHql(String hql);
	
	public abstract void setSql(String sql);
	
	public abstract void setParameter(int idx,Object para);
	
	public abstract void setParameter(String pname,Object para);
	
	public abstract Map getParameterM();
	
	public abstract List getParameterL();
	
	public abstract void setPageInfo(PageInfo pi);
	
	public abstract void setAsc(String asc);
	
	public abstract void setDesc(String desc);
	
	public abstract String getAsc();
	
	public abstract String getDesc();
}