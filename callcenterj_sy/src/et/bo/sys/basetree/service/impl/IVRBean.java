/**
 * 
 */
package et.bo.sys.basetree.service.impl;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class IVRBean {

	private String id = "";
	
	private String label;

	private String parent_id = "";

	private String functype = "";

	private String nickname = "";

	private String content = "";

	private List<IVRBeanVoice> ivrList;
	
	private String voiceType;

	private String telnum = "";

	private String telkey = "";

	private String size = "";

	private String value = "";
	
	private String languageType="";
 	private String orderProgramme = "";
	private String customizeCancel = "";
	private String expertId = "";
	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFunctype() {
		return functype;
	}

	public void setFunctype(String functype) {
		this.functype = functype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTelkey() {
		return telkey;
	}

	public void setTelkey(String telkey) {
		this.telkey = telkey;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<IVRBeanVoice> getIvrList() {
		if(ivrList != null)
		{
			Collections.sort(ivrList);
		}
		return ivrList;
	}

	public void setIvrList(List<IVRBeanVoice> ivrList) {
		this.ivrList = ivrList;
	}

	public String getVoiceType() {
		return voiceType;
	}

	public void setVoiceType(String voiceType) {
		this.voiceType = voiceType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCustomizeCancel() {
		return customizeCancel;
	}

	public void setCustomizeCancel(String customizeCancel) {
		this.customizeCancel = customizeCancel;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getOrderProgramme() {
		return orderProgramme;
	}

	public void setOrderProgramme(String orderProgramme) {
		this.orderProgramme = orderProgramme;
	}

	
}
