/**
 * 	@(#)VoiceFileChange.java   2007-1-16 ÉÏÎç11:14:59
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation;

 /**
 * @author zhaoyifei
 * @version 2007-1-16
 * @see
 */
public interface VoiceFileChange {

	public abstract boolean pcmToWave(String source,String target);
	public abstract boolean waveToPcm(String source,String target);
	public abstract boolean adToPcm(String source,String target);
	public abstract boolean pcmToAd(String source,String target);
	public abstract boolean ad6ToPcm(String source,String target);

}
