/**
 * 	@(#)FuzzSearch.java   Oct 9, 2006 2:19:57 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policefuzz.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.PoliceFuzzInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Oct 9, 2006
 * @see
 */
public class FuzzSearch extends MyQueryImpl{
	
	String tag_freeze_ok = "0";
	
	String tag_freeze_not = "1";
	
	/**
	 * @describe 警务人员信息
	 * @param dto
	 *            类型 IBaseDTO
	 * @param pi
	 *            类型 PageInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchFuzzList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String fuzzNo = (String)dto.get("fuzzNo");
		if (fuzzNo!=null&&!fuzzNo.equals("")) {
			dc.add(Expression.eq("fuzzNo", fuzzNo));
		}
		String policename = (String)dto.get("name");
		if (policename!=null&&!policename.trim().equals("")) {
			dc.add(Expression.like("name", "%"+policename+"%"));
		}
		//dc.add(Expression.eq("tagFreeze", tag_freeze_ok));
		dc.add(Expression.eq("tagDel", "N"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	/**
	 * @describe 警务人员信息
	 * @param dto
	 *            类型 IBaseDTO
	 * @param pi
	 *            类型 PageInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchCountList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String tagunit = (String)dto.get("tagUnit");
		if (tagunit.equals("0")) {
			
		}else{
			dc.add(Expression.eq("tagUnit", tagunit));
		}
		dc.add(Expression.isNotNull("password"));
		dc.add(Expression.eq("tagDel", "N"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	/**
	 * @describe 警务人员信息
	 * @param dto
	 *            类型 IBaseDTO
	 * @param pi
	 *            类型 PageInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchCountList(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String tagunit = (String)dto.get("tagUnit");
		if (tagunit.equals("0")) {
			
		}else{
			dc.add(Expression.eq("tagUnit", tagunit));
		}
		dc.add(Expression.isNotNull("password"));
		dc.add(Expression.eq("tagDel", "N"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 查询警号是否存在
	 * @param
	 * @version Oct 11, 2006
	 * @return
	 */
	public MyQuery searchFuzzList(String policeNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		dc.add(Expression.eq("fuzzNo", policeNum));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * 根据警号查询警务人员信息
	 * @param
	 * @version Oct 11, 2006
	 * @return
	 */
	public MyQuery searchFuzzInfo(String policeNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		if (policeNum!=null&&!policeNum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policeNum));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}

}
