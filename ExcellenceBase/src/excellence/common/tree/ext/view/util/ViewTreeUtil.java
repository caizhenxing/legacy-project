/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.ext.view.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
/**
 * 视图树显示相关实现
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ViewTreeUtil {
	/**
	 * 解析 以map返回
	 * @param keyValueStr value close=closeImg.gif
	 * @version 2008-1-24
	 * @return Map key close value img
	 */
	public static Map parseKeyValue(String keyValueStr)
	{
		//生名一个初使化的map防止NullPointerException出项
		Map<String, String> kvMap = new HashMap<String, String>();
		if(keyValueStr != null)
		{
			StringTokenizer st = new StringTokenizer(keyValueStr.trim(),";");
			String token = null;
			while(st.hasMoreTokens())
			{
				token = st.nextToken().trim();
				//System.out.println(token+"++++++");
				int index = token.indexOf("=");
				if(index > -1&&index<token.length()-1)
				{
					kvMap.put(token.substring(0, index).trim(), token.substring(index+1));
				}
			}
		}

		return kvMap;
	}
}
