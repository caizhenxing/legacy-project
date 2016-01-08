package et.bo.focusPursue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.flow.service.FlowService;
import et.bo.focusPursue.service.FocusPursueService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCaseinfo;
import et.po.OperFocusinfo;
import et.po.OperInquiryinfo;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class FocusPursueServiceImpl implements FocusPursueService {


	private BaseDAO dao = null;

	private KeyService ks = null;
	private FlowService flowService = null;
	private int num = 0;

	
	public static HashMap hashmap = new HashMap();

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	
	public void addFocusPursue(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createFocusPursue(dto));
	}

	
	private OperFocusinfo createFocusPursue(IBaseDTO dto) {
		OperFocusinfo ofi = new OperFocusinfo();
		String id = ks.getNext("oper_focusinfo");
		ofi.setFocusId(id);

		ofi.setChargeEditor(dto.get("chargeEditor").toString());
		ofi.setCheckAdvise1(dto.get("checkAdvise1").toString());
		ofi.setCheckAdvise2(dto.get("checkAdvise2").toString());
		ofi.setCheckAdvise3(dto.get("checkAdvise3").toString());
		ofi.setChiefEditor(dto.get("chiefEditor").toString());
		ofi.setChiefTitle(dto.get("chiefTitle").toString());
		ofi.setComments(dto.get("comments").toString());
		ofi.setContactMail(dto.get("contactMail").toString());

		ofi.setCreateTime(TimeUtil.getNowTime());
		
		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("焦点追踪库", id, state, (String) dto.get("subid"));
		flowService.addOrUpdateFlow("焦点追踪库", id, state, (String) dto.get("subid"),null);
		ofi.setState(state);

		ofi.setDictFocusType(dto.get("dictFocusType").toString());
		ofi.setFirstEditor(dto.get("firstEditor").toString());
		ofi.setFrontFor(dto.get("frontFor").toString());
		ofi.setFucosContent(dto.get("fucosContent").toString());
		ofi.setLeadInstruction(dto.get("leadInstruction").toString());
		ofi.setPeriod(dto.get("period").toString());
		ofi.setProductLabel(dto.get("productLabel").toString());
		ofi.setSendUnit(dto.get("sendUnit").toString());
		ofi.setSupportSite(dto.get("supportSite").toString());
		ofi.setSupportTel(dto.get("supportTel").toString());
		ofi.setSubEditor(dto.get("subEditor").toString());
		ofi.setSubTitle(dto.get("subTitle").toString());
		ofi.setSummary(dto.get("summary").toString());
		ofi.setUnderTake(dto.get("underTake").toString());
		ofi.setDictProductType(dto.get("dictProductType").toString());
		ofi.setUploadfile((String)dto.get("uploadfile"));
		
		//添加记录工号
		ofi.setCaserid(dto.get("subid").toString());
		
		return ofi;
	}

	
	
	
	 public void delFocusPursue(String id) {
		 OperFocusinfo u = (OperFocusinfo)dao.loadEntity(OperFocusinfo.class, id);
		 dao.removeEntity(u);
	 }


	 
	public IBaseDTO getFocusPursue(String id) {
	 // TODO Auto-generated method stub
		OperFocusinfo ofi = (OperFocusinfo)dao.loadEntity(OperFocusinfo.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("focusId", ofi.getFocusId());
	 dto.set("frontFor", ofi.getFrontFor());
	 dto.set("underTake", ofi.getUnderTake());
	 dto.set("period", ofi.getPeriod());
	 dto.set("dictFocusType", ofi.getDictFocusType());
	 dto.set("createTime",TimeUtil.getTheTimeStr(ofi.getCreateTime(),"yyyy-MM-dd"));
	 dto.set("sendUnit", ofi.getSendUnit());
	 dto.set("chiefEditor", ofi.getChiefEditor());
	 dto.set("subEditor", ofi.getSubEditor());
	 dto.set("firstEditor", ofi.getFirstEditor());
	 dto.set("chargeEditor", ofi.getChargeEditor());
	 dto.set("supportTel", ofi.getSupportTel());
	 dto.set("supportSite", ofi.getSupportSite());
	 dto.set("contactMail", ofi.getContactMail());
	 dto.set("productLabel", ofi.getProductLabel());
	 dto.set("chiefTitle", ofi.getChiefTitle());
	 dto.set("subTitle", ofi.getSubTitle());
	 dto.set("summary", ofi.getSummary());
	 dto.set("fucosContent", ofi.getFucosContent());
	 dto.set("leadInstruction", ofi.getLeadInstruction());
	 dto.set("checkAdvise1", ofi.getCheckAdvise1());
	 dto.set("checkAdvise2", ofi.getCheckAdvise2());
	 dto.set("checkAdvise3", ofi.getCheckAdvise3());
	 dto.set("state", ofi.getState());
	 dto.set("comments", ofi.getComments());
	 dto.set("dictProductType",ofi.getDictProductType());
	 dto.set("uploadfile", ofi.getUploadfile());
	 dto.set("caserid", ofi.getCaserid());
	 return dto;
	 }

	
	
	public int getFocusPursueSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateFocusPursue(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.updateEntity(modifyFocusPursue(dto));
	 return false;
	 }
		
	private OperFocusinfo modifyFocusPursue(IBaseDTO dto){
		
		OperFocusinfo ofi = (OperFocusinfo)dao.loadEntity(OperFocusinfo.class, dto.get("focusId").toString());
		
		ofi.setChargeEditor(dto.get("chargeEditor").toString());
		ofi.setCheckAdvise1(dto.get("checkAdvise1").toString());
		ofi.setCheckAdvise2(dto.get("checkAdvise2").toString());
		ofi.setCheckAdvise3(dto.get("checkAdvise3").toString());
		ofi.setChiefEditor(dto.get("chiefEditor").toString());
		ofi.setChiefTitle(dto.get("chiefTitle").toString());
		ofi.setComments(dto.get("comments").toString());
		ofi.setContactMail(dto.get("contactMail").toString());
		
		
		if(dto.get("createTime")!=null&&!dto.get("createTime").equals(""))
		{
			ofi.setCreateTime(TimeUtil.getTimeByStr(dto.get("createTime").toString(), "yyyy-MM-dd"));
		}
		
		String state = (String) dto.get("state");
		
//		flowService.addOrUpdateFlow("焦点追踪库", (String) dto.get("focusId"), state, (String) dto.get("subid"));
		//flowService.addOrUpdateFlow("焦点追踪库", (String) dto.get("focusId"), state, (String) dto.get("subid"),ofi.getState());
		ofi.setState(state);
		
		ofi.setDictFocusType(dto.get("dictFocusType").toString());
		ofi.setFirstEditor(dto.get("firstEditor").toString());
		ofi.setFrontFor(dto.get("frontFor").toString());
		ofi.setFucosContent(dto.get("fucosContent").toString());
		ofi.setLeadInstruction(dto.get("leadInstruction").toString());
		ofi.setPeriod(dto.get("period").toString());
		ofi.setProductLabel(dto.get("productLabel").toString());
		ofi.setSendUnit(dto.get("sendUnit").toString());
		ofi.setSupportSite(dto.get("supportSite").toString());
		ofi.setSupportTel(dto.get("supportTel").toString());
		ofi.setSubEditor(dto.get("subEditor").toString());
		ofi.setSubTitle(dto.get("subTitle").toString());
		ofi.setSummary(dto.get("summary").toString());
		ofi.setUnderTake(dto.get("underTake").toString());
		ofi.setDictProductType(dto.get("dictProductType").toString());
		ofi.setUploadfile((String)dto.get("uploadfile"));
		
//		添加受理工号
		String submitUser = dto.get("subid").toString();
		ofi.setCaserid(submitUser);
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE, ofi);
		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("一审") || state.equals("二审驳回")) {
			List l = StaticServlet.userPowerMap.get("焦点追踪库一审");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("焦点追踪库一审",
						(String) dto.get("focusId"), state, submitUser,
						audingUser);
				
				//用来存放map的list
				List subList = new ArrayList();
				if(MessageCollection.m_instance.get(audingUser)!=null){
					subList = MessageCollection.m_instance.get(audingUser);
					subList.add(m);
				}else{
					subList.add(m);
				}
				MessageCollection.m_instance.put(audingUser, subList);
			}
		}else if (state.equals("二审")) {
			List l = StaticServlet.userPowerMap.get("焦点追踪库二审");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("焦点追踪库二审",
						(String) dto.get("focusId"), state, submitUser,
						audingUser);
				
				//用来存放map的list
				List subList = new ArrayList();
				if(MessageCollection.m_instance.get(audingUser)!=null){
					subList = MessageCollection.m_instance.get(audingUser);
					subList.add(m);
				}else{
					subList.add(m);
				}
				MessageCollection.m_instance.put(audingUser, subList);
			}
		}
