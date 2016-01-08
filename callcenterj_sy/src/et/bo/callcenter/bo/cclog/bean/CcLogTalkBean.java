package et.bo.callcenter.bo.cclog.bean;

import java.util.Date;
/**
 * CcLog_Talk表对应的Bean
 * @author 陈岗
 */
public class CcLogTalkBean {
	private String ID; 	 // 流水号
	private String CCLOG_ID; 	 // cclog表的id
	private Date RING_BEGINTIME; 	 // 开始振铃时间
	private Date TOUCH_BEGINTIME; 	 // 开始接听时间
	private String WAITING_KEEPTIME; 	 // 等待时长
	private Date TOUCH_ENDTIME; 	 // 结束接听时间
	private String TOUCH_KEEPTIME; 	 // 接通时长
	private String TOUCH_POST; 	 // 接听端口
	private String RECORD_PATH;  //录音文件路径
	private String POST_TYPE; 	 // 端口类型
	private String RESPONDENT; 	 // 应答人（sys_use_id,expert_id)
	private String RESPONDENT_TYPE; 	 // 应答人类型(座席、专家、IVR）
	private String PHONE_NUM; 	 // 应答人电话号码
	private Date DISPLACE_BEGINTIME; 	 // 开始转接时间
	private String DISPLACE_KEEPTIME; 	 // 转接时长
	private String PROCESS_TYPE; 	 // 接续动作（从哪来到哪去)
	private String IS_DELETE; 	 // 删除标记
	private String REMARK; 	 // 备注
	private String ADDRESS;  //地址

	public void readCcLogTalkInfo() {
//		System.out.println("********************CcLogTalkINFO*********************");
//		System.out.println("ID = " + ID);
//		System.out.println("CCLOG_ID = " + CCLOG_ID);
//		System.out.println("RING_BEGINTIME = " + RING_BEGINTIME);
//		System.out.println("TOUCH_BEGINTIME = " + TOUCH_BEGINTIME);
//		System.out.println("WAITING_KEEPTIME = " + WAITING_KEEPTIME);
//		System.out.println("TOUCH_ENDTIME = " + TOUCH_ENDTIME);
//		System.out.println("TOUCH_KEEPTIME = " + TOUCH_KEEPTIME);
//		System.out.println("TOUCH_POST = " + TOUCH_POST);
//		System.out.println("POST_TYPE = " + POST_TYPE);
//		System.out.println("RESPONDENT = " + RESPONDENT);
//		System.out.println("RESPONDENT_TYPE = " + RESPONDENT_TYPE);
//		System.out.println("PHONE_NUM = " + PHONE_NUM);
//		System.out.println("DISPLACE_BEGINTIME = " + DISPLACE_BEGINTIME);
//		System.out.println("DISPLACE_KEEPTIME = " + DISPLACE_KEEPTIME);
//		System.out.println("PROCESS_TYPE = " + PROCESS_TYPE);
//		System.out.println("IS_DELETE = " + IS_DELETE);
//		System.out.println("REMARK = " + REMARK);
//		System.out.println("********************CcLogTalkINFO*********************");
	}
	
	public String getCCLOG_ID() {
		return CCLOG_ID;
	}
	public void setCCLOG_ID(String cclog_id) {
		CCLOG_ID = cclog_id;
	}
	public Date getDISPLACE_BEGINTIME() {
		return DISPLACE_BEGINTIME;
	}
	public void setDISPLACE_BEGINTIME(Date displace_begintime) {
		DISPLACE_BEGINTIME = displace_begintime;
	}
	public String getDISPLACE_KEEPTIME() {
		return DISPLACE_KEEPTIME;
	}
	public void setDISPLACE_KEEPTIME(String displace_keeptime) {
		DISPLACE_KEEPTIME = displace_keeptime;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getIS_DELETE() {
		return IS_DELETE;
	}
	public void setIS_DELETE(String is_delete) {
		IS_DELETE = is_delete;
	}
	public String getPHONE_NUM() {
		return PHONE_NUM;
	}
	public void setPHONE_NUM(String phone_num) {
		PHONE_NUM = phone_num;
	}
	public String getPOST_TYPE() {
		return POST_TYPE;
	}
	public void setPOST_TYPE(String post_type) {
		POST_TYPE = post_type;
	}
	public String getPROCESS_TYPE() {
		return PROCESS_TYPE;
	}
	public void setPROCESS_TYPE(String process_type) {
		PROCESS_TYPE = process_type;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String remark) {
		REMARK = remark;
	}
	public String getRESPONDENT() {
		return RESPONDENT;
	}
	public void setRESPONDENT(String respondent) {
		RESPONDENT = respondent;
	}
	public String getRESPONDENT_TYPE() {
		return RESPONDENT_TYPE;
	}
	public void setRESPONDENT_TYPE(String respondent_type) {
		RESPONDENT_TYPE = respondent_type;
	}
	public Date getRING_BEGINTIME() {
		return RING_BEGINTIME;
	}
	public void setRING_BEGINTIME(Date ring_begintime) {
		RING_BEGINTIME = ring_begintime;
	}
	public Date getTOUCH_BEGINTIME() {
		return TOUCH_BEGINTIME;
	}
	public void setTOUCH_BEGINTIME(Date touch_begintime) {
		TOUCH_BEGINTIME = touch_begintime;
	}
	public Date getTOUCH_ENDTIME() {
		return TOUCH_ENDTIME;
	}
	public void setTOUCH_ENDTIME(Date touch_endtime) {
		TOUCH_ENDTIME = touch_endtime;
	}
	public String getTOUCH_KEEPTIME() {
		return TOUCH_KEEPTIME;
	}
	public void setTOUCH_KEEPTIME(String touch_keeptime) {
		TOUCH_KEEPTIME = touch_keeptime;
	}
	public String getTOUCH_POST() {
		return TOUCH_POST;
	}
	public void setTOUCH_POST(String touch_post) {
		TOUCH_POST = touch_post;
	}
	public String getWAITING_KEEPTIME() {
		return WAITING_KEEPTIME;
	}
	public void setWAITING_KEEPTIME(String waiting_keeptime) {
		WAITING_KEEPTIME = waiting_keeptime;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String address) {
		ADDRESS = address;
	}
	public String getRECORD_PATH() {
		return RECORD_PATH;
	}
	public void setRECORD_PATH(String record_path) {
		RECORD_PATH = record_path;
	}
}
