/**
 * 	@(#)NewsInfoService.java   2007-1-16 上午10:46:45
 *	 。 
 *	 
 */
package et.bo.newsInfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.news.common.GetStringByByte;
import et.bo.newsInfo.service.NewsInfoService;
import et.po.NewsArea;
import et.po.NewsArticle;
import et.po.NewsStyle;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @describe
 * @author Administrator
 * @version 2007-1-16
 * @see
 */
public class NewsInfoServiceImpl implements NewsInfoService {
    
	int num = 0;
	
	private BaseDAO dao = null;
	/**
	 * 
	 */
	public List getNewsList(String type){
		List list = new ArrayList();
		NewsInfoHelp nih = new NewsInfoHelp();
		Object[] result = (Object[])dao.findEntity(nih.newsQuery(type));
		for(int i=0,size=result.length;i<size;i++){
			NewsArticle na = (NewsArticle)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("articleid", na.getArticleid());
			if(na.getTitle().length()>16){
				dbd.set("title", na.getTitle().substring(0,16));
			}else{
				dbd.set("title", na.getTitle());
			}		
			dbd.set("updateDate", na.getUpdatetime());
			dbd.set("content", na.getContent());
//			dbd.set("friendName", fui.getFriendName());
//			dbd.set("addDate", fui.getAddDate());
			list.add(dbd);
		}
		return list;
	}
	
	public List getNewsSelectList(String type, String classType){
		List list = new ArrayList();
		NewsInfoHelp nih = new NewsInfoHelp();
		Object[] result = (Object[])dao.findEntity(nih.newsQuery(type));
		for(int i=0,size=result.length;i<size;i++){
			NewsArticle na = (NewsArticle)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
//			dbd.set("articleid", na.getArticleid());
			String title = "";
			String trtd = "";
			if(na.getTitle().length()>16){
				title=na.getTitle().substring(0,16);
			}else{
				title=na.getTitle();
			}
			String date = TimeUtil.getTheTimeStr(na.getUpdatetime(),"MM-dd");
//			dbd.set("updateDate", na.getUpdatetime());
//			dbd.set("content", na.getContent());
//			dbd.set("friendName", fui.getFriendName());
//			dbd.set("addDate", fui.getAddDate());
			if(type.equals("one")){
				trtd = "<tr><td class="+classType+">"+title+"</td></tr>";
			}else if(type.equals("two")){
				trtd = "<tr><td class="+classType+">"+title+"</td><td class="+classType+">"+"<div align='right'>"+date+"</div>"+"</td></tr>";
			}
			
			dbd.set("trtd", trtd);
			list.add(dbd);
		}
		return list;
	}
	
	public List getNewsStyle(String id){
		List list = new ArrayList();
		NewsArea na = (NewsArea)dao.loadEntity(NewsArea.class, id);
		if(na==null){return list;}
		NewsStyle ns = (NewsStyle)dao.loadEntity(NewsStyle.class, na.getStyleId());
		if(ns==null){return list;}
		NewsInfoHelp nih = new NewsInfoHelp();
		Object[] result = (Object[])dao.findEntity(nih.newsAreaQuery(na.getId(), ns.getNewsNum()));
		for(int i=0,size=result.length;i<size;i++){
			String title = "";
			StringBuilder sb = new StringBuilder();
			NewsArticle naOne = (NewsArticle)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			if(!ns.getTitleCharNum().equals("")){
//				System.out.println(GetStringByByte.substring(naOne.getTitle(), 16, ""));
				if(naOne.getTitle().getBytes().length>Integer.parseInt(ns.getTitleCharNum())*2){
					title=GetStringByByte.substring(naOne.getTitle(), Integer.parseInt(ns.getTitleCharNum())*2, "");
				}else{
					title=naOne.getTitle();
				}
			}else{
				title=naOne.getTitle();
			}
			sb.append("<tr>");
			sb.append("<td>");
//			sb.append("<div align='left'>");
			sb.append("<font");
			sb.append(" ");
			sb.append("style='font-size:");
			sb.append(ns.getTitleCharSize());
			sb.append("'");
			sb.append(" ");
			sb.append("color=");
			sb.append("'");
			sb.append(ns.getTitleCharColor());
			sb.append("'");
			sb.append(">");
//			System.out.println(ns.getArticleProperty());
			if(ns.getArticleProperty().equals("1")){
				sb.append("[新闻]");
			}
//			if(ns.getShowStyle().equals("1")){
			sb.append("<a href='/website/news/opernews.do?method=toNewsInfo&id=");
			sb.append(naOne.getArticleid());	
			sb.append("'");	
			sb.append("style='color:");
			sb.append(ns.getTitleCharColor());
			sb.append("'");
			sb.append(">");
			sb.append(title);
			sb.append("</a>");
			if(ns.getClickTimes().equals("1")){
				sb.append("(0)");
			}
//			}			
			sb.append("</font>");
//			sb.append("</div>");
			sb.append("</td>");
			if(ns.getAuthor().equals("1")){
				sb.append("<td width='50' align='right'>");
				sb.append("<font");
				sb.append(" ");
				sb.append("style='font-size:");
				sb.append(ns.getTitleCharSize());
				sb.append("'");
				sb.append(">");
				sb.append(naOne.getAuthor());
				sb.append("</font>");
				sb.append("</td>");
			}
			if(ns.getShowStyle().equals("2")){
				sb.append("<td width='50' align='right'>");
				sb.append("<font");
				sb.append(" ");
				sb.append("style='font-size:");
				sb.append(ns.getTitleCharSize());
				sb.append("'");
				sb.append(">");
				sb.append(TimeUtil.getTheTimeStr(naOne.getUpdatetime(),"MM-dd"));
				sb.append("</font>");
				sb.append("</td>");
			}
			sb.append("</tr>");
			dbd.set("trtd", sb.toString());
			list.add(dbd);
		}		
		return list;
	}
	
