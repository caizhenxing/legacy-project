/**
 * 	@(#)AgroKonwledgeService.java   2007-4-5 ����08:52:26
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.callcenter.bo.cclog.service;

import et.bo.callcenter.serversocket.panel.impl.CtiBean;

/**
 * CclogMain�����ɾ��
 *
 * @version 2008-05-06 
 * @author ����Ȩ
 */
public interface CcMainService {
	/**
	 * ��CtiBeanת����CcMain�������ݿ���
	 * @param ctiBean
	 */
	void addCcLogMain(CtiBean ctiBean);
	/**
	 * ��ctiBeanת����CcMain�޸����ݿ�
	 * @param ctiBean
	 */
	void updateCcLogMain(CtiBean ctiBean);
}
