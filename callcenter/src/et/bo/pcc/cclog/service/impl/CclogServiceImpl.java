package et.bo.pcc.cclog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.pcc.cclog.service.CclogService;
import et.bo.pcc.policeinfo.PoliceInfoService;
import et.bo.pcc.policeinfo.impl.InfoSearch;
import et.po.CcLog;
import et.po.PoliceCallin;
import et.po.PoliceCallinInfo;
import et.po.PoliceFuzzInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class CclogServiceImpl implements CclogService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private ClassTreeService cts;

	private PoliceInfoService info = null;

	private ClassTreeService depTree = null;

	private int num;

	public void addCclog(ConnectInfo ci) {
		// TODO Auto-generated method stub
		try {
			dao.saveEntity(createCclog(ci));
			ConnectInfo.getCurConn().remove(ci.getInPort());
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private CcLog createCclog(et.bo.callcenter.base.ConnectInfo ci) {
		CcLog cl = new CcLog();
		cl.setId(ci.getId());
		cl.setClientIp(ci.getClientIp());
		cl.setInPort(ci.getInPort());
		cl.setOperatorPort(ci.getOperatorPort());
		cl.setPhoneNum(ci.getPhoneNum());
		cl.setBeginTime(ci.getBeginTime() == null ? TimeUtil.getTimeByStr("")
				: ci.getBeginTime());
		cl.setEndTime(ci.getEndTime() == null ? TimeUtil.getTimeByStr("") : ci
				.getEndTime());
		cl.setOperateTime(ci.getOperateTime() == null ? TimeUtil
				.getTimeByStr("") : ci.getOperateTime());
		if (ci.getOperateTime() != null && ci.getEndTime() != null) {
			long seconds = (ci.getEndTime().getTime() - ci.getOperateTime()
					.getTime()) / 1000;
			cl.setBetweenTime(new Long(seconds).intValue());
		} else {
			cl.setBetweenTime(0);
		}
		if (ci.getRecFile() != null) {
			// StringBuffer sb = new StringBuffer();
			// sb.append("http://");
			// sb.append(cts.getvaluebyNickName(ConstantsI.CLIENT_IP));
			// System.out.println("工控机IP :
			// "+cts.getvaluebyNickName(ConstantsI.CLIENT_IP));
			// sb.append("/");
			// sb.append(ci.getRecFile());
			// cl.setRecFile(sb.toString());
			// System.out.println(sb.toString());
			// System.out.println("实际地址为: "+cl.getRecFile());
			// String path =
			// "http://"+cts.getvaluebyNickName(ConstantsI.CLIENT_IP)+"/"+ci.getRecFile();
			cl.setRecFile(ci.getRecFile());
			// System.out.println(cl.getRecFile());
		} else {
			cl.setRecFile("");
		}
		// cl.setRecFile(ci.getRecFile());
		if (ci.getCmd() != null) {
			cl.setCmd(ci.getCmd().toString());
		}
		cl.setRemark(ci.getRemark());
		return cl;
	}

	public void delCclog(IBaseDTO dto) {
		// TODO Auto-generated method stub
		CcLog cl = (CcLog) dao
				.loadEntity(CcLog.class, dto.get("id").toString());
		dao.removeEntity(cl);
	}

	public List queryCclog(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		CclogHelp ch = new CclogHelp();
		Object[] result = null;
		
		try {
			result = dao.findEntity(ch.cclogQuery(dto, pi));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("result length is "+result.length);
		num = dao.findEntitySize(ch.cclogQuery(dto, pi));
		// System.out.println("num length "+num);
		for (int i = 0, size = result.length; i < size; i++) {
			CcLog cl = (CcLog) result[i];
			list.add(CclogToDynaBeanDTO(cl));
		}
		return list;
	}

	private DynaBeanDTO CclogToDynaBeanDTO(CcLog cl) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		String id = cl.getId();
		PoliceCallin pci = (PoliceCallin) dao
				.loadEntity(PoliceCallin.class, id);
		dbd.set("id", id);
		if (pci != null) {
			String fuzznum = (String) pci.getFuzzNo();
			dbd.set("fuzznum", fuzznum);
			String operatornum = (String) pci.getOperator();
			dbd.set("operatornum", pci.getOperator());
		}
		dbd.set("phoneNum", cl.getPhoneNum());
		// dbd.set("inPort", cl.getInPort());
		dbd.set("beginTime", cl.getBeginTime());
		dbd.set("operateTime", cl.getOperateTime());
		dbd.set("endTime", cl.getEndTime());
		// dbd.set("recFile", cl.getRecFile());
		// String recFile = (String)cl.getRecFile();
		if (!(cl.getRecFile() == null)) {
			StringBuffer sb = new StringBuffer();
			sb.append("http://");
			sb.append(cts.getvaluebyNickName(ConstantsI.CLIENT_IP));
			// System.out.println("工控机IP :
			// "+cts.getvaluebyNickName(ConstantsI.CLIENT_IP));
			sb.append("/");
			sb.append(cl.getRecFile());
			// cl.setRecFile(sb.toString());
			// System.out.println(sb.toString());
			// System.out.println("实际地址为: "+cl.getRecFile());
			// String path =
			// "http://"+cts.getvaluebyNickName(ConstantsI.CLIENT_IP)+"/"+ci.getRecFile();
			dbd.set("recFile", sb.toString());
			// System.out.println(cl.getRecFile());
		} else {
			dbd.set("recFile", "");
		}
		return dbd;
	}

	public IBaseDTO getCclogInfo(String id) {
		CcLog cl = (CcLog) dao.loadEntity(CcLog.class, id);
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", cl.getId());
		dbd.set("clientIp", cl.getClientIp());
		dbd.set("inPort", cl.getInPort());
		dbd.set("operatorPort", cl.getOperatorPort());
		dbd.set("phoneNum", cl.getPhoneNum());
		dbd
				.set("beginTime", TimeUtil.getTheTimeStr(cl.getBeginTime(),
						"HH:mm"));
		dbd.set("operateTime", TimeUtil.getTheTimeStr(cl.getOperateTime(),
				"HH:mm"));
		dbd.set("endTime", TimeUtil.getTheTimeStr(cl.getEndTime(), "HH:mm"));
		dbd.set("recFile", cl.getRecFile());
		dbd.set("cmd", cl.getCmd());
		dbd.set("remark", cl.getRemark());
		return dbd;
	}

	public int getCclogSize() {
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

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public IBaseDTO listFuzzInfo(String id) {
		// TODO Auto-generated method stub
		PoliceCallin pci = (PoliceCallin) dao
				.loadEntity(PoliceCallin.class, id);
		String fuzznum = pci.getFuzzNo();
		IBaseDTO dto = new DynaBeanDTO();
		InfoSearch infoSearch = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(infoSearch
				.searchFuzzInfo(fuzznum));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceFuzzInfo pf = (PoliceFuzzInfo) result[i];
			dto.set("id", pf.getId());
			dto.set("fuzzNo", pf.getFuzzNo());
			dto.set("name", pf.getName());
			dto.set("sex", pf.getSex());
			dto.set("birthday", pf.getBirthday());
			dto.set("password", pf.getPassword());
			dto.set("repassword", pf.getPassword());
			dto.set("mobileTel", pf.getMobileTel());
			dto.set("tagPoliceKind", pf.getPersonType());
			dto.set("tagUnit", depTree.getvaluebyId(pf.getTagUnit()));
			dto.set("tagArea", pf.getTagArea());
			dto.set("duty", pf.getDuty());
			dto.set("tagFreeze", pf.getTagFreeze());
			dto.set("tagDel", pf.getTagDel());
		}
		return dto;
	}

	public List listPhoneInfo(String id) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		PoliceCallin pci = (PoliceCallin) dao
				.loadEntity(PoliceCallin.class, id);
		Iterator it = pci.getPoliceCallinInfos().iterator();
		while (it.hasNext()) {
			PoliceCallinInfo pcii = (PoliceCallinInfo) it.next();
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", pcii.getId());
			dbd.set("fuzzno", pcii.getPoliceCallin().getFuzzNo());
			dbd.set("quInfo", pcii.getQuInfo());
			String content = pcii.getContent();
			// if (content.length()>=35) {
			// content = content.substring(0,34)+"...";
			// }
			dbd.set("content", content);
			dbd.set("remark", pcii.getRemark());
			l.add(dbd);
		}
		return l;
	}

	public PoliceInfoService getInfo() {
		return info;
	}

	public void setInfo(PoliceInfoService info) {
		this.info = info;
	}

	public int getCcLogInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List queryCcLogInfo(IBaseDTO dto, PageInfo pi, String begintime,
			String opernum) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		CclogHelp ch = new CclogHelp();
		Object[] result = null;
		try {
			result = dao.findEntity(ch.queryToday(dto, pi, begintime, opernum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		num = dao.findEntitySize(ch.queryToday(dto, pi, begintime, opernum));
		for (int i = 0, size = result.length; i < size; i++) {
			CcLog cl = (CcLog) result[i];
			list.add(CclogToDynaBeanDTO(cl));
		}
//		Object[] re = null;
//		re = dao.findEntity(ch.queryTodaySize(dto, pi, begintime, opernum));
//		num = ((Integer) re[0]).intValue();
		
		return list;
	}

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public List getDepInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		List<LabelValueBean> depList = depTree.getLabelList("1");
		Iterator<LabelValueBean> it = depList.iterator();
		CclogHelp ch = new CclogHelp();
		while (it.hasNext()) {
			LabelValueBean lvb = it.next();
			String id = lvb.getValue();
			DynaBeanDTO mdto = new DynaBeanDTO();
			mdto.set("depname", depTree.getvaluebyId(id));
			int size = dao.findEntitySize(ch.queryDep(dto,id));
			mdto.set("count", size);
			l.add(mdto);
		}
		return l;
	}

}
