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
    //����������ֵת��������
    public static String tochina(String str) throws Exception {
        str = new String(str.getBytes("ISO-8859-1"), "GBK");
        return str;
    }

    //��InputStreamת��ΪString
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
            System.err.println("����Ϣת��ʱ����");
        }
        return "";
    }
    
//    //��"ת����\",��������ҳ����ʾ
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
    ' ȥ��Html��ʽ�����ڴ����ݿ���ȡ��ֵ���������ʱ
    ' ע�⣺value="?"���һ��Ҫ��˫����
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
    
	/**�滻�ַ���*/
	public static String Replace(String source, String oldString, String newString)
	{
       StringBuffer output = new StringBuffer();

       int lengthOfSource = source.length();   // Դ�ַ�������
       int lengthOfOld = oldString.length();   // ���ַ�������
       int posStart = 0;   // ��ʼ����λ��
       int pos;            // ���������ַ�����λ��

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