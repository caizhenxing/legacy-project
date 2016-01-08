package et.bo.callcenter.message;

import et.bo.callcenter.business.nouse.EventAction;

public interface EventI {
	/*
	 * 传入事件和参数。
	 * 返回的在RuleImpl的属性中设定。
	 */
	String action(String rule,String[] args);
	String getResult();
}
