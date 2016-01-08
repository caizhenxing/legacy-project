/*
 * @(#)CallbackHelp.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.callback.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperCallback;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>�ͻ���������ѯ</p>
 * 
 * @version 2008-04-01
 * @author nie
 */
public class CallbackHelp extends MyQueryImpl{
	
	public MyQuery custinfoQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCallback.class);
		
		//����ȷ����δɾ��������
		dc.add(Restrictions.eq("isDelete","0"));
		//�ط�����
		String str = (String)dto.get("callback_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("callbackContent","%"+str+"%"));
		}
		//�طñ�ע
		str = (String)dto.get("remark");
		if(!str.equals("")){
			dc.add(Restrictions.like("remark","%"+str+"%"));
		}
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
	

}
