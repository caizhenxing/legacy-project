/**
 * 	@(#)FileManagerService.java   2006-9-4 下午03:07:57
 *	 。 
 *	 
 */
package et.bo.oa.file.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import excellence.common.tree.TreeControlI;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author yifei zhao
 * @version 2006-9-4
 * @see TreeControlI
 */
public interface FileManagerService {

	/**
     * load all Folders 
     *@return  <code>TreeControlI</code> is tree of departments
     */
	public TreeControlI loadFolders();
	
	/**
	 * 返回文件信息，文件权限
	 * @param
	 * @version 2006-9-4
	 * @return
	 */
	public IBaseDTO loadFileInfo(String id,String user);
	
	/**
	 * 增加文件（增加版本）
	 * @param dto 文件信息
	 * @version 2006-9-4
	 * @return 
	 */
	public void addFile(IBaseDTO dto);
	
	/**
	 * 审核文件通过与否，通过的话，在浏览里显示，未同过的话，删除。
	 * @param id 文件id
	 * @param user 审核人
	 * @param pass 是否通过
	 * @version 2006-9-4
	 * @return
	 */
	public void checkFile(String id,String user,boolean pass);
	
	/**
	 * 删除文件
	 * @param id 文件id
	 * @version 2006-9-4
	 * @return
	 */
	public void delFile(String id);
	
	/**
	 * 恢复文件上一版本
	 * @param id 文件id
	 * @version 2006-9-4
	 * @return
	 */
	public void resumeFile(String id);
	
	/**
	 * 授权
	 * @param dto
	 * @version 2006-9-4
	 * @return
	 */
	public void accredit(IBaseDTO dto);
	
	/**
	 * 下载文件
	 * @param response HttpServletResponse 
	 * @param id 文件id
	 * @version 2006-9-4
	 * @return
	 */
	public void download(HttpServletResponse response,String id);
}
