/**
 * 	@(#)HandsetNoteSearch.java   Sep 26, 2006 3:19:35 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.handsetnote.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.HandsetNoteInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Sep 26, 2006
 * @see
 */
public class HandsetNoteSearch extends MyQueryImpl {

	// 成功删除(标志位)
	private String DEL_SUCCESS = "Y";

	// 未删除(标志位)
	private String DEL_FAIL = "N";

	/**
	 * <p>
	 * 根据手机信息查询
	 * </p>
	 * 
	 * @param info:根据新闻信息查询
	 * 
	 * @return:MyQuery mq
	 * 
	 * @throws
	 */

	public MyQuery searchHandsetInfo(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(HandsetNoteInfo.class);
		dc.add(Expression.eq("delSign", DEL_FAIL));
		if (dto.get("handsetnum") != null
				&& dto.get("handsetnum").toString().equals("")) {
			dc.add(Expression.like("handsetNum", "%"
					+ dto.get("handsetnum").toString() + "%"));
		}
		if (dto.get("handsetinfo") != null
				&& dto.get("handsetinfo").toString().equals("")) {
			dc.add(Expression.like("handsetInfo", "%"
					+ dto.get("handsetinfo").toString() + "%"));
		}
		if (dto.get("sendstate") != null) {
			if (dto.get("sendstate").equals("0")) {
				
			}else{
				dc.add(Expression.eq("sendState", dto.get("sendstate").toString()));
			}
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

}
