package et.bo.sad.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import et.po.OperSadinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class SadHelp extends MyQueryImpl
{
	/** @describe 取得端口对照表所有记录 */
	public MyQuery sadQuery(IBaseDTO dto, PageInfo pi)
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperSadinfo.class);
		String sadRid = (String) dto.get("sadRid");
		String custName = (String) dto.get("custName");
		String custTel = (String) dto.get("custTel");
		String productName = (String) dto.get("productName");
		String custAddr = (String) dto.get("custAddr");
		String dictSadType = (String) dto.get("dictSadType");
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		if (!sadRid.equals(""))
		{
			dc.add(Restrictions.like("sadRid", "%" + sadRid + "%"));
		}
		if (!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("deployBegin", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals(""))
		{
			dc.add(Restrictions.le("deployBegin", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}

		if (!custName.equals(""))
		{
			dc.add(Restrictions.like("custName", "%" + custName + "%"));
		}
		if (!custTel.equals(""))
		{
			dc.add(Restrictions.like("custTel", "%" + custTel + "%"));
		}
		if (!productName.equals(""))
		{
			dc.add(Restrictions.like("productName", "%" + productName + "%"));
		}
		if (!custAddr.equals(""))
		{
			dc.add(Restrictions.like("custAddr", "%" + custAddr + "%"));
		}
		if (!dictSadType.equals(""))
		{
			dc.add(Restrictions.eq("dictSadType", dictSadType));
		}
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		dc.addOrder(Order.desc("sadTime"));

		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());

		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery sadInfoQuery(IBaseDTO dto, PageInfo pi)
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperSadinfo.class);
		

		dc.addOrder(Order.desc("deployBegin"));

		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
}
