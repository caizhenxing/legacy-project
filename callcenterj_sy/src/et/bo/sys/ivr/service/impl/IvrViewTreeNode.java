/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-4-1
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.service.impl;

import et.po.CcIvrTreeInfo;
import excellence.common.tree.ext.view.impl.ViewTreeNode;
/**
* 对显示的树在扩展加入tagShow等属性
*
* @version 	jan 01 2008 
* @author 王文权
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
     * 树节点的深考贝
     * @param
     * @version 2008-1-26
     * @return Object 是树节点的实例
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
