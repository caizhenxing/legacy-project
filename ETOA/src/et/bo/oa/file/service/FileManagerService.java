/**
 * 	@(#)FileManagerService.java   2006-9-4 ����03:07:57
 *	 �� 
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
	 * �����ļ���Ϣ���ļ�Ȩ��
	 * @param
	 * @version 2006-9-4
	 * @return
	 */
	public IBaseDTO loadFileInfo(String id,String user);
	
	/**
	 * �����ļ������Ӱ汾��
	 * @param dto �ļ���Ϣ
	 * @version 2006-9-4
	 * @return 
	 */
	public void addFile(IBaseDTO dto);
	
	/**
	 * ����ļ�ͨ�����ͨ���Ļ������������ʾ��δͬ���Ļ���ɾ����
	 * @param id �ļ�id
	 * @param user �����
	 * @param pass �Ƿ�ͨ��
	 * @version 2006-9-4
	 * @return
	 */
	public void checkFile(String id,String user,boolean pass);
	
	/**
	 * ɾ���ļ�
	 * @param id �ļ�id
	 * @version 2006-9-4
	 * @return
	 */
	public void delFile(String id);
	
	/**
	 * �ָ��ļ���һ�汾
	 * @param id �ļ�id
	 * @version 2006-9-4
	 * @return
	 */
	public void resumeFile(String id);
	
	/**
	 * ��Ȩ
	 * @param dto
	 * @version 2006-9-4
	 * @return
	 */
	public void accredit(IBaseDTO dto);
	
	/**
	 * �����ļ�
	 * @param response HttpServletResponse 
	 * @param id �ļ�id
	 * @version 2006-9-4
	 * @return
	 */
	public void download(HttpServletResponse response,String id);
}
