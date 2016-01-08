package et.bo.markanainfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import et.bo.flow.service.FlowService;
import et.bo.markanainfo.service.MarkanainfoService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;

import et.po.OperMarkanainfo;

import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


public class MarkanainfoServiceImpl implements MarkanainfoService {

	private BaseDAO dao = null;

	private KeyService ks = null;
	private FlowService flowService = null;
	private ClassTreeService cts = null;
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
	public void addMarkanainfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createMarkanainfo(dto));
	}
	
	private OperMarkanainfo createMarkanainfo(IBaseDTO dto) {
		
		OperMarkanainfo omi = new OperMarkanainfo();
		String id = ks.getNext("oper_markanainfo");
		omi.setMarkanaId(id);
		omi.setChargeEditor(dto.get("chargeEditor").toString());
		omi.setCheckAdvise1(dto.get("checkAdvise1").toString());
		omi.setCheckAdvise2(dto.get("checkAdvise2").toString());
		omi.setCheckAdvise3(dto.get("checkAdvise3").toString());
		omi.setChiefEditor(dto.get("chiefEditor").toString());
		omi.setChiefTitle(dto.get("chiefTitle").toString());
		omi.setComments(dto.get("comments").toString());
		omi.setContactMail(dto.get("contactMail").toString());
		if(!dto.get("createTime").equals(""))
		{
			omi.setCreateTime(TimeUtil.getTimeByStr(dto.get("createTime").toString(), "yyyy-MM-dd"));
		}

		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("�г�������", id, state, (String) dto.get("subid"));
		flowService.addOrUpdateFlow("�г�������", id, state, (String) dto.get("subid"),null);
		omi.setState(state);
		
		omi.setDictProductType(dto.get("dictProductType").toString());
		omi.setFirstEditor(dto.get("firstEditor").toString());
		omi.setFrontFor(dto.get("frontFor").toString());
		omi.setDictCommentType(dto.get("dictCommentType").toString());
		omi.setMarkanaContent(dto.get("markanaContent").toString());
		omi.setPeriod(dto.get("period").toString());
		omi.setProductLabel(dto.get("productLabel").toString());
		omi.setSendUnit(dto.get("sendUnit").toString());
		omi.setSupportSite(dto.get("supportSite").toString());
		omi.setSupportTel(dto.get("supportTel").toString());
		omi.setSubEditor(dto.get("subEditor").toString());
		omi.setSubTitle(dto.get("subTitle").toString());
		omi.setSummary(dto.get("summary").toString());
		omi.setUnderTake(dto.get("underTake").toString());
		omi.setUploadfile((String)dto.get("uploadfile"));
		
		//���������
		omi.setCaserid(dto.get("subid").toString());
		return omi;
	}

	 public void delMarkanainfo(String id) {
	 // TODO Auto-generated method stub
		 OperMarkanainfo u = (OperMarkanainfo)dao.loadEntity(OperMarkanainfo.class, id);
		 dao.removeEntity(u);
	 }

	public IBaseDTO getMarkanainfo(String id) {
		// TODO Auto-generated method stub
		OperMarkanainfo omi = (OperMarkanainfo)dao.loadEntity(OperMarkanainfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("markanaId", omi.getMarkanaId());
		dto.set("frontFor", omi.getFrontFor());
		dto.set("underTake", omi.getUnderTake());
		dto.set("period", omi.getPeriod());
		dto.set("dictProductType", omi.getDictProductType());
		dto.set("createTime",TimeUtil.getTheTimeStr(omi.getCreateTime(),"yyyy-MM-dd"));
		dto.set("sendUnit", omi.getSendUnit());
		dto.set("chiefEditor", omi.getChiefEditor());
		dto.set("subEditor", omi.getSubEditor());
		dto.set("firstEditor", omi.getFirstEditor());
		dto.set("chargeEditor", omi.getChargeEditor());
		dto.set("supportTel", omi.getSupportTel());
		dto.set("supportSite", omi.getSupportSite());
		dto.set("contactMail", omi.getContactMail());
		dto.set("productLabel", omi.getProductLabel());
		dto.set("chiefTitle", omi.getChiefTitle());
		dto.set("subTitle", omi.getSubTitle());
		dto.set("summary", omi.getSummary());
		dto.set("markanaContent", omi.getMarkanaContent());
		dto.set("dictCommentType", omi.getDictCommentType());
		dto.set("checkAdvise1", omi.getCheckAdvise1());
		dto.set("checkAdvise2", omi.getCheckAdvise2());
		dto.set("checkAdvise3", omi.getCheckAdvise3());
		dto.set("state", omi.getState());
		dto.set("comments", omi.getComments());
		dto.set("uploadfile", omi.getUploadfile());
		 
		return dto;
	}

	public int getMarkanainfoSize() {
		// TODO Auto-generated method stub
		return num;
	}
	
	 public boolean updateMarkanainfo(IBaseDTO dto) {
		 // TODO Auto-generated method stub
		 dao.updateEntity(modifyMarkanainfo(dto));
		 return false;
	 }
		
	private OperMarkanainfo modifyMarkanainfo(IBaseDTO dto){
		
		OperMarkanainfo omi = (OperMarkanainfo)dao.loadEntity(OperMarkanainfo.class, dto.get("markanaId").toString());
		
		omi.setChargeEditor(dto.get("chargeEditor").toString());
		omi.setCheckAdvise1(dto.get("checkAdvise1").toString());
		omi.setCheckAdvise2(dto.get("checkAdvise2").toString());
		omi.setCheckAdvise3(dto.get("checkAdvise3").toString());
		omi.setChiefEditor(dto.get("chiefEditor").toString());
		omi.setChiefTitle(dto.get("chiefTitle").toString());
		omi.setComments(dto.get("comments").toString());
		omi.setContactMail(dto.get("contactMail").toString());
		if(!dto.get("createTime").equals("")){
			omi.setCreateTime(TimeUtil.getTimeByStr(dto.get("createTime").toString(), "yyyy-MM-dd"));
		}
		
		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("�г�������", (String) dto.get("markanaId"), state, (String) dto.get("subid"));
		
		omi.setState(state);
		
		omi.setDictProductType(dto.get("dictProductType").toString());
		omi.setFirstEditor(dto.get("firstEditor").toString());
		omi.setFrontFor(dto.get("frontFor").toString());
		omi.setDictCommentType(dto.get("dictCommentType").toString());
		omi.setMarkanaContent(dto.get("markanaContent").toString());
		omi.setPeriod(dto.get("period").toString());
		omi.setProductLabel(dto.get("productLabel").toString());
		omi.setSendUnit(dto.get("sendUnit").toString());
		omi.setSupportSite(dto.get("supportSite").toString());
		omi.setSupportTel(dto.get("supportTel").toString());
		omi.setSubEditor(dto.get("subEditor").toString());
		omi.setSubTitle(dto.get("subTitle").toString());
		omi.setSummary(dto.get("summary").toString());
		omi.setUnderTake(dto.get("underTake").toString());
		omi.setUploadfile((String)dto.get("uploadfile"));
//		 ָ���ύ����˭
		String submitUser = dto.get("subid").toString();		
//		�޸�ʱ���޸�������
//		omi.setCaserid(submitUser);
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.SHICHANGFENXI_MESSAGE, omi);		
//		 ���״̬Ϊһ��Ͷ��󲵻أ����͵ķ����������г�������һ��Ȩ�޵��˷��Ͷ���Ϣ
		if (state.equals("һ��") || state.equals("���󲵻�")) {
			List l = StaticServlet.userPowerMap.get("�г�������һ��");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("�г�������",
						(String) dto.get("markanaId"), state, submitUser,
						audingUser);
				
				//�������map��list
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
//		  ���״̬Ϊ���󣬷��͵ķ����������г����������Ȩ�޵��˷��Ͷ���Ϣ
		else if (state.equals("����")) {
			List l = StaticServlet.userPowerMap.get("�г����������");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("�г�������",
						(String) dto.get("markanaId"), state, submitUser,
						audingUser);
				
				//�������map��list
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
//		 ������״̬��һ�󲵻أ����͵ķ������ύ�˵ķ���
		else if (state.equals("һ�󲵻�")) {
			flowService.addOrUpdateFlow("�г�������",
					(String) dto.get("markanaId"), state, omi.getCaserid(),
					(String)dto.get("subid"));
			//�������map��list
			List subList = new ArrayList();
			if(MessageCollection.m_instance.get(omi.getCaserid())!=null&&MessageCollection.m_instance.get(omi.getCaserid()).size()>0){
				subList = MessageCollection.m_instance.get(omi.getCaserid());
				subList.add(m);
			}else{
				subList.add(m);
			}
			MessageCollection.m_instance.put(omi.getCaserid(), subList);
		}else{
			flowService.addOrUpdateFlow("�г�������", (String) dto.get("markanaId"), state, (String) dto.get("subid"),omi.getState());
		}
		
		return omi;
	 }

	public List markanainfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		
		String Summary = null;
		
		List list = new ArrayList();

		MarkanainfoHelp fph = new MarkanainfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(fph.markanainfoQuery(dto, pi));
		num = dao.findEntitySize(fph.markanainfoQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperMarkanainfo omi = (OperMarkanainfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("markanaId", omi.getMarkanaId());
			dbd.set("frontFor", omi.getFrontFor());
			dbd.set("underTake", omi.getUnderTake());
			dbd.set("period", omi.getPeriod());
			dbd.set("dictProductType", omi.getDictProductType());
			dbd.set("createTime",TimeUtil.getTheTimeStr(omi.getCreateTime(),"yyyy-MM-dd"));
			dbd.set("sendUnit", omi.getSendUnit());
			dbd.set("chiefEditor", omi.getChiefEditor());
			dbd.set("subEditor", omi.getSubEditor());
			dbd.set("firstEditor", omi.getFirstEditor());
			dbd.set("chargeEditor", omi.getChargeEditor());
			dbd.set("supportTel", omi.getSupportTel());
			dbd.set("supportSite", omi.getSupportSite());
			dbd.set("contactMail", omi.getContactMail());
			dbd.set("productLabel", omi.getProductLabel());
			dbd.set("chiefTitle", omi.getChiefTitle());
			dbd.set("subTitle", omi.getSubTitle());
						
			Summary = omi.getSummary();
			if(omi.getSummary().length()>15){
				dbd.set("summary", Summary.substring(0, 30)+".........");
			}else{
				dbd.set("summary",omi.getSummary());
			}
			
			dbd.set("markanaContent", omi.getMarkanaContent());
			dbd.set("dictCommentType", omi.getDictCommentType());
			dbd.set("checkAdvise1", omi.getCheckAdvise1());
			dbd.set("checkAdvise2", omi.getCheckAdvise2());
			dbd.set("checkAdvise3", omi.getCheckAdvise3());
			dbd.set("state", omi.getState());
			dbd.set("comments", omi.getComments());
			 list.add(dbd);
		}
		return list;
	}

	/**
	 * ״̬ת��
	 * @param state
	 * @return
	 */
	public String changeState(String state){		
		if("firstDraft".equals(state)){
			return "����";
		}else if("firstCensor".equals(state)){
			return "һ��";
		}else if("firstCensorBack".equals(state)){			
			return "һ�󲵻�";
		}else if("secondCensor".equals(state)){
			return "����";
		}else if("secondCensorBack".equals(state)){
			return "���󲵻�";
		}else if("issuance".equals(state)){
			return "����";
		}else{
			return "";
		}
	}
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state){		
		if("firstCensorBack".equals(state)){//||"secondCensorBack".equals(state)
			String str_state="һ�󲵻�";
			List l = (List)MessageCollection.m_instance.get(agentId);
			if(l!=null){		
				Iterator it = l.iterator();				
				while(it.hasNext()){
					Map m = (Map)it.next();
					//����г�������Ϊ��
					if (m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE)!=null) {
						OperMarkanainfo oc = (OperMarkanainfo)m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE);	
						//���״̬��ͬ
						if(oc.getState().equals(str_state)){
							it.remove();
						}
					}
				}
			}
		}else if("secondCensorBack".equals(state)||"firstCensor".equals(state)){
			String str_state=changeState(state);
			if(str_state.length()>1){
				List l_agent = StaticServlet.userPowerMap.get("�г�������һ��");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//����г�������Ϊ��
							if (m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE)!=null) {
								OperMarkanainfo oc = (OperMarkanainfo)m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE);	
								//���״̬��ͬ
								if(oc.getState().equals(str_state)){
									it.remove();
								}
							}
						}
					}
				}
			}
		}else if("secondCensor".equals(state)){
			String str_state="����";
			if(str_state.length()>1){
				List l_agent = StaticServlet.userPowerMap.get("�г����������");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//����г�������Ϊ��
							if (m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE)!=null) {
								OperMarkanainfo oc = (OperMarkanainfo)m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE);	
								//���״̬��ͬ
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
