/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.watch;

import java.util.Observable;
import java.util.Observer;

/**
 * @author zhang feng
 * 
 */
public class Watcher implements Observer {

	public Watcher(Watched w) {
		w.addObserver(this);
	}

	public void update(Observable ob, Object arg) {
		System.out.println("Data has been changed to: '"
				+ ((Watched) ob).retrieveData() + "'");
	}

}
