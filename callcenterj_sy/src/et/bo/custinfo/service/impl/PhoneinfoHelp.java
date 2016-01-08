/*
 * @(#)CustinfoHelp.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.custinfo.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * �ͻ���������ѯ
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */
public class PhoneinfoHelp extends MyQueryImpl {

	public MyQuery custinfoQuery(IBaseDTO dto, PageInfo pi) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// StringBuilder hql = new StringBuilder();
		// hql.append("from OperCustinfo where 1=1");

		// ����ȷ����δɾ��������
		// dc.add(Restrictions.eq("isDelete", "0"));
		String username = (String) dto.get("user_name");
		// ����
		if (!username.equals("")) {
			dc.add(Restrictions.like("custName", "%" + username + "%"));
			// hql.append(" and custName like %" + username + "%");
		}
		String telphone = (String) dto.get("cust_tel_home");
		// �绰��orլ�磬or�ֻ���or�칫�绰
		if (!telphone.equals("")) {
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%"
					+ telphone + "%"), Restrictions.or(Restrictions.like(
					"custTelMob", "%" + telphone + "%"), Restrictions.like(
					"custTelWork", "%" + telphone + "%"))));
			// hql.append(" or custTelHome like %" + telphone
			// + "% or custTelMob like %" + telphone
			// + "% or custTelWork like %" + telphone + "%");
		}
		// �ͻ�����
		String custType = (String) dto.get("dict_cust_type");
		if (!custType.equals("")) {
			dc.add(Restrictions.like("dictCustType", "%" + custType + "%"));
			// hql.append(" and dictCustType = '" + custType + "'");
		}
		// �û���ַ 
		String custAddr = (String) dto.get("cust_addr");
		if (!custAddr.equals("")) {
			dc.add(Restrictions.like("custAddr", "%"+custAddr+"%"));
		}
		//������
		String custRid = (String) dto.get("cust_rid");
		if (!custRid.equals("")) {
			dc.add(Restrictions.eq("custRid",custRid));
		}		
		
//		//ʱ��
//		String cust_develop_time = (String)dto.get("cust_develop_time");
//		String cust_positive_time = (String)dto.get("cust_positive_time");
//		if(!cust_positive_time.equals("")){
//			dc.add(Restrictions.ge("cust_develop_time",cust_develop_time));
//		}
//		if(!cust_develop_time.equals("")){
//			dc.add(Restrictions.le("cust_positive_time",cust_positive_time));
//		}
		// �ͻ�����
		// str = (String) dto.get("cust_type");
		// if (!str.equals("0")) {
		// // dc.add(Restrictions.eq("dictCustType", str));
		// hql.append(" and dictCustType = " + str);
		// }
		dc.addOrder(Order.desc("custId"));
		// hql.append(" order by custId desc");
		 mq.setDetachedCriteria(dc);
		// mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}

	public MyQuery phoneinfoQuery(IBaseDTO dto, PageInfo pi) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// ����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete", "0"));

		// ����
		String str = (String) dto.get("cust_name");
		if (!str.equals("")) {
			dc.add(Restrictions.like("custName", "%" + str + "%"));
		}
		// �绰��orլ�磬or�ֻ���or�칫�绰
		str = (String) dto.get("cust_tel_home");
		if (!str.equals("")) {
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + str
					+ "%"), Restrictions.or(Restrictions.like("custTelMob", "%"
					+ str + "%"), Restrictions.like("custTelWork", "%" + str
					+ "%"))));
		}
		// �ͻ���ҵ
		str = (String) dto.get("cust_voc");
		if (!str.equals("")) {
			dc.add(Restrictions.like("dictCustVoc", "%" + str + "%"));
		}
		// �ͻ�����
		str = (String) dto.get("cust_type");
		if (!str.equals("1")) {
			dc.add(Restrictions.eq("dictCustType", str));
		}
		// dc.add(Restrictions.or(Restrictions.eq("dictCustType", ""),
		// Restrictions.eq("dictCustType", "")));
		
	
		dc.addOrder(Order.desc("custId"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}

	public MyQuery openwinQuery(String tel) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		if (!tel.equals("")) {
			dc.add(Restrictions.or(Restrictions.eq("custTelHome", tel),
					Restrictions.or(Restrictions.eq("custTelMob", tel),
							Restrictions.eq("custTelWork", tel))));
		}

		mq.setDetachedCriteria(dc);

		return mq;
	}

	public MyQuery allQuery() {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// ����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete", "0"));

		mq.setDetachedCriteria(dc);

		return mq;
	}

	/**
	 * ��ѯ���е�ר��
	 * 
	 * @param dto
	 * @param pi
	 * @return
	 */
	public MyQuery custinfoExpertAllQuery() {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// ����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete", "0"));
		// dict_cust_type = SYS_TREE_0000000684 ��ר��
		dc.add(Restrictions.like("dictCustType", "SYS_TREE_0000000684"));
		dc.addOrder(Order.desc("expertType"));
		dc.addOrder(Order.asc("custName"));
		mq.setDetachedCriteria(dc);

		return mq;
	}
	
