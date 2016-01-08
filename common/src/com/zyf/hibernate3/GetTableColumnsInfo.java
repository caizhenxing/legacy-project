/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：biswf
 * 制作时间：2007-12-7上午10:17:36
 * 包名：com.zyf.hibernate3
 * 文件名：GetTableColumnsInfo.java
 * 制作者：wenjb
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
	 * 通过实体类得到对应持久类
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
	 * 通过实体类得到对应的表名
	 * 
	 * @param clazz
	 * @return String
	 */
	public static String getTableName(Class clazz) {

		return getPersistentClass(clazz).getTable().getName();

	}

	/**
	 * 通过实体类得到所有列的集合信息
	 * 
	 * @param clazz
	 * @return Iterator
	 */
	public static Iterator getColumnsInfo(Class clazz) {

		return getPersistentClass(clazz).getTable().getColumnIterator();

	}

	/**
	 * 通过实体类得到所有外键的集合信息
	 * 
	 * @param clazz
	 * @return Iterator
	 */
	public static Iterator getFKColumnsInfo(Class clazz) {

		return getPersistentClass(clazz).getTable().getForeignKeyIterator();

	}

	/**
	 * 通过实体类得到第一个主键的名称
	 * 
	 * @param clazz
	 * @return String
	 */
	public static String getPkColumnName(Class clazz) {

		return getPersistentClass(clazz).getTable().getPrimaryKey()

		.getColumn(0).getName();

	}
}
