/**
 * 	@(#)PoliceInfoService.java   Oct 10, 2006 9:08:38 AM
 *	 。 
 *	 
 */
package et.bo.pcc.policeinfo;

import java.util.List;

import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 10, 2006
 * @see
 */
public interface PoliceInfoService {
	
	/**
	 * 检查警号是否存在
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return 返回是否成功
	 */
	public boolean checkPoliceNum(String policeNum,String password);
	
	/**
	 * 确认此警务人员信息是否核实
	 * @param dto IBaseDTO
	 * @version Oct 10, 2006
	 * @return 返回检查是否属实
	 */
	public boolean checkMorePolice(IBaseDTO dto);
	
	/**
	 * 得到警务人员详细信息
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return 返回警务人员详细信息
	 */
	public IBaseDTO getPoliceInfo(String policeNum);
	
	/**
	 * 加入呼入查询记录(写入内存map)
	 * @param
	 * @version Oct 10, 2006
	 * @return
	 */
	public boolean addPoliceCallInInfo(PoliceCallInInfoInMemory pm);
	
	/**
	 * 批量导入数据库
	 * @param policeCallInInfo List
	 * @version Oct 10, 2006
	 * @return
	 */
	public boolean addBatchPoliceCallInInfo(List policeCallInInfo);
	
	/**
	 * 查询警务人员呼入记录信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回警务人员列表信息
	 */
	public List callInInfoIndex(String pocid);
	
	/**
	 * 得到内存中的id号根据座席号
	 * @param
	 * @version Oct 14, 2006
	 * @return
	 */
	public String getPoliceIdByOpNum(String operatornum);
	
	/**
	 * 将temp表数据删除,将值存入实际数据表
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回警务人员列表信息
	 */
	public boolean finishOper(String pocid);
	
	public void upTable(String pocid);
	
	public boolean insertValue(String pocid);
	
	public IBaseDTO getQuestionInfo(String id);
	
	/**
	 * 将policecallin中的值
	 * @param
	 * @version Dec 7, 2006
	 * @return
	 */
	public void transactBefore();
}
