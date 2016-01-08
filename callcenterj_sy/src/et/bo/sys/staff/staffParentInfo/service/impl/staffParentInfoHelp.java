package et.bo.sys.staff.staffParentInfo.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import et.po.StaffParent;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class staffParentInfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery staffParentInfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(StaffParent.class);
		String parentName =(String)dto.get("parentName");
		String work = (String)dto.get("work");
		String linkTel = (String)dto.get("linkTel");
		String dictParentPolity = (String)dto.get("dictParentPolity");
		
		
		
		if(!parentName.equals("")){
			dc.add(Restrictions.like("parentName", "%"+parentName+"%"));
		}
		if(!work.equals("")){
			dc.add(Restrictions.like("work", "%"+work+"%"));
		}
		if(!linkTel.equals("")){
			dc.add(Restrictions.like("linkTel", "%"+linkTel+"%"));
		}
	
		if(!dictParentPolity.equals("")){
			dc.add(Restrictions.eq("dictParentPolity", dictParentPolity));
		}
		
		
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
