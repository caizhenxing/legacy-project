/**
 * 	@(#)TestFor.java   May 12, 2007 1:36:04 PM
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.test.string;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import et.bo.sys.basetree.service.impl.IVRBean;
import et.bo.sys.ivr.service.ClassBaseTreeService;
import excellence.framework.base.container.SpringContainer;

/**
 * @author
 * @version May 12, 2007
 * @see
 */
public class TestFor {
	
	private static final String rootId="SYS_TREE_0000000041";

	/**
	 * @param
	 * @version May 12, 2007
	 * @return
	 */
	public static void main(String[] args) {

		ClassBaseTreeService ivrClassTreeService = (ClassBaseTreeService) SpringContainer
				.getInstance().getBean("ClassBaseTreeService");
		ivrClassTreeService.loadParamTree();
		List<IVRBean> l = new ArrayList<IVRBean>();
		l = ivrClassTreeService.getListById(rootId);
		Iterator it = l.iterator();
		while (it.hasNext()) {
			IVRBean ib = (IVRBean)it.next();
			System.out.println(ib.getId());
		}

	}

}
