/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-19
 */
package et.bo.sys.login.bean;

/**
 * @author zhang feng
 * 
 */
public class UserBean {
	// ��½id
	private String userId = "";
	
	// ��id
	private String lineId = "";

	// �û���
	private String userName = "";

	// �û���
	private String userGroup = "";

	// �û���ɫ
	private String userRole = "";

	// �û�����
	private String userDepartment = "";

	// ��������
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
