package com.iflytek;

public class Qtts {
	static {
		System.loadLibrary("QuickTTS");
	}

	public native long QuickTTSSynth(byte[] text, boolean is_file,
			byte[] output, byte[] serverIP, byte[] svcid, byte[] param);

	/**
	 * @param text
	 *            ������ı�����������һ����ָ�����ļ�����ֱ���ı�
	 * @param is_file
	 *            ָ����һ�������Ƿ�Ϊ�ļ�����������Ϊ���ı�
	 * @param output
	 *            ����������ļ�·������C:\xxx.wav��
	 * @param serverIP
	 *            ָ����������ַ�Ͷ˿ںţ���ʽ��(192.168.1.1:10344) ���Բ�ָ������ISP�Զ�Ѱ�ҡ�
	 * @param svcid
	 *            ָ���ϳɷ����ID����ce30/intp50֮�࣬һ���Ϊ�ա�
	 * @param param
	 *            �ԡ��������ͣ�����ֵ����ʽ ������������ã� �硰1��1
	 *            2��3��,����������ͺͲ���ֵ���Բο������ĵ���iFlyTTS.h�Ķ��塣
	 * @return ����ϳɳɹ�������TTSERR_OK�����򷵻ش�����룬������� �ο�TTSERRCODE.h�ļ�
	 */
	public long synthesize(String text, boolean is_file, String output,
			String serverIP, String svcid, String param) {
		return QuickTTSSynth(text.getBytes(), is_file, output.getBytes(),
				serverIP.getBytes(), svcid.getBytes(), param.getBytes());
	}
}
