package et.bo.sms.columnInfo.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ColumnInfo;
import et.po.ColumnInfoMessage;
import et.po.ColumnInfoSendtime;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * 
 * @author chen gang
 *
 */
public class ColumnInfoHelp extends MyQueryImpl {
	public MyQuery getOrderRecord(String mobileNum, String colInfo) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		sb.append("select ci from ColumnInfo ci where ci.id = ci.id");
		sb.append(" and ci.mobileNum = '"+ mobileNum +"'");
		sb.append(" and ci.ordeType = 'customize'");
		sb.append(" and ci.menuEndtime is null");
		sb.append(" and ci.colInfo = '"+ colInfo +"'");
		
		mq.setHql(sb.toString());
		return mq;
	}
	
	public MyQuery getCustomizeRecord(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ColumnInfo.class);
		
		String beginTime =(String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		String mobileNum = (String)dto.get("mobileNum");
		String orderType = (String)dto.get("orderType");
		String columnInfo = (String)dto.get("columnInfo");
		
		
		if(mobileNum!=null&&!mobileNum.equals("")){
			dc.add(Restrictions.eq("mobileNum",mobileNum));
		}
		if(orderType!=null&&!orderType.equals("")){
			dc.add(Restrictions.eq("ordeType",orderType));
		}
		if(columnInfo!=null&&!columnInfo.equals("")){
			dc.add(Restrictions.eq("colInfo",columnInfo));
		}
		if (beginTime!=null&&!beginTime.equals("")){
			dc.add(Restrictions.ge("menuBegintime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (endTime!=null&&!endTime.equals("")){
			dc.add(Restrictions.le("menuBegintime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		dc.add(Restrictions.isNull("deleteMark"));
		dc.addOrder(Order.desc("menuBegintime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery getCustomizeRecordSize(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ColumnInfo.class);
		dc.add(Restrictions.eq("mobileNum", dto.get("mobileNum")));
		dc.add(Restrictions.eq("ordeType", "customize"));
		dc.add(Restrictions.isNull("menuEndtime"));
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	
	public MyQuery getOrderMenuById(String id) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ColumnInfo.class);
		dc.add(Restrictions.eq("id", id));
		
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery getOrderMenuByNick(String nickname) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ColumnInfo.class);
		dc.add(Restrictions.eq("colInfo", nickname));
		
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery getColumnInfoTime(String nickname) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ColumnInfoSendtime.class);
		dc.add(Restrictions.eq("columnInfo", nickname));
		
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery messageQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ColumnInfoMessage.class);
		
		String columnName = (String)dto.get("columnName");
		String columnInfo = (String)dto.get("columnInfo");
		
		if(!"".equals(columnInfo))
			dc.add(Restrictions.eq("columnInfo", columnInfo));
		if(!"".equals(columnName))
			dc.add(Restrictions.like("messageName", "%"+columnName+"%"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery getMessageInfo(String id) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ColumnInfoMessage.class);
		dc.add(Restrictions.eq("id", Integer.valueOf(id)));
		
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
