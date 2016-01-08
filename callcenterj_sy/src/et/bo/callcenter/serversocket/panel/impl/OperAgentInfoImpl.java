/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-5-8
 */
package et.bo.callcenter.serversocket.panel.impl;

import et.bo.callcenter.serversocket.iconst.ConstRuleI;
import et.bo.callcenter.serversocket.panel.OperAgentInfoService;
import et.po.CcAgentInfo;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * ���ڴ��е���ϯ״̬��Ϣ����ɾ��
 * 
 * @author wangwenquan
 * 
 */
public class OperAgentInfoImpl implements OperAgentInfoService {
	private BaseDAO dao=null;
	private KeyService ks = null;
	/**
	 * ��AgentInfoMap ���Ӧ��AgentInfoBeanɾ��
	 * @param String agentNum
	 */
//	public void deleteAgentInfoBean(String agentNum) {
//		// TODO Auto-generated method stub
//		if(RefreshPanelService.agentInfoMap.get(agentNum)!=null)
//		{
//			RefreshPanelService.agentInfoMap.remove(agentNum);
//		}
//	}
	/**
	 * ��AgentInfoBean����AgentInfoMap��
	 * @param infoBean
	 */
	public void insertOrUpdateAgentInfoBean(CcAgentInfo infoBean) {
		// TODO Auto-generated method stub
		String key = ks.getNext("CC_AGENT_INFO");
		infoBean.setId(key);
		if(infoBean.getAgentId()!=null&&!"".equals(infoBean.getAgentId().trim()))
		{
			String hql = "from CcAgentInfo a where a.agentId='"+infoBean.getAgentId()+"' and convert(varchar(10),a.insertDate,120) like '"+TimeUtil.getTheTimeStr(infoBean.getInsertDate(), "yyyy-MM-dd")+"%'";
			MyQuery mq = new MyQueryImpl();
			mq.setHql(hql);
			Object[] os = dao.findEntity(mq);
			//�������ж� �������¼�����ڲ�ִ�в������
			if(os.length==0)
			{
				dao.saveEntity(infoBean);
			}
		}
		else
		{
			//System.out.println("agentInfo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+infoBean.getAgentId());
		}
	}
	
	/**
	 * ��ѯCcAgentInfo
	 * @param String agent_id
	 * @param String yyyyMMdd yyyy-mm-dd
	 */
	public CcAgentInfo queryCcAgentInfo(String agent_id, String yyyyMMdd)
	{
		//Date beginTime = TimeUtil.getTimeByStr(yyyyMMdd);
		//Date endTime = new Date(beginTime.getTime()+24*60*60*1000);
		String hql = "from CcAgentInfo c where c.agentId = '"+agent_id+"' and convert(varchar(10),c.insertDate,120) like '"+yyyyMMdd+"%'";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] oarr = dao.findEntity(mq);
		for(int i=0; i<oarr.length; i++)
		{
			CcAgentInfo ca = (CcAgentInfo)oarr[i];
			return ca;
		}
		return null;
	}
	/**
	 * ��ѯCcAgentInfo
	 * @param String agent_id
	 * @param String yyyyMMdd yyyy-mm-dd
	 * @return String ��CcAgentInfoƴ���ַ������� agentId@value,insertDate@value,....;
	 */
	public String queryCcAgentInfoToStr(String agent_id, String yyyyMMdd)
	{
		CcAgentInfo info = this.queryCcAgentInfo(agent_id, yyyyMMdd);
		
		if(info != null)
		{
			StringBuffer sb = new StringBuffer(ConstRuleI.CMD_AGENTSTATEINFO+":");
			sb.append("ID@"+info.getId()+",");
			sb.append("AGENTID@"+info.getAgentId()+",");
			String cldjl = info.getCldjl();
			if(cldjl==null)
			{
				cldjl = "";
			}
			sb.append("CLDJL@"+cldjl+","); //�����¼
			String clmzxl = info.getClmzxl();
			if(clmzxl==null)
			{
				clmzxl = "";
			}
			sb.append("CLMZXL@"+clmzxl+","); //��Ŀ��ѯ��: 
			String Czxzcr = info.getCzxzcr();
			if(Czxzcr==null)
			{
				Czxzcr = "";
			}
			sb.append("CZXZCR@"+Czxzcr+","); //����������:
			String Czxzj = info.getCzxzj();
			if(Czxzj==null)
			{
				Czxzj = "";
			}
			sb.append("CZXZJ@"+Czxzj+",");  //����ר��:
			String Tzxzt = info.getTzxzt();
			if(Tzxzt==null)
			{
				Tzxzt = "";
			}
			sb.append("TZXZT@"+Tzxzt+",");  //��ϯ״̬
			String InsertDate = TimeUtil.getTheTimeStr(info.getInsertDate(), "yyyy-MM-dd");
			if(InsertDate==null)
			{
				InsertDate = "";
			}
			sb.append("INSERTDATE@"+InsertDate+","); //����ʱ��
			Double Tbcsc = info.getTbcsc();
			if(Tbcsc==null)
			{
				Tbcsc = new Double(0);
			}
			sb.append("TBCSC@"+Tbcsc+",");  //����ʱ��: 
			String Tdlsj = TimeUtil.getTheTimeStr(info.getTdlsj(),"yyyy-MM-dd");
			if(Tdlsj==null)
			{
				Tdlsj = "";
			}
			sb.append("TDLSJ@"+Tdlsj+",");  //��¼ʱ��
			Integer Tdqpds = info.getTdqpds();
			if(Tdqpds==null)
			{
				Tdqpds = 0;
			}
			sb.append("TDQPDS@"+Tdqpds+",");  //��ǰ�Ŷ��� :
			String Tdqsj = TimeUtil.getTheTimeStr(info.getTdqsj(),"yyyy-MM-dd");
			if(Tdqsj==null)
			{
				Tdqsj = "";
			}
			sb.append("TDQSJ@"+Tdqsj+",");  //��ǰʱ��:  
			Double Tfwzs =info.getTfwzs();
			if(Tfwzs==null)
			{
				Tfwzs = 0.0;
			}
			sb.append("TFWZS@"+Tfwzs+","); //������ʱ:
			
			String UpdateDate = TimeUtil.getTheTimeStr(info.getUpdateDate(),"yyyy-MM-dd");
			if(UpdateDate==null)
			{
				UpdateDate = "";
			}
			sb.append("UPDATEDATE@"+UpdateDate);  //��������
			
			return sb.toString();
		}
		return null;
	}
	/**
	 * �޸�AgnetInfoMap ���AgentInfoBean
	 * @param infoBean
	 */
	//public void updateAgentInfoBean(CcAgentInfo infoBean) {
		// TODO Auto-generated method stub
		//CcAgentInfo dbInfo = dao.loadEntity(CcAgentInfo.class, infoBean.getId());
	//}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
	
}
