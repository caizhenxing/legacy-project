/**
 * 	@(#)OperatorListen.java   Oct 8, 2006 3:35:20 PM
 *	 。 
 *	 
 */
package et.bo.pcc.operatorlisten;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 8, 2006
 * @see
 */
public interface OperatorListenService {
	
	/**
	 * 查询座席监控信息
	 * @param dto 类型 IBaseDTO 座席信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List operatorListenIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getOperatorListenSize();
	
	/**
	 * 查询操作员列表 
	 * IBaseDTO
	 * @param
	 * @version Sep 12, 2006
	 * @return
	 */
	public List<LabelValueBean> userlist();
	
}
