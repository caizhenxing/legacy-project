/**
 * 沈阳卓越科技有限公司
 * 2008-4-23
 */
package et.test.demochange;

/**
 * @author zhang feng
 * 
 */
public class TestDemo implements DemoListener {
	private DemoSource ds;

	public TestDemo() {
		ds = new DemoSource();
		ds.addDemoListener(this);
		System.out.println("添加监听器完毕");
		try {
			Thread.sleep(3000);
			// 改变属性,触发事件
			ds.setName("改变属性,触发事件");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		ds.addDemoListener(this);
		System.out.println("添加监听器完毕2");
		try {
			Thread.sleep(3000);
			// 改变属性,触发事件
			ds.setName("改变属性,触发事件2");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		ds.removeDemoListener(this);
		System.out.println("添加监听器完毕3");
		try {
			Thread.sleep(3000);
			// 改变属性,触发事件
			ds.setName("改变属性,触发事件3");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String args[]) {
		new TestDemo();
	}

	/**
	 * demoEvent
	 * 
	 * @param dm
	 *            DemoEvent
	 * @todo Implement this test.DemoListener method
	 */
	public void demoEvent(DemoEvent dm) {
		System.out.println("事件处理方法");
		System.out.println(dm.getName());
		dm.say();
	}

}