	public String getNewsString(){
		return "<div>333</div>";
	}
	
	public String getNewsString(String newsSort, String styleId){
		List list = new ArrayList();
		NewsArea na = (NewsArea)dao.loadEntity(NewsArea.class, newsSort);
		if(na==null){return "";}
		NewsStyle ns = (NewsStyle)dao.loadEntity(NewsStyle.class, styleId);
		if(ns==null){return "";}
		NewsInfoHelp nih = new NewsInfoHelp();
		Object[] result = (Object[])dao.findEntity(nih.newsAreaQuery(na.getId(), ns.getNewsNum()));
		StringBuilder sb = new StringBuilder();
		sb.append("<table width='95%' height='30' border='0' cellpadding='0' cellspacing='0'>");
		for(int i=0,size=result.length;i<size;i++){
			String title = "";
			NewsArticle naOne = (NewsArticle)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			if(!ns.getTitleCharNum().equals("")){
//				System.out.println(GetStringByByte.substring(naOne.getTitle(), 16, ""));
				if(naOne.getTitle().getBytes().length>Integer.parseInt(ns.getTitleCharNum())*2){
					title=GetStringByByte.substring(naOne.getTitle(), Integer.parseInt(ns.getTitleCharNum())*2, "");
				}else{
					title=naOne.getTitle();
				}
			}else{
				title=naOne.getTitle();
			}
			sb.append("<tr>");
			sb.append("<td>");
//			sb.append("<div align='left'>");
			sb.append("<font");
			sb.append(" ");
			sb.append("style='font-size:");
			sb.append(ns.getTitleCharSize());
			sb.append("'");
			sb.append(" ");
			sb.append("color=");
			sb.append("'");
			sb.append(ns.getTitleCharColor());
			sb.append("'");
			sb.append(">");
//			System.out.println(ns.getArticleProperty());
			if(ns.getArticleProperty().equals("1")){
				sb.append("[新闻]");
			}
//			if(ns.getShowStyle().equals("1")){
			sb.append("<a href='/website/news/opernews.do?method=toNewsInfo&id=");
			sb.append(naOne.getArticleid());	
			sb.append("'");	
			sb.append("style='color:");
			sb.append(ns.getTitleCharColor());
			sb.append("'");
			sb.append(">");
			sb.append(title);
			sb.append("</a>");
			if(ns.getClickTimes().equals("1")){
				sb.append("(0)");
			}
//			}			
			sb.append("</font>");
//			sb.append("</div>");
			sb.append("</td>");
			if(ns.getAuthor().equals("1")){
				sb.append("<td width='50' align='right'>");
				sb.append("<font");
				sb.append(" ");
				sb.append("style='font-size:");
				sb.append(ns.getTitleCharSize());
				sb.append("'");
				sb.append(">");
				sb.append(naOne.getAuthor());
				sb.append("</font>");
				sb.append("</td>");
			}
			if(ns.getShowStyle().equals("2")){
				sb.append("<td width='50' align='right'>");
				sb.append("<font");
				sb.append(" ");
				sb.append("style='font-size:");
				sb.append(ns.getTitleCharSize());
				sb.append("'");
				sb.append(">");
				sb.append(TimeUtil.getTheTimeStr(naOne.getUpdatetime(),"MM-dd"));
				sb.append("</font>");
				sb.append("</td>");
			}
			sb.append("</tr>");
//			dbd.set("trtd", sb.toString());
//			list.add(dbd);
		}	
		sb.append("</table>");
		return sb.toString();
	}
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
