/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.ext.view.impower;

import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
/**
 * ��ͼ��Ȩ�޽ڵ�
 * ����� ��չ��ViewTreeControlNode Ϊ��ʵ��Ȩ�޹���
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ViewTreeControlImpowerNode extends ViewTreeControlNode {
	/*
	 * ��ʱ��ͼ��
	 */
	private String tmpGroupIcon;
	/*
	 * ��ʱ�û�ͼ��
	 */
	private String tmpUserIcon;
	
	/**
	 * 
	 * �õ���ʱ��ͼ��
	 * @param 
	 * @version 2008-1-4
	 * @return String tmpGroupIcon ��ʱ��ͼ��
	 * @throws
	 */
	public String getTmpGroupIcon() {
		return tmpGroupIcon;
	}
	/**
	 * 
	 * ������ʱ��ͼ��
	 * @param String tmpGroupIcon ��ʱ��ͼ��
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setTmpGroupIcon(String tmpGroupIcon) {
		this.tmpGroupIcon = tmpGroupIcon;
	}
	/**
	 * 
	 * �õ���ʱ�û�ͼ��
	 * @param 
	 * @version 2008-1-4
	 * @return String tmpGroupIcon ��ʱ��ͼ��
	 * @throws
	 */
	public String getTmpUserIcon() {
		return tmpUserIcon;
	}
	/**
	 * 
	 * ������ʱ�û�ͼ��
	 * @param String tmpGroupIcon ��ʱ��ͼ��
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setTmpUserIcon(String tmpUserIcon) {
		this.tmpUserIcon = tmpUserIcon;
	}
	
	
}
