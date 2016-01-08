/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-9-6
 * 文件名：CommonBasicCodeInitService.java
 * 制作者：李鹤
 * 
 */
package com.zyf.common.basiccode.service;

import java.util.List;

import com.zyf.core.ServiceBase;

public interface CommonBasicCodeInitService extends ServiceBase {

	static final String SERVICE_NAME = "common.CommonBasicCodeInitService";
	/**
	 * 加载指定编码的节点
	 * 
	 * @param id
	 *            要加载的节点编码
	 * @return 节点信息
	 */
	public List load(String id);

	/**
	 * 初始化选中的子系统
	 * 
	 * @param subID
	 *            子系统id
	 * @return 处理结果
	 */
	public String reset(String subID);

	/**
	 * 根据ID查找记录
	 * 
	 * @param id
	 *            要找的ID
	 * @return 找到的记录
	 */
	public List find(String id);

}
