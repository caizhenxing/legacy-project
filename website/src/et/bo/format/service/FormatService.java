/**
 * 	@(#)FormatService.java   2007-1-22 ����10:12:48
 *	 �� 
 *	 
 */
package et.bo.format.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe
 * @author Ҷ����
 * @version 2007-1-22
 * @see
 */
public interface FormatService {
	/**
	 * @describe ��ʽ���
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void formatAdd(IBaseDTO dto);
	/**
	 * @describe0 ��ʽ�޸�
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void formatUpdate(IBaseDTO dto);
	/**
	 * @describe ��ʽɾ��
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void formatDel(String id);
	/**
	 * @describe ��ʽ�б�
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public List formatList(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe ȡ������
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public int getSize();
	/**
	 * @describe ȡ����ʽ��ϸ��Ϣ
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public IBaseDTO getFormatInfo(String id);
	/**
	 * @describe ��ʽ�����б�
	 * @param
	 * @return <labelValueBean>
	 */
	public List<LabelValueBean> getStyleList();
}
