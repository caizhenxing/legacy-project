
package et.bo.quoteCircs.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class QuoteCircsHelp extends MyQueryImpl{
	
	public MyQuery linkManQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		dc.add(Restrictions.eq("dictCustType", "SYS_TREE_0000002108"));
		//≈≈–Ú
		dc.addOrder(Order.asc("custName"));
		
		String custAddr = (String)dto.get("custAddr");
		if(!custAddr.equals("")){
			dc.add(Restrictions.like("custAddr", "%"+custAddr+"%"));
		}
		
		String custName = (String)dto.get("custName");
		if(!custName.equals("")){
			dc.add(Restrictions.eq("custName", custName));
		}
		
		String custId = (String)dto.get("custId");
		if(!custId.equals("")){
			dc.add(Restrictions.like("custId", "%"+custId+"%"));
		}
		mq.setDetachedCriteria(dc);
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
}
