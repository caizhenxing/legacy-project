/* ��    ����et.bo.stat.service
 * �� �� ����FocusCaseInfoUserService.java
 * ע��ʱ�䣺2008-8-28 13:52:14
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */

package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Interface FocusCaseInfoUserService.
 * ���㰸����ͳ��֮��ϯ������ͳ��
 * @author Wang Lichun
 */
public interface FocusCaseInfoUserService {
	
	/**
	 * ����dto����Ĳ���ֵ��ִ��ͳ�ƣ������ظ���ͳ�ƽ�����ɵ�JFreeChart����.
	 * 
	 * @param dto the dto
	 * 
	 * @return the j free chart
	 */
	public JFreeChart statistic(IBaseDTO dto);
	
	/**
	 * ����DTO����Ĳ���ֵ��ִ��ͳ�ƣ�����ͳ�ƽ�����д������ɷ��ϱ����ʽ���б�.
	 * 
	 * @param dto the dto
	 * 
	 * @return the list< dyna bean dt o>
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto);
}
