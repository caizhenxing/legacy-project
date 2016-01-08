/**
 * 	@(#)ForumLogHelp.java   2007-1-8 上午11:16:04
 *	 。 
 *	 
 */
package et.bo.forum.log.service.impl;

import java.sql.Time;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.ForumLog;
import et.po.ForumUserInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 叶浦亮
 * @version 2007-1-8
 * @see
 */
public class ForumLogHelp extends MyQueryImpl {
	/**
	 * 查询日志
	 * @param
	 * @version 2007-1-8
	 * @return MyQuery
	 */
	public MyQuery logQuery(IBaseDTO dto, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumLog.class);
		String userId = (String)dto.get("userId");
		if(!userId.equals("")){
			dc.add(Restrictions.eq("userId", userId));
		}
		String act = (String)dto.get("act");
		if(!act.equals("")){
			dc.add(Restrictions.eq("action", act));
		}
		String ip = (String)dto.get("ip");
		if(!ip.equals("")){
			dc.add(Restrictions.eq("ip", ip));
		}
		String beginTime = (String)dto.get("beginTime");
		if(!beginTime.equals("")){
			dc.add(Restrictions.gt("operTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		String endTime = (String)dto.get("endTime");
		if(!endTime.equals("")){
			dc.add(Restrictions.lt("operTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		String moduleName = (String)dto.get("moduleName");
		if(!moduleName.equals("")){
			dc.add(Restrictions.eq("moduleName", moduleName));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;       
	}
}
