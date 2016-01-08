package et.bo.sad.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.flow.service.FlowService;
import et.bo.messages.show.MessageCollection;
import et.bo.sad.service.SadService;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.ExpertgroupHotline;
import et.po.OperSadinfo;
import et.po.OperCustinfo;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class SadServiceImpl implements SadService {


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
	
	public void addSad(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createSad(dto));
	}

	
	private OperSadinfo createSad(IBaseDTO dto) {
		OperSadinfo osi = new OperSadinfo();	
		String id = ks.getNext("oper_sadinfo");
		osi.setSadId(id);		
		if(!dto.get("cust_id").equals(""))
		{
			String cust_id = dto.get("cust_id").toString();
			if(cust_id != null && !cust_id.equals("")){
				OperCustinfo ocf = new OperCustinfo();
				ocf.setCustId(cust_id);
				osi.setOperCustinfo(ocf);
			}
		}
		osi.setCaseExpert(dto.get("caseExpert").toString());
		osi.setCountUnit(dto.get("countUnit").toString());
		osi.setCustAddr(dto.get("custAddr").toString());
		osi.setCustName(dto.get("custName").toString());
		osi.setCustTel(dto.get("custTel").toString());		
		if(!dto.get("deployBegin").equals(""))
		{
			osi.setDeployBegin(TimeUtil.getTimeByStr(dto.get("deployBegin").toString(),"yyyy-MM-dd"));
		}				
		if(!dto.get("deployEnd").equals(""))
		{
			osi.setDeployEnd(TimeUtil.getTimeByStr(dto.get("deployEnd").toString(),"yyyy-MM-dd"));
		}
		osi.setPriceUnit(dto.get("priceUnit").toString());
		osi.setProductCount(dto.get("productCount").toString());
		osi.setProductName(dto.get("productName").toString());
		osi.setProductPic(dto.get("productPic").toString());
		osi.setProductPrice(dto.get("productPrice").toString());
		osi.setProductScale(dto.get("productScale").toString());
		osi.setRemark(dto.get("remark").toString());

		String state = (String) dto.get("state");
		if(state == null || state.equals("")) state = "原始";
//		flowService.addOrUpdateFlow("农产品供求库", id, state, (String) dto.get("subid"));
		//flowService.addOrUpdateFlow("农产品供求库", id, state, (String) dto.get("subid"),null);
		osi.setState(state);
		
		osi.setSadTime(TimeUtil.getNowTime());		
		osi.setDictSadType(dto.get("dictSadType").toString());		
		osi.setSadRid(dto.get("sadRid").toString());		
		osi.setPost(dto.get("post").toString());
		osi.setQuestionId(dto.get("question_id").toString());
		osi.setUploadfile((String)dto.get("uploadfile"));
		return osi;
	}
	
	 public void delSad(String id) {
	 // TODO Auto-generated method stub
		 OperSadinfo u = (OperSadinfo)dao.loadEntity(OperSadinfo.class, id);
	 dao.removeEntity(u);
	 }
 
	public IBaseDTO getSadInfo(String id) {
	 // TODO Auto-generated method stub
	 OperSadinfo osi = (OperSadinfo)dao.loadEntity(OperSadinfo.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("sadId", id);
	 dto.set("sadRid", osi.getSadRid());	 
	 dto.set("caseExpert", osi.getCaseExpert());

	 String sadTime = "";
	 if( osi.getSadTime()!= null && ! (osi.getSadTime().equals(""))){
		 sadTime = TimeUtil.getTheTimeStr(osi.getSadTime(), "yyyy-MM-dd");
	 }
	 dto.set("sadTime", sadTime);
	 
	 dto.set("custName", osi.getCustName());
	 dto.set("custAddr", osi.getCustAddr());
	 dto.set("custTel", osi.getCustTel());
	 dto.set("state", osi.getState());
	 dto.set("productName", osi.getProductName());
	 dto.set("productScale", osi.getProductScale());
	 dto.set("productCount", osi.getProductCount());
	 dto.set("countUnit", osi.getCountUnit());
	 dto.set("productPrice", osi.getProductPrice());
	 dto.set("priceUnit", osi.getPriceUnit());
	 
	 String deployBegin = "";
	 if( osi.getDeployBegin() != null && ! (osi.getDeployBegin().equals(""))){
		 deployBegin = TimeUtil.getTheTimeStr(osi.getDeployBegin(), "yyyy-MM-dd");
	 }
	 dto.set("deployBegin", deployBegin);
	 
	 String deployEnd = "";
	 if( osi.getDeployEnd() != null && ! (osi.getDeployEnd().equals(""))){
		 deployEnd = TimeUtil.getTheTimeStr(osi.getDeployEnd(), "yyyy-MM-dd");
	 }
	 dto.set("deployEnd", deployEnd);
	 
	 dto.set("productPic", osi.getProductPic());

	
	 dto.set("dictSadType", osi.getDictSadType());	 
	 dto.set("remark", osi.getRemark());
	 dto.set("post", osi.getPost());
	 dto.set("uploadfile", osi.getUploadfile());
	 return dto;
	 }
	public int getSadSize() {
		// TODO Auto-generated method stub
		return num;
	}
	public boolean updateSad(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifySad(dto));
	 return false;
	 }
		
	private OperSadinfo modifySad(IBaseDTO dto){	
		OperSadinfo osi = (OperSadinfo)dao.loadEntity(OperSadinfo.class, dto.get("sadId").toString());
		osi.setCaseExpert(dto.get("caseExpert").toString());
		osi.setCountUnit(dto.get("countUnit").toString());
		osi.setCustAddr(dto.get("custAddr").toString());
		osi.setCustName(dto.get("custName").toString());
		osi.setCustTel(dto.get("custTel").toString());
		if(!dto.get("deployBegin").equals(""))
		{
			osi.setDeployBegin(TimeUtil.getTimeByStr(dto.get("deployBegin").toString(),"yyyy-MM-dd"));
		}				
		if(!dto.get("deployEnd").equals(""))
		{
			osi.setDeployEnd(TimeUtil.getTimeByStr(dto.get("deployEnd").toString(),"yyyy-MM-dd"));
		}
		osi.setPriceUnit(dto.get("priceUnit").toString());
		osi.setProductCount(dto.get("productCount").toString());
		osi.setProductName(dto.get("productName").toString());
		osi.setProductPic(dto.get("productPic").toString());
		osi.setProductPrice(dto.get("productPrice").toString());
		osi.setProductScale(dto.get("productScale").toString());
		osi.setRemark(dto.get("remark").toString());		

		String state = (String) dto.get("state");
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.GONGQIU_MESSAGE, osi);
		
