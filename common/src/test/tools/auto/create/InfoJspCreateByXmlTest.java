/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 5, 20077:30:40 PM
 * 文件名：InfoJspCreateByXmlTest.java
 * 制作者：zhaoyf
 * 
 */
package test.tools.auto.create;

import junit.framework.TestCase;

import com.zyf.tools.auto.create.info.InfoJspCreateByXml;

/**
 * @author zhaoyf
 *
 */
public class InfoJspCreateByXmlTest extends TestCase {

	/**
	 * Test method for {@link com.zyf.tools.auto.create.info.InfoJspCreateByXml#createJsp(java.lang.String)}.
	 */
	public void testCreateJsp() {
		InfoJspCreateByXml ijc=new InfoJspCreateByXml();
		try {
			ijc.createJsp("d:\\zhaoyifei\\info\\infoJsp.xml");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
