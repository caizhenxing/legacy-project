package et.bo.pcc.callinFirewall.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperatorWorkInfo;
import et.po.PoliceCallinFirewall;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class CallinFirewallHelp extends MyQueryImpl {
	/**
	 * @describe �����б��ѯ
	 */
	public MyQuery callinFirewallQuery(IBaseDTO dto,PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(PoliceCallinFirewall.class);
		if(!dto.get("callinNumBegin").toString().equals("")&&dto.get("callinNumEnd").toString().equals("")){
			dc.add(Restrictions.like("callinNumBegin", "%"+dto.get("callinNumBegin").toString()+"%"));
		}
//		else if(!dto.get("callinNumBegin").toString().equals("")&&!dto.get("callinNumEnd").toString().equals("")){
//			dc.add(Restrictions.between("", arg1, arg2))
//		}
		
		if(!dto.get("isPass").toString().equals("")){
			dc.add(Restrictions.eq("isPass", dto.get("isPass").toString()));
		}
		if(!dto.get("isAvailable").toString().equals("")){
			dc.add(Restrictions.eq("isAvailable", dto.get("isAvailable").toString()));
		}
		mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
	}   
	/**
	 * @describe �����Ƿ��ں�������
	 */
	public MyQuery IfInBlacklist(String phoneNum){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(PoliceCallinFirewall.class);
		dc.add(Restrictions.eq("callinNumBegin", phoneNum));
//		dc.add(Restrictions.ge("callinNumBegin", phoneNum));
//		dc.add(Restrictions.le("callinNumEnd", phoneNum));
		dc.add(Restrictions.eq("isPass", "N"));
		dc.add(Restrictions.eq("isAvailable", "Y"));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
        return mq;
	}   
	/**
	 * @describe �Ƿ�����ͬ����
	 */
	public MyQuery ifHaveSameNum(String phoneNum){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(PoliceCallinFirewall.class);
		dc.add(Restrictions.eq("callinNumBegin", phoneNum));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;       
	}   
}
