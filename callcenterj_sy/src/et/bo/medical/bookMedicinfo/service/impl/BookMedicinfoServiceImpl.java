package et.bo.medical.bookMedicinfo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import et.bo.flow.service.FlowService;
import et.bo.medical.bookMedicinfo.service.BookMedicinfoService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperBookMedicinfo;
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

public class BookMedicinfoServiceImpl implements BookMedicinfoService {


	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private ClassTreeService cts = null;

	private int num = 0;

	
	public static HashMap hashmap = new HashMap();
	
	private FlowService flowService = null;
	
	
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
	 * @describe ����ר������ȡר�������б�
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
	
	public void addBookMedicinfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createBookMedicinfo(dto));
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
	private OperBookMedicinfo createBookMedicinfo(IBaseDTO dto) {

		OperBookMedicinfo obmi = new OperBookMedicinfo();
		
		String id = ks.getNext("oper_book_medicinfo");
		obmi.setId(id);
		obmi.setContents(dto.get("contents").toString());
		obmi.setCreateTime(TimeUtil.getNowTime());
		obmi.setDictSex(dto.get("dictSex").toString());

		String state = (String) dto.get("state");
////		flowService.addOrUpdateFlow("ԤԼҽ�Ʒ�����Ϣ��", id, state, (String) dto.get("subid"));
//		flowService.addOrUpdateFlow("ԤԼҽ�Ʒ�����Ϣ��", id, state, (String) dto.get("subid"),null);
		obmi.setState(state);
		obmi.setExpertSort(dto.get("billNum").toString());
		obmi.setExpert(dto.get("expertName").toString());
		
		obmi.setIsParter(dto.get("isParter").toString());
		obmi.setBookRid(dto.get("bookRid").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setTraceService(dto.get("traceService").toString());
		obmi.setCustAddr(dto.get("custAddr").toString());
		obmi.setCustName(dto.get("custName").toString());
		obmi.setCustTel(dto.get("custTel").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setDictServiceType(dto.get("dictServiceType").toString());
		obmi.setIsVisit(dto.get("isVisit").toString());
		if(!dto.get("bookVisitTime").equals(""))
		{
			obmi.setBookVisitTime(TimeUtil.getTimeByStr(dto.get("bookVisitTime").toString(), "yyyy-MM-dd"));
		}	
		if(!dto.get("visitTime").equals(""))
		{
			obmi.setVisitTime(TimeUtil.getTimeByStr(dto.get("visitTime").toString(), "yyyy-MM-dd"));
		}
		obmi.setHospitalAdvice(dto.get("hospitalAdvice").toString());
		obmi.setNavigator(dto.get("navigator").toString());
		obmi.setBed(dto.get("bed").toString());
		obmi.setItems(dto.get("items").toString());
		obmi.setCharge(dto.get("charge").toString());
		obmi.setFavours(dto.get("favours").toString());
		obmi.setVisitResult(dto.get("visitResult").toString());
		obmi.setRemark(dto.get("remark").toString());

		return obmi;
	}

	
	
	
	 public void delBookMedicinfo(String id) {
	 // TODO Auto-generated method stub
		 OperBookMedicinfo u = (OperBookMedicinfo)dao.loadEntity(OperBookMedicinfo.class, id);
		 dao.removeEntity(u);
	 }


	 
	public IBaseDTO getBookMedicinfo(String id) {
	 // TODO Auto-generated method stub
	OperBookMedicinfo obmi = (OperBookMedicinfo)dao.loadEntity(OperBookMedicinfo.class, id);
	 IBaseDTO dto = new DynaBeanDTO();

	 dto.set("id", obmi.getId());
	 
	 dto.set("billNum", obmi.getExpertSort());
	 dto.set("expertName", obmi.getExpert());
	 dto.set("custName", obmi.getCustName());
	 dto.set("custTel", obmi.getCustTel());
	 dto.set("dictSex", obmi.getDictSex());
	 dto.set("custAddr", obmi.getCustAddr());
	 dto.set("state", obmi.getState());
	 dto.set("isParter", obmi.getIsParter());
	 dto.set("contents", obmi.getContents());
	 dto.set("reply", obmi.getReply());
	 dto.set("traceService", obmi.getTraceService()); 
	 dto.set("bookRid", obmi.getBookRid());
	 dto.set("dictServiceType", obmi.getDictServiceType());
	 dto.set("isVisit", obmi.getIsVisit());
	 if(obmi.getBookVisitTime()!=null&&!obmi.getBookVisitTime().equals(""))
	{
		dto.set("bookVisitTime",TimeUtil.getTheTimeStr(obmi.getBookVisitTime(), "yyyy-MM-dd"));
	}	
	if(obmi.getVisitTime()!=null&&!obmi.getVisitTime().equals(""))
	{
		dto.set("visitTime",TimeUtil.getTheTimeStr(obmi.getVisitTime(), "yyyy-MM-dd"));
	}
	 dto.set("hospitalAdvice", obmi.getHospitalAdvice());
	 dto.set("navigator", obmi.getNavigator());
	 dto.set("bed", obmi.getBed());
	 dto.set("items", obmi.getItems());
	 dto.set("charge", obmi.getCharge());
	 dto.set("favours", obmi.getFavours());
	 dto.set("visitResult", obmi.getVisitResult());
	 dto.set("bookRid", obmi.getBookRid());	 
	 dto.set("remark", obmi.getRemark());
	 dto.set("createTime",TimeUtil.getTheTimeStr(obmi.getCreateTime(),"yyyy-MM-dd"));
	 return dto;
	 }

	
	
	public int getBookMedicinfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateBookMedicinfo(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.updateEntity(modifyBookMedicinfo(dto));
	 return false;
	 }
		
	private OperBookMedicinfo modifyBookMedicinfo(IBaseDTO dto){
		OperBookMedicinfo obmi = (OperBookMedicinfo)dao.loadEntity(OperBookMedicinfo.class, dto.get("id").toString());
		obmi.setContents(dto.get("contents").toString());
		obmi.setDictSex(dto.get("dictSex").toString());		

		String state = (String) dto.get("state");
////		flowService.addOrUpdateFlow("ԤԼҽ�Ʒ�����Ϣ��", (String)dto.get("id"), state, (String) dto.get("subid"));
//		flowService.addOrUpdateFlow("ԤԼҽ�Ʒ�����Ϣ��", (String)dto.get("id"), state, (String) dto.get("subid"),obmi.getState());
		obmi.setState(state);
		obmi.setExpertSort(dto.get("billNum").toString());
		obmi.setExpert(dto.get("expertName").toString());
		obmi.setIsParter(dto.get("isParter").toString());
//		 ָ���ύ����˭
		String submitUser = dto.get("bookRid").toString();		
		obmi.setBookRid(submitUser);		
		obmi.setReply(dto.get("reply").toString());
		obmi.setTraceService(dto.get("traceService").toString());
		obmi.setCustAddr(dto.get("custAddr").toString());
		obmi.setCustName(dto.get("custName").toString());
		obmi.setCustTel(dto.get("custTel").toString());
		obmi.setReply(dto.get("reply").toString());
		obmi.setDictServiceType(dto.get("dictServiceType").toString());
		obmi.setIsVisit(dto.get("isVisit").toString());
		obmi.setIsParter(dto.get("isParter").toString());
		if(!dto.get("bookVisitTime").equals(""))
		{
			obmi.setBookVisitTime(TimeUtil.getTimeByStr(dto.get("bookVisitTime").toString(), "yyyy-MM-dd"));
		}	
		if(!dto.get("visitTime").equals(""))
		{
			obmi.setVisitTime(TimeUtil.getTimeByStr(dto.get("visitTime").toString(), "yyyy-MM-dd"));
		}		
		obmi.setHospitalAdvice(dto.get("hospitalAdvice").toString());
		obmi.setNavigator(dto.get("navigator").toString());
		obmi.setBed(dto.get("bed").toString());
		obmi.setItems(dto.get("items").toString());
		obmi.setCharge(dto.get("charge").toString());
		obmi.setFavours(dto.get("favours").toString());
		obmi.setVisitResult(dto.get("visitResult").toString());
		obmi.setRemark(dto.get("remark").toString());
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE, obmi);		
//		 ������״̬Ϊ��������󣬷��͵ķ�����������˵ķ����Ͷ���Ϣ
		if (state.equals("����") || state.equals("����")) {
			List l = StaticServlet.userPowerMap.get("ԤԼҽ�Ʒ�����Ϣ��");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("ԤԼҽ�Ʒ�����Ϣ��",
						(String) dto.get("id"), state, submitUser,
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
//		 ������״̬�ǲ��أ����͵ķ������ύ�˵ķ���
		else if (state.equals("����")) {
			flowService.addOrUpdateFlow("ԤԼҽ�Ʒ�����Ϣ��",
					(String) dto.get("id"), state, submitUser,
					(String)dto.get("subid"));
			//�������map��list
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

	public List bookMedicinfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		
		
		List list = new ArrayList();

		BookMedicinfoHelp sh = new BookMedicinfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(sh.bookMedicinfoQuery(dto, pi));
		num = dao.findEntitySize(sh.bookMedicinfoQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			OperBookMedicinfo obmi = (OperBookMedicinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();	
			String reply = obmi.getReply();
			dbd.set("id", obmi.getId());
			dbd.set("expertName", obmi.getExpert());
			dbd.set("custName", obmi.getCustName());
			dbd.set("custTel", obmi.getCustTel());
			dbd.set("dictSex", obmi.getDictSex());
			dbd.set("custAddr", obmi.getCustAddr());
			
			if(obmi.getIsParter()!=null && !obmi.getIsParter().equals(""))
			{
				if(obmi.getIsParter().equals("yes"))
				{
					dbd.set("isParter", "�μ�");
				}
				else
				{
					dbd.set("isParter", "û�вμ�");
				}
			}
			
			if(obmi.getDictServiceType()!=null && !obmi.getDictServiceType().equals(""))
			{
				dbd.set("dictServiceType", cts.getLabelById(obmi.getDictServiceType()));
			}	

			dbd.set("state", obmi.getState());
			
			if(!obmi.getContents().toString().equals("")&&obmi.getContents()!=null)
			{
				
				if(obmi.getContents().toString().length()>15)
				{
					dbd.set("contents", obmi.getContents().toString().substring(0, 14)+"....");
				}
				else
				{
					dbd.set("contents", obmi.getContents());
				}				
			}
			if(obmi.getReply()!=null)
			{
				if(reply.length()>15)
				{
					dbd.set("reply", reply.substring(0, 15)+"...");
				}
				else
				{
					dbd.set("reply", obmi.getReply());
				}
			}
			dbd.set("traceService", obmi.getTraceService());			 
			dbd.set("bookRid", obmi.getBookRid());			
			dbd.set("isVisit", obmi.getIsVisit());
			 if(obmi.getBookVisitTime()!=null&&!obmi.getBookVisitTime().equals(""))
			{
				dbd.set("bookVisitTime",TimeUtil.getTheTimeStr(obmi.getBookVisitTime(),"yyyy-MM-dd"));
			}	
			if(obmi.getVisitTime()!=null&&!obmi.getVisitTime().equals(""))
			{
				dbd.set("visitTime",TimeUtil.getTheTimeStr(obmi.getVisitTime(), "yyyy-MM-dd"));
			}
			dbd.set("hospitalAdvice", obmi.getHospitalAdvice());
			dbd.set("navigator", obmi.getNavigator());
			dbd.set("bed", obmi.getBed());
			dbd.set("items", obmi.getItems());
			dbd.set("charge", obmi.getCharge());
			dbd.set("favours", obmi.getFavours());
			dbd.set("visitResult", obmi.getVisitResult());
			dbd.set("bookRid", obmi.getBookRid());			 
			dbd.set("remark", obmi.getRemark());
			dbd.set("createTime",TimeUtil.getTheTimeStr(obmi.getCreateTime(),"yyyy-MM-dd"));
			
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
	public List getExpertList() {
		return cts.getLabelVaList("zhuanjialeibie");
	}

	/**
	 * ״̬ת��
	 * @param state
	 * @return
	 */
	public String changeState(String state){		
		if("wait".equals(state)){
			return "����";		
		}else if("back".equals(state)){
			return "����";		
		}else if("pass".equals(state)){			
			return "����";		
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
		if("back".equals(state)){
			String str_state="����";
			List l = (List)MessageCollection.m_instance.get(agentId);
			if(l!=null){		
				Iterator it = l.iterator();				
				while(it.hasNext()){
					Map m = (Map)it.next();
					//���ԤԼҽ�Ʒ�����Ϣ��Ϊ��
					if (m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE)!=null) {
						OperBookMedicinfo oc = (OperBookMedicinfo)m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE);	
						//���״̬��ͬ
						if(oc.getState().equals(str_state)){
							it.remove();
						}
					}
				}
			}
		}else{
			String str_state=changeState(state);
			if(str_state.length()>1){
				List l_agent = StaticServlet.userPowerMap.get("ԤԼҽ�Ʒ�����Ϣ��");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//���ԤԼҽ�Ʒ�����Ϣ��Ϊ��
							if (m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE)!=null) {
								OperBookMedicinfo oc = (OperBookMedicinfo)m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE);	
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
