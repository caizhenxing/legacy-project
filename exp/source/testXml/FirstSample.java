/*
 * 创建日期 2005-4-18
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testXml;

import org.xml.sax.Attributes;
import org.xml.*;
import org.xml.sax.helpers.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class FirstSample extends DefaultHandler{

	public static void main(String[] args) 
	{
		try
		{
			DefaultHandler handler=new FirstSample();
			SAXParserFactory saxFactory=SAXParserFactory.newInstance();
			SAXParser saxParser=saxFactory.newSAXParser();
			saxParser.parse(new InputSource("e:/xml/input.xml"),handler);
		}catch(SAXParseException e)
		{
			e.printStackTrace();
		}catch(SAXException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	protected void println(String s)
	{
		System.out.println(s);
	}
	public void startDocument() throws SAXException
	{
		println("startDocument");
	}
	public void endDocument() throws SAXException
	{
		println("endDocumnet");
	}
	public void startElement(String uri,String localName,String qName,Attributes atts)throws SAXException
	{
		println("startElement: "+(uri!=null&&uri.length()>0?uri+":":"")+localName);
		int n=atts.getLength();
		for(int i=0;i<n;i++)
		{
			println("attribute: "+atts.getLocalName(i)+"=\""+atts.getValue(i)+"\"");
		}
	}
	public void endElement(String uri,String localName,String gName)throws SAXException
	{
		println("endElement") ;
	}
	public void characters(char[] ch,int start,int length)throws SAXException
	{
		String data=new String(ch,start,length).trim();
		if(data.length()>0)
		println("characters: "+data);
	}
	public void processingInstruction(String target,String data)throws SAXException
	{
		println("processingInstruction: "+target);
		if(data!=null&&data.length()>0)
		println("data: "+data);
	}
	public void warning(SAXParseException e)throws SAXException
	{
		println("warning: "+e);
	}
	public void error(SAXParseException e)throws SAXException
	{
		println("error: "+e);
	}
	public void fatalError(SAXParseException e)throws SAXException
	{
		println("fatal error: "+e );
		throw e;
	}
}
