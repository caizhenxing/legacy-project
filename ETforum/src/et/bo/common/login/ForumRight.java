package et.bo.common.login;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import et.config.hibernate.HibernateSessionFactory;
import et.po.ForumTopic;
import et.po.ForumUserInfo;

/**
 * 论坛权限
 * @author zhaoyifei,zhangfeng
 * @version Dec 22, 2006
 * @see
 */
public class ForumRight {

	public String GUEST="guest";
	public String NORMAL="normal";
	public String AUTHOR="author";
	public String FORBIDDEN="forbidden";
	public String MANAGER="manager";
	//拥有所有论坛模块权限，与树状根相同
	public String MANAGER_ALL = "1";
	public Long getRight(String userkey)
	{
		Session session=HibernateSessionFactory.getSession();
		Transaction tx=session.beginTransaction();
		try
		{
			ForumUserInfo fui=(ForumUserInfo)session.get(ForumUserInfo.class,userkey);
			Long right=null;
			if(fui==null)
			{
				ForumUserInfo f=new ForumUserInfo();
				//f.setUserkey(userkey);
				session.save(f);
				right=null;
			}
			//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%"+fui.getUserkey());
			if(fui!=null)
			//right=fui.getPurview();
			tx.commit();
			return right;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			return null;
		}
	}
	/*
	 * 权限:
	 * guest:只能浏览帖子
	 * forbidden：收藏、好友
	 * normal：回复、发帖、收藏、好友
	 * author：编辑
	 * 
	 * manager:精华、置顶、推荐、转帖、删除、封人
	 */
	public List returnRight(String userkey,String postAuthor,String area)
	{
		List rights=new ArrayList();
		
		if(userkey==null||userkey.trim().equals(""))
		{
			rights.add(GUEST);
			return rights;
		}
		Session session=HibernateSessionFactory.getSession();
		Transaction tx=session.beginTransaction();
		ForumUserInfo fu=(ForumUserInfo)session.get(ForumUserInfo.class,userkey);
		if(fu==null)
		{
			rights.add(GUEST);
			return rights;
		}
		if (fu.getForumRole().equals(FORBIDDEN)) {
			rights.add(FORBIDDEN);
			return rights;
		}
		//不被禁止，则为普通用户
		if(!fu.getForumRole().equals(FORBIDDEN))
		rights.add(NORMAL);
		//如果是作者，加作者权限
		if(fu.getForumRole()!=null&&fu.getForumRole().equals(MANAGER))
		{
			ForumTopic ft = (ForumTopic)session.get(ForumTopic.class, area);
			String partentArea = ft.getParentId();
			String UserAreaId = fu.getAreaId();
			//如果为1，则表示所有区域都有管理员权限
			//如果是本区内则比较即可
			//如果是父区域则有某模块下的权限
			if(UserAreaId.equals(MANAGER_ALL)||UserAreaId.equals(area)||UserAreaId.equals(partentArea))
			rights.add(MANAGER);
		}else{
			if(userkey.equals(postAuthor))
				rights.add(AUTHOR);
		}
        tx.commit();
		return rights;
	}
	
	public static boolean ischild(String area,String topic)
	{
		int t=Integer.parseInt(topic);
		int a=Integer.parseInt(area);
		while(a!=t&&t!=0)
		{
			t=t/100;
		}
		if(t==0)
			return false;
		return true;
	}
	public static String getSameRoot(String topic1,String topic2)
	{
		int t1=Integer.parseInt(topic1);
		int t2=Integer.parseInt(topic2);
		while(t1!=t2&&t1>0&&t2>0)
		{
			int temp=t1>t2?t1/100:t2/100;
			if(t1>t2)
				t1=temp;
			else
				t2=temp;
		}
		if(t1==t2)
			return Integer.toString(t1);
		else
			return null;
	}
	public static void main(String[] arg0)
	{
		ForumRight fr=new ForumRight();
		//System.out.println(ForumRight.getSameRoot("201","201"));
		//fr.getRight("zhangfeng");
		//System.out.println(fr.returnRight("terry","101","",""));
		//System.out.println(fr.ischild("2","102"));
		/*List l=fr.returnRight("zhangfeng","102","",null);
		for(int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i).toString());
		}*/
	}
}
