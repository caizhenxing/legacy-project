package et.bo.callcenter.serversocket.checklogin.impl;

public class LoginUser {
	// 登陆的ip
	private String ip = null;

	// 座席号
	private String user = null;

	// 端口号
	private String port = null;

	// 技能
	private String skill = null;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

}
