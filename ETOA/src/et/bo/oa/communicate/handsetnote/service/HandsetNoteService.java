/**
 * 	@(#)HandsetNoteService.java   Sep 26, 2006 1:49:18 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.handsetnote.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Sep 26, 2006
 * @see
 */
public interface HandsetNoteService {
	
	/**
	 * 保存手机信息到数据库
	 * 
	 * @param dto
	 *            类型 IBaseDTO 手机信息
	 * @param sendtype 
	 * 			  类型 IBaseDTO 发送类型
	 * @return 类型 boolean 如果保存成功 返回ture，反之返回false
	 * 
	 */
	public boolean saveHandsetNote(IBaseDTO dto,String sendtype);
	
	/**
	 * 修改手机信息到数据库
	 * 
	 * @param dto
	 *            类型 IBaseDTO 手机信息
	 * @return 类型 boolean 如果修改成功 返回ture，反之返回false
	 * 
	 */
	public boolean updateHandsetNote(IBaseDTO dto);
	
	/**
	 * 删除手机信息，打删除标记
	 * 
	 * @param dto
	 *            类型 IBaseDTO 手机信息
	 * @return 类型 boolean 如果删除成功 返回ture，反之返回false
	 * 
	 */
	public boolean delHandsetNote(String[] selectIt);
	
	/**
	 * 删除手机信息永久删除
	 * 
	 * @param dto
	 *            类型 IBaseDTO 手机信息
	 * @return 类型 boolean 如果删除成功 返回ture，反之返回false
	 * 
	 */
	public boolean delHandsetNoteForever(String[] selectIt);
	
	/**
	 * 手机信息查询列表
	 * @param dto 类型 IBaseDTO 邮件信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @param mailboxType 类型 String 邮箱类型
	 * @return 类型 List 返回邮件列表信息
	 */
	public List handsetIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getHandsetIndexSize();
	
	/**
	 * 得到手机短信信息
	 * @param
	 * @version Sep 26, 2006
	 * @return
	 */
	public IBaseDTO getHandsetNoteInfo(String id);
}
