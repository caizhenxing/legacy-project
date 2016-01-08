/**
 * 	@(#)PoliceCallInServiceImpl.java   Oct 11, 2006 3:55:30 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policeinfo.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import et.bo.pcc.policeinfo.PoliceCallInService;
import et.po.PoliceCallin;
import et.po.PoliceCallinInfo;
import et.po.PoliceFuzzInfo;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;

/**
 * 记入公安呼入信息
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public class PoliceCallInServiceImpl implements PoliceCallInService {
	
	private BaseDAO dao = null;

	private KeyService ks = null;

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
	 * @see et.bo.pcc.policeinfo.PoliceCallInService#addPoliceCallInInfo(excellence.framework.base.dto.IBaseDTO, java.util.Set)
	 */
	public boolean addPoliceCallInInfo(et.bo.callcenter.business.impl.PoliceCallin poc) {
		// TODO Auto-generated method stub
		//boolean flag = false;
		PoliceCallin policeCallin = new PoliceCallin();
		policeCallin.setId(poc.getId());
		String fuzznum = poc.getFuzzNum();
		policeCallin.setFuzzNo(fuzznum);
		policeCallin.setPassValidNum(""+poc.getNum());
		policeCallin.setIsValidIn(poc.getValidInfo());
		policeCallin.setOperator(poc.getOperatorNum());
		InfoSearch info = new InfoSearch();
		System.out.println("fuzzno ======= "+fuzznum);
		String department = "";
		if (fuzznum==null) {
			
		}else{
		Object[] result = dao.findEntity(info.searchDepartment(fuzznum));
		for(int i=0,size=result.length;i<size;i++){
			PoliceFuzzInfo pf = (PoliceFuzzInfo)result[i];
			department = pf.getTagUnit();
		}}
		policeCallin.setDepartment(department);
		List l = poc.getCallinInfo();
		Iterator it = l.iterator();
		Set set = new HashSet();
		while (it.hasNext()) {
			PoliceCallinInfo pcii = (PoliceCallinInfo)it.next();
			pcii.setId(pcii.getId());
			pcii.setPoliceCallin(policeCallin);
			pcii.setTagInfo(pcii.getTagInfo());
			pcii.setQuInfo(pcii.getQuInfo());
			pcii.setContent(pcii.getContent());
			pcii.setRemark(pcii.getRemark());
			set.add(pcii);
		}
		//policeCallin.setPoliceCallinInfos(set);
		//policeCallin.setId((String) policeCallInDTO.get("id"));
		//policeCallin.setFuzzNo((String) policeCallInDTO.get("fuzzNo"));
		//policeCallin.setPassValidNum(new Integer((String)policeCallInDTO.get("passvalidnum")));
		//policeCallin.setIsValidIn(policeCallInDTO.get("isvalidin")==null?"":(String)policeCallInDTO.get("isvalidin"));
		//policeCallin.setOperator((String) policeCallInDTO.get("operator"));
		//policeCallin.setPoliceCallinInfos(policeCallInInfo);
		System.out.println("fuzzno"+fuzznum);
		System.out.println("department"+department);
		dao.saveEntity(policeCallin);
		//dao.flush();
		return true;
	}

}
