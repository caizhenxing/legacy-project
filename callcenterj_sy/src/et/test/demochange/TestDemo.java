/**
 * ����׿Խ�Ƽ����޹�˾
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
		System.out.println("��Ӽ��������");
		try {
			Thread.sleep(3000);
			// �ı�����,�����¼�
			ds.setName("�ı�����,�����¼�");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		ds.addDemoListener(this);
		System.out.println("��Ӽ��������2");
		try {
			Thread.sleep(3000);
			// �ı�����,�����¼�
			ds.setName("�ı�����,�����¼�2");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		ds.removeDemoListener(this);
		System.out.println("��Ӽ��������3");
		try {
			Thread.sleep(3000);
			// �ı�����,�����¼�
			ds.setName("�ı�����,�����¼�3");
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
		System.out.println("�¼�������");
		System.out.println(dm.getName());
		dm.say();
	}

}
