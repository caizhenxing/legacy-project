package et.bo.word;

/*************************************
 *
 *���ã�����jacob�������ģ��word����word �ļ���
 *
 *��������ΪHashMap���󣬶����е�Key����wordģ����Ҫ�滻���ֶΣ�Value���������滻��ֵ��
 * wordģ��������Ҫ�滻���ֶΣ���HashMap�е�Key���������ַ���ͷ�ͽ�β���磺$code$��$date$����������ִ�д�����滻��
 * ����Ҫ�滻ΪͼƬ���ֶΣ�Key�������image����ValueΪͼƬ��ȫ·����Ŀǰֻ�ж��ļ���׺��Ϊ��.bmp��.jpg��.gif����
 * Ҫ�滻����е�����ʱ��HashMap�е�Key��ʽΪ��table$R@N�������У�R����ӱ��ĵ�R�п�ʼ�滻��N����wordģ���еĵ�N�ű��
 * ValueΪArrayList����ArrayList�а����Ķ���ͳһΪString[]��һ��String[]����һ�����ݣ�ArrayList�е�һ����¼Ϊ�����¼��
 * ��¼���Ǳ����Ҫ�滻���кţ��磺Ҫ�滻��һ�С������С������е����ݣ����һ����¼ΪString[3] {��1��,��3��,��5��}��
 *
 *
 *create on 2007.3.6
 *author fgl
 *
 *
 ************************************/

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Java2Word {
	private boolean saveOnExit;

	/**
	 * word�ĵ�
	 */
	private Dispatch doc = null;

	/**
	 * word���г������
	 */
	private ActiveXComponent word;

	/**
	 * ����word�ĵ�
	 */
	private Dispatch documents;

	/**
	 * ���캯��
	 */
	public Java2Word() {
		saveOnExit = false;
		word = new ActiveXComponent("Word.Application");
		word.setProperty("Visible", new Variant(false));
		documents = word.getProperty("Documents").toDispatch();
	}

	/**
	 * ���ò������˳�ʱ�Ƿ񱣴�
	 * 
	 * @param saveOnExit
	 *            true-�˳�ʱ�����ļ���false-�˳�ʱ�������ļ�
	 */
	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	/**
	 * �õ��������˳�ʱ�Ƿ񱣴�
	 * 
	 * @return boolean true-�˳�ʱ�����ļ���false-�˳�ʱ�������ļ�
	 */
	public boolean getSaveOnExit() {
		return saveOnExit;
	}

	/**
	 * ���ļ�
	 * 
	 * @param inputDoc
	 *            Ҫ�򿪵��ļ���ȫ·��
	 * @return Dispatch �򿪵��ļ�
	 */
	public Dispatch open(String inputDoc) {
		return Dispatch.call(documents, "Open", inputDoc).toDispatch();
	}

	/**
	 * ѡ������
	 * 
	 * @return Dispatch ѡ���ķ�Χ������
	 */
	public Dispatch select() {
		return word.getProperty("Selection").toDispatch();
		// selection = Dispatch.get(msWordApp, "Selection").toDispatch();
	}

	public Dispatch mySelect() {
		return Dispatch.get(word, "Selection").toDispatch();
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * 
	 * @param selection
	 *            Ҫ�ƶ�������
	 * @param count
	 *            �ƶ��ľ���
	 */
	public void moveUp(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveUp");
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * 
	 * @param selection
	 *            Ҫ�ƶ�������
	 * @param count
	 *            �ƶ��ľ���
	 */
	public void moveDown(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * 
	 * @param selection
	 *            Ҫ�ƶ�������
	 * @param count
	 *            �ƶ��ľ���
	 */
	public void moveLeft(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveLeft");
	}

	/**
	 * ��ѡ�����ݻ����������ƶ�
	 * 
	 * @param selection
	 *            Ҫ�ƶ�������
	 * @param count
	 *            �ƶ��ľ���
	 */
	public void moveRight(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveRight");
	}

	/**
	 * �Ѳ�����ƶ����ļ���λ��
	 * 
	 * @param selection
	 *            �����
	 */
	public void moveStart(Dispatch selection) {
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	/**
	 * ��ѡ�����ݻ����㿪ʼ�����ı�
	 * 
	 * @param selection
	 *            ѡ������
	 * @param toFindText
	 *            Ҫ���ҵ��ı�
	 * @return boolean true-���ҵ���ѡ�и��ı���false-δ���ҵ��ı�
	 */
	public boolean find(Dispatch selection, String toFindText) {
		// ��selection����λ�ÿ�ʼ��ѯ
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		// ����Ҫ���ҵ�����
		Dispatch.put(find, "Text", toFindText);
		// ��ǰ����
		Dispatch.put(find, "Forward", "True");
		// ���ø�ʽ
		Dispatch.put(find, "Format", "True");
		// ��Сдƥ��
		Dispatch.put(find, "MatchCase", "True");
		// ȫ��ƥ��
		Dispatch.put(find, "MatchWholeWord", "True");
		// ���Ҳ�ѡ��
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * ��ѡ�������滻Ϊ�趨�ı�
	 * 
	 * @param selection
	 *            ѡ������
	 * @param newText
	 *            �滻Ϊ�ı�
	 */
	public void replace(Dispatch selection, String newText) {
		// �����滻�ı�
		Dispatch.put(selection, "Text", newText);
	}

	/**
	 * ȫ���滻
	 * 
	 * @param selection
	 *            ѡ�����ݻ���ʼ�����
	 * @param oldText
	 *            Ҫ�滻���ı�
	 * @param newText
	 *            �滻Ϊ�ı�
	 */
	public void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
		// �ƶ����ļ���ͷ
		moveStart(selection);
		if (oldText.startsWith("table") || replaceObj instanceof List) {
			replaceTable(selection, oldText, (List) replaceObj);
		} else {
			String newText = (String) replaceObj;
			if (oldText.indexOf("image") != -1
					|| newText.lastIndexOf(".bmp") != -1
					|| newText.lastIndexOf(".jpg") != -1
					|| newText.lastIndexOf(".gif") != -1)
				while (find(selection, oldText)) {
					replaceImage(selection, newText);
					Dispatch.call(selection, "MoveRight");
				}
			else
				while (find(selection, oldText)) {
					replace(selection, newText);
					Dispatch.call(selection, "MoveRight");
				}
		}
	}

	/**
	 * �滻ͼƬ
	 * 
	 * @param selection
	 *            ͼƬ�Ĳ����
	 * @param imagePath
	 *            ͼƬ�ļ���ȫ·����
	 */
	public void replaceImage(Dispatch selection, String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath);
	}

	/**
	 * �滻���
	 * 
	 * @param selection
	 *            �����
	 * @param tableName
	 *            ������ƣ�����table$1@1��table$2@1...table$R@N��R����ӱ���еĵ�N�п�ʼ��䣬
	 *            N����word�ļ��еĵ�N�ű�
	 * @param fields
	 *            �����Ҫ�滻���ֶ������ݵĶ�Ӧ��
	 */
	public void replaceTable(Dispatch selection, String tableName, List dataList) {
		if (dataList.size() <= 1) {
//			System.out.println("Empty table!");
			return;
		}
		// Ҫ������
		String[] cols = (String[]) dataList.get(0);
		// ������
		String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
		// �ӵڼ��п�ʼ���
		int fromRow = Integer.parseInt(tableName.substring(tableName
				.lastIndexOf("$") + 1, tableName.lastIndexOf("@")));
		// ���б��
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// Ҫ���ı��
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tbIndex))
				.toDispatch();
		// ����������
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		// �����
		for (int i = 1; i < dataList.size(); i++) {
			// ĳһ������
			String[] datas = (String[]) dataList.get(i);
			// �ڱ�������һ��
			if (Dispatch.get(rows, "Count").getInt() < fromRow + i - 1)
				Dispatch.call(rows, "Add");
			// �����е������
			for (int j = 0; j < datas.length; j++) {
				// �õ���Ԫ��
				Dispatch cell = Dispatch.call(table, "Cell",
						Integer.toString(fromRow + i - 1), cols[j])
						.toDispatch();
				// ѡ�е�Ԫ��
				Dispatch.call(cell, "Select");
				// ���ø�ʽ
				Dispatch font = Dispatch.get(selection, "Font").toDispatch();
				Dispatch.put(font, "Bold", "0");
				Dispatch.put(font, "Italic", "0");
				// ��������
				Dispatch.put(selection, "Text", datas[j]);
			}
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param outputPath
	 *            ����ļ�������·����
	 */
	public void save(String outputPath) {
		System.out.println("path#" + outputPath + "#" + word);
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(),
				"FileSaveAs", outputPath);
	}

	/**
	 * �ر��ļ�
	 * 
	 * @param document
	 *            Ҫ�رյ��ļ�
	 */
	public void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(saveOnExit));
	}

	/**
	 * �˳�����
	 */
	public void quit() {
		word.invoke("Quit", new Variant[0]);
		ComThread.Release();
	}

	/**
	 * ����ģ�塢��������word�ļ�
	 * 
	 * @param inputPath
	 *            ģ���ļ�������·����
	 * @param outPath
	 *            ����ļ�������·����
	 * @param badyData
	 *            �ĵ��������ݰ�������Ҫ�����ֶΡ���Ӧ�����ݣ�
	 * @param headerData
	 *            ҳͷ�������ݰ�������Ҫ�����ֶΡ���Ӧ�����ݣ�
	 */
	public void toWord(String inputPath, String outPath, HashMap bodyData,
			HashMap headerData) {
		String oldText;
		Object newValue;
		// /D:/JavaTool/tomcat-5.5/webapps/callcenterj_sy/WEB-INF/classes/
		String path = (this.getClass()).getResource("/").getPath();
		if (path != null) {
			if (path.indexOf("/") == 0) {
				path = path.substring(1);
			}
			if (path.indexOf("WEB-INF") != -1) {
				path = path.substring(0, path.indexOf("WEB-INF"));
			}
		}

		inputPath = path + inputPath;
		outPath = path + outPath;
		// System.out.println(inputPath);
		// System.out.println(outPath);
		try {
			doc = open(inputPath);

			Dispatch selection = select();

			Iterator keys = bodyData.keySet().iterator();
			while (keys.hasNext()) {
				oldText = (String) keys.next();
				newValue = bodyData.get(oldText);
				replaceAll(selection, oldText, newValue);
			}
			this.moveStart(selection);
			// �滻ҳͷ
			toReplaceHeader(selection, headerData);

			save(outPath);
			System.out.println("####�滻�ɹ�!!");
		} catch (Exception e) {
			// debug.println("toword[Java2Word]------------����word�ļ�ʧ�ܣ�"+e.getMessage(),true);
			e.printStackTrace();
		} finally {
			if (doc != null)
				close(doc);
		}
	}

	/**
	 * ����ģ�塢��������word�ļ�
	 * 
	 * @param inputPath
	 *            ģ���ļ�������·����
	 * @param outPath
	 *            ����ļ�������·����
	 * @param data
	 *            ���ݰ�������Ҫ�����ֶΡ���Ӧ�����ݣ�
	 */
	public void toWord(String inputPath, String outPath, HashMap data) {
		String oldText;
		Object newValue;
		try {
			doc = open(inputPath);

			Dispatch selection = select();

			Iterator keys = data.keySet().iterator();
			while (keys.hasNext()) {
				oldText = (String) keys.next();
				newValue = data.get(oldText);
				replaceAll(selection, oldText, newValue);
			}
			this.moveStart(selection);
			// �滻ҳͷ
			toReplaceHeader(selection, data);

			save(outPath);
		} catch (Exception e) {
			// debug.println("toword[Java2Word]------------����word�ļ�ʧ�ܣ�"+e.getMessage(),true);

		} finally {
			if (doc != null)
				close(doc);
		}
	}

	/**
	 * �滻wordҳü
	 * 
	 * @param data
	 *            ���ݰ�������Ҫ�����ֶΡ���Ӧ�����ݣ�
	 */
	public void toReplaceHeader(Dispatch selection, HashMap data) {
		String oldText;
		Object newValue;
		// ����ҳ��ҳβ
		// ȡ�û�������
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		// ȡ�û�������
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// ȡ���Ӵ�����
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9������ҳ��(�α����ڴ�)
		Dispatch.put(view, "SeekView", "9");// ҳ���е���Ϣ
		Iterator keys = data.keySet().iterator();
		while (keys.hasNext()) {
			oldText = (String) keys.next();
			newValue = data.get(oldText);
			replaceAll(selection, oldText, newValue);
		}

	}

	/**
	 * ����ҳ��ҳβ
	 */
	public void setPageInfo(Dispatch selection) {
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		// ����ҳ��ҳβ
		// ȡ�û�������
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		// ȡ�û�������
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// ȡ���Ӵ�����
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9������ҳ��(�α����ڴ�)
		Dispatch.put(view, "SeekView", "9");// ҳ���е���Ϣ
		Dispatch.put(alignment, "Alignment", "2");
		Dispatch.put(selection, "Text", "��ũ���ߵ��Ͱ���");

		if (this.find(selection, "#date#")) {
			System.out.println("##################");
			System.out.println("##################");
			System.out.println("##################");
			// this.replace(selection, new java.util.Date().toString());
		}

		this.replace(selection, new java.util.Date().toString());
		// 10������ҳβ(�α����ڴ�)
		Dispatch.put(view, "SeekView", "10");// ҳβ�е���Ϣ
		Dispatch.put(alignment, "Alignment", "1");
		Dispatch.put(selection, "Text", "��ũ����  12316");
	}

	public void myFindChange(String fText, String changeV) {
		Dispatch selection = word.getProperty("Selection").toDispatch();// ��ö�Selection���
		Dispatch.call(selection, "HomeKey", new Variant(6));

		// Dispatch.call(arg0, arg1, arg2)
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();// ���Find���
		Dispatch.put(find, "Text", fText); // �����ַ���"name"
		Dispatch.call(find, "Execute"); // ִ�в�ѯ
		Dispatch.put(selection, "Text", changeV); // �滻Ϊ"����"

		Dispatch.call(word, "Save"); // ����
		Dispatch.call(word, "Close", new Variant(false));
	}

	/**
	 * ����ҳ��ҳβ
	 */
	public void setPageInfoTest(Dispatch selection) {
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		// ����ҳ��ҳβ
		// ȡ�û�������
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		// ȡ�û�������
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// ȡ���Ӵ�����
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9������ҳ��(�α����ڴ�)
		Dispatch.put(view, "SeekView", "9");// ҳ���е���Ϣ
		Dispatch.put(alignment, "Alignment", "2");
		Dispatch.put(selection, "Text", "��ũ���ߵ��Ͱ���");
		System.out.println("#####" + this.find(selection, "#date#"));
		if (this.find(selection, "#date#")) {
			System.out.println("##################");
			System.out.println("##################");
			System.out.println("##################");
			// this.replace(selection, new java.util.Date().toString());
		}

		this.replace(selection, new java.util.Date().toString());
		// 10������ҳβ(�α����ڴ�)
		Dispatch.put(view, "SeekView", "10");// ҳβ�е���Ϣ
		Dispatch.put(alignment, "Alignment", "1");
		Dispatch.put(selection, "Text", "��ũ����  12316");
	}

	public static void main(String[] args) {
		Java2Word jw = new Java2Word();
		String path = (jw.getClass()).getResource(".").getPath();
		if (path != null) {
			if (path.indexOf("/") == 0) {
				path = path.substring(1);
			}
			if (path.indexOf("WebRoot") != -1) {
				path = path.substring(0, path.indexOf("WEB-INF"));
			}
		}
		String inFilePath = path + "wordTemplet/marAnalysis.doc";
		System.out.println(inFilePath);
		String outFilePath = path + "wordTemplet/test1.doc";
		HashMap<String, String> reMap = new HashMap<String, String>();
		reMap.put("#date#", "####СȨ####");
		reMap.put("#date1#", "####СȨ1####");
		reMap.put("#date2#", "####СȨ2####");
		reMap.put("#date3#", "####СȨ3####");
		reMap.put("#header#", "ҳͷҳͷҳͷ");
		reMap
				.put(
						"@date3@",
						"Ϊ8.00Ԫ/�������Ϫ������ͼ�ȵ�������ͼ�Ϊ7.20Ԫ/������ڿ�ƽ�ȵ�����ȫʡ����ƽ�����ۼ۸�Ϊ25.61Ԫ/���������1.47%����߼�Ϊ30.00Ԫ/������ڲ�ͼ����ɽ�ȵ�������ͼ�Ϊ20.00Ԫ/��������ں顢���еȵ�����Ϊ8.00Ԫ/�������Ϫ������ͼ�ȵ�������ͼ�Ϊ7.20Ԫ/������ڿ�ƽ�ȵ�����ȫʡ����ƽ�����ۼ۸�Ϊ25.61Ԫ/���������1.47%����߼�Ϊ30.00Ԫ/������ڲ�ͼ����ɽ�ȵ�������ͼ�Ϊ20.00Ԫ/��������ں顢���еȵ�����Ϊ8.00Ԫ/�������Ϫ������ͼ�ȵ�������ͼ�Ϊ7.20Ԫ/������ڿ�ƽ�ȵ�����ȫʡ����ƽ�����ۼ۸�Ϊ25.61Ԫ/���������1.47%����߼�Ϊ30.00Ԫ/������ڲ�ͼ����ɽ�ȵ�������ͼ�Ϊ20.00Ԫ/��������ں顢���еȵ�����");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("2008��6��16��", "AAAAAAAAA");
		headerMap.put("#header#", "����ҳͷ����ҳͷ");

		jw.toWord(inFilePath, outFilePath, reMap, headerMap);
		// jw.open((jw.getClass()).getResource(".");
		// String path = (jw.getClass()).getResource(".").getPath();
		// if(path!=null)
		// {
		// if(path.indexOf("/")==0)
		// {
		// path = path.substring(1);
		// }
		// if(path.indexOf("WebRoot")!=-1)
		// {
		// path = path.substring(0,path.indexOf("WEB-INF"));
		// }
		// }
		// String wordFilePath = path + "wordSample/test.doc";
		// Dispatch wd = jw.open(wordFilePath);
		// Dispatch s = jw.mySelect();
		// System.out.println(jw.find(s, "#data#"));
		//		
		// //jw.setPageInfo(select);
		// jw.myFindChange("#date#", "@@@@@@@@@@@@@#########");
		// jw.setSaveOnExit(true);
		// jw.close(wd);
	}
}