//		 如果审核状态是驳回，发送的方向是提交人的方向
		else if (state.equals("一审驳回")) {
			flowService.addOrUpdateFlow("焦点追踪库一审驳回",
					(String) dto.get("focusId"), state, submitUser,
					(String)dto.get("subid"));
			//用来存放map的list
			List subList = new ArrayList();
			if(MessageCollection.m_instance.get(submitUser)!=null&&MessageCollection.m_instance.get(submitUser).size()>0){
				subList = MessageCollection.m_instance.get(submitUser);
				subList.add(m);
			}else{
				subList.add(m);
			}
			MessageCollection.m_instance.put(submitUser, subList);
		}
//		else if (state.equals("二审驳回")) {
//			flowService.addOrUpdateFlow("焦点追踪库二审驳回",
//					(String) dto.get("focusId"), state, submitUser,
//					(String)dto.get("subid"));
//			//用来存放map的list
//			List subList = new ArrayList();
//			if(MessageCollection.m_instance.get(submitUser)!=null&&MessageCollection.m_instance.get(submitUser).size()>0){
//				subList = MessageCollection.m_instance.get(submitUser);
//				subList.add(m);
//			}else{
//				subList.add(m);
//			}
//			MessageCollection.m_instance.put(submitUser, subList);
//		}
		else if (state.equals("发布")) {
			//osi.setCaseTime(TimeUtil.getNowTime());
		}
		
		return ofi;
	 }

	public List focusPursueQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		
		String Summary = null;
		List list = new ArrayList();

		FocusPursueHelp fph = new FocusPursueHelp();
		
		Object[] result = (Object[]) dao.findEntity(fph.focusPursueQuery(dto, pi));
		num = dao.findEntitySize(fph.focusPursueQuery(dto, pi));

		
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperFocusinfo ofi = (OperFocusinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("focusId", ofi.getFocusId());
			dbd.set("frontFor", ofi.getFrontFor());
			dbd.set("underTake", ofi.getUnderTake());
			dbd.set("period", ofi.getPeriod());
			dbd.set("dictFocusType", ofi.getDictFocusType());
			dbd.set("createTime", TimeUtil.getTheTimeStr(ofi.getCreateTime(),"yyyy-MM-dd"));
			dbd.set("sendUnit", ofi.getSendUnit());
			dbd.set("chiefEditor", ofi.getChiefEditor());
			dbd.set("subEditor", ofi.getSubEditor());
			dbd.set("firstEditor", ofi.getFirstEditor());
			dbd.set("chargeEditor", ofi.getChargeEditor());
			dbd.set("supportSite", ofi.getSupportSite());
			dbd.set("contactMail", ofi.getContactMail());
			dbd.set("contactMail", ofi.getContactMail());
			dbd.set("productLabel", ofi.getProductLabel());
			dbd.set("chiefTitle", ofi.getChiefTitle());
			dbd.set("subTitle", ofi.getSubTitle());
			dbd.set("dictProductType", ofi.getDictProductType());
			
			Summary = ofi.getSummary();
			if(ofi.getSummary()!=null&&ofi.getSummary().length()>15)
			{
				dbd.set("summary", Summary.substring(0, 15)+"...");
			}
			else
			{
				dbd.set("summary",ofi.getSummary());
			}
			
			dbd.set("fucosContent", ofi.getFucosContent());
			dbd.set("leadInstruction", ofi.getLeadInstruction());
			dbd.set("checkAdvise1", ofi.getCheckAdvise1());
			dbd.set("checkAdvise2", ofi.getCheckAdvise2());
			dbd.set("checkAdvise3", ofi.getCheckAdvise3());
			dbd.set("state", ofi.getState());
			dbd.set("comments", ofi.getComments());
			list.add(dbd);
		}
		return list;
	}

