package et.bo.oa.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import et.bo.common.WorkFlowStatciParameter;
import et.bo.oa.workflow.service.WorkFlowInfo;
import et.bo.oa.workflow.service.WorkFlowService;
import et.po.FlowDefine;
import et.po.FlowInstance;
import et.po.FlowNextStep;
import et.po.FlowRight;
import et.po.WorkflowInstance;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.flow.msg.FlowMsgService;
import excellence.flow.msg.MsgBean;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;






public class WorkFlowServiceJbpmImpl implements WorkFlowService {

	private BaseDAO dao=null;
	private FlowMsgService fms=null;
	private KeyService ks=null;
	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public FlowMsgService getFms() {
		return fms;
	}

	public void setFms(FlowMsgService fms) {
		this.fms = fms;
	}

	public List getTaskList(String role, String userName,int size1) {
		// TODO Auto-generated method stub
		receiveMsgs();
		List result=new ArrayList();
		WorkFlowHelp wfh=new WorkFlowHelp();
		Object[] right=dao.findEntity(wfh.createRight(role,userName));
		String[] rights=new String[right.length];
		for(int i=0,size=right.length;i<size;i++)
		{
			FlowRight fr=(FlowRight)right[i];
			rights[i]=fr.getFlowActor();
		}
		Object[] defines=dao.findEntity(wfh.createDefine(rights));
		Object[] ins=dao.findEntity(wfh.createInstance(defines,userName));
		for(int i=0,size=ins.length;i<size;i++)
		{
			if(size1!=0&&i>=size1)
				break;
				FlowInstance fi=(FlowInstance)ins[i];
				IBaseDTO dto=new DynaBeanDTO();
				dto.set("name",fi.getFlowDefine().getFlowDefName());
				dto.set("id",fi.getId());
				dto.set("time",fi.getBeginTime());
				result.add(dto);
			
		}
		return result;
	}

	public WorkFlowInfo getTask(String id,String actor) {
		// TODO Auto-generated method stub
		FlowInstance fi=(FlowInstance)dao.loadEntity(FlowInstance.class,id);
		WorkFlowInfo wfi=new WorkFlowInfoJbpmImpl();
		if(fi.getFlowActor()!=null&&!fi.getFlowActor().trim().equals("")&&!fi.getFlowActor().equals(actor))
			return null;
		wfi.setAction(fi.getFlowDefine().getFlowDefAction());
		wfi.setStepName(fi.getFlowDefine().getFlowDefName());
		wfi.setId(fi.getId());
		wfi.setType(fi.getFlowDefine().getFlowDefType());
		wfi.setWfDefid(fi.getFlowDefine().getId());
		wfi.setWfInsid(fi.getFlowInstanceId());
		wfi.setMessage(fi.getMessage());
		HashMap<String,String> temp=new HashMap<String,String>();
		Set ts=fi.getFlowDefine().getFlowNextSteps();
		Iterator tsi=ts.iterator();
		while(tsi.hasNext())
		{
			FlowNextStep ft=(FlowNextStep)tsi.next();
			temp.put(ft.getFlowNextStep(),ft.getFlowNextStepName());
		}
		
		wfi.setNextSteps(temp);
		List<String> roles=new ArrayList<String>();
		List<String> users=new ArrayList<String>();
		WorkFlowHelp wfh=new WorkFlowHelp();
		Object[] right=dao.findEntity(wfh.createRight(fi.getFlowDefine().getFlowDefNextActor()));
		for(int i=0,size=right.length;i<size;i++)
		{
			FlowRight fr=(FlowRight)right[i];
			String user=fr.getOaUser();
			String role=fr.getOaRole();
			if(user!=null&&!user.trim().equals(""))
				users.add(user);
			if(role!=null&&!role.trim().equals(""))
				roles.add(role);
		}
		wfi.setRoles(roles);
		wfi.setUsers(users);
		operBegin(id,actor);
		return wfi;
	}

	public boolean operBegin(String id, String actor) {
		// TODO Auto-generated method stub
		FlowInstance fi=(FlowInstance)dao.loadEntity(FlowInstance.class,id);
		if(fi.getFlowOperator()!=null)
		return false;
		else
		{
			fi.setFlowOperator(actor);
			dao.saveEntity(fi);
			return true;
		}
	}

	public boolean operFalse(String id, String actor) {
		// TODO Auto-generated method stub
		FlowInstance fi=(FlowInstance)dao.loadEntity(FlowInstance.class,id);
		if(fi.getFlowOperator()==null||!fi.getFlowOperator().equals(actor))
			return false;
		else
		{
			fi.setFlowOperator(null);
			dao.saveEntity(fi);
			return true;
		}
	}

