/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.common.tree.parameter.build.service;

import java.util.List;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeService;
/**
 * 构建类型tree
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface BuildParamTreeService {
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param rootId 根节点id
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree();
	
	TreeService getParamTree();
	
	List<LabelValueBean> getLabelValueBeanListByNickName(String nickName);
	String getIdByNidkName(String nickName);
	void reloadParamTree();
	void setRootId(String rootId);
	
}
