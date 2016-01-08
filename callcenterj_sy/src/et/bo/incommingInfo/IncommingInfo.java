/*
 * @(#)IncommingInfo.java	 2008-06-24
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.incommingInfo;

import et.po.CcMain;
import et.po.CcTalk;
import et.po.OperQuestion;

/**
 * 
 * @author 王文权
 * 简单的po 来电信息统计 CcMain OperQuestion CcTalk 的一些字段
 */
public class IncommingInfo{
	private String questionId;
	private String talkId;
	private String mainId;
	private String tel_num;	//来电
	private String addtimeBegin; //受理时间
	private String addtimeEnd; //受理时间
	private String dictQuestionType1; //资讯栏目
	private String respondent; //应答
	private String questionContent; //问题
	private String answerContent; //答案详细
	private String addtime; //受理时间
	
	
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAddtimeBegin() {
		return addtimeBegin;
	}
	public void setAddtimeBegin(String addtimeBegin) {
		this.addtimeBegin = addtimeBegin;
	}
	public String getAddtimeEnd() {
		return addtimeEnd;
	}
	public void setAddtimeEnd(String addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getDictQuestionType1() {
		return dictQuestionType1;
	}
	public void setDictQuestionType1(String dictQuestionType1) {
		this.dictQuestionType1 = dictQuestionType1;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getRespondent() {
		return respondent;
	}
	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}
	public String getTalkId() {
		return talkId;
	}
	public void setTalkId(String talkId) {
		this.talkId = talkId;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	
	
	
	
}
