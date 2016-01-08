package et.bo.screen.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface SpecialSurveyService {

	public int getRecordSize();

	/**
	 * ���һ��������Ϣ
	 * @param dto
	 */
	public void addSpecialSurvey(IBaseDTO dto);

	/**
	 * ɾ��һ��������Ϣ
	 * @param id
	 */
	public void delSpecialSurvey(String id);

	/**
	 * �޸�һ��������Ϣ
	 * @param dto
	 * @return  boolean
	 */
	public boolean updateSpecialSurvey(IBaseDTO dto);

	/**
	 * ��ʱ�� ���� �ؼ��� ׫���� ģ����ѯ
	 * @param dto
	 * @param pi
	 * @return List
	 */
	public List getSSSList(IBaseDTO dto, PageInfo pi);

	/**
	 * ��id�����ض�������Ϣ
	 * @param id
	 * @return ������¼
	 */
	public IBaseDTO getObjById(String id);
	/**
	 * �õ�����Ļ��Ϣ���б�
	 * @return
	 */
	public List screenList();

}