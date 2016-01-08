/*
 * @(#)ConstKeyValueService.java	 2009-03-13
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.constKeyValue.service;
/**
 * <p>常量信息维护</p>
 * 
 * @version 2009-03-13
 * @author wangwenquan
 */
public interface ConstKeyValueService {
	/**
	 * 增加功能
	 * @param type
	 * @param key
	 * @param value
	 */
	public void addConstKeyValue(String type,String key, String value);
	/**
	 * 增加或修改功能
	 * @param type
	 * @param key
	 * @param value
	 * @return  int 0成功 1失败
	 */
	public int addOrUpdateConstKeyValue(String type,String key, String value);
	/**
	 * 修改功能
	 * @param id
	 * @param value
	 */
	public void updateConstKeyValue(String id, String value);
	/**
	 * 查询功能
	 * @param type
	 * @param key
	 * @return String
	 */
	public String getConstValueByTypeKey(String type,String key);
}
