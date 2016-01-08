/**
 * 	@(#)PhoneInfoService.java   Nov 7, 2006 9:35:40 AM
 *	 。 
 *	 
 */
package et.bo.pcc.phoneinfo;

import java.util.List;

import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Nov 7, 2006
 * @see
 */
public interface PhoneInfoService {
	
	/**
	 * 检查警号是否存在
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return 返回是否成功
	 */
	public boolean checkPoliceNum(String policeNum,String password);
	
	/**
	 * 得到警务人员详细信息
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return 返回警务人员详细信息
	 */
	public IBaseDTO getPoliceInfo(String policeNum);
	
	/**
	 * 添加警务人员信息，主表(返回主键)
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public String addPoliceCallin(IBaseDTO dto);
	
	/**
	 * 加入警员呼入信息
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public boolean addPoliceCallInInfo(PoliceCallInInfoInMemory pm);
	
	/**
	 * 查询警务人员呼入记录信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回警务人员列表信息
	 */
	public List callInInfoIndex(String pocid,String fuzznum);
	
	/**
	 * 得到警员询问问题信息
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public IBaseDTO getQuestionInfo(String id);
	
	/**
	 * 保存cclog信息
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public String saveCclog(String phonenum);
	
	/**
	 * 修改cclog信息
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public void upCclog(String id);
	
	/**
	 * 
	 * @param
	 * @version Nov 7, 2006
	 * @return
	 */
	public void saveCclogEnd(String phonenum);
}
