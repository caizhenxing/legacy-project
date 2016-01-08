/*
 * Created on Jun 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package et.bo.forum.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Translate {
    //将传过来的值转换成中文
    public static String tochina(String str) throws Exception {
        str = new String(str.getBytes("ISO-8859-1"), "GBK");
        return str;
    }

    //将InputStream转换为String
    public static String getStr(InputStream in) {
        try {
            StringBuffer temp = null;
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(in));
            String tmpStr = "";
            String strSum = "";
            while ((tmpStr = buffer.readLine()) != null) {
                strSum = strSum + tmpStr;
            }
            buffer.close();
            return strSum;
        } catch (Exception e) {
            System.err.println("流信息转换时出错！");
        }
        return "";
    }
    
//    //将"转换成\",用于在网页中显示
//    
//    public static String getTranslate(String tmpStr) throws Exception{
//        char[] c = tmpStr.toCharArray();
//        String str = "";
//        for(int i = 0 ; i < c.length ; i ++){
//
//        }
//        return str;
//    }
    /*' ============================================
    ' 去除Html格式，用于从数据库中取出值填入输入框时
    ' 注意：value="?"这边一定要用双引号
    ' ============================================*/
    public static String inHTML(String str)
    {
    	String sTemp;
    	sTemp = str;
//    	if(sTemp.equals(""))
//    	{
//			System.out.println("system exit!");    	
//    		System.exit(0);
//    		
//    	}
    	if(sTemp ==null)
    	{
    		return "";
    	}
    	sTemp = sTemp.replaceAll("&", "&amp;");
    	sTemp = sTemp.replaceAll("<", "&lt;");
    	sTemp = sTemp.replaceAll(">", "&gt;");
    	sTemp = sTemp.replaceAll("\"", "&quot;");
    	return sTemp;
    }
    
	/**替换字符串*/
	public static String Replace(String source, String oldString, String newString)
	{
       StringBuffer output = new StringBuffer();

       int lengthOfSource = source.length();   // 源字符串长度
       int lengthOfOld = oldString.length();   // 老字符串长度
       int posStart = 0;   // 开始搜索位置
       int pos;            // 搜索到老字符串的位置

       while ((pos = source.indexOf(oldString, posStart)) >= 0) {
           output.append(source.substring(posStart, pos));
           output.append(newString);
           posStart = pos + lengthOfOld;
       }
       if (posStart < lengthOfSource) {
           output.append(source.substring(posStart));
       }
       return output.toString();
	}
}