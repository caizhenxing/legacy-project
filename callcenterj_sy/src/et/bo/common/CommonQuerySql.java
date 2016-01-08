/**
 * 沈阳卓越科技有限公司
 */
package et.bo.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.po.SysUser;

/**
 * @author 王文权
 * 将查询用的的公共sql放到这里便于统一维护
 *
 */
public class CommonQuerySql {
	//查询座席工号用的
	public   static  final String  AGENTWORKQUERYSQL = "select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'"; 
}
/**
 * <html:select property="agentNum">
						<option value="">请选择</option>
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
	 * 获得受理工号列表
	*
	public List userQuery();
 */
