/**
 * className OperExperterService 
 * 
 * 创建日期 2008-5-12
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.callcenter.bo.experter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import et.bo.callcenter.bo.experter.OperExperterService;
import et.po.OperCustinfo;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
/**
 * 操作专家信息
 * @version 	2008-05-06
 * @author 王文权
 */
public class OperExperterImpl implements OperExperterService {
	BaseDAO dao = null;
	//public KeyService ks = null;
	public List<OperCustinfo> getOperCustinfoList() {
		// TODO Auto-generated method stub
		List<OperCustinfo> experterList = new ArrayList<OperCustinfo>();
		OperExpertorSearch oper = new OperExpertorSearch();
		Object[] o = dao.findEntity(oper.experterSearch());
		for(int i=0; i<o.length; i++)
		{
			OperCustinfo experter = (OperCustinfo)o[i];
			experterList.add(experter);
		}
		return experterList;
	}
	/**
	 * 得到所有的专家列表字符串 专家名字 和电话  小王#23834132,小李#13998823514,@小张#8002;
	 * @return String 专家名字:电话,专家名字:电话; 
	 */
	public String getOperCustinfoStrs() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		List<OperCustinfo> experters = this.getOperCustinfoList();
		for(int i=0; i<experters.size(); i++)
		{
			OperCustinfo info = experters.get(i);
			if(i>0)
			{
				sb.append(",");
			}
			sb.append(info.getCustName()+"#"+info.getCustTelMob());
		}
		return sb.toString();
	}
	
	/**
	 * 得到所有的专家列表字符串 专家ID 和NAME
	 * @return String ID#NAME,ID#NAME; 
	 */
	public String getOperExperterIDNameStrs()
	{
		StringBuffer sb = new StringBuffer();
		List<OperCustinfo> experters = this.getOperCustinfoList();
		for(int i=0; i<experters.size(); i++)
		{
			OperCustinfo info = experters.get(i);
			if(i>0)
			{
				sb.append(",");
			}
			sb.append(info.getCustId()+"#"+info.getCustName());
		}
		return sb.toString();
	}
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
}
