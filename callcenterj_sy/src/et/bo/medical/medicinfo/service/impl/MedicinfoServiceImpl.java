package et.bo.medical.medicinfo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import et.bo.flow.service.FlowService;
import et.bo.medical.medicinfo.service.MedicinfoService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperMedicinfo;
import et.po.SysUser;

import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class MedicinfoServiceImpl implements MedicinfoService {

	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private ClassTreeService cts = null;

	private int num = 0;

	public static HashMap hashmap = new HashMap();

	private FlowService flowService = null;

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	public List userQuery()
	{
		String sql = "select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'";
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
	public FlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

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

	/**
	 * @describe 根据专家类别获取专家名称列表
	 * @param expertType
	 * @return
	 */
	public List getExpertNameList(String expertType){
		List expertNameList = new ArrayList();
		MyQuery mq = new MyQueryImpl();
		String hql = "select custName from OperCustinfo a where a.expertType = '"+expertType+"' order by a.custName asc";
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		for (int i = 0; i < o.length; i++) {
			String custName = (String)o[i];
			if(custName != null){
				LabelValueBean  lvb = new LabelValueBean();
				lvb.setLabel(custName);
				lvb.setValue(custName);
				expertNameList.add(lvb);
			}
		}
		return expertNameList;
	}
	
	public void addMedicinfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createMedicinfo(dto));
	}

	private OperMedicinfo createMedicinfo(IBaseDTO dto) {

		OperMedicinfo obmi = new OperMedicinfo();
		String id = ks.getNext("oper_medicinfo");
		obmi.setId(id);
		obmi.setMedicRid(dto.get("medicRid").toString());
		obmi.setContents(dto.get("contents").toString());
		obmi.setCreateTime(TimeUtil.getNowTime());
		obmi.setDictSex(dto.get("dictSex").toString());

		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("普通医疗服务信息库", id, state, (String) dto.get("subid"));
//		flowService.addOrUpdateFlow("普通医疗服务信息库", id, state, (String) dto.get("subid"),null);
		obmi.setState(state);
		
		obmi.setExpert(dto.get("billNum").toString());
		obmi.setExpertName(dto.get("expertName").toString());
		obmi.setIsParter(dto.get("isParter").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setTraceService(dto.get("traceService").toString());
		obmi.setCustAddr(dto.get("custAddr").toString());
		obmi.setCustName(dto.get("custName").toString());
		obmi.setCustTel(dto.get("custTel").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setRemark(dto.get("remark").toString());

		return obmi;
	}

	public void delMedicinfo(String id) {
		// TODO Auto-generated method stub
		OperMedicinfo u = (OperMedicinfo) dao.loadEntity(OperMedicinfo.class,
				id);
		dao.removeEntity(u);
	}

	public IBaseDTO getMedicinfo(String id) {
		// TODO Auto-generated method stub
		OperMedicinfo omi = (OperMedicinfo) dao.loadEntity(OperMedicinfo.class,
				id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("id", omi.getId());
		dto.set("medicRid", omi.getMedicRid());
		dto.set("billNum", omi.getExpert());
		dto.set("expertName", omi.getExpertName());
		dto.set("custName", omi.getCustName());
		dto.set("custTel", omi.getCustTel());
		dto.set("dictSex", omi.getDictSex());
		dto.set("custAddr", omi.getCustAddr());
		dto.set("state", omi.getState());
		dto.set("isParter", omi.getIsParter());
		dto.set("contents", omi.getContents());
		dto.set("reply", omi.getReply());
		dto.set("traceService", omi.getTraceService());
		dto.set("remark", omi.getRemark());
		dto.set("createTime", TimeUtil.getTheTimeStr(omi.getCreateTime(), "yyyy-MM-dd"));

		return dto;
	}

	public int getMedicinfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public boolean updateMedicinfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.updateEntity(modifyMedicinfo(dto));
		return false;
	}

	private OperMedicinfo modifyMedicinfo(IBaseDTO dto) {

		OperMedicinfo obmi = (OperMedicinfo) dao.loadEntity(
				OperMedicinfo.class, dto.get("id").toString());

		// obmi.setId(ks.getNext("oper_book_medicinfo"));
		obmi.setContents(dto.get("contents").toString());
		obmi.setCreateTime(TimeUtil.getNowTime());
		obmi.setDictSex(dto.get("dictSex").toString());

		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("普通医疗服务信息库", (String)dto.get("id"), state, (String) dto.get("subid"));
//		flowService.addOrUpdateFlow("普通医疗服务信息库", (String)dto.get("id"), state, (String) dto.get("subid"),obmi.getState());
		obmi.setState(state);

		obmi.setExpert(dto.get("billNum").toString());
		obmi.setExpertName(dto.get("expertName").toString());
		obmi.setIsParter(dto.get("isParter").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setTraceService(dto.get("traceService").toString());
		obmi.setCustAddr(dto.get("custAddr").toString());
		obmi.setCustName(dto.get("custName").toString());
		obmi.setCustTel(dto.get("custTel").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setRemark(dto.get("remark").toString());
//		 指定提交人是谁
		String submitUser = dto.get("medicRid").toString();
		obmi.setMedicRid(submitUser);
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE, obmi);		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("普通医疗服务信息库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("普通医疗服务信息库",
						(String) dto.get("id"), state, submitUser,
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
			flowService.addOrUpdateFlow("普通医疗服务信息库",
					(String) dto.get("id"), state, submitUser,
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

		return obmi;
	}

	public List medicinfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		MedicinfoHelp sh = new MedicinfoHelp();

		Object[] result = (Object[]) dao.findEntity(sh.medicinfoQuery(dto, pi));
		num = dao.findEntitySize(sh.medicinfoQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperMedicinfo omi = (OperMedicinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("id", omi.getId());
			dbd.set("medicRid", omi.getMedicRid());
			dbd.set("expertName", omi.getExpertName());
			dbd.set("custName", omi.getCustName());
			dbd.set("custTel", omi.getCustTel());
			dbd.set("dictSex", omi.getDictSex());
			dbd.set("custAddr", omi.getCustAddr());

			if (omi.getIsParter() != null) {
				if (omi.getIsParter().equals("yes")) {
					dbd.set("isParter", "参加");
				} else {
					dbd.set("isParter", "没有参加");
				}
			}

			String contents = omi.getContents();
			String reply = omi.getReply();
			dbd.set("createTime", TimeUtil.getTheTimeStr(omi.getCreateTime(), "yyyy-MM-dd"));
			dbd.set("state", omi.getState());
			
			if (omi.getContents() != null) {
				if (contents.length() > 15) {
					dbd.set("contents", contents.substring(0, 15) + "...");
				} else {
					dbd.set("contents", omi.getContents());
				}
			}

			if (omi.getReply() != null) {
				if (reply.length() > 15) {
					dbd.set("reply", reply.substring(0, 15) + "...");
				} else {
					dbd.set("reply", omi.getReply());
				}
			}

			dbd.set("traceService", omi.getTraceService());
			dbd.set("remark", omi.getRemark());
			list.add(dbd);
		}
		return list;
	}
	
	public List getExpertList() {
		return cts.getLabelVaList("zhuanjialeibie");
	}

	/**
	 * 状态转换
	 * @param state
	 * @return
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
					//如果普通医疗服务信息不为空
					if (m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE)!=null) {
						OperMedicinfo oc = (OperMedicinfo)m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE);	
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
				List l_agent = StaticServlet.userPowerMap.get("普通医疗服务信息库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果普通医疗服务信息不为空
							if (m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE)!=null) {
								OperMedicinfo oc = (OperMedicinfo)m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE);	
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
