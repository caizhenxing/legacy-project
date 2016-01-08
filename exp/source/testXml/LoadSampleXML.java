/*
 * 创建日期 2005-1-11
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
import java.util.*;
import java.io.*;
import javax.xml.stream.*;

public class LoadSampleXML {
  public static void main(String args[]) throws Exception {
	Properties prop = new Properties();

	FileInputStream fis =
	  new FileInputStream("e:\\sample.xml");
	prop.loadFromXML(fis);
	prop.list(System.out);
	System.out.println("\nThe foo property: " +
		prop.getProperty("foo"));
		XMLStreamException x=new XMLStreamException();
  }
}

