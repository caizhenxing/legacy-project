/**
 * @(#)LeaveWordService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.commoninfo.leaveWord.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe <code>LeaveWordService</code> is interface
 * @author 　叶浦亮
 * @version 1.0, //
 * @see
 * @see
 */
public interface LeaveWordService {
    /**
	 * @describe　录入留言板信息
	 * @param 
	 * @return 
	 * 
	 */
    public boolean addLeaveWordInfo(IBaseDTO dto);
    /**
	 * @describe 修改留言板信息
	 * @param
	 * @return
	 * 
	 */  
//    public boolean updateLeaveWordInfo(IBaseDTO dto);
    /**
	 * @describe 删除留言板信息
	 * @param
	 * @return
	 * 
	 */
    public boolean deleteLeaveWordInfo(IBaseDTO dto);
    /**
	 * @describe 得到条数
	 * @param
	 * @return
	 * 
	 */    
    public int getLeaveWordSize();
    /**
	 * @describe 根据条件查询留言板信息
	 * @param
	 * @return
	 * 
	 */    
    public List findLeaveWordInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe 根据条件查询留言板详细信息
	 * @param
	 * @return
	 * 
	 */    
    public List findSeeLeaveWordInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe 根据id查询信息load
	 * @param
	 * @return
	 * 
	 */
    public IBaseDTO getLeaveWordInfo(String id);
    /**
	 * @describe 判断是否有同名类别
	 * @param
	 * @return
	 * 
	 */
//    public boolean isHaveSameName (IBaseDTO dto);
    /**
	 * @describe 取得类别LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
//    public  List<LabelValueBean> getLabelList(String userId, String sign);
    /**
	 * @describe 根据类别Id取得类别名
	 * @param
	 * @return   类别名
	 * 
	 */
//    public String getSortNameById (String Id);

}
