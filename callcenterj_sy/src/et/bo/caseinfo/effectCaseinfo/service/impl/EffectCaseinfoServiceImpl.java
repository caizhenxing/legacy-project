package et.bo.caseinfo.effectCaseinfo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import et.bo.caseinfo.effectCaseinfo.service.EffectCaseinfoService;
import et.bo.flow.service.FlowService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCaseinfo;
import et.po.OperCustinfo;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class EffectCaseinfoServiceImpl implements EffectCaseinfoService
{
	private BaseDAO dao = null;

	private KeyService ks= null;

	private ClassTreeService cts = null;

	private int num = 0;

	public static HashMap	hashmap	= new HashMap();

	private FlowService flowService = null;

	public FlowService getFlowService()
	{
		return flowService;
	}

	public void setFlowService(FlowService flowService)
	{
		this.flowService = flowService;
	}

	public BaseDAO getDao()
	{
		return dao;
	}

	public void setDao(BaseDAO dao)
	{
		this.dao = dao;
	}

	public KeyService getKs()
	{
		return ks;
	}
	public List exportQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<OperCustinfo> list=new ArrayList<OperCustinfo>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				OperCustinfo oc=new OperCustinfo();
				oc.setCustName(rs.getString("cust_name"));
				list.add(oc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void setKs(KeyService ks)
	{
		this.ks = ks;
	}

	public void addEffectCaseinfo(IBaseDTO dto)
	{
		dao.saveEntity(createEffectCaseinfo(dto));
	}

	private OperCaseinfo createEffectCaseinfo(IBaseDTO dto)
	{
		OperCaseinfo oci = new OperCaseinfo();
		String id = ks.getNext("oper_caseinfo");
		oci.setCaseId(id);
		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setCaseAttr5(dto.get("caseAttr5").toString());
		oci.setCaseContent(dto.get("caseContent").toString());
		oci.setExpertType(dto.get("expertType").toString());
		oci.setCaseExpert(dto.get("caseExpert").toString());
		oci.setCasePic(dto.get("casePic").toString());
		oci.setCaseTime(TimeUtil.getNowTime());
		oci.setCaseVideo(dto.get("caseVideo").toString());
		oci.setCaseReply(dto.get("caseReply").toString());
		oci.setCaseReport(dto.get("caseReport").toString());
		//当前状态是什么
		String state = (String) dto.get("state");
		//是谁做的提交
		String subid = dto.get("caseRid").toString();
//		flowService.addOrUpdateFlow("效果案例库", id, state, (String) dto.get("subid"));
		//flowService.addOrUpdateFlow("效果案例库", id, state, (String) dto.get("subid"),null);
		//flowService.addOrUpdateFlow("效果案例库", id, state, subid);
		//提交为待审状态
		
		
		oci.setState(state);
		
		oci.setDictCaseType("effectCase");
		oci.setCaseRid(subid);
		oci.setRemark(dto.get("remark").toString());
		oci.setUploadfile((String)dto.get("uploadfile"));
		oci.setAddtime(new Date());
		return oci;
	}

	public void delEffectCaseinfo(String id)
	{
		// TODO Auto-generated method stub
		OperCaseinfo u = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class, id);
		dao.removeEntity(u);
	}

	public IBaseDTO getEffectCaseinfo(String id)
	{
		// TODO Auto-generated method stub
		OperCaseinfo oci = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("caseId", oci.getCaseId());
		dto.set("dictCaseType", oci.getDictCaseType());
		dto.set("caseRid", oci.getCaseRid().trim());
		dto.set("expertType", oci.getExpertType());
		dto.set("caseExpert", oci.getCaseExpert());
		dto.set("custName", oci.getCustName());
		dto.set("custAddr", oci.getCustAddr());
		dto.set("custTel", oci.getCustTel());
		dto.set("state", oci.getState());
		dto.set("caseAttr5", oci.getCaseAttr5());
		dto.set("caseContent", oci.getCaseContent());
		dto.set("caseReply", oci.getCaseReply());
		dto.set("casePic", oci.getCasePic());
		dto.set("caseVideo", oci.getCaseVideo());
		dto.set("caseReport", oci.getCaseReport());
		dto.set("remark", oci.getRemark());
		dto.set("uploadfile", oci.getUploadfile());
		dto.set("caseTime", oci.getCaseTime());
		return dto;
	}

	public int getEffectCaseinfoSize()
	{
		// TODO Auto-generated method stub
		return num;
	}

	public boolean updateEffectCaseinfo(IBaseDTO dto)
	{
		// TODO Auto-generated method stub
		dao.saveEntity(modifyEffectCaseinfo(dto));
		return false;
	}

	private OperCaseinfo modifyEffectCaseinfo(IBaseDTO dto)
	{
		String id = dto.get("caseId").toString();
		OperCaseinfo oci = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class, id);

		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setCaseAttr5(dto.get("caseAttr5").toString());
		oci.setCaseContent(dto.get("caseContent").toString());
		oci.setExpertType(dto.get("expertType").toString());
		oci.setCaseExpert(dto.get("caseExpert").toString());
		oci.setCasePic(dto.get("casePic").toString());
		oci.setCaseVideo(dto.get("caseVideo").toString());
		oci.setCaseReply(dto.get("caseReply").toString());
		oci.setCaseReport(dto.get("caseReport").toString());

		String state = (String) dto.get("state");
		
//		flowService.addOrUpdateFlow("效果案例库", (String) dto.get("caseId"), state, (String) dto.get("subid"));
//		flowService.addOrUpdateFlow("效果案例库", (String) dto.get("caseId"), state, (String) dto.get("subid"));
		//flowService.addOrUpdateFlow("效果案例库", id, state, (String) dto.get("subid"));
//		String subid = (String) dto.get("subid");
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.XIAOGUOANLI_MESSAGE, oci);
		
