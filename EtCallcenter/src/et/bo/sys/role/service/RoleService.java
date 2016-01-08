/**
 * @(#)RoleService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.role.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**<code>RoleService</code> is interface 
 * which contains a series of action about group
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/17
 * @since   1.0
 *
 */
public interface RoleService {

//	������¼���ɫ��Ϣ
    public boolean addRoleInfo(IBaseDTO dto);
    
    //�޸Ľ�ɫ��Ϣ
    public boolean updateRoleInfo(IBaseDTO dto);
    
    //ɾ����ɫ��Ϣ
    public boolean deleteRoleInfo(IBaseDTO dto);
    
    //�õ�����
    public int getRoleSize();
    
    //����������ѯ��ɫ
    public List findRoleInfo(IBaseDTO dto,PageInfo pi);
    
    //��ѯ��������Ϣ��load
    public IBaseDTO getRoleInfo(String id);
}
