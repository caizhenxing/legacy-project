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
	 * 添加信息
	 * @param pid 为主表id
	 * @version Oct 30, 2006
	 * @return
	 */
	public void addQuestionInfo(IBaseDTO dto,String pid);
	/**
	 * 修改信息
	 * @param
	 * @version Oct 30, 2006
	 * @return
	 */
	public void upQuestionInfo(IBaseDTO dto);
	
	/**
	 * @describe 查询呼叫中心日志
	 * @param
	 * @return List  Cclog类型
	 */
	public List queryCclog(IBaseDTO dto, PageInfo pi);
    /**
	 * @describe 得到日志条数
	 * @param
	 * @return int
	 * 
	 */    
    public int getCclogSize();
    /**
	 * @describe 得到日志详细信息
	 * @param
	 * @return IBaseDTO 日志dto
	 */
    public IBaseDTO getCclogInfo(String id);
    
    public List listPhoneInfo(String id);
    IBaseDTO listFuzzInfo(String id);
    
    /**
     * 得到日志信息的列表
     * @param
     * @version Oct 30, 2006
     * @return
     */
    public List queryCcLogInfo(IBaseDTO dto, PageInfo pi,String begintime,String opernum);
    public int getCcLogInfoSize();
    
    public List getDepInfo(IBaseDTO dto);
    /**
     * 根据电话信息得到info
     * @param
     * @version Apr 13, 2007
     * @return
     */
    public IBaseDTO getInfo(String cclogId, String cctalkId);
	
	/**
     * 根据明细表的id得到问题的详细信息，明细信息
     * @param
     * @version Apr 13, 2007
     * @return
     */
	public IBaseDTO getQuestionInfo(String id);
	
	/**
	 * 添加回答成功的人的信息
	 * @param dto
	 * @param id
	 */
	public void addCclogInfo(IBaseDTO dto,String id);
	
	/**
	 * 查询当天当前座席员接受问题列表
	 * 2008-02-27 modify by chengang
	 * @param dto
	 * @return
	 */
	public List queryQuestion(IBaseDTO dto);
	
	/**
	 * 根据座席查找记录
	 * @param num
	 * @return
	 */
	public List queryQuestionByAgent(String num);
	
	/**
	 * 根据主表id查询IVR信息
	 * @param cclogId
	 * @return
	 */
	public List queryIVRInfo(String cclogId);
	
	/**
	 * 根据主表id查询问题信息
	 * @param cclogId
	 * @return
	 */
	public List queryQuesInfo(String cclogId);
	
	/**
	 * 查询问题详细信息
	 * @param questionId
	 * @return
	 */
	public IBaseDTO getQuesInfo(String questionId);
	
	/**
	 * 根据座席员名查询其当天应答电话的录音信息
	 * @param userName
	 * @return
	 */
	public List queryRecord(String userName, String telNum, PageInfo pi);
	/**
	 * 根据座席员名查询其当天应答电话的录音信息
	 * @return
	 */
	List queryRecord(String un,String telNum, String beginTime, String endTime, PageInfo pageInfo);
	public int getRecordSize();
	
	public List userQuery(String sql);
}
