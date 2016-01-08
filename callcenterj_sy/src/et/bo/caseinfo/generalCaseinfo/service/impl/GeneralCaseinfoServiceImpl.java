package et.bo.caseinfo.generalCaseinfo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.caseinfo.generalCaseinfo.service.GeneralCaseinfoService;
import et.bo.flow.service.FlowService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.stat.service.impl.StatDateStr;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCaseinfo;
import et.po.OperCustinfo;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class GeneralCaseinfoServiceImpl implements GeneralCaseinfoService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private FlowService flowService = null;

	public FlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	private int num = 0;

	// public static HashMap hashmap = new HashMap();

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

	public void addGeneralCaseinfo(IBaseDTO dto) {
		dao.saveEntity(createGeneralCaseinfo(dto));
	}

	private OperCaseinfo createGeneralCaseinfo(IBaseDTO dto) {
		OperCaseinfo oci = new OperCaseinfo();
		String id = ks.getNext("oper_caseinfo");
		oci.setCaseId(id);
		oci.setCaseAttr1(dto.get("caseAttr1").toString());
		oci.setCaseAttr2(dto.get("caseAttr2").toString());
		oci.setCaseAttr3(dto.get("caseAttr3").toString());
		oci.setCaseAttr4(dto.get("caseAttr4").toString());
		oci.setCaseAttr5(dto.get("caseAttr5").toString());
		oci.setCaseContent(dto.get("caseContent").toString());
		oci.setExpertType(dto.get("expertType").toString());
		oci.setCaseExpert(dto.get("caseExpert").toString());
		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setCasePic(dto.get("casePic").toString());
		oci.setCaseReply(dto.get("caseReply").toString());
		oci.setCaseReview(dto.get("caseReview").toString());
		oci.setCaseRid(dto.get("caseRid").toString());
		String state1 = dto.get("state").toString();
		if (state1.equals("发布")) {
			oci.setCaseTime(TimeUtil.getNowTime());
		}
		oci.setCaseVideo(dto.get("caseVideo").toString());

		String state = (String) dto.get("state");
		// flowService.addOrUpdateFlow("普通案例库", id, state, (String)
		// dto.get("subid"),null);
		oci.setState(state);

		oci.setDictCaseType("putong");
		oci.setRemark(dto.get("remark").toString());
		oci.setUploadfile((String) dto.get("uploadfile"));
		oci.setDictCaseType(dto.get("dictCaseType").toString());
		oci.setAddtime(new Date());
		return oci;
	}

	public void delGeneralCaseinfo(String id) {
		OperCaseinfo u = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class, id);
		dao.removeEntity(u);
	}

	public IBaseDTO getGeneralCaseinfo(String id) {
		OperCaseinfo oci = (OperCaseinfo) dao
				.loadEntity(OperCaseinfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("caseId", oci.getCaseId());
		dto.set("caseRid", oci.getCaseRid().trim());
		dto.set("expertType", oci.getExpertType());
		dto.set("caseExpert", oci.getCaseExpert());
		dto.set("custName", oci.getCustName());
		dto.set("custAddr", oci.getCustAddr());
		dto.set("custTel", oci.getCustTel());
		dto.set("state", oci.getState());
		dto.set("caseAttr1", oci.getCaseAttr1());
		dto.set("caseAttr2", oci.getCaseAttr2());
		dto.set("caseAttr3", oci.getCaseAttr3());
		dto.set("caseAttr4", oci.getCaseAttr4());
		dto.set("caseAttr5", oci.getCaseAttr5());
		dto.set("caseContent", oci.getCaseContent());
		dto.set("caseReply", oci.getCaseReply());
		dto.set("caseReview", oci.getCaseReview());
		dto.set("casePic", oci.getCasePic());
		dto.set("caseVideo", oci.getCaseVideo());
		dto.set("remark", oci.getRemark());
		dto.set("uploadfile", oci.getUploadfile());
		dto.set("dictCaseType", oci.getDictCaseType());
		dto.set("caseTime", oci.getAddtime());
		return dto;
	}

	public int getGeneralCaseinfoSize() {
		return num;
	}

	public boolean updateGeneralCaseinfo(IBaseDTO dto) {
		dao.saveEntity(modifyGeneralCaseinfo(dto));
		return false;
	}

	private OperCaseinfo modifyGeneralCaseinfo(IBaseDTO dto) {
		OperCaseinfo oci = (OperCaseinfo) dao.loadEntity(OperCaseinfo.class,
				dto.get("caseId").toString());

		// sendSMS(oci,dto);//发送消息

		oci.setCaseAttr1(dto.get("caseAttr1").toString());
		oci.setCaseAttr2(dto.get("caseAttr2").toString());
		oci.setCaseAttr3(dto.get("caseAttr3").toString());
		oci.setCaseAttr4(dto.get("caseAttr4").toString());
		oci.setCaseAttr5(dto.get("caseAttr5").toString());
		oci.setCaseContent(dto.get("caseContent").toString());
		oci.setExpertType(dto.get("expertType").toString());
		oci.setCaseExpert(dto.get("caseExpert").toString());
		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setCasePic(dto.get("casePic").toString());
		oci.setCaseReply(dto.get("caseReply").toString());
		oci.setCaseReview(dto.get("caseReview").toString());
		// 指定提交人是谁
		String submitUser = dto.get("caseRid").toString();
		oci.setCaseRid(submitUser);
		oci.setCaseVideo(dto.get("caseVideo").toString());

		// if(state != null && state.equals("")){
		// oci.setklhasd(t)
		// }
		
		oci.setRemark(dto.get("remark").toString());
		oci.setUploadfile((String) dto.get("uploadfile"));
		oci.setDictCaseType(dto.get("dictCaseType").toString());
		String state = (String) dto.get("state");

		oci.setState(state);	
		
//		System.out.println("subid = "+dto.get("subid"));
//		System.out.println("accid = "+dto.get("accid"));	
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.DIANXINGANLI_MESSAGE, oci);		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("普通案例库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("普通案例库",
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
			flowService.addOrUpdateFlow("普通案例库",
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
	/**
   * @describe 查询普通案例库信列表for screen state='已审'
   * @param
   * @return List
   */
	public List generalCaseinfoQuery(int size,String state)
	{
		List list = new ArrayList();
		GeneralCaseinfoHelp sh = new GeneralCaseinfoHelp();
		Object[] result = (Object[]) dao.findEntity(sh.screenGeneralCaseinfoQuery(size,state));

		for (int i = 0, rSize = result.length; i < rSize; i++) {
			OperCaseinfo oci = (OperCaseinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("caseExpert", oci.getCaseExpert());
			dbd.set("custName", oci.getCustName());
			//dbd.set("custAddr", oci.getCustAddr());
			//dbd.set("custTel", oci.getCustTel());

			//dbd.set("state", oci.getState());

//			dbd.set("caseAttr1", oci.getCaseAttr1());
//			dbd.set("caseAttr2", oci.getCaseAttr2());
//			dbd.set("caseAttr3", oci.getCaseAttr3());
//			dbd.set("caseAttr4", oci.getCaseAttr4());
//			dbd.set("caseAttr5", oci.getCaseAttr5());

			String caseContent = oci.getCaseContent();
			dbd.set("caseContent", caseContent);

			String caseReply = oci.getCaseReply();
			dbd.set("caseReply", caseReply);

//			dbd.set("caseReview", oci.getCaseReview());
//			dbd.set("casePic", oci.getCasePic());
//			dbd.set("caseVideo", oci.getCaseVideo());
//			dbd.set("remark", oci.getRemark());
//			dbd.set("dictCaseType", oci.getDictCaseType());
//			dbd.set("caseTime", TimeUtil.getTheTimeStr(oci.getAddtime(),
//					"yyyy-MM-dd"));
			list.add(dbd);
		}
		return list;
	}
	public List generalCaseinfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		List list = new ArrayList();
		GeneralCaseinfoHelp sh = new GeneralCaseinfoHelp();
		StatDateStr.setBeginEndTimeAll(dto);
		Object[] result = (Object[]) dao.findEntity(sh.generalCaseinfoQuery(
				dto, pi));
		num = dao.findEntitySize(sh.generalCaseinfoQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			OperCaseinfo oci = (OperCaseinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("caseId", oci.getCaseId());
			dbd.set("caseRid", oci.getCaseRid());
			dbd.set("caseExpert", oci.getCaseExpert());
			dbd.set("custName", oci.getCustName());
			dbd.set("custAddr", oci.getCustAddr());
			dbd.set("custTel", oci.getCustTel());

			dbd.set("state", oci.getState());

			dbd.set("caseAttr1", oci.getCaseAttr1());
			dbd.set("caseAttr2", oci.getCaseAttr2());
			dbd.set("caseAttr3", oci.getCaseAttr3());
			dbd.set("caseAttr4", oci.getCaseAttr4());
			dbd.set("caseAttr5", oci.getCaseAttr5());

			String caseContent = oci.getCaseContent();
			if (caseContent != null && caseContent.length() > 15) {
				caseContent = caseContent.substring(0, 15) + "...";
			}
			dbd.set("caseContent", caseContent);

			String caseReply = oci.getCaseReply();
			if (caseReply != null && caseReply.length() > 15) {
				caseReply = caseReply.substring(0, 15) + "...";
			}
			dbd.set("caseReply", caseReply);

			dbd.set("caseReview", oci.getCaseReview());
			dbd.set("casePic", oci.getCasePic());
			dbd.set("caseVideo", oci.getCaseVideo());
			dbd.set("remark", oci.getRemark());
			dbd.set("dictCaseType", oci.getDictCaseType());
			dbd.set("caseTime", TimeUtil.getTheTimeStr(oci.getAddtime(),
					"yyyy-MM-dd"));
			list.add(dbd);
		}
		return list;
	}

	public void updatePhoto(String id, String path) {
		OperCaseinfo osi = (OperCaseinfo) dao
				.loadEntity(OperCaseinfo.class, id);
		osi.setCasePic(path);
		dao.updateEntity(osi);
	}

	public void updateVideo(String id, String path) {
		OperCaseinfo osi = (OperCaseinfo) dao
				.loadEntity(OperCaseinfo.class, id);
		osi.setCaseVideo(path);
		dao.updateEntity(osi);
	}

	public List exportQuery(String sql) {
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		List<OperCustinfo> list = new ArrayList<OperCustinfo>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				OperCustinfo oc = new OperCustinfo();
				oc.setCustName(rs.getString("cust_name"));
				list.add(oc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List userQuery(String sql) {
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		List<SysUser> list = new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su = new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// private void sendSMS(OperCaseinfo old,IBaseDTO now) {
	// if(!now.get("state").toString().equals("原始")&&!old.getState().equals(now.get("state").toString())){
	// if(old.getState().equals("原始")){
	// if(now.get("state").toString().equals("待审")){//up send
	// sendUpSMS();
	// }
	// }else if(old.getState().equals("待审")){
	// if(now.get("state").toString().equals("驳回")){//down send
	// sendDownSMS(old.getCaseRid(),"有一条待审信息被驳回");
	// }else if(now.get("state").toString().equals("已审")){//down send
	// sendDownSMS(old.getCaseRid(),"有一条待审信息通过审核");
	// }
	// }else if(old.getState().equals("驳回")){//up send
	// sendUpSMS();
	// }
	// // else if(old.getState().equals("已审")){
	// //
	// // }else if(old.getState().equals("发布")){
	// // }
	//			
	// }
	// }

	// private void sendUpSMS(){
	// MyQuery mq=new MyQueryImpl();
	// DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
	// dc.add(Restrictions.like("auditing","%普通案例库%"));
	// mq.setDetachedCriteria(dc);
	// Object[] result = (Object[]) dao.findEntity(mq);
	// for (int i = 0, size = result.length; i < size; i++) {
	// SysUser sysuser = (SysUser) result[i];
	// StaticServlet.messageMap.put(sysuser.getUserId(), "在普通案例库，有一条待审信息！");
	// System.out.println(sysuser.getUserId()+":在普通案例库，有一条待审信息！");
	// }
	// }
	//	
	// private void sendDownSMS(String name,String content){
	// StaticServlet.messageMap.put(name, "在普通案例库，"+content);
	// }

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
					//如果普通案例不为空
					if (m.get(SysStaticParameter.DIANXINGANLI_MESSAGE)!=null) {
						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.DIANXINGANLI_MESSAGE);	
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
				List l_agent = StaticServlet.userPowerMap.get("普通案例库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果普通案例不为空
							if (m.get(SysStaticParameter.DIANXINGANLI_MESSAGE)!=null) {
								OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.DIANXINGANLI_MESSAGE);	
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

	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCaseinfo.class);
		
		dc.add(Restrictions.eq("dictCaseType", "putong"));
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCaseinfo oci = (OperCaseinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("casecontent", oci.getCaseContent());
			dbd.set("casereply", oci.getCaseReply());
			l.add(dbd);
		}
		return l;

	}	
	
}
