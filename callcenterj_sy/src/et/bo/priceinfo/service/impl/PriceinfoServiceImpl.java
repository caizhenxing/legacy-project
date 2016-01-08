package et.bo.priceinfo.service.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import et.bo.flow.service.FlowService;
import et.bo.linkmanPriceinfo.service.impl.LinkmanPriceinfoHelp;
import et.bo.messages.show.MessageCollection;
import et.bo.priceinfo.service.PriceinfoService;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCustinfo;
import et.po.OperPriceinfo;
import et.po.OperQuestion;
import et.po.OperSadinfo;
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

public class PriceinfoServiceImpl implements PriceinfoService {


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

	public void addOperPriceinfoSad(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String dictPriceType = dto.get("dictPriceType").toString();
        if("SYS_TREE_0000000627".equals(dictPriceType)) dto.set("dictPriceType", "收购价");
        if("SYS_TREE_0000000628".equals(dictPriceType)) dto.set("dictPriceType", "批发价");
        if("SYS_TREE_0000000629".equals(dictPriceType)) dto.set("dictPriceType", "零售价");
//		if(!cts.getLabelById(dictPriceType).equals("")){
//			dto.set("dictPriceType",dictPriceType);
//			 System.out.println("###"+cts.getLabelById(dictPriceType)+dictPriceType);
//		 }
		dao.saveEntity(createOperPriceinfo(dto));
	}
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
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
	private OperPriceinfo createOperPriceinfo(IBaseDTO dto) {
		OperPriceinfo opi = new OperPriceinfo();
		
		
		String id = ks.getNext("oper_priceinfo");
		opi.setPriceId(id);
//		添加客户ID
		
			String cust_id = (String)dto.get("cust_id");
//			if(cust_id != null && !cust_id.equals("")){
//				OperCustinfo ocf = new OperCustinfo();
//				ocf.setCustId(cust_id);
//				opi.setOperCustinfo(ocf);
//			}
			
			if(cust_id != null && !cust_id.equals("")){
				LinkmanPriceinfoHelp lph = new LinkmanPriceinfoHelp();
				Object[] result = dao.findEntity(lph.custinfoQuery(cust_id));
				if(result != null && result.length > 0){
					OperCustinfo ocf = (OperCustinfo)result[0];
					opi.setOperCustinfo(ocf);
					opi.setCustNumber(ocf.getCustNumber());
					opi.setCustType(ocf.getDictCustType());
//					opi.setCustAddr(ocf.getCustAddr());
				}
			}
		
		
		opi.setCustAddr(dto.get("custAddr").toString());
		opi.setCustName(dto.get("custName").toString());
		opi.setCustTel(dto.get("custTel").toString());
		
		
		if(!dto.get("deployTime").equals(""))
		{
			opi.setDeployTime(TimeUtil.getTimeByStr(dto.get("deployTime").toString(),"yyyy-MM-dd"));
		}else{
			opi.setDeployTime(TimeUtil.getNowTime());//修改代码
		}
		System.out.println("opi.getDeployTime(): "+opi.getDeployTime());
	
		String state = (String) dto.get("state");
		if(state == null || state.equals("")) state = "原始";
//		flowService.addOrUpdateFlow("农产品价格库", id, state, (String)dto.get("subid"));
		System.out.println("id is "+id);
		System.out.println("state is "+state);
		System.out.println("subid is "+(String)dto.get("subid"));
		flowService.addOrUpdateFlow("农产品价格库", id, state, dto.get("subid").toString(),"");
		opi.setState(state);
		
		opi.setDictPriceType(dto.get("dictPriceType").toString());
		
		opi.setOperTime(TimeUtil.getNowTime());
		opi.setPriceExpert(dto.get("priceExpert").toString());
		opi.setPriceRid(dto.get("priceRid").toString());
		opi.setPriceUnit(dto.get("priceUnit").toString());	
		opi.setProductName(dto.get("productName").toString());
		opi.setDictProductType1(dto.get("dictProductType1").toString());
		opi.setDictProductType2(dto.get("dictProductType2").toString());
		opi.setProductPrice(dto.get("productPrice").toString());
		opi.setProductScale(dto.get("productScale").toString());
		opi.setRemark(dto.get("remark").toString());
		opi.setAddtime(TimeUtil.getNowTime());//修改代码
		opi.setQuestionId(dto.get("question_id").toString());

		return opi;
	}

	
	
	
	 public void delOperPriceinfo(String id) {
	 // TODO Auto-generated method stub
		 OperPriceinfo u = (OperPriceinfo)dao.loadEntity(OperPriceinfo.class, id);
		 dao.removeEntity(u);
	 }


	 
	public IBaseDTO getOperPriceinfo(String id) {
	 // TODO Auto-generated method stub
	 OperPriceinfo opi = (OperPriceinfo)dao.loadEntity(OperPriceinfo.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 
	 dto.set("priceId", opi.getPriceId());
	 dto.set("priceRid", opi.getPriceRid());
	 if(opi.getPriceExpert() != null && !opi.getPriceExpert().equals("")){
		 dto.set("priceExpert", cts.getLabelById(opi.getPriceExpert()));
	 }else{
		 dto.set("priceExpert", "");
	 }

	 dto.set("custName", opi.getCustName());
	 dto.set("custAddr", opi.getCustAddr());
	 dto.set("custTel", opi.getCustTel());
	 dto.set("state", opi.getState());
	 dto.set("dictPriceType", opi.getDictPriceType());
	 dto.set("dictProductType1", opi.getDictProductType1());
	 dto.set("dictProductType2", opi.getDictProductType2());
	 dto.set("productScale", opi.getProductScale());
	 dto.set("productPrice", opi.getProductPrice());
	 dto.set("priceUnit", opi.getPriceUnit());
	 dto.set("deployTime", TimeUtil.getTheTimeStr(opi.getDeployTime(),"yyyy-MM-dd"));
	 dto.set("productName",opi.getProductName());
	 dto.set("remark", opi.getRemark());
	 
	 return dto;
	 }

	
	
	public int getOperPriceinfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateOperPriceinfo(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifySad(dto));
	 return false;
	 }
		
