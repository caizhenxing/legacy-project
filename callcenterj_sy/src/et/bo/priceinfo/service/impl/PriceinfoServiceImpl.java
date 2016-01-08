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
        if("SYS_TREE_0000000627".equals(dictPriceType)) dto.set("dictPriceType", "�չ���");
        if("SYS_TREE_0000000628".equals(dictPriceType)) dto.set("dictPriceType", "������");
        if("SYS_TREE_0000000629".equals(dictPriceType)) dto.set("dictPriceType", "���ۼ�");
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
//		��ӿͻ�ID
		
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
			opi.setDeployTime(TimeUtil.getNowTime());//�޸Ĵ���
		}
		System.out.println("opi.getDeployTime(): "+opi.getDeployTime());
	
		String state = (String) dto.get("state");
		if(state == null || state.equals("")) state = "ԭʼ";
//		flowService.addOrUpdateFlow("ũ��Ʒ�۸��", id, state, (String)dto.get("subid"));
		System.out.println("id is "+id);
		System.out.println("state is "+state);
		System.out.println("subid is "+(String)dto.get("subid"));
		flowService.addOrUpdateFlow("ũ��Ʒ�۸��", id, state, dto.get("subid").toString(),"");
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
		opi.setAddtime(TimeUtil.getNowTime());//�޸Ĵ���
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
		
//		flowService.addOrUpdateFlow("ũ��Ʒ�۸��", (String)dto.get("priceId"), state, (String)dto.get("subid"));
		//flowService.addOrUpdateFlow("ũ��Ʒ�۸��", (String)dto.get("priceId"), state, (String)dto.get("subid"),opi.getState());
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
		
//		 ������״̬Ϊ��������󣬷��͵ķ�����������˵ķ����Ͷ���Ϣ
		if (state.equals("����") || state.equals("����")) {
			List l = StaticServlet.userPowerMap.get("ũ��Ʒ�۸��");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("ũ��Ʒ�۸��",
						(String) dto.get("priceId"), state, submitUser,
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
			flowService.addOrUpdateFlow("ũ��Ʒ�۸��",
					(String) dto.get("priceId"), state, submitUser,
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
		}else if (state.equals("����")) {
			//osi.setCaseTime(TimeUtil.getNowTime());
		}
		
		return opi;
	 }

	/**
	 * ��ȡscreen�ļ۸񿴰�����
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
			
			//��Ʒ
			String productName = op.getProductName();
			dbd.set("productName", productName);
			//����
			String custAddr = op.getCustAddr();
			dbd.set("custAddr", custAddr);
			//�۸�����
			String dictPriceType = op.getDictPriceType();
			dbd.set("dictPriceType", dictPriceType);
			//�۸�
			String productPrice = op.getProductPrice();
			dbd.set("productPrice", productPrice);
			//����
			
			l.add(dbd);
		}
		return l;
	}	
	/**
	 * @describe ��ѯ�г��۸��б�forExcel
	 * @param
	 * @return List
	 */ 
	public List<List<String>> operPriceinfoExcelQuery(IBaseDTO dto)
	{
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> titles = new ArrayList<String>();
		List<String> contents= null;
		//deployTime,productName,custAddr,productPrice,dictPriceType,state,priceRid
		//����ʱ��,��Ʒ����,����,�۸�,�۸�����,���״̬,����
		titles.add("����ʱ��");
		titles.add("��Ʒ����");
		titles.add("����");
		titles.add("�۸�");
		titles.add("�۸�����");
		titles.add("��ע");
//		titles.add("���״̬");
//		titles.add("����");
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
	 * ״̬ת��
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
					//���ũ��Ʒ�۸�ⰸ����Ϊ��
					if (m.get(SysStaticParameter.JIAGE_MESSAGE)!=null) {
						OperPriceinfo oc = (OperPriceinfo)m.get(SysStaticParameter.JIAGE_MESSAGE);	
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
				List l_agent = StaticServlet.userPowerMap.get("ũ��Ʒ�۸��");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//���ũ��Ʒ�۸�ⲻΪ��
							if (m.get(SysStaticParameter.JIAGE_MESSAGE)!=null) {
								OperPriceinfo oc = (OperPriceinfo)m.get(SysStaticParameter.JIAGE_MESSAGE);	
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
