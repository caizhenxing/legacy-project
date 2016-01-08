/**
 * 	@(#)LinkInfo.java   2007-1-16 ÉÏÎç11:02:51
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.Date;

 /**
 * @author zhaoyifei
 * @version 2007-1-16
 * @see
 */
public class LinkInfo {

	public enum LinkType{
		L_TOW,
		L_THREE,
		L_LISTEN
	};
	private LinkType lt;
	private LineService ls1;
	private LineService ls2;
	private LineService ls3;
	private Date begin;
	private Date end;
	private boolean l1;
	private boolean l2;
	private boolean l3;
	
	public boolean isL1() {
		return l1;
	}
	public void setL1(boolean l1) {
		this.l1 = l1;
	}
	public boolean isL2() {
		return l2;
	}
	public void setL2(boolean l2) {
		this.l2 = l2;
	}
	public boolean isL3() {
		return l3;
	}
	public void setL3(boolean l3) {
		this.l3 = l3;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public LineService getLs1() {
		return ls1;
	}
	public void setLs1(LineService ls1) {
		this.ls1 = ls1;
	}
	public LineService getLs2() {
		return ls2;
	}
	public void setLs2(LineService ls2) {
		this.ls2 = ls2;
	}
	public LineService getLs3() {
		return ls3;
	}
	public void setLs3(LineService ls3) {
		this.ls3 = ls3;
	}
	public LinkType getLt() {
		return lt;
	}
	public void setLt(LinkType lt) {
		this.lt = lt;
	}
}
