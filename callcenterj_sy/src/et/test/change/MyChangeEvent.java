/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.change;

import java.util.EventObject;

/**
 * @author zhang feng
 * 
 */
public class MyChangeEvent extends EventObject {

	private String myname;

	/**
	 * @param source
	 */
	public MyChangeEvent(Object source) {
		super(source);
	}

	/*
	 * @param source myname
	 */
	public MyChangeEvent(Object source, String myname) {
		super(source);
		this.myname = myname;
	}

	/*
	 * @return String
	 */
	public String getMyname() {
		return myname;
	}

}
