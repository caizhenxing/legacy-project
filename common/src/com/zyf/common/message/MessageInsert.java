/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺2007-11-20����15:13:03
 * �ļ�����MessageService.java
 * �����ߣ�zhangwenqi
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
		//����Ϣ�������ڣ����ͼ�������ֵ
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        Date date = cal.getTime();
        messageBean.setCreateDate(date);
        //���ͼ�������ֵΪ0
        messageBean.setSendCount(0);
        //��Ϣ״̬����ֵΪ0
        messageBean.setMessageStatus("0");
        
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(calEnd.YEAR, 1);
        Date endDate = calEnd.getTime();
        
        //ӳ�䣬��ֵ
        TblUfMessage tblUfMessage = new TblUfMessage();
    	tblUfMessage.setSendUserid(messageBean.getSendUserId());
        tblUfMessage.setReceiverUserid(messageBean.getReceiveUserId());
        tblUfMessage.setTitle(messageBean.getTitle());
        tblUfMessage.setMessageContent(messageBean.getMessageContent());
        tblUfMessage.setMessageStatus(messageBean.getMessageStatus());
        tblUfMessage.setCreateDate(messageBean.getCreateDate());
        //���Ԥ�����Ϳ�ʼʱ�䲻��д����ֵΪϵͳ��ǰʱ��
        if ( messageBean.getScheduleDate() != null) {
        	tblUfMessage.setScheduleDate(messageBean.getScheduleDate());
        } else {
        	tblUfMessage.setScheduleDate(messageBean.getCreateDate());
        }
        //������ͽ���ʱ�䲻��д����ֵΪϵͳ��ǰʱ���һ��
        if ( messageBean.getEndDate() != null) {
        	tblUfMessage.setEndDate(messageBean.getEndDate());
        } else {
        	tblUfMessage.setEndDate(endDate);
        }
        tblUfMessage.setSendDate(messageBean.getSendDate());
        //������ʹ�������д����ֵΪ1
        if ( messageBean.getSendTime() != 0) {
        	tblUfMessage.setSendTime(messageBean.getSendTime());
        } else {
        	tblUfMessage.setSendTime(1);
        }
        tblUfMessage.setSendCount(messageBean.getSendCount());
        tblUfMessage.setIntervalTime(messageBean.getIntervalTime());
        
        //��ͨ�ֶθ�ֵ
        tblUfMessage.setMessageId(Tools.getPKCode());
        tblUfMessage.setDelFlg("0");
        tblUfMessage.setVersion(Integer.valueOf("0"));
        
        //���뵽���ݿ�
        return insertData(tblUfMessage);
	}
	/**
	 * ��������:���뵽���ݿ�
	 * @param args
	 * 2007-11-29 ����10:02:49
	 * @version 1.0
	 * @author zhangwq
	 */
	private static TblUfMessage insertData(TblUfMessage tblUfMessage) {
		getService().saveOrUpdate(tblUfMessage);
		return tblUfMessage;
	}

    /**
     * ��������:���µ����ݿ�
     * @param args
     * 2007-11-29 ����10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage modifyMessage(TblUfMessage tblUfMessage) {
    	getService().update(tblUfMessage);
        return tblUfMessage;
    }

    /**
     * ��������:��ȡ����
     * @param args
     * 2007-11-29 ����10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage loadMessage(String id) {
    	return (TblUfMessage)getService().load(TblUfMessage.class, id);
    }

    /**
     * ��������:ɾ������
     * @param args
     * 2007-11-29 ����10:02:49
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
     * ��������:ɾ������
     * @param args
     * 2007-11-29 ����10:02:49
     * @version 1.0
     * @author zhangwq
     */
    public static TblUfMessage delete(TblUfMessage tblUfMessage) {
    	getService().delete(tblUfMessage);
        return tblUfMessage;
    }
    
    /**
     * ��ͨService���ýӿ�
     */
    private static CommonService getService() {
		return (CommonService) ServiceProvider
				.getService(CommonService.SERVICE_NAME);
	}
}