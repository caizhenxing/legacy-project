/*
 * @(#)FixedContactHelp.java	 2008-06-10
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
 * 固定联络员—查询
 * </p>
 * @version 2008-06-06
 * @author 王默
 */
public class FixedContactHelp extends MyQueryImpl
{
	public MyQuery FixedContactQuery(IBaseDTO dto, PageInfo pi)
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// 首先确定“未删除”条件
		dc.add(Restrictions.eq("isDelete", "0"));

		// 查询oper_custinfo表中的"dict_cust_type"字段值以83为结尾的条件
		String custType = (String) dto.get("cust_type");//对应JSP表单域名
		dc.add(Restrictions.eq("dictCustType", custType));

		// 姓名
		String str = (String) dto.get("cust_name");
		if (!str.equals(""))
		{
			dc.add(Restrictions.like("custName", "%" + str + "%"));
		}
		// 电话，or宅电，or手机，or办公电话
		str = (String) dto.get("cust_tel_home");
		if (!str.equals(""))
		{
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + str + "%"), Restrictions.or(
					Restrictions.like("custTelMob", "%" + str + "%"), Restrictions.like("custTelWork", "%"
							+ str + "%"))));
		}
		// 客户地址
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

		// 首先确定“未删除”条件
		dc.add(Restrictions.eq("isDelete", "0"));

		mq.setDetachedCriteria(dc);

		return mq;
	}
}
