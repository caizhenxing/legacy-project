/**
 * 	@(#)InfoSearch.java   Oct 10, 2006 10:36:50 AM
 *	 �� 
 *	 
 */
package et.bo.pcc.policeinfo.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.bo.sys.user.action.Password_encrypt;
import et.po.PoliceCallin;
import et.po.PoliceFuzzInfo;
import et.po.PoliceinfoTemp;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Oct 10, 2006
 * @see
 */
public class InfoSearch extends MyQueryImpl{
	
	/**
	 * @describe ������Ա��Ϣ
	 * @param dto
	 *            ���� IBaseDTO
	 * @param pi
	 *            ���� PageInfo
	 * @return ����
	 * 
	 */
	public MyQuery searchFuzzInfoByPoliceId(String policenum,String password) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceFuzzInfo.class);
		if (policenum!=null&&!policenum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policenum));
		}
		Password_encrypt pe = new Password_encrypt();
		password = pe.pw_encrypt(password);
		if (password!=null&&!password.equals("")) {
			dc.add(Expression.eq("password", password));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * ������ϸ��Ϣ��ѯ(���ݾ���)
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
	 * ��ѯ��Ϣ
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchInfoSize(String pcid) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceinfoTemp.class);
		dc.add(Expression.eq("PId", pcid));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * ��ѯ��Ϣ
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchInfoByY(String pcid) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceinfoTemp.class);
		dc.add(Expression.eq("PId", pcid));
		dc.add(Expression.eq("tag", "Y"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * ������ϸ��Ϣ��ѯ(���ݾ���)
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
	
	/**
	 * ������ϸ��Ϣ��ѯ
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchPoliceCallinInfo() {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallin.class);
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * ������ϸ��Ϣ��ѯ(���ݾ���)
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public MyQuery searchPoliceCallinInfo(String policenum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallin.class);
		if (policenum!=null&&!policenum.equals("")) {
			dc.add(Expression.eq("fuzzNo", policenum));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
