/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.demochange;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author zhang feng
 * 
 */
public class DemoSource {
	private Vector repository = new Vector();

	private DemoListener dl;

	private String sName = "";

	public DemoSource() {
	}

	// 注册监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	public void addDemoListener(DemoListener dl) {
		repository.addElement(dl);// 这步要注意同步问题
	}

	// 如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	public void notifyDemoEvent(DemoEvent event) {
		Enumeration enu = repository.elements();// 这步要注意同步问题
		while (enu.hasMoreElements()) {
			dl = (DemoListener) enu.nextElement();
			dl.demoEvent(event);
		}
	}

	// 删除监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	public void removeDemoListener(DemoListener dl) {
		repository.remove(dl);// 这步要注意同步问题
	}

	/**
	 * 设置属性
	 * 
	 * @param str1
	 *            String
	 */
	public void setName(String str1) {
		boolean bool = false;
		if (str1 == null && sName != null)
			bool = true;
		else if (str1 != null && sName == null)
			bool = true;
		else if (!sName.equals(str1))
			bool = true;

		this.sName = str1;

		// 如果改变则执行事件
		if (bool)
			notifyDemoEvent(new DemoEvent(this, sName));
	}

	public String getName() {
		return sName;
	}

}
