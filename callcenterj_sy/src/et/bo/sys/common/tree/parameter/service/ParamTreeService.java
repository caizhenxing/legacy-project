/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.common.tree.parameter.service;

import java.util.List;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
/**
 * 构建类型tree
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface ParamTreeService {
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param rootId 根节点id
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree();
	/**
	 * 树节点的增加
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	void addParamTree(IBaseDTO dto);
	/**
	 * 树节点的修改
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	void upateParamTree(IBaseDTO dto);
	/**
	 * 树节点的删除
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	void removeParamTree(String id);
	/**
	 * 根据NickName以label Value的形式显示该树的子节点列表
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	List<LabelValueBean> getLabelValueBeanListByNickName(String nickName,TreeService tree);
	/**
	 * 根据NickName以该节点的列表
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	String getIdByNidkName(String nickName,TreeService tree);
	//void reloadParamTree();
	void setRootId(String rootId);
	
}
