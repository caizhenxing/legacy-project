/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�qware
 * ����ʱ�䣺2007-11-27����08:23:33
 * ������com.zyf.security.model
 * �ļ�����DataAccessType.java
 * �����ߣ�yushn
 * @version 1.0
 */
package com.zyf.security.model;

/**
 * ���ݷ�������
 * @author yushn
 * @version 1.0
 */
public interface DataAccessType {
	/**
	 * ϵͳ��:û�з�Χ����
	 */
	int SYSTEM = 0;
	/**
	 * ���ż�:ֻ�ܷ������Լ�ͬ���ŵ��û�����������
	 */
	int DEPT = 1;
	/**
	 * ���˼���ֻ�ܷ����Լ�����������
	 */
	int SELF = 2;
	/**
	 * ��ֹ�����ɷ���
	 */
	int FORBID = 3;
}
