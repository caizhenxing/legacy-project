/**
 * className BaseTreeService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.service;

import java.io.Serializable;
/**
* ����������չ����
*
* @version 	jan 24 2008 
* 
* @author ����Ȩ
*/
public interface TreeNodeExtendedService extends Serializable , Cloneable{
//	/**
//	 * 
//	 * ����BaseTreeNodeService
//	 * @param BaseTreeService��bts
//	 * @version 2008-1-4
//	 * @return void
//	 * @throws
//	 */
//	void setBaseTreeNodeService(BaseTreeNodeService baseTreeNodeService);
//	/**
//	 * 
//	 * �õ�BaseTreeService
//	 * @param 
//	 * @version 2008-1-4
//	 * @return BaseTreeService
//	 * @throws
//	 */
//	BaseTreeNodeService getBaseTreeNodeService();
//	/**
//     * ���ڵ�����
//     * @param
//     * @version 2008-1-26
//     * @return Object �����ڵ��ʵ��
//     * @throws CloneNotSupportedException;
//     */
	Object clone() throws CloneNotSupportedException;
}
