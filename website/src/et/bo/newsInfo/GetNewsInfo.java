/**
 * 	@(#)GetNewsInfo.java   2007-1-30 ÏÂÎç05:05:58
 *	 ¡£ 
 *	 
 */
package et.bo.newsInfo;

import et.bo.newsInfo.service.NewsInfoService;
import excellence.framework.base.container.SpringContainer;

/**
 * @describe
 * @author Administrator
 * @version 2007-1-30
 * @see
 */
public class GetNewsInfo {
	public static String getNews(){
		String news = "";
		SpringContainer s=SpringContainer.getInstance();
		NewsInfoService newsInfoService = (NewsInfoService)s.getBean("NewsInfoService");
		news = newsInfoService.getNewsString("NEWS_AREA_0000000043","NEW_STYLE_0000000038");
		System.out.println("++++++++++++++++++++++++++++++++===");
		return news;
	}
}
