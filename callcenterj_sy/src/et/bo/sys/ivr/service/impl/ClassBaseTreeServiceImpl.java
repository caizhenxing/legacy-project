/**
 * className IvrTreeServiceImpl 
 * 
 * 创建日期 2008-4-3
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import et.bo.sys.basetree.service.impl.IVRBean;
import et.bo.sys.basetree.service.impl.IVRBeanVoice;
import et.bo.sys.ivr.service.ClassBaseTreeService;
import et.bo.sys.ivr.service.IvrClassTreeService;
import et.po.CcIvrDeploy;
import et.po.CcIvrTreeInfo;
import et.po.CcIvrTreevoiceDetail;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * Ivr调用虚哟啊的classTree
 * 对ClassTreeServiceImpl做部分从写使其能够应用于ivr管理
 * @version 	april 01 2008 
 * @author 王文权
 */
public class ClassBaseTreeServiceImpl implements ClassBaseTreeService{

	private BaseDAO dao;
	private IvrClassTreeService classTreeService;
	public IvrClassTreeService getClassTreeService() {
		return classTreeService;
	}

	/**
	 * 得到节点为treeId的所有孩子节点的IVRBean
	 */
	public List<IVRBean> getListById(String treeId)
	{
		
		return classTreeService.getIVRBeanListById(treeId);
	}
	
	private List<IVRBeanVoice> getIvrBeanVoiceList(String treeId)
	{
		List<IVRBeanVoice> voiceList = new ArrayList<IVRBeanVoice>();
		IVRBean bean = classTreeService.getIVRBeanById(treeId);
		voiceList = bean.getIvrList();
		return voiceList;
	}
	
	public String getCCLogIvrDeployName(String id)
	{
		String str = null;
		CcIvrDeploy cid = (CcIvrDeploy) dao.loadEntity(CcIvrDeploy.class,id);
			if(cid.getVoicefilepath().equals(""))
			{
				str = "";
			}
			else
			{
				str = cid.getVoicefilepath();
			}

		return str;
	}
	
	public void loadParamTree(){
		this.classTreeService.loadParamTree();
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public void setClassTreeService(IvrClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
		//this.classTreeService.loadParamTree();
	}
	public IVRBean getIVRBeanById(String treeId)
	{
		return classTreeService.getIVRBeanById(treeId); 
	}
	
}
