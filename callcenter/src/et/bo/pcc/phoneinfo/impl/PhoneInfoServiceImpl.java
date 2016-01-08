/**
 * 	@(#)PhoneInfoServiceImpl.java   Nov 7, 2006 9:53:51 AM
 *	 ¡£ 
 *	 
 */
package et.bo.pcc.phoneinfo.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.pcc.phoneinfo.PhoneInfoService;
import et.bo.pcc.policeinfo.impl.InfoSearch;
import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import et.po.CcLog;
import et.po.PoliceCallin;
import et.po.PoliceCallinInfo;
import et.po.PoliceFuzzInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Nov 7, 2006
 * @see
 */
public class PhoneInfoServiceImpl implements PhoneInfoService {
	
	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private ClassTreeService depTree = null;
	
	private ClassTreeService ctree = null;

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
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

	/* (non-Javadoc)
	 * @see et.bo.pcc.phoneinfo.PhoneInfoService#addPoliceCallInInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addPoliceCallInInfo(PoliceCallInInfoInMemory pm) {
		boolean flag = false;

		PoliceCallin pc = (PoliceCallin)dao.loadEntity(PoliceCallin.class, pm.getPcid());
		PoliceCallinInfo pcii = new PoliceCallinInfo();
		pcii.setId(ks.getNext("police_callin_info"));
		pcii.setPoliceCallin(pc);
		pcii.setTagInfo(pm.getTaginfo());
		pcii.setQuInfo(pm.getQuinfo());
		pcii.setContent(pm.getContent());
		pcii.setRemark(pm.getRemark());
		
		dao.saveEntity(pcii);
		
		return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phoneinfo.PhoneInfoService#callInInfoIndex(java.lang.String)
	 */
	public List callInInfoIndex(String pocid,String fuzznum) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		PhoneSearch ps = new PhoneSearch();
		PoliceCallin pc = (PoliceCallin)dao.loadEntity(PoliceCallin.class, pocid);
		Object[] result = (Object[]) dao.findEntity(ps.searchInfoSize(pc));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceCallinInfo pcii = (PoliceCallinInfo)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("id", pcii.getId());
			String taginfo = ctree.getvaluebyId(pcii.getTagInfo());
			dbd.set("taginfo", taginfo);
			dbd.set("quinfo", pcii.getQuInfo());
			dbd.set("content", pcii.getContent());
			dbd.set("remark", pcii.getRemark());
			l.add(dbd);
		}
		return l;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phoneinfo.PhoneInfoService#checkPoliceNum(java.lang.String, java.lang.String)
	 */
	public boolean checkPoliceNum(String policeNum, String password) {
		// TODO Auto-generated method stub
		boolean flag = false;
		InfoSearch infoSearch = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(infoSearch.searchFuzzInfoByPoliceId(policeNum,password));
		for (int i = 0, size = result.length; i < size; i++) {
			flag = true;
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phoneinfo.PhoneInfoService#getPoliceInfo(java.lang.String)
	 */
	public IBaseDTO getPoliceInfo(String policeNum) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		InfoSearch infoSearch = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(infoSearch.searchFuzzInfo(policeNum));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceFuzzInfo pf = (PoliceFuzzInfo)result[i];
			dto.set("id", pf.getId());
			dto.set("fuzzNo", pf.getFuzzNo());
			dto.set("name", pf.getName());
			dto.set("sex", pf.getSex());
			dto.set("birthday", pf.getBirthday());
			dto.set("password", pf.getPassword());
			dto.set("repassword", pf.getPassword());
			dto.set("mobileTel", pf.getMobileTel());
			dto.set("tagPoliceKind",pf.getPersonType());
			dto.set("tagUnit", depTree.getvaluebyId(pf.getTagUnit()));
			dto.set("tagArea", pf.getTagArea());
			dto.set("duty", pf.getDuty());
			dto.set("tagFreeze", pf.getTagFreeze());
			dto.set("tagDel", pf.getTagDel());
		}
		return dto;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phoneinfo.PhoneInfoService#getQuestionInfo(java.lang.String)
	 */
	public IBaseDTO getQuestionInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		PoliceCallinInfo pt = (PoliceCallinInfo)dao.loadEntity(PoliceCallinInfo.class, id);
		dto.set("taginfo", pt.getTagInfo());
		dto.set("quinfo", pt.getQuInfo());
		dto.set("content", pt.getContent());
		dto.set("remark", pt.getRemark());
		return dto;
	}
	
	public String addPoliceCallin(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String pid = "";
		PoliceCallin pci = new PoliceCallin();
		//pid = ks.getNext("police_callin");
		pid = (String)dto.get("pid");
		pci.setId(pid);
		String fuzznum = (String)dto.get("fuzzNo");
		pci.setFuzzNo(fuzznum);
		pci.setOperator((String)dto.get("operator"));
		pci.setIsValidIn((String)dto.get("isvalidin"));
		pci.setPassValidNum((String)dto.get("passvalidnum"));
		PhoneSearch ps = new PhoneSearch();
		String department = "";
		if (fuzznum==null) {
			
		}else{
		Object[] result = dao.findEntity(ps.searchDepartment(fuzznum));
		
		for(int i=0,size=result.length;i<size;i++){
			PoliceFuzzInfo pf = (PoliceFuzzInfo)result[i];
			department = pf.getTagUnit();
		}}
		pci.setDepartment(department);
		dao.saveEntity(pci);
		return pid;
	}

	public boolean addPoliceCallInInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	public String saveCclog(String phonenum) {
		// TODO Auto-generated method stub
		String pid = ks.getNext("cc_log");
		CcLog cc = new CcLog();
		cc.setId(pid);
		cc.setPhoneNum(phonenum);
		cc.setBeginTime(TimeUtil.getNowTime());
		cc.setOperateTime(TimeUtil.getNowTime());
		dao.saveEntity(cc);
		return pid;
	}

	public void upCclog(String id) {
		// TODO Auto-generated method stub
		CcLog cc = (CcLog)dao.loadEntity(CcLog.class, id);
		cc.setEndTime(TimeUtil.getNowTime());
		dao.updateEntity(cc);
	}

	public void saveCclogEnd(String phonenum) {
		// TODO Auto-generated method stub
		String pid = ks.getNext("cc_log");
		CcLog cc = new CcLog();
		cc.setId(pid);
		cc.setPhoneNum(phonenum);
		cc.setBeginTime(TimeUtil.getNowTime());
		cc.setOperateTime(TimeUtil.getNowTime());
		cc.setEndTime(TimeUtil.getNowTime());
		dao.saveEntity(cc);
	}

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

}
