package et.bo.corpinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import et.po.OperCorpinfo;
import et.po.OperPriceinfo;

import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class CorpinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery corpinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCorpinfo.class);
		
		String createTime=dto.get("createTime").toString();
		String endTime=dto.get("endTime").toString();
		String corpRid=dto.get("corpRid").toString();
		String reply = (String)dto.get("reply");
		String contents = (String)dto.get("contents");
		String dictServiceType = (String)dto.get("dictServiceType");
		
		if(!endTime.equals("")){
			dc.add(Restrictions.lt("createtime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if(!createTime.equals("")){
			dc.add(Restrictions.gt("createtime", TimeUtil.getTimeByStr(createTime, "yyyy-MM-dd")));
		}
		if(!corpRid.equals("")){
			dc.add(Restrictions.like("corpRid", "%"+corpRid+"%"));
		}
		if(!contents.equals("")){
			dc.add(Restrictions.like("contents", "%"+contents+"%"));
		}
		if(!reply.equals("")){
			dc.add(Restrictions.like("reply", "%"+reply+"%"));
		}
		if(!dictServiceType.equals("")){
			dc.add(Restrictions.eq("dictServiceType", dictServiceType));
		}
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		dc.addOrder(Order.desc("createtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
