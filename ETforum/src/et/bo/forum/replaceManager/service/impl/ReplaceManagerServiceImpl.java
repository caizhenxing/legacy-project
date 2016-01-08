/**
 * 	@(#)ReplaceManagerServiceImpl.java   2006-12-25 下午04:03:49
 *	 。 
 *	 
 */
package et.bo.forum.replaceManager.service.impl;

import java.util.StringTokenizer;

import et.bo.forum.common.io.IO;
import et.bo.forum.common.io.RW;
import et.bo.forum.replaceManager.service.ReplaceManagerService;

/**
 * @author 叶浦亮
 * @version 2006-12-25
 * @see
 */
public class ReplaceManagerServiceImpl implements ReplaceManagerService {

	IO io = new IO();
	
	public void addRule(String ruleArray) {
		// TODO Auto-generated method stub
		StringTokenizer strtk = new StringTokenizer(ruleArray,"\r\n");
//		System.out.println();
		int j = 0;
		StringBuilder sb = new StringBuilder();
		while(strtk.hasMoreTokens()){
			String s = (String) strtk.nextToken();
			System.out.println("-------------------------------------------");
			System.out.println(s);
			System.out.println("-------------------------------------------");
			System.out.println(j++);
			int i = s.indexOf("=");
			if(i==-1){
				sb.append(s);
				sb.append("\t");
				sb.append("=");
				sb.append("\t");
				sb.append("");
				sb.append("\r\n");
			}else{
				sb.append(s.substring(0, i));
//				System.out.println(s.substring(0, i));
				sb.append("\t");
				sb.append("=");
				sb.append("\t");
				sb.append(s.substring(i+1, s.length()));
//				System.out.println(s.substring(i+1, s.length()));
				sb.append("\r\n");
			}		
		}
		RW rw = new RW();
		rw.write("D:/1.txt", sb.toString());		
	}

	public void delRule(String id) {
		// TODO Auto-generated method stub
		
	}

	public void updateRule(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public String getRule(){
		try {
			String s = io.read("d:/1.txt");
			StringTokenizer strtk = new StringTokenizer(s,"\r\n");
			StringBuilder sb = new StringBuilder();
			while(strtk.hasMoreTokens()){
				String rule = (String) strtk.nextToken();
				StringTokenizer strtkRule = new StringTokenizer(rule);	
				sb.append(strtkRule.nextToken().trim());
				sb.append(strtkRule.nextToken().trim());
                boolean bl = strtkRule.hasMoreTokens();
                if(bl){
                	sb.append(strtkRule.nextToken().trim());
                }else{
                	sb.append("");
                }
				
				sb.append("\r\n");				
			}
			System.out.println("--------------------------------------------");
			System.out.println(sb.toString());
			System.out.println("--------------------------------------------");
			return sb.toString();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public String postReplace(String postInfo){
		System.out.println(postInfo);
		try {
			String s = io.read("d:/1.txt");
			StringTokenizer strtk = new StringTokenizer(s,"\r\n");
//			StringBuilder sb = new StringBuilder();
			while(strtk.hasMoreTokens()){
				String rule = (String) strtk.nextToken();
				StringTokenizer strtkRule = new StringTokenizer(rule);
				String EL = strtkRule.nextToken().trim();
				System.out.println("表达式 "+EL);
				strtkRule.nextToken().trim();
				String replaceS = "";
				boolean bl = strtkRule.hasMoreTokens();
                if(bl){
                	replaceS = strtkRule.nextToken().trim();
                }
				System.out.println("替换为 "+replaceS);
				postInfo = postInfo.replaceAll(EL, replaceS);			
			}
			System.out.println("--------------------------------------------");
//			System.out.println(sb.toString());
			System.out.println("--------------------------------------------");
//			return sb.toString();
			return postInfo;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	

}
