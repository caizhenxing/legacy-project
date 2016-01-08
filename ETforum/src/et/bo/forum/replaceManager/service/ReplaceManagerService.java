/**
 * 	@(#)ReplaceManagerService.java   2006-12-25 œ¬ŒÁ04:03:01
 *	 °£ 
 *	 
 */
package et.bo.forum.replaceManager.service;

/**
 * @author “∂∆÷¡¡
 * @version 2006-12-25
 * @see
 */
public interface ReplaceManagerService {
	public void addRule(String ruleArray);
	public void updateRule(String id);
	public void delRule(String id);
	public String getRule();
	public String postReplace(String postInfo);
	

}
