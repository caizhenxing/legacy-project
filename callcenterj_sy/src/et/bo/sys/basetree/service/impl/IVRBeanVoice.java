/**
 * 沈阳卓越科技有限公司
 */
package et.bo.sys.basetree.service.impl;

/**
 * 语音文件的列表
 * 
 * @author zhangfeng
 * 
 */
public class IVRBeanVoice implements Comparable{

	private String id;

	private String mainParentId;

	private String mainId;

	private String layOrder;

	private String voicePath;
	
	private String isUse;
	
	private String languageType;

	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLayOrder() {
		return layOrder;
	}

	public void setLayOrder(String layOrder) {
		this.layOrder = layOrder;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getMainParentId() {
		return mainParentId;
	}

	public void setMainParentId(String mainParentId) {
		this.mainParentId = mainParentId;
	}

	public String getVoicePath() {
		return voicePath;
	}

	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		IVRBeanVoice voice = (IVRBeanVoice) o;
		String layOrder = this.getLayOrder();
		if(layOrder == null)
		{
			layOrder = "";
		}
		String voiceOrder = voice.getLayOrder();
		if(voiceOrder == null)
		{
			voiceOrder = "";
		}
		//System.out.println(layOrder+"|"+voiceOrder+":"+layOrder.compareTo(voiceOrder));
		return layOrder.compareTo(voiceOrder);
	}
}
