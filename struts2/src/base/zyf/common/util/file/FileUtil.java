/**
 * 	@(#)FileUtil.java   2006-9-12 下午02:49:09
 *	 。 
 *	 
 */
package base.zyf.common.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhangfeng
 * @version 2008-03-11
 * @see
 */
public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * 创建文件夹,如果文件夹存在不处理,如果不存在,创建
	 * 
	 * @param filePath
	 *            文件夹路径
	 */
	public static void createNewDirectory(String filePath) {
		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	/**
	 * 在指定目录下创建新的文件,如果文件存在,删除文件后创建,如果文件不存在,创建新的文件
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param fileName
	 *            文件名
	 */
	public static void createNewFile(String filePath, String fileName) {
		// 检查路径是否存在
		File path = new File(filePath);
		if (path.exists()) {
			log.debug("路径已经存在,不需要再创建");
		} else {
			path.mkdir();
		}
		try {
			if (!fileName.equals("")) {
				File file = new File(filePath, fileName);
				if (file.exists()) {
					file.delete();
					file.createNewFile();
				} else {
					file.createNewFile();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定路径下的指定文件是否可读
	 * 
	 * @param pathFile
	 * @return
	 */
	public static boolean theFileIsRead(String pathFile) {
		boolean flag = false;
		if (!pathFile.equals("")) {
			File path = new File(pathFile);
			if (path.canRead()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 指定路径下的指定文件是否可写
	 * 
	 * @param pathFile
	 * @return
	 */
	public static boolean theFileIsWrite(String pathFile) {
		boolean flag = false;
		if (!pathFile.equals("")) {
			File path = new File(pathFile);
			if (path.canWrite()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 指定路径下的指定文件是不是隐藏文件
	 * 
	 * @param pathFile
	 * @return
	 */
	public static boolean theFileIsHidden(String pathFile) {
		boolean flag = false;
		if (!pathFile.equals("")) {
			File path = new File(pathFile);
			if (path.isHidden()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 指定路径下的指定文件是不是文件
	 * 
	 * @param pathFile
	 * @return
	 */
	public static boolean theFileIsFile(String pathFile) {
		boolean flag = false;
		if (!pathFile.equals("")) {
			File path = new File(pathFile);
			if (path.isFile()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 指定路径下的指定文件是不是目录
	 * 
	 * @param pathFile
	 * @return
	 */
	public static boolean theFileIsDirectory(String pathFile) {
		boolean flag = false;
		if (!pathFile.equals("")) {
			File path = new File(pathFile);
			if (path.isDirectory()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 向文件中写数据，追加或者覆盖
	 * 
	 * @param file
	 *            文件全路径
	 * @param value
	 *            要写入的值
	 * @param flag
	 *            是否追加写入
	 * @throws Exception
	 */
	public static void writeToFile(String file, String value, boolean flag) {
		if (file.equals("") || file == null) {
			throw new NullPointerException("未输入文件名");
		} else {

			int i = file.lastIndexOf("/");
			String path = file.substring(0, i);
			String fileName = file.substring(i + 1, file.length());

			File f = new File(path + "/" + fileName);
			if (!f.exists()) {
				createNewFile(path, fileName);
			}

			// 声明一个缓冲输出流
			BufferedWriter bw = null;
			// 声明一个控制台缓冲输入流,动态写入信息。
			BufferedReader br = null;
			try {
				// 构造文件输出字符流,第二参数为true是向文件追加信息的意思
				FileWriter fw = new FileWriter(file, flag);
				bw = new BufferedWriter(fw);
				// 从控制台输入信息
				InputStreamReader isr = new InputStreamReader(System.in);
				br = new BufferedReader(isr);
				bw.write(value);
				bw.newLine();
				bw.flush();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (bw != null) {
						bw.close();
					}
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
	}

	/**
	 * 得到所有文件的列表的信息
	 * 
	 * @param filePath
	 *            文件夹名称
	 * @return List
	 */
	public static List getListByPath(String filePath) {
		List l = new ArrayList();
		File path = new File(filePath);
		String[] str = path.list();
		for (int i = 0; i < str.length; i++) {
			l.add(str[i]);
		}
		return l;
	}

	public static void main(String[] arg0) {
		String path = "C:\\Documents and Settings\\Administrator\\桌面\\moonIvr";
		// String file = "1.txt";
		// String pathfile = "D:/voicemail/1.txt";
		// FileUtil.createNewFile(path, file);
		// FileUtil.createNewFile("D:/voicemail", "");
		// System.out
		// .println(theFileIsRead(pathfile) == false ? "不可读的" : "是可以读取的");
		// System.out.println(theFileIsWrite(path + "/" + file) == false ?
		// "不可写的"
		// : "是可以写的");
		// System.out.println(theFileIsHidden(path + "/" + file) == false ?
		// "不隐藏的"
		// : "是隐藏的");
		// System.out.println(theFileIsHidden(path + "/" + file) == false ?
		// "不隐藏的"
		// : "是隐藏的");

		FileUtil fu = new FileUtil();
//		fu.writeToFile(path, "1111111111111", true);
		
		Iterator it = fu.getListByPath(path).iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}
