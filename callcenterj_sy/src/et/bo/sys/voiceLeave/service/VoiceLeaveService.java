package et.bo.sys.voiceLeave.service;

/*
 * @(#)CustinfoService.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>��������</p>
 * 
 * @version 2008-03-19
 * @author wangwenquan
 */

public interface VoiceLeaveService {
	/*
        List list = custinfoService.custinfoQuery(dto,pageInfo);
        int size = custinfoService.getCustinfoSize();
	 */
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List voiceLeaveQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * ����id�õ���ϸ��Ϣ����dto��
	 * @param id
	 * @return
	 */
	public IBaseDTO getVoiceLeaveInfo(String id);
	/**
	 * ִ�д��� ��ifdispose �� 1 disposeSuggest ��ֵ
	 * @param dto ���ݴ������
	 */
	public void execDoWith(IBaseDTO dto);
	/**
	 * ��ѯ�����б��������
	 * ȡ�ò�ѯ�б��������
	 * @return �õ�list������
	 */
	public int getVoiceLeaveSize(); 
	/**
	 * ȡ�����ڵ��б�
	 * 
	 * @return �����������ݵ�list
	 */
	public List<LabelValueBean> getVoiceNodeList();
	/**
	 * ����idȥnickName
	 * 
	 */
	public String getNickNameById(String id);
}


