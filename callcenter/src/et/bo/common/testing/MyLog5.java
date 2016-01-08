/**
 * 
 */
package et.bo.common.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import et.bo.common.ConstantsCommonI;
import excellence.common.util.file.FileUtil;

/**
 * @author Administrator
 *
 */
public class MyLog5 {

public static void info(String s){
		
		if(!ConstantsCommonI.TEST_STATE5)return;
		FileUtil fu=new FileUtil();
		StringBuffer sb=new StringBuffer();
		sb.append(ConstantsCommonI.TEST_LINE);
		sb.append(ConstantsCommonI.TEST_RETURN);
		sb.append(ConstantsCommonI.TEST_DELIM1);
		//time.
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		sb.append(sdf.format(d));
		//
		sb.append(ConstantsCommonI.TEST_DELIM2);
		sb.append(ConstantsCommonI.TEST_DELIM3);
		sb.append(s);
		sb.append(ConstantsCommonI.TEST_RETURN);
		sb.append(ConstantsCommonI.TEST_LINE);
		sb.append(ConstantsCommonI.TEST_RETURN);
		fu.writeToFile(ConstantsCommonI.TEST_FILE5, 
				sb.toString(), 
				true);
		System.out.println(sb.toString());
	}
}
