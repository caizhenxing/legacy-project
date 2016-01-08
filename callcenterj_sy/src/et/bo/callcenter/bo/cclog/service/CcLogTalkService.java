package et.bo.callcenter.bo.cclog.service;

import java.util.ArrayList;

import et.bo.callcenter.bo.cclog.bean.CcLogTalkBean;
import et.po.CcMain;

/**
 * @describe CcLog��־���
 * @param
 * @version 2007-7-26
 * @return
 */
public interface CcLogTalkService {
	/**
	 * �����õĽӿ�cclog_main
	 * @param id
	 * @return
	 */
	public CcMain loadMainValue(String id);
	/**
	 * cclog_talk
	 * @param id
	 * @return
	 */
	public String loadValue(String id);
	
	public void addCcLogTalk(ArrayList<CcLogTalkBean> list);
	
}
