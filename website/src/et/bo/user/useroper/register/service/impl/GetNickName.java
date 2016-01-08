package et.bo.user.useroper.register.service.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import et.config.hibernate.HibernateSessionFactory;
import et.po.ForumUserInfo;

public class GetNickName {

	public static String getNickName(String id) {
		String nickname = "";
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		ForumUserInfo fu = (ForumUserInfo) session.get(ForumUserInfo.class, id);
		nickname = fu.getName();
		tx.commit();
		return nickname;
	}

}
