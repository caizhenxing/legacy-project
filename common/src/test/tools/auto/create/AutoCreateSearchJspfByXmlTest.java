/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 29, 20073:21:15 PM
 * 文件名：AutoCreateSearchJspfByXmlTest.java
 * 制作者：zhaoyf
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
