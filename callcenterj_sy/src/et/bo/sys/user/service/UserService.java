/**
 * @(#)UserService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.user.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <code>UserService</code> is interface which contains a series of action
 * about user
 * 
 * @author yuzhuo Jing
 * 
 * @version 08/02/15
 * @since 1.0
 * @see IBaseDTO
 */
public interface UserService {
	/**
	 * �����ϯԱ�б�
	 * 
	 * @param sql
	 * @return List
	 */

	public List userQuery(String sql);

	/**
	 * @describe ȡ��OA�û���Ϣ����
	 * @param
	 * @return int
	 */
	public abstract int listUserSize(IBaseDTO dto);

	/**
	 * @describe ����Idȡ��OA�û���Ϣ
	 * @param
	 * @return dto(����)
	 */
	public IBaseDTO getUserInfo(String id);

	/**
	 * @describe ���OA�û���Ϣ
	 * @param
	 * @return void
	 */
	public void addUser(IBaseDTO dto);

	/**
	 * @describe �޸�OA�û���Ϣ
	 * @param
	 * @return void
	 */
	public boolean userUpdate(IBaseDTO dto);

	/**
	 * @describe ɾ��OA�û���Ϣ
	 * @param
	 * @return void
	 */
	public void delUser(String id);

	/**
	 * @describe ��ѯOA�û���Ϣ
	 * @param
	 * @return List
	 */
	public List<IBaseDTO> userList(IBaseDTO dto, PageInfo pi);

	/**
	 * �õ��û��б�
	 * 
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getUsers(String src);

	/**
	 * @describe �������OA�û���Ϣ
	 * @param
	 * @return boolean
	 */
	public boolean addUserAll(IBaseDTO dto, String str);

	/**
	 * @describe ��������Ϣ
	 * @param
	 * @return list
	 */
	public List getGroupList();

	/**
	 * @describe ���ؽ�ɫ��Ϣ
	 * @param
	 * @return list
	 */
	public List getRoleList();

	/**
	 * ����Ҫ��ֵ����dto�У����ظ���ϯ���
	 * 
	 * @return
	 */
	public List<IBaseDTO> getUserList();

	/**
	 * zhang feng add ��ϵͳ������ʱ����Ȩ�޵��û�����Ϣ���ص��ڴ���
	 * ����ͨ�����⣬keyֵputong����������������ֵ�����о���Ȩ�޵���Ա���б�
	 * ����������غ������ϵͳ���κ�ʱ��õ�����Ȩ�޵���Ա���б����ڵ���ҪĿ���Ǹ����Ͷ���Ϣ����Աʹ��
	 */
	void addPowerInStaticMap();

}
