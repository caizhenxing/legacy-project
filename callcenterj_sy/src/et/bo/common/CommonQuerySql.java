/**
 * ����׿Խ�Ƽ����޹�˾
 */
package et.bo.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.po.SysUser;

/**
 * @author ����Ȩ
 * ����ѯ�õĵĹ���sql�ŵ��������ͳһά��
 *
 */
public class CommonQuerySql {
	//��ѯ��ϯ�����õ�
	public   static  final String  AGENTWORKQUERYSQL = "select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'"; 
}
/**
 * <html:select property="agentNum">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
					public List userQuery()
	{
		String sql = "select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'";
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	**
	 * ����������б�
	*
	public List userQuery();
 */
