/**
 * className OperExperterService 
 * 
 * �������� 2008-5-12
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * ����ר����Ϣ
 * @version 	2008-05-06
 * @author ����Ȩ
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
	 * �õ����е�ר���б��ַ��� ר������ �͵绰  С��#23834132,С��#13998823514,@С��#8002;
	 * @return String ר������:�绰,ר������:�绰; 
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
	 * �õ����е�ר���б��ַ��� ר��ID ��NAME
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
