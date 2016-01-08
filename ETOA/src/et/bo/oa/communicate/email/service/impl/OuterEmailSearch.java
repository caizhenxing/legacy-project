/**
 * 	@(#)OuterEmailSearch.java   Sep 8, 2006 1:33:40 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.email.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.EmailBox;
import et.po.InemailInfo;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Sep 8, 2006
 * @see
 */
public class OuterEmailSearch extends MyQueryImpl {

	// 标识为发件箱
	private String EMAIL_TYPE_GETBOX = "1";

	// 标识为内网邮件
	private String EMAIL_IN = "1";

	// 标识为外网邮件
	private String EMAIL_OUT = "2";

	/**
	 * @describe 查询邮箱列表信息
	 * @param userkey
	 *            类型 String
	 * @return 类型
	 * 
	 */

	public MyQuery searchEmailBoxList(SysUser sys) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(EmailBox.class);
		dc.add(Expression.eq("sysUser", sys));
		mq.setDetachedCriteria(dc);
		return mq;
	}

	/**
	 * @describe 收件箱
	 * @param 类型
	 * @return 类型
	 * 
	 */

	public MyQuery searchGetEmailList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
		dc.add(Expression.eq("delSign", "n".toUpperCase()));
		dc.add(Expression.eq("emailType", EMAIL_TYPE_GETBOX));
		dc.add(Expression.eq("inorout", EMAIL_OUT));
		dc.add(Expression.eq("emailboxId", dto.get("emailboxid")));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * @describe 发件箱
	 * @param 类型
	 * @return 类型
	 * 
	 */

	public MyQuery searchSendEmailList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
		dc.add(Expression.eq("delSign", "n".toUpperCase()));
		dc.add(Expression.eq("emailType", "2"));
		dc.add(Expression.eq("inorout", "1"));
		// 
		dc.add(Expression.eq("sendUser", dto.get("sendUser").toString()));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * @describe 草稿箱
	 * @param 类型
	 * @return 类型
	 * 
	 */

	public MyQuery searchDraftEmailList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
		dc.add(Expression.eq("delSign", "n".toUpperCase()));
		dc.add(Expression.eq("emailType", "3"));
		dc.add(Expression.eq("inorout", "1"));
		dc.add(Expression.eq("sendUser", dto.get("sendUser").toString()));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * @describe 垃圾箱
	 * @param 类型
	 * @return 类型
	 * 
	 */

	public MyQuery searchDelEmailList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
		dc.add(Expression.eq("delSign", "y".toUpperCase()));
		// dc.add(Expression.eq("emailType", "4"));
		dc.add(Expression.eq("inorout", "1"));
		dc.add(Expression.eq("sendUser", dto.get("sendUser").toString()));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

}
