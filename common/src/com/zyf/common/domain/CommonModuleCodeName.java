/*
 * 浙江大学快威集团版权所有(2007-2008). Power by COHEG team.
 */
package com.zyf.common.domain;

import com.zyf.common.CommonConstants;
import com.zyf.framework.codename.AbstractModuleCodeName;

/**
 * @author zhangli
 * @since 2007-3-22
 * @version $Id: CommonModuleCodeName.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public class CommonModuleCodeName extends AbstractModuleCodeName {

    protected String getInternalModuleName() {
        return CommonConstants.MODULE_NAME;
    }
}
