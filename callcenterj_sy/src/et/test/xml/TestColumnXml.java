package et.test.xml;

import java.io.File;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import et.xmlbean.ChartDocument;
import et.xmlbean.ChartDocument.Chart.Set;

import junit.framework.TestCase;

public class TestColumnXml extends TestCase {

	public void testReadXML() {
		String url = this.getClass().getResource("/").getPath();
		int temI = url.indexOf("WebRoot");
		url = url.substring(1, temI);
		url = url + "WebRoot/dataxml/Column2D.xml";

		File xmlFile = new File(url);
		try {
			ChartDocument doc = ChartDocument.Factory.parse(xmlFile);
			System.out.println(doc.getChart().getYAxisName());
			Set[] st = doc.getChart().getSetArray();
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

	public void testModifyXML() {
		String url = this.getClass().getResource("/").getPath();
		int temI = url.indexOf("WebRoot");
		url = url.substring(1, temI);
		url = url + "WebRoot/dataxml/Column2D.xml";

		File xmlFile = new File(url);

		try {
			ChartDocument doc = ChartDocument.Factory.parse(xmlFile);
			doc.getChart().setCaption("12316呼叫中心年话务量统计");
			Set[] st = doc.getChart().getSetArray();
			for (int i = 0; i < 30; i++) {
				st[i].setLabel(i+1+"日");
				st[i].setValue((short)((i+1)*40));
			}
			doc.save(xmlFile);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
