/**
 * 	@(#)FileUtil.java   2006-9-12 ����02:49:09
 *	 �� 
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
	 * �����ļ���,����ļ��д��ڲ�����,���������,����
	 * 
	 * @param filePath
	 *            �ļ���·��
	 */
	public static void createNewDirectory(String filePath) {
		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	/**
	 * ��ָ��Ŀ¼�´����µ��ļ�,����ļ�����,ɾ���ļ��󴴽�,����ļ�������,�����µ��ļ�
	 * 
	 * @param filePath
	 *            �ļ���·��
	 * @param fileName
	 *            �ļ���
	 */
	public static void createNewFile(String filePath, String fileName) {
		// ���·���Ƿ����
		File path = new File(filePath);
		if (path.exists()) {
			log.debug("·���Ѿ�����,����Ҫ�ٴ���");
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
	 * ָ��·���µ�ָ���ļ��Ƿ�ɶ�
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
	 * ָ��·���µ�ָ���ļ��Ƿ��д
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
	 * ָ��·���µ�ָ���ļ��ǲ��������ļ�
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
	 * ָ��·���µ�ָ���ļ��ǲ����ļ�
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
	 * ָ��·���µ�ָ���ļ��ǲ���Ŀ¼
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
	 * ���ļ���д���ݣ�׷�ӻ��߸���
	 * 
	 * @param file
	 *            �ļ�ȫ·��
	 * @param value
	 *            Ҫд���ֵ
	 * @param flag
	 *            �Ƿ�׷��д��
	 * @throws Exception
	 */
	public static void writeToFile(String file, String value, boolean flag) {
		if (file.equals("") || file == null) {
			throw new NullPointerException("δ�����ļ���");
		} else {

			int i = file.lastIndexOf("/");
			String path = file.substring(0, i);
			String fileName = file.substring(i + 1, file.length());

			File f = new File(path + "/" + fileName);
			if (!f.exists()) {
				createNewFile(path, fileName);
			}

			// ����һ�����������
			BufferedWriter bw = null;
			// ����һ������̨����������,��̬д����Ϣ��
			BufferedReader br = null;
			try {
				// �����ļ�����ַ���,�ڶ�����Ϊtrue�����ļ�׷����Ϣ����˼
				FileWriter fw = new FileWriter(file, flag);
				bw = new BufferedWriter(fw);
				// �ӿ���̨������Ϣ
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
	 * �õ������ļ����б����Ϣ
	 * 
	 * @param filePath
	 *            �ļ�������
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
		String path = "C:\\Documents and Settings\\Administrator\\����\\moonIvr";
		// String file = "1.txt";
		// String pathfile = "D:/voicemail/1.txt";
		// FileUtil.createNewFile(path, file);
		// FileUtil.createNewFile("D:/voicemail", "");
		// System.out
		// .println(theFileIsRead(pathfile) == false ? "���ɶ���" : "�ǿ��Զ�ȡ��");
		// System.out.println(theFileIsWrite(path + "/" + file) == false ?
		// "����д��"
		// : "�ǿ���д��");
		// System.out.println(theFileIsHidden(path + "/" + file) == false ?
		// "�����ص�"
		// : "�����ص�");
		// System.out.println(theFileIsHidden(path + "/" + file) == false ?
		// "�����ص�"
		// : "�����ص�");

		FileUtil fu = new FileUtil();
//		fu.writeToFile(path, "1111111111111", true);
		
		Iterator it = fu.getListByPath(path).iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}
