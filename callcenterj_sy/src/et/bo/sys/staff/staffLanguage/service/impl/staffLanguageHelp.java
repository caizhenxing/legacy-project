package et.bo.sys.staff.staffLanguage.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import et.po.StaffLanguage;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class staffLanguageHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery StaffLanguageQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(StaffLanguage.class);
		String dictLanguageType =(String)dto.get("dictLanguageType");
		String dictLanguageDegree = (String)dto.get("dictLanguageDegree");
		
		
		
		if(!dictLanguageType.equals("")){
			dc.add(Restrictions.like("dictLanguageType", dictLanguageType));
		}
	
		if(!dictLanguageDegree.equals("")){
			dc.add(Restrictions.eq("dictLanguageDegree", dictLanguageDegree));
		}
		
		
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
