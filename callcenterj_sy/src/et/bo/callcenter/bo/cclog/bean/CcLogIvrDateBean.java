package et.bo.callcenter.bo.cclog.bean;

import java.util.Date;

public class CcLogIvrDateBean {
	private String id ; 	 // 流水号
	private String ivr_id ; 	 // 外键
	private String module_id ; 	 // 模块ID号
	private Date module_begintime ; 	 // 开始震铃时间
	private Date module_endtime ; 	 // 发起方接续过程结束时间
	private String is_delete ; 	 // 删除标记
	private String remark ; 	 // 备注
	
	public void readCcLogIvrDateInfo() {
//		System.out.println("********************CcLogIvrDateINFO*********************");
//		System.out.println("id = " + id);
//		System.out.println("ivr_id = " + ivr_id);
//		System.out.println("module_id = " + module_id);
//		System.out.println("module_begintime = " + module_begintime);
//		System.out.println("module_endtime = " + module_endtime);
//		System.out.println("is_delete = " + is_delete);
//		System.out.println("remark = " + remark);
//		System.out.println("********************CcLogIvrDateINFO*********************");
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	
	public Date getModule_begintime() {
		return module_begintime;
	}
	public void setModule_begintime(Date module_begintime) {
		this.module_begintime = module_begintime;
	}
	public Date getModule_endtime() {
		return module_endtime;
	}
	public void setModule_endtime(Date module_endtime) {
		this.module_endtime = module_endtime;
	}
	public String getIvr_id() {
		return ivr_id;
	}
	public void setIvr_id(String ivr_id) {
		this.ivr_id = ivr_id;
	}
	public String getModule_id() {
		return module_id;
	}
	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
