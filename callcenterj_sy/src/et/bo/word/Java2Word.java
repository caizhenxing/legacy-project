package et.bo.word;

/*************************************
 *
 *作用：利用jacob插件根据模板word生成word 文件！
 *
 *传入数据为HashMap对象，对象中的Key代表word模板中要替换的字段，Value代表用来替换的值。
 * word模板中所有要替换的字段（即HashMap中的Key）以特殊字符开头和结尾，如：$code$、$date$……，以免执行错误的替换。
 * 所有要替换为图片的字段，Key中需包含image或者Value为图片的全路径（目前只判断文件后缀名为：.bmp、.jpg、.gif）。
 * 要替换表格中的数据时，HashMap中的Key格式为“table$R@N”，其中：R代表从表格的第R行开始替换，N代表word模板中的第N张表格；
 * Value为ArrayList对象，ArrayList中包含的对象统一为String[]，一条String[]代表一行数据，ArrayList中第一条记录为特殊记录，
 * 记录的是表格中要替换的列号，如：要替换第一列、第三列、第五列的数据，则第一条记录为String[3] {“1”,”3”,”5”}。
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
	 * word文档
	 */
	private Dispatch doc = null;

	/**
	 * word运行程序对象
	 */
	private ActiveXComponent word;

	/**
	 * 所有word文档
	 */
	private Dispatch documents;

	/**
	 * 构造函数
	 */
	public Java2Word() {
		saveOnExit = false;
		word = new ActiveXComponent("Word.Application");
		word.setProperty("Visible", new Variant(false));
		documents = word.getProperty("Documents").toDispatch();
	}

	/**
	 * 设置参数：退出时是否保存
	 * 
	 * @param saveOnExit
	 *            true-退出时保存文件，false-退出时不保存文件
	 */
	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	/**
	 * 得到参数：退出时是否保存
	 * 
	 * @return boolean true-退出时保存文件，false-退出时不保存文件
	 */
	public boolean getSaveOnExit() {
		return saveOnExit;
	}

	/**
	 * 打开文件
	 * 
	 * @param inputDoc
	 *            要打开的文件，全路径
	 * @return Dispatch 打开的文件
	 */
	public Dispatch open(String inputDoc) {
		return Dispatch.call(documents, "Open", inputDoc).toDispatch();
	}

	/**
	 * 选定内容
	 * 
	 * @return Dispatch 选定的范围或插入点
	 */
	public Dispatch select() {
		return word.getProperty("Selection").toDispatch();
		// selection = Dispatch.get(msWordApp, "Selection").toDispatch();
	}

	public Dispatch mySelect() {
		return Dispatch.get(word, "Selection").toDispatch();
	}

	/**
	 * 把选定内容或插入点向上移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveUp(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveUp");
	}

	/**
	 * 把选定内容或插入点向下移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveDown(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/**
	 * 把选定内容或插入点向左移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveLeft(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveLeft");
	}

	/**
	 * 把选定内容或插入点向右移动
	 * 
	 * @param selection
	 *            要移动的内容
	 * @param count
	 *            移动的距离
	 */
	public void moveRight(Dispatch selection, int count) {
		for (int i = 0; i < count; i++)
			Dispatch.call(selection, "MoveRight");
	}

	/**
	 * 把插入点移动到文件首位置
	 * 
	 * @param selection
	 *            插入点
	 */
	public void moveStart(Dispatch selection) {
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	/**
	 * 从选定内容或插入点开始查找文本
	 * 
	 * @param selection
	 *            选定内容
	 * @param toFindText
	 *            要查找的文本
	 * @return boolean true-查找到并选中该文本，false-未查找到文本
	 */
	public boolean find(Dispatch selection, String toFindText) {
		// 从selection所在位置开始查询
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		// 设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
		// 向前查找
		Dispatch.put(find, "Forward", "True");
		// 设置格式
		Dispatch.put(find, "Format", "True");
		// 大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		// 全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		// 查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 把选定内容替换为设定文本
	 * 
	 * @param selection
	 *            选定内容
	 * @param newText
	 *            替换为文本
	 */
	public void replace(Dispatch selection, String newText) {
		// 设置替换文本
		Dispatch.put(selection, "Text", newText);
	}

	/**
	 * 全局替换
	 * 
	 * @param selection
	 *            选定内容或起始插入点
	 * @param oldText
	 *            要替换的文本
	 * @param newText
	 *            替换为文本
	 */
	public void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
		// 移动到文件开头
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
	 * 替换图片
	 * 
	 * @param selection
	 *            图片的插入点
	 * @param imagePath
	 *            图片文件（全路径）
	 */
	public void replaceImage(Dispatch selection, String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath);
	}

	/**
	 * 替换表格
	 * 
	 * @param selection
	 *            插入点
	 * @param tableName
	 *            表格名称，形如table$1@1、table$2@1...table$R@N，R代表从表格中的第N行开始填充，
	 *            N代表word文件中的第N张表
	 * @param fields
	 *            表格中要替换的字段与数据的对应表
	 */
	public void replaceTable(Dispatch selection, String tableName, List dataList) {
		if (dataList.size() <= 1) {
//			System.out.println("Empty table!");
			return;
		}
		// 要填充的列
		String[] cols = (String[]) dataList.get(0);
		// 表格序号
		String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
		// 从第几行开始填充
		int fromRow = Integer.parseInt(tableName.substring(tableName
				.lastIndexOf("$") + 1, tableName.lastIndexOf("@")));
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tbIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		// 填充表格
		for (int i = 1; i < dataList.size(); i++) {
			// 某一行数据
			String[] datas = (String[]) dataList.get(i);
			// 在表格中添加一行
			if (Dispatch.get(rows, "Count").getInt() < fromRow + i - 1)
				Dispatch.call(rows, "Add");
			// 填充该行的相关列
			for (int j = 0; j < datas.length; j++) {
				// 得到单元格
				Dispatch cell = Dispatch.call(table, "Cell",
						Integer.toString(fromRow + i - 1), cols[j])
						.toDispatch();
				// 选中单元格
				Dispatch.call(cell, "Select");
				// 设置格式
				Dispatch font = Dispatch.get(selection, "Font").toDispatch();
				Dispatch.put(font, "Bold", "0");
				Dispatch.put(font, "Italic", "0");
				// 输入数据
				Dispatch.put(selection, "Text", datas[j]);
			}
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param outputPath
	 *            输出文件（包含路径）
	 */
	public void save(String outputPath) {
		System.out.println("path#" + outputPath + "#" + word);
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(),
				"FileSaveAs", outputPath);
	}

	/**
	 * 关闭文件
	 * 
	 * @param document
	 *            要关闭的文件
	 */
	public void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(saveOnExit));
	}

	/**
	 * 退出程序
	 */
	public void quit() {
		word.invoke("Quit", new Variant[0]);
		ComThread.Release();
	}

	/**
	 * 根据模板、数据生成word文件
	 * 
	 * @param inputPath
	 *            模板文件（包含路径）
	 * @param outPath
	 *            输出文件（包含路径）
	 * @param badyData
	 *            文档内容数据包（包含要填充的字段、对应的数据）
	 * @param headerData
	 *            页头内容数据包（包含要填充的字段、对应的数据）
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
			// 替换页头
			toReplaceHeader(selection, headerData);

			save(outPath);
			System.out.println("####替换成功!!");
		} catch (Exception e) {
			// debug.println("toword[Java2Word]------------操作word文件失败！"+e.getMessage(),true);
			e.printStackTrace();
		} finally {
			if (doc != null)
				close(doc);
		}
	}

	/**
	 * 根据模板、数据生成word文件
	 * 
	 * @param inputPath
	 *            模板文件（包含路径）
	 * @param outPath
	 *            输出文件（包含路径）
	 * @param data
	 *            数据包（包含要填充的字段、对应的数据）
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
			// 替换页头
			toReplaceHeader(selection, data);

			save(outPath);
		} catch (Exception e) {
			// debug.println("toword[Java2Word]------------操作word文件失败！"+e.getMessage(),true);

		} finally {
			if (doc != null)
				close(doc);
		}
	}

	/**
	 * 替换word页眉
	 * 
	 * @param data
	 *            数据包（包含要填充的字段、对应的数据）
	 */
	public void toReplaceHeader(Dispatch selection, HashMap data) {
		String oldText;
		Object newValue;
		// 插入页首页尾
		// 取得活动窗体对象
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		// 取得活动窗格对象
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// 取得视窗对象
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9是设置页首(游标所在处)
		Dispatch.put(view, "SeekView", "9");// 页首中的信息
		Iterator keys = data.keySet().iterator();
		while (keys.hasNext()) {
			oldText = (String) keys.next();
			newValue = data.get(oldText);
			replaceAll(selection, oldText, newValue);
		}

	}

	/**
	 * 设置页首页尾
	 */
	public void setPageInfo(Dispatch selection) {
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		// 插入页首页尾
		// 取得活动窗体对象
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		// 取得活动窗格对象
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// 取得视窗对象
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9是设置页首(游标所在处)
		Dispatch.put(view, "SeekView", "9");// 页首中的信息
		Dispatch.put(alignment, "Alignment", "2");
		Dispatch.put(selection, "Text", "金农热线典型案例");

		if (this.find(selection, "#date#")) {
			System.out.println("##################");
			System.out.println("##################");
			System.out.println("##################");
			// this.replace(selection, new java.util.Date().toString());
		}

		this.replace(selection, new java.util.Date().toString());
		// 10是设置页尾(游标所在处)
		Dispatch.put(view, "SeekView", "10");// 页尾中的信息
		Dispatch.put(alignment, "Alignment", "1");
		Dispatch.put(selection, "Text", "金农热线  12316");
	}

	public void myFindChange(String fText, String changeV) {
		Dispatch selection = word.getProperty("Selection").toDispatch();// 获得对Selection组件
		Dispatch.call(selection, "HomeKey", new Variant(6));

		// Dispatch.call(arg0, arg1, arg2)
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();// 获得Find组件
		Dispatch.put(find, "Text", fText); // 查找字符串"name"
		Dispatch.call(find, "Execute"); // 执行查询
		Dispatch.put(selection, "Text", changeV); // 替换为"张三"

		Dispatch.call(word, "Save"); // 保存
		Dispatch.call(word, "Close", new Variant(false));
	}

	/**
	 * 设置页首页尾
	 */
	public void setPageInfoTest(Dispatch selection) {
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		// 插入页首页尾
		// 取得活动窗体对象
		Dispatch activeWindow = word.getProperty("ActiveWindow").toDispatch();
		// 取得活动窗格对象
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane")
				.toDispatch();
		// 取得视窗对象
		Dispatch view = Dispatch.get(activePane, "View").toDispatch();
		// 9是设置页首(游标所在处)
		Dispatch.put(view, "SeekView", "9");// 页首中的信息
		Dispatch.put(alignment, "Alignment", "2");
		Dispatch.put(selection, "Text", "金农热线典型案例");
		System.out.println("#####" + this.find(selection, "#date#"));
		if (this.find(selection, "#date#")) {
			System.out.println("##################");
			System.out.println("##################");
			System.out.println("##################");
			// this.replace(selection, new java.util.Date().toString());
		}

		this.replace(selection, new java.util.Date().toString());
		// 10是设置页尾(游标所在处)
		Dispatch.put(view, "SeekView", "10");// 页尾中的信息
		Dispatch.put(alignment, "Alignment", "1");
		Dispatch.put(selection, "Text", "金农热线  12316");
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
		reMap.put("#date#", "####小权####");
		reMap.put("#date1#", "####小权1####");
		reMap.put("#date2#", "####小权2####");
		reMap.put("#date3#", "####小权3####");
		reMap.put("#header#", "页头页头页头");
		reMap
				.put(
						"@date3@",
						"为8.00元/斤，出现在溪湖、昌图等地区；最低价为7.20元/斤，出现在康平等地区。全省仔猪平均零售价格为25.61元/斤，环比上升1.47%；最高价为30.00元/斤，出现在昌图、黑山等地区；最低价为20.00元/斤，出现在于洪、绥中等地区。为8.00元/斤，出现在溪湖、昌图等地区；最低价为7.20元/斤，出现在康平等地区。全省仔猪平均零售价格为25.61元/斤，环比上升1.47%；最高价为30.00元/斤，出现在昌图、黑山等地区；最低价为20.00元/斤，出现在于洪、绥中等地区。为8.00元/斤，出现在溪湖、昌图等地区；最低价为7.20元/斤，出现在康平等地区。全省仔猪平均零售价格为25.61元/斤，环比上升1.47%；最高价为30.00元/斤，出现在昌图、黑山等地区；最低价为20.00元/斤，出现在于洪、绥中等地区。");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("2008年6月16日", "AAAAAAAAA");
		headerMap.put("#header#", "这是页头这是页头");

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