/*
 * @(#)MessagesHelp.java	 2008-05-06
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
 * <p>问题管理——查询</p>
 * @author nie
 */
public class MessagesHelp extends MyQueryImpl{
	
	public MyQuery messagesQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//内容
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}

		//接收或发送
		str = (String)dto.get("type");
		if(!str.equals("")){
			if(str.equals("0")){
				dc.add(Restrictions.eq("sendId",dto.get("send_id").toString()));
			}else if(str.equals("1")){
				dc.add(Restrictions.eq("receiveId",dto.get("send_id").toString()));
			}
		}
		
		dc.add(Restrictions.or(Restrictions.eq("sendId",dto.get("send_id").toString()), Restrictions.eq("receiveId",dto.get("send_id").toString())));
		
		//是否已读
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

		//内容
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}

		//接收或发送
		str = (String)dto.get("type");
		if(!str.equals("")){
			if(str.equals("0")){
				dc.add(Restrictions.eq("sendId",dto.get("send_id").toString()));
			}else if(str.equals("1")){
				dc.add(Restrictions.eq("receiveId",dto.get("send_id").toString()));
			}
		}
		
		dc.add(Restrictions.or(Restrictions.eq("sendId",dto.get("send_id").toString()), Restrictions.eq("receiveId",dto.get("send_id").toString())));
		
		//是否已读
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
	//管理员查询
	public MyQuery messagesAdminQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//内容
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}
		

		//是否已读
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
//	管理员查询
	public MyQuery messagesAdminSizeQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);

		//内容
		String str = (String)dto.get("message_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("messageContent","%"+str+"%"));
		}
		

		//是否已读
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
		//未读消息
		dc.add(Restrictions.like("dictReadFlag","0"));
		dc.addOrder(Order.desc("receiveId"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
