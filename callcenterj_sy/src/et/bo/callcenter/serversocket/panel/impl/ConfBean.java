/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang feng
 * 
 */
public class ConfBean {
	// ���鷢����
	private String confBeginMan = "";

	// �������
	private String confGroup = "";

	// ���������
	private List confMan = new ArrayList();

	public String getConfBeginMan() {
		return confBeginMan;
	}

	public void setConfBeginMan(String confBeginMan) {
		this.confBeginMan = confBeginMan;
	}

	public String getConfGroup() {
		return confGroup;
	}

	public void setConfGroup(String confGroup) {
		this.confGroup = confGroup;
	}

	public List getConfMan() {
		return confMan;
	}

	public void setConfMan(List confMan) {
		this.confMan = confMan;
	}

}
