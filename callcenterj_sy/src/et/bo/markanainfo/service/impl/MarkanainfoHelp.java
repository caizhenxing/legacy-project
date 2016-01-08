package et.bo.markanainfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import et.po.OperFocusinfo;
import et.po.OperMarkanainfo;
import et.po.OperPriceinfo;

import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class MarkanainfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery markanainfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperMarkanainfo.class);
		
		String createTime = (String)dto.get("createTime");//出版日期		
		String chiefTitle =(String)dto.get("chiefTitle");//主标题
		String dictCommentType = (String)dto.get("dictCommentType");//评别
		String chargeEditor = (String)dto.get("chargeEditor");//责任编辑
		String summary = (String)dto.get("summary");//摘要
		String dictProductType = (String)dto.get("dictProductType");//品种
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
		if(!chiefTitle.equals("")){
			dc.add(Restrictions.like("chiefTitle","%"+chiefTitle +"%"));
		}
		if(!chargeEditor.equals("")){
			dc.add(Restrictions.like("chargeEditor",chargeEditor));
		}
		if(!summary.equals("")){
			dc.add(Restrictions.like("summary", "%"+summary+"%"));
		}
		if(!dictCommentType.equals("")){
			dc.add(Restrictions.eq("dictCommentType",dictCommentType));
		}
		if(!dictProductType.equals("")){
			dc.add(Restrictions.eq("dictProductType",dictProductType));
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
