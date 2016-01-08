package et.bo.sms.sendAndReceive.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.HandsetNoteInfoAlready;
import et.po.HandsetNoteInfoNot;
import et.po.OperSadinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class sendAndReceiveHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery sendQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		StringBuffer sb = new StringBuffer();
		DetachedCriteria dc=DetachedCriteria.forClass(HandsetNoteInfoAlready.class);
		String receiveNum =(String)dto.get("receiveNum");
		String operTime = (String)dto.get("operTime");
		String management = (String)dto.get("management");
		String schedularTime = (String)dto.get("schedularTime");
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		String colunmInfo=(String)dto.get("columnInfo");
		if (!colunmInfo.equals(""))
		{
			dc.add(Restrictions.eq("columnInfo", colunmInfo));
		}
		if (!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("operTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals(""))
		{
			dc.add(Restrictions.le("operTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if(!receiveNum.equals("")){
			dc.add(Restrictions.like("receiveNum","%"+receiveNum +"%"));
		}
		if(!management.equals("")){
			
			sb.append(" and hsnia.management like '"+management+"'");
		}
		if(!operTime.equals("")){
			
			dc.add(Restrictions.ge("operTime",TimeUtil.getTimeByStr(operTime,"yyyy-MM-dd")));
		}
		if(!schedularTime.equals("")){
		
			dc.add(Restrictions.le("operTime",TimeUtil.getTimeByStr(operTime,"yyyy-MM-dd")));
		}
		
		dc.add(Restrictions.ne("delSign", "N"));
		dc.addOrder(Order.desc("operTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}
	public MyQuery sendNotQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();		
		DetachedCriteria dc=DetachedCriteria.forClass(HandsetNoteInfoNot.class);
		String receiveNum =(String)dto.get("receiveNum");
		String management = (String)dto.get("management");
		String schedularTime = (String)dto.get("schedularTime");
		String colunmInfo=(String)dto.get("columnInfo");
		if (!colunmInfo.equals(""))
		{
			dc.add(Restrictions.eq("columnInfo", colunmInfo));
		}
		if(!receiveNum.equals("")){
			dc.add(Restrictions.like("receiveNum","%"+receiveNum +"%"));
		}
		if(!management.equals("")){
			dc.add(Restrictions.like("management", "%"+management+"%"));
		}
		if(!"".equals(schedularTime) && schedularTime!=""){
			dc.add(Restrictions.le("schedularTime",TimeUtil.getTimeByStr(schedularTime,"yyyy-MM-dd")));
		}		
		dc.add(Restrictions.ne("delSign", "N"));
		dc.addOrder(Order.desc("schedularTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}  
}
