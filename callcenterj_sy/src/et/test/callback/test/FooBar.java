/**
 * 沈阳卓越科技有限公司
 * 2008-5-26
 */
package et.test.callback.test;

/**
 * @author zhang feng
 * 
 */
public class FooBar {

	private ICallBack callBack;

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
		doSth();
	}

	public void doSth() {
		callBack.postExec();
	}

}
