/**
 * 	@(#)PointServiceImpl.java   2006-11-29 上午10:13:27
 *	 。 
 *	 
 */
package et.bo.forum.point.service.impl;

import et.bo.forum.point.service.PointService;
import et.po.ForumExprienceLevel;
import et.po.ForumUserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.dao.BaseDAO;

/**
 * @describe PointService接口实现
 * @author 叶浦亮
 * @version 2006-11-29
 * @see
 */
public class PointServiceImpl implements PointService {
	
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#addAnswerPoint()
	 */
	public void addAnswerPoint(String userId) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		String point = cts.getvaluebyNickName("forum_point_addAnswerPoint");
		int userPoint = fui.getPoint()+Integer.parseInt(point);
		fui.setPoint(userPoint);
		dao.saveEntity(fui);

	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#addManagerPoint(int)
	 */
	public void addManagerPoint(String userId, int point) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		int userPoint = fui.getPoint()+point;
		fui.setPoint(userPoint);
		dao.saveEntity(fui);

	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#addSendPoint()
	 */
	public void addSendPoint(String userId) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		String point = cts.getvaluebyNickName("forum_point_addSendPoint");
		int userPoint = fui.getPoint()+Integer.parseInt(point);
		fui.setPoint(userPoint);
		dao.saveEntity(fui);
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#cutDeletePostPoint()
	 */
	public void cutDeletePostPoint(String userId) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		String point = cts.getvaluebyNickName("forum_piont_cutDeletePostPoint");
		int userPoint = fui.getPoint()+Integer.parseInt(point);
		fui.setPoint(userPoint);
		dao.saveEntity(fui);
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#cutManagerPoint()
	 */
	public void cutManagerPoint(String userId, int point) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		int userPoint = fui.getPoint()+point;
		fui.setPoint(userPoint);
		dao.saveEntity(fui);
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#getUserLevel(int)
	 */
	public String getUserLevel(int point) {
		// TODO Auto-generated method stub
		PointHelp ph = new PointHelp();
		Object[] result=(Object[])dao.findEntity(ph.userLevelQuery(point));
		String userLevel ="";
		if(result.length!=0){
			ForumExprienceLevel fel = (ForumExprienceLevel)result[0];
			userLevel = fel.getUserLevel(); 
		}
		return userLevel;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#joinPrimePoint()
	 */
	public void joinPrimePoint(String userId) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		String point = cts.getvaluebyNickName("forum_point_joinPrimePoint");
		int userPoint = fui.getPoint()+Integer.parseInt(point);
		fui.setPoint(userPoint);
		dao.saveEntity(fui);
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.point.service.PointService#putTopPoint()
	 */
	public void putTopPoint(String userId) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		String point = cts.getvaluebyNickName("forum_piont_putTopPoint");
		int userPoint = fui.getPoint()+Integer.parseInt(point);
		fui.setPoint(userPoint);
		dao.saveEntity(fui);
	}
	
	public String getUserPiont(String userId){
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		if(fui!=null){
			return String.valueOf(fui.getPoint());
		}else{
			return "";
		}
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
