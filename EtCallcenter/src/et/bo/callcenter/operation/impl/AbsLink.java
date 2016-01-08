/**
 * 	@(#)AbsLink.java   2007-1-19 ÏÂÎç03:24:56
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation.impl;

import java.util.Date;
import java.util.HashMap;

import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.LinkInfo;
import et.bo.callcenter.operation.LinkService;
import et.bo.callcenter.operation.LineInfo.LineState;
import et.bo.callcenter.operation.LineInfo.LineType;
import et.bo.callcenter.operation.LinkInfo.LinkType;
import et.bo.callcenter.plant.dongjin_c161a.impl.DongjinParameter;

 /**
 * @author zhaoyifei
 * @version 2007-1-19
 * @see
 */
public abstract class AbsLink implements LinkService {

	protected HashMap<String,LinkInfo> linking=new HashMap<String,LinkInfo>() ;
	protected HashMap<String,LinkInfo> waitlink=new HashMap<String,LinkInfo>() ;
	
	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LinkService#clearLink()
	 */
	public LinkInfo clearLink(LineService ls) {
		// TODO Auto-generated method stub
		LinkInfo li=linking.get(Integer.toString(ls.getLineNum()));
		li.setEnd(new Date());
		LineService ls1=li.getLs1();
		LineService ls2=li.getLs2();
		LineService ls3=li.getLs3();
		if(li.getLt().equals(LinkType.L_TOW))
			this.clearLink(li.getLs1().getLineNum(),li.getLs2().getLineNum());
		if(li.getLt().equals(LinkType.L_LISTEN))
			this.clearListen(li.getLs1().getLineNum(),li.getLs2().getLineNum());
		if(li.getLt().equals(LinkType.L_THREE))
		{
			this.clearLinkThree(li.getLs1().getLineNum(),li.getLs2().getLineNum(),li.getLs3().getLineNum());
			linking.remove(Integer.toString(li.getLs3().getLineNum()));
			if(!ls3.getLi().getLineState().equals(LineState.L_FREE))
				ls3.startPlaySignal(DongjinParameter.SIG_BUSY1);
		}
		if(!ls1.getLi().getLineState().equals(LineState.L_FREE))
			ls1.startPlaySignal(DongjinParameter.SIG_BUSY1);
		if(!ls2.getLi().getLineState().equals(LineState.L_FREE))
			ls2.startPlaySignal(DongjinParameter.SIG_BUSY1);
		linking.remove(Integer.toString(li.getLs1().getLineNum()));
		linking.remove(Integer.toString(li.getLs2().getLineNum()));
	
		
		
		
		return li;
	}

	

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LinkService#setLink(et.bo.callcenter.operation.LineService, et.bo.callcenter.operation.LineService)
	 */
	public void setLink(LineService ls1, LineService ls2) {
		// TODO Auto-generated method stub
		LinkInfo li=new LinkInfo();
		ls1.stopPaly();
		ls2.stopPaly();
		
		li.setLs1(ls1);
		li.setLs2(ls2);
		li.setLt(LinkType.L_TOW);
		linking.put(Integer.toString(ls1.getLineNum()),li);
		linking.put(Integer.toString(ls2.getLineNum()),li);
		li.setBegin(new Date());
		ls1.getLi().setLineState(LineState.L_LINK);
		ls2.getLi().setLineState(LineState.L_LINK);
		
		this.setLink(ls1.getLineNum(),ls2.getLineNum());
		
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LinkService#setListen(et.bo.callcenter.operation.LineService, et.bo.callcenter.operation.LineService)
	 */
	public void setListen(LineService ls1, LineService ls2) {
		// TODO Auto-generated method stub
		LinkInfo li=new LinkInfo();
		ls1.stopPaly();
		ls2.stopPaly();
		
		li.setLs1(ls1);
		li.setLs2(ls2);
		li.setLt(LinkType.L_LISTEN);
		linking.put(Integer.toString(ls1.getLineNum()),li);
		linking.put(Integer.toString(ls2.getLineNum()),li);
		li.setBegin(new Date());
		ls1.getLi().setLineState(LineState.L_LINK);
		ls2.getLi().setLineState(LineState.L_LINK);
		this.setLinsten(ls1.getLineNum(),ls2.getLineNum());
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.LinkService#setlinkThree(et.bo.callcenter.operation.LineService, et.bo.callcenter.operation.LineService, et.bo.callcenter.operation.LineService)
	 */
	public void setlinkThree(LineService ls1, LineService ls2, LineService ls3) {
		// TODO Auto-generated method stub
		LinkInfo li=new LinkInfo();
		ls1.stopPaly();
		ls2.stopPaly();
		ls3.stopPaly();
		
		li.setLs1(ls1);
		li.setLs2(ls2);
		li.setLs3(ls3);
		li.setLt(LinkType.L_THREE);
		linking.put(Integer.toString(ls1.getLineNum()),li);
		linking.put(Integer.toString(ls2.getLineNum()),li);
		linking.put(Integer.toString(ls3.getLineNum()),li);
		li.setBegin(new Date());
		ls1.getLi().setLineState(LineState.L_LINK);
		ls2.getLi().setLineState(LineState.L_LINK);
		ls3.getLi().setLineState(LineState.L_LINK);
		
		this.setLinkThree(ls1.getLineNum(),ls2.getLineNum(),ls3.getLineNum());
	}
	protected abstract void setLink(int n1,int n2);
	protected abstract void clearLink(int n1,int n2);
	protected abstract void setLinsten(int n1,int n2);
	protected abstract void clearListen(int n1,int n2);
	protected abstract void setLinkThree(int n1,int n2,int n3);
	protected abstract void clearLinkThree(int n1,int n2,int n3);



	public boolean contain(LineService ls) {
		// TODO Auto-generated method stub
		return linking.containsKey(Integer.toString(ls.getLineNum()));
	}



	public void setRecord(LineService ls) {
		// TODO Auto-generated method stub
		
	}
}
