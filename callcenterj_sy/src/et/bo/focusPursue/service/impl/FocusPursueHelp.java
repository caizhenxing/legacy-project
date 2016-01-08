package et.bo.focusPursue.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import excellence.common.util.time.TimeUtil;

import et.po.OperFocusinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class FocusPursueHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery focusPursueQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperFocusinfo.class);
		
		String createTime = (String)dto.get("createTime");		
		String chargeEditor = (String)dto.get("chargeEditor");
		String dictFocusType = (String)dto.get("dictFocusType");
		String chiefTitle =(String)dto.get("chiefTitle");
		String summary = (String)dto.get("summary");
		
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		
		if (!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("createTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals(""))
		{
			dc.add(Restrictions.le("createTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if(!createTime.equals("")){
			dc.add(Restrictions.like("createTime",TimeUtil.getTimeByStr(createTime, "yyyy-MM-dd")));
		}
		if(!chargeEditor.equals("")){
			dc.add(Restrictions.like("chargeEditor", "%"+chargeEditor+"%"));
		}
		if(!dictFocusType.equals("")){
			dc.add(Restrictions.like("dictFocusType", "%"+dictFocusType+"%"));
		}
		if(!chiefTitle.equals("")){
			dc.add(Restrictions.like("chiefTitle","%"+chiefTitle +"%"));
		}		
		if(!summary.equals("")){
			dc.add(Restrictions.like("summary", "%"+summary+"%"));
		}
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		dc.addOrder(Order.desc("createTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
