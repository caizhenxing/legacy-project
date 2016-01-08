package et.bo.sys.ivr.ivrClassTree;

import et.po.CcIvrTreeInfo;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;

public class IvrBaseTreeNodeServiceImpl extends BaseTreeNodeServiceImpl{
	private CcIvrTreeInfo treeInfo;

	public CcIvrTreeInfo getTreeInfo() {
		return treeInfo;
	}

	public void setTreeInfo(CcIvrTreeInfo treeInfo) {
		this.treeInfo = treeInfo;
	}
	
}
