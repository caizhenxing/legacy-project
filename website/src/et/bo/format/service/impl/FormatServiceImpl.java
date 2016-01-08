/**
 * 	@(#)FormatServiceImpl.java   2007-1-22 …œŒÁ10:13:34
 *	 °£ 
 *	 
 */
package et.bo.format.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.format.service.FormatService;
import et.po.NewsStyle;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @describe
 * @author “∂∆÷¡¡
 * @version 2007-1-22
 * @see
 */
public class FormatServiceImpl implements FormatService{
	
	private int num = 0;
	
	private BaseDAO dao = null;

    private KeyService ks = null;
	
	public void formatAdd(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(this.createFormat(dto));
	}
	
	private NewsStyle createFormat(IBaseDTO dto){
		NewsStyle ns = new NewsStyle();
		ns.setId(ks.getNext("new_style"));
		ns.setStyleDescribe(dto.get("styleDescribe").toString());
		ns.setNewsNum(dto.get("newsNum").toString());
		ns.setShowStyle(dto.get("showStyle").toString());
		ns.setTitleCharNum(dto.get("titleCharNum").toString());
		ns.setTitleCharColor(dto.get("titleCharColor").toString());
		ns.setTitleCharFont(dto.get("titleCharFont").toString());
		ns.setTitleCharSize(dto.get("titleCharSize").toString());
		ns.setContentCharNum(dto.get("contentCharNum").toString());
		//
		ns.setArticleProperty(dto.get("articleProperty").toString());
		ns.setAuthor(dto.get("author").toString());
		ns.setClickTimes(dto.get("clickTimes").toString());
		ns.setUpdatetime(dto.get("updatetime").toString());
		ns.setShowMore(dto.get("showMore").toString());
		ns.setIsHot(dto.get("isHot").toString());
		ns.setHotArticle(dto.get("hotArticle").toString());
		ns.setTuijianArticle(dto.get("tuijianArticle").toString());
		ns.setDateRange(dto.get("dateRange").toString());
		ns.setPaixuField(dto.get("paixuField").toString());
		ns.setPaixuMethod(dto.get("paixuMethod").toString());
		return ns;
	}

	public void formatDel(String id) {
		// TODO Auto-generated method stub
		NewsStyle ns = (NewsStyle)dao.loadEntity(NewsStyle.class, id);
		dao.removeEntity(ns);		
	}

	public List formatList(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		FormatHelp fh = new FormatHelp();
		Object[] result = (Object[]) dao.findEntity(fh.formatQuery(
				dto, pi));
		num = dao.findEntitySize(fh.formatQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			NewsStyle ns = (NewsStyle) result[i];
			list.add(this.newsStyleToDTO(ns));
		}
		return list;
	}
	
	private DynaBeanDTO newsStyleToDTO(NewsStyle ns){
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("id", ns.getId());
		dto.set("styleDescribe", ns.getStyleDescribe());
		dto.set("showStyle", ns.getShowStyle());
		dto.set("newsNum",ns.getNewsNum());
		dto.set("titleCharNum", ns.getTitleCharNum());
		dto.set("titleCharColor", ns.getTitleCharColor());
		dto.set("titleCharFont", ns.getTitleCharFont());
		dto.set("titleCharSize", ns.getTitleCharNum());
		return dto;
	}

	public void formatUpdate(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(this.modifyFormat(dto));		
	}
	
	private NewsStyle modifyFormat(IBaseDTO dto){
		NewsStyle ns = (NewsStyle)dao.loadEntity(NewsStyle.class, dto.get("id").toString());
		ns.setStyleDescribe(dto.get("styleDescribe").toString());
		ns.setNewsNum(dto.get("newsNum").toString());
		ns.setShowStyle(dto.get("showStyle").toString());
		ns.setTitleCharNum(dto.get("titleCharNum").toString());
		ns.setTitleCharColor(dto.get("titleCharColor").toString());
		ns.setTitleCharFont(dto.get("titleCharFont").toString());
		ns.setTitleCharSize(dto.get("titleCharSize").toString());
		ns.setContentCharNum(dto.get("contentCharNum").toString());
        //
		ns.setArticleProperty(dto.get("articleProperty").toString());
		ns.setAuthor(dto.get("author").toString());
		ns.setClickTimes(dto.get("clickTimes").toString());
		ns.setUpdatetime(dto.get("updatetime").toString());
		ns.setShowMore(dto.get("showMore").toString());
		ns.setIsHot(dto.get("isHot").toString());
		ns.setHotArticle(dto.get("hotArticle").toString());
		ns.setTuijianArticle(dto.get("tuijianArticle").toString());
		ns.setDateRange(dto.get("dateRange").toString());
		ns.setPaixuField(dto.get("paixuField").toString());
		ns.setPaixuMethod(dto.get("paixuMethod").toString());
		return ns;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return num;
	}
	
	public IBaseDTO getFormatInfo(String id){
		IBaseDTO dto = new DynaBeanDTO();
		NewsStyle ns = (NewsStyle)dao.loadEntity(NewsStyle.class, id);
		dto.set("id", ns.getId());
		dto.set("styleDescribe", ns.getStyleDescribe());
		dto.set("showStyle", ns.getShowStyle());
		dto.set("newsNum",ns.getNewsNum());
		dto.set("titleCharNum", ns.getTitleCharNum());
		dto.set("titleCharColor", ns.getTitleCharColor());
		dto.set("titleCharFont", ns.getTitleCharFont());
		dto.set("titleCharSize", ns.getTitleCharSize());
		//
		dto.set("articleProperty", ns.getArticleProperty());
		dto.set("author", ns.getAuthor());
		dto.set("clickTimes", ns.getClickTimes());
		dto.set("updatetime", ns.getUpdatetime());
		dto.set("showMore", ns.getShowMore());
		dto.set("isHot", ns.getIsHot());
		dto.set("hotArticle", ns.getHotArticle());
		dto.set("tuijianArticle", ns.getTuijianArticle());
		dto.set("dateRange", ns.getDateRange());
		dto.set("paixuField", ns.getPaixuField());
		dto.set("paixuMethod", ns.getPaixuMethod());
		return dto;
	}
	
	public List<LabelValueBean> getStyleList(){
		List list = new ArrayList();
		FormatHelp fh = new FormatHelp();
		Object[] result = (Object[])dao.findEntity(fh.styleQuery());
		for(int i=0,size=result.length;i<size;i++){
			NewsStyle ns = (NewsStyle)result[i];
			list.add(new LabelValueBean(ns.getStyleDescribe(),ns.getId()));
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
