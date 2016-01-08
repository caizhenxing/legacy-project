/**
 * 
 */
package et.bo.custinfo.service.impl;

import java.util.List;

import et.bo.custinfo.service.PhoneService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public class PhoneImpl implements PhoneService {
	
	private BaseDAO dao = null;
	
	public KeyService ks = null;

	private ClassTreeService cts = null;

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
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
	 * @see et.bo.custinfo.service.PhoneService#phoneQuery(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List phoneQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		return null;
	}

}
