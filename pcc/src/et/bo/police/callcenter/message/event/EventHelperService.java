package et.bo.police.callcenter.message.event;

public interface EventHelperService {
	/*
	 * 刷新座席面版的状态
	 * 
	 */
	public void refreshOperatorPanel();
	/*
	 * 刷新工控机的座席状态
	 * 工控机：icc
	 */
	public void refreshIccOperatorState();
	
	/*
	 * 存储cclog、policeCallin、Police_callin_info
	 */
	public void saveInfo(String id);
}
