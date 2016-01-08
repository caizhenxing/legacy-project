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
	 * ���ҵ������Ϣ��¼
	 * @param dto
	 * @return
	 */
	public boolean addColumnInfo(IBaseDTO dto);
	public ColumnInfo loadColunmInfo(String id);
	public void updateColumnInfo(ColumnInfo ci);
	
	/**
	 * ���Ҷ��ƺ�δ�˶��ļ�¼�б�
	 * @param telNum
	 * @return
	 */
	public List columnInfoSearch(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ���ؼ�¼�б�����
	 * @return
	 */
	public int getColumnInfoSize();
	public DynaBeanDTO colunmInfoQuery(String id);
	/**
	 * �˶�����
	 * @param id
	 * @return
	 */
	public boolean cancelColInfo(String id);
	
	public ColumnInfoSendtime getCis(String nickname);
	
	public boolean addColumnInfoSendtime(String nickname, String sendTime);
	
	public boolean addColumnInfoMessage(IBaseDTO dto);
	
	/**
	 * ����id��÷��Ͷ���Ϣ��ϸ
	 * @param id
	 * @return
	 */
	public DynaBeanDTO getMessagesInfo(String id);
	
	/**
	 * ��ѯ��Ϣά������
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
