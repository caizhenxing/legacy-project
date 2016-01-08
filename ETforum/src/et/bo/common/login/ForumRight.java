package et.bo.common.login;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import et.config.hibernate.HibernateSessionFactory;
import et.po.ForumTopic;
import et.po.ForumUserInfo;

/**
 * ��̳Ȩ��
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
	//ӵ��������̳ģ��Ȩ�ޣ�����״����ͬ
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
	 * Ȩ��:
	 * guest:ֻ���������
	 * forbidden���ղء�����
	 * normal���ظ����������ղء�����
	 * author���༭
	 * 
	 * manager:�������ö����Ƽ���ת����ɾ��������
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
		//������ֹ����Ϊ��ͨ�û�
		if(!fu.getForumRole().equals(FORBIDDEN))
		rights.add(NORMAL);
		//��������ߣ�������Ȩ��
		if(fu.getForumRole()!=null&&fu.getForumRole().equals(MANAGER))
		{
			ForumTopic ft = (ForumTopic)session.get(ForumTopic.class, area);
			String partentArea = ft.getParentId();
			String UserAreaId = fu.getAreaId();
			//���Ϊ1�����ʾ���������й���ԱȨ��
			//����Ǳ�������Ƚϼ���
			//����Ǹ���������ĳģ���µ�Ȩ��
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
