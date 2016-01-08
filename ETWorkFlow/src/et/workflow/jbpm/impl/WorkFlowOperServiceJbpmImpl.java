package et.workflow.jbpm.impl;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ocelot.flow.msg.FlowMsgService;
import ocelot.flow.msg.MsgBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.db.JbpmSession;
import org.jbpm.db.JbpmSessionFactory;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.State;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.exe.SwimlaneInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import et.workflow.common.Parameter;
import et.workflow.jbpm.WorkFlowOperService;


public class WorkFlowOperServiceJbpmImpl implements WorkFlowOperService {

	
	static JbpmSessionFactory jbpmSessionFactory = JbpmSessionFactory
	.buildJbpmSessionFactory();
	 private static Log log = LogFactory.getLog(WorkFlowOperServiceJbpmImpl.class);
	
	 private FlowMsgService fms=null;

	 private MsgBeanJbpm mbj=null;
	public MsgBeanJbpm getMbj() {
		return mbj;
	}



	public void setMbj(MsgBeanJbpm mbj) {
		this.mbj = mbj;
	}



	public FlowMsgService getFms() {
		return fms;
	}



	public void setFms(FlowMsgService fms) {
		this.fms = fms;
	}



	public String singleFlow(String flowId, String transitionName,boolean send) {
		// TODO Auto-generated method stub
		JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
		jbpmSession.beginTransaction();
		//ProcessDefinition processDefinition = jbpmSession.getGraphSession().findLatestProcessDefinition(flowId);
		ProcessInstance processInstance = jbpmSession.getGraphSession().loadProcessInstance(Long.parseLong(flowId));
		Token token=processInstance.getRootToken();
		
		if(token.isTerminatedImplicitly())
			return null;
		log.fatal("***************************");
		   log.fatal("***************************");
		   log.fatal(token.getNode().getName());
		if(transitionName!=null&&!transitionName.equals(""))
		token.signal(transitionName);
		else
			token.signal();
		log.fatal(token.getNode().getName());
		if(token.isTerminatedImplicitly())
		{
			jbpmSession.commitTransaction();
			// 关闭jbpmSession.
			jbpmSession.close();
			return null;
		}
			
		
		jbpmSession.getGraphSession().saveProcessInstance(processInstance);
		jbpmSession.commitTransaction();
		// 关闭jbpmSession.
		jbpmSession.close();
		operFlow(flowId,send);
		return Long.toString(token.getId());
		
	}

	

	public String execTask(MsgBeanJbpm tm,boolean send) {
		// TODO Auto-generated method stub
		String taskId=tm.getTaskId();
		String transitionName=tm.getMessageInfo();
		JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
		jbpmSession.beginTransaction();
		TaskInstance ti=jbpmSession.getTaskMgmtSession().loadTaskInstance(Long.parseLong(taskId));
		Token token=ti.getToken();
		Node prenode=token.getNode();
		String preName=prenode.getName();
		log.fatal("***************************");
		   log.fatal(ti.isSignalling());
		   log.fatal(ti.isStartTaskInstance());
		   log.fatal(ti.isLast());
		   
		   log.fatal("***************************");
		
		if(token.isTerminatedImplicitly())
			return "end";
		if(ti.isSignalling())
		if(transitionName!=null&&!transitionName.equals(""))
		ti.end(transitionName);
		else
			ti.end();
		
		if(token.isTerminatedImplicitly())
		{
			jbpmSession.commitTransaction();
			// 关闭jbpmSession.
			jbpmSession.close();
			return "end";
		}
		String flowId=Long.toString(token.getProcessInstance().getId());
		Node nextnode=token.getNode();
		String nextName=nextnode.getName();
		jbpmSession.commitTransaction();
		// 关闭jbpmSession.
		jbpmSession.close();
		if(!nextName.equals(preName))
		{
			operFlow(flowId,send);
			
		}
		
		return Long.toString(ti.getId());
	}

	

	public String createNewFlow(String flowId,boolean send) {
		// TODO Auto-generated method stub
		JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
		jbpmSession.beginTransaction();
		ProcessDefinition processDefinition = jbpmSession.getGraphSession()
		.findLatestProcessDefinition(flowId);
		ProcessInstance processInstance = processDefinition.createProcessInstance();
		
	
		jbpmSession.getGraphSession().saveProcessInstance(processInstance);
		jbpmSession.commitTransaction();
		jbpmSession.close();
		singleFlow(Long.toString(processInstance.getId()),null,send);
		// 关闭jbpmSession.
		
		return Long.toString(processInstance.getId());
	}

