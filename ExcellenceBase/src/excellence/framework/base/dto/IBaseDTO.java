package excellence.framework.base.dto;

import org.apache.commons.beanutils.DynaBean;
/**
 * 
 * @author ��һ��
 *@version 2.0 2005-08-10
 */
public interface IBaseDTO extends DynaBean{
	/**
	 * ��objectȡֵ������dto
	 * @param o Object ֵ����Դ
	 * @version 2006-8-30

	 */
	public void setObject(Object o);
	/**
	 * ��dto��ȡֵ������object
	 * @param o Object �ȴ���ֵ�Ķ���
	 * @version 2006-8-30
	 * @return
	 */
	public void loadValue(Object o);
}
