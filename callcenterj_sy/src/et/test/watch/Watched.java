/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.watch;

import java.util.Observable;

/**
 * @author zhang feng
 * 
 */
public class Watched extends Observable {

	private String data = "";

	public String retrieveData() {
		return data;
	}

	public void changeData(String data) {
		if (!this.data.equals(data)) {
			this.data = data;
			setChanged();
		}
		notifyObservers();
	}
}
