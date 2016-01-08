/**
 * 	@(#)PoliceInfoServiceImpl.java   Oct 10, 2006 9:52:05 AM
 *	 ¡£ 
 *	 
 */
package et.bo.pcc.policeinfo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import et.bo.pcc.policeinfo.PoliceInfoService;
import et.po.PoliceCallin;
import et.po.PoliceCallinInfo;
import et.po.PoliceFuzzInfo;
import et.po.PoliceinfoTemp;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 10, 2006
 * @see
 */
public class PoliceInfoServiceImpl implements PoliceInfoService {
	
	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private ClassTreeService ctree = null;
	
	private ClassTreeService depTree = null;

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#addBatchPoliceCallInInfo(java.util.List)
	 */
	public boolean addBatchPoliceCallInInfo(List policeCallInInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#addPoliceCallInInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addPoliceCallInInfo(PoliceCallInInfoInMemory pm) {
		// TODO Auto-generated method stub
		boolean flag = false;
		//List callInfo = new ArrayList();
		//PoliceCallinInfo pcii = new PoliceCallinInfo();
		//pcii.setId(ks.getNext("police_callin_info"));
		//String taginfo = ctree.getvaluebyId(pm.getTaginfo());
		//pcii.setTagInfo(taginfo);
		//pcii.setQuInfo(pm.getQuinfo());
		//pcii.setContent(pm.getContent());
		//pcii.setRemark(pm.getRemark());
		
		//et.bo.callcenter.business.impl.PoliceCallin pci = 
			//(et.bo.callcenter.business.impl.PoliceCallin)et.bo.callcenter.business.impl.PoliceCallin.
			//getCallinIdMap().get(pm.getPcid());
		//pci.getCallinInfo().add(pcii);

		PoliceinfoTemp pt = new PoliceinfoTemp();
		pt.setId(ks.getNext("policeinfo_temp"));
		pt.setPId(pm.getPcid());
		pt.setTagInfo(pm.getTaginfo());
		pt.setQuInfo(pm.getQuinfo());
		pt.setContent(pm.getContent());
		pt.setRemark(pm.getRemark());
		pt.setTag("N");
		
		dao.saveEntity(pt);
		
		return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#callInInfoIndex(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List callInInfoIndex(String pocid) {
		List l = new ArrayList();
		InfoSearch is = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(is.searchInfoSize(pocid));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceinfoTemp pcii = (PoliceinfoTemp)result[i];
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
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#checkMorePolice(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean checkMorePolice(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#checkPoliceNum(java.lang.String)
	 */
	public boolean checkPoliceNum(String policeNum,String password) {
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
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#getCallInInfoSize()
	 */
	public int getCallInInfoSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policeinfo.PoliceInfoService#getPoliceInfo(java.lang.String)
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

	public String getPoliceIdByOpNum(String operatornum) {
		// TODO Auto-generated method stub
		String id = "";
		Set set = et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			et.bo.callcenter.business.impl.PoliceCallin pc = (et.bo.callcenter.business.impl.PoliceCallin)it.next();
			if (pc.getOperatorNum().equals(operatornum)) {
				id = pc.getId();
			}
		}
		return id;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public boolean finishOper(String pocid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		PoliceCallin pci = (PoliceCallin)dao.loadEntity(PoliceCallin.class, pocid);
		if (pci==null) {
			return flag;
		}
		InfoSearch is = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(is.searchInfoSize(pocid));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceinfoTemp pt = (PoliceinfoTemp)result[i];
			PoliceCallinInfo pcii = new PoliceCallinInfo();
			pcii.setId(pt.getId());
			pcii.setPoliceCallin(pci);
			pcii.setTagInfo(pt.getTagInfo());
			pcii.setQuInfo(pt.getQuInfo());
			pcii.setContent(pt.getContent());
			pcii.setRemark(pt.getRemark());
			dao.saveEntity(pcii);
			dao.removeEntity(pt);
		}
		flag = true;
		return flag;
	}

	public void upTable(String pocid) {
		// TODO Auto-generated method stub
		InfoSearch is = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(is.searchInfoSize(pocid));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceinfoTemp pt = (PoliceinfoTemp)result[i];
			pt.setTag("Y");
			dao.updateEntity(pt);
		}
	}

	public boolean insertValue(String pocid) {
		// TODO Auto-generated method stub
		InfoSearch is = new InfoSearch();
		et.po.PoliceCallin pci = (et.po.PoliceCallin)dao.loadEntity(et.po.PoliceCallin.class, pocid);
		if (pci==null) {
			return false;
		}
		Object[] result = (Object[]) dao.findEntity(is.searchInfoByY(pocid));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceinfoTemp pt = (PoliceinfoTemp)result[i];
			PoliceCallinInfo pcii = new PoliceCallinInfo();
			pcii.setId(pt.getId());
			pcii.setPoliceCallin(pci);
			pcii.setTagInfo(pt.getTagInfo());
			pcii.setQuInfo(pt.getQuInfo());
			pcii.setContent(pt.getContent());
			pcii.setRemark(pt.getRemark());
			dao.saveEntity(pcii);
			dao.removeEntity(pt);
		}
		return true;
	}

	public IBaseDTO getQuestionInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		PoliceinfoTemp pt = (PoliceinfoTemp)dao.loadEntity(PoliceinfoTemp.class, id);
		dto.set("taginfo", pt.getTagInfo());
		dto.set("quinfo", pt.getQuInfo());
		dto.set("content", pt.getContent());
		dto.set("remark", pt.getRemark());
		return dto;
	}

	public void transactBefore() {
		// TODO Auto-generated method stub
		InfoSearch is = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(is.searchPoliceCallinInfo());
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceCallin pc = (PoliceCallin)result[i];
			String pfuzzNum = pc.getFuzzNo()==null?"":pc.getFuzzNo();
			System.out.println("pfuzzNum+++==== "+pfuzzNum);
			if (!pfuzzNum.equals("")) {
				System.out.println("Ö´ÐÐ"+pfuzzNum);
				String department = "";
				Object[] resultdep = (Object[]) dao.findEntity(is.searchFuzzInfo(pfuzzNum));
				for (int j = 0, jsize = resultdep.length; j < jsize; j++) {
					PoliceFuzzInfo pfdep = (PoliceFuzzInfo)resultdep[j];
					department = pfdep.getTagUnit();
				}
				pc.setDepartment(department);
				dao.updateEntity(pc);
			}
		}
	}

}