	private OperPriceinfo modifySad(IBaseDTO dto){
		
	
		
		OperPriceinfo opi = (OperPriceinfo)dao.loadEntity(OperPriceinfo.class, dto.get("priceId").toString());
//		opi.setPriceId(ks.getNext("oper_priceinfo"));
		opi.setCustAddr(dto.get("custAddr").toString());
		opi.setCustName(dto.get("custName").toString());
		opi.setCustTel(dto.get("custTel").toString());
		
		
		if(!dto.get("deployTime").equals(""))
		{
			opi.setDeployTime(TimeUtil.getTimeByStr(dto.get("deployTime").toString(),"yyyy-MM-dd"));
		}
		
		String state = (String) dto.get("state");
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.JIAGE_MESSAGE, opi);
		
//		flowService.addOrUpdateFlow("农产品价格库", (String)dto.get("priceId"), state, (String)dto.get("subid"));
		//flowService.addOrUpdateFlow("农产品价格库", (String)dto.get("priceId"), state, (String)dto.get("subid"),opi.getState());
		opi.setState(state);
		
		opi.setDictPriceType(dto.get("dictPriceType").toString());
		opi.setOperTime(TimeUtil.getNowTime());
		opi.setPriceExpert(dto.get("priceExpert").toString());
		
		String submitUser = dto.get("priceRid").toString();
		opi.setPriceRid(submitUser);
		