	public void deployDefFlow(String[] paths) {
		// TODO Auto-generated method stub
		JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
		// 并且在永久层会话上开启事务
		jbpmSession.beginTransaction();
		for(int i=0,size=paths.length;i<size;i++)
		{
			ProcessDefinition processDefinition =ProcessDefinition.parseXmlResource(paths[i]);
			jbpmSession.getGraphSession().saveProcessDefinition(processDefinition);
		}
//		 提交事务
		jbpmSession.commitTransaction();
		// 关闭会话.
		jbpmSession.close();
	}

	private void sendTaskMsg(ProcessInstance instance)
	{
		Token token=instance.getRootToken();
		Node node=token.getNode();
		if(!(node instanceof TaskNode))
			return;
		TaskMgmtInstance taskMgmtInstance=instance.getTaskMgmtInstance();
		
		TaskNode tn=(TaskNode)node;
	    Set set=	tn.getTasks();
	    
		   // TaskMgmtSession taskMgmtSession=new TaskMgmtSession(jbpmSession);
		    Collection taskInstances = taskMgmtInstance.getTaskInstances();//taskMgmtSession.findTaskInstances("zhaoyifei");
		    if(null==taskInstances)
		    	return;
		    log.fatal(taskInstances.size());
		    Iterator iter = taskInstances.iterator();
		    while (iter.hasNext()) {
		      TaskInstance taskInstance = (TaskInstance) iter.next();
		      
		     if(!taskInstance.isSignalling())
		    	 continue;
		      taskInstance.start();
		     // taskInstance.end();
		      SwimlaneInstance s= taskInstance.getSwimlaneInstance();//taskMgmtInstance.getSwimlaneInstance(taskInstance.getActorId());
		      String swimName="";
		      if(s!=null)
		      {
		    	  if(mbj.getActor()!=null&&!mbj.getActor().trim().equals(""))
		    	  {  
		    		  s.setActorId(mbj.getActor());
		    	  	swimName=s.getActorId();
		    	  }
		    	  else
		    		  swimName=s.getActorId();
		    	 
		      }  
		      
		      
		    	  
		      sendMsg(Long.toString(instance.getId()),Long.toString(taskInstance.getId()),swimName,MsgBeanJbpm.FLOW_TASK,taskInstance.getName());
		      
		    }
		    
	}
	private void sendStateMsg(ProcessInstance instance)
	{
		Token token=instance.getRootToken();
		Node node=token.getNode();
		List l=node.getLeavingTransitionsList();
		if(node instanceof State)
		{
			State tn=(State)node;
			String name=tn.getName();
		
		
		
		sendMsg(Long.toString(instance.getId()),null,null,MsgBeanJbpm.FLOW_NEXT,name);
		}
	}
	private void sendMsg(String id,String taskId,String actor,String type,String name)
	{
		MsgBeanJbpm mbj1=new MsgBeanJbpm();
		mbj1.setMessageType(type);
		mbj1.setId(id);
		mbj1.setTaskId(taskId);
		mbj1.setActor(actor);
		mbj1.setMessageInfo(name);
		mbj1.setMessage(mbj.getMessage());
		System.out.println("************************");
		System.out.println(id);
		System.out.println(actor);
		System.out.println(type);
		
		System.out.println("************************");
		MsgBean mb=new MsgBean();
		mb.setDest(Parameter.OA_DESTINATION);
		mb.setMsg(mbj1.getMsg());
		fms.sendMsg(mb);
	}
	private String operFlow(String flowId,boolean send)
	{
		JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
		jbpmSession.beginTransaction();
		try
		{
		//ProcessDefinition processDefinition = jbpmSession.getGraphSession().findLatestProcessDefinition(flowId);
		ProcessInstance instance = jbpmSession.getGraphSession().loadProcessInstance(Long.parseLong(flowId));
	
		Token token=instance.getRootToken();
		log.fatal("***************************");
		   log.fatal(token.getNode().getName());
		   if(token.isTerminatedImplicitly())
		   {
			   log.fatal("***************************");
			   log.fatal("this flow is end , place check it !");
			   log.fatal("***************************");
				return "end";
		   }
		//Node node=token.getNode();
		   if(send)
		   {
			   	this.sendStateMsg(instance);
			   	this.sendTaskMsg(instance);
		   }
		log.fatal("***************************");
		log.fatal("***************************");
		return Long.toString(token.getId());
		}catch(Exception e)
		{
			log.error(this,e);
			//throw e;
			
		}finally
		{
			jbpmSession.commitTransaction();
			// 关闭会话.
			jbpmSession.close();
			return "";
		}
		
	}
	public static void main(String[] args) {
		WorkFlowOperServiceJbpmImpl wfosji=new WorkFlowOperServiceJbpmImpl();
		MsgBeanJbpm mbj=new MsgBeanJbpm();
		mbj.setActor("aa");
		mbj.setId("exam");
		mbj.setMessageType(mbj.FLOW_CREATE_TASK);
		mbj.setThisActor("bbb");
		wfosji.setMbj(mbj);
		//wfosji.createNewFlow("exam",false);
		//wfosji.execTask(mbj,false);
		wfosji.createNewAndTask(mbj);
		
		//wfosji.singleFlow("26",null);
		//wfosji.execTask("125",null);
	}



