/*
 * @(#)CustinfoHelp.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.screen.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import et.po.ScreenQuickMessage;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * ÿ�տ�Ѷ������ѯ
 * </p>
 * 
 * @version 2008-03-19
 * @author wwq
 */
public class QuickMessageHelp extends MyQueryImpl {

	public MyQuery quickMessageQuery(IBaseDTO dto, PageInfo pi) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ScreenQuickMessage.class);

		String str = (String) dto.get("msgTitle");
		//����
		if (!str.equals("")) {
			 dc.add(Restrictions.like("msgTitle", "%" + str + "%"));
//			hql.append(" and custName like %" + str + "%");
		}
		// ��������
//		 ʱ��
		 String btime = (String) dto.get("beginTime");
		 String etime = (String) dto.get("endTime");
		 if(!"".equals(btime) && !"".equals(etime)) {
			 dc.add(Restrictions.between("createDate", TimeUtil.getTimeByStr(btime), TimeUtil.getTimeByStr(etime)));
		 }
		
		 dc.addOrder(Order.desc("createDate"));
		
		 mq.setDetachedCriteria(dc);
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
		if (!str.equals("1")&&!str.equals("")) {
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
	 * ��ѯ���еĿ�Ѷ
	 * 
	 * @param dto
	 * @param pi
	 * @return
	 */
	public MyQuery quickMessageAllQuery() {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ScreenQuickMessage.class);

		// dict_cust_type = SYS_TREE_0000000684 ��ר��
		
		dc.addOrder(Order.desc("createDate"));
		mq.setDetachedCriteria(dc);

		return mq;
	}
}
