package et.bo.linkmanPriceinfo.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import et.bo.flow.service.FlowService;
import et.bo.linkmanPriceinfo.service.LinkmanPriceinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCustinfo;
import et.po.OperPriceinfo;
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

public class LinkmanPriceinfoImpl implements LinkmanPriceinfoService {


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
		dao.saveEntity(createOperPriceinfo(dto));
	}
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	
	private OperPriceinfo createOperPriceinfo(IBaseDTO dto) {
		OperPriceinfo opi = new OperPriceinfo();
		
		
		String id = ks.getNext("oper_priceinfo");
		opi.setPriceId(id);
//		添加客户ID
		
			String cust_id = (String)dto.get("cust_id");
			if(cust_id != null && !cust_id.equals("")){
				LinkmanPriceinfoHelp lph = new LinkmanPriceinfoHelp();
				Object[] result = dao.findEntity(lph.custinfoQuery(cust_id));
				if(result != null && result.length > 0){
					OperCustinfo ocf = (OperCustinfo)result[0];
					opi.setOperCustinfo(ocf);
					opi.setCustNumber(ocf.getCustNumber());
					opi.setCustType(ocf.getDictCustType());
					opi.setCustAddr(ocf.getCustAddr());
				}
			}
		
		
//		opi.setCustAddr(dto.get("custAddr").toString());
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
//		opi.setCustAddr(dto.get("custAddr").toString());
		opi.setCustName(dto.get("custName").toString());
		opi.setCustTel(dto.get("custTel").toString());
		
		
		String cust_id = (String)dto.get("cust_id");
		if(cust_id != null && !cust_id.equals("")){
			LinkmanPriceinfoHelp lph = new LinkmanPriceinfoHelp();
			Object[] result = dao.findEntity(lph.custinfoQuery(cust_id));
			if(result != null && result.length > 0){
				OperCustinfo ocf = (OperCustinfo)result[0];
				opi.setOperCustinfo(ocf);
				opi.setCustNumber(ocf.getCustNumber());
				opi.setCustType(ocf.getDictCustType());
				opi.setCustAddr(ocf.getCustAddr());
			}
		}
		
		if(!dto.get("deployTime").equals(""))
		{
			opi.setDeployTime(TimeUtil.getTimeByStr(dto.get("deployTime").toString(),"yyyy-MM-dd"));
		}
		
		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("农产品价格库", (String)dto.get("priceId"), state, (String)dto.get("subid"));
		flowService.addOrUpdateFlow("农产品价格库", (String)dto.get("priceId"), state, (String)dto.get("subid"),opi.getState());
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

		return opi;
	 }

	public List operPriceinfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		
		
		List list = new ArrayList();

		LinkmanPriceinfoHelp sh = new LinkmanPriceinfoHelp();
		
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
			 dbd.set("cust_number", opi.getCustNumber());
			 
			 dbd.set("state", opi.getState());

			 if(opi.getDictPriceType()!=null && !opi.getDictPriceType().equals(""))
			 {
				 if(!cts.getLabelById(opi.getDictPriceType()).equals(""))
				 {
					 dbd.set("dictPriceType",cts.getLabelById(opi.getDictPriceType()));
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

//	 人员列表为查询页面提供数据
	public List<LabelValueBean> getUserList() {
		List<LabelValueBean> uList = new ArrayList<LabelValueBean>();
		String hql = SysStaticParameter.QUERY_LINK_SQL;
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os = dao.findEntity(mq);
		LabelValueBean bean = null;
		OperCustinfo su = new OperCustinfo();
		for (int i = 0; i < os.length; i++) {
			bean = new LabelValueBean();
			su = (OperCustinfo) os[i];
			try {
				bean.setLabel(su.getCustName());
				bean.setValue(su.getCustId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			uList.add(bean);
		}

		return uList;
	}
}
