/**
 * 	@(#)GetStringByByte.java   2007-1-30 ����01:55:53
 *	 �� 
 *	 
 */
package et.bo.news.common;

/**
 * @describe
 * @author Administrator
 * @version 2007-1-30
 * @see
 */
public class GetStringByByte {
	/**
     * ȡ�ַ�����ǰtoCount���ַ�
     *
     * @param str �������ַ���
     * @param toCount ��ȡ����
     * @param more ��׺�ַ���
     * @version 2004.11.24
     * @author zhulx
     * @return String
     */
    public static String substring(String str, int toCount,String more)
    {
      int reInt = 0;
      String reStr = "";
      if (str == null)
        return "";
      char[] tempChar = str.toCharArray();
      for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
        String s1 = str.valueOf(tempChar[kk]);
        byte[] b = s1.getBytes();
        reInt += b.length;
        reStr += tempChar[kk];
      }
      if (toCount == reInt || (toCount == reInt - 1))
        reStr += more;
      return reStr;
    } 

}