	public boolean operSuccess(String id, String actor) {
		// TODO Auto-generated method stub
		FlowInstance fi=(FlowInstance)dao.loadEntity(FlowInstance.class,id);
		if(fi.getFlowOperator()==null||!fi.getFlowOperator().equals(actor))
			return false;
		else
		{
			fi.setIfSuccess("1");
			fi.setEndTime(TimeUtil.getNowTime());
			dao.saveEntity(fi);
			return true;
		}
	}

	

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public void receiveMsgs() {
		// TODO Auto-generated method stub
		List<MsgBean> l=fms.receiveMsg(WorkFlowStatciParameter.OA_DESTINATION,true);
		Iterator i=l.iterator();
		while(i.hasNext())
		{
			MsgBean mb=(MsgBean) i.next();
			MsgBeanJbpm mbj=new MsgBeanJbpm();
			mbj.setMsg(mb.getMsg());
			acceptMsg(mbj);
		}
	}

	
	private void acceptMsg(MsgBeanJbpm mbj)
	{
		FlowInstance fi=new FlowInstance();
		WorkFlowHelp wfh=new WorkFlowHelp();
		fi.setBeginTime(TimeUtil.getNowTime());
		fi.setFlowInstanceId(mbj.getId());
		fi.setFlowTaskId(mbj.getTaskId());
		fi.setId(ks.getNext("flow_instance"));
		Object[] r=dao.findEntity(wfh.createDefine(mbj.getMessageInfo()));
		fi.setFlowDefine((FlowDefine)r[0]);
		fi.setFlowActor(mbj.getActor());
		fi.setMessage(mbj.getMessage());
		Object[] re=dao.findEntity(wfh.createIns(fi.getFlowInstanceId()));
		if(re.length==0)
		{
			WorkflowInstance wfi=new WorkflowInstance();
			wfi.setId(ks.getNext("workflow_instance"));
			wfi.setInstanceId(fi.getFlowInstanceId());
			wfi.setWorkflowDefine(fi.getFlowDefine().getWorkflowDefine());
			dao.saveEntity(wfi);
		}
		dao.saveEntity(fi);
	}

	public List getWorkFlows(String role, String userName) {
		// TODO Auto-generated method stub
		WorkFlowHelp wfh=new WorkFlowHelp();
		Object[] result=dao.findEntity(wfh.createDefine());
		List re=new ArrayList();
		for(int i=0,size=result.length;i<size;i++)
		{
			FlowDefine fd=(FlowDefine)result[i];
			IBaseDTO dto=new DynaBeanDTO();
			dto.set("name",fd.getFlowDefName());
			dto.set("id",fd.getId());
			
			re.add(dto);
		}
		return re;
	}

	public void sendMsg(String id, String next, String nextActor,String message) {
		// TODO Auto-generated method stub
		FlowInstance fi=(FlowInstance)dao.loadEntity(FlowInstance.class,id);
		MsgBeanJbpm mbj=new MsgBeanJbpm();
		mbj.setId(fi.getFlowInstanceId());
		mbj.setTaskId(fi.getFlowTaskId());
		mbj.setMessageType(fi.getFlowDefine().getFlowDefType());
		mbj.setMessageInfo(next);
		mbj.setActor(nextActor);
		mbj.setMessage(message);
		MsgBean mb=new MsgBean();
		mb.setMsg(mbj.getMsg());
		mb.setDest(WorkFlowStatciParameter.JBPM_DESTINATION);
		fms.sendMsg(mb);
	}

	public void startFlow(String id, String nextActor,String message) {
		// TODO Auto-generated method stub
		FlowDefine fd=(FlowDefine)dao.loadEntity(FlowDefine.class,id);
		MsgBeanJbpm mbj=new MsgBeanJbpm();
		mbj.setId(fd.getWorkflowDefine().getName());
		mbj.setMessageType(fd.getFlowDefType());
		mbj.setMessage(message);
		mbj.setActor(nextActor);
		MsgBean mb=new MsgBean();
		mb.setMsg(mbj.getMsg());
		mb.setDest(WorkFlowStatciParameter.JBPM_DESTINATION);
		fms.sendMsg(mb);
	}

	public void startFlowAndFirst(String id, String actor, String nextActor, String tranName,String message) {
		// TODO Auto-generated method stub
		FlowDefine fd=(FlowDefine)dao.loadEntity(FlowDefine.class,id);
		MsgBeanJbpm mbj=new MsgBeanJbpm();
		mbj.setId(fd.getWorkflowDefine().getName());
		mbj.setMessageType(fd.getFlowDefType());
		mbj.setMessage(message);
		mbj.setActor(nextActor);
		mbj.setThisActor(actor);
		mbj.setMessageInfo(tranName);
		MsgBean mb=new MsgBean();
		mb.setMsg(mbj.getMsg());
		mb.setDest(WorkFlowStatciParameter.JBPM_DESTINATION);
		fms.sendMsg(mb);
	}

	
	
	
}
