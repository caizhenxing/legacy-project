/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.transfer;

import java.util.List;

import com.zyf.framework.transfer.TransferExportRow;

/**
 * Ϊ��Ҫ���������ݷ���, Ŀ��������
 * <ul>
 * <li>��ֹ�ڴ����</li>
 * <li>Excel �ļ������������ 65535, ����������ݳ���������, �������</li>
 * </ul>
 * @see SimpleExportRowsGrouper
 * @see ByCountExportRowsGrouper
 * @since 2006-7-16
 * @author java2enterprise
 * @version $Id: ExportRowsGrouper.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public interface ExportRowsGrouper {
	
	/**
	 * ʵ�ַ��鹦��, ��������һ�� List, ���е�ÿ��Ԫ��ҲӦ���� List, ÿ�� List �е�Ԫ���� {@link TransferExportRow}
	 * @param exportRows exportRows list fill with {@link TransferExportRow}
	 * @return list of list
	 */
	List group(List exportRows);
}
