package et.bo.oa.workflow.service.impl;




import java.text.ParseException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.common.WorkFlowStatciParameter;
import et.po.FlowDefine;
import et.po.FlowInstance;
import et.po.FlowRight;
import et.po.WorkflowInstance;
import excellence.common.page.PageInfo;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class WorkFlowHelp {

	public MyQuery createRight(String role,String name)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FlowRight.class);
		
		dc.add(Restrictions.or(Restrictions.eq("oaRole",role),Restrictions.eq("oaUser",name)));
		
		
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createRight(String flowActor)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FlowRight.class);
		
		dc.add(Restrictions.eq("flowActor",flowActor));
		
		
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createInstance(Object[] defines,String user)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FlowInstance.class);
		
		//dc.add(Restrictions.or(Restrictions.eq("oaRole",role),Restrictions.eq("oaUser",name)));
		dc.add(Restrictions.isNull("endTime"));
		dc.add(Restrictions.or(Restrictions.ne("ifSuccess","1"),Restrictions.isNull("ifSuccess")));
		if(defines!=null&&defines.length!=0)
			
		{
		dc.add(Restrictions.or(Restrictions.and(Restrictions.in("flowDefine",defines),Restrictions.or(Restrictions.eq("flowActor",""),Restrictions.isNull("flowActor"))),Restrictions.eq("flowActor",user)));
		
		}
		else
			if(user!=null&&!user.trim().equals(""))
			dc.add(Restrictions.eq("flowActor",user));
		dc.addOrder(Order.desc("beginTime"));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createDefine()
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FlowDefine.class);
		
		//dc.add(Restrictions.or(Restrictions.eq("oaRole",role),Restrictions.eq("oaUser",name)));
		dc.add(Restrictions.eq("flowDefType",WorkFlowStatciParameter.FLOW_TYPE_CREATEFLOW));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createDefine(Object[] rights)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FlowDefine.class);
		
		//dc.add(Restrictions.or(Restrictions.eq("oaRole",role),Restrictions.eq("oaUser",name)));
		if(rights!=null&&rights.length!=0)
		dc.add(Restrictions.in("flowDefActor",rights));
		else
			dc.add(Restrictions.eq("id",""));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createDefine(String defId)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FlowDefine.class);
		
		//dc.add(Restrictions.or(Restrictions.eq("oaRole",role),Restrictions.eq("oaUser",name)));
		
			dc.add(Restrictions.eq("flowDefId",defId));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createIns(String insId)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(WorkflowInstance.class);
		
		//dc.add(Restrictions.or(Restrictions.eq("oaRole",role),Restrictions.eq("oaUser",name)));
		
			dc.add(Restrictions.eq("instanceId",insId));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
}
