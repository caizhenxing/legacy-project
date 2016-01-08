/**
 * 	@(#)PhoneServiceImpl.java   Oct 12, 2006 11:11:43 AM
 *	 ¡£ 
 *	 
 */
package et.bo.pcc.assay.phone.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.pcc.assay.phone.service.PhoneService;
import et.po.CcLog;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 12, 2006
 * @see
 */
public class PhoneServiceImpl implements PhoneService {
	
    private BaseDAO dao=null;
    
    private KeyService ks = null;
    
    private int PHONE_NUM = 0;

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
	 * @see et.bo.pcc.assay.phone.service.PhoneService#getPhoneInfo(java.lang.String)
	 */
	public IBaseDTO getPhoneInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		CcLog ccl = (CcLog)dao.loadEntity(CcLog.class, id);
		dto.set("phonenum", ccl.getPhoneNum());
		dto.set("operatetime", TimeUtil.getTheTimeStr(ccl.getOperateTime(),"yyyy-MM-dd HH:mm:ss"));
		dto.set("endtime", TimeUtil.getTheTimeStr(ccl.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
		return dto;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.assay.phone.service.PhoneService#getPhoneSize()
	 */
	public int getPhoneSize() {
		// TODO Auto-generated method stub
		return PHONE_NUM;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.assay.phone.service.PhoneService#phoneIndex(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List phoneIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		PhoneSearch pSearch = new PhoneSearch();
		Object[] result = null;
		try{
		result = (Object[]) dao.findEntity(pSearch.searchPhoneByTime(dto,pageInfo));
		}catch(Exception e){
			e.printStackTrace();
		}
        int s = dao.findEntitySize(pSearch.searchPhoneByTime(dto,pageInfo));
        PHONE_NUM = s;
        for (int i = 0, size = result.length; i < size; i++) {
        	CcLog cc = (CcLog)result[i];
        	DynaBeanDTO dbd = new DynaBeanDTO();
        	dbd.set("id", cc.getId());
        	dbd.set("phone", cc.getPhoneNum());
        	dbd.set("begintime", TimeUtil.getTheTimeStr(cc.getBeginTime(),"yyyy-MM-dd HH:mm:ss"));
        	dbd.set("operatetime", TimeUtil.getTheTimeStr(cc.getOperateTime(),"yyyy-MM-dd HH:mm:ss"));
        	dbd.set("endtime", TimeUtil.getTheTimeStr(cc.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
        	l.add(dbd);
        }
//        Object[] re=null;
//		re = dao.findEntity(pSearch.searchPhoneByTimeSize(dto,pageInfo));
//		PHONE_NUM=((Integer)re[0]).intValue();

		return l;
	}

}