/*��ʼ����*/
	
	public MyQuery custinfoQuery2(IBaseDTO dto, PageInfo pi) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		dc.add(Restrictions.eq("dictCustType", "SYS_TREE_0000002108"));
		
		String username = (String) dto.get("user_name");
		// ����
		if (!username.equals("")) {
			dc.add(Restrictions.like("custName", "%" + username + "%"));
		}
		String telphone = (String) dto.get("cust_tel_home");
		// �绰��orլ�磬or�ֻ���or�칫�绰
		if (!telphone.equals("")) {
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%"
					+ telphone + "%"), Restrictions.or(Restrictions.like(
					"custTelMob", "%" + telphone + "%"), Restrictions.like(
					"custTelWork", "%" + telphone + "%"))));
		}
		// �û���ַ 
		String custAddr = (String) dto.get("cust_addr");
		if (!custAddr.equals("")) {
			dc.add(Restrictions.like("custAddr", "%"+custAddr+"%"));
		}
		//������
		String custRid = (String) dto.get("cust_rid");
		if (!custRid.equals("")) {
			dc.add(Restrictions.eq("custRid",custRid));
		}				
		//ʱ��
		String cust_develop_time = (String)dto.get("cust_develop_time");
		String cust_number = (String)dto.get("cust_number");
		//����Ա���
		if(!cust_number.equals("")){
			dc.add(Restrictions.like("custNumber", "%"+cust_number+"%"));
//			dc.add(Restrictions.ge("custDevelopTime",TimeUtil.getTimeByStr(cust_develop_time, "yyyy-MM-dd")));
		}
		if(!cust_develop_time.equals("")){
			dc.add(Restrictions.le("custDevelopTime",TimeUtil.getTimeByStr(cust_develop_time, "yyyy-MM-dd")));
		}
		//�û����
		String cust_way_by = (String)dto.get("cust_way_by");
		if(!"".equals(cust_way_by)){
			dc.add(Restrictions.like("custWayBy", "%"+cust_way_by+"%"));
		}
		//������Ŀ
		String productName = (String)dto.get("productName");
		if(!"".equals(productName)){
			dc.add(Restrictions.like("custJob", "%"+productName+"%"));
		}
		//������ʽ
		String cust_work_way = (String)dto.get("cust_work_way");
		if(!"".equals(cust_work_way)){
			dc.add(Restrictions.like("custWorkWay", "%"+cust_work_way+"%"));
		}
		//��̭���
		String is_eliminate = (String)dto.get("is_eliminate");
		if(!"".equals(is_eliminate)){
			dc.add(Restrictions.eq("isEliminate",is_eliminate));
		}
		//��̭ԭ��
		String eliminate_reason = (String)dto.get("eliminate_reason");
		if(!"".equals(eliminate_reason)){
			dc.add(Restrictions.like("eliminateReason", "%"+eliminate_reason+"%"));
		}
		dc.addOrder(Order.desc("custId"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}
/*��������*/
}
