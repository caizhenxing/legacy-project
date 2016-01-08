/*
 * 创建日期 2005-1-10
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testXml;

/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.io.*; //Java基础包，包含各种IO操作 
import java.util.*; //Java基础包，包含各种标准数据结构操作 
import javax.xml.parsers.*; //XML解析器接口 
import org.w3c.dom.*; //XML的DOM实现 
import org.apache.crimson.tree.XmlDocument; //写XML文件要用到 

public class XMLTest { 
	  Vector student_Vector; 
	  XMLTest() { 

	  } 

//为了保存多个学生信息，还得借助一个集合类(并不是单纯意义上的集合，JAVA中的集合是集合框架的概念，包含向量、列表、哈希表等），这里采用Vector向量类。定义在XMLTest测试类中，命名为student_Vector。然后定义两个方法readXMLFile和writeXMLFile，实现读写操作。代码如下： 
	  private void readXMLFile(String inFile) throws Exception { 
//为解析XML作准备，创建DocumentBuilderFactory实例,指定DocumentBuilder 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = null; 
			try { 
				  db = dbf.newDocumentBuilder(); 
			} 
			catch (ParserConfigurationException pce) { 
				  System.err.println(pce); //出异常时输出异常信息，然后退出，下同 
				  System.exit(1); 
			} 

			Document doc = null; 
			try { 
				  doc = db.parse(inFile); 
			} 
			catch (DOMException dom) { 
				  System.err.println(dom.getMessage()); 
				  System.exit(1); 
			} 
			catch (IOException ioe) { 
				  System.err.println(ioe); 
				  System.exit(1); 
			} 
//下面是解析XML的全过程，比较简单，先取根元素"学生花名册" 
			Element root = doc.getDocumentElement(); 
//取"学生"元素列表 
			NodeList students = root.getElementsByTagName("学生"); 
			for (int i = 0; i < students.getLength(); i++) { 
//依次取每个"学生"元素 
				  Element student = (Element) students.item(i); 
//创建一个学生的Bean实例 
				  StudentBean studentBean = new StudentBean(); 
//取学生的性别属性 
				  studentBean.setSex(student.getAttribute("性别")); 
//取"姓名"元素，下面类同 
				  NodeList names = student.getElementsByTagName("姓名"); 
				  if (names.getLength() == 1) { 
						Element e = (Element) names.item(0); 
						Text t = (Text) e.getFirstChild(); 
						studentBean.setName(t.getNodeValue()); 
				  } 

				  NodeList ages = student.getElementsByTagName("年龄"); 
				  if (ages.getLength() == 1) { 
						Element e = (Element) ages.item(0); 
						Text t = (Text) e.getFirstChild(); 
						studentBean.setAge(Integer.parseInt(t.getNodeValue())); 
				  } 

				  NodeList phones = student.getElementsByTagName("电话"); 
				  if (phones.getLength() == 1) { 
						Element e = (Element) phones.item(0); 
						Text t = (Text) e.getFirstChild(); 
						studentBean.setPhone(t.getNodeValue()); 
				  } 

				  student_Vector.add(studentBean); 
			} 
	  } 

	  private void writeXMLFile(String outFile) throws Exception { 
//为解析XML作准备，创建DocumentBuilderFactory实例,指定DocumentBuilder 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = null; 
			try { 
				  db = dbf.newDocumentBuilder(); 
			} 
			catch (ParserConfigurationException pce) { 
				  System.err.println(pce); 
				  System.exit(1); 
			} 

			Document doc = null; 
			doc = db.newDocument(); 

//下面是建立XML文档内容的过程，先建立根元素"学生花名册" 
			Element root = doc.createElement("学生花名册"); 
//根元素添加上文档 
			doc.appendChild(root); 

//取学生信息的Bean列表 
			for (int i = 0; i < student_Vector.size(); i++) { 
//依次取每个学生的信息 
				  StudentBean studentBean = (StudentBean) student_Vector.get(i); 
//建立"学生"元素，添加到根元素 
				  Element student = doc.createElement("学生"); 
				  student.setAttribute("性别", studentBean.getSex()); 
				  root.appendChild(student); 
//建立"姓名"元素，添加到学生下面，下同 
				  Element name = doc.createElement("姓名"); 
				  student.appendChild(name); 
				  Text tName = doc.createTextNode(studentBean.getName()); 
				  name.appendChild(tName); 

				  Element age = doc.createElement("年龄"); 
				  student.appendChild(age); 
				  Text tAge = doc.createTextNode(String.valueOf(studentBean. 
							  getAge())); 
				  age.appendChild(tAge); 

				  Element phone = doc.createElement("电话"); 
				  student.appendChild(phone); 
				  Text tPhone = doc.createTextNode(studentBean.getPhone()); 
				  phone.appendChild(tPhone); 
			} 
//把XML文档输出到指定的文件 
			FileOutputStream outStream = new FileOutputStream(outFile); 
			OutputStreamWriter outWriter = new OutputStreamWriter(outStream); 
			( (XmlDocument) doc).write(outWriter, "GB2312"); 
			outWriter.close(); 
			outStream.close(); 
	  } 

//最后加入测试主函数，如下： 
	  public static void main(String[] args) throws Exception { 
//建立测试实例 
			XMLTest xmlTest = new XMLTest(); 
//初始化向量列表 
			xmlTest.student_Vector = new Vector(); 

			System.out.println("开始读Input.xml文件"); 
			xmlTest.readXMLFile("e:\\Input.xml"); 

			System.out.println("读入完毕,开始写Output.xml文件"); 
			xmlTest.writeXMLFile("e:\\Output.xml"); 
			System.out.println("写入完成"); 
			System.in.read(); 
	  } 
} 
