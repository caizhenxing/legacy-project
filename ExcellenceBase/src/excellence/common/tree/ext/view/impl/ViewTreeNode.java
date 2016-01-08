/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.ext.view.impl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeNodeExtendedService;
/**
 * ��ͼ�����Խڵ�
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ViewTreeNode implements TreeNodeExtendedService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//*****************���Ļ�����չ����*********************
	/**
	 * ע��BaseTreeNodeService
	 */
	//private BaseTreeNodeService baseTreeNodeService;
	/**
	 * ��ʾͼ��
	 */
	private String icon;
	/**
	 * ��ʾ��ʱͼ��
	 */
	private String tmpIcon;
	/**
	 * �ڵ���Ϊ
	 */
	private String action;
	/**
	 * �ڵ� target
	 */
	private String target;
	/**
	 * �ڵ��Ƿ�չ��
	 */
	private boolean expanded;
	/**
	 * �ڵ���
	 */
	private String domain;
	/**
	 * �ڵ��Ƿ���ʾ
	 */
	 private String tagShow;
	/**
	 * �ڵ���
	 */
	//*****************���Ļ�����չ���Ե�get set����*********************
	/**
	public BaseTreeNodeService getBaseTreeNodeService() {
		// TODO Auto-generated method stub
		return this.baseTreeNodeService;
	}
	public void setBaseTreeNodeService(BaseTreeNodeService bts) {
		// TODO Auto-generated method stub
		this.baseTreeNodeService = bts;
	}
	*/
	/**
	 * �õ��ڵ���Ϊ
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getAction() {
		return action;
	}
	/**
	 * ���ýڵ���Ϊ
	 * @param String action
	 * @version 2008-1-24
	 * @return
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * �õ���
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * ������
	 * @param String domain
	 * @version 2008-1-24
	 * @return
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * �õ�expanded
	 * @param
	 * @version 2008-1-24
	 * @return boolean
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * ����expanded
	 * @param
	 * @version 2008-1-24
	 * @return boolean
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * �õ�ͼ��
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * ����ͼ��
	 * @param String icon
	 * @version 2008-1-24
	 * @return
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * �õ�target
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 
	 */
	/**
	 * ����target
	 * @param String target
	 * @version 2008-1-24
	 * @return
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	public String getTmpIcon() {
		return tmpIcon;
	}
	public void setTmpIcon(String tmpIcon) {
		this.tmpIcon = tmpIcon;
	}
	public void setTagShow(String tagShow)
	{
		this.tagShow = tagShow;
	}
	public String getTagShow()
	{
		return this.tagShow;
	}
	/**
	 * ���
	 * @param 
	 * @version 2008-1-24
	 * @return Ojbect instance of ViewTreeNode
	 */
	public Object clone() throws CloneNotSupportedException {
		ViewTreeNode node = new ViewTreeNode();
		node.setAction(this.getAction());
		node.setDomain(this.getDomain());
		node.setExpanded(this.isExpanded());
		node.setIcon(this.icon);
		node.setTarget(this.getTarget());
		node.setTmpIcon(this.tmpIcon);
		node.setTagShow(this.tagShow);
		return node;
		
	}
}
