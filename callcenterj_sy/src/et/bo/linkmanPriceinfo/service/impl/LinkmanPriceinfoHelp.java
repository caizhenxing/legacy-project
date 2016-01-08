package et.bo.linkmanPriceinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import et.po.OperPriceinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class LinkmanPriceinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 查询联络员报价信息列表
//	 */
	public MyQuery operPriceinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperPriceinfo.class);
		
//		String priceRid =(String)dto.get("priceRid");
		String cust_number = (String)dto.get("cust_number");
//		String deployTime = (String) dto.get("deployTime");
		String custName = (String) dto.get("custName");
		String custAddr = (String) dto.get("custAddr");	
//		String custTel = (String)dto.get("custTel");
		String productName = (String)dto.get("productName");
		String dictProductType1 = (String)dto.get("dictProductType1");	
		String dictPriceType = (String)dto.get("dictPriceType");
		String dictProductType2 = (String)dto.get("dictProductType2");
		
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		
		if (!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("deployTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals(""))
		{
			dc.add(Restrictions.le("deployTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if(!dictPriceType.equals("")){
			dc.add(Restrictions.eq("dictPriceType",dictPriceType));
		}
		if(!productName.equals("")){
			dc.add(Restrictions.eq("productName",productName));
		}
//		if(!priceRid.equals("")){
//			dc.add(Restrictions.like("priceRid","%"+priceRid +"%"));
//		}	
		if (!custAddr.equals(""))
		{
			dc.add(Restrictions.like("custAddr", custAddr + "%"));
		}
//		if(!productPrice.equals("")){
//			dc.add(Restrictions.like("productPrice", "%"+productPrice+"%"));
//		}
//		if (!deployTime.equals(""))
//		{
//			dc.add(Restrictions.eq("deployTime", TimeUtil.getTimeByStr(deployTime, "yyyy-MM-dd")));
//		}
//		if(!custTel.equals("")){
//			dc.add(Restrictions.like("custTel", "%"+custTel+"%"));
//		}
		if(!"".equals(cust_number)){
			dc.add(Restrictions.like("cust_number", "%"+cust_number+"%"));
		}
		if(!"".equals(custName)){
			dc.add(Restrictions.like("custName", "%"+custName+"%"));
		}
		
		if(!dictProductType1.equals("")){
			dc.add(Restrictions.eq("dictProductType1",dictProductType1));
		}
		if(!dictProductType2.equals("")){
			dc.add(Restrictions.eq("dictProductType2",dictProductType2));
		}

		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		dc.add(Restrictions.eq("custType", "SYS_TREE_0000002108"));
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	}
	
	public MyQuery custinfoQuery(String cust_id) {
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCustinfo.class);
		
		dc.add(Restrictions.eq("custId", cust_id));
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
