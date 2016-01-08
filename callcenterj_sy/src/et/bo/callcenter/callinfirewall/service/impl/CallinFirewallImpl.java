package et.bo.callcenter.callinfirewall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import et.bo.callcenter.callinfirewall.service.CallinFirewallService;
import et.po.CcFirewall;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class CallinFirewallImpl implements CallinFirewallService {

	static Logger log = Logger.getLogger(CallinFirewallImpl.class.getName());
	
	private BaseDAO dao = null;

	private KeyService ks = null;

	private int num;

	public boolean IfInBlacklist(String phoneNum) {
		// TODO Auto-generated method stub
		CallinFirewallHelp cfh = new CallinFirewallHelp();
		int i = dao.findEntitySize(cfh.IfInBlacklist(phoneNum));
		if(i!=0){
			return true;
		}else{
			return false;
		}
		
	}

	public void addRule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createRule(dto));
	}

	private CcFirewall createRule(IBaseDTO dto) {
		CcFirewall pf = new CcFirewall();
		pf.setId(ks.getNext("PoliceCallinFirewall"));
		pf.setPhoneNum(dto.get("callinNum").toString());
		if (dto.get("beginTime").toString().equals("")) {
			//System.out.println("true");
		} else {
			//System.out.println("false");
		}
		pf.setBeginTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("beginTime")
				.toString(), "yyyy-MM-dd"));
		pf.setEndTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("endTime")
				.toString(), "yyyy-MM-dd"));
		pf.setIsPass(dto.get("isPass").toString());
//		pf.setIsAvailable(dto.get("isAvailable").toString());
		pf.setRemark(dto.get("remark").toString());
		return pf;
	}

	public void updateRule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(modifyRule(dto));
	}

	private CcFirewall modifyRule(IBaseDTO dto) {
		CcFirewall pf =(CcFirewall)dao.loadEntity(CcFirewall.class, dto.get("id").toString());
//		pf.setId();
		pf.setPhoneNum(dto.get("callinNum").toString());
//		pf.setCallinNumEnd(dto.get("callinNumEnd").toString());
		pf.setBeginTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("beginTime")
				.toString(), "yyyy-MM-dd"));
		pf.setEndTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("endTime")
				.toString(), "yyyy-MM-dd"));
		pf.setIsPass(dto.get("isPass").toString());
//		pf.setIsAvailable(dto.get("isAvailable").toString());
		pf.setRemark(dto.get("remark").toString());
		return pf;
	}

	public void delRule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		CcFirewall pf =(CcFirewall)dao.loadEntity(CcFirewall.class, dto.get("id").toString());
		dao.removeEntity(pf);
	}

	public IBaseDTO getRuleInfo(String id) {
		CcFirewall pf =(CcFirewall)dao.loadEntity(CcFirewall.class, id);
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId());
		dbd.set("callinNum", pf.getPhoneNum());
		dbd.set("beginTime", TimeUtil.getTheTimeStr(pf.getBeginTime(),"yyyy-MM-dd"));
		dbd.set("endTime", TimeUtil.getTheTimeStr(pf.getEndTime(), "yyyy-MM-dd"));
		
		if("0".equals(pf.getIsPass()))
			dbd.set("isPass", "通过");
		else if("1".equals(pf.getIsPass()))
			dbd.set("isPass", "未通过");
		return dbd;
	}

	public List ruleQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		CallinFirewallHelp cfh = new CallinFirewallHelp();
		Object[] result = (Object[]) dao.findEntity(cfh.callinFirewallQuery(
				dto, pi));
		num = dao.findEntitySize(cfh.callinFirewallQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			CcFirewall pf = (CcFirewall) result[i];
			list.add(PoliceCallinFirewallToDynaBeanDTO(pf));
		}
		return list;
	}

	private DynaBeanDTO PoliceCallinFirewallToDynaBeanDTO(
			CcFirewall pf) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId());
		dbd.set("callinNum", pf.getPhoneNum());
		dbd.set("beginTime", pf.getBeginTime());
		dbd.set("endTime", pf.getEndTime());
		dbd.set("isPass", pf.getIsPass());
		dbd.set("remark", pf.getRemark());
		return dbd;
	}
	
	public boolean ifHaveSameNum(String phoneNum){
		CallinFirewallHelp cfh = new CallinFirewallHelp();
		int i = dao.findEntitySize(cfh.ifHaveSameNum(phoneNum));
		if(i==0){
			return false;
		}else{
			return true;
		}
	}

	public int getRuleSize() {
		return num;
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
	
}
