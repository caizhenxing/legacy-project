/*
 * �������� 2004-8-20
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package base;
import java.io.File;
import com.jacob.com.*;
import com.jacob.activeX.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class Exam_2 {

 public static void main(String[] args) {

  ActiveXComponent app = new ActiveXComponent("Word.Application");//����word
  String inFile = "e:\\һ\\dsfg.doc";//Ҫת����word�ļ�
  String tpFile = "e:\\һ\\dsfg.htm";//��ʱ�ļ�
  String otFile = "e:\\my.xls";//Ŀ���ļ�
  //String inFile = "F:\\����\\jspt\\����\\������Ŀ�ĵ�\\ORACLE9I�İ�װ.doc";//Ҫת����word�ļ�
//String tpFile = "c:\\download\\ORACLE9I�İ�װ.htm";//��ʱ�ļ�
	 //String otFile = "c:\\download\\ORACLE9I�İ�װ.xml";//Ŀ���ļ�
  boolean flag = false;
  try {
   app.setProperty("Visible", new Variant(false));//����word���ɼ�
   Object docs = app.getProperty("Documents").toDispatch();
   Object doc = Dispatch.invoke(docs,"Open", Dispatch.Method, new Object[]{inFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();//��word�ļ�
   Dispatch.invoke(doc,"SaveAs", Dispatch.Method, new Object[]{tpFile,new Variant(8)}, new int[1]);//��Ϊhtml��ʽ���浽��ʱ�ļ�
   //Dispatch.invoke(doc,"SaveAs", Dispatch.Method, new Object[]{tpFile,new Variant(8)}, new int[1]);//��Ϊhtml��ʽ���浽��ʱ�ļ�
   Variant f = new Variant(false);
   Dispatch.call(doc, "Close", f);
   flag = true;
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   app.invoke("Quit", new Variant[] {});
  }

  /*if ( flag ) {
   app = new ActiveXComponent("Excel.Application");//����excel
   try {
	app.setProperty("Visible", new Variant(false));//����excel���ɼ�
	Object workbooks = app.getProperty("Workbooks").toDispatch();
	Object workbook = Dispatch.invoke(workbooks,"Open",Dispatch.Method,new Object[]{tpFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();//����ʱ�ļ�
	Dispatch.invoke(workbook,"SaveAs", Dispatch.Method, new Object[]{otFile,new Variant(46)}, new int[1]);//��xml��ʽ���浽Ŀ���ļ�
	Variant f = new Variant(false);
	Dispatch.call(workbook, "Close", f);
   } catch (Exception e) {
	e.printStackTrace();app.invoke("Quit", new Variant[] {});
   } finally {
	app.invoke("Quit", new Variant[] {});
	try {
	 File file = new File(tpFile);
	 file.delete();
	} catch (Exception e) {
	}
   }
  }*/
 }
}
