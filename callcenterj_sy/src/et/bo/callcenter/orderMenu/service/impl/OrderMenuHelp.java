package et.bo.callcenter.orderMenu.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OrderMenu;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
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
		sb.append(" and om.ivrTreeInfoId = '"+ ivrInfo +"' order by om.menuEndtime desc ");
		
		mq.setHql(sb.toString());
		return mq;
	}
	
	/**
	 * 根据电话号码显示定制信息列表
	 * @param telNum
	 * @param pi
	 * @return
	 */
	public MyQuery getCustomizeRecord(String telNum, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
//		DetachedCriteria dc = DetachedCriteria.forClass(OrderMenu.class);
//		dc.add(Restrictions.eq("telnum", telNum));
//		dc.add(Restrictions.eq("menuType", "customize"));
//		dc.add(Restrictions.isNull("menuEndtime"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("select om from OrderMenu om");
//		sb.append(" where om.telnum = '"+ telNum +"'");
		sb.append(" where om.telnum like '%"+ telNum +"%'");
		sb.append(" and om.menuType = 'customize'");
		sb.append(" and om.menuEndtime is null order by om.menuEndtime desc ");
		
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
	
	/**
	 * 根据电话号码显示用户点播、定制的所有历史记录信息列表
	 * @param telNum
	 * @param pi
	 * @return
	 */
	public MyQuery getBusinessMenuRecord(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		String telNum = dto.get("telNum").toString();
		String orderType = dto.get("orderType").toString();
		String ivrInfo = dto.get("ivrInfo").toString();
		String beginDate = dto.get("beginDate").toString();
		String endDate = dto.get("endDate").toString();
		
		sb.append("select om from OrderMenu om where telnum<>'' ");
		sb.append(" and telnum like '%"+ telNum +"%'");
		if(orderType!=null&&!"".equals(orderType)){
			sb.append(" and menuType='"+orderType+"'");
		}
		if(ivrInfo!=null&&!"".equals(ivrInfo)){
			sb.append(" and ivrTreeInfoId='"+ivrInfo+"'");
		}
		if(beginDate!=null&&!"".equals(beginDate)){
			sb.append(" and menuBegintime>='"+ beginDate +"'");
		}
		if(endDate!=null&&!"".equals(endDate)){
			sb.append(" and menuEndtime<='"+ endDate +"'");
		}
		sb.append(" order by menuBegintime desc ");
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		mq.setHql(sb.toString());
		return mq;
	}
	
	public MyQuery getBusinessMenuRecordSize(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		String telNum = dto.get("telNum").toString();
		String orderType = dto.get("orderType").toString();
		String ivrInfo = dto.get("ivrInfo").toString();
		String beginDate = dto.get("beginDate").toString();
		String endDate = dto.get("endDate").toString();
		
		sb.append("select om from OrderMenu om where telnum<>'' ");
		sb.append(" and telnum like '%"+ telNum +"%'");
		if(orderType!=null&&!"".equals(orderType)){
			
			sb.append(" and menuType='"+orderType+"'");
		}
		if(ivrInfo!=null&&!"".equals(ivrInfo)){
			sb.append(" and ivrTreeInfoId='"+ivrInfo+"'");
		}
		if(beginDate!=null&&!"".equals(beginDate)){
			sb.append(" and menuBegintime>='"+ beginDate +"'");
		}
		if(endDate!=null&&!"".equals(endDate)){
			sb.append(" and menuEndtime<='"+ endDate +"'");
		}
		sb.append(" order by menuBegintime desc ");
		mq.setHql(sb.toString());
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
}
