/**
 * 
 */
package et.bo.sys.bak.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 *
 */
public interface BakService {
	
	/**
	 * ���ڽ��и������ݿ�ı���
	 *
	 */
	public void backup();
	
	/**
	 * ���ñ����ֶ�����
	 *
	 */
	public void backupSet();
	
	/**
	 * ��������
	 *
	 */
	public void startBakupImmediate();
	
	/**
	 * @describe ��������ݿ�ı�������
	 * @param
	 * @return void
	 */ 
	public void addBak(IBaseDTO dto);
	/**
	 * @describe �޸����ݿ�ı�������
	 * @param
	 * @return void
	 */ 
	public boolean updateBak(IBaseDTO dto);
	/**
	 * @describe ɾ�����ݿ�ı�������
	 * @param
	 * @return void
	 */ 
	public void delBak(String id);
	
	
	/**
	 * @describe ��ѯ���ݿ�ı��������б�
	 * @param
	 * @return List
	 */ 
	public List bakQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getBakSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getBakInfo(String id);
	

}
