/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.demochange;

import java.util.EventObject;

/**
 * @author zhang feng
 * 
 */
public class DemoEvent extends EventObject {
	private Object obj;

	private String sName;

	public DemoEvent(Object source, String sName) {
		super(source);
		obj = source;
		this.sName = sName;
	}

	public Object getSource() {
		return obj;
	}

	public void say() {
		System.out.println("这个是 say 方法...");
	}

	public String getName() {
		return sName;
	}

}
