package et.bo.pcc.portCompare.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.PortCompare;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class PortCompareHelp extends MyQueryImpl {
	/**
	 * @describe ȡ�ö˿ڶ��ձ����м�¼
	 */
	public MyQuery portAndIpQuery(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(PortCompare.class);
		mq.setDetachedCriteria(dc);
		return mq;       
	}   
	/**
	 * @describe �ж��Ƿ�����ͬ�˿�
	 */
	public MyQuery isHaveSamePort(String port){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(PortCompare.class);
		dc.add(Restrictions.eq("physicsPort", port));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;       
	}   
	/**
	 * @describe �ж��Ƿ�����ͬIp
	 */
	public MyQuery isHaveSameIp(String ip){
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PortCompare.class);
		dc.add(Restrictions.eq("ip", ip));
		mq.setFetch(1);
		mq.setDetachedCriteria(dc);
		return mq;
	}
	/**
	 * @describe ȡ�ö˿ڶ��ձ��б�
	 */
	public MyQuery portCompareQuery(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(PortCompare.class);
//		dc.add(Restrictions.eq("physicsPort", port));
//		if(!dto.get("physicsPort").toString().equals("")){
//			dc.add(Restrictions.like("physicsPort", "%"+dto.get("physicsPort").toString()+"%"));
//		}
//		if(!dto.get("ip").toString().equals("")){
//			dc.add(Restrictions.like("ip", "%"+dto.get("ip").toString()+"%"));
//		}
		dc.addOrder(Order.asc("addDate"));
		mq.setDetachedCriteria(dc);
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
	/**
	 * @describe �ж��Ƿ�����ͬIP
	 */
	public MyQuery HaveSameIp(String port,String ip){
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PortCompare.class);
		dc.add(Restrictions.eq("ip", ip));
		dc.add(Restrictions.ne("physicsPort", port));
		mq.setDetachedCriteria(dc);
//		mq.setFetch(1);	
		return mq;
	}
	
}
