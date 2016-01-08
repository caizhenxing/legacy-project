package et.bo.forum.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserList {

	private static HashMap user=new HashMap();
	private static HashMap count=new HashMap();
	
	public static int getUserSize(int areaId)
	{
		return 0;
	}
	public static void setUser(String userkey,int areaId)
	{
		if(user.containsKey(userkey))
			{
				Integer oldId=(Integer)user.get(userkey);
				Integer newId=new Integer(areaId);
				user.put(userkey,newId);
				subCount(oldId);
				addCount(newId);
			}
		else
		{
			Integer newId=new Integer(areaId);
			user.put(userkey,newId);
			addCount(newId);
		}
		
	}
	public static void remove(String userkey)
	{
		if(user.containsKey(userkey))
		{
			Integer oldId=(Integer)user.get(userkey);
			subCount(oldId);
			user.remove(userkey);
		}
	}
	private static void addCount(Integer i)
	{
		if(!count.containsKey(i))
			count.put(i,new Integer(0));
		Integer num=(Integer)count.get(i);
		Integer temp=new Integer(num.intValue()+1);
		count.put(i,temp);
		Integer parent=new Integer(i.intValue()/100);
		if(parent.intValue()>0)
			addCount(parent);
		if(parent.intValue()==0)
		{
			if(!count.containsKey(new Integer(0)))
				count.put(new Integer(0),new Integer(0));
			Integer a=(Integer)count.get(new Integer(0));
			Integer b=new Integer(a.intValue()+1);
			count.put(new Integer(0),b);
		}
	}
	private static void subCount(Integer i)
	{
		Integer num=(Integer)count.get(i);
		Integer temp=new Integer(num.intValue()-1);
		count.put(i,temp);
		Integer parent=new Integer(i.intValue()/100);
		if(count.containsKey(parent)&&parent.intValue()!=0)
			subCount(parent);
		if(parent.intValue()==0)
		{
			if(!count.containsKey(new Integer(0)))
				count.put(new Integer(0),new Integer(0));
			Integer a=(Integer)count.get(new Integer(0));
			Integer b=new Integer(a.intValue()-1);
			count.put(new Integer(0),b);
		}
	}
	public static int getCount(int id)
	{
		Integer i=new Integer(id);
		if(count.containsKey(i))
		{
			Integer size=(Integer)count.get(i);
			return size.intValue();
		}
		return 0;
	}
	public static List<String> getUsers(int topicId)
	{
		List<String> userList=new ArrayList<String>();
		Set<String> users=user.keySet();
		for(String u:users)
		{
			Integer i=(Integer)user.get(u);
			if(i.byteValue()==topicId)
				userList.add(u);
		}
		return userList;
	}
	public static boolean isOnline(String userId)
	{
		return user.containsKey(userId);
	}
	public static void main(String[] arg0)
	{
		long begin=System.currentTimeMillis();
		/*int total=100000;
		for(int i=10;i<total;i++)
		{
			UserList.setUser(Integer.toString(i),i);
		}*/
		//UserList.setUser("a",101);
		
		UserList.setUser("a",5);
		UserList.setUser("b",5);
		UserList.setUser("c",5);
		System.out.println(UserList.getCount(5));
		UserList.remove("a");
		System.out.println(UserList.getCount(5));
		System.err.println("---first time is--"+(System.currentTimeMillis()-begin));
		
		List<String> u=UserList.getUsers(102);
		for(String a:u)
		{
			System.out.println(a);
		}
		System.err.println("---time is--"+(System.currentTimeMillis()-begin));
		
	}
}
