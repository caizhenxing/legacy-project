package ocelot.common.util.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.StringSubstitution;
import org.apache.oro.text.regex.Substitution;
import org.apache.oro.text.regex.Util;








 /**
 * @author ’‘“ª∑«
 * @version 2007-1-15
 * @see
 */
public class AnalyseString {

	public static String email="[0-9A-Za-z]+@([0-9a-zA-Z]+.){1,2}(com|net|cn|com.cn)";
	public static String http="http://[0-9A-Za-z]+.([0-9A-Za-z]+.)*(com|net|cn|com.cn)";
	public static String http1="http://.*?/";
	
	
	public static String parseUnique(String source,String key)
	{
		PatternCompiler pc=new Perl5Compiler();

		try {
			Pattern pattern=pc.compile(key);
			PatternMatcher pm=new Perl5Matcher();
			PatternMatcherInput pmi=new PatternMatcherInput(source);
			if(pm.contains(pmi,pattern))
			//pm.matchesPrefix(source,pattern);
			{
				MatchResult mr=pm.getMatch();
				
				return mr.group(0);
			}
			return null;
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public  static List<String> parse(String source,String[] keyList)
	{
		List<String> result=new ArrayList<String>();
		
		StringBuffer key=new StringBuffer();
		
		
		for(int i=0,size=keyList.length;i<size;i++)
		{
			if(i!=0)
				key.append("|");
			key.append(keyList[i]);
					
		}
		
		
		
		PatternCompiler pc=new Perl5Compiler();

		try {
			Pattern pattern=pc.compile(key.toString());
			PatternMatcher pm=new Perl5Matcher();
			PatternMatcherInput pmi=new PatternMatcherInput(source);
			int s=0;
			while(pm.contains(pmi,pattern))
			//pm.matchesPrefix(source,pattern);
			{
				MatchResult mr=pm.getMatch();
				
				
				result.add(mr.group(s));
				
				s++;
			}
			
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
	public static String filtrate(String source,String[] keyList)
	{
		return  filtrate(source,keyList,"");
	}
	public static String filtrate(String source,String[] keyList,String substitute)
	{
		
		StringBuffer key=new StringBuffer();
		
		
		for(int i=0,size=keyList.length;i<size;i++)
		{
			if(i!=0)
				key.append("|");
			key.append(keyList[i]);
			
		
		}
		
		PatternCompiler pc=new Perl5Compiler();

		try {
			Pattern pattern=pc.compile(key.toString());
			PatternMatcher pm=new Perl5Matcher();
			String result=Util.substitute(pm,pattern,new Perl5Substitution(substitute),source,Util.SUBSTITUTE_ALL);
			
			return result;
			
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static boolean contain(String source,String[] keyList)
	{
		StringBuffer key=new StringBuffer();
		String anyis=null;
		anyis="|";
		if(keyList.length==0)
			return false;
		for(int i=0,size=keyList.length;i<size;i++)
		{
			if(i!=0)
				key.append(anyis);
			key.append(keyList[i]);
		}
		
		PatternCompiler pc=new Perl5Compiler();

		try {
			Pattern pattern=pc.compile(key.toString());
			PatternMatcher pm=new Perl5Matcher();
			PatternMatcherInput pmi=new PatternMatcherInput(source);
			return  pm.contains(pmi,pattern);
			
			
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static void  main(String[] arg0)
	{
		/*long b=System.currentTimeMillis();
		String[] keyword={"\\[rm\\].*?\\[/rm\\]",
				"\\[mp\\].*?\\[/mp\\]",
				"\\[img\\].*?\\[/img\\]"
		};
		String[] keyword1={"\\[rm\\]","\\[/rm\\]",
				"\\[mp\\]","\\[/mp\\]",
				"\\[img\\]","\\[/img\\]"
		};
		AnalyseString as=new AnalyseString();
		System.out.println(as.parseUnique("http://www.51.com.cn/user/bbs/141/200512/77918,1.html",as.http1));
		//System.out.println(as.contain("[mp]mpmpmpmpmpmp[/mp] [rm]rmrmrmrmrmrmmr[/rm] [img]imgimgimgimgimgimgimgimgimgimgimg[/img] [rm]mrmr[/rm]",keyword1));
		System.out.println(as.contain("aaaaaabbbbbcccccabc",new String[]{}));
		String[] s=new String[1];
		s[0]=",";*/
		String l="0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#";
		List<String> receives=AnalyseString.parseString(l,"#");
		for(String a:receives)
		{
			System.out.println(a);
		}
	}
	public static List<String> parseString(String source,String key)
	{
		List<String> re=new ArrayList<String>();
		StringTokenizer strtk = new StringTokenizer(source,",");
        String takeuser = "";
        while (strtk.hasMoreTokens()) {
            takeuser = strtk.nextToken();
            re.add(takeuser);
            //dto.set("sendUser",takeuser);
        }
        return re;
	}
	
}
