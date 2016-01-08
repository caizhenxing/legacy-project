/*
 * 创建日期 2004-8-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package base;
import java.io.File;
import com.jacob.com.*;
import com.jacob.activeX.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class Exam_2 {

 public static void main(String[] args) {

  ActiveXComponent app = new ActiveXComponent("Word.Application");//启动word
  String inFile = "e:\\一\\dsfg.doc";//要转换的word文件
  String tpFile = "e:\\一\\dsfg.htm";//临时文件
  String otFile = "e:\\my.xls";//目标文件
  //String inFile = "F:\\程序\\jspt\\资料\\集抄项目文档\\ORACLE9I的安装.doc";//要转换的word文件
//String tpFile = "c:\\download\\ORACLE9I的安装.htm";//临时文件
	 //String otFile = "c:\\download\\ORACLE9I的安装.xml";//目标文件
  boolean flag = false;
  try {
   app.setProperty("Visible", new Variant(false));//设置word不可见
   Object docs = app.getProperty("Documents").toDispatch();
   Object doc = Dispatch.invoke(docs,"Open", Dispatch.Method, new Object[]{inFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();//打开word文件
   Dispatch.invoke(doc,"SaveAs", Dispatch.Method, new Object[]{tpFile,new Variant(8)}, new int[1]);//作为html格式保存到临时文件
   //Dispatch.invoke(doc,"SaveAs", Dispatch.Method, new Object[]{tpFile,new Variant(8)}, new int[1]);//作为html格式保存到临时文件
   Variant f = new Variant(false);
   Dispatch.call(doc, "Close", f);
   flag = true;
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   app.invoke("Quit", new Variant[] {});
  }

  /*if ( flag ) {
   app = new ActiveXComponent("Excel.Application");//启动excel
   try {
	app.setProperty("Visible", new Variant(false));//设置excel不可见
	Object workbooks = app.getProperty("Workbooks").toDispatch();
	Object workbook = Dispatch.invoke(workbooks,"Open",Dispatch.Method,new Object[]{tpFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();//打开临时文件
	Dispatch.invoke(workbook,"SaveAs", Dispatch.Method, new Object[]{otFile,new Variant(46)}, new int[1]);//以xml格式保存到目标文件
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
