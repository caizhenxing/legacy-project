/**
 * 	@(#)PoliceFuzzService.java   Oct 9, 2006 2:18:45 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policefuzz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 9, 2006
 * @see
 */
public interface PoliceFuzzService {
	
	/**
	 * 录入警务人员信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addFuzzInfo(IBaseDTO dto);
	
	/**
	 * 修改警务人员信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean updateFuzzInfo(IBaseDTO dto);
	
	/**
	 * 删除警务人员信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean delFuzzInfo(IBaseDTO dto);
	
	/**
	 * 得到警务人员信息
	 * @param id 类型 String 警务人员
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getFuzzInfo(String id);
	
	
	/**
	 * 得到警务人员详细信息
	 * @param policeNum String
	 * @version Oct 10, 2006
	 * @return 返回警务人员详细信息
	 */
	public IBaseDTO getPoliceInfo(String policeNum);
	/**
	 * 查询警务人员信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回警务人员列表信息
	 */
	public List fuzzIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getFuzzSize();
	
	/**
	 * 检查警号是否存在
	 * @param policeNum String
	 * @version Oct 11, 2006
	 * @return true 存在 false 不存在
	 */
	public boolean checkPoliceNum(String policeNum);
	
	/**
	 * 修改警号
	 * @param policeNum String
	 * @version Oct 11, 2006
	 * @return true 修改成功 false 修改失败
	 */
	public boolean updatePoliceNum(IBaseDTO dto);
	
	//添加信息
	public void addPoc(et.po.PoliceFuzzInfo pf);
	
	/**
	 * 查询警务人员信息
	 * @param dto 类型 IBaseDTO 警务人员信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回警务人员列表信息
	 */
	public List countIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getCountSize();
	
	/**
	 * 得到远程文件的链接地址
	 * @param
	 * @version Nov 23, 2006
	 * @return
	 */
	public String getRemoateFile(IBaseDTO dto) throws FileNotFoundException,IOException, RowsExceededException, WriteException;
	
}
