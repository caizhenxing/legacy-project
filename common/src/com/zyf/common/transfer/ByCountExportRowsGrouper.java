/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.transfer;

import java.util.List;

import org.springframework.util.Assert;

import com.zyf.utils.MiscUtils;

/**
 * ������������, ÿ���������ͨ�� {@link #setPerGroupCount(int)} ����, ���������, ÿ�������� {@link #DEFAULT_PER_GROUP_COUNT}
 * @since 2006-7-16
 * @author java2enterprise
 * @version $Id: ByCountExportRowsGrouper.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public class ByCountExportRowsGrouper implements ExportRowsGrouper {
	
	public static final int DEFAULT_PER_GROUP_COUNT = 1000;
	
	public static final int MAX_PER_GROUP_COUNT = 65535;
	
	private int perGroupCount = DEFAULT_PER_GROUP_COUNT;

	/**
	 * ����ÿ�������
	 * @param groupCount the groupCount to set
	 */
	public void setPerGroupCount(int perGroupCount) {
		Assert.isTrue(perGroupCount > 0, " ÿ������������� 0 ");
		Assert.isTrue(perGroupCount < MAX_PER_GROUP_COUNT, " ÿ����������С�� " + MAX_PER_GROUP_COUNT);
		this.perGroupCount = perGroupCount;
	}
	
	public List group(List exportRows) {		
		return MiscUtils.splitListBySize(exportRows, perGroupCount);
	}
	
	
}