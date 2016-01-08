package et.bo.callcenter;

import et.bo.callcenter.business.nouse.CallcenterInfo;

public interface DbSchedule {
	/*
	 * 传入CallcenterInfo，根据系统设置的schedule进行保存。
	 * 1、直接存
	 * 2、定期存比如10秒
	 * 3、定数量进行存。比如20个CallcenterInfo存一次。
	 * 4\其它操作
	 */
	void scheduleSave();
//	String getSystemInfo();
	/*
	 * 一些查询方法,略。
	 */
	
}
