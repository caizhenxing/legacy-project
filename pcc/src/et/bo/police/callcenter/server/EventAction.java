package et.bo.police.callcenter.server;

public interface EventAction {
	/*
	 * 传入事件和参数。
	 * 返回的在EventActionImpl的属性中设定。
	 */
	EventAction action(String rule,String[] args);
	
}
