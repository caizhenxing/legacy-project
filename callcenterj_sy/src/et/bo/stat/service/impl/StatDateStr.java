/**
 * 	@(#) StatDateStr.java 2009-02-11 下午01:09:59
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.service.impl;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author wangwenquan
 * 做统计查询时 查当天的有问题这个类处理这个问题 如果开始时间=结束时间则 beginTime 00:00:00 endTime 23:59:59 这种格式
 */
public class StatDateStr {
	/**
	 * 当开始时间==结束时间时 加时分秒
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
	 * 当开始时间==结束时间时 且开始时间和结束时间不为空时 加时分秒
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
	 * 根据日期长度返回解析字符串
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
