/**
 * 沈阳卓越科技有限公司
 * 2008-4-22
 */
package et.bo.sms.modermSend.service;

import java.util.List;


import et.bo.sms.modermSend.service.impl.SMSContent;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 荆玉琢
 * @version 1.0
 * 
 */
public interface SmsSendNewService {

	/**
	 * @describe 单发功能
	 * @param
	 * @return void
	 */
	public void sendToOne(SMSContent smscontent);

	/**
	 * @describe 群发功能
	 * @param
	 * @return void
	 */
	public void sendToGroup(SMSContent smscontent);

	/**
	 * @describe 在保存定时发送信息和草稿时,调用此方法.
	 * @param
	 * @return void
	 */
	public boolean saveDraft(SMSContent smscontent, IBaseDTO dto);

	/**
	 * @describe 在发送信息成功的时候，把相应的信息内容分别写入发送信息内容表和已发送信息表.
	 * @param
	 * @return void
	 */
	public boolean saveSuccess(SMSContent smscontent);

	/**
	 * 根据群组id查询该群组联系人
	 * 
	 * @param groupId
	 * @return
	 */
	public List linkManQuery(String groupId);

	/**
	 * 返回通讯录群组列表
	 * 
	 * @return
	 */
	public List linkGroupQuery();
	
	

	/**
	 * 根据群组id查询该群组联系人
	 * 
	 * @param groupId
	 * @return String
	 */
	public String telByLinkMan(String id);
	
	/**
	 * 查询数据列表,返回全部记录的list。
	 * 取得查询列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List custinfoAllQuery();
	
	public List getUserList(String userType);
}