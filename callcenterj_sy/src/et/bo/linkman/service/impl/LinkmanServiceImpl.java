package et.bo.linkman.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.RowSet;

import et.bo.corpinfo.service.CorpinfoService;
import et.bo.flow.service.FlowService;
import et.bo.linkman.service.LinkmanService;
import et.po.OperCorpinfo;
import et.po.OperQuestion;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class LinkmanServiceImpl implements LinkmanService {

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

	private OperCorpinfo createOperCorpinfo(IBaseDTO dto) {
		OperCorpinfo oci = new OperCorpinfo();

		oci.setId(ks.getNext("oper_corpinfo"));
		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setContents(dto.get("contents").toString());
		oci.setCorpRid(dto.get("corpRid").toString());
		oci.setCreatetime(TimeUtil.getNowTime());
		oci.setDictServiceType(dto.get("dictServiceType").toString());
		oci.setExpert(dto.get("expert").toString());
		oci.setRemark(dto.get("remark").toString());
		oci.setReply(dto.get("reply").toString());
		if (dto.get("createTime")!=null&&dto.get("createTime").toString().length()>=10){
			oci.setCreatetime(TimeUtil.getTimeByStr(dto.get("createTime")
				.toString(), "yyyy-MM-dd"));
		}
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
		dto.set("dictServiceType", oci.getDictServiceType());

		dto.set("contents", oci.getContents());
		dto.set("reply", oci.getReply());
		dto.set("remark", oci.getRemark());
		if (oci.getCreatetime()!=null&&oci.getCreatetime().toString().length()>=10){
			dto.set("createTime", oci.getCreatetime().toString().substring(0, 10));
		}
		return dto;
	}

	public int getLinkmanSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public boolean updateOperCorpinfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(modifySad(dto));
		return false;
	}

	private OperCorpinfo modifySad(IBaseDTO dto) {

		OperCorpinfo oci = (OperCorpinfo) dao.loadEntity(OperCorpinfo.class,
				dto.get("id").toString());

		oci.setCustAddr(dto.get("custAddr").toString());
		oci.setCustName(dto.get("custName").toString());
		oci.setCustTel(dto.get("custTel").toString());
		oci.setContents(dto.get("contents").toString());
		oci.setCorpRid(dto.get("corpRid").toString());
		oci.setCreatetime(TimeUtil.getNowTime());

		oci.setDictServiceType(dto.get("dictServiceType").toString());
		oci.setExpert(dto.get("expert").toString());
		oci.setRemark(dto.get("remark").toString());
		oci.setReply(dto.get("reply").toString());
		if (dto.get("createTime")!=null&&dto.get("createTime").toString().length()>=10){
			
			oci.setCreatetime(TimeUtil.getTimeByStr(dto.get("createTime")
				.toString().substring(0, 10), "yyyy-MM-dd"));
		}
		return oci;
	}

	public List linkmanQuery(IBaseDTO dto, PageInfo pi,int pageState) {
		List list = new ArrayList();

		LinkmanHelp sh = new LinkmanHelp();
		
		RowSet set = (RowSet) dao.getRowSetByJDBCsql(sh.linkmanQuery(dto, pi));
		
		try {
			int num1=0;
			set.beforeFirst();
			while (set.next()) {
				DynaBeanDTO dbd = new DynaBeanDTO();
				String isOut=set.getString("isOut");
				String custTelMob= set.getString("custTelMob");
				String custTelHome= set.getString("custTelHome");
				String custTelWork= set.getString("custTelWork");
				String addtime=set.getString("addtime");
				if(addtime!=null&&addtime.length()>19){
					addtime=addtime.substring(0, 19);
				}
				if(custTelMob!=null&&!custTelMob.equals("")){
					dbd.set("custTelMob",custTelMob);
				}else if(custTelHome!=null&&!custTelHome.equals("")){
					dbd.set("custTelMob",custTelHome);
				}else if(custTelWork!=null&&!custTelWork.equals("")){
					dbd.set("custTelMob",custTelWork);
				}
				if(isOut!=null&&isOut.equals("out")){
					dbd.set("isOut", "ºô³ö");
				}else {
					dbd.set("isOut", "ºôÈë");
				}
				++num1;
				dbd.set("custName", set.getString("custName"));
				dbd.set("custAddr", set.getString("custAddr"));
				dbd.set("dictQuestionType1", set.getString("dictQuestionType1"));				
				dbd.set("addtime",addtime );
				list.add(dbd);
			}
			num=num1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List list1=new ArrayList();
		int k=pi.getPageSize()*(pageState-1);
		int maxSize=k+pi.getPageSize();
		if(maxSize>list.size()){
			maxSize=list.size();
		}
		for(int i=k;i<maxSize;i++){
			list1.add(list.get(i));	
		}
		return list1;
		
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
