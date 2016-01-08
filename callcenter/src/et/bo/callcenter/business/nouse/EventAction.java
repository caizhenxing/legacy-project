package et.bo.callcenter.business.nouse;

public interface EventAction {
	/*
	 * 传入事件和参数。
	 * 返回的在EventActionImpl的属性中设定。
	 */
	EventAction action(String rule,String[] args);
	
}
