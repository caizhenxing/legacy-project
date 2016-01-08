/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-23
 */
package et.test.demochange;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author zhang feng
 * 
 */
public class DemoSource {
	private Vector repository = new Vector();

	private DemoListener dl;

	private String sName = "";

	public DemoSource() {
	}

	// ע����������������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	public void addDemoListener(DemoListener dl) {
		repository.addElement(dl);// �ⲽҪע��ͬ������
	}

	// �������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	public void notifyDemoEvent(DemoEvent event) {
		Enumeration enu = repository.elements();// �ⲽҪע��ͬ������
		while (enu.hasMoreElements()) {
			dl = (DemoListener) enu.nextElement();
			dl.demoEvent(event);
		}
	}

	// ɾ�����������������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	public void removeDemoListener(DemoListener dl) {
		repository.remove(dl);// �ⲽҪע��ͬ������
	}

	/**
	 * ��������
	 * 
	 * @param str1
	 *            String
	 */
	public void setName(String str1) {
		boolean bool = false;
		if (str1 == null && sName != null)
			bool = true;
		else if (str1 != null && sName == null)
			bool = true;
		else if (!sName.equals(str1))
			bool = true;

		this.sName = str1;

		// ����ı���ִ���¼�
		if (bool)
			notifyDemoEvent(new DemoEvent(this, sName));
	}

	public String getName() {
		return sName;
	}

}
