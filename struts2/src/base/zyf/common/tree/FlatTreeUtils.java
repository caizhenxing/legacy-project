/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 31, 20099:34:53 AM
 * ������base.zyf.common.tree
 * �ļ�����FlatTreeUtils.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

/**
 * ������һ�������࣬��Ҫ�������ı任����һ����̬�������������ڼ̳�
 * @author zhaoyifei
 * @version 1.0
 */
public abstract class FlatTreeUtils {

	/** ��Ԫ��֮��ķָ��� */
	public static final String ELEMENT_SEPARATOR = "|||";
	
	/** text �� value ֮��ķָ��� */
	public static final String TEXT_VALUE_SEPARATOR = "###";
	/**
	 * �� {@link FlatTreeNode} �ļ������л����ַ����� /publicresource/web/public/scripts/codeTree.js ����
	 * @param treeNodes ����, Ԫ�����ͱ����� {@link FlatTreeNode}
	 * @param allNodeSelectable �Ƿ����нڵ㶼���Ա�ѡ��, ��������չʾ����ʱ����Ϊ true, ��������ѡ��ʱֵ�� {@link FlatTreeNode#isCanbeSelected()} ����
	 * @return ���л�����ַ���
	 * ��ʽ���£�
	 * id###name###�Ƿ����ӽڵ�0/1###�Ƿ�Ҷ��###ͼƬ����###�Ƿ񲻿�ѡ(0/1)|||other
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
				// ע�� codeTree.js �еĲ����� "�Ƿ񲻿�ѡ", ����Ҫ��
				buffer.append(true ? 0 : 1);
			}
			first = false;
		}
		return buffer.toString();
	}
}
