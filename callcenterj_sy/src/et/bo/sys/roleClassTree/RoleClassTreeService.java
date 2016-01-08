/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.roleClassTree;

import java.util.Map;

/**
 * ��ɫ�����е�Ȩ������Ҷ�ӽڵ�Ȩ����صĲ���
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface RoleClassTreeService {
    /**
     * ���û�������ɫ��Ȩ������map�� key nickName value SysRoleLeaf
     * @param userId
     * @return map key id value SysLeafRight
     */
	Map getRoleLeafRightByUserId(String userId);
}
