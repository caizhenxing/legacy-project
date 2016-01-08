/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sms.sendAndReceive.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 短信收发查询
 * @author  荆玉琢
 * @version 1.0, 2008-03-27//
 * @see
 */
public interface sendAndReceiveService {
	/**
	 * @describe 查询已发送信息列表
	 * @param
	 * @return List
	 */ 
	public List sendQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getSendSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(类型)
	 */ 
	public IBaseDTO getSendInfo(String id);
	
	/**
	 * @describe 删除已发送信息
	 * @param
	 * @return void
	 */ 
	public void delSend(String id,String type);
	
	
	
	
	
	/**
	 * @describe 查询未发送列表
	 * @param
	 * @return List
	 */ 
	public List sendNotQuery(IBaseDTO dto, PageInfo pi);
	
	
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getSendNotSize();
	
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getReceivetSize();
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(类型)
	 */ 
	public IBaseDTO getSendNotInfo(String id);
	
	/**
	 * @describe 删除未发送信息
	 * @param
	 * @return void
	 */ 
	public void delSendNot(String id,String type);
	
	/**
	 * @describe 查询收到单条信息
	 * @param
	 * @return List
	 */
	public IBaseDTO receiveOneQuery(String num);
	/**
	 * @describe 查询收到信息列表
	 * @param
	 * @return List
	 */ 
	public List receiveQuery(IBaseDTO dto, PageInfo pi,String num);
	
	
	/**
	 * @describe 查询收到短信信息条数
	 * @param
	 * @return List
	 */ 
	public List receiveSizeQuery();
	

	/**
	 * @describe　短信删除
	 * @param
	 * @return void
	 */
	public void delMessage(String id);

	
}
