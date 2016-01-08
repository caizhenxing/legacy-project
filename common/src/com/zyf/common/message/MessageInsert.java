/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-11-20下午15:13:03
 * 文件名：MessageService.java
 * 制作者：zhangwenqi
 */
package com.zyf.common.message;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.zyf.common.crud.service.CommonService;
import com.zyf.common.message.domain.TblUfMessage;
import com.zyf.container.ServiceProvider;
import com.zyf.tools.Tools;

public class MessageInsert  {
	
	public static TblUfMessage createMessage(MessageBean messageBean) throws Exception {
		//将消息创建日期，发送计数，赋值
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        Date date = cal.getTime();
        messageBean.setCreateDate(date);
        //发送计数，赋值为0
        messageBean.setSendCount(0);
        //消息状态，赋值为0
        messageBean.setMessageStatus("0");
        
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(calEnd.YEAR, 1);
        Date endDate = calEnd.getTime();
        
        //映射，赋值
        TblUfMessage tblUfMessage = new TblUfMessage();
    	tblUfMessage.setSendUserid(messageBean.getSendUserId());
        tblUfMessage.setReceiverUserid(messageBean.getReceiveUserId());
        tblUfMessage.setTitle(messageBean.getTitle());
        tblUfMessage.setMessageContent(messageBean.getMessageContent());
        tblUfMessage.setMessageStatus(messageBean.getMessageStatus());
        tblUfMessage.setCreateDate(messageBean.getCreateDate());
        //如果预定发送开始时间不填写，则赋值为系统当前时间
        if ( messageBean.getScheduleDate() != null) {
        	tblUfMessage.setScheduleDate(messageBean.getScheduleDate());
        } else {
        	tblUfMessage.setScheduleDate(messageBean.getCreateDate());
        }
        //如果发送结束时间不填写，则赋值为系统当前时间加一年
        if ( messageBean.getEndDate() != null) {
        	tblUfMessage.setEndDate(messageBean.getEndDate());
        } else {
        	tblUfMessage.setEndDate(endDate);
        }
        tblUfMessage.setSendDate(messageBean.getSendDate());
        //如果发送次数不填写，则赋值为1
        if ( messageBean.getSendTime() != 0) {
        	tblUfMessage.setSendTime(messageBean.getSendTime());
        } else {
        	tblUfMessage.setSendTime(1);
        }
        tblUfMessage.setSendCount(messageBean.getSendCount());
        tblUfMessage.setIntervalTime(messageBean.getIntervalTime());
        
        //共通字段赋值
        tblUfMessage.setMessageId(Tools.getPKCode());
        tblUfMessage.setDelFlg("0");
        tblUfMessage.setVersion(Integer.valueOf("0"));
        
        //插入到数据库
        return insertData(tblUfMessage);
	}
	/**
	 * 功能描述:插入到数据库
	 * @param args
	 * 2007-11-29 上午10:02:49
	 * @version 1.0
	 * @author zhangwq
	 */
	private static TblUfMessage insertData(TblUfMessage tblUfMessage) {
		getService().saveOrUpdate(tblUfMessage);
		return tblUfMessage;
	}

    /**
     * 功能描述:更新到数据库
     * @param args
     * 2007-11-29 上午10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage modifyMessage(TblUfMessage tblUfMessage) {
    	getService().update(tblUfMessage);
        return tblUfMessage;
    }

    /**
     * 功能描述:读取数据
     * @param args
     * 2007-11-29 上午10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage loadMessage(String id) {
    	return (TblUfMessage)getService().load(TblUfMessage.class, id);
    }

    /**
     * 功能描述:删除数据
     * @param args
     * 2007-11-29 上午10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage delete(String id) {
    	TblUfMessage tblUfMessage;
    	
    	tblUfMessage = loadMessage(id);
    	if(tblUfMessage != null){
    		getService().delete(tblUfMessage);
    	}
        return tblUfMessage;
    }

    /**
     * 功能描述:删除数据
     * @param args
     * 2007-11-29 上午10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage delete(TblUfMessage tblUfMessage) {
    	getService().delete(tblUfMessage);
        return tblUfMessage;
    }
    
    /**
     * 共通Service调用接口
     */
    private static CommonService getService() {
		return (CommonService) ServiceProvider
				.getService(CommonService.SERVICE_NAME);
	}
}