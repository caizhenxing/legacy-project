/**
 * 	@(#)PoliceValidServiceImpl.java   Oct 20, 2006 5:46:41 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policevalid.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.pcc.policevalid.PoliceValidService;
import et.bo.sys.user.action.Password_encrypt;
import et.po.PoliceFuzzInfo;
import et.po.PoliceUpdatePass;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 20, 2006
 * @see
 */
public class PoliceValidServiceImpl implements PoliceValidService {
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	private ClassTreeService depTree=null;
	
	private int POLICE_NUM = 0;

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
	
	private PoliceUpdatePass createPUPass(IBaseDTO dto){
		PoliceUpdatePass pup = new PoliceUpdatePass();
		String fuzzno = (String)dto.get("fuzzNo");
		SearchValidInfo sv = new SearchValidInfo();
		Object[] result = (Object[]) dao.findEntity(sv.getFuzz(dto));
		PoliceFuzzInfo pfi = (PoliceFuzzInfo)result[0];
		pup.setId(ks.getNext("police_update_pass"));
		pup.setFuzzNum(fuzzno);
		pup.setName(pfi.getName());
		pup.setIdCard(pfi.getIdCard());
		Password_encrypt pe = new Password_encrypt();
		pup.setNewpassword(pe.pw_encrypt((String)dto.get("password")));
		pup.setUptime(TimeUtil.getNowTime());
		pup.setDepartment((String)dto.get("department"));
		pup.setDepartment(pfi.getTagUnit());
		pup.setIp((String)dto.get("ip"));
		return pup;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policevalid.PoliceValidService#addPoliceValidInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addPoliceValidInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(createPUPass(dto));
		SearchValidInfo sv = new SearchValidInfo();
		Object[] result = (Object[]) dao.findEntity(sv.getFuzz(dto));
		PoliceFuzzInfo pfi = (PoliceFuzzInfo)result[0];
		Password_encrypt pe = new Password_encrypt();
		pfi.setPassword(pe.pw_encrypt(dto.get("password").toString()));
		dao.updateEntity(pfi);
		flag = true;
		return flag;
	}


	
	public boolean validPoliceInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SearchValidInfo sv = new SearchValidInfo();
		Object[] fuzzResult = (Object[])dao.findEntity(sv.searchFuzzPwdEmpty(dto));
		if(fuzzResult.length==0)
			return false;
		PoliceFuzzInfo pfi=(PoliceFuzzInfo)fuzzResult[0];
		String beforePwd=dto.get("beforePwd").toString();
		if(beforePwd.equals("")){
			if(pfi.getPassword().equals(""))
			return true;
		}
		else
		{
			Password_encrypt pe = new Password_encrypt();
			String pwd = pe.pw_encrypt(beforePwd);
			if(pwd.equals(pfi.getPassword()))
				return true;
		}
		
		String idcard = (String)dto.get("idcard");
		String indataidcard = pfi.getIdCard();
		if (indataidcard.length()==15) {
			if (idcard.equals(indataidcard)) {
				flag = true;
			}
		}
		if (indataidcard.length()==18) {
			//取到年前面的数据
			String first = indataidcard.substring(0,6);
			//去掉年后和最后一位的数据
			String middle = indataidcard.substring(8,17);
			//完成后数据为15位的完成串
			String finalString = first + middle;
			if (indataidcard.equals(idcard)||finalString.equals(idcard)) {
				flag = true;
			}
		}
		
		return flag;
	}

	public int getInfoSize() {
		// TODO Auto-generated method stub
		return POLICE_NUM;
	}

	public List infoIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		SearchValidInfo sv = new SearchValidInfo();
		Object[] result = (Object[]) dao.findEntity(sv.searchFuzzRegister(
				dto, pageInfo));
		int s = dao.findEntitySize(sv.searchFuzzRegister(dto, pageInfo));
		POLICE_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceUpdatePass pup = (PoliceUpdatePass) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", pup.getId());
			dbd.set("fuzzno", pup.getFuzzNum());
			dbd.set("name", pup.getName());
			dbd.set("idcard", pup.getIdCard());
			dbd.set("uptime", TimeUtil.getTheTimeStr(pup.getUptime()));
			dbd.set("department", depTree.getvaluebyId(pup.getDepartment()));
			//dbd.set("duty", depTree.getvaluebyId(policeFuzzInfo.getTagUnit()));
			l.add(dbd);
		}
		return l;
	}

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

}
