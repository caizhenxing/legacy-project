package et.bo.police.callcenter.server;

public interface DbSchedule {
	/*
	 * 传入CallcenterInfo，根据系统设置的schedule进行保存。
	 * 1、直接存
	 * 2、定期存比如10秒
	 * 3、定数量进行存。比如20个CallcenterInfo存一次。
	 */
	void scheduleSave(CallcenterInfo cci);
//	String getSystemInfo();
	/*
	 * 一些查询方法,略。
	 */
	
}