//		List l = StaticServlet.userPowerMap.get("效果案例库");
//		for (int i = 0; i < l.size(); i++) {
//			String audingUser = (String) l.get(i);
//			// 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
//			if (state.equals("待审") || state.equals("已审")) {
//				flowService.addOrUpdateFlow("效果案例库",
//						(String) dto.get("caseId"), state, subid,
//						audingUser);
//				//MessageCollection.m_instance.put(audingUser, m);
//			}
//			// 如果审核状态是驳回，发送的方向是提交人的方向
//			if (state.equals("驳回")) {
//				flowService.addOrUpdateFlow("效果案例库",
//						(String) dto.get("caseId"), state, subid,
//						audingUser);
//				//MessageCollection.m_instance.put(subid, m);
//			}
//		}

		oci.setState(state);
//		 指定提交人是谁
		String submitUser = dto.get("caseRid").toString();
		oci.setCaseRid(submitUser);
		oci.setRemark(dto.get("remark").toString());
		oci.setUploadfile((String)dto.get("uploadfile"));
		oci.setDictCaseType(dto.get("dictCaseType").toString());
		

//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("效果案例库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("效果案例库",
						(String) dto.get("caseId"), state, submitUser,
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
		else if (state.equals("驳回")) {
			flowService.addOrUpdateFlow("效果案例库",
					(String) dto.get("caseId"), state, submitUser,
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
		}else if (state.equals("发布")) {
			oci.setCaseTime(TimeUtil.getNowTime());
		}
		
		return oci;
	}

	public List effectCaseinfoQuery(IBaseDTO dto, PageInfo pi)
	{
		// TODO Auto-generated method stub

		List list = new ArrayList();
		EffectCaseinfoHelp sh = new EffectCaseinfoHelp();
		Object[] result = (Object[]) dao.findEntity(sh.operCaseinfoQuery(dto, pi));
		num = dao.findEntitySize(sh.operCaseinfoQuery(dto, pi));
		for(int i = 0, size = result.length; i < size; i++)
		{
			OperCaseinfo oci = (OperCaseinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("caseId", oci.getCaseId());
			dbd.set("caseRid", oci.getCaseRid());
			dbd.set("caseExpert", oci.getCaseExpert());
			dbd.set("custName", oci.getCustName());
			dbd.set("custAddr", oci.getCustAddr());
			dbd.set("custTel", oci.getCustTel());

			String caseContent = oci.getCaseContent();
			String caseReply = oci.getCaseReply();

			dbd.set("state", oci.getState());


			dbd.set("caseAttr5", oci.getCaseAttr5());

			if (caseContent != null && caseContent.length() > 15)
			{
				dbd.set("caseContent", caseContent.substring(0, 15) + "...");
			}
			else
			{
				dbd.set("caseContent", oci.getCaseContent());
			}

			if (caseReply != null && caseReply.length() > 15)
			{
				dbd.set("caseReply", caseReply.substring(0, 15) + "...");
			}
			else
			{
				dbd.set("caseReply", oci.getCaseReply());
			}

			dbd.set("casePic", oci.getCasePic());
			dbd.set("caseVideo", oci.getCaseVideo());
			dbd.set("caseReport", oci.getCaseReport());
			dbd.set("remark", oci.getRemark());
			dbd.set("uploadfile", oci.getUploadfile());
			dbd.set("caseTime",TimeUtil.getTheTimeStr(oci.getCaseTime(),"yyyy-MM-dd"));
			list.add(dbd);
		}
		return list;
	}

	public void updatePhoto(String id, String path)
	{
		OperCaseinfo osi = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class, id);
		osi.setCasePic(path);
		dao.updateEntity(osi);
	}

	public void updateVideo(String id, String path)
	{
		OperCaseinfo osi = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class, id);
		osi.setCaseVideo(path);
		dao.updateEntity(osi);
	}

	public ClassTreeService getCts()
	{
		return cts;
	}

	public void setCts(ClassTreeService cts)
	{
		this.cts = cts;
	}
	
	/**
	 * 状态转换
	 */
	public String changeState(String state){		
		if("wait".equals(state)){
			return "待审";		
		}else if("back".equals(state)){
			return "驳回";		
		}else if("pass".equals(state)){			
			return "已审";		
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
			String str_state="驳回";
			List l = (List)MessageCollection.m_instance.get(agentId);
			if(l!=null){		
				Iterator it = l.iterator();				
				while(it.hasNext()){
					Map m = (Map)it.next();
					//如果效果案例不为空
					if (m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE)!=null) {
						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE);	
						//如果状态相同						
						if(oc.getState().equals(str_state)){
							it.remove();
						}
					}
				}
			}
		}else{
			String str_state=changeState(state);
			if(str_state.length()>1){
				List l_agent = StaticServlet.userPowerMap.get("效果案例库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果效果案例不为空
							if (m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE)!=null) {
								OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE);	
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
