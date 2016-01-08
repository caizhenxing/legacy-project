/**
 * 沈阳卓越科技有限公司
 * 2008-4-19
 */
package et.bo.sys.login.bean;

/**
 * @author zhang feng
 * 
 */
public class UserBean {
	// 登陆id
	private String userId = "";
	
	// 线id
	private String lineId = "";

	// 用户名
	private String userName = "";

	// 用户组
	private String userGroup = "";

	// 用户角色
	private String userRole = "";

	// 用户部门
	private String userDepartment = "";

	// 技能类型
	private String skill = "";

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
