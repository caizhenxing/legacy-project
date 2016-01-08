package et.bo.forum.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import et.bo.forum.common.UserList;

public class SessionCountListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("this is session begin");
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		String userkey=(String) arg0.getSession().getAttribute("userkey");
		if(userkey==null)
			userkey=arg0.getSession().getId();
		UserList.remove(userkey);
	}

}
