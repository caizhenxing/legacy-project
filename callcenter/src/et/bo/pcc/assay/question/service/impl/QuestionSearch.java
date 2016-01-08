/**
 * 	@(#)QuestionSearch.java   Oct 11, 2006 1:42:56 PM
 *	 。 
 *	 
 */
package et.bo.pcc.assay.question.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.PoliceFuzzInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public class QuestionSearch extends MyQueryImpl {

	/**
	 * @describe 根据问题内容查询
	 * @param 类型
	 *            InemailInfo inemailInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchQuestion(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(PoliceCallinInfo.class);
		// String tagtype = (String)dto.get("taginfo");
		// if (tagtype!=null&&!tagtype.trim().equals("")) {
		// if (tagtype.equals("0")) {
		//				
		// }else{
		// dc.add(Expression.eq("tagInfo","tagtype"));
		// }
		// }
		// String question = (String)dto.get("quinfo");
		// if (question!=null&&!question.trim().equals("")) {
		// dc.add(Expression.like("quInfo","%"+question+"%"));
		// }
		// mq.setDetachedCriteria(dc);
		StringBuilder hql = new StringBuilder();
		hql
				.append("select pcii from PoliceCallinInfo pcii,PoliceCallin pci,CcLog c where pci.id=pcii.policeCallin and pci.id=c.id");
		String quinfo = (String) dto.get("quinfo");
		if (quinfo != null && !quinfo.trim().equals("")) {
			hql.append(" and pcii.quInfo like '" + quinfo + "'");
		}
		String taginfo = (String) dto.get("taginfo");
		if (taginfo != null && !taginfo.trim().equals("")) {
			if (taginfo.equals("0")) {

			} else {
				hql.append(" and pcii.tagInfo = '" + taginfo + "'");
			}
		}
		String fuzzno = (String) dto.get("fuzzno");
		if (fuzzno != null && !fuzzno.trim().equals("")) {
			hql.append(" and pci.fuzzNo = '" + fuzzno + "'");
		}
		if (!dto.get("begintime").toString().equals("")){
			hql.append(" and c.beginTime>=to_date('"+dto.get("begintime").toString()+"','yyyy-MM-dd') ");
		}
		if (!dto.get("endtime").toString().equals("")){
			hql.append(" and c.beginTime<=to_date('"+dto.get("endtime").toString()+"','yyyy-MM-dd') ");
		}
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * 根据警号查询警务人员信息
	 * 
	 * @param
	 * @version Oct 11, 2006
	 * @return
	 */
	public MyQuery searchFuzzInfo(String policeNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		if (policeNum != null && !policeNum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policeNum));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}

	/**
	 * 根据警号查询信息
	 * 
	 * @param
	 * @version Oct 11, 2006
	 * @return
	 */
	public MyQuery searchFuzzInfoById(String policeNum, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String hql = "select pcii from PoliceCallin pci inner join PoliceCallinInfo pcii on pci.id=pcii.policeCallin where pci.fuzzNo='"
				+ policeNum + "'";
		mq.setHql(hql);
		// mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

}
