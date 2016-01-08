package et.bo.callcenter.callOutSet.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import et.po.OrderMenu;
import excellence.common.page.PageInfo;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class OrderMenuHelp extends MyQueryImpl {
	public MyQuery getOrderRecord(String telNum, String ivrInfo) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		sb.append("select om from OrderMenu om where om.id = om.id");
		sb.append(" and om.telnum = '"+ telNum +"'");
		sb.append(" and om.menuType = 'customize'");
		sb.append(" and om.menuEndtime is null");
		sb.append(" and om.ivrTreeInfoId = '"+ ivrInfo +"'");
		
		mq.setHql(sb.toString());
		return mq;
	}
	
	public MyQuery getCustomizeRecord(String telNum, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
//		DetachedCriteria dc = DetachedCriteria.forClass(OrderMenu.class);
//		dc.add(Restrictions.eq("telnum", telNum));
//		dc.add(Restrictions.eq("menuType", "customize"));
//		dc.add(Restrictions.isNull("menuEndtime"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("select om from OrderMenu om");
		sb.append(" where om.telnum = '"+ telNum +"'");
		sb.append(" and om.menuType = 'customize'");
		sb.append(" and om.menuEndtime is null");
		
//		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		mq.setHql(sb.toString());
		return mq;
	}
	
	public MyQuery getCustomizeRecordSize(String telNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OrderMenu.class);
		dc.add(Restrictions.eq("telnum", telNum));
		dc.add(Restrictions.eq("menuType", "customize"));
		dc.add(Restrictions.isNull("menuEndtime"));
		
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery getOrderMenuById(String id) {
		int oid = Integer.parseInt(id);
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OrderMenu.class);
		dc.add(Restrictions.eq("id", oid));
		
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 查询用户列表
	 * @return
	 */
	public MyQuery getUserList(String userType) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		
		dc.add(Restrictions.eq("dictCustType", userType));
		
		
		mq.setDetachedCriteria(dc);
		mq.setFetch(100);
		return mq;
	}
}
