/**
 * 	@(#)AbsLine.java   2007-1-22 ÏÂÎç01:50:51
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation.impl;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import et.bo.callcenter.operation.EventBean;
import et.bo.callcenter.operation.LineInfo;
import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.EventBean.EventType;
import et.bo.callcenter.operation.LineInfo.LineType;

 /**
 * @author zhaoyifei
 * @version 2007-1-22
 * @see
 */
public abstract class AbsLine implements LineService {
	
	boolean isCountTime=false;
	int limit;
	int countTime;
	public void startTime(int hms) {
		// TODO Auto-generated method stub
		isCountTime=true;
		this.limit=hms;
	}
	public void endTime()
	{
		if(isCountTime)
		{
			countTime=0;
			limit=0;
			isCountTime=false;
		}
	}
	protected Queue<EventBean> ebs=new LinkedBlockingQueue<EventBean>();
	protected LineInfo li;
	public AbsLine()
	{
		super();
		li=new LineInfo();
	}
	public AbsLine(int i,LineType lt,int logic)
	{
		super();
		li=new LineInfo();
		li.setLineNum(i);
		li.setLineType(lt);
		li.setLineLogicNum(logic);
	}
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#getLineNum()
	 */
	public int getLineNum() {
		// TODO Auto-generated method stub
		return li.getLineNum();
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#listener()
	 */
	public Queue listener() {
		// TODO Auto-generated method stub
		if(isCountTime)
		{
			countTime++;
			if(countTime==limit)
			{
				EventBean eb=new EventBean();
				eb.setEt(EventType.L_TIMEOUT);
				eb.setLineNum(li.getLineNum());
				ebs.add(eb);
				endTime();
			}
		}
		this.listen(li.getLineNum());
		return ebs;
	}
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#sendDtmf(java.lang.String)
	 */
	public void sendDtmf(String dtmf) {
		// TODO Auto-generated method stub
		this.newSendDtmfBuf(li.getLineNum(), dtmf);
	}
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#startIndexPlayFile(java.util.List)
	 */
	public boolean startIndexPlayFile(List<String> files) {
		// TODO Auto-generated method stub
		return this.startIndexPlayFile(li.getLineNum(), files);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#startPlayFile(java.lang.String)
	 */
	public boolean startPlayFile(String file) {
		// TODO Auto-generated method stub
		return this.startPlayFile(li.getLineNum(), file);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#startRecordFile(java.lang.String)
	 */
	public boolean startRecordFile(String file) {
		// TODO Auto-generated method stub
		return this.startRecordFile(li.getLineNum(), file);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#stopIndexPlayFile()
	 */
	public void stopIndexPlayFile() {
		// TODO Auto-generated method stub
		this.stopIndexPlayFile(li.getLineNum());
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#stopPlayFile()
	 */
	public void stopPlayFile() {
		// TODO Auto-generated method stub
		this.stopPlayFile(li.getLineNum());
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#stopRecordFile()
	 */
	public void stopRecordFile() {
		// TODO Auto-generated method stub
		this.stopRecordFile(li.getLineNum());
	}
	protected abstract void newSendDtmfBuf(int i,String dtmf);
	
	protected abstract boolean startIndexPlayFile(int i,List<String> files);
	
	protected abstract boolean startPlayFile(int i,String file);
	
	protected abstract boolean startRecordFile(int i,String file);
	
	protected abstract void stopIndexPlayFile(int i);
	
	protected abstract void stopPlayFile(int i);
	
	protected abstract void stopRecordFile(int i);
	
	/**
	 * ¼àÌý×´Ì¬º¯Êý
	 */
	protected abstract  void listen(int i);
	/*protected abstract boolean ringDetect(int i);
	
	protected abstract boolean checkPlayEnd(int i);
	
	protected abstract boolean checkIndexPlayFile(int i);
	
	protected abstract boolean checkRecordEnd(int i);
	
	protected abstract boolean haveDtmf(int i);
	
	protected abstract boolean checkSendEnd(int i);
	
	protected abstract int readCheckResult(int i);
	
	protected abstract boolean checkPolarity(int i);
	
	protected abstract boolean checkSilence(int i,int num);
	
	protected abstract boolean offHookDetect(int i);
	
	protected abstract int hangUpDetect(int i);
	*/

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.UserLineService#feedPower()
	 */
	public void feedPower() {
		// TODO Auto-generated method stub
		if(li.getLineType().equals(LineType.IN_LINE))
		this.feedPower(li.getLineNum());
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.UserLineService#feedRealRing()
	 */
	public void feedRealRing() {
		// TODO Auto-generated method stub
		if(li.getLineType().equals(LineType.IN_LINE))
		this.feedRealRing(li.getLineNum());
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.UserLineService#feedRealRing(int, int)
	 */
	public void feedRealRing(int ring, int interval) {
		// TODO Auto-generated method stub
		if(li.getLineType().equals(LineType.IN_LINE))
		this.feedRing(li.getLineNum(), ring, interval);
		
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.UserLineService#startPlaySignal(int)
	 */
	public void startPlaySignal(int sigType) {
		// TODO Auto-generated method stub
		if(li.getLineType().equals(LineType.IN_LINE))
		this.startPlaySignal(li.getLineNum(),sigType);
	}
	
	protected abstract void feedPower(int i);
	
	protected abstract void feedRealRing(int i);
	
	protected abstract void feedRing(int i,int ring,int interval);
	
	protected abstract void startPlaySignal(int i,int type);
	
	
	
	

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.TrunkLineService#hangUp()
	 */
	public void hangUp() {
		// TODO Auto-generated method stub
		if(li.getLineType().equals(LineType.OUT_LINE))
		this.hangUp(li.getLineNum());
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.TrunkLineService#offHook()
	 */
	public void offHook() {
		// TODO Auto-generated method stub
		if(li.getLineType().equals(LineType.OUT_LINE))
		this.offHook(li.getLineNum());
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LineService#dail(java.lang.String, java.lang.String)
	 */
	public void dial(String dail, String pre) {
		// TODO Auto-generated method stub
		this.dial(li.getLineNum(), dail, pre);
	}
	protected abstract void hangUp(int i);
	protected abstract void offHook(int i);
	protected abstract void dial(int i,String dail, String pre);
	/**
	 * @return the li
	 */
	public LineInfo getLi() {
		return li;
	}
	/**
	 * @param li the li to set
	 */
	public void setLi(LineInfo li) {
		this.li = li;
	}
	
}
