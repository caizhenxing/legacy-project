/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.web;

import java.util.List;

import com.zyf.web.BaseActionForm;

/**
 * ����<code>struts</code>��ǰ��<code>form-bean</code>����
 * @author zhangli
 * @version $Id: BaseForm.java,v 1.1 2007/11/05 03:16:30 yushn Exp $
 * @since 2007-4-29
 */
public class BaseForm extends BaseActionForm {

    private List list;
    
    public boolean isShrink() {
        return false;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
