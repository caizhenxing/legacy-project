/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 29, 20073:21:15 PM
 * �ļ�����AutoCreateSearchJspfByXmlTest.java
 * �����ߣ�zhaoyf
 * 
 */
package test.tools.auto.create;

import junit.framework.TestCase;

import com.zyf.tools.auto.create.search.AutoCreateSearchJspfByXml;

/**
 * @author zhaoyf
 *
 */
public class AutoCreateSearchJspfByXmlTest extends TestCase {

	/**
	 * Test method for {@link com.zyf.tools.auto.create.search.AutoCreateSearchJspf#createJsp(java.lang.String)}.
	 */
	public void testCreateJspf() {
		AutoCreateSearchJspfByXml auto=new AutoCreateSearchJspfByXml();
		auto.createJsp("d:\\zhaoyifei\\search\\searchJsp.xml");
		
		fail("Not yet implemented");
	}

}
