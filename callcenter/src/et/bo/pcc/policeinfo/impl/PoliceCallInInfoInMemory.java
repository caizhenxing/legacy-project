/**
 * 	@(#)PoliceCallInInfo.java   Oct 17, 2006 5:41:34 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policeinfo.impl;

/**
 * @author zhang
 * @version Oct 17, 2006
 * @see
 */
public class PoliceCallInInfoInMemory {
	//警员id(一直存于内存中)
	private String pcid;
	
	private String id;
	
	private String taginfo;
	
	private String quinfo;
	
	private String content;
	
	private String remark;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}

	public String getQuinfo() {
		return quinfo;
	}

	public void setQuinfo(String quinfo) {
		this.quinfo = quinfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaginfo() {
		return taginfo;
	}

	public void setTaginfo(String taginfo) {
		this.taginfo = taginfo;
	}

}
