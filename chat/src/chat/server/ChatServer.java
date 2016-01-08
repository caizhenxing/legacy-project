package chat.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

	private static Map<String,ChatRoom> rooms=new ConcurrentHashMap<String,ChatRoom>();
	
	public void addRoom(String room,ChatMember cm)
	{
		
	}
}
