package et.bo.screen.service.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import et.po.OperPriceinfo;
import et.po.ScreenOperSadinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ScreenServiceHelp {
	/**
	 * 每日价格查询
	 * @return
	 */
	public MyQuery screenPriceInfoQuery()
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperPriceinfo.class);	
		dc.addOrder(Order.desc("deployTime"));
		mq.setDetachedCriteria(dc); 
		mq.setFirst(0);
		mq.setFetch(200);
		return mq;
	}
	/**
	 * 
	 * @return
	 */
	public MyQuery screenSadInfoQuery()
	{
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ScreenOperSadinfo.class);	
		dc.addOrder(Order.desc("deployBegin"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	/**
	 * 金典案例查询
	 * @author wangwenquan
	 * @return 
	 */
	public MyQuery screenCaseInfoQuery()
	{
		//String hql = "select top 50 from OperCaseinfo a where Convert(varchar(10),a.addtime,120) like '"+TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd")+"%' order by a.addtime desc";
		String hql = "from OperCaseinfo a  order by a.caseTime desc";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		mq.setFirst(0);
		mq.setFetch(200);
		return mq;
	}
	//12316快讯查询
	public MyQuery screenQuickMessageQuery()
	{
		String hql = "from ScreenQuickMessage a order by a.createDate desc";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		mq.setFirst(0);
		mq.setFetch(10);
		return mq;
	}
	//话务实况查询
	public MyQuery huaWuQuery()
	{
		//String hql = "from OperQuestion a where a.operCustinfo.custId is not null and Convert(varchar(10),a.addtime,120) like '"+TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd")+"%' order by a.addtime desc";
		String hql = "from OperQuestion a where a.operCustinfo.custId is not null  order by a.addtime desc";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		mq.setFirst(0);
		mq.setFetch(200);
		return mq;
	}
	//所有专家查询 查询之前先将专家类型标志位设为默认值
	public MyQuery allExpertsQuery()
	{
		//select * from oper_custinfo where dict_cust_type = 'SYS_TREE_0000002103'
		String hql = "from OperCustinfo a where a.dictCustType = 'SYS_TREE_0000002103' order by a.custName ";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		return mq;
	}
	//所有专家查询 查询之前先将专家类型标志位设为默认值 1 2 3 
	public MyQuery allExpertsQuery(String type)
	{
		//select * from oper_custinfo where dict_cust_type = 'SYS_TREE_0000002103'
		String hql = "from OperCustinfo a where a.dictCustType = 'SYS_TREE_0000002103' and a.screenExpertType = '"+type+"' order by a.custName ";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		return mq;
	}
//	所有专家查询 查询之前先将专家类型标志位设为默认值 1 2 3 
	public MyQuery allExpertsMutexQuery(String type)
	{
		//select * from oper_custinfo where dict_cust_type = 'SYS_TREE_0000002103'
		String hql = "from OperCustinfo a where a.dictCustType = 'SYS_TREE_0000002103' and a.screenExpertType != '"+type+"' order by a.custName ";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		return mq;
	}
	//焦点追中
	public String jiaoDianQuery()
	{
		/*
		String hql = "from " 
		+"( "
		+"		select a.caseContent, a.caseReply, a.addtime createTime "
		+"		from OperCaseinfo a where a.dictCaseState = 'putOut' "
		+"		union all "
		+"		select a.summary, a.fucosContent, a.createTime createTime "
		+"		from OperFocusinfo a where a.dictFocusState = 'putOut' "
		+"		) a "
		+"		where convert(varchar(10),a.createTime, 120) < '2008-06-30' "
		+"		order by a.createTime desc ";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		return mq;
		*/
		//Date beforeDate = new Date((new Date().getTime())-1000*60*60*24*7);
		String sql = "select top 10 * from "
		+" ( "
		+" select a.case_content as content, a.case_reply as otherCol, a.addtime createTime "
		+" from oper_caseinfo a "
		+" where a.state = '发布' "
		+" union all "
		+" select a.fucos_content, a.summary, a.create_time createTime "
		+" from oper_focusinfo a "
		+" where a.state = '发布' "
		+" ) a order by a.createTime desc";
		//+" where convert(varchar(10),a.createTime, 120) between '"+TimeUtil.getTheTimeStr(beforeDate,"yyyy-MM-dd")+"' and '"+TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd")+"'" 
		//+" order by a.createTime desc ";
		
		//System.out.println(sql);
		return sql;
	}
	
	//质询量汇总
	public String zixunSum()
	{
		String sql = "select * " 
		+" from "
		+" ( "
		+" select count(*)+130000 as 'zixunsum' "
		+" from oper_question "
		+" ) a, "
		+" ( "
		+" select count(*) as 'dayzixun' "
		+" from oper_question "
		+" where convert(varchar(10),addtime,120) = convert(varchar(10),getdate(),120) "
		+" ) b, "
		+" ( "
		+" select count(*) as 'shengchan' "
		+" from oper_question "
		+" where dict_question_type1 in ('种植咨询','养殖咨询') and convert(varchar(10),addtime,120) = convert(varchar(10),getdate(),120) "
		+" ) c, "
		+" ( "
		+" select count(*) as 'shichang' "
		+" from oper_question "
		+" where dict_question_type1 in ('供求发布','价格行情','价格报送') and convert(varchar(10),addtime,120) = convert(varchar(10),getdate(),120) "
		+" ) d, "
		+" ( "
		+" select count(*) as 'zhengce' " 
		+" from oper_question  "
		+" where dict_question_type1 = '政策咨询' and convert(varchar(10),addtime,120) = convert(varchar(10),getdate(),120) "
		+" ) e, "
		+" ( "
		+" select count(*) as 'yiliao'  "
		+" from oper_question  "
		+" where dict_question_type1 = '医疗服务' and convert(varchar(10),addtime,120) = convert(varchar(10),getdate(),120) "
		+" ) f, "
		+" ( "
		+" select count(*) as 'other'  "
		+" from oper_question  "
		+" where dict_question_type1 not in ('种植咨询','养殖咨询','供求发布','价格行情','价格报送','政策咨询','医疗服务') and convert(varchar(10),addtime,120) = convert(varchar(10),getdate(),120) "
		+" ) g " ;
		return sql;
	}
	
}