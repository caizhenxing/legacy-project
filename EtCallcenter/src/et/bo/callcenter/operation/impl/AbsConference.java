/**
 * 	@(#)AbsConference.java   2007-1-19 ÉÏÎç10:23:08
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation.impl;

import java.util.HashMap;
import java.util.Map;

import et.bo.callcenter.operation.ConferenceInfo;
import et.bo.callcenter.operation.ConferenceService;
import et.bo.callcenter.operation.LineService;

 /**
 * @author zhaoyifei
 * @version 2007-1-19
 * @see
 */
public abstract class AbsConference implements ConferenceService {

	protected Map<String,ConferenceInfo> cis=new HashMap<String,ConferenceInfo>();
	
	public void addLine(int i,LineService ls) {
		// TODO Auto-generated method stub
		if(cis.containsKey(Integer.toString(i)))
		{
			ConferenceInfo ci=cis.get(Integer.toString(i));
			if(ci.addLine(ls))
				this.addLine(i, ls);
		}
		else
		{
			ConferenceInfo ci=new ConferenceInfo(i,ls);
			cis.put(Integer.toString(i),ci);
			this.addLine(i, ls);
		}
		
	}
	public void addListen(int i,LineService ls) {
		// TODO Auto-generated method stub
		if(cis.containsKey(Integer.toString(i)))
		{
			ConferenceInfo ci=cis.get(Integer.toString(i));
			if(ci.addLine(ls))
				this.addListen(i,ls);
		}
		else
		{
			ConferenceInfo ci=new ConferenceInfo(i,ls);
			cis.put(Integer.toString(i),ci);
			this.addListen(i,ls);
		}
		
	}
	public void addRecord(int i,LineService ls,String file) {
		// TODO Auto-generated method stub
		if(cis.containsKey(Integer.toString(i)))
		{
			ConferenceInfo ci=cis.get(Integer.toString(i));
			ci.setRecordFile(file);
			if(ci.addLine(ls))
				this.addRecordl(i,ls,file);
		}
		else
		{
			ConferenceInfo ci=new ConferenceInfo(i,ls);
			cis.put(Integer.toString(i),ci);
			ci.setRecordFile(file);
			this.addRecordl(i,ls,file);
		}
		
	}
	public void subLine(int i,LineService ls) {
		// TODO Auto-generated method stub
		if(cis.containsKey(Integer.toString(i)))
		{
			ConferenceInfo ci=cis.get(Integer.toString(i));
			if(ci.subLine(ls))
				this.subLine(i,ls);
		}
		
	}
	public void subListen(int i,LineService ls) {
		// TODO Auto-generated method stub
		if(cis.containsKey(Integer.toString(i)))
		{
			ConferenceInfo ci=cis.get(Integer.toString(i));
			if(ci.subLine(ls))
				this.subListen(i, ls);
		}
		
	}
	public void subRecord(int i,LineService ls) {
		// TODO Auto-generated method stub
		if(cis.containsKey(Integer.toString(i)))
		{
			ConferenceInfo ci=cis.get(Integer.toString(i));
			if(ci.subLine(ls))
				this.subRecordl(i, ls);
		}
		
	}
	protected abstract void addLine(int i,int ls);
	protected abstract void addListen(int i,int ls);
	protected abstract void subLine(int i,int ls);
	protected abstract void subListen(int i,int ls);
	protected abstract void addRecordl(int i,LineService ls,String file);
	protected abstract void subRecordl(int i,LineService ls);
}
