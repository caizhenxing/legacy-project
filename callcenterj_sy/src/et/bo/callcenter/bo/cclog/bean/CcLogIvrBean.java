package et.bo.callcenter.bo.cclog.bean;

import java.util.Date;

public class CcLogIvrBean {
	private String id ; 	 // ��ˮ��
	private String cclog_id ; 	 // ���
	private Date ivr_begintime ; 	 // ��ʼ����ʱ��
	private Date ivr_endtime ; 	 // ���𷽽������̽���ʱ��
	private String process_type ; 	 // ��������������������ȥ)
	private String is_delete ; 	 // ɾ�����
	private String remark ; 	 // ��ע
	
	public void readCcLogIvrInfo() {
//		System.out.println("************************CcLogIvrINFO***************************");
//		System.out.println("id = " + id);
//		System.out.println("cclog_id = " + cclog_id);
//		System.out.println("ivr_begintime = " + ivr_begintime);
//		System.out.println("ivr_endtime = " + ivr_endtime);
//		System.out.println("process_type = " + process_type);
//		System.out.println("is_delete = " + is_delete);
//		System.out.println("remark = " + remark);
//		System.out.println("************************CcLogIvrINFO***************************");
	}
	public String getCclog_id() {
		return cclog_id;
	}
	public void setCclog_id(String cclog_id) {
		this.cclog_id = cclog_id;
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
	
	public Date getIvr_endtime() {
		return ivr_endtime;
	}
	public void setIvr_endtime(Date ivr_endtime) {
		this.ivr_endtime = ivr_endtime;
	}
	public Date getIvr_begintime() {
		return ivr_begintime;
	}
	public void setIvr_begintime(Date ivr_begintime) {
		this.ivr_begintime = ivr_begintime;
	}
	public String getProcess_type() {
		return process_type;
	}
	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
