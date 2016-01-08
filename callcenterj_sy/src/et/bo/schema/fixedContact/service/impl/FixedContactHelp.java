/*
 * @(#)FixedContactHelp.java	 2008-06-10
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.schema.fixedContact.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import et.po.OperCustinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * �̶�����Ա����ѯ
 * </p>
 * @version 2008-06-06
 * @author ��Ĭ
 */
public class FixedContactHelp extends MyQueryImpl
{
	public MyQuery FixedContactQuery(IBaseDTO dto, PageInfo pi)
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// ����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete", "0"));

		// ��ѯoper_custinfo���е�"dict_cust_type"�ֶ�ֵ��83Ϊ��β������
		String custType = (String) dto.get("cust_type");//��ӦJSP������
		dc.add(Restrictions.eq("dictCustType", custType));

		// ����
		String str = (String) dto.get("cust_name");
		if (!str.equals(""))
		{
			dc.add(Restrictions.like("custName", "%" + str + "%"));
		}
		// �绰��orլ�磬or�ֻ���or�칫�绰
		str = (String) dto.get("cust_tel_home");
		if (!str.equals(""))
		{
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + str + "%"), Restrictions.or(
					Restrictions.like("custTelMob", "%" + str + "%"), Restrictions.like("custTelWork", "%"
							+ str + "%"))));
		}
		// �ͻ���ַ
		str = (String) dto.get("cust_addr");
		if (!str.equals(""))
		{
			dc.add(Restrictions.like("custAddr", "%" + str + "%"));
		}

		dc.addOrder(Order.desc("custId"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}

	public MyQuery openwinQuery(String tel)
	{

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		if (!tel.equals(""))
		{
			dc.add(Restrictions.or(Restrictions.eq("custTelHome", tel), Restrictions.or(Restrictions.eq(
					"custTelMob", tel), Restrictions.eq("custTelWork", tel))));
		}

		mq.setDetachedCriteria(dc);

		return mq;
	}

	public MyQuery allQuery()
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// ����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete", "0"));

		mq.setDetachedCriteria(dc);

		return mq;
	}
}
