package et.workflow.jbpm.impl;


import java.util.Iterator;
import java.util.List;

import ocelot.flow.msg.FlowMsgService;
import ocelot.flow.msg.MsgBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.workflow.common.Parameter;
import et.workflow.jbpm.WorkFlowOperService;
import et.workflow.jbpm.WorkFlowService;

public class WorkFlowServiceImpl implements WorkFlowService {

	  private static Log log = LogFactory.getLog(WorkFlowServiceImpl.class);
	  private WorkFlowOperService wfos=null;
	  private FlowMsgService fms=null;
	
	private void acceptMsg(MsgBeanJbpm tm)
	{
		try
		{
		String type=tm.getMessageType();
		wfos.setMbj(tm);
		if(type.equals(tm.FLOW_CREATE))
		{
			wfos.createNewFlow(tm.getId(),true);
		}
		if(type.equals(tm.FLOW_NEXT))
		{
			wfos.singleFlow(tm.getId(),tm.getMessageInfo(),true);
		}
		if(type.equals(tm.FLOW_TASK))
		{
			wfos.execTask(tm,true);
		}
		if(type.equals(tm.FLOW_CREATE_TASK))
		{
			wfos.createNewAndTask(tm);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			log.error(this,e);
		}
	}


	public void ListenMsg() {
		// TODO Auto-generated method stub
		List<MsgBean> l=fms.receiveMsg(Parameter.JBPM_DESTINATION,true);
		Iterator i=l.iterator();
		
		
		while(i.hasNext())
		{
			log.info("^^^^^^^^^^^^^^^^^^^^^");
			MsgBean mb=(MsgBean) i.next();
			MsgBeanJbpm mbj=new MsgBeanJbpm();
			mbj.setMsg(mb.getMsg());
			acceptMsg(mbj);
			log.info("|_|_|_|_|_|_|_|_|_|_|_|_|");
		}
	
		
	}


	public WorkFlowOperService getWfos() {
		return wfos;
	}


	public void setWfos(WorkFlowOperService wfos) {
		this.wfos = wfos;
	}


	public FlowMsgService getFms() {
		return fms;
	}


	public void setFms(FlowMsgService fms) {
		this.fms = fms;
	}
}
