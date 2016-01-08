/**
 * 	@(#)ConferenceDongjinImpl.java   2007-1-19 ÉÏÎç11:03:29
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation.dongjin;

import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.impl.AbsConference;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinConf;

 /**
 * @author zhaoyifei
 * @version 2007-1-19
 * @see
 */
public class ConferenceDongjinImpl extends AbsConference {

	private IDongjinConf idc=null;
	public IDongjinConf getIdc() {
		return idc;
	}

	public void setIdc(IDongjinConf idc) {
		this.idc = idc;
	}

	

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsConference#addLine(int, et.bo.callcenter.operation.LineService)
	 */
	@Override
	protected void addLine(int i, int ls) {
		// TODO Auto-generated method stub
		idc.addChnl(i,ls,-6,0);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsConference#addListen(int, et.bo.callcenter.operation.LineService)
	 */
	@Override
	protected void addListen(int i, int ls) {
		// TODO Auto-generated method stub
		idc.addListenChnl(i,ls);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsConference#addRecord(int, et.bo.callcenter.operation.LineService)
	 */
	@Override
	protected void addRecordl(int i, LineService ls,String file) {
		// TODO Auto-generated method stub
		ls.startPlayFile(file);
		idc.dConfAddRecListenChnl(i,ls.getLineNum());
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsConference#subLine(int, et.bo.callcenter.operation.LineService)
	 */
	@Override
	protected void subLine(int i, int ls) {
		// TODO Auto-generated method stub
		idc.subChnl(i,ls);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsConference#subListen(int, et.bo.callcenter.operation.LineService)
	 */
	@Override
	protected void subListen(int i, int ls) {
		// TODO Auto-generated method stub
		idc.subListenChnk(i,ls);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsConference#subRecord(int, et.bo.callcenter.operation.LineService)
	 */
	@Override
	protected void subRecordl(int i, LineService ls) {
		// TODO Auto-generated method stub
		ls.stopPlayFile();
		idc.dConfSubRecListenChnl(i,ls.getLineNum());
	}


}
