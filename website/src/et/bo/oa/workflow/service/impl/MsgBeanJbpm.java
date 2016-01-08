package et.bo.oa.workflow.service.impl;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class MsgBeanJbpm {

	public static String FLOW_CREATE="c";
	public static String FLOW_NEXT="n";
	public static String FLOW_TASK="t";
	public static String FLOW_CREATE_TASK="e";
	private String id="";
	private String taskId="";
	private String messageInfo="";
	private String actor="";
	private String messageType="";
	private String thisActor="";
	private String message="";
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		
		if(this.message==null)
			this.message="";
		else
			this.message = message;
	}
	public String getThisActor() {
		return thisActor;
	}
	public void setThisActor(String thisActor) {
		if(thisActor==null)
			this.thisActor="";
		else
		this.thisActor = thisActor;
	}
	public String getMessageInfo() {
		return messageInfo;
	}
	public void setMessageInfo(String messageInfo) {
		if(messageInfo!=null)
		this.messageInfo = messageInfo;
		else
			this.messageInfo ="";
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		
		this.messageType = messageType;
	}
	
	
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		
		if(actor==null)
			this.actor="";
		else
			this.actor = actor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMsg(String xml)
	{
		Properties p=new Properties();
		try {
			InputStream  is=new StringBufferInputStream(xml);
			p.loadFromXML(is);
			is.close();
			this.id=p.getProperty("id");
			this.taskId=p.getProperty("taskId");
			this.messageInfo=p.getProperty("messageInfo");
			this.messageType=p.getProperty("messageType");
			this.actor=p.getProperty("actor");
			this.thisActor=p.getProperty("thisActor");
			this.message=p.getProperty("message");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getMsg()
	{
		String xml=null;
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		Properties p=new Properties();
		p.setProperty("id",this.id);
		p.setProperty("taskId",taskId);
		p.setProperty("messageInfo",this.messageInfo);
		p.setProperty("messageType",this.messageType);
		p.setProperty("actor",this.actor);
		p.setProperty("thisActor",this.thisActor);
		p.setProperty("message",this.message);
		try {
			p.storeToXML(bos,"");
			xml=bos.toString();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return xml;
	}
	public static void main(String[] arg0)
	{
		MsgBeanJbpm mbj=new MsgBeanJbpm();
		
		mbj.setId("1");
		
		mbj.setMessageInfo("dd");
		mbj.setMessageType("23");
		
		mbj.setMsg(mbj.getMsg());
		
		
		
		
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
		if(this.taskId==null)
			this.taskId="";
	}
}

