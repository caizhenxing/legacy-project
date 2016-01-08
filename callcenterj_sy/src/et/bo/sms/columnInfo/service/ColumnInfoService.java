package et.bo.sms.columnInfo.service;

import java.util.List;

import et.po.ColumnInfo;
import et.po.ColumnInfoSendtime;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 
 * @author chen gang
 *
 */
public interface ColumnInfoService {
	/**
	 * 添加业务定制信息记录
	 * @param dto
	 * @return
	 */
	public boolean addColumnInfo(IBaseDTO dto);
	public ColumnInfo loadColunmInfo(String id);
	public void updateColumnInfo(ColumnInfo ci);
	
	/**
	 * 查找定制后还未退订的记录列表
	 * @param telNum
	 * @return
	 */
	public List columnInfoSearch(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 返回记录列表数量
	 * @return
	 */
	public int getColumnInfoSize();
	public DynaBeanDTO colunmInfoQuery(String id);
	/**
	 * 退订操作
	 * @param id
	 * @return
	 */
	public boolean cancelColInfo(String id);
	
	public ColumnInfoSendtime getCis(String nickname);
	
	public boolean addColumnInfoSendtime(String nickname, String sendTime);
	
	public boolean addColumnInfoMessage(IBaseDTO dto);
	
	/**
	 * 根据id获得发送短信息详细
	 * @param id
	 * @return
	 */
	public DynaBeanDTO getMessagesInfo(String id);
	
	/**
	 * 查询信息维护内容
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List messagesQuery(IBaseDTO dto, PageInfo pi);
	
	public int getMessagesSize();
	
	public boolean addMessage(IBaseDTO dto);
	
	public boolean updateMessage(IBaseDTO dto);
	
	public boolean deleteMessage(String messageId);
}
