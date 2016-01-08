/**
 * 	@(#)SearchSearch.java   Oct 31, 2006 9:27:47 AM
 *	 �� 
 *	 
 */
package et.bo.pcc.phonesearch.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.PhoneSearch;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Oct 31, 2006
 * @see
 */
public class SearchSearch {
	
	/**
	 * @describe ��ѯ�绰��Ϣ
	 * @param dto
	 *            ���� IBaseDTO
	 * @param pi
	 *            ���� PageInfo
	 * @return ����
	 * 
	 */
	public MyQuery searchPhoneList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PhoneSearch.class);
		String num = (String)dto.get("num");
		if (num!=null&&!num.equals("")) {
			dc.add(Expression.like("num", "%"+num+"%"));
		}
		String unit = (String)dto.get("unit");
		if (unit!=null&&!unit.equals("")) {
			dc.add(Expression.like("unit", "%"+unit+"%"));
		}
		String dep = (String)dto.get("deprtment");
		if (dep!=null&&!dep.equals("")) {
			dc.add(Expression.like("deprtment", "%"+dep+"%"));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
}
