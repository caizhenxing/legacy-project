/**
 * 
 * 制作时间：2007-8-16下午02:51:55
 * 工程名：comoon
 * 文件名：com.zyf.common.dict.service.implHrBasicCodeService.java
 * 制作者：Administrator
 * 
 */
package com.zyf.common.dict.service;

import java.util.List;

import com.zyf.common.dict.domain.CommonBasicCode;
import com.zyf.core.ServiceBase;



/**
 * @author zhaoyifei
 *
 */
public interface CommonBasicCodeService extends ServiceBase{

	static final String SERVICE_NAME = "common.CommonBasicCodeService";
	
	List list(CommonBasicCode config);

	/**
	 * 保存或创建一个配置信息
	 * @param config 要保存或创建的配置信息
	 */
	void saveOrUpdate(CommonBasicCode config);
	
	/**
	 * 删除指定的配置信息, 如果这个节点下有子节点做级联删除
	 * @param config 要删除的节点
	 */
	void delete(CommonBasicCode config);
	
	/**
	 * 加载指定编码的节点
	 * @param id 要加载的节点编码
	 * @return 节点信息
	 */
	CommonBasicCode load(String id);
	
	/**
	 * 加载指定编码的节点
	 * @param id 要加载的节点编码
	 * @return 节点信息
	 */
	CommonBasicCode get(String id);
	
	/**
	 * 在指定的节点下创建新的节点信息
	 * @param parentId 指定节点的<code>code</code>
	 * @return 新创建的节点信息
	 */
	CommonBasicCode newInstance(String parentId);
}
