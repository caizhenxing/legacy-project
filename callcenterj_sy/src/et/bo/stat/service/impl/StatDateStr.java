/**
 * 	@(#) StatDateStr.java 2009-02-11 ����01:09:59
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.stat.service.impl;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author wangwenquan
 * ��ͳ�Ʋ�ѯʱ �鵱�������������ദ��������� �����ʼʱ��=����ʱ���� beginTime 00:00:00 endTime 23:59:59 ���ָ�ʽ
 */
public class StatDateStr {
	/**
	 * ����ʼʱ��==����ʱ��ʱ ��ʱ����
	 * @param dto
	 */
	public static void setBeginEndTime(IBaseDTO dto)
	{
		String bTime = dto.get("beginTime")==null?"":dto.get("beginTime").toString();
		String eTime = dto.get("endTime")==null?"":dto.get("endTime").toString();
		if(bTime.equals(eTime))
		{
			dto.set("beginTime", bTime+" 00:00:00");
			dto.set("endTime", eTime+" 23:59:59");
		}
	}
	/**
	 * ����ʼʱ��==����ʱ��ʱ �ҿ�ʼʱ��ͽ���ʱ�䲻Ϊ��ʱ ��ʱ����
	 * @param dto
	 */
	public static void setBeginEndTimeAll(IBaseDTO dto)
	{
		String bTime = dto.get("beginTime")==null?"":dto.get("beginTime").toString();
		String eTime = dto.get("endTime")==null?"":dto.get("endTime").toString();
		if("".equals(bTime)||"".equals(eTime))
			return;
		dto.set("beginTime", bTime+" 00:00:00");
		dto.set("endTime", eTime+" 23:59:59");
	}
	/**
	 * �������ڳ��ȷ��ؽ����ַ���
	 * @param timeStr
	 * @return
	 */
	public static String getParseDateStr(String timeStr)
	{
		if(timeStr==null||"".equals(timeStr.trim()))
		{
			return "yyyy-MM-dd";
		}
		else if(timeStr.length()>10)
		{
			return "yyyy-MM-dd HH:mm:ss";
		}
		else
			return "yyyy-MM-dd";
	}
}
