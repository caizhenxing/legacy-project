/*
 * �������� 2005-1-10
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testXml;

/**
 * @author ��һ��
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.io.*; //Java����������������IO���� 
import java.util.*; //Java���������������ֱ�׼���ݽṹ���� 
import javax.xml.parsers.*; //XML�������ӿ� 
import org.w3c.dom.*; //XML��DOMʵ�� 
import org.apache.crimson.tree.XmlDocument; //дXML�ļ�Ҫ�õ� 

public class XMLTest { 
	  Vector student_Vector; 
	  XMLTest() { 

	  } 

//Ϊ�˱�����ѧ����Ϣ�����ý���һ��������(�����ǵ��������ϵļ��ϣ�JAVA�еļ����Ǽ��Ͽ�ܵĸ�������������б���ϣ��ȣ����������Vector�����ࡣ������XMLTest�������У�����Ϊstudent_Vector��Ȼ������������readXMLFile��writeXMLFile��ʵ�ֶ�д�������������£� 
	  private void readXMLFile(String inFile) throws Exception { 
//Ϊ����XML��׼��������DocumentBuilderFactoryʵ��,ָ��DocumentBuilder 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = null; 
			try { 
				  db = dbf.newDocumentBuilder(); 
			} 
			catch (ParserConfigurationException pce) { 
				  System.err.println(pce); //���쳣ʱ����쳣��Ϣ��Ȼ���˳�����ͬ 
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
//�����ǽ���XML��ȫ���̣��Ƚϼ򵥣���ȡ��Ԫ��"ѧ��������" 
			Element root = doc.getDocumentElement(); 
//ȡ"ѧ��"Ԫ���б� 
			NodeList students = root.getElementsByTagName("ѧ��"); 
			for (int i = 0; i < students.getLength(); i++) { 
//����ȡÿ��"ѧ��"Ԫ�� 
				  Element student = (Element) students.item(i); 
//����һ��ѧ����Beanʵ�� 
				  StudentBean studentBean = new StudentBean(); 
//ȡѧ�����Ա����� 
				  studentBean.setSex(student.getAttribute("�Ա�")); 
//ȡ"����"Ԫ�أ�������ͬ 
				  NodeList names = student.getElementsByTagName("����"); 
				  if (names.getLength() == 1) { 
						Element e = (Element) names.item(0); 
						Text t = (Text) e.getFirstChild(); 
						studentBean.setName(t.getNodeValue()); 
				  } 

				  NodeList ages = student.getElementsByTagName("����"); 
				  if (ages.getLength() == 1) { 
						Element e = (Element) ages.item(0); 
						Text t = (Text) e.getFirstChild(); 
						studentBean.setAge(Integer.parseInt(t.getNodeValue())); 
				  } 

				  NodeList phones = student.getElementsByTagName("�绰"); 
				  if (phones.getLength() == 1) { 
						Element e = (Element) phones.item(0); 
						Text t = (Text) e.getFirstChild(); 
						studentBean.setPhone(t.getNodeValue()); 
				  } 

				  student_Vector.add(studentBean); 
			} 
	  } 

	  private void writeXMLFile(String outFile) throws Exception { 
//Ϊ����XML��׼��������DocumentBuilderFactoryʵ��,ָ��DocumentBuilder 
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

//�����ǽ���XML�ĵ����ݵĹ��̣��Ƚ�����Ԫ��"ѧ��������" 
			Element root = doc.createElement("ѧ��������"); 
//��Ԫ��������ĵ� 
			doc.appendChild(root); 

//ȡѧ����Ϣ��Bean�б� 
			for (int i = 0; i < student_Vector.size(); i++) { 
//����ȡÿ��ѧ������Ϣ 
				  StudentBean studentBean = (StudentBean) student_Vector.get(i); 
//����"ѧ��"Ԫ�أ���ӵ���Ԫ�� 
				  Element student = doc.createElement("ѧ��"); 
				  student.setAttribute("�Ա�", studentBean.getSex()); 
				  root.appendChild(student); 
//����"����"Ԫ�أ���ӵ�ѧ�����棬��ͬ 
				  Element name = doc.createElement("����"); 
				  student.appendChild(name); 
				  Text tName = doc.createTextNode(studentBean.getName()); 
				  name.appendChild(tName); 

				  Element age = doc.createElement("����"); 
				  student.appendChild(age); 
				  Text tAge = doc.createTextNode(String.valueOf(studentBean. 
							  getAge())); 
				  age.appendChild(tAge); 

				  Element phone = doc.createElement("�绰"); 
				  student.appendChild(phone); 
				  Text tPhone = doc.createTextNode(studentBean.getPhone()); 
				  phone.appendChild(tPhone); 
			} 
//��XML�ĵ������ָ�����ļ� 
			FileOutputStream outStream = new FileOutputStream(outFile); 
			OutputStreamWriter outWriter = new OutputStreamWriter(outStream); 
			( (XmlDocument) doc).write(outWriter, "GB2312"); 
			outWriter.close(); 
			outStream.close(); 
	  } 

//��������������������£� 
	  public static void main(String[] args) throws Exception { 
//��������ʵ�� 
			XMLTest xmlTest = new XMLTest(); 
//��ʼ�������б� 
			xmlTest.student_Vector = new Vector(); 

			System.out.println("��ʼ��Input.xml�ļ�"); 
			xmlTest.readXMLFile("e:\\Input.xml"); 

			System.out.println("�������,��ʼдOutput.xml�ļ�"); 
			xmlTest.writeXMLFile("e:\\Output.xml"); 
			System.out.println("д�����"); 
			System.in.read(); 
	  } 
} 
