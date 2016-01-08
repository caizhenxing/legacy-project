package et.bo.police.callcenter.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ocelot.framework.base.dao.BaseDAO;
import ocelot.framework.base.query.MyQuery;
import ocelot.framework.base.query.impl.MyQueryImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import et.bo.police.callcenter.server.RuleInfo;
import et.po.CcRule;

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
	private BaseDAO dao = null;
	
	
	public RuleInfoImpl() {
		init();
	}
	
	public CcRule getCcRule(String key){
		return (CcRule)ruleMap.get(key);
	}
	public void reload(){
		this.init();
	}
	public List getRuleList() {
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
		mq.setDetachedCriteria(dc);
		Object[] oArray =dao.findEntity(mq);
		if(null==oArray){
			log.error("db init error!");
			return;
		}
		CcRule[] crArray=(CcRule[])oArray;
		ruleList.add(crArray);
		for(int i=0;i<crArray.length;i++){
			ruleMap.put(crArray[i].getRuleAct()+crArray[i].getTagMark(), crArray[i]);
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
