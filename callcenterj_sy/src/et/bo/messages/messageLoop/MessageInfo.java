/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.messages.messageLoop;

import java.util.Date;

/**
 * <p>��Ϣ��ѯ�� ǰ̨js���������õ���Ϣ</p>
 * 
 * @version 2008-12-03
 * @author wangwenquan
 */
public class MessageInfo {
	private String message_id; //��ˮ��
	private String send_id; //������id
	private String send_name; //����������
	private String receive_id; //������id
	private String message_content; //��Ϣ���� 
	private Date send_time; //����ʱ��
	private String dict_read_flag; //��ȡ��־
	/**
	 * 1 �Ѷ�
	 * 0 δ��
	 * @return
	 */
	public String getDict_read_flag() {
		return dict_read_flag;
	}
	public void setDict_read_flag(String dict_read_flag) {
		this.dict_read_flag = dict_read_flag;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(String receive_id) {
		this.receive_id = receive_id;
	}
	public String getSend_id() {
		return send_id;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public Date getSend_time() {
		return send_time;
	}
	public String getSendTimeStr()
	{
		if(this.send_time==null)
			this.send_time = new Date();
		return this.send_time.getDay()+""+this.send_time.getHours()+""+this.send_time.getMinutes()+""+this.getSend_time().getSeconds();
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}


	
	
}
