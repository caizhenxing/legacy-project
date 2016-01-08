/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.screen;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>大屏幕相关某个表改变时将其操作位一供前台js读取</p>
 * 
 * @version 2008-03-29
 * @author nie
 */
public class DbOperSign {
	//这个map的key为表名 值为时间记录上一次对此表进行破坏性操作的时间已决定是否需要读取相应表内容
	public static Map<String, SignBean> tblSignMap = new HashMap<String, SignBean>();
	static{
		//对12316快讯做标识
		tblSignMap.put("ScreenQueryMessage", new SignBean());
	}
	/**
	 * 返回true key对应的bean读数据库 返回false key对应的bean不读数据库
	 * @param key
	 * @return
	 */
	public static boolean getSign(String key)
	{
		SignBean sb = tblSignMap.get(key);
		if(sb != null)
		{
			return sb.getSign();
		}
		return false;
	}
	public static void setSign(String key)
	{
		SignBean sb = tblSignMap.get(key);
		if(sb!=null)
		{
			sb.addSign();
		}
	}
	public static void main(String[] args)
	{

	}
	
}
class SignBean
{
	private int modifySign = -1;
	private int readSign = -1;

	/**
	 * 判断是否需要读取数据库
	 * @return
	 */
	public boolean getSign()
	{
		if(readSign<modifySign)
		{
			readSign = modifySign;
			return true;
		}
		else if(readSign == -1)
		{
			readSign = 1;
			modifySign = 1;
			return true;
		}
		return false;
	}
	/**
	 * 对数据库做破坏性操作时调用它
	 *
	 */
	public void addSign()
	{
		this.modifySign++;
	}
}
