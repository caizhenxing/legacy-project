/**
 * className IvrTreeServiceImpl 
 * 
 * 创建日期 2008-4-3
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.service;

import java.util.List;

import et.bo.sys.basetree.service.impl.IVRBean;
/**
 * Ivr调用虚哟啊的classTree
 * 对ClassTreeServiceImpl做部分从写使其能够应用于ivr管理
 * @version 	april 01 2008 
 * @author 王文权
 */
public interface ClassBaseTreeService {
	IVRBean getIVRBeanById(String treeId);
	List<IVRBean> getListById(String treeId);
	
	void loadParamTree();
	/**
	 * IvrdeployAction里使用
	 * @param id
	 * @return
	 */
	public String getCCLogIvrDeployName(String id);
}
