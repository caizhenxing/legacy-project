/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.util.HashMap;
import java.util.Map;

import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>大面板座席话务实况信息</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class AgentTellInfo extends AgentInfoBean{
	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * <p>大面板座席话务实况信息</p>
	 * @param String ymd 时间yyyy-MM-dd
	 * @param String agent 人员id
	 * @param Map paramMap 其他条件 
	 * @return String ajax为座席长面板呈现数据 显示座席话务实况信息
	 */
	@Override
	public String getAgentInfo(String ymd, String agent,Map paramMap) {
		// TODO Auto-generated method stub
		//dao = (BaseDAO)SpringRunningContainer.getInstance().getBean("BaseDao");
		//do something
		String hql = "select b.addtime, e.city,e.sectionCounty, e.communityVillage,   d.custName, c.respondent, b.dictQuestionType1, c.respondentType"
			+ " from CcMain a, OperQuestion b, CcTalk c, OperCustinfo d, OperAddress e"
			+ " where b.ringBegintime = a.ringBegintime and a.id = c.ccMain.id and b.operCustinfo.custId = d.custId and d.custAddr = e.id and Convert(varchar(10),b.addtime,120) like '"+TimeUtil.getTheTimeStr(new java.util.Date(),"yyyy-MM-dd")+"%' order by b.addtime desc";
		
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] oarr = dao.findEntity(mq);
		StringBuffer sb = new StringBuffer("<table width=\"93%\" height=\"44\" border=\"0\" cellpadding=\"0\" cellspacing=\"5\" id=\"tblId\">");
		sb.append(" <tr>");
		
		sb.append(" <td width=\"6%\" align=\"center\" class=\"blue2_12b\"> 序号</td>");
			
		sb.append(" <td width=\"14%\" align=\"center\" class=\"blue2_12b\">时间</td> ");
		sb.append(" <td width=\"22%\" align=\"center\" class=\"blue2_12b\">来电情况</td> ");
		sb.append(" <td width=\"8%\" align=\"center\" class=\"blue2_12b\">受理座席</td> ");
		sb.append(" <td width=\"42%\" align=\"center\" class=\"blue2_12b\">咨询问题</td> ");
		sb.append(" <td align=\"center\" class=\"blue2_12b\" width=\"8%\">解决方式</td>");
		sb.append("</tr>");
		

		for(int i=0; i<oarr.length; i++)
		{
			//查询结果以数据的形式返回
			Object[] qArr = (Object[])oarr[i];
			//时间
			sb.append((String)qArr[0]);
			//城市
			String city = (String)qArr[1];
			if(city==null)
				city = "";
			//sectionCounty 和平区
			String sectionCounty = (String)qArr[2];
			if(sectionCounty==null)
				sectionCounty = "";
			//communityVillage 五经街 
			String communityVillage = (String)qArr[3];
			if(communityVillage==null)
				communityVillage = "";
			String custName = (String)qArr[4];
			//来电情况
			sb.append("|"+city+sectionCounty+communityVillage+custName);
			//受理座席
			sb.append("|"+(String)qArr[5]);
			//资询问题
			sb.append("|"+(String)qArr[6]);
			//解决方式
			String respondentType = (String)qArr[7];
			if(respondentType!=null&&"AGENT".equals(respondentType.trim()))
			{
				sb.append("|座席@");
			}
			else
			{
				sb.append("|专家@");
			}
			//sb.append((String)qArr[1]+(String))
		}
		String rStr = sb.toString();
		if(rStr.length()>0)
		{
			if(rStr.lastIndexOf("@")==(rStr.length()-1))
			{
				return rStr.substring(0,rStr.length()-1);
			}
		}
		return  rStr;
	}
	/*
	 * <table width="93%" height="44" border="0" cellpadding="0" cellspacing="5" id="tblId">
							<tr>
								<td width="6%" align="center" class="blue2_12b">
									序号
								</td>
								<td width="14%" align="center" class="blue2_12b">
									时间
								</td>
								<td width="22%" align="center" class="blue2_12b">
									来电情况
								</td>
								<td width="8%" align="center" class="blue2_12b">
									受理座席
								</td>
								<td width="42%" align="center" class="blue2_12b">
									咨询问题
								</td>
								<td align="center" class="blue2_12b" width="8%">
									解决方式
								</td>
							</tr>
						</table>
	 */
	public static void main(String [] args)
	{
		DayQuestionVolume dq = 	(DayQuestionVolume)SpringContainer.getInstance().getBean("DayQuestionVolumeService");
		//dq.getAgentInfo("2008-06-12","13");
	}
}
