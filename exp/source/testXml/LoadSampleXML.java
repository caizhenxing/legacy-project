/*
 * �������� 2005-1-11
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

