package test.tools.auto.create;

import junit.framework.TestCase;

import com.zyf.tools.auto.create.list.ListJspCreateByXml;

public class ListJspCreateByXmlTest extends TestCase {

	public void testCreateJsp() {
		ListJspCreateByXml ljcx=new ListJspCreateByXml();
		ljcx.createJsp("d:\\zhaoyifei\\list\\listJsp.xml");
		fail("Not yet implemented");
	}

}
