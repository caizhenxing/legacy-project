package et.bo.sys.login;

import java.util.List;

public class UserInfo {

	private String role=null;
	private String userName=null;
	private String group=null;
	private String dep=null;
	private List<String> deps=null;
	public List<String> getDeps() {
		return deps;
	}
	public void setDeps(List<String> deps) {
		this.deps = deps;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
