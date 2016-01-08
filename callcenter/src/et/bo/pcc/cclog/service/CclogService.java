/**
 * @(#)CclogService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.pcc.cclog.service;

import java.util.List;

import et.bo.callcenter.base.ConnectInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 呼叫中心日志接口
 * @author  叶浦亮
 * @version 1.0, 2006-10-08//
 * @see
 */
public interface CclogService {
    /**
	 * @describe 添加呼叫中心日志
	 * @param
	 * @return void
	 */
	public void addCclog(ConnectInfo ci);
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
}
