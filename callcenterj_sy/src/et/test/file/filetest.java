/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-6-21
 */
package et.test.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author zhang feng
 *
 */
public class filetest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb=new StringBuffer();
		//����ָ���ļ����µ���Ƶ�ļ�
		sb.append("d:\\TTS");
		File f1=new File(sb.toString());
		if(!f1.exists()){
			f1.mkdir();
		}
		sb.append("\\zhuanjiazixun");
		File f2=new File(sb.toString());
		if(!f2.exists()){
			f2.mkdir();
		}
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
		sb.append("\\");
		sb.append(sdf.format(date));
		sb.append(".wav");
		File f3=new File(sb.toString());
		if(!f3.exists()){
			try {
				f3.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//ɾ��ָ���ļ��µ���Ƶ�ļ�
		String delstr="D:\\TTS\\zhuanjiazixun\\20080621_185932_484.wav";
		File f4=new File(delstr);
		if(f4.exists()){
			f4.delete();
		}
	}

}
