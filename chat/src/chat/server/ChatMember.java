package chat.server;

import java.nio.channels.SocketChannel;

public class ChatMember {

	private SocketChannel channel;
	private String name;
	private String ip;
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public SocketChannel getChannel() {
		return channel;
	}
	public void setChannel(SocketChannel channel) {
		this.channel = channel;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
