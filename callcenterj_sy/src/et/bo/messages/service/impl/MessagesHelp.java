/*
 * @(#)MessagesHelp.java	 2008-05-06
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.messages.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperMessages;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>�����������ѯ</p>
 * @author nie
 */
public class MessagesHelp extends MyQueryImpl{
	
	public MyQuery messagesQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//����
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}

		//���ջ���
		str = (String)dto.get("type");
		if(!str.equals("")){
			if(str.equals("0")){
				dc.add(Restrictions.eq("sendId",dto.get("send_id").toString()));
			}else if(str.equals("1")){
				dc.add(Restrictions.eq("receiveId",dto.get("send_id").toString()));
			}
		}
		
		dc.add(Restrictions.or(Restrictions.eq("sendId",dto.get("send_id").toString()), Restrictions.eq("receiveId",dto.get("send_id").toString())));
		
		//�Ƿ��Ѷ�
		str = (String)dto.get("dict_read_flag");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictReadFlag",str));
		}
		dc.addOrder(Order.desc("sendTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}
public MyQuery messagesSizeQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//����
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}

		//���ջ���
		str = (String)dto.get("type");
		if(!str.equals("")){
			if(str.equals("0")){
				dc.add(Restrictions.eq("sendId",dto.get("send_id").toString()));
			}else if(str.equals("1")){
				dc.add(Restrictions.eq("receiveId",dto.get("send_id").toString()));
			}
		}
		
		dc.add(Restrictions.or(Restrictions.eq("sendId",dto.get("send_id").toString()), Restrictions.eq("receiveId",dto.get("send_id").toString())));
		
		//�Ƿ��Ѷ�
		str = (String)dto.get("dict_read_flag");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictReadFlag",str));
		}
		dc.addOrder(Order.desc("sendTime"));
		mq.setDetachedCriteria(dc);
		//mq.setFirst(pi.getBegin());
		//mq.setFetch(pi.getPageSize());

		return mq;
	}
	//����Ա��ѯ
	public MyQuery messagesAdminQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//����
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}
		

		//�Ƿ��Ѷ�
		str = (String)dto.get("dict_read_flag");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictReadFlag",str));
		}
		dc.addOrder(Order.desc("sendTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}
//	����Ա��ѯ
	public MyQuery messagesAdminSizeQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//����
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}
		

		//�Ƿ��Ѷ�
		str = (String)dto.get("dict_read_flag");
		if(!str.equals("")){
			dc.add(Restrictions.eq("dictReadFlag",str));
		}
		dc.addOrder(Order.desc("sendTime"));
		mq.setDetachedCriteria(dc);
		//mq.setFirst(pi.getBegin());
		//mq.setFetch(pi.getPageSize());

		return mq;
	}
	public MyQuery nonReadmessagesQuery(){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);
		//δ����Ϣ
		dc.add(Restrictions.like("dictReadFlag","0"));
		dc.addOrder(Order.desc("receiveId"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
