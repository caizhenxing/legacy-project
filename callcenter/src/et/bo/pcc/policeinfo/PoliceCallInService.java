/**
 * 	@(#)PoliceCallInService.java   Oct 11, 2006 3:17:06 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policeinfo;

import java.util.Set;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public interface PoliceCallInService {
	
	/**
	 * 添加公安呼入信息和查询内容
	 * @param policeCallInDTO IBaseDTO 公安呼入信息（流水号，警号等）
	 * @param policeCallInInfo Set 存入pojo类对象 
	 * @version Oct 11, 2006
	 * @return true 保存成功 false 保存失败
	 */
	public boolean addPoliceCallInInfo(et.bo.callcenter.business.impl.PoliceCallin poc);
	
}
