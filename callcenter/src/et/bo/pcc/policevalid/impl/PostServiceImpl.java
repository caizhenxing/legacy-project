/**
 * 
 */
package et.bo.pcc.policevalid.impl;

import et.bo.pcc.policevalid.PostService;
import et.bo.sys.user.action.Password_encrypt;
import et.po.PoliceFuzzInfo;
import et.po.PoliceUpdatePass;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public class PostServiceImpl implements PostService {

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
	
	private PoliceUpdatePass createPUPass(IBaseDTO dto){
		PoliceUpdatePass pup = new PoliceUpdatePass();
		String fuzzno = (String)dto.get("fuzzNo");
		SearchPostInfo sp = new SearchPostInfo();
		Object[] result = (Object[]) dao.findEntity(sp.getFuzz(dto));
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
		SearchPostInfo sp = new SearchPostInfo();
		Object[] result = (Object[]) dao.findEntity(sp.getFuzz(dto));
		PoliceFuzzInfo pfi = (PoliceFuzzInfo)result[0];
		Password_encrypt pe = new Password_encrypt();
		pfi.setPassword(pe.pw_encrypt(dto.get("password").toString()));
		dao.updateEntity(pfi);
		flag = true;
		return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.policevalid.PoliceValidService#validPoliceInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public String validPoliceInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String result = "2";
		SearchPostInfo sp = new SearchPostInfo();
		Object[] fuzzResult = (Object[])dao.findEntity(sp.searchFuzz(dto));
		for (int i = 0; i < fuzzResult.length; i++) {
			PoliceFuzzInfo pfi = (PoliceFuzzInfo)fuzzResult[0];
			if (pfi.getPassword()!=null) {
				result = "1";
				return result;
			}
			String idcard = (String)dto.get("idcard");
			String indataidcard = pfi.getIdCard();
			//System.out.println("iddatacard "+indataidcard);
			if (indataidcard.length()==15) {
				if (idcard.equals(indataidcard)) {
					result = "0";
					return result;
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
					result="0";
					return result;
				}
			}
			//result = "0";
		}
		return result;
	}

}
