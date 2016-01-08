package et.po;

import java.util.Date;

/**
 * OperMarkanainfo generated by MyEclipse Persistence Tools
 */

public class OperMarkanainfo implements java.io.Serializable {

	// Fields

	private String markanaId;

	private String frontFor;

	private String underTake;

	private String period;

	private String dictProductType;

	private String dictCommentType;

	private Date createTime;

	private String sendUnit;

	private String chiefEditor;

	private String subEditor;

	private String firstEditor;

	private String chargeEditor;

	private String supportTel;

	private String supportSite;

	private String contactMail;

	private String productLabel;

	private String chiefTitle;

	private String subTitle;

	private String summary;

	private String markanaContent;

	private String checkAdvise1;

	private String checkAdvise2;

	private String checkAdvise3;

	private String state;

	private String comments;

	private String uploadfile;
	
	private String caserid;

	// Constructors

	/** default constructor */
	public OperMarkanainfo() {
	}

	/** minimal constructor */
	public OperMarkanainfo(String markanaId) {
		this.markanaId = markanaId;
	}

	/** full constructor */
	public OperMarkanainfo(String markanaId, String frontFor, String underTake, String period, String dictProductType, String dictCommentType, Date createTime, String sendUnit, String chiefEditor,
			String subEditor, String firstEditor, String chargeEditor, String supportTel, String supportSite, String contactMail, String productLabel, String chiefTitle, String subTitle,
			String summary, String markanaContent, String checkAdvise1, String checkAdvise2, String checkAdvise3, String state, String comments, String uploadfile, String caserid) {
		this.markanaId = markanaId;
		this.frontFor = frontFor;
		this.underTake = underTake;
		this.period = period;
		this.dictProductType = dictProductType;
		this.dictCommentType = dictCommentType;
		this.createTime = createTime;
		this.sendUnit = sendUnit;
		this.chiefEditor = chiefEditor;
		this.subEditor = subEditor;
		this.firstEditor = firstEditor;
		this.chargeEditor = chargeEditor;
		this.supportTel = supportTel;
		this.supportSite = supportSite;
		this.contactMail = contactMail;
		this.productLabel = productLabel;
		this.chiefTitle = chiefTitle;
		this.subTitle = subTitle;
		this.summary = summary;
		this.markanaContent = markanaContent;
		this.checkAdvise1 = checkAdvise1;
		this.checkAdvise2 = checkAdvise2;
		this.checkAdvise3 = checkAdvise3;
		this.state = state;
		this.comments = comments;
		this.uploadfile = uploadfile;
		this.caserid = caserid;
	}

	// Property accessors

	public String getMarkanaId() {
		return this.markanaId;
	}

	public void setMarkanaId(String markanaId) {
		this.markanaId = markanaId;
	}

	public String getFrontFor() {
		return this.frontFor;
	}

	public void setFrontFor(String frontFor) {
		this.frontFor = frontFor;
	}

	public String getUnderTake() {
		return this.underTake;
	}

	public void setUnderTake(String underTake) {
		this.underTake = underTake;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDictProductType() {
		return this.dictProductType;
	}

	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}

	public String getDictCommentType() {
		return this.dictCommentType;
	}

	public void setDictCommentType(String dictCommentType) {
		this.dictCommentType = dictCommentType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSendUnit() {
		return this.sendUnit;
	}

	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}

	public String getChiefEditor() {
		return this.chiefEditor;
	}

	public void setChiefEditor(String chiefEditor) {
		this.chiefEditor = chiefEditor;
	}

	public String getSubEditor() {
		return this.subEditor;
	}

	public void setSubEditor(String subEditor) {
		this.subEditor = subEditor;
	}

	public String getFirstEditor() {
		return this.firstEditor;
	}

	public void setFirstEditor(String firstEditor) {
		this.firstEditor = firstEditor;
	}

	public String getChargeEditor() {
		return this.chargeEditor;
	}

	public void setChargeEditor(String chargeEditor) {
		this.chargeEditor = chargeEditor;
	}

	public String getSupportTel() {
		return this.supportTel;
	}

	public void setSupportTel(String supportTel) {
		this.supportTel = supportTel;
	}

	public String getSupportSite() {
		return this.supportSite;
	}

	public void setSupportSite(String supportSite) {
		this.supportSite = supportSite;
	}

	public String getContactMail() {
		return this.contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getProductLabel() {
		return this.productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public String getChiefTitle() {
		return this.chiefTitle;
	}

	public void setChiefTitle(String chiefTitle) {
		this.chiefTitle = chiefTitle;
	}

	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getMarkanaContent() {
		return this.markanaContent;
	}

	public void setMarkanaContent(String markanaContent) {
		this.markanaContent = markanaContent;
	}

	public String getCheckAdvise1() {
		return this.checkAdvise1;
	}

	public void setCheckAdvise1(String checkAdvise1) {
		this.checkAdvise1 = checkAdvise1;
	}

	public String getCheckAdvise2() {
		return this.checkAdvise2;
	}

	public void setCheckAdvise2(String checkAdvise2) {
		this.checkAdvise2 = checkAdvise2;
	}

	public String getCheckAdvise3() {
		return this.checkAdvise3;
	}

	public void setCheckAdvise3(String checkAdvise3) {
		this.checkAdvise3 = checkAdvise3;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUploadfile() {
		return this.uploadfile;
	}

	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getCaserid() {
		return caserid;
	}

	public void setCaserid(String caserid) {
		this.caserid = caserid;
	}

}