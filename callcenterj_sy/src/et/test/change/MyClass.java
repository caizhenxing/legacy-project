/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.change;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author zhang feng
 * 
 */
public class MyClass {
	
	private String name = "2004-9-27";

	private static Vector myChangeListeners = new Vector();

	/**
	 * 
	 */
	public MyClass() {
		super();
	}

	/*
	 * @param myname
	 */
	public void testMyEvent(String myname) {
		this.name = myname;
		notifyMyChangeListeners();
	}

	/*
	 * @param listener add a listener;
	 */
	public void addMyChangeListener(MyChangeListener listener) {
		myChangeListeners.add(listener);
	}

	/*
	 * @param listener remove a listener;
	 */
	public void removeMyChangeListener(MyChangeListener listener) {
		myChangeListeners.remove(listener);
	}

	/*
	 * notify all listeners;
	 */
	private void notifyMyChangeListeners() {
		MyChangeEvent event = new MyChangeEvent(MyClass.class, name);
		Iterator iterator = ((Vector) myChangeListeners.clone()).iterator();

		while (iterator.hasNext()) {
			MyChangeListener listener = (MyChangeListener) iterator.next();
			listener.MyChanged(event);
		}
	}

}
