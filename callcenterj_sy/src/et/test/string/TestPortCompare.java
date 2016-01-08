/**
 * 沈阳卓越科技有限公司
 * 2008-4-21
 */
package et.test.string;

import java.util.HashMap;

import et.bo.callcenter.portCompare.service.PortCompareService;
import excellence.framework.base.container.SpringContainer;

/**
 * @author zhang feng
 *
 */
public class TestPortCompare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PortCompareService pcs = (PortCompareService)SpringContainer.getInstance().getBean("PortCompareService");
		HashMap map = pcs.getInnerPortMap();
		System.out.println(map.size());
	}

}
