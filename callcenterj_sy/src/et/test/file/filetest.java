/**
 * 沈阳卓越科技有限公司
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
		//生成指定文件夹下的音频文件
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
		//删除指定文件下的音频文件
		String delstr="D:\\TTS\\zhuanjiazixun\\20080621_185932_484.wav";
		File f4=new File(delstr);
		if(f4.exists()){
			f4.delete();
		}
	}

}
