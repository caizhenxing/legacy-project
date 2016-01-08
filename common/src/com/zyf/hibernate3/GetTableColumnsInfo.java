/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�biswf
 * ����ʱ�䣺2007-12-7����10:17:36
 * ������com.zyf.hibernate3
 * �ļ�����GetTableColumnsInfo.java
 * �����ߣ�wenjb
 * @version 1.0
 */
package com.zyf.hibernate3;

import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;

/**
 * 
 * @author wenjb
 * @version 1.0
 */
public class GetTableColumnsInfo {

	private static Configuration hibernateConf = new Configuration();

	/**
	 * ͨ��ʵ����õ���Ӧ�־���
	 * 
	 * @param clazz
	 * @return PersistentClass
	 */
	private static PersistentClass getPersistentClass(Class clazz) {

		synchronized (GetTableColumnsInfo.class) {

			PersistentClass pc = hibernateConf.getClassMapping(clazz.getName());

			if (pc == null) {

				hibernateConf = hibernateConf.addClass(clazz);

				pc = hibernateConf.getClassMapping(clazz.getName());

			}

			return pc;

		}

	}

	/**
	 * ͨ��ʵ����õ���Ӧ�ı���
	 * 
	 * @param clazz
	 * @return String
	 */
	public static String getTableName(Class clazz) {

		return getPersistentClass(clazz).getTable().getName();

	}

	/**
	 * ͨ��ʵ����õ������еļ�����Ϣ
	 * 
	 * @param clazz
	 * @return Iterator
	 */
	public static Iterator getColumnsInfo(Class clazz) {

		return getPersistentClass(clazz).getTable().getColumnIterator();

	}

	/**
	 * ͨ��ʵ����õ���������ļ�����Ϣ
	 * 
	 * @param clazz
	 * @return Iterator
	 */
	public static Iterator getFKColumnsInfo(Class clazz) {

		return getPersistentClass(clazz).getTable().getForeignKeyIterator();

	}

	/**
	 * ͨ��ʵ����õ���һ������������
	 * 
	 * @param clazz
	 * @return String
	 */
	public static String getPkColumnName(Class clazz) {

		return getPersistentClass(clazz).getTable().getPrimaryKey()

		.getColumn(0).getName();

	}
}
