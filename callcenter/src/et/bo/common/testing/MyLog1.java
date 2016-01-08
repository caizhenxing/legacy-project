package et.bo.common.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import et.bo.common.ConstantsCommonI;
import excellence.common.util.file.FileUtil;

public class MyLog1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyLog1.info("hello world!");

	}
	public static void info(String s){
		if(!ConstantsCommonI.TEST_STATE1)return;
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
		fu.writeToFile(ConstantsCommonI.TEST_FILE1, 
				sb.toString(), 
				true);
	}
}
