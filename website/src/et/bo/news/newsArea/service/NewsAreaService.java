/**
 * 	@(#)NewsAreaService.java   2007-1-23 ����02:50:06
 *	 �� 
 *	 
 */
package et.bo.news.newsArea.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe
 * @author Ҷ����
 * @version 2007-1-23
 * @see
 */
public interface NewsAreaService {
	/**
	 * @describe �������
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void areaAdd(IBaseDTO dto);
	/**
	 * @describe0 �����޸�
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void areaUpdate(IBaseDTO dto);
	/**
	 * @describe ����ɾ��
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void areaDel(String id);
	/**
	 * @describe �����б�
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public List areaList(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe ȡ������
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public int getSize();
	/**
	 * @describe ȡ��������ϸ��Ϣ
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public IBaseDTO getAreaInfo(String id);
	/**
	 * @describe ���������б�
	 * @param
	 * @return <labelValueBean>
	 */
	public List<LabelValueBean> getAreaList();

}
