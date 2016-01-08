/**
 * @(#)InstantMsgService.java	 06/05/12
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.oa.communicate.instant.service;


import java.util.List;

import excellence.framework.base.dto.IBaseDTO;

/**<code>InstantMsgService</code> is interface 
 * which contains a series of action about im
 * 
 * @author  yifei zhao
 * 
 * @version 06/05/12
 * @since   1.0

 */
public interface InstantMsgService {

	public void sendMsg(List<String> receivers,String content,String sendUser);
	public List<InstantMsg> receiveMsgs(String receiveUser,boolean clean);
	public boolean hasMsg(String receiveUser);
	public IBaseDTO receiveMsg(String receiveUser,boolean clean);
	public List<IBaseDTO> userList();
}
