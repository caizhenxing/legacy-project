/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-21
 */
package et.test.staticTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhang feng
 * 
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "AGENTSIGN:{0};";
		
		String RULER_REGEX = "\\w+\\:{1}.*;{1}";
		Pattern pattern = Pattern.compile(RULER_REGEX);
		Matcher matcher = pattern.matcher(str);
		boolean b = matcher.matches();
		// ����������ʱ��������true�����򷵻�false
		System.out.println(b);

	}

}
