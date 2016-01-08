package et.bo.police.callcenter.message;

import et.bo.police.callcenter.server.EventAction;

public interface RuleI {
	/*
	 * 传入事件和参数。
	 * 返回的在RuleImpl的属性中设定。
	 */
	RuleI action(String rule,String[] args);
	//	String getResult();
}
