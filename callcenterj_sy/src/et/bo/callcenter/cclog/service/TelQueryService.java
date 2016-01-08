/**
 * 
 */
package et.bo.callcenter.cclog.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public interface TelQueryService {
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */	
	public List userQuery(String sql);
	/**
   * @describe ��ѯ�绰�б�
   * @param
   * @return List
   */
	public List telQuery(IBaseDTO dto, PageInfo pi);
	
	public int getSize();
	
	public List getTel(IBaseDTO dto, PageInfo pi,String pageState);
	/**
	 * �����绰��ͳ��ͼ��xml
	 *
	 */
	public void createXml();
}
