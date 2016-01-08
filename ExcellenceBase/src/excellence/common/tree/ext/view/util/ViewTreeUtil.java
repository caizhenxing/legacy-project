/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.ext.view.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
/**
 * ��ͼ����ʾ���ʵ��
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ViewTreeUtil {
	/**
	 * ���� ��map����
	 * @param keyValueStr value close=closeImg.gif
	 * @version 2008-1-24
	 * @return Map key close value img
	 */
	public static Map parseKeyValue(String keyValueStr)
	{
		//����һ����ʹ����map��ֹNullPointerException����
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
