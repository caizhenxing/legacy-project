/**
 * 
 * 制作时间：Nov 15, 20073:16:21 PM
 * 文件名：CreateMain.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.tools.auto.create;

import base.zyf.tools.auto.create.entry.EntryJspCreateByXml;
import base.zyf.tools.auto.create.info.InfoJspCreateByXml;
import base.zyf.tools.auto.create.list.ListJspCreateByXml;
import base.zyf.tools.auto.create.search.AutoCreateSearchJspfByXml;

/**
 * @author zhaoyf
 *
 */
public class CreateMain {

	/**
	 * 功能描述
	 * @param args
	 * Nov 15, 2007 3:16:21 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AutoCreateSearchJspfByXml auto=new AutoCreateSearchJspfByXml();
		auto.createJsp("d:\\zhaoyifei\\search\\searchJsp.xml");
		EntryJspCreateByXml ejcx=new EntryJspCreateByXml();
		ejcx.createJsp("d:\\zhaoyifei\\entry\\entryJsp.xml");
		InfoJspCreateByXml ijc=new InfoJspCreateByXml();
		ijc.createJsp("d:\\zhaoyifei\\info\\infoJsp.xml");
		ListJspCreateByXml ljcx=new ListJspCreateByXml();
		ljcx.createJsp("d:\\zhaoyifei\\list\\listJsp.xml");
	}

}
