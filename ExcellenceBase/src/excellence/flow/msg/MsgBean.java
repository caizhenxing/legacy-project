package excellence.flow.msg;

public class MsgBean {

	private String dest=null;
	private String source=null;
	private String msg=null;
	public String getMsg()
	{
		return msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg=msg;
	}
	
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest=dest;
	}
	
	public String getSource()
	{
		return this.source;
	}
	public void setSource(String source)
	{
		this.source=source;
	}
}
