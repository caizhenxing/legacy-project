/**
 * 	@(#)ModuleManagerService.java   2006-12-11 下午02:42:27
 *	 。 
 *	 
 */
package et.bo.forum.moduleManager.service;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe 论坛模块管理操作接口
 * @author 叶浦亮
 * @version 1.0, 2006-12-11
 * @see
 */
public interface ModuleManagerService {
    /**
	 * @describe 添加模块: (可以设置是否上传,发帖是否加分等等...)
	 * @param
	 * @return
	 */
	public void addModule(IBaseDTO dto);
	/**
	 * @describe 修改模块
	 * @param
	 * @return
	 */
	public void updateModule(IBaseDTO dto);
	/**
	 * @describe 删除模块
	 * @param
	 * @return
	 */
	public void deleteModule(String id);
	/**
	 * 取得模块信息
	 * @param
	 * @version 2006-12-11
	 * @return
	 */
	public IBaseDTO getModuleInfo(String id);
	/**
	 * @describe 模块列表
	 * @param
	 * @return HashMap
	 */
	public HashMap moduleList();
	/**
	 * 得到条数
	 * @param
	 * @version 2006-12-11
	 * @return int
	 */
	public int getSize();
	/**
	 * 取得模块名称标题列表
	 * @param
	 * @version 2006-12-18
	 * @return LabelValueBean
	 */
	public List<LabelValueBean> getAllModuleValueBean();
	/**
	 * 取得模块名称标题列表
	 * @param
	 * @version 2006-12-18
	 * @return LabelValueBean
	 */
	public List<LabelValueBean> getModuleValueBean();
	/**
	 * 增加区域发帖回复次数
	 * @param
	 * @version 2006-12-18
	 * @return
	 */
	public void addAnswerTimes(String id);
	/**
	 * 增加区域点击次数
	 * @param
	 * @version 2006-12-18
	 * @return
	 */
	public void addPostNum(String id);
	/**
	 * 判断模块名是否存在
	 * @param
	 * @version 2006-12-18
	 * @return true存在 false不存在
	 */
	public boolean isModuleExist(String moduleName);
	/**
	 * 更新时判断模块名是否存在
	 * @param
	 * @version 2006-12-18
	 * @return true存在 false不存在
	 */
	public boolean updateIsModuleExist(String moduleName,String id);
	/**
	 * 通过模块名返回模块Id
	 * @param
	 * @version 2006-12-18
	 * @return Stirng
	 */
	public String getIdByModuleName(String moduleName);
}

