/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.asset.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;


import et.bo.common.ListValueService;
import et.bo.oa.assissant.asset.service.AssetOperService;
import et.bo.oa.assissant.asset.service.AssetService;
import et.po.AssetsInfo;
import et.po.AssetsOper;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class AssetServiceImpl implements AssetService {

	private BaseDAO dao=null;
	private KeyService ks = null;
	private ListValueService listValueService =null;
	private AssetOperService assetOperService =null;
	/**
	 * @return Returns the assetOperService.
	 */
	public AssetOperService getAssetOperService() {
		return assetOperService;
	}

	/**
	 * @param assetOperService The assetOperService to set.
	 */
	public void setAssetOperService(AssetOperService assetOperService) {
		this.assetOperService = assetOperService;
	}

	/**
	 * @return Returns the dao.
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao The dao to set.
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return Returns the listValueService.
	 */
	public ListValueService getListValueService() {
		return listValueService;
	}

	/**
	 * @param listValueService The listValueService to set.
	 */
	public void setListValueService(ListValueService listValueService) {
		this.listValueService = listValueService;
	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	private AssetsInfo createPoByDTO(IBaseDTO dto)
	{
		AssetsInfo ai =new AssetsInfo();
		ai.setAssetsId(null !=dto.get("assetsId") && !"".equals((String)dto.get("assetsId")) ? (String)dto.get("assetsId") : ks.getNext("assets_info"));
		ai.setAssetsName(null !=dto.get("assetsName") && !"".equals((String)dto.get("assetsName")) ? (String)dto.get("assetsName") : "");
		ai.setAssetsNum(null !=dto.get("assetsNum") && !"".equals((String)dto.get("assetsNum")) ? new Integer((String)dto.get("assetsNum")) : 1);
		//TODO
		AssetsOper ao =new AssetsOper();
		String aoid =(String)dto.get("assetsOper");
		ao.setOperId(aoid);
		ai.setAssetsOper(ao);
		ai.setAssetsType(null !=dto.get("assetsType") && !"".equals((String)dto.get("assetsType")) ? (String)dto.get("assetsType") : "");
		ai.setAssetsWithfor(null !=dto.get("assetsWithfor") && !"".equals((String)dto.get("assetsWithfor")) ? (String)dto.get("assetsWithfor") : "");
		ai.setRemark(null !=dto.get("info_remark") && !"".equals((String)dto.get("info_remark")) ? (String)dto.get("info_remark") : "");
		return ai;
	}
	
	private IBaseDTO createDTOByPo(IBaseDTO dto, AssetsInfo ai)
	{
		dto.set("assetsId",ai.getAssetsId());
		dto.set("assetsName",ai.getAssetsName());
		dto.set("assetsNum",ai.getAssetsNum());
		dto.set("assetsOper",ai.getAssetsOper().getOperId());
		dto.set("assetsType",ai.getAssetsType());
		dto.set("assetsWithfor",ai.getAssetsWithfor());
		dto.set("info_remark",ai.getRemark());
		return dto;
	}
	
	public boolean insert(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		boolean flag =exist((String)dto.get("assetsId"));
		if(flag)
			return false;		
		AssetsInfo ai =createPoByDTO(dto);
		dao.saveEntity(ai);
		return true;
	}

	public boolean update(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		AssetsInfo ai =createPoByDTO(dto);
		dao.updateEntity(ai);
		return true;
	}

	public boolean delete(String id) {
		//TODO 需要写出方法的具体实现
		AssetsInfo ai =(AssetsInfo)dao.loadEntity(AssetsInfo.class,id);
		if(null ==ai)
		{
			return false;
		}
		else
		{
			dao.removeEntity(ai);
			return true;
		}
			
	}

	public List<IBaseDTO> list(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
//		GroupHelp gh =new GroupHelp();
		AssetHelp ah =new AssetHelp();
		Object [] o =this.dao.findEntity(ah.listMQ(dto,pi));
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				IBaseDTO tdto =new DynaBeanDTO();
				createDTOByPo(tdto,(AssetsInfo)oo);
				l.add(tdto);
			}
			return l;
		}
		return l;
	}

	public int listSize(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
		AssetHelp ah =new AssetHelp();
		MyQuery mq =ah.listMQ(dto,pi);
		int i =dao.findEntitySize(mq);
		return i;
	}

	public IBaseDTO load(String id) {
		//TODO 需要写出方法的具体实现
		AssetsInfo ai =(AssetsInfo)dao.loadEntity(AssetsInfo.class, id);
		IBaseDTO dto =new DynaBeanDTO();
		createDTOByPo(dto, ai);
		return dto;
	}
	
	public int deficiency(String deficiencyAsset)
	{
		AssetHelp ah =new AssetHelp();
		MyQuery mq =ah.deficiency(deficiencyAsset);
		int i =dao.findEntitySize(mq);
		return i;
	}

	public boolean exist(String id)
	{
		AssetsInfo ai =(AssetsInfo)dao.loadEntity(AssetsInfo.class,id);
		if(null==ai)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public int howMany(String AssetOperId)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(AssetsInfo.class);
		AssetsOper ao =new AssetsOper();
		ao.setOperId(AssetOperId);
		dc.add(Expression.eq("assetsOper",ao));
		dc.setProjection(Projections.sum("assetsNum"));
		mq.setDetachedCriteria(dc);
		Object [] o =dao.findEntity(mq);
		int i =0;
		
		if(null !=o && o.length >0 && o[0] !=null)
		{
			i =new Integer(o[0].toString()).intValue();
		}
//		
		return i;
	}
	
	public IBaseDTO getOperDtoByInfoId(String id)
	{
		IBaseDTO dto =new DynaBeanDTO();
		AssetsInfo ai =(AssetsInfo)dao.loadEntity(AssetsInfo.class,id);
		AssetsOper ao =ai.getAssetsOper();
		dto =this.assetOperService.load(ao.getOperId());
		return dto;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

}
