/**
 * 	@(#) TelService.java 2008-6-26 11:01
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichcun
 *��ҵ��Ϣ��֮��Ϣ������ͳ��
 */
public interface CorpInfoByServiceTypeService {
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
