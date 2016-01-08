/**
 * className IvrTreeServiceImpl 
 * 
 * �������� 2008-4-3
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.ivr.service;

import java.util.List;

import et.bo.sys.basetree.service.impl.IVRBean;
/**
 * Ivr������Ӵ����classTree
 * ��ClassTreeServiceImpl�����ִ�дʹ���ܹ�Ӧ����ivr����
 * @version 	april 01 2008 
 * @author ����Ȩ
 */
public interface ClassBaseTreeService {
	IVRBean getIVRBeanById(String treeId);
	List<IVRBean> getListById(String treeId);
	
	void loadParamTree();
	/**
	 * IvrdeployAction��ʹ��
	 * @param id
	 * @return
	 */
	public String getCCLogIvrDeployName(String id);
}
