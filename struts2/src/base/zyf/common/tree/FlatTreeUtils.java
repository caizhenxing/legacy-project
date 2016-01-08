/**
 * 
 * 项目名称：struts2
 * 制作时间：May 31, 20099:34:53 AM
 * 包名：base.zyf.common.tree
 * 文件名：FlatTreeUtils.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 本类是一个抽象类，主要用于树的变换，有一个静态方法，不能用于继承
 * @author zhaoyifei
 * @version 1.0
 */
public abstract class FlatTreeUtils {

	/** 各元素之间的分隔符 */
	public static final String ELEMENT_SEPARATOR = "|||";
	
	/** text 和 value 之间的分隔符 */
	public static final String TEXT_VALUE_SEPARATOR = "###";
	/**
	 * 将 {@link FlatTreeNode} 的集合序列化成字符串供 /publicresource/web/public/scripts/codeTree.js 解析
	 * @param treeNodes 集合, 元素类型必须是 {@link FlatTreeNode}
	 * @param allNodeSelectable 是否所有节点都可以被选中, 当树用于展示数据时必需为 true, 当树用于选择时值由 {@link FlatTreeNode#isCanbeSelected()} 决定
	 * @return 序列化后的字符串
	 * 格式如下：
	 * id###name###是否有子节点0/1###是否叶子###图片类型###是否不可选(0/1)|||other
	 */
	public static String serialize(List<TreeNodeI> treeNodes, boolean allNodeSelectable) {
		Assert.notNull(treeNodes, " treeNodes required. ");
		
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (TreeNodeI node:treeNodes) {
			if (!first) {
				buffer.append(ELEMENT_SEPARATOR);
			}
			buffer.append(node.getId());
			buffer.append(TEXT_VALUE_SEPARATOR);
			buffer.append(node.getName());
			buffer.append(TEXT_VALUE_SEPARATOR);
			buffer.append(!((TreeViewI)node).isLast() ? 0 : 1);
			buffer.append(TEXT_VALUE_SEPARATOR);
			buffer.append(!((TreeViewI)node).isLast());
			buffer.append(TEXT_VALUE_SEPARATOR);
			buffer.append(((TreeViewI)node).getIcon());
			buffer.append(TEXT_VALUE_SEPARATOR);
			
			if (allNodeSelectable) {
				buffer.append(0);
			} else {
				// 注意 codeTree.js 中的参数是 "是否不可选", 所以要求反
				buffer.append(true ? 0 : 1);
			}
			first = false;
		}
		return buffer.toString();
	}
}
