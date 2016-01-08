/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project framework
 */
package com.zyf.business.spi;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.zyf.business.Initializable;
import com.zyf.container.ServiceProviderUtils;
import com.zyf.container.support.ConfigFinishedEvent;
import com.zyf.exception.UnexpectedException;

/**
 * spi class to init all concreate classes of {@link Initializable}
 * @see Initializable
 * @see ConfigFinishedEvent
 * @since 2006-8-10
 * @author java2enterprise
 * @version $Id: InitializableBeansProcessor.java,v 1.1 2007/11/05 03:16:29 yushn Exp $
 */
public class InitializableBeansProcessor implements ApplicationListener {

	private static transient Log logger = LogFactory.getLog(InitializableBeansProcessor.class);
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (ConfigFinishedEvent.class.isInstance(event)) {
			init(event);
		}
	}

	private void init(ApplicationEvent event) {
		if (logger.isInfoEnabled()) {
			logger.info("开始执行所有实现 " + Initializable.class + " 接口的 init 方法");
		}
		
		List beans = ServiceProviderUtils.getServicesOfType(Initializable.class);
		for (Iterator iter = beans.iterator(); iter.hasNext(); ) {
			Object bean = iter.next();
			Assert.isInstanceOf(Initializable.class, bean);
			try {
                if (logger.isDebugEnabled()) {
                    logger.debug("开始初始化[" + Arrays.asList(ClassUtils.getAllInterfaces(bean)) + "的init");
                }
				((Initializable) bean).init();
                if (logger.isDebugEnabled()) {
                    logger.debug("完成初始化[" + Arrays.asList(ClassUtils.getAllInterfaces(bean)) + "的init");
                }
			} catch (Throwable t) {
				if (logger.isErrorEnabled()) {
                    String msg = "Initializable.init error: bean [" + 
                        bean.getClass().getName() + "]";
					logger.error(msg, t);
					throw new UnexpectedException(msg, t);
				}
			}
		} 
		
		if (logger.isInfoEnabled()) {
			logger.info("所有实现 " + Initializable.class + " 接口的 init 方法执行完毕");
		}
	}
}
