/**
 * 	@(#)StringUtil.java   2006-11-20 ÉÏÎç10:00:49
 *	 ¡£ 
 *	 
 */
package ocelot.common.util.regex;

 /**
 * @author zhaoyifei
 * @version 2006-11-20
 * @see
 */
public class StringUtil {

	public static String ellipsis(String source,int num,String ellipsis)
	{
		StringBuilder sb=new StringBuilder();
		if(source.length()<num)
			return source;
		sb.append(source.subSequence(0,num));
		if(sb.length()==num)
			sb.append(ellipsis);
		return sb.toString();
	}
	public static void main(String[] arg0)
	{
		System.out.println(StringUtil.ellipsis("123456789",9,"@"));
	}
}
