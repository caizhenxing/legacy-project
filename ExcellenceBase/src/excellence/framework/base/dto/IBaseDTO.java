package excellence.framework.base.dto;

import org.apache.commons.beanutils.DynaBean;
/**
 * 
 * @author 赵一非
 *@version 2.0 2005-08-10
 */
public interface IBaseDTO extends DynaBean{
	/**
	 * 从object取值，赋予dto
	 * @param o Object 值的来源
	 * @version 2006-8-30

	 */
	public void setObject(Object o);
	/**
	 * 从dto中取值，赋予object
	 * @param o Object 等待赋值的对象
	 * @version 2006-8-30
	 * @return
	 */
	public void loadValue(Object o);
}
