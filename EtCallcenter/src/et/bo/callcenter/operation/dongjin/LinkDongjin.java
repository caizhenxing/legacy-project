/**
 * 	@(#)LinkDongjin.java   2007-1-22 ÏÂÎç01:39:08
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation.dongjin;

import et.bo.callcenter.operation.LineService;
import et.bo.callcenter.operation.impl.AbsLink;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinLink;

 /**
 * @author zhaoyifei
 * @version 2007-1-22
 * @see
 */
public class LinkDongjin extends AbsLink {

	private IDongjinLink idl=null;
	public IDongjinLink getIdl() {
		return idl;
	}

	public void setIdl(IDongjinLink idl) {
		this.idl = idl;
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLink#clearLink(int, int)
	 */
	@Override
	protected void clearLink(int n1, int n2) {
		// TODO Auto-generated method stub
		idl.clearLink(n1,n2);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLink#clearLinkThree(int, int, int)
	 */
	@Override
	protected void clearLinkThree(int n1, int n2, int n3) {
		// TODO Auto-generated method stub
		idl.clearThree(n1,n2,n3);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLink#clearListen(int, int)
	 */
	@Override
	protected void clearListen(int n1, int n2) {
		// TODO Auto-generated method stub
		idl.clearOneFromAnother(n1,n2);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLink#setLink(int, int)
	 */
	@Override
	protected void setLink(int n1, int n2) {
		// TODO Auto-generated method stub
		idl.setLink(n1,n2);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLink#setLinkThree(int, int, int)
	 */
	@Override
	protected void setLinkThree(int n1, int n2, int n3) {
		// TODO Auto-generated method stub
		idl.linkThree(n1,n2,n3);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.impl.AbsLink#setLinsten(int, int)
	 */
	@Override
	protected void setLinsten(int n1, int n2) {
		// TODO Auto-generated method stub
		idl.linkOneToAnother(n1, n2);
	}

	

}
