/**
 * 	@(#)SearchHelp.java   2006-12-21 ÏÂÎç02:07:05
 *	 ¡£ 
 *	 
 */
package et.bo.forum.search.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ForumPosts;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhangfeng
 * @version 2006-12-21
 * @see
 */
public class SearchHelp extends MyQueryImpl {
	public MyQuery postListQuery(IBaseDTO dto, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eqProperty("id", "parentId"));
		String title = (String)dto.get("title");
		if(!title.equals("")){
			dc.add(Restrictions.like("title", "%"+title+"%"));
		}
		String itemId = (String)dto.get("itemId");
		System.out.println("ITEMID  :  "+itemId);
		if(!itemId.equals("")){
			dc.add(Restrictions.eq("itemId", itemId));
		}
		String dateType = (String)dto.get("dateType");
		System.out.println("dateType  :  "+dateType);
		if(!dateType.equals("")){
			try {
				dc.add(Restrictions.ge("postAt", getDate(dateType)));
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("#########################");
			}
		}
		String userkey = (String)dto.get("userkey");
		if(!userkey.equals("")){
			dc.add(Restrictions.eq("userkey", userkey));
		}
		dc.addOrder(Order.desc("postAt"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;     
	}
	private Date getDate(String dateType){
		Calendar ca = Calendar.getInstance();
		Date date = new Date();
		Date today = TimeUtil.getNowTime();
		ca.setTime(today);
		if(dateType.equals("day")){
			ca.add(ca.DATE,-1);
		}else if(dateType.equals("week")){
			ca.add(ca.DAY_OF_WEEK, -7);
		}else if(dateType.equals("month")){
			ca.add(ca.DAY_OF_MONTH, -30);
		}else if(dateType.equals("twoMonth")){
			ca.add(ca.DAY_OF_MONTH, -61);
		}else if(dateType.equals("threeMonth")){
			ca.add(ca.DAY_OF_MONTH, -92);
		}
		date = ca.getTime();
		System.out.println(" Ê±¼ä·¶Î§ "+TimeUtil.getTheTimeStr(date, "yyyy-MM-dd"));
		return date;
	}

}
