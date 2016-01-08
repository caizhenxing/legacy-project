/**
 * 	@(#)QuestionService.java   Oct 11, 2006 1:21:27 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.assay.question.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public interface QuestionService {
	
	/**
	 * ��ѯ��ϯ�����Ϣ
	 * @param dto ���� IBaseDTO ��ϯ��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List questionIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getQuestionSize();
	
	/**
	 * �õ�������Ϣ
	 * @param id ���� String ������
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getQuestionInfo(String id);

}
