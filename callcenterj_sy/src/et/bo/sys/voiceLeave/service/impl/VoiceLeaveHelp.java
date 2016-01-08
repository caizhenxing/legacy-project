package et.bo.sys.voiceLeave.service.impl;

/*
 * @(#)CustinfoHelp.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.CcVoiceLeave;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>客户管理——查询</p>
 * 
 * @version 2008-03-19
 * @author 王文权
 */
public class VoiceLeaveHelp extends MyQueryImpl{
	
	public MyQuery voiceLeaveQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		try
		{
//			DetachedCriteria dc = DetachedCriteria.forClass(CcVoiceLeave.class);
//			
//			
//			//开始日期
//			String str = (String)dto.get("beginTime");
//			if(str!=null&&("".equals(str.trim())==false)){
//				dc.add(Restrictions.like("beginTime","%"+str+"%"));
//			}
//			//结束日期
//			String endTime = (String)dto.get("endTime");
//			if(endTime!=null&&("".equals(endTime.trim())==false)){
//				dc.add(Restrictions.like("endTime","%"+endTime+"%"));
//			}
//			//是否处理
//			String ifDispose = (String)dto.get("ifDispose");
//			if(ifDispose!=null&&("0".equals(ifDispose.trim())==false))
//			{
//				dc.add(Restrictions.eq("ifDispose", ifDispose));
//			}
//			dc.addOrder(Order.desc("beginTime"));
//			mq.setDetachedCriteria(dc);
			StringBuffer sb = new StringBuffer("from CcVoiceLeave a ");
			StringBuffer wSb = new StringBuffer("");
			int count = 0;
			//开始日期
			String beginTime = (String)dto.get("beginTime");
			if(beginTime!=null&&("".equals(beginTime.trim())==false)){
				wSb.append(" a.beginTime like '"+beginTime+"%' ");
				count ++;
			}
			//结束日期
			String endTime = (String)dto.get("endTime");
			if(endTime!=null&&("".equals(endTime.trim())==false)){
				if(count != 0)
				{
					wSb.append(" and a.endTime like '"+endTime+"%' ");
				}
				else
				{
					wSb.append(" a.endTime like '"+endTime+"%' ");
				}
				count++;
			}
			//是否处理
			String ifDispose = (String)dto.get("ifDispose");
			if(ifDispose!=null&&("".equals(ifDispose.trim())==false))
			{
				if(count != 0)
				{
					wSb.append(" and a.ifDispose = '"+ifDispose+"' ");
				}
				else
				{
					wSb.append(" a.ifDispose = '"+ifDispose+"' ");
				}
			}
			//语音节点
			String ivrtype = (String)dto.get("ivrtypeId");
			if(ivrtype!=null&&!"".equals(ivrtype.trim()))
			{
				if(count != 0)
				{
					wSb.append(" and a.ivrtype = '"+ivrtype+"' ");
				}
				else
				{
					wSb.append(" a.ivrtype = '"+ivrtype+"' ");
				}
			}
			String whereSql = wSb.toString();
			
			String hql = sb.toString();
			if(!"".equals(whereSql.trim()))
			{
				hql = hql + " where " + whereSql;
			}
			hql = hql + " order by a.beginTime desc ";
			mq.setHql(hql);
			//System.out.println(pi.getBegin()+":"+pi.getPageSize());
			mq.setFirst(pi.getBegin());
			mq.setFetch(pi.getPageSize());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mq;
	}
	
}
