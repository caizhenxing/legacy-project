package et.bo.sys.staff.workUnitInfo.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import et.po.WorkUnitInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class workUnitInfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery workUnitInfo(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(WorkUnitInfo.class);
		String companyName =(String)dto.get("companyName");
		String companyTel =(String)dto.get("companyTel");
		
		String companyAddress =(String)dto.get("companyAddress");
		String dictCompanyType =(String)dto.get("dictCompanyType");
		String helpsign =(String)dto.get("helpsign");

		
		
		if(!companyName.equals("")){
			dc.add(Restrictions.like("companyName", "%"+ companyName +"%"));
		}
	
		if(!companyTel.equals("")){
			dc.add(Restrictions.eq("companyTel", "%"+ companyTel +"%"));
		}
		
		if(!companyAddress.equals("")){
			dc.add(Restrictions.eq("companyAddress", "%"+ companyAddress +"%"));
		}
		if(!dictCompanyType.equals("")){
			dc.add(Restrictions.eq("dictCompanyType","%"+ dictCompanyType +"%" ));
		}
		if(!helpsign.equals("")){
			dc.add(Restrictions.eq("helpsign", "%"+ helpsign +"%"));
		}

		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