//		flowService.addOrUpdateFlow("农产品供求库", (String) dto.get("sadId"), state, (String) dto.get("subid"));
		//flowService.addOrUpdateFlow("农产品供求库", (String) dto.get("sadId"), state, (String) dto.get("subid"),osi.getState());
		osi.setState(state);
		
		osi.setSadTime(TimeUtil.getNowTime());
		osi.setDictSadType(dto.get("dictSadType").toString());
		
		String submitUser = dto.get("sadRid").toString();
		osi.setSadRid(submitUser);
		
		osi.setPost(dto.get("post").toString());
		osi.setUploadfile((String)dto.get("uploadfile"));
		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("农产品供求库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("农产品供求库",
						(String) dto.get("sadId"), state, submitUser,
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
			flowService.addOrUpdateFlow("农产品供求库",
					(String) dto.get("sadId"), state, submitUser,
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
			//osi.setCaseTime(TimeUtil.getNowTime());
		}
	 
		return osi;
	 }

	public List sadQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub			
		List list = new ArrayList();
		SadHelp sh = new SadHelp();	
		Object[] result = (Object[]) dao.findEntity(sh.sadQuery(dto, pi));
		num = dao.findEntitySize(sh.sadQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			OperSadinfo osi = (OperSadinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();			
			dbd.set("sadId", osi.getSadId());	
			
			 if(osi.getDictSadType()!=null && !osi.getDictSadType().equals(""))
			 {	
				 dbd.set("dictSadType",cts.getLabelById(osi.getDictSadType()));
			 }			
			dbd.set("sadRid", osi.getSadRid()); 
			dbd.set("caseExpert", osi.getCaseExpert());
			dbd.set("sadTime", TimeUtil.getTheTimeStr(osi.getSadTime(), "yyyy-MM-dd"));
			dbd.set("custName", osi.getCustName());
			dbd.set("custAddr", osi.getCustAddr());
			dbd.set("custTel", osi.getCustTel());
			
			dbd.set("state", osi.getState());
			
			dbd.set("productName", osi.getProductName());
			dbd.set("productScale", osi.getProductScale());
			dbd.set("productCount", osi.getProductCount());
			dbd.set("countUnit", osi.getCountUnit());
			dbd.set("productPrice", osi.getProductPrice());
			dbd.set("priceUnit", osi.getPriceUnit());
			dbd.set("deployBegin", osi.getDeployBegin());
			dbd.set("deployEnd", osi.getDeployEnd());
			dbd.set("productPic", osi.getProductPic());
			dbd.set("remark", osi.getRemark());
			dbd.set("post", osi.getPost());
			list.add(dbd);
		}
		return list;
	}
	
	public List sadInfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub			
		List list = new ArrayList();
		SadHelp sh = new SadHelp();	
		Object[] result = (Object[]) dao.findEntity(sh.sadQuery(dto, pi));
		num = dao.findEntitySize(sh.sadInfoQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			OperSadinfo osi = (OperSadinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();			
			dbd.set("sadId", osi.getSadId());	
			
			 if(osi.getDictSadType()!=null && !osi.getDictSadType().equals(""))
			 {	
				 dbd.set("dictSadType",cts.getLabelById(osi.getDictSadType()));
			 }			
			dbd.set("sadRid", osi.getSadRid()); 
			dbd.set("caseExpert", osi.getCaseExpert());
			dbd.set("sadTime", osi.getSadTime());
			dbd.set("custName", osi.getCustName());
			dbd.set("custAddr", osi.getCustAddr());
			dbd.set("custTel", osi.getCustTel());
			
			dbd.set("state", osi.getState());
			
			dbd.set("productName", osi.getProductName());
			dbd.set("productScale", osi.getProductScale());
			dbd.set("productCount", osi.getProductCount());
			dbd.set("countUnit", osi.getCountUnit());
			dbd.set("productPrice", osi.getProductPrice());
			dbd.set("priceUnit", osi.getPriceUnit());
			dbd.set("deployBegin", osi.getDeployBegin());
			dbd.set("deployEnd", osi.getDeployEnd());
			dbd.set("productPic", osi.getProductPic());
			dbd.set("remark", osi.getRemark());
			dbd.set("post", osi.getPost()); 
			list.add(dbd);
		}
		return list;
	}

	
	 public void updatePhoto(String id,String path)
	 { 
		 OperSadinfo osi = (OperSadinfo)dao.loadEntity(OperSadinfo.class, id); 
		 
		
		 
		 osi.setProductPic(path);
		 dao.updateEntity(osi);
		 
	 }
	
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
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
					//如果农产品供求库案例不为空
					if (m.get(SysStaticParameter.GONGQIU_MESSAGE)!=null) {
						OperSadinfo oc = (OperSadinfo)m.get(SysStaticParameter.GONGQIU_MESSAGE);	
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
				List l_agent = StaticServlet.userPowerMap.get("农产品供求库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果效果案例不为空
							if (m.get(SysStaticParameter.GONGQIU_MESSAGE)!=null) {
								OperSadinfo oc = (OperSadinfo)m.get(SysStaticParameter.GONGQIU_MESSAGE);	
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
	/**
	 * 获得受理工号列表
	 */
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
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperSadinfo.class);
//		dc.add(Restrictions.eq("state", "已审"));
//		dc.addOrder(Order.desc("sadId"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperSadinfo osi = (OperSadinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("productName", osi.getProductName());//产品名
			 if(osi.getDictSadType()!=null && !osi.getDictSadType().equals(""))
			 {	
				 dbd.set("dictSadType",cts.getLabelById(osi.getDictSadType()));//供、求
			 }	
			dbd.set("productCount", osi.getProductCount());//产品数量
			dbd.set("custTel", osi.getCustTel());//联系电话
//			System.out.println("id: "+osi.getSadId());
//			System.out.println("pro_name:"+osi.getProductName());
			l.add(dbd);
		}
		return l;
	}	
}
