package et.bo.callcenter.message.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.callcenter.message.RuleInfo;
import et.po.CcRule;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class RuleInfoImpl implements RuleInfo{
	/*
	 * 命令（rule）的map（表cc_rule的map），key是rule_act+"_"+tag_mark,value 是CcRule。
	 */
	private static Map ruleMap=new HashMap();
	/*
	 * 命令的顺序列表
	 */
	private static List ruleList=new ArrayList();
//	private static Log log = LogFactory.getLog(RuleInfoImpl.class);
	private static Log log = LogFactory.getLog(RuleInfoImpl.class);
	/*
	 * 注入
	 */
	private BaseDAO dao = null;
	
	
	public RuleInfoImpl() {
	}
	
	public CcRule getCcRule(String key){
		//log.debug("*********888");
		if(ruleMap.size()==0)
			init();
		//log.debug("*********488");
		//System.out.println(ruleMap.size());
		//System.out.println(ruleMap.get(key));
		return (CcRule)ruleMap.get(key);
	}
	public void reload(){
		this.init();
	}
	public List getRuleList() {
		if(ruleMap.size()==0)init();
		return ruleList;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/*
	 * 懒加载,初始化。
	 */
	private void init(){
		if(ruleMap.size()!=0) return;
		if(ruleList.size()!=0) return;
		
		//fetch all
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(et.po.CcRule.class);
//		dc.add(arg0)
//		dc.add(Criterion.)
		dc.add(Restrictions.eq("ver","V2.0"));
		mq.setDetachedCriteria(dc);
		
		Object[] oArray = (Object[])dao.findEntity(mq);
		log.debug("*********333s"+oArray.length);
		if(null==oArray){
			log.error("db init error!");
			return;
		}
//		CcRule[] crArray=(CcRule[])oArray;
//		log.debug("*********909");
//		ruleList.add(crArray);
		
		for(int i=0;i<oArray.length;i++){
			CcRule cr =(CcRule)oArray[i];
			ruleMap.put(cr.getRuleAct()+"_"+cr.getTagMark(), cr);
			ruleList.add(cr);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RuleInfoImpl rh = new RuleInfoImpl();
//		log.info(rh.ruleMap.size());
	}

}
