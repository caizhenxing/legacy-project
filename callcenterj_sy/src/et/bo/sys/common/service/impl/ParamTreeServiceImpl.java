/**
 * 	@(#)DepTreeNode.java   2006-11-13 下午06:14:43
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.sys.common.service.impl;

import excellence.common.tree.TreeNode;

/**
 * @author 
 * @version 2006-11-13
 * @see
 */
public class ParamTreeServiceImpl extends TreeNode {

	private String admin=null;

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
