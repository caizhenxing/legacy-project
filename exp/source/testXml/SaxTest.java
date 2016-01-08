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
 
//import javax.xml.parsers.*; 
import org.xml.sax.*; 
import org.xml.sax.helpers.*; 
import java.io.*; 




public class SaxTest extends DefaultHandler { 


   // ����DefaultHandler��ķ��� 
   // ������SAX�¼�֪ͨ�� 
   // 
		// ����������Ч�¼�����org.xml.sax.ContentHandler 
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

	  // ��������ԣ�����Ҳһ����ӡ���������� 
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


		 // ����SAX 2������������ 
		 XMLReader xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser"); 

		 // ��װContentHandler������ 
		 xr.setContentHandler( new SaxTest() ); 

			// �����ļ������� 
		 xr.parse( new InputSource( 
			   new FileReader( "e:\\exampleA.xml" )) ); 


	  }catch ( Exception e )  { 
		 e.printStackTrace(); 
	  } 

   } 

} 

