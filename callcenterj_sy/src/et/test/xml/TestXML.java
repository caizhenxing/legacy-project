package et.test.xml;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.xmlbeans.XmlException;

import et.xmlbean.ChartDocument;

public class TestXML extends TestCase {
	
	private String filename = null;
	
	public void testReadXML(){
		filename = "D:\\workspace\\callcenterj_sy\\src\\et\\test\\xml\\Data.xml";
		File xmlFile = new File(filename);
		try {
			ChartDocument doc = ChartDocument.Factory.parse(xmlFile);
//			et.xmlbean.ChartDocument.Chart.Dataset[] t = doc.getChart().getDatasetArray();
//			for (int i = 0; i < t.length; i++) {
//				System.out.println(t[i].xmlText());
//			}
			et.xmlbean.ChartDocument.Chart.Set[] st = doc.getChart().getSetArray();
			for (int i = 0; i < st.length; i++) {
				System.out.println(st[i].getLabel());
				System.out.println(st[i].getValue());
			}
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