	public String createNewAndTask(MsgBeanJbpm mbj) {
		// TODO Auto-generated method stub
		JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
		jbpmSession.beginTransaction();
		ProcessDefinition processDefinition = jbpmSession.getGraphSession()
		.findLatestProcessDefinition(mbj.getId());
		ProcessInstance processInstance = new ProcessInstance(processDefinition);
		Token token=processInstance.getRootToken();
		
		if(token.isTerminatedImplicitly())
			return null;
		log.fatal("***************************");
		   log.fatal("***************************");
		   log.fatal(token.getNode().getName());
		   String transitionName=mbj.getMessageInfo();
		
			token.signal();
			log.fatal(token.getNode().getName());
		if(token.isTerminatedImplicitly())
		{
			jbpmSession.commitTransaction();
			// 关闭jbpmSession.
			jbpmSession.close();
			return null;
		}
		Node node=token.getNode();
		if(!(node instanceof TaskNode))
			return null;
		TaskMgmtInstance taskMgmtInstance=processInstance.getTaskMgmtInstance();
		
		TaskNode tn=(TaskNode)node;
	    Set set=	tn.getTasks();
	    
		   // TaskMgmtSession taskMgmtSession=new TaskMgmtSession(jbpmSession);
		    Collection taskInstances = taskMgmtInstance.getTaskInstances();//taskMgmtSession.findTaskInstances("zhaoyifei");
		    if(null==taskInstances)
		    	return null;
		    log.fatal(taskInstances.size());
		    Iterator iter = taskInstances.iterator();
		    while (iter.hasNext()) {
		      TaskInstance taskInstance = (TaskInstance) iter.next();
		      
		     if(!taskInstance.isSignalling())
		    	 continue;
		      taskInstance.start();
		     // taskInstance.end();
		      SwimlaneInstance s= taskInstance.getSwimlaneInstance();//taskMgmtInstance.getSwimlaneInstance(taskInstance.getActorId());
		      String swimName="";
		      if(s!=null)
		      {
		    	  if(mbj.getActor()!=null&&!mbj.getActor().trim().equals(""))
		    	  {  s.setActorId(mbj.getThisActor());
		    	  	swimName=s.getActorId();
		    	  }
		    	  
		      }
		      if(mbj.getThisActor()!=null&&!mbj.getThisActor().equals(""))
		    	  s.setActorId(mbj.getThisActor());
		      if(transitionName!=null&&!transitionName.equals(""))
		    	  taskInstance.end(transitionName);
		  		else
		      taskInstance.end();
		    }
		      
		    
		    
		   
		    
		    
		jbpmSession.getGraphSession().saveProcessInstance(processInstance);
		jbpmSession.commitTransaction();
		jbpmSession.close();
		operFlow(Long.toString(processInstance.getId()),true);
		// 关闭jbpmSession.
		
		return Long.toString(processInstance.getId());
	}
	
}
