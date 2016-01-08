package et.bo.callcenter.bo.cclog.bean;

import java.util.Date;

public class CcLogMailBoxBean {
	private String id;
	private String cclogid;
    private Date begintime;
    private Date endtime;
    private String longtime;
    private String way;
    private String ifdispose;
    private String dispoesplan;
    private String phoneNum;
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public String getCclogid() {
		return cclogid;
	}
	public void setCclogid(String cclogid) {
		this.cclogid = cclogid;
	}
	public String getDispoesplan() {
		return dispoesplan;
	}
	public void setDispoesplan(String dispoesplan) {
		this.dispoesplan = dispoesplan;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIfdispose() {
		return ifdispose;
	}
	public void setIfdispose(String ifdispose) {
		this.ifdispose = ifdispose;
	}
	public String getLongtime() {
		return longtime;
	}
	public void setLongtime(String longtime) {
		this.longtime = longtime;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
