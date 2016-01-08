package et.bo.medical.medicinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import et.po.OperMedicinfo;

import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class MedicinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery medicinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperMedicinfo.class);
		String isParter =(String)dto.get("isParter");
		String contents = (String)dto.get("contents");
		String reply = (String)dto.get("reply");

		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		String medicRid = (String)dto.get("medicRid");
		if(!medicRid.equals("")){
			dc.add(Restrictions.eq("medicRid", medicRid));
		}
		if(!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("createTime",TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		
		if(!endTime.equals(""))
		{
			dc.add(Restrictions.le("createTime",TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		
		if(!isParter.equals("")){
			dc.add(Restrictions.eq("isParter",isParter));
		}

		if(!contents.equals("")){
			dc.add(Restrictions.like("contents", "%"+contents+"%"));
		}
		if(!reply.equals("")){
			dc.add(Restrictions.like("reply", "%"+reply+"%"));
		}

		String billNum = (String) dto.get("billNum");
		if (billNum != null && !"".equals(billNum.trim())) {
			dc.add(Restrictions.like("expert", billNum.trim()));
		}
		String expertName = (String) dto.get("expertName");
		if (expertName != null && !"".equals(expertName.trim())) {
			dc.add(Restrictions.like("expertName", expertName.trim()));
		}
		
		dc.addOrder(Order.desc("createTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
