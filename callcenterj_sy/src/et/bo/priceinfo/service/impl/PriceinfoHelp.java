package et.bo.priceinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.stat.service.impl.StatDateStr;
import et.po.OperPriceinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class PriceinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录 price_rid agent_id
//	 */
	public MyQuery operPriceinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperPriceinfo.class);
		
		String priceRid =(String)dto.get("priceRid");
		String productPrice = (String)dto.get("productPrice");
		String deployTime = (String) dto.get("deployTime");
		String custAddr = (String) dto.get("custAddr");	
		String custTel = (String)dto.get("custTel");
		String productName = (String)dto.get("productName");
		String dictProductType1 = (String)dto.get("dictProductType1");	
//		String dictPriceType = (String)dto.get("dictPriceType");
		String dictProductType2 = (String)dto.get("dictProductType2");
		StatDateStr.setBeginEndTimeAll(dto);
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		
		String dictPriceType = dto.get("dictPriceType").toString();
        if("SYS_TREE_0000000627".equals(dictPriceType)) dictPriceType = "收购价";
        if("SYS_TREE_0000000628".equals(dictPriceType)) dictPriceType = "批发价";
        if("SYS_TREE_0000000629".equals(dictPriceType)) dictPriceType = "零售价";
        
		if (!beginTime.equals(""))
		{
			
				dc.add(Restrictions.ge("deployTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd HH:mm:ss")));
		}
		if (!endTime.equals(""))
		{
			
				dc.add(Restrictions.le("deployTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd HH:mm:ss")));
		}
		if(!dictPriceType.equals("")){
			dc.add(Restrictions.eq("dictPriceType",dictPriceType));
		}
		if(!productName.equals("")){
			dc.add(Restrictions.eq("productName",productName));
		}
		if(!priceRid.equals("")){
			dc.add(Restrictions.like("priceRid","%"+priceRid +"%"));
		}	
		if (!custAddr.equals(""))
		{
			dc.add(Restrictions.like("custAddr", custAddr + "%"));
		}
		if(!productPrice.equals("")){
			dc.add(Restrictions.like("productPrice", "%"+productPrice+"%"));
		}
		if (!deployTime.equals(""))
		{
			dc.add(Restrictions.eq("deployTime", TimeUtil.getTimeByStr(deployTime, "yyyy-MM-dd")));
		}
		if(!custTel.equals("")){
			dc.add(Restrictions.like("custTel", "%"+custTel+"%"));
		}
		if(!dictProductType1.equals("")){
			dc.add(Restrictions.like("dictProductType1", "%"+dictProductType1+"%"));
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
		String agent_id=dto.get("agent_id")==null?"":(String)dto.get("agent_id");
		if(!"".equals(agent_id))
		{
			dc.add(Restrictions.eq("priceRid", agent_id));
		}
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	} 
	
	public MyQuery operPriceinfoExcelQuery(IBaseDTO dto){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperPriceinfo.class);
		
		String priceRid =(String)dto.get("priceRid");
		String productPrice = (String)dto.get("productPrice");
		String deployTime = (String) dto.get("deployTime");
		String custAddr = (String) dto.get("custAddr");	
		String custTel = (String)dto.get("custTel");
		String productName = (String)dto.get("productName");
		String dictProductType1 = (String)dto.get("dictProductType1");	
//		String dictPriceType = (String)dto.get("dictPriceType");
		String dictProductType2 = (String)dto.get("dictProductType2");
		StatDateStr.setBeginEndTimeAll(dto);
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		
		String dictPriceType = dto.get("dictPriceType").toString();
        if("SYS_TREE_0000000627".equals(dictPriceType)) dictPriceType = "收购价";
        if("SYS_TREE_0000000628".equals(dictPriceType)) dictPriceType = "批发价";
        if("SYS_TREE_0000000629".equals(dictPriceType)) dictPriceType = "零售价";
        
		if (!beginTime.equals(""))
		{
			
				dc.add(Restrictions.ge("deployTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd HH:mm:ss")));
		}
		if (!endTime.equals(""))
		{
			
				dc.add(Restrictions.le("deployTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd HH:mm:ss")));
		}
		if(!dictPriceType.equals("")){
			dc.add(Restrictions.eq("dictPriceType",dictPriceType));
		}
		if(!productName.equals("")){
			dc.add(Restrictions.eq("productName",productName));
		}
		if(!priceRid.equals("")){
			dc.add(Restrictions.like("priceRid","%"+priceRid +"%"));
		}	
		if (!custAddr.equals(""))
		{
			dc.add(Restrictions.like("custAddr", custAddr + "%"));
		}
		if(!productPrice.equals("")){
			dc.add(Restrictions.like("productPrice", "%"+productPrice+"%"));
		}
		if (!deployTime.equals(""))
		{
			dc.add(Restrictions.eq("deployTime", TimeUtil.getTimeByStr(deployTime, "yyyy-MM-dd")));
		}
		if(!custTel.equals("")){
			dc.add(Restrictions.like("custTel", "%"+custTel+"%"));
		}
		if(!dictProductType1.equals("")){
			dc.add(Restrictions.like("dictProductType1", "%"+dictProductType1+"%"));
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
		String agent_id=dto.get("agent_id")==null?"":(String)dto.get("agent_id");
		if(!"".equals(agent_id))
		{
			dc.add(Restrictions.eq("priceRid", agent_id));
		}
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		
		return mq;       
	} 
}
