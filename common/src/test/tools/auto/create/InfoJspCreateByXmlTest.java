/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Nov 5, 20077:30:40 PM
 * �ļ�����InfoJspCreateByXmlTest.java
 * �����ߣ�zhaoyf
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
