/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.domain.tree;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.util.Assert;

import com.zyf.web.view.ComboSupportList;

/**
 * @since 2006-7-27
 * @author java2enterprise
 * @version $Id: FlatTreeUtils.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public abstract class FlatTreeUtils {
	
	/**
	 * 将 {@link FlatTreeNode} 的集合序列化成字符串供 /publicresource/web/public/scripts/codeTree.js 解析
	 * @param treeNodes 集合, 元素类型必须是 {@link FlatTreeNode}
	 * @param allNodeSelectable 是否所有节点都可以被选中, 当树用于展示数据时必需为 true, 当树用于选择时值由 {@link FlatTreeNode#isCanbeSelected()} 决定
	 * @return 序列化后的字符串
	 * 格式如下：
	 * id###name###是否有子节点0/1###是否叶子###图片类型###是否不可选(0/1)|||other
	 */
	public static String serialize(Collection treeNodes, boolean allNodeSelectable) {
		Assert.notNull(treeNodes, " treeNodes required. ");
		
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (Iterator iter = treeNodes.iterator(); iter.hasNext(); ) {
			Object forEach = iter.next();
			Assert.isInstanceOf(FlatTreeNode.class, forEach);
			FlatTreeNode node = (FlatTreeNode) forEach;
			if (!first) {
				buffer.append(ComboSupportList.ELEMENT_SEPARATOR);
			}
			buffer.append(node.getIdentity());
			buffer.append(ComboSupportList.TEXT_VALUE_SEPARATOR);
			buffer.append(node.getCaptain());
			buffer.append(ComboSupportList.TEXT_VALUE_SEPARATOR);
			buffer.append(node.isHasChildren() ? 0 : 1);
			buffer.append(ComboSupportList.TEXT_VALUE_SEPARATOR);
			buffer.append(node.isLeaf());
			buffer.append(ComboSupportList.TEXT_VALUE_SEPARATOR);
			buffer.append(node.getImageType());
			buffer.append(ComboSupportList.TEXT_VALUE_SEPARATOR);
			
			if (allNodeSelectable) {
				buffer.append(0);
			} else {
				// 注意 codeTree.js 中的参数是 "是否不可选", 所以要求反
				buffer.append(node.isCanbeSelected() ? 0 : 1);
			}
			first = false;
		}
		return buffer.toString();
	}
	
	
	
}
