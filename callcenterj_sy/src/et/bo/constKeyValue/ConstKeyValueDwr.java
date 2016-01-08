/*
 * @(#)AddressMainService.java	 2009-03-13
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.constKeyValue;

import et.bo.constKeyValue.service.ConstKeyValueService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>省市县地址信息维护</p>
 * 
 * @version 2009-03-13
 * @author wangwenquan
 */
public class ConstKeyValueDwr {
	public ConstKeyValueDwr()
	{
		service = (ConstKeyValueService)SpringRunningContainer.getInstance().getBean("ConstKeyValueService");
	}
	ConstKeyValueService service;
	/**
	 * 增加功能
	 * @param type
	 * @param key
	 * @param value
	 */
	public void addConstKeyValue(String type,String key, String value)
	{
		service.addConstKeyValue(type, key, value);
	}
	/**
	 * 增加或修改功能
	 * @param type
	 * @param key
	 * @param value
	 * @return  int 0成功 1失败
	 */
	public int addOrUpdateConstKeyValue(String type,String key, String value)
	{
		return service.addOrUpdateConstKeyValue(type, key, value);
	}
	/**
	 * 查询功能
	 * @param type
	 * @param key
	 * @return String
	 */
	public String getConstValueByTypeKey(String type,String key)
	{
		return service.getConstValueByTypeKey(type, key);
	}
}
