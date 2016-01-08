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
 
//import javax.xml.parsers.*; 
import org.xml.sax.*; 
import org.xml.sax.helpers.*; 
import java.io.*; 




public class SaxTest extends DefaultHandler { 


   // 重载DefaultHandler类的方法 
   // 以拦截SAX事件通知。 
   // 
		// 关于所有有效事件，见org.xml.sax.ContentHandler 
   // 
   public void startDocument( ) throws SAXException { 
	  System.out.println( "SAX Event: START DOCUMENT" ); 
   } 

   public void endDocument( ) throws SAXException { 
	  System.out.println( "SAX Event: END DOCUMENT" ); 
   } 

   public void startElement( String namespaceURI, 
			  String localName, 
			  String qName, 
			  Attributes attr ) throws SAXException { 
		 System.out.println( "SAX Event: START ELEMENT[ " + 
				  localName + " ]" ); 

	  // 如果有属性，我们也一并打印出来．．． 
				for ( int i = 0; i < attr.getLength(); i++ ){ 
				   System.out.println( "   ATTRIBUTE: " + 
				  attr.getLocalName(i) + 
				  " VALUE: " + 
				  attr.getValue(i) ); 
	  } 

   } 

   public void endElement( String namespaceURI, 
			  String localName, 
			  String qName ) throws SAXException { 
	  System.out.println( "SAX Event: END ELEMENT[ " + 
				  localName + " ]" ); 
   } 

   public void characters( char[] ch, int start, int length ) 
				  throws SAXException { 

	  System.out.print( "SAX Event: CHARACTERS[ " ); 

	  try { 
		 OutputStreamWriter outw = new OutputStreamWriter(System.out); 
		 outw.write( ch, start,length ); 
		 outw.flush(); 
	  } catch (Exception e) { 
		 e.printStackTrace(); 
	  } 

	  System.out.println( " )" ); 

   } 


   public static void main( String[] argv ){ 

	  System.out.println( "Example1 SAX Events:" ); 
	  try { 

		  //  SAXParserFactory  spFactory = SAXParserFactory.newInstance(); 
		 // SAXParser sParser = spFactory.newSAXParser(); 


		 // 建立SAX 2解析器．．． 
		 XMLReader xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser"); 

		 // 安装ContentHandler．．． 
		 xr.setContentHandler( new SaxTest() ); 

			// 解析文件．．． 
		 xr.parse( new InputSource( 
			   new FileReader( "e:\\exampleA.xml" )) ); 


	  }catch ( Exception e )  { 
		 e.printStackTrace(); 
	  } 

   } 

} 

