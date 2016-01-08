/**
 * 
 */
package et.bo.callcenter.cclog.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public interface CclogMainService {
	/**
	 * �����Ϣ
	 * @param pid Ϊ����id
	 * @version Oct 30, 2006
	 * @return
	 */
	public void addQuestionInfo(IBaseDTO dto,String pid);
	/**
	 * �޸���Ϣ
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public void upQuestionInfo(IBaseDTO dto);
	
	/**
	 * @describe ��ѯ����������־
	 * @param
	 * @return List  Cclog����
	 */
	public List queryCclog(IBaseDTO dto, PageInfo pi);
    /**
	 * @describe �õ���־����
	 * @param
	 * @return int
	 * 
	 */    
    public int getCclogSize();
    /**
	 * @describe �õ���־��ϸ��Ϣ
	 * @param
	 * @return IBaseDTO ��־dto
	 */
    public IBaseDTO getCclogInfo(String id);
    
    public List listPhoneInfo(String id);
    IBaseDTO listFuzzInfo(String id);
    
    /**
     * �õ���־��Ϣ���б�
     * @param
     * @version Oct 30, 2006
     * @return
     */
    public List queryCcLogInfo(IBaseDTO dto, PageInfo pi,String begintime,String opernum);
    public int getCcLogInfoSize();
    
    public List getDepInfo(IBaseDTO dto);
    /**
     * ���ݵ绰��Ϣ�õ�info
     * @param
     * @version Apr 13, 2007
     * @return
     */
    public IBaseDTO getInfo(String cclogId, String cctalkId);
	
	/**
     * ������ϸ���id�õ��������ϸ��Ϣ����ϸ��Ϣ
     * @param
     * @version Apr 13, 2007
     * @return
     */
	public IBaseDTO getQuestionInfo(String id);
	
	/**
	 * ��ӻش�ɹ����˵���Ϣ
	 * @param dto
	 * @param id
	 */
	public void addCclogInfo(IBaseDTO dto,String id);
	
	/**
	 * ��ѯ���쵱ǰ��ϯԱ���������б�
	 * 2008-02-27 modify by chengang
	 * @param dto
	 * @return
	 */
	public List queryQuestion(IBaseDTO dto);
	
	/**
	 * ������ϯ���Ҽ�¼
	 * @param num
	 * @return
	 */
	public List queryQuestionByAgent(String num);
	
	/**
	 * ��������id��ѯIVR��Ϣ
	 * @param cclogId
	 * @return
	 */
	public List queryIVRInfo(String cclogId);
	
	/**
	 * ��������id��ѯ������Ϣ
	 * @param cclogId
	 * @return
	 */
	public List queryQuesInfo(String cclogId);
	
	/**
	 * ��ѯ������ϸ��Ϣ
	 * @param questionId
	 * @return
	 */
	public IBaseDTO getQuesInfo(String questionId);
	
	/**
	 * ������ϯԱ����ѯ�䵱��Ӧ��绰��¼����Ϣ
	 * @param userName
	 * @return
	 */
	public List queryRecord(String userName, String telNum, PageInfo pi);
	/**
	 * ������ϯԱ����ѯ�䵱��Ӧ��绰��¼����Ϣ
	 * @return
	 */
	List queryRecord(String un,String telNum, String beginTime, String endTime, PageInfo pageInfo);
	public int getRecordSize();
	
	public List userQuery(String sql);
}
