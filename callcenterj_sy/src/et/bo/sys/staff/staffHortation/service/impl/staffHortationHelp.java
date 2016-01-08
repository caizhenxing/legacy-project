package et.bo.sys.staff.staffHortation.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import et.po.StaffHortation;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class staffHortationHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery staffHortationQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(StaffHortation.class);
		String hortationType =(String)dto.get("hortationType");
		String hortationTime = (String)dto.get("hortationTime");
		
		
		
		if(!hortationType.equals("")){
			dc.add(Restrictions.eq("hortationType", hortationType));
		}
	
		if(!hortationTime.equals("")){
			dc.add(Restrictions.eq("hortationType", hortationTime));
		}
		
		
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
