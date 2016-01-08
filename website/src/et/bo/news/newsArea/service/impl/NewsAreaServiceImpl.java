/**
 * 	@(#)NewsAreaServiceImpl.java   2007-1-23 œ¬ŒÁ02:50:50
 *	 °£ 
 *	 
 */
package et.bo.news.newsArea.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.format.service.impl.FormatHelp;
import et.bo.news.newsArea.service.NewsAreaService;
import et.po.NewsArea;
import et.po.NewsStyle;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @describe
 * @author “∂∆÷¡¡
 * @version 2007-1-23
 * @see
 */
public class NewsAreaServiceImpl implements NewsAreaService {
	
    private int num = 0;
	
	private BaseDAO dao = null;

    private KeyService ks = null;

	public void areaAdd(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(this.createArea(dto));
	}
	
	private NewsArea createArea(IBaseDTO dto){
		NewsArea na = new NewsArea();
		na.setId(ks.getNext("News_Area"));
		na.setStyleId(dto.get("styleId").toString());
		na.setNewsAreaName(dto.get("newsAreaName").toString());
		na.setRemark(dto.get("remark").toString());
		return na;
	}

	public void areaDel(String id) {
		// TODO Auto-generated method stub
		NewsArea na = (NewsArea)dao.loadEntity(NewsArea.class, id);
		dao.removeEntity(na);
	}

	public List areaList(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		NewsAreaHelp nah = new NewsAreaHelp();
		Object[] result = (Object[])dao.findEntity(nah.AreaQuery(dto, pi));
		num = dao.findEntitySize(nah.AreaQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			NewsArea na = (NewsArea) result[i];
			list.add(this.newsAreaToDTO(na));
		}
		return list;
	}
	
	private DynaBeanDTO newsAreaToDTO(NewsArea na){
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("id", na.getId());
		dto.set("newsAreaName", na.getNewsAreaName());
		dto.set("styleId", getStyleName(na.getStyleId()));
		dto.set("remark",na.getRemark());
		return dto;
	}
	
	private String getStyleName(String id){
		NewsStyle ns = (NewsStyle)dao.loadEntity(NewsStyle.class, id);
		return ns.getStyleDescribe();
	}

	public void areaUpdate(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(this.modifyArea(dto));	
	}
	
	private NewsArea modifyArea(IBaseDTO dto){
		NewsArea na = (NewsArea)dao.loadEntity(NewsArea.class, dto.get("id").toString());
		na.setStyleId(dto.get("styleId").toString());
		na.setNewsAreaName(dto.get("newsAreaName").toString());
		na.setRemark(dto.get("remark").toString());
		return na;
	}

	public IBaseDTO getAreaInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		NewsArea na = (NewsArea)dao.loadEntity(NewsArea.class, id);
		dto.set("id", na.getId());
		dto.set("newsAreaName", na.getNewsAreaName());
		dto.set("styleId", na.getStyleId());
		dto.set("remark",na.getRemark());
		return dto;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return num;
	}
	
	public List<LabelValueBean> getAreaList(){
		List list = new ArrayList();
		NewsAreaHelp nah = new NewsAreaHelp();
		Object[] result = (Object[])dao.findEntity(nah.AreaQuerySelect());
		for(int i=0,size=result.length;i<size;i++){
			NewsArea na = (NewsArea)result[i];
			list.add(new LabelValueBean(na.getNewsAreaName(),na.getId()));
		}
		return list;
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
