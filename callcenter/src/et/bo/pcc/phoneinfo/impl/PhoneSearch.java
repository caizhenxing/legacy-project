/**
 * 	@(#)PhoneSearch.java   Nov 7, 2006 9:54:09 AM
 *	 。 
 *	 
 */
package et.bo.pcc.phoneinfo.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.PoliceCallin;
import et.po.PoliceCallinInfo;
import et.po.PoliceFuzzInfo;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Nov 7, 2006
 * @see
 */
public class PhoneSearch extends MyQueryImpl{
	/**
	 * 警务详细信息查询(根据警号)
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchFuzzInfo(String policenum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		if (policenum!=null&&!policenum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policenum));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 查询警务人员主表信息
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public MyQuery searchPoliceCallin(String policenum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallin.class);
		if (policenum!=null&&!policenum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policenum));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 查询信息
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchInfoSize(PoliceCallin pc) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallinInfo.class);
		dc.add(Expression.eq("policeCallin", pc));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 警务详细信息查询(根据警号)
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchDepartment(String policenum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		if (policenum!=null&&!policenum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policenum));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
