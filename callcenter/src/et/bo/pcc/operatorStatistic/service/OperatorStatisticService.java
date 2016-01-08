/**
 * @(#)OperatorStatisticService.java 1.0 //
 * 
 *  ��  
 * 
 */
package et.bo.pcc.operatorStatistic.service;

import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe ��ϯԱ�������ͳ�ƽӿ�
 * @author Ҷ����
 * @version 1.0, 2006--10-09 //
 * @see
 */
public interface OperatorStatisticService {
    /**
	 * @describe ǩ���¼��� 
	 * @param    String operator(����Աid)
	 * @return
	 */
	public void addSignIn(String operator);
	/**
	 * @describe ǩ����¼���
	 * @param    String operator(����Աid)
	 * @return
	 */
	public void addSignOut(String operator);
	/**
	 * @describe ��ϯ��¼���
	 * @param    String operator(����Աid)
	 * @return
	 */
	public void addSetting(String operator);
	/**
	 * @describe ��ϯ��¼���
	 * @param    String operator(����Աid)
	 * @return
	 */
	public void addOutSetting(String operator);
	/**
	 * @describe �����绰��¼��� 
	 * @param    String operator(����Աid)
	 * @return
	 */
	public void addAnswerPhone(String operator ,Date date);
	/**
	 * @describe �Ҷϵ绰��¼���
	 * @param    String operator(����Աid)
	 * @return
	 */
	public void addDisconnectPhone(String operator ,Date date);

	/**
	 * @describe ��ϯԱ�������ͳ��
	 * @param    
	 * @return List
	 */
	public List operatorWorkInfoQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe ��ϯԱ���������ϸͳ��(��������ʱ����ϸ)
	 * @param
	 * @return List
	 */
	public List operatorWorkInfoDetailQuery(IBaseDTO dto);
	/**
	 * @describe ��ϯԱ��ϯ���ͳ�Ʋ�ѯ
	 * @param
	 * @return List
	 */
	public List operatorChuXiQuery(IBaseDTO dto);
    /**
	 * @describe ��ϯԱ�������ͳ�Ʋ�ѯ
	 * @param
	 * @return List
	 */
	public List operatorJieTingQuery(IBaseDTO dto);
	/**
	 * @describe ��ϯԱ�뿪���ͳ�Ʋ�ѯ
	 * @param
	 * @return List
	 */
	public List operatorOutQuery(IBaseDTO dto);
	/**
	 * @describe �õ���¼����
	 * @param
	 * @return int 
	 */
	public int getOperatorWorkInfoSize();
	/**
	 * @describe ��ϯԱ�б�
	 * @param
	 * @return <labelValueBean>
	 */
	public List<LabelValueBean> getWorkInfoPersonList();
	/**
	 * @describe ��ϯԱ��ϯʱ��
	 * @param
	 * @return List
	 */
	public List chuXiTimeQuery(IBaseDTO dto);
	/**
	 * @describe ��ϯԱ�뿪ʱ���ѯ
	 * @param
	 * @return List
	 */
	public List outTimeQuery(IBaseDTO dto);
	/**
	 * @describe ��ϯԱ����ʱ���ѯ
	 * @param
	 * @return List
	 */
	public List jieTingTimeQuery(IBaseDTO dto);
}
