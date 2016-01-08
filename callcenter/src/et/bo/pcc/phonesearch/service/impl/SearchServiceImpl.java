/**
 * 	@(#)SearchServiceImpl.java   Oct 30, 2006 5:58:46 PM
 *	 。 
 *	 
 *	此类中和数据库对应的数据顺序反了，改动是应该注意
 */
package et.bo.pcc.phonesearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.pcc.phonesearch.service.SearchService;
import et.po.PhoneSearch;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 30, 2006
 * @see
 */
public class SearchServiceImpl implements SearchService {
	
	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private int SEARCH_NUM = 0;
	
	//添加
	private PhoneSearch createFuzzInfo(IBaseDTO dto) {
		PhoneSearch ps = new PhoneSearch();
		ps.setId(ks.getNext("phone_search"));
		ps.setNum((String)dto.get("num"));
		ps.setDeprtment((String)dto.get("deprtment"));
		ps.setUnit((String)dto.get("unit"));
		ps.setRemark((String)dto.get("remark"));
		return ps;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phonesearch.service.SearchService#addSearchInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public void addSearchInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createFuzzInfo(dto));
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phonesearch.service.SearchService#delSearchInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public void delSearchInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PhoneSearch ps = (PhoneSearch) dao.loadEntity(PhoneSearch.class, dto.get("id").toString());
		dao.removeEntity(ps);
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phonesearch.service.SearchService#getPhoneSearch(java.lang.String)
	 */
	public IBaseDTO getPhoneSearch(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		PhoneSearch ps = (PhoneSearch) dao.loadEntity(PhoneSearch.class, id);
		dto.set("id", ps.getId());
		dto.set("num", ps.getNum());
		dto.set("deprtment", ps.getUnit());
		dto.set("unit", ps.getDeprtment());
		dto.set("remark", ps.getRemark());
		return dto;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phonesearch.service.SearchService#getPhoneSearchSize()
	 */
	public int getPhoneSearchSize() {
		// TODO Auto-generated method stub
		return SEARCH_NUM;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phonesearch.service.SearchService#phoneSearch(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List phoneSearch(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		SearchSearch spList = new SearchSearch();
		Object[] result = (Object[]) dao.findEntity(spList.searchPhoneList(dto, pi));
		int s = dao.findEntitySize(spList.searchPhoneList(dto, pi));
		SEARCH_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			PhoneSearch ps = (PhoneSearch) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ps.getId());
			dbd.set("num", ps.getNum());
			dbd.set("deprtment", ps.getDeprtment());
			dbd.set("unit", ps.getUnit());
			dbd.set("remark", ps.getRemark());
			l.add(dbd);
		}
		return l;
	}
	
	//修改
	private PhoneSearch upFuzzInfo(IBaseDTO dto) {
		PhoneSearch ps = new PhoneSearch();
		ps.setId((String)dto.get("id"));
		ps.setNum((String)dto.get("num"));
		ps.setDeprtment((String)dto.get("deprtment"));
		ps.setUnit((String)dto.get("unit"));
		ps.setRemark((String)dto.get("remark"));
		return ps;
	}

	/* (non-Javadoc)
	 * @see et.bo.pcc.phonesearch.service.SearchService#upSearchInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public void upSearchInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.updateEntity(upFuzzInfo(dto));
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
