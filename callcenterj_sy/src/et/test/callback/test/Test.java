/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-5-26
 */
package et.test.callback.test;

/**
 * @author zhang feng
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FooBar foo = new FooBar();
		foo.setCallBack(new ICallBack() {
			public void postExec() {
				System.out.println("method executed.");
			}
		});
	}

}
