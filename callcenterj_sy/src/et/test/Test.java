package et.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Test {
	private static Log log = LogFactory.getLog(Test.class);
	/**
	 * @param args
	 */
	public void test()
	{
 
	}
	public static void main(String[] args) {
		Map m = new HashMap();
		m.put("001", "123");
		m.put("001", "大家");
		
		m.containsKey("001");
		
		System.out.println(m.size());
		System.out.println(formatLongToTimeStr(3601000L));
	}
	
	public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (hour + "小时" + minute  + "分钟"
                + second  + "秒");
	 }
}
