package et.bo.corpinfo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import et.bo.corpinfo.service.CorpinfoService;
import et.bo.flow.service.FlowService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCorpinfo;
import et.po.OperCustinfo;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class CorpinfoServiceImpl implements CorpinfoService {

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

	public void addOperCorpinfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createOperCorpinfo(dto));
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
	
	private OperCorpinfo createOperCorpinfo(IBaseDTO dto) {
		OperCorpinfo oci = new OperCorpinfo();
		String id = ks.getNext("oper_corpinfo");
		oci.setId(id);
		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setContents(dto.get("contents").toString());
		oci.setCorpRid(dto.get("corpRid").toString());
		oci.setCreatetime(TimeUtil.getNowTime());

		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("企业信息库", id, state, (String) dto.get("subid"));
		flowService.addOrUpdateFlow("企业信息库", id, state, (String) dto.get("subid"),null);
		oci.setState(state);
		
		oci.setDictServiceType(dto.get("dictServiceType").toString());
		oci.setExpert(dto.get("expert").toString());
		oci.setRemark(dto.get("remark").toString());
		oci.setReply(dto.get("reply").toString());
		if (dto.get("createTime")!=null&&dto.get("createTime").toString().length()>=10){
			oci.setCreatetime(TimeUtil.getTimeByStr(dto.get("createTime")
				.toString(), "yyyy-MM-dd"));
		}
		oci.setUploadfile((String)dto.get("uploadfile"));
		return oci;
	}

	public void delOperCorpinfo(String id) {
		// TODO Auto-generated method stub
		OperCorpinfo u = (OperCorpinfo) dao.loadEntity(OperCorpinfo.class, id);
		dao.removeEntity(u);
	}

	public IBaseDTO getOperCorpinfo(String id) {
		// TODO Auto-generated method stub
		OperCorpinfo oci = (OperCorpinfo) dao
				.loadEntity(OperCorpinfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("id", oci.getId());
		dto.set("corpRid", oci.getCorpRid());
		dto.set("expert", oci.getExpert());

		dto.set("custName", oci.getCustName());

		dto.set("custTel", oci.getCustTel());
		dto.set("custAddr", oci.getCustAddr());
		dto.set("state", oci.getState());
		dto.set("dictServiceType", oci.getDictServiceType());

		dto.set("contents", oci.getContents());
		dto.set("reply", oci.getReply());
		dto.set("remark", oci.getRemark());
		if (oci.getCreatetime()!=null&&oci.getCreatetime().toString().length()>=10){
			dto.set("createTime", oci.getCreatetime().toString().substring(0, 10));
		}
		dto.set("uploadfile", oci.getUploadfile());
		return dto;
	}

	public int getOperCorpinfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public boolean updateOperCorpinfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(modifyOperCorpinfo(dto));
		return false;
	}

	private OperCorpinfo modifyOperCorpinfo(IBaseDTO dto) {

		OperCorpinfo oci = (OperCorpinfo) dao.loadEntity(OperCorpinfo.class,
				dto.get("id").toString());

		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setContents(dto.get("contents").toString());
//		 指定提交人是谁
		String submitUser = dto.get("corpRid").toString();
		oci.setCorpRid(submitUser);
		oci.setCreatetime(TimeUtil.getNowTime());

		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("企业信息库", (String) dto.get("id"), state, (String) dto.get("subid"));
//		flowService.addOrUpdateFlow("企业信息库", (String) dto.get("id"), state, (String) dto.get("subid"),oci.getState());
		oci.setState(state);
		
		oci.setDictServiceType(dto.get("dictServiceType").toString());
		oci.setExpert(dto.get("expert").toString());
		oci.setRemark(dto.get("remark").toString());
		oci.setReply(dto.get("reply").toString());
		if (dto.get("createTime")!=null){
			oci.setCreatetime(TimeUtil.getTimeByStr((String)dto.get("createTime"), "yyyy-MM-dd"));
		}
		oci.setUploadfile((String)dto.get("uploadfile"));
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.QIYE_MESSAGE, oci);		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("企业信息库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("企业信息库",
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
			flowService.addOrUpdateFlow("企业信息库",
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
		
		return oci;
	}

	public List operCorpinfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		CorpinfoHelp sh = new CorpinfoHelp();

		Object[] result = (Object[]) dao.findEntity(sh.corpinfoQuery(dto, pi));
		num = dao.findEntitySize(sh.corpinfoQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperCorpinfo opi = (OperCorpinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", opi.getId());
			dbd.set("corpRid", opi.getCorpRid());
			dbd.set("expert", opi.getExpert());

			dbd.set("custName", opi.getCustName());

			dbd.set("custTel", opi.getCustTel());
			dbd.set("custAddr", opi.getCustAddr());

			String contents = opi.getContents();

			dbd.set("state", opi.getState());

			if (opi.getContents() != null) {
				if (contents.length() > 15) {
					dbd.set("contents", contents.substring(0, 15) + "...");
				} else {
					dbd.set("contents", opi.getContents());
				}
			}

			if (opi.getDictServiceType()!=null && !opi.getDictServiceType().equals("")) {

				if (!cts.getLabelById(opi.getDictServiceType()).equals("")) {
					dbd.set("dictServiceType", cts.getLabelById(opi
							.getDictServiceType()));
				}

			}

			dbd.set("reply", opi.getReply());
			dbd.set("remark", opi.getRemark());	
			if (opi.getCreatetime()!=null&&opi.getCreatetime().toString().length()>9){
				dbd.set("createTime",opi.getCreatetime().toString().substring(0, 10));
			}
			list.add(dbd);
		}
		return list;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
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
					//如果企业信息不为空
					if (m.get(SysStaticParameter.QIYE_MESSAGE)!=null) {
						OperCorpinfo oc = (OperCorpinfo)m.get(SysStaticParameter.QIYE_MESSAGE);	
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
				List l_agent = StaticServlet.userPowerMap.get("企业信息库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果企业信息不为空
							if (m.get(SysStaticParameter.QIYE_MESSAGE)!=null) {
								OperCorpinfo oc = (OperCorpinfo)m.get(SysStaticParameter.QIYE_MESSAGE);	
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
