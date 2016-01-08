package et.bo.pcc.callinFirewall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import et.bo.pcc.callinFirewall.service.CallinFirewallService;
import et.po.PoliceCallinFirewall;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class CallinFirewallServiceImpl implements CallinFirewallService {

	static Logger log = Logger.getLogger(CallinFirewallServiceImpl.class.getName());
	
	private BaseDAO dao = null;

	private KeyService ks = null;

	private int num;

	public boolean IfInBlacklist(String phoneNum) {
		// TODO Auto-generated method stub
		CallinFirewallHelp cfh = new CallinFirewallHelp();
		int i=dao.findEntitySize(cfh.IfInBlacklist(phoneNum));
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

	private PoliceCallinFirewall createRule(IBaseDTO dto) {
		PoliceCallinFirewall pf = new PoliceCallinFirewall();
		pf.setId(ks.getNext("PoliceCallinFirewall"));
		pf.setCallinNumBegin(dto.get("callinNumBegin").toString());
		pf.setCallinNumEnd(dto.get("callinNumEnd").toString());
		if (dto.get("beginTime").toString().equals("")) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
		pf.setBeginTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("beginTime")
				.toString(), "HH:mm"));
		pf.setEndTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("endTime")
				.toString(), "HH:mm"));
		pf.setIsPass(dto.get("isPass").toString());
		pf.setIsAvailable(dto.get("isAvailable").toString());
		pf.setRemark(dto.get("remark").toString());
		return pf;
	}

	public void updateRule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(modifyRule(dto));
	}

	private PoliceCallinFirewall modifyRule(IBaseDTO dto) {
		PoliceCallinFirewall pf =(PoliceCallinFirewall)dao.loadEntity(PoliceCallinFirewall.class, dto.get("id").toString());
//		pf.setId(dto.get("id").toString());
		pf.setCallinNumBegin(dto.get("callinNumBegin").toString());
		pf.setCallinNumEnd(dto.get("callinNumEnd").toString());
		pf.setBeginTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("beginTime")
				.toString(), "HH:mm"));
		pf.setEndTime(dto.get("beginTime").toString().equals("") ? TimeUtil
				.getTimeByStr("") : TimeUtil.getTimeByStr(dto.get("endTime")
				.toString(), "HH:mm"));
		pf.setIsPass(dto.get("isPass").toString());
		pf.setIsAvailable(dto.get("isAvailable").toString());
		pf.setRemark(dto.get("remark").toString());
		return pf;
	}

	public void delRule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PoliceCallinFirewall pf = (PoliceCallinFirewall) dao.loadEntity(
				PoliceCallinFirewall.class, dto.get("id").toString());
		dao.removeEntity(pf);
	}

	public IBaseDTO getRuleInfo(String id) {
		PoliceCallinFirewall pf = (PoliceCallinFirewall) dao.loadEntity(
				PoliceCallinFirewall.class, id);
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId());
		dbd.set("callinNumBegin", pf.getCallinNumBegin());
		dbd.set("callinNumEnd", pf.getCallinNumEnd());
		dbd.set("beginTime", TimeUtil.getTheTimeStr(pf.getBeginTime(),"HH:mm"));
		dbd.set("endTime", TimeUtil.getTheTimeStr(pf.getEndTime(), "HH:mm"));
		dbd.set("isPass", pf.getIsPass());
		dbd.set("isAvailable", pf.getIsAvailable());
		return dbd;
	}

	public List ruleQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		CallinFirewallHelp cfh = new CallinFirewallHelp();
		Object[] result = (Object[]) dao.findEntity(cfh.callinFirewallQuery(
				dto, pi));
		num = dao.findEntitySize(cfh.callinFirewallQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceCallinFirewall pf = (PoliceCallinFirewall) result[i];
			list.add(PoliceCallinFirewallToDynaBeanDTO(pf));
		}
		return list;
	}

	private DynaBeanDTO PoliceCallinFirewallToDynaBeanDTO(
			PoliceCallinFirewall pf) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId());
		dbd.set("callinNumBegin", pf.getCallinNumBegin());
		dbd.set("callinNumEnd", pf.getCallinNumEnd());
		dbd.set("beginTime", pf.getBeginTime());
		dbd.set("endTime", pf.getEndTime());
		dbd.set("isPass", pf.getIsPass());
		dbd.set("isAvailable", pf.getIsAvailable());
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
