/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-4-1
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.ivrClassTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.bo.sys.basetree.service.impl.IVRBean;
import et.bo.sys.basetree.service.impl.IVRBeanVoice;
import et.bo.sys.ivr.service.IvrClassTreeLoadService;
import et.bo.sys.ivr.service.impl.IvrViewTreeNode;
import et.po.BaseTree;
import et.po.CcIvrTreeInfo;
import et.po.CcIvrTreevoiceDetail;
import et.po.ViewTreeDetail;
import excellence.common.key.KeyService;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
* 为IvrClassTreeService提供数据
*
* @version 	jan 01 2008 
* @author 王文权
*/
public class IvrClassTreeLoadServiceImpl implements IvrClassTreeLoadService{
	private BaseDAO dao=null;
	private KeyService ks = null;
	
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * 将db xml等的数据载入TreeNodeService里 里边有BaseTreeNodeService 树基本属性,IvrViewTreeNode 树视图用的,CcIvrTreeInfo CcIvrTreeInfo
	 * @return List<BaseTreeNodeService>
	 */
	public List<BaseTreeNodeService> loadTreeNodeService() {
		// TODO Auto-generated method stub
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(BaseTree.class);
		dc.add(Expression.ne("deleteMark", "1"));
//		dc.add(Expression.eq("type", "ivr"));
		dc.addOrder(Order.asc("layerOrder"));
		mq.setDetachedCriteria(dc);
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> btnList = new ArrayList<BaseTreeNodeService>();
		
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			BaseTree bt = (BaseTree)o[i];
			//先加载基表在加载子表 将子表注入基表中
			IvrBaseTreeNodeServiceImpl btn = new IvrBaseTreeNodeServiceImpl();
			btn.setId(bt.getId());
			btn.setLabel(bt.getLabel());
			btn.setNickName(bt.getNickName());
			
			btn.setParentId(bt.getParentId());
			btn.setLayerOrder(bt.getLayerOrder());
			btn.setType(bt.getType());
			//Ivr视图显示的 IvrViewTreeNode 
			IvrViewTreeNode vtn = new IvrViewTreeNode();
			Iterator it =  bt.getViewTreeDetails().iterator();
			//System.out.println(bt.getViewTreeDetails().isEmpty()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			if(it.hasNext())
			{
				ViewTreeDetail vtd = (ViewTreeDetail)it.next();
				vtn.setAction(vtd.getAction());
				
				vtn.setTarget(vtd.getTarget());
				vtn.setIcon(vtd.getIcon());
				vtn.setTarget(vtd.getTarget());
				vtn.setTagShow(vtd.getTagShow());
			}
			btn.setTreeNodeExtendedService(vtn);
			btn.setIsRoot(bt.getIsRoot());
			//将CcIvrTreeInfo放入树节点中
			Iterator infoIt = bt.getCcIvrTreeInfos().iterator();
			if(infoIt.hasNext())
			{
				//btn.setTreeInfo((CcIvrTreeInfo)infoIt.next());
				vtn.setTreeInfo((CcIvrTreeInfo)infoIt.next());
			}
			btnList.add(btn);
		}
		return btnList;
	}

	public Map<String,IVRBean> getIVRBeanMap()
	{
		Map<String,IVRBean> beanMap = new HashMap<String,IVRBean>();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(CcIvrTreeInfo.class);
		dc.add(Expression.ne("deleteMark", "1"));
		//dc.addOrder(Order.asc("layerOrder"));
		mq.setDetachedCriteria(dc);
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			CcIvrTreeInfo info = (CcIvrTreeInfo)o[i];
			//System.out.println(info.getId()+"++++++"+info.getCcIvrTreevoiceDetails().size());
			IVRBean bean = getPIVRBeanById(info);
			beanMap.put(info.getId(), bean);
		}
		return beanMap;
	}
	/**
	 * 将CcIvrTreeInfo 转成 IVRBean
	 * @param info
	 * @return
	 */
	private IVRBean getPIVRBeanById(CcIvrTreeInfo info)
	{
		IVRBean bean = new IVRBean();
		bean.setContent(info.getContent());
		bean.setFunctype(info.getFunctype());
		bean.setId(info.getId());
		Iterator it = info.getCcIvrTreevoiceDetails().iterator();
		List<IVRBeanVoice> voiceList = new ArrayList<IVRBeanVoice>();
		while(it.hasNext())
		{
			CcIvrTreevoiceDetail detail = (CcIvrTreevoiceDetail)it.next();
			IVRBeanVoice voice = new IVRBeanVoice();
			voice.setId(detail.getId());
			voice.setIsUse(detail.getIsUse());
			voice.setLanguageType(detail.getLanguageType());
			voice.setLayOrder(detail.getLayerOrder());
			voice.setVoicePath(detail.getVoicePath());
			//System.out.println(detail.getVoicePath()+">>>>>>>>>>>>>>>>>>>>");
			voice.setMainId(info.getId());
			voice.setMainParentId(info.getBaseTree().getParentId());
			if("1".equals(voice.getIsUse()))
			{
				voiceList.add(voice);
			}
		}
		bean.setIvrList(voiceList);
		bean.setLabel(info.getBaseTree().getLabel());
		bean.setLanguageType(info.getLanguageType());
		bean.setNickname(info.getNickname());
		bean.setParent_id(info.getBaseTree().getParentId());
		bean.setSize(info.getLengthSize());
		bean.setTelkey(info.getTelKey());
		bean.setTelnum(info.getTelNum());
		bean.setValue(info.getCheckValue());
		bean.setVoiceType(info.getVoiceType());
		bean.setCustomizeCancel(info.getCustomizeCancel());
		bean.setOrderProgramme(info.getOrderProgramme());
		bean.setExpertId(info.getExpertId());
		return bean;
	}
	/**
	 * 接口中的方法这里不需要 设null
	 * 将db xml等的数据载入TreeNodeService里
	 * @param String action 节点行为
	 * @param String target 节点目标
	 * @return List<BaseTreeNodeService>
	 */
	public List<BaseTreeNodeService> loadLeafRights(String action, String target)
	{
		return null;
	}
	
	/**
	 * 接口中的方法这里不需要 设null
	 * 将用户信息封装到BaseTreeNodeService里在生成部门树时其作为叶子节点附加到部门树上
	 * @param String action 节点行为
	 * @param String target 节点目标
	 * @return List<BaseTreeNodeService> 
	 */
	public List<BaseTreeNodeService> getAllUsersForDeptTree(String action, String target)
	{
		return null;
	}
}
