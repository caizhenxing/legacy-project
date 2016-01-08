package com.zyf.scheduling;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.zyf.container.ServiceProviderUtils;
import com.zyf.container.support.ConfigFinishedEvent;

/**
 * <p>����������ʱִ�е�����ļ�����, �����յ����ü�����ɵ�֪ͨ��, ����ʵ���˽ӿ�
 * {@link com.zyf.scheduling.Schedulable Schedulable}������, ��Ӧ�ùر�ʱ
 * Ҫ��������������еĵ�������</p>
 * 
 * @author scott
 * @since 2006-2-28
 * @version $Id: SchedulingListener.java,v 1.1 2007/11/05 03:16:29 yushn Exp $
 *
 */
public class SchedulingListener implements ApplicationListener, DisposableBean {
    private Log logger = LogFactory.getLog(SchedulingListener.class);
    
    /**
     * ����ִ�е�������Ķ�ʱ��
     */
    private Timer timer = new Timer();
    
    /**
     * �����ȵ�����
     */
    private List scheduledList;

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ConfigFinishedEvent) {
            this.scheduledList = ServiceProviderUtils.getServicesOfType(Schedulable.class);
            SchedulingIterator iterator = new SchedulingIterator() {
                public void callback(Schedulable schedulable) {
                    schedulable.schedules(timer);
                }
            };
            
            iterator.iterator(scheduledList);
        }
    }

    public void destroy() throws Exception {
        timer.cancel();
        timer = null;
        
        if (this.scheduledList != null) {
            SchedulingIterator iterator = new SchedulingIterator() {
                public void callback(Schedulable schedulable) {
                    schedulable.cancel();
                }
            };
            
            iterator.iterator(scheduledList);
            scheduledList = null;
        }
    }

    private abstract class SchedulingIterator {
        public void iterator(List scheduledList) {
            for (Iterator it = scheduledList.iterator(); it.hasNext(); ) {
                Schedulable schedulable = (Schedulable) it.next();
                try {
                    callback(schedulable);
                } catch (Exception e) {
                    if (logger.isInfoEnabled()) {
                        logger.info("ִ�е�������ʱ��������", e);
                    }
                }
            }
        }
        
        public abstract void callback(Schedulable schedulable);
    }
}
