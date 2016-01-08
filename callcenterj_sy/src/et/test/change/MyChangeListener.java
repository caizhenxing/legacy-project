/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.change;

import java.util.EventListener;

/**
 * @author zhang feng
 * 
 */
public interface MyChangeListener extends EventListener {
	/*
	 * @param event
	 */
	public void MyChanged(MyChangeEvent event);
}
