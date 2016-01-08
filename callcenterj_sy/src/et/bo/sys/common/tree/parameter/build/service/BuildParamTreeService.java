/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.common.tree.parameter.build.service;

import java.util.List;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeService;
/**
 * ��������tree
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface BuildParamTreeService {
	/**
	 * ����hql��� ���ڵ�id rootId ����һ��������
	 * @param rootId ���ڵ�id
	 * @return TreeService ������
	 * @throws
	 */
	TreeService buildTree();
	
	TreeService getParamTree();
	
	List<LabelValueBean> getLabelValueBeanListByNickName(String nickName);
	String getIdByNidkName(String nickName);
	void reloadParamTree();
	void setRootId(String rootId);
	
}
