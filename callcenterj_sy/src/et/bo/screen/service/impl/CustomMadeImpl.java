package et.bo.screen.service.impl;
/**
 * 定制服务接口实现类
 * @author Chen Gang
 */
import java.util.ArrayList;
import java.util.List;

import et.bo.screen.service.CustomMadeService;
import et.po.OperCustomMade;
import et.po.OperMarketAnalysis;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class CustomMadeImpl implements CustomMadeService {
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;
	
	private KeyService ks = null;
	
	private int num = 0;

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

	/**
	 * 根据id取得一条定制服务信息详细
	 * @param id
	 * @return
	 */
	public IBaseDTO getCustomMadeInfo(String id) {
		OperCustomMade oma = (OperCustomMade)dao.loadEntity(OperCustomMade.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", oma.getId());
		dto.set("serviceType", oma.getServiceType());
		dto.set("columnName", oma.getColumnName());
		dto.set("columnDetail", oma.getColumnDetail());
		dto.set("madeMethod", oma.getCustomMadeMethod());
		dto.set("priceInfo", oma.getPriceInfo());
		dto.set("remark", oma.getRemark());		
		return dto;
	}
	
	/**
	 * 根据查询条件返回定制服务信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List customMadeInfoQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		MarAnalysisHelp mah = new MarAnalysisHelp();
		
		Object[] result = dao.findEntity(mah.customMadeQuery(dto, pi));
		num = dao.findEntitySize(mah.customMadeQuery(dto, pi));

		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				OperCustomMade oma = (OperCustomMade)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", oma.getId());
				dbd.set("serviceType", oma.getServiceType());
				dbd.set("columnName", oma.getColumnName());
				dbd.set("madeMethod", oma.getCustomMadeMethod());
				dbd.set("priceInfo", oma.getPriceInfo());
				list.add(dbd);
			}
		}
		return list;
	}
	
	public List customMadeInfoQuery2() {
		List list = new ArrayList();
		MarAnalysisHelp mah = new MarAnalysisHelp();
		
		Object[] result = dao.findEntity(mah.customMadeQuery2());

			for(int i=0,size=result.length; i<size; i++) {
				OperCustomMade oma = (OperCustomMade)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				
				dbd.set("id", oma.getId());
				dbd.set("serviceType", oma.getServiceType());
				dbd.set("columnName", oma.getColumnName());
				dbd.set("columnDetail", oma.getColumnDetail());
				dbd.set("madeMethod", oma.getCustomMadeMethod());
				dbd.set("priceInfo", oma.getPriceInfo());
				
				list.add(dbd);
			}

		return list;
	}
	
	public int getCustomMadeInfoSize() {
		return num;
	}
	
	/**
	 * 添加定制服务信息
	 * @param dto
	 * @return
	 */
	public boolean addCustomMade(IBaseDTO dto) {
		try {
			dao.saveEntity(createCustomMade(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperCustomMade createCustomMade(IBaseDTO dto) {
		OperCustomMade oma = new OperCustomMade();
		
		oma.setId(ks.getNext("OPER_CUSTOM_MADE"));
		oma.setServiceType(dto.get("serviceType").toString());
		oma.setColumnName(dto.get("columnName").toString());
		oma.setColumnDetail(dto.get("columnDetail").toString());
		oma.setCustomMadeMethod(dto.get("madeMethod").toString());
		oma.setPriceInfo(dto.get("priceInfo").toString());
		oma.setRemark(dto.get("remark").toString());
		oma.setRecordPerson(""); 
		oma.setRecordTime(TimeUtil.getNowTime());
		
		return oma;
	}
	
	/**
	 * 修改定制服务信息
	 * @param dto
	 * @return
	 */
	public boolean updateCustomMade(IBaseDTO dto) {
		try {
			dao.updateEntity(modifyCustomMade(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperCustomMade modifyCustomMade(IBaseDTO dto) {
		OperCustomMade oma = 
			(OperCustomMade)dao.loadEntity(OperCustomMade.class, dto.get("id").toString());
		
		oma.setServiceType(dto.get("serviceType").toString());
		oma.setColumnName(dto.get("columnName").toString());
		oma.setColumnDetail(dto.get("columnDetail").toString());
		oma.setCustomMadeMethod(dto.get("madeMethod").toString());
		oma.setPriceInfo(dto.get("priceInfo").toString());
		oma.setRemark(dto.get("remark").toString());
		
		return oma;
	}
	
	/**
	 * 删除定制服务信息
	 * @param dto
	 * @return
	 */
	public boolean deleteCustomMade(IBaseDTO dto) {
		OperCustomMade oma = 
			(OperCustomMade)dao.loadEntity(OperCustomMade.class, dto.get("id").toString());
		if(oma != null) {
			try {
				dao.removeEntity(oma);
				return true;
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return false;
	}
}
