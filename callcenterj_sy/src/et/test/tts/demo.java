package et.test.tts;

import java.util.Date;

import com.iflytek.Qtts;

import excellence.common.util.time.TimeUtil;

public class demo {
	public String playVoice(String voiceStr, String ip)
	{
		Qtts qttsobj = new Qtts();
		TimeUtil tu = new TimeUtil();
		String str = tu.getTheTimeStr(new Date(), "yyyyMMddHHmmss");
		str = "Z:/" + str.trim() + ".wav";
		System.out.println(str);
		qttsobj.synthesize(voiceStr, false, str, ip,
				"", "1=3");
		System.out.println("ִ�гɹ�...");
		System.out.println("the end time "+tu.getTheTimeStr(new Date(), "yyyyMMddHHmmss"));
		return str;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Qtts qttsobj = new Qtts();
		TimeUtil tu = new TimeUtil();
		String str = tu.getTheTimeStr(new Date(), "yyyyMMddHHmmss");
		str = "Z:\\" + str + ".wav";
		System.out.println(str);
		qttsobj.synthesize("��ð�����һ���������Եĳ��򣬿�����ô��", false, str, "192.168.1.140",
				"", "1=3");
		System.out.println("ִ�гɹ�...");
		System.out.println("the end time "+tu.getTheTimeStr(new Date(), "yyyyMMddHHmmss"));
	}

}
