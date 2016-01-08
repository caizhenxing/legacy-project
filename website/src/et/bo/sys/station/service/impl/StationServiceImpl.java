/**
 * 	@(#)StationServiceImpl.java   Sep 2, 2006 3:04:41 PM
 *	 ¡£ 
 *	 
 */
package et.bo.sys.station.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.station.service.StationService;
import et.po.SysStationInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Sep 2, 2006
 * @see
 */
public class StationServiceImpl implements StationService {
	
    private BaseDAO dao=null;
    private ClassTreeService cts=null;
    private KeyService ks = null;
    
    private int STATION_NUM = 0;

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
	 * @see et.bo.sys.station.service.StationService#StationIndex(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List StationIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
        List l = new ArrayList();
        StationSearch stationSearch = new StationSearch();
        Object[] result = null;
        try {
        	result = (Object[]) dao.findEntity(stationSearch.searchStationInfo(dto, pageInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
         
        int s = dao.findEntitySize(stationSearch.searchStationInfo(dto, pageInfo));
        STATION_NUM = s;
        for (int i = 0, size = result.length; i < size; i++) {
        	SysStationInfo dep = (SysStationInfo) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id", dep.getId());
            dbd.set("personname", dep.getDepPersonName());
            dbd.set("deplevel", dep.getDepLevel());
            dbd.set("describe", dep.getDepDescribe());
            l.add(dbd);
        }
        return l;
	}
	
	private SysStationInfo createStationInfo(IBaseDTO dto){
		SysStationInfo dep = new SysStationInfo();
		dep.setId(ks.getNext("sys_station_info"));
		dep.setDepartmentId(dto.get("departmentid").toString());
		dep.setDepPersonName(dto.get("deppersonname").toString());
		dep.setDepLevel(dto.get("deplevel").toString());
		dep.setDepDescribe(dto.get("depdescribe").toString());
		dep.setRemark(dto.get("remark").toString());
		return dep;
	}

	/* (non-Javadoc)
	 * @see et.bo.sys.station.service.StationService#addStationBox(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addStationBox(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
        dao.saveEntity(createStationInfo(dto));
        flag = true;
        return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.sys.station.service.StationService#delStationBox(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean delStationBox(IBaseDTO dto) {
		// TODO Auto-generated method stub
        boolean flag = false;
        SysStationInfo dep = (SysStationInfo) dao.loadEntity(
        		SysStationInfo.class, dto.get("id").toString());
        dao.removeEntity(dep);
        flag = true;
        return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.sys.station.service.StationService#getStationSize()
	 */
	public int getStationSize() {
		// TODO Auto-generated method stub
		return STATION_NUM;
	}
	
	private SysStationInfo upStationInfo(IBaseDTO dto){
		SysStationInfo dep = new SysStationInfo();
		dep.setId(dto.get("id").toString());
		dep.setDepartmentId(dto.get("departmentid").toString());
		dep.setDepPersonName(dto.get("deppersonname").toString());
		dep.setDepLevel(dto.get("deplevel").toString());
		dep.setDepDescribe(dto.get("depdescribe").toString());
		dep.setRemark(dto.get("remark").toString());
		return dep;
	}

	/* (non-Javadoc)
	 * @see et.bo.sys.station.service.StationService#updateStationBox(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean updateStationBox(IBaseDTO dto) {
		// TODO Auto-generated method stub
        boolean flag = false;
        dao.updateEntity(upStationInfo(dto));
        flag = true;
        return flag;
	}

	public List StationList(String station_class) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		StationSearch stationSearch = new StationSearch();
		Object[] result = (Object[]) dao.findEntity(stationSearch.searchStationInfo(station_class));
		for (int i = 0, size = result.length; i < size; i++) {
			SysStationInfo dep = (SysStationInfo) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("personname", dep.getDepPersonName());
            dbd.set("deplevel", dep.getDepLevel());
            l.add(dbd);
        }
		return l;
	}
	
	public IBaseDTO getStationInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		SysStationInfo dep = (SysStationInfo) dao.loadEntity(
				SysStationInfo.class, id);
		dto.set("id", dep.getId());
		dto.set("departmentid", dep.getDepartmentId());
		dto.set("deppersonname", dep.getDepPersonName());
		dto.set("deplevel", dep.getDepLevel());
		dto.set("depdescribe", dep.getDepDescribe());
		dto.set("remark", dep.getRemark());
		return dto;
	}

	public TreeControlI loadDepartments() {
		// TODO Auto-generated method stub
		return cts.getTree(cts.ROOT,true);
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
