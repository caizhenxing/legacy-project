/**
 * 	@(#)UserManagerHelp.java   2006-12-25 下午01:50:28
 *	 。 
 *	 
 */
package et.bo.forum.userManager.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ForumUserInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 叶浦亮
 * @version 2006-12-25
 * @see
 */
public class UserManagerHelp extends MyQueryImpl{
	/**
	 * 查询用户列表
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery userListQuery(IBaseDTO dto, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumUserInfo.class);
		String userId = (String)dto.get("id");
		if(!userId.equals("")){
			System.out.println(userId);
			dc.add(Restrictions.eq("id", userId));
		}
		String email = (String)dto.get("email");
		if(!email.equals("")){
			dc.add(Restrictions.eq("email", email));
		}
		String beginTime = (String)dto.get("beginTime");
		if(!beginTime.equals("")){
			dc.add(Restrictions.lt("registerDate", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		String endTime = (String)dto.get("endTime");
		if(!endTime.equals("")){
			dc.add(Restrictions.gt("registerDate", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		String beginPoint = (String)dto.get("beginPoint");
		if(!beginPoint.equals("")){
			dc.add(Restrictions.lt("point", beginPoint));
		}
		String endPoint = (String)dto.get("endPoint");
		if(!endPoint.equals("")){
			dc.add(Restrictions.gt("point", endPoint));
		}
		String beginSendPost = (String)dto.get("beginSendPost");
		if(!beginSendPost.equals("")){
			dc.add(Restrictions.lt("sendPostNum", Integer.parseInt(beginSendPost)));
		}
		String endSendPost = (String)dto.get("endSendPost");
		if(!endSendPost.equals("")){
			dc.add(Restrictions.gt("sendPostNum", Integer.parseInt(endSendPost)));
		}
		String lastLogin = (String)dto.get("noLoginDate");
		if(!lastLogin.equals("")){
			Date loginDate = getDate(Integer.parseInt(lastLogin));
			dc.add(Restrictions.lt("lastLogin", loginDate));
		}		
		dc.addOrder(Order.asc("registerDate"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;       
	}
	
	
	private Date getDate(int num){
		Calendar ca = Calendar.getInstance();
		Date date = new Date();
		Date today = TimeUtil.getNowTime();
		ca.setTime(today);	
		ca.add(ca.DATE,-num);
		date = ca.getTime();
		System.out.println(" 时间范围 "+TimeUtil.getTheTimeStr(date, "yyyy-MM-dd"));
		return date;
	}
}
