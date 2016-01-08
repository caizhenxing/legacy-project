package et.bo.callcenter.message;

public interface EventStrValid {
	String getEvtResultAfterOper();
	/*
	 * s为命令
	 * 返回命令结果，null是命令有问题。
	 */
	RuleArg getRuleArg(String s);
}
