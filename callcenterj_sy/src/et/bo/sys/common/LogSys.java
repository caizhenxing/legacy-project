/**
 * 	@(#)LogSys.java   2006-8-30 下午06:24:19
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.sys.common;

import java.util.Date;

import excellence.common.util.time.TimeUtil;

/**
 * @author 
 * @version 2006-8-30
 * @see
 */
public class LogSys {

	String user;
	String actorType;
	String modu;
	String ip;
	String remark;
	Date dt;
	LogSys(String user,String operType,String mod,String ip,String remark)
	{
		this.user=user;
		this.actorType=operType;
		this.modu=mod;
		this.ip=ip;
		this.remark=remark;
		dt=TimeUtil.getNowTime();
	}
	public String getActorType() {
		return actorType;
	}
	public void setActorType(String actorType) {
		this.actorType = actorType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getModu() {
		return modu;
	}
	public void setModu(String modu) {
		this.modu = modu;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
}
