/**
 * 沈阳卓越科技有限公司
 * 2008-5-5
 */
package et.bo.callcenter.pbx2ivr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import et.bo.callcenter.pbx2ivr.service.Pbx2IvrService;
import et.po.PortPbxIvr;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 梁云锋
 * 
 */
public class Pbx2IvrServiceImpl implements Pbx2IvrService {
	private BaseDAO dao;

	private KeyService ks;

	public static Map<String, IBaseDTO> portsMap = new HashMap<String, IBaseDTO>();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.pbx2ivr.service.Pbx2IvrService#add(excellence.framework.base.dto.IBaseDTO)
	 */
	public void add(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PortPbxIvr record = new PortPbxIvr();
		record.setId(ks.getNext("port_pbx_ivr"));
		record.setPbxPort(dto.get("pbxPort").toString());
		record.setPbxType(dto.get("pbxType").toString());
		record.setIvrPort(dto.get("ivrPort").toString());
		record.setRemark(dto.get("remark").toString());
		dao.saveEntity(record);
		portsMap.put(record.getId(), dto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.pbx2ivr.service.Pbx2IvrService#delete(excellence.framework.base.dto.IBaseDTO)
	 */
	public void delete(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PortPbxIvr record = (PortPbxIvr) dao.loadEntity(PortPbxIvr.class, dto
				.get("id").toString());
		dao.removeEntity(record);
		IBaseDTO tmp = portsMap.get(dto.get("id").toString());
		portsMap.remove(tmp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.pbx2ivr.service.Pbx2IvrService#getPortMapInfo(java.lang.String)
	 */
	public IBaseDTO getPortMapInfo(String id) {
		// TODO Auto-generated method stub
		DynaBeanDTO dto = new DynaBeanDTO();
		PortPbxIvr record = (PortPbxIvr) dao.loadEntity(PortPbxIvr.class, id);
		dto.set("id", record.getId());
		dto.set("pbxPort", record.getPbxPort());
		dto.set("pbxType", record.getPbxType());
		dto.set("ivrPort", record.getIvrPort());
		dto.set("remark", record.getRemark());
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.pbx2ivr.service.Pbx2IvrService#query()
	 */
	public List<DynaBeanDTO> query() {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PortPbxIvr.class);
		mq.setDetachedCriteria(dc);
		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0; i < result.length; i++) {
			PortPbxIvr record = (PortPbxIvr) result[i];
			DynaBeanDTO dto = new DynaBeanDTO();
			dto.set("id", record.getId());
			dto.set("pbxPort", record.getPbxPort());
			dto.set("pbxType", record.getPbxType());
			dto.set("ivrPort", record.getIvrPort());
			dto.set("remark", record.getRemark());
			list.add(dto);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.pbx2ivr.service.Pbx2IvrService#update(excellence.framework.base.dto.IBaseDTO)
	 */
	public void update(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PortPbxIvr record = (PortPbxIvr) dao.loadEntity(PortPbxIvr.class, dto
				.get("id").toString());
		record.setPbxPort(dto.get("pbxPort").toString());
		record.setPbxType(dto.get("pbxType").toString());
		record.setIvrPort(dto.get("ivrPort").toString());
		record.setRemark(dto.get("remark").toString());
		portsMap.put(dto.get("id").toString(), dto);
	}
	/**
	 * 初始化portsMap，加载所有交换机与IVR端口映射
	 *
	 */
	public void initPortsMap(){
		if(portsMap==null||portsMap.isEmpty()){
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PortPbxIvr.class);
		mq.setDetachedCriteria(dc);
		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0; i < result.length; i++) {
			PortPbxIvr record = (PortPbxIvr) result[i];
			DynaBeanDTO dto = new DynaBeanDTO();
			dto.set("id", record.getId());
			dto.set("pbxPort", record.getPbxPort());
			dto.set("pbxType", record.getPbxType());
			dto.set("ivrPort", record.getIvrPort());
			dto.set("remark", record.getRemark());
			portsMap.put(dto.get("id").toString(),dto);
		}
		}
	}
	/**
	 * 根据ivr端口得到对应的交换机端口
	 * @param ivrPort
	 * @return
	 */
	public String getPbxPortByIvr(String ivrPort){
		Iterator<Map.Entry<String, IBaseDTO>> i=portsMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=i.next();
			IBaseDTO temp=(IBaseDTO)entry.getValue();
			if(temp.get("ivrPort").toString().equals(ivrPort))
				return temp.get("pbxPort").toString();
		}
		return null;
	}
	/**
	 * 根据交换机端口得到对应的IVR端口
	 * @param pbxPort
	 * @return
	 */
	public String getIvrPortByPbx(String pbxPort){
		Iterator<Map.Entry<String, IBaseDTO>> i=portsMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=i.next();
			IBaseDTO temp=(IBaseDTO)entry.getValue();
			if(temp.get("pbxPort").toString().equals(pbxPort))
				return temp.get("ivrPort").toString();
		}
		return null;
	}

}