//	private void sendSMS(OperCaseinfo old,IBaseDTO now) {		
//		if(!now.get("state").toString().equals("初稿")&&!old.getState().equals(now.get("state").toString())){	
//			if(old.getState().equals("初稿")||old.getState().equals("一审驳回")){
//				if(now.get("state").toString().equals("一审")){//up send
//					sendUpSMS("一审");
//				}
//			}else if(old.getState().equals("一审") || old.getState().equals("二审驳回")){
//				if(now.get("state").toString().equals("一审驳回")){//down send
//					sendDownSMS(old.getCaseRid(),"有一条待审信息被驳回");
//				}else if(now.get("state").toString().equals("二审")){//up send
//					sendUpSMS("二审");
//				}				
//			}else if(old.getState().equals("二审")){//up send
//				if(now.get("state").toString().equals("二审驳回")){//down send
//					sendDownSMS(old.getCaseRid(),"有一条待审信息被驳回");
//				}else if(now.get("state").toString().equals("发布")){//down send
//					sendDownSMS(old.getCaseRid(),"有一条待审信息通过审核");
//				}	
//			}
////			else if(old.getState().equals("发布")){				
////			}
//			
//		}
//	}
//	
//	private void sendUpSMS(String state){
//		MyQuery mq=new MyQueryImpl();
//		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
//		dc.add(Restrictions.like("auditing","%焦点追踪库"+state+"%"));
//		mq.setDetachedCriteria(dc);
//		Object[] result = (Object[]) dao.findEntity(mq);
//		for (int i = 0, size = result.length; i < size; i++) {
//			SysUser sysuser = (SysUser) result[i];
//			StaticServlet.messageMap.put(sysuser.getUserId(), "在焦点追踪库，有一条待审信息！");
//		}
//	}
//	
//	private void sendDownSMS(String name,String content){
//		StaticServlet.messageMap.put(name, "在焦点追踪库，"+content);
//	}

	/**
	 * 状态转换
	 */
	public String changeState(String state){		
		if("wait".equals(state)){
			return "一审";		
		}else if("back".equals(state)){
			return "一审驳回";		
		}else if("waitagain".equals(state)){			
			return "二审";		
		}else if("backagain".equals(state)){			
			return "二审驳回";		
		}else if("issuance".equals(state)){
			return "发布";		
		}else{
			return "";		
		}		 
	}

	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state){
		if("back".equals(state)){
			String str_state="一审驳回";
			List l = (List)MessageCollection.m_instance.get(agentId);
			if(l!=null){		
				Iterator it = l.iterator();				
				while(it.hasNext()){
					Map m = (Map)it.next();
					//如果农产品供求库案例不为空
					if (m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE)!=null) {
						OperFocusinfo oc = (OperFocusinfo)m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE);	
						//如果状态相同						
						if(oc.getState().equals(str_state)){
							it.remove();
						}
					}
				}
			}
		}else if("backagain".equals(state)){
			String str_state="二审驳回";
			List l = (List)MessageCollection.m_instance.get(agentId);
			if(l!=null){		
				Iterator it = l.iterator();				
				while(it.hasNext()){
					Map m = (Map)it.next();
					//如果农产品供求库案例不为空
					if (m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE)!=null) {
						OperFocusinfo oc = (OperFocusinfo)m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE);	
						//如果状态相同						
						if(oc.getState().equals(str_state)){
							it.remove();
						}
					}
				}
			}
		}else{
			String str_state=changeState(state);
			if(str_state.length()>1 && "一审".equals(str_state)){
				List l_agent = StaticServlet.userPowerMap.get("焦点追踪库一审");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果效果案例不为空
							if (m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE)!=null) {
								OperFocusinfo oc = (OperFocusinfo)m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE);	
								//如果状态相同
								if(oc.getState().equals(str_state)){
									it.remove();
								}
							}
						}
					}
				}
			}else if(str_state.length()>1 && "二审".equals(str_state)){
				List l_agent = StaticServlet.userPowerMap.get("焦点追踪库二审");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果效果案例不为空
							if (m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE)!=null) {
								OperFocusinfo oc = (OperFocusinfo)m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE);	
								//如果状态相同
								if(oc.getState().equals(str_state)){
									it.remove();
								}
							}
						}
					}
				}
			}
		}		
	}
}
