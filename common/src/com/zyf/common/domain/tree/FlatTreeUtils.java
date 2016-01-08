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
	 * �� {@link FlatTreeNode} �ļ������л����ַ����� /publicresource/web/public/scripts/codeTree.js ����
	 * @param treeNodes ����, Ԫ�����ͱ����� {@link FlatTreeNode}
	 * @param allNodeSelectable �Ƿ����нڵ㶼���Ա�ѡ��, ��������չʾ����ʱ����Ϊ true, ��������ѡ��ʱֵ�� {@link FlatTreeNode#isCanbeSelected()} ����
	 * @return ���л�����ַ���
	 * ��ʽ���£�
	 * id###name###�Ƿ����ӽڵ�0/1###�Ƿ�Ҷ��###ͼƬ����###�Ƿ񲻿�ѡ(0/1)|||other
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
				// ע�� codeTree.js �еĲ����� "�Ƿ񲻿�ѡ", ����Ҫ��
				buffer.append(node.isCanbeSelected() ? 0 : 1);
			}
			first = false;
		}
		return buffer.toString();
	}
	
	
	
}
