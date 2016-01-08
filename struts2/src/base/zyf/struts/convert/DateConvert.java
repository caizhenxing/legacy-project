/**
 * 
 * 项目名称：struts2
 * 制作时间：May 27, 20093:22:41 PM
 * 包名：base.zyf.struts.convert
 * 文件名：DateConvert.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.struts.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

import base.zyf.tools.Tools;

/**
 * 用于自动转换date类型
 * @author zhaoyifei
 * @version 1.0
 */
public class DateConvert extends StrutsTypeConverter {
	 final Log log = LogFactory.getLog(getClass()); 
	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@Override
	public Object convertFromString(Map arg0, String[] values, Class arg2) {
		Pattern pattern = Pattern.compile("yyyy-MM-dd");   
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   
        if(pattern.matcher(values[0]).find())   
        {   
            try {   
                return dateFormat.parse(values[0]);   
            } catch (ParseException e) {
				log.error(arg2.toString()+" "+values[0]+" 不能转换成时间类型，时间类型支持 yyyy-MM-dd");
            	return null;
			}   
        }
        Pattern pattern1 = Pattern.compile("yyyy-MM-dd HH:mm:ss");   
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(pattern.matcher(values[0]).find())   
        {   
            try {   
                return dateFormat.parse(values[0]);   
            } catch (ParseException e) {
				log.error(arg2.toString()+" "+values[0]+" 不能转换成时间类型，时间类型支持 yyyy-MM-dd HH:mm:ss");
            	return null;
			}   
        }
        log.error(arg2.toString()+" "+values[0]+" 不能转换成时间类型，时间类型支持yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss");
        return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@Override
	public String convertToString(Map arg0, Object arg1) {
		if (Date.class.isInstance(arg1)) {
			return Tools.getTimeStyle((Date)arg1);
		}else
		{
			return "";
		}
	}

}
