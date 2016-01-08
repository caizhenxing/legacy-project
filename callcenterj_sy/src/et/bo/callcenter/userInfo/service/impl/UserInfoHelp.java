/*
 * @(#)CustinfoHelp.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.callcenter.userInfo.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>�ͻ���������ѯ</p>
 * 
 * @version 2008-03-19
 * @author nie
 */
public class UserInfoHelp extends MyQueryImpl{
	
	public MyQuery custinfoQuery(IBaseDTO dto, PageInfo pi){
//		
//		MyQuery mq = new MyQueryImpl();
//		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
//		
//		//����ȷ����δɾ��������
//		dc.add(Restrictions.eq("isDelete","0"));
//		
//		//����
//		String str = (String)dto.get("cust_name");
//		if(!str.equals("")){
//			dc.add(Restrictions.like("custName","%"+str+"%"));
//		}
//		//�绰��orլ�磬or�ֻ���or�칫�绰
////		String telType = (String)dto.get("tel_type");
////		if(!telType.equals("mobile")){
//////			dc.add(Restrictions.or(Restrictions.like("custTelHome","%"+str+"%"),
//////				   Restrictions.or(Restrictions.like("custTelMob","%"+str+"%"),
//////							       Restrictions.like("custTelWork","%"+str+"%")
//////				   )));
////			dc.add(Restrictions.like("", arg1))
////		}
//		//�ͻ���ҵ
//		str = (String)dto.get("cust_voc");
//		if(!str.equals("")){
//			dc.add(Restrictions.like("dictCustVoc","%"+str+"%"));
//		}
//		//�ͻ�����
//		str = (String)dto.get("cust_type");
//		if(!str.equals("")){
//			dc.add(Restrictions.eq("dictCustType",str));
//		}
//		dc.addOrder(Order.desc("custId"));
//		mq.setDetachedCriteria(dc);
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
//		
//		return mq;

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// StringBuilder hql = new StringBuilder();
		// hql.append("from OperCustinfo where 1=1");

		// ����ȷ����δɾ��������
		// dc.add(Restrictions.eq("isDelete", "0"));
		String username = (String) dto.get("cust_name");
//		// ����
		if (!username.equals("")) {
			dc.add(Restrictions.like("custName", "%" + username + "%"));
//			// hql.append(" and custName like %" + username + "%");
		}
		
//		//�ͻ���ҵ
		String str = (String)dto.get("cust_voc");
		if(!str.equals("")){
			dc.add(Restrictions.like("dictCustVoc","%"+str+"%"));
		}
//		String telphone = (String) dto.get("cust_tel_home");
//		// �绰��orլ�磬or�ֻ���or�칫�绰
//		if (!telphone.equals("")) {
//			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%"
//					+ telphone + "%"), Restrictions.or(Restrictions.like(
//					"custTelMob", "%" + telphone + "%"), Restrictions.like(
//					"custTelWork", "%" + telphone + "%"))));
//			// hql.append(" or custTelHome like %" + telphone
//			// + "% or custTelMob like %" + telphone
//			// + "% or custTelWork like %" + telphone + "%");
//		}
//		// �ͻ�����
		String custType = (String) dto.get("cust_type");
		if (!custType.equals("")) {
			dc.add(Restrictions.like("dictCustType", "%" + custType + "%"));
//			// hql.append(" and dictCustType = '" + custType + "'");
		}
//		// �ͻ�����
//		// str = (String) dto.get("cust_type");
//		// if (!str.equals("0")) {
//		// // dc.add(Restrictions.eq("dictCustType", str));
//		// hql.append(" and dictCustType = " + str);
//		// }
		dc.addOrder(Order.desc("custId"));
		// hql.append(" order by custId desc");
		 mq.setDetachedCriteria(dc);
		// mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}
	
	public MyQuery openwinQuery(String tel){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		
		if(!tel.equals("")){
			dc.add(Restrictions.or(Restrictions.eq("custTelHome",tel),
				   Restrictions.or(Restrictions.eq("custTelMob",tel),
							       Restrictions.eq("custTelWork",tel)
				   )));
		}
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	
	public MyQuery allQuery(){
			
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		
		//����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete","0"));
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
}
