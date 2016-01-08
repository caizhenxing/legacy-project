/**
 * 	@(#)VoiceMail.java   2007-3-2 ÉÏÎç10:32:36
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.connection.voicemail;

import java.util.ArrayList;
import java.util.List;

 /**
 * @author zhaoyifei
 * @version 2007-3-2
 * @see
 */
public class VoiceMail {

private List voices = new ArrayList();
    private String hasNew=null;
    public List getVoices() {
        return voices;
    }
    public void setVoices(List voices)
    {
    	this.voices=voices;
    }
    public void addVoice(String Voice) {
    	voices.add(Voice);
    }
	/**
	 * @return the hasNew
	 */
	public String getHasNew() {
		return hasNew;
	}
	/**
	 * @param hasNew the hasNew to set
	 */
	public void setHasNew(String hasNew) {
		this.hasNew = hasNew;
	}
}
