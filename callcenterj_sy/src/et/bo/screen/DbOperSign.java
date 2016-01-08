/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.screen;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>����Ļ���ĳ����ı�ʱ�������λһ��ǰ̨js��ȡ</p>
 * 
 * @version 2008-03-29
 * @author nie
 */
public class DbOperSign {
	//���map��keyΪ���� ֵΪʱ���¼��һ�ζԴ˱�����ƻ��Բ�����ʱ���Ѿ����Ƿ���Ҫ��ȡ��Ӧ������
	public static Map<String, SignBean> tblSignMap = new HashMap<String, SignBean>();
	static{
		//��12316��Ѷ����ʶ
		tblSignMap.put("ScreenQueryMessage", new SignBean());
	}
	/**
	 * ����true key��Ӧ��bean�����ݿ� ����false key��Ӧ��bean�������ݿ�
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
	 * �ж��Ƿ���Ҫ��ȡ���ݿ�
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
	 * �����ݿ����ƻ��Բ���ʱ������
	 *
	 */
	public void addSign()
	{
		this.modifySign++;
	}
}
