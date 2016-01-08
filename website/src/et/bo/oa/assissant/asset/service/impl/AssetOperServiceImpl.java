/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.asset.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;


import et.bo.common.ListValueService;
import et.bo.oa.assissant.asset.service.AssetOperService;
import et.po.AssetsOper;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class AssetOperServiceImpl implements AssetOperService {

	private BaseDAO dao=null;
	private KeyService ks = null;
	private ListValueService listValueService =null;
	
	private AssetsOper createPoByDTO(IBaseDTO dto)
	{
		AssetsOper ao =new AssetsOper();
		ao.setOperId(null !=dto.get("operId") && !"".equals((String)dto.get("operId")) ? (String)dto.get("operId") : ks.getNext("assets_oper"));
		ao.setOperCode(null !=dto.get("operCode") && !"".equals((String)dto.get("operCode")) ? (String)dto.get("operCode") : "");
		ao.setOperName(null !=dto.get("operName") && !"".equals((String)dto.get("operName")) ? (String)dto.get("operName") : "");
		ao.setOperType(null !=dto.get("operType") && !"".equals((String)dto.get("operType")) ? (String)dto.get("operType") : "");		
		ao.setOperTime(null !=dto.get("operTime") && !"".equals((String)dto.get("operTime")) ? TimeUtil.getTimeByStr((String)dto.get("operTime"),"yyyy-MM-dd") : new Date());
		ao.setAssetsPrice(null !=dto.get("assetsPrice") && !"".equals((String)dto.get("assetsPrice")) ? new Double((String)dto.get("assetsPrice")) : 0.0);
		ao.setAssetsNum(null !=dto.get("operassetsNum") && !"".equals((String)dto.get("operassetsNum")) ? new Integer((String)dto.get("operassetsNum")) : 0);
		ao.setInCompany(null !=dto.get("inCompany") && !"".equals((String)dto.get("inCompany")) ? (String)dto.get("inCompany") : "");
		ao.setInPeople(null !=dto.get("inPeople") && !"".equals((String)dto.get("inPeople")) ? (String)dto.get("inPeople") : "");
		ao.setOutCompany(null !=dto.get("outCompany") && !"".equals((String)dto.get("outCompany")) ? (String)dto.get("outCompany") : "");
		ao.setOutPeople(null !=dto.get("outPeople") && !"".equals((String)dto.get("outPeople")) ? (String)dto.get("outPeople") : "");
		ao.setSign(null !=dto.get("sign") && !"".equals((String)dto.get("sign")) ? (String)dto.get("sign") : "");
		ao.setRemark(null !=dto.get("remark") && !"".equals((String)dto.get("remark")) ? (String)dto.get("remark") : "");
		return ao;
	}
	
	private IBaseDTO createDTOByPo(IBaseDTO dto, AssetsOper ao)
	{
		dto.set("operId",ao.getOperId());
		dto.set("operCode",ao.getOperCode());
		dto.set("operName",ao.getOperName());
		dto.set("operType",ao.getOperType());
		dto.set("operTime",ao.getOperTime());
		dto.set("assetsPrice",ao.getAssetsPrice());
		dto.set("operassetsNum",ao.getAssetsNum());
		dto.set("inCompany",ao.getInCompany());
		dto.set("inPeople",ao.getInPeople());
		dto.set("outCompany",ao.getOutCompany());
		dto.set("outPeople",ao.getOutPeople());
		dto.set("sign",ao.getSign());
		dto.set("remark",ao.getRemark());		
		return dto;
	}
	
	public boolean insert(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
//		dto.set("sign","1");
		AssetsOper ao =createPoByDTO(dto);
		dao.saveEntity(ao);
		return true;
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

	public boolean update(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		AssetsOper ao =createPoByDTO(dto);
		dao.updateEntity(ao);
		return true;
	}

	public boolean delete(String id) {
		//TODO 需要写出方法的具体实现
		AssetsOper ao =(AssetsOper)dao.loadEntity(AssetsOper.class,id);
		if(null ==ao)
		{
			return false;
		}
		else
		{
			dao.removeEntity(ao);
			return true;
		}
	}

	public List<IBaseDTO> list(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
		AssetOperHelp aoh =new AssetOperHelp();
		Object [] o =this.dao.findEntity(aoh.listMQ(dto,pi));
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				IBaseDTO tdto =new DynaBeanDTO();
				createDTOByPo(tdto,(AssetsOper)oo);
				l.add(tdto);
			}
			return l;
		}
		return l;
	}

	public int listSize(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
		AssetOperHelp aoh =new AssetOperHelp();
		int i =dao.findEntitySize(aoh.listMQ(dto,pi));
		return i;
	}

	public IBaseDTO load(String id) {
		//TODO 需要写出方法的具体实现
		IBaseDTO dto =new DynaBeanDTO();
		AssetsOper ao =(AssetsOper)dao.loadEntity(AssetsOper.class,id);
		createDTOByPo(dto ,ao);
		return dto;
	}
	
	public boolean existOperCode(String operCode)
	{
		MyQuery mq =new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(AssetsOper.class);
		dc.add(Expression.eq("operCode",operCode));
		mq.setDetachedCriteria(dc);
		Object [] o =dao.findEntity(mq);
		if(o!=null && o.length>0)
		{
			return true;
		}
		return false;
	}
	
	public List listLVBatch()
	{
		List list =new ArrayList();
		MyQuery mq =new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(AssetsOper.class);
		dc.add(Expression.eq("sign","1"));
		mq.setDetachedCriteria(dc);
		Object [] temp =this.dao.findEntity(mq);
		
		if(null !=temp && temp.length >0)
		{
			for(Object oo:temp)
			{				
				AssetsOper ao =(AssetsOper)oo;
				String k =ao.getOperCode();
				String v =ao.getOperId();
				LabelValueBean lv =new LabelValueBean(k,v);
				list.add(lv);
//				
			}
		}	
//		
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

}
