/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.service;

import com.zyf.core.ServiceBase;

/**
 * 基础的服务接口, 只要方法接受参数就不允许是<code>null</code>, 对于<code>String</code>
 * 类型的参数不允许是<code>empty string</code>, 如果参数是空值抛出<code>NullPointerException</code>,
 * 省去了在每个方法开始出编写验证参数的代码
 * @author zhangli
 * @version $Id: ServiceBaseWithNotAllowedNullParamters.java,v 1.1 2007/11/05 03:16:23 yushn Exp $
 * @since 2007-4-22
 */
public interface ServiceBaseWithNotAllowedNullParamters extends ServiceBase {

}
