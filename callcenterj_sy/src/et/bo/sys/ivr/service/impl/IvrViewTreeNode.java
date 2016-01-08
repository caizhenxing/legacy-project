/**
 * className TreePropertyService 
 * 
 * �������� 2008-4-1
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.ivr.service.impl;

import et.po.CcIvrTreeInfo;
import excellence.common.tree.ext.view.impl.ViewTreeNode;
/**
* ����ʾ��������չ����tagShow������
*
* @version 	jan 01 2008 
* @author ����Ȩ
*/
public class IvrViewTreeNode extends ViewTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tagShow;
	private CcIvrTreeInfo treeInfo;
	
	
	public CcIvrTreeInfo getTreeInfo() {
		return treeInfo;
	}

	public void setTreeInfo(CcIvrTreeInfo treeInfo) {
		this.treeInfo = treeInfo;
	}

	public String getTagShow() {
		return tagShow;
	}

	public void setTagShow(String tagShow) {
		this.tagShow = tagShow;
	}
	
	/**
     * ���ڵ�����
     * @param
     * @version 2008-1-26
     * @return Object �����ڵ��ʵ��
     * @throws CloneNotSupportedException;
     */
	public Object clone() throws CloneNotSupportedException {
			IvrViewTreeNode node = new IvrViewTreeNode();
			node.setAction(this.getAction());
			node.setDomain(this.getDomain());
			node.setExpanded(this.isExpanded());
			node.setIcon(this.getIcon());
			node.setTarget(this.getTarget());
			node.setTmpIcon(this.getTmpIcon());
			node.setTagShow(this.getTagShow());
			CcIvrTreeInfo cInfo = new CcIvrTreeInfo();
			CcIvrTreeInfo sInfo = this.getTreeInfo();
			cInfo.setBaseTree(sInfo.getBaseTree());
			cInfo.setCheckValue(sInfo.getCheckValue());
			cInfo.setContent(sInfo.getContent());
			cInfo.setDeleteMark(sInfo.getDeleteMark());
			cInfo.setFunctype(sInfo.getFunctype());
			cInfo.setId(sInfo.getId());
			cInfo.setLanguageType(sInfo.getLanguageType());
			cInfo.setLengthSize(sInfo.getLengthSize());
			cInfo.setNickname(sInfo.getNickname());
			cInfo.setTelKey(sInfo.getTelKey());
			cInfo.setTelNum(cInfo.getTelNum());
			cInfo.setVoiceType(cInfo.getVoiceType());
			node.setTreeInfo(cInfo);
			return node;
	}
}
