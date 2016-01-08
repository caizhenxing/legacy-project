/**
 * 
 */
package et.bo.pcc.policevalid.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.bo.sys.user.action.Password_encrypt;
import et.po.PoliceFuzzInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author Administrator
 *
 */
public class SearchPostInfo extends MyQueryImpl{
	/**
	 * @describe 警务人员信息
	 * @param dto
	 *            类型 IBaseDTO
	 * @param pi
	 *            类型 PageInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchFuzz(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String fuzznum =(String)dto.get("fuzzNo");
		if (fuzznum!=null&&!fuzznum.equals("")) {
			dc.add(Expression.eq("fuzzNo", fuzznum));
		}
		String name =(String)dto.get("name");
		if (name!=null&&!name.equals("")) {
			dc.add(Expression.eq("name", name));
		}
//		String idcard =(String)dto.get("idcard");
//		if (idcard!=null&&!idcard.equals("")) {
//			dc.add(Expression.eq("idCard", idcard));
//		}
		String unit =(String)dto.get("unit");
		if (unit!=null&&!unit.equals("")) {
			dc.add(Expression.eq("tagUnit", unit));
		}
		mq.setDetachedCriteria(dc);
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
	public MyQuery searchFuzzPwdEmpty(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String fuzznum =(String)dto.get("fuzzNo");
		if (fuzznum!=null&&!fuzznum.equals("")) {
			dc.add(Expression.eq("fuzzNo", fuzznum));
		}
		String name =(String)dto.get("name");
		if (name!=null&&!name.equals("")) {
			dc.add(Expression.eq("name", name));
		}
		String idcard =(String)dto.get("idcard");
		if (idcard!=null&&!idcard.equals("")) {
			dc.add(Expression.eq("idCard", idcard));
		}
		String unit =(String)dto.get("unit");
		if (unit!=null&&!unit.equals("")) {
			dc.add(Expression.eq("tagUnit", unit));
		}
//		Password_encrypt pe = new Password_encrypt();
//		String pwd = pe.pw_encrypt(dto.get("beforePwd").toString());
//		if(pwd!=null&&!pwd.equals("")){
		
			//dc.add(Expression.eq("", dto.get("beforePwd").toString()));
//		}
		mq.setDetachedCriteria(dc);
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
	public MyQuery getFuzz(IBaseDTO dto) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		String fuzznum =(String)dto.get("fuzzNo");
		if (fuzznum!=null&&!fuzznum.equals("")) {
			dc.add(Expression.eq("fuzzNo", fuzznum));
		}
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;
	}
}
