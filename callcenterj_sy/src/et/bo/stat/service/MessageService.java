/**
 * 	@(#) MessageService.java 2008-4-15 ����09:54:24
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.stat.service;

import java.util.List;

import org.jfree.chart.JFreeChart;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author liangyunfeng
 *
 */
public interface MessageService {
	/**
	 * ����dto����Ĳ���ֵ��ִ��ͳ�ƣ������ظ���ͳ�ƽ�����ɵ�JFreeChart����
	 * @param dto
	 * @return
	 */
	public JFreeChart statistic(IBaseDTO dto);
	/**
	 * ����DTO����Ĳ���ֵ��ִ��ͳ�ƣ�����ͳ�ƽ�����д��������ɷ��ϱ�����ʽ���б�
	 * @param dto
	 * @return
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto);
}