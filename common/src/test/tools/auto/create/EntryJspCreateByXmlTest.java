package test.tools.auto.create;

import junit.framework.TestCase;

import com.zyf.tools.auto.create.entry.EntryJspCreateByXml;

public class EntryJspCreateByXmlTest extends TestCase {

	public void testCreateJsp() {
		EntryJspCreateByXml ejcx=new EntryJspCreateByXml();
		ejcx.createJsp("d:\\zhaoyifei\\entry\\entryJsp.xml");
		fail("Not yet implemented");
	}

}
