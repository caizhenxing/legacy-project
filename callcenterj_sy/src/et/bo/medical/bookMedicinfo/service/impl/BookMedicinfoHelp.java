package et.bo.medical.bookMedicinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import et.po.OperBookMedicinfo;
import et.po.OperMedicinfo;
import et.po.OperPriceinfo;

import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class BookMedicinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery bookMedicinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperBookMedicinfo.class);
		String isParter =(String)dto.get("isParter");
		String contents = (String)dto.get("contents");
		
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		String bookRid = (String) dto.get("bookRid");
		if(!bookRid.equals("")){
			dc.add(Restrictions.like("bookRid", "%"+bookRid+"%"));
		}
		if (!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("createTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals(""))
		{
			dc.add(Restrictions.le("createTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if(!isParter.equals("")){
			dc.add(Restrictions.eq("isParter",isParter));
		}
	
		if(!contents.equals("")){
			dc.add(Restrictions.like("contents", "%"+contents+"%"));
		}
		
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		String billNum = (String) dto.get("billNum");
		if (billNum != null && !"".equals(billNum.trim())) {
			dc.add(Restrictions.like("expertSort", billNum.trim()));
		}
		String expertName = (String) dto.get("expertName");
		if (expertName != null && !"".equals(expertName.trim())) {
			dc.add(Restrictions.like("expert", expertName.trim()));
		}
		
//		就诊类型
		String dictServiceType = (String) dto.get("dictServiceType");
		if (dictServiceType != null && !"".equals(dictServiceType.trim())) {
			dc.add(Restrictions.eq("dictServiceType", dictServiceType.trim()));
		}
		
		dc.addOrder(Order.desc("createTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
