/**
 * 	@(#)PointService.java   2006-11-22 下午03:58:33
 *	 。 
 *	 
 */
package et.bo.forum.point.service;

/**
 * @describe 论坛积分操作接口
 * @author 叶浦亮
 * @version 2006-11-22
 * @see
 */
public interface PointService {
	/**
	 * @describe 发帖加分
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void addSendPoint(String userId);
	/**
	 * @describe 回帖加分
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void addAnswerPoint(String userId);
	/**
	 * @describe 管理员加分
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void addManagerPoint(String userId, int point);
	/**
	 * @describe 加入精华加分
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void joinPrimePoint(String userId);
	/**
	 * @describe 置顶加分 
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void putTopPoint(String userId);
	/**
	 * @describe 删贴扣分
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void cutDeletePostPoint(String userId);
	/**
	 * @describe 管理员扣分
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void cutManagerPoint(String userId, int point);
	/**
	 * @describe 根据用户积分得到等级
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public String getUserLevel(int point);
	/**
	 * @describe 取得用户积分
	 * @param
	 * @version 2006-12-19
	 * @return  String
	 */
	public String getUserPiont(String userId);
}
