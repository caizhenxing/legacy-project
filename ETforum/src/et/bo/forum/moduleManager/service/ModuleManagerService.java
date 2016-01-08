/**
 * 	@(#)ModuleManagerService.java   2006-12-11 ����02:42:27
 *	 �� 
 *	 
 */
package et.bo.forum.moduleManager.service;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe ��̳ģ���������ӿ�
 * @author Ҷ����
 * @version 1.0, 2006-12-11
 * @see
 */
public interface ModuleManagerService {
    /**
	 * @describe ���ģ��: (���������Ƿ��ϴ�,�����Ƿ�ӷֵȵ�...)
	 * @param
	 * @return
	 */
	public void addModule(IBaseDTO dto);
	/**
	 * @describe �޸�ģ��
	 * @param
	 * @return
	 */
	public void updateModule(IBaseDTO dto);
	/**
	 * @describe ɾ��ģ��
	 * @param
	 * @return
	 */
	public void deleteModule(String id);
	/**
	 * ȡ��ģ����Ϣ
	 * @param
	 * @version 2006-12-11
	 * @return
	 */
	public IBaseDTO getModuleInfo(String id);
	/**
	 * @describe ģ���б�
	 * @param
	 * @return HashMap
	 */
	public HashMap moduleList();
	/**
	 * �õ�����
	 * @param
	 * @version 2006-12-11
	 * @return int
	 */
	public int getSize();
	/**
	 * ȡ��ģ�����Ʊ����б�
	 * @param
	 * @version 2006-12-18
	 * @return LabelValueBean
	 */
	public List<LabelValueBean> getAllModuleValueBean();
	/**
	 * ȡ��ģ�����Ʊ����б�
	 * @param
	 * @version 2006-12-18
	 * @return LabelValueBean
	 */
	public List<LabelValueBean> getModuleValueBean();
	/**
	 * �����������ظ�����
	 * @param
	 * @version 2006-12-18
	 * @return
	 */
	public void addAnswerTimes(String id);
	/**
	 * ��������������
	 * @param
	 * @version 2006-12-18
	 * @return
	 */
	public void addPostNum(String id);
	/**
	 * �ж�ģ�����Ƿ����
	 * @param
	 * @version 2006-12-18
	 * @return true���� false������
	 */
	public boolean isModuleExist(String moduleName);
	/**
	 * ����ʱ�ж�ģ�����Ƿ����
	 * @param
	 * @version 2006-12-18
	 * @return true���� false������
	 */
	public boolean updateIsModuleExist(String moduleName,String id);
	/**
	 * ͨ��ģ��������ģ��Id
	 * @param
	 * @version 2006-12-18
	 * @return Stirng
	 */
	public String getIdByModuleName(String moduleName);
}

