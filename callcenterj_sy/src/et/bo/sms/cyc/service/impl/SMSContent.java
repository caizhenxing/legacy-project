/**
 * 	@(#) SMSContent.java 2008-4-22 
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.sms.cyc.service.impl;
import java.util.List;
/**
 * @author ������
 * @version 1.0
 */
public class SMSContent {
	private String sendNum;
	private String sendManId;
	private String content;
	private String quantity;
	private String receiveNum;
	private String receiveManId;
	private String operCount;
	private String schedularTime;
	private String managementId;
	private String operationId;
	private String sendState;//����״̬
//	private List groupNum;//����һ��Ⱥ�����
	
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getManagementId() {
		return managementId;
	}
	public void setManagementId(String managementId) {
		this.managementId = managementId;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperCount() {
		return operCount;
	}
	public void setOperCount(String operCount) {
		this.operCount = operCount;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getReceiveManId() {
		return receiveManId;
	}
	public void setReceiveManId(String receiveManId) {
		this.receiveManId = receiveManId;
	}
	public String getReceiveNum() {
		return receiveNum;
	}
	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}
	public String getSchedularTime() {
		return schedularTime;
	}
	public void setSchedularTime(String schedularTime) {
		this.schedularTime = schedularTime;
	}
	public String getSendManId() {
		return sendManId;
	}
	public void setSendManId(String sendManId) {
		this.sendManId = sendManId;
	}
	public String getSendNum() {
		return sendNum;
	}
	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}
//	public List getGroupNum() {
//		return groupNum;
//	}
//	public void setGroupNum(List groupNum) {
//		this.groupNum = groupNum;
//	}
}
