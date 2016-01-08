package et.bo.oa.assissant.document.config;

/**
 * 取得文件的扩展名
 * @author zhangfeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
import java.io.File;

public class GetFileName {

	/**
	 * Return the extension portion of the file's name .
	 * 
	 * @see #getExtension
	 */
	public static String getExtension(File f) {
		return (f != null) ? getExtension(f.getName()) : "";
	}

	public static String getExtension(String filename) {
		return getExtension(filename, "");
	}

	public static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

	public static String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "http://images.sohu.com/cs/mms/128128/99349.jpg";
//		String s = "1.19";
		
		
		
	}

}
