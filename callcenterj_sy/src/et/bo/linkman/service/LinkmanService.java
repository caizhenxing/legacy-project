/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.linkman.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ��ҵ����
 * @author  ������
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface LinkmanService {
	/**
	 * @describe �������Ա
	 * @param
	 * @return void
	 */ 
	public void addOperCorpinfo(IBaseDTO dto);
	/**
	 * @describe �޸�����Ա
	 * @param
	 * @return void
	 */ 
	public boolean updateOperCorpinfo(IBaseDTO dto);
	/**
	 * @describe ɾ������Ա
	 * @param
	 * @return void
	 */ 
	public void delOperCorpinfo(String id);
	
	
	/**
	 * @describe ��ѯ����Ա�б�
	 * @param
	 * @return List
	 */ 
	public List linkmanQuery(IBaseDTO dto, PageInfo pi,int pageState);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getLinkmanSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getOperCorpinfo(String id);
	
}
