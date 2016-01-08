/**
 * 	@(#) TelService.java 2008-4-11 ����01:09:36
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>���ﰸ������ȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µİ�������action</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public interface HZCaseInfoStatisticsByStateService {
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */	
	public List userQuery(String sql);
	/**
	 * ����dto����Ĳ���ֵ��ִ��ͳ�ƣ������ظ���ͳ�ƽ�����ɵ�JFreeChart����
	 * @param dto
	 * @return JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto);
	/**
	 * ����DTO����Ĳ���ֵ��ִ��ͳ�ƣ�����ͳ�ƽ�����д������ɷ��ϱ����ʽ���б�
	 * @param dto
	 * @return List
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto);
}
