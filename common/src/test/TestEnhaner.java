/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：common
 * 制作时间：Dec 8, 20074:21:59 PM
 * 包名：test
 * 文件名：TestEnhaner.java
 * 制作者：zhaoyf
 * @version 
 */
package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.zyf.exception.UnexpectedException;
import com.zyf.utils.DateUtils;

/**
 * 
 * @author zhaoyf
 * @version 1.0
 */
public class TestEnhaner {

private static Perl5Matcher matcher = new Perl5Matcher();
private static Pattern dateFormatPattern;
private static final String dateString = "EEE MMM dd HH:mm:ss zzz yyyy";
    private void aa() {
       
    }
	/**
	 * 功能描述
	 * @param args
	 * Dec 8, 2007 4:21:59 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
	            dateFormatPattern = new Perl5Compiler().compile(
	                "([0-9]{4}[^0-9][0-9]{1,2}[^0-9][0-9]{1,2}[^0-9\\s]?)?([\\s]?[0-9]{1,2}:[0-9]{1,2}(:[0-9]{1,2}(\\.[0-9]+)?)?)?", 
	                Perl5Compiler.CASE_INSENSITIVE_MASK);
	        } catch (MalformedPatternException e) {
	            throw new UnexpectedException("不能编译解析日期格式的正则表达式", e);
	        }
		String str="1900-01-01 00:00:00.";
		StringBuffer canonicalFormat = new StringBuffer(25);
        SimpleDateFormat sdf = null;
        Date ret = null;
        try {
        	//System.out.println(dateFormatPattern.getPattern());
            if (matcher.matches(str, dateFormatPattern)) {
                MatchResult result = matcher.getMatch();
                if(result.group(1) == null)
                {
                	canonicalFormat.append("1900-01-01 ");
                }else
                {
                canonicalFormat.append(result.group(1));
                }
                if (result.group(2) == null) {
                    canonicalFormat.append(" 00:00:00");
                } else {
                    canonicalFormat.append(result.group(2));
                }
                
                if (result.group(3) == null) {
                    canonicalFormat.append(":00");
                } else {
                    canonicalFormat.append(result.group(3));
                }
                
                sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINESE);
                try {
                    ret = sdf.parse(canonicalFormat.toString());
                } catch (ParseException e) {
                    sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.CHINESE);
                    ret = sdf.parse(canonicalFormat.toString());
                }
            } else {
                sdf = new SimpleDateFormat(dateString, Locale.US);
                ret = sdf.parse(str);
            }
        } catch (ParseException e) {
            throw new UnexpectedException("不能把[" + str + "]解析为日期", e);
        }

	}

}
