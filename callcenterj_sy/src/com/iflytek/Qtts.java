package com.iflytek;

public class Qtts {
	static {
		System.loadLibrary("QuickTTS");
	}

	public native long QuickTTSSynth(byte[] text, boolean is_file,
			byte[] output, byte[] serverIP, byte[] svcid, byte[] param);

	/**
	 * @param text
	 *            输入的文本，可以由下一变量指定是文件还是直接文本
	 * @param is_file
	 *            指明上一个变量是否为文件名，否则认为是文本
	 * @param output
	 *            输出的语音文件路径（如C:\xxx.wav）
	 * @param serverIP
	 *            指定服务器地址和端口号，形式如(192.168.1.1:10344) 可以不指定，由ISP自动寻找。
	 * @param svcid
	 *            指定合成服务的ID，如ce30/intp50之类，一般可为空。
	 * @param param
	 *            以“参数类型＝参数值的形式 ”传入参数配置， 如“1＝1
	 *            2＝3”,具体参数类型和参数值可以参考开发文档或iFlyTTS.h的定义。
	 * @return 如果合成成功，返回TTSERR_OK，否则返回错误代码，具体可以 参考TTSERRCODE.h文件
	 */
	public long synthesize(String text, boolean is_file, String output,
			String serverIP, String svcid, String param) {
		return QuickTTSSynth(text.getBytes(), is_file, output.getBytes(),
				serverIP.getBytes(), svcid.getBytes(), param.getBytes());
	}
}