		opi.setPriceUnit(dto.get("priceUnit").toString());
		opi.setProductName(dto.get("productName").toString());
		opi.setDictProductType1(dto.get("dictProductType1").toString());
		opi.setDictProductType2(dto.get("dictProductType2").toString());
		opi.setProductPrice(dto.get("productPrice").toString());
		opi.setProductScale(dto.get("productScale").toString());
		opi.setRemark(dto.get("remark").toString());
		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("农产品价格库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("农产品价格库",
						(String) dto.get("priceId"), state, submitUser,
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
			flowService.addOrUpdateFlow("农产品价格库",
					(String) dto.get("priceId"), state, submitUser,
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
		
		return opi;
	 }

	/**
	 * 获取screen的价格看板数据
	 */
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperPriceinfo.class);
		
//		dc.add(Restrictions.eq("dictCaseType", "putong"));
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(50);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperPriceinfo op = (OperPriceinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			//产品
			String productName = op.getProductName();
			dbd.set("productName", productName);
			//产地
			String custAddr = op.getCustAddr();
			dbd.set("custAddr", custAddr);
			//价格类型
			String dictPriceType = op.getDictPriceType();
			dbd.set("dictPriceType", dictPriceType);
			//价格
			String productPrice = op.getProductPrice();
			dbd.set("productPrice", productPrice);
			//走势
			
			l.add(dbd);
		}
		return l;
	}	
	/**
	 * @describe 查询市场价格列表forExcel
	 * @param
	 * @return List
	 */ 
	public List<List<String>> operPriceinfoExcelQuery(IBaseDTO dto)
	{
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> titles = new ArrayList<String>();
		List<String> contents= null;
		//deployTime,productName,custAddr,productPrice,dictPriceType,state,priceRid
		//报价时间,产品名称,产地,价格,价格类型,审核状态,工号
		titles.add("报价时间");
		titles.add("产品名称");
		titles.add("产地");
		titles.add("价格");
		titles.add("价格类型");
		titles.add("备注");
//		titles.add("审核状态");
//		titles.add("工号");
		list.add(titles);
		PriceinfoHelp sh = new PriceinfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(sh.operPriceinfoExcelQuery(dto));

		
		for (int i = 0, size = result.length; i < size; i++) {
			OperPriceinfo opi = (OperPriceinfo) result[i];
			contents = new ArrayList<String>();
			contents.add(TimeUtil.getTheTimeStr(opi.getDeployTime(),"yyyy-MM-dd"));
			contents.add(opi.getProductName());
			contents.add(opi.getCustAddr());
			contents.add(opi.getProductPrice());
			contents.add(opi.getDictPriceType()==null?"":opi.getDictPriceType());
			contents.add(opi.getRemark()==null?"":opi.getRemark());
//			contents.add(opi.getState());
//			contents.add(opi.getPriceRid());
			list.add(contents);

			
		}
		return list;
	}
	public List operPriceinfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		
		
		List list = new ArrayList();

		PriceinfoHelp sh = new PriceinfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(sh.operPriceinfoQuery(dto, pi));
		num = dao.findEntitySize(sh.operPriceinfoQuery(dto, pi));

		
		for (int i = 0, size = result.length; i < size; i++) {
			OperPriceinfo opi = (OperPriceinfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			 dbd.set("priceId", opi.getPriceId());
			 dbd.set("priceRid", opi.getPriceRid());
			 dbd.set("priceExpert", opi.getPriceExpert());
			 String date = DateFormat.getDateInstance().format(opi.getOperTime());
			 dbd.set("oper_time", date);
			 dbd.set("custName", opi.getCustName());
			 dbd.set("custAddr", opi.getCustAddr());
			 dbd.set("custTel", opi.getCustTel());
			 
			 dbd.set("state", opi.getState());

			 if(opi.getDictPriceType()!=null && !opi.getDictPriceType().equals(""))
			 {
				 try
				 {
					 dbd.set("dictPriceType",opi.getDictPriceType());
//					 if(!cts.getLabelById(opi.getDictPriceType()).equals(""))
//					 {
						 
//						 dbd.set("dictPriceType",cts.getLabelById(opi.getDictPriceType()));
//						 System.out.println("###"+cts.getLabelById(opi.getDictPriceType())+opi.getDictPriceType());
//					 }
				 }
				 catch(Exception e)
				 {
					 
				 }
			 }
			 
			 dbd.set("productName",opi.getProductName());
			 dbd.set("dictProductType1", opi.getDictProductType1());
			 dbd.set("dictProductType2", opi.getDictProductType2());
			 dbd.set("productScale", opi.getProductScale());
			 dbd.set("productPrice", opi.getProductPrice());
			 dbd.set("priceUnit", opi.getPriceUnit());
			 dbd.set("deployTime",TimeUtil.getTheTimeStr(opi.getDeployTime(),"yyyy-MM-dd"));
			 dbd.set("remark", opi.getRemark());
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
					//如果农产品价格库案例不为空
					if (m.get(SysStaticParameter.JIAGE_MESSAGE)!=null) {
						OperPriceinfo oc = (OperPriceinfo)m.get(SysStaticParameter.JIAGE_MESSAGE);	
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
				List l_agent = StaticServlet.userPowerMap.get("农产品价格库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果农产品价格库不为空
							if (m.get(SysStaticParameter.JIAGE_MESSAGE)!=null) {
								OperPriceinfo oc = (OperPriceinfo)m.get(SysStaticParameter.JIAGE_MESSAGE);	
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
