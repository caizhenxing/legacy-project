package com.zyf.common.transfer.importexcel;

import java.util.Map;

/**
 * ����{@link CommonTransfer <code>HrTransfer</code>}�аѶ�������д�����ݿ�
 * @author Scott Captain
 * @since 2006-8-17
 * @version $Id: CommonTransferCallback.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public interface CommonTransferCallback {
    /**
     * {@link CommonTransfer <code>HrTransfer</code>}ÿ����һ�����ݾ͵���һ���������
     * @param map   ��<code>dbf</code>,<code>excel</code>�����ļ�������һ������, 
     * <code>key</code>�������ļ��е�������, <code>value</code>����������е�ֵ
     * @return �������������д�������ݿ�ͷ���<code>true</code>, ���򷵻�<code>false</code>
     */
    boolean executePerRow(Map map);
}
