package et.bo.callcenter.bo.cclog.bean;

import java.util.Date;
/**
 * CcLog表对应的Bean
 * @author 陈岗
 *
 */
public class CcLogBean {
	private String id ; 	 // 流水号
	private String begin_post ; 	 // 发起端口
	private String post_type ; 	 // 端口类型
	private String tel_num ; 	 // 来电号码
	private Date ring_begintime ; 	 // 开始震铃时间
	private Date process_endtime ; 	 // 发起方接续过程结束时间
	private String record_path ; 	 // 录音文件路径
	private Date record_buildtime ; 	 // 记录创建时间
	private String process_keeptime ; 	 // 接续过程时长（PROCESS_ENDTIME-SHAKE_BEGINTIME）
	private String process_type ; 	 // 接续动作（从哪来到哪去)
	private String is_delete ; 	 // 删除标记
	private String remark ; 	 // 备注
	private String policeNum;
	
	public void readCcLogInfo() {
//		System.out.println("************************CcLogINFO***************************");
//		System.out.println("id = " + id);
//		System.out.println("begin_post = " + begin_post);
//		System.out.println("post_type = " + post_type);
//		System.out.println("tel_num = " + tel_num);
//		System.out.println("ring_begintime = " + ring_begintime);
//		System.out.println("process_endtime = " + process_endtime);
//		System.out.println("record_path = " + record_path);
//		System.out.println("record_buildtime = " + record_buildtime);
//		System.out.println("process_keeptime = " + process_keeptime);
//		System.out.println("process_type = " + process_type);
//		System.out.println("is_delete = " + is_delete);
//		System.out.println("remark = " + remark);
//		System.out.println("policeNum = " + policeNum);
//		System.out.println("************************CcLogINFO***************************");
	}
	
	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}

	public String getBegin_post() {
		return begin_post;
	}
	public void setBegin_post(String begin_post) {
		this.begin_post = begin_post;
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
	public String getPost_type() {
		return post_type;
	}
	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}
	public Date getProcess_endtime() {
		return process_endtime;
	}
	public void setProcess_endtime(Date process_endtime) {
		this.process_endtime = process_endtime;
	}
	public String getProcess_keeptime() {
		return process_keeptime;
	}
	public void setProcess_keeptime(String process_keeptime) {
		this.process_keeptime = process_keeptime;
	}
	public String getProcess_type() {
		return process_type;
	}
	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}
	public Date getRecord_buildtime() {
		return record_buildtime;
	}
	public void setRecord_buildtime(Date record_buildtime) {
		this.record_buildtime = record_buildtime;
	}
	public String getRecord_path() {
		return record_path;
	}
	public void setRecord_path(String record_path) {
		this.record_path = record_path;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getRing_begintime() {
		return ring_begintime;
	}
	public void setRing_begintime(Date ring_begintime) {
		this.ring_begintime = ring_begintime;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	
}
