package com.zyf.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.zyf.core.ContextInfo;
import com.zyf.exception.UnexpectedException;
import com.zyf.persistent.filter.Condition;
import com.zyf.persistent.filter.ConditionConstants;
import com.zyf.persistent.filter.ConditionInfo;
import com.zyf.persistent.filter.Paginater;

/**
 * <p>���<code>BaseActionForm</code>�а���<code>Condition</code>�����õ�<code>ContextInfo</code>
 * ��, ͬʱ�ж��Ƿ�ִ�з�ҳ����. ���һ��<code>web</code>����Ҫ�üܹ�ִ�з�ҳ����, ��ô��
 * ������һ��Ҫ��������<code>Paginater</code>�����ԵĲ���, ����<tt>paginater.page=0</tt>
 * ����<tt>paginater.pageSize=20</tt>��,��<code>struts</code>�������Ҫ��<code>RequestProcessor's populate</code>
 * ֮��ִ��</p>
 * <p>����ͬ������,����ֵ�������ڵ����(����"����ʱ����__��__")��������������: �����ڱ�����
 * �Ե�<code>map</code>���ֳ�<code>key</code>�Ϳ�����,��<tt>jsp</tt>�����ǿ�������
 * <code>struts's map</code>�����﷨, ������ʵ��Ӧ���е�һ������<pre>
 *    &lt;tr class="tab_5"&gt;
 *       &lt;td width="20%"&gt;����ʱ��&lt;/td&gt;
 *       &lt;td width="80%"&gt;
 *           &lt;input name="conditions(publishedTime1).name" value="publishedTime" type="hidden"/&gt;
 *           &lt;input name="conditions(publishedTime1).operator" value="&gt;=" type="hidden"/&gt;
 *           &lt;input name="conditions(publishedTime1).type" value="java.util.Date" type="hidden"/&gt;
 *           &lt;input name="conditions(publishedTime1).value" value="&lt;c:out value='${theForm.conditions['publishedTime1'].value}'/&gt;" type="text"/&gt;
 *           &lt;input name="conditions(publishedTime2).name" value="publishedTime" type="hidden"/&gt;
 *           &lt;input name="conditions(publishedTime2).operator" value="&lt;=" type="hidden"/&gt;
 *           &lt;input name="conditions(publishedTime2).type" value="java.util.Date" type="hidden"/&gt;
 *           &lt;input name="conditions(publishedTime2).value" value="&lt;c:out value='${theForm.conditions['publishedTime2'].value}'/&gt;" type="text"/&gt;
 *       &lt;/td&gt;
 *   &lt;/tr&gt;
 * </pre>��ϸ�������Ƭ��, ���Կ�������<code>key</code>����<tt>publishedTime1</tt>��<tt>publishedTime2</tt>
 * ��������<tt>conditions(publishedTime1).name</tt>��Ȼ��<tt>publishedTime</tt>
 * </p>
 * <p>���������������<code>empty</code>, ����û���������, ��������ͺ��Բ���</p>
 * @author scott
 * @since 2006-5-7
 * @version $Id: RetrieveParametersForContextInfo.java,v 1.1 2007/11/05 03:16:04 yushn Exp $
 *
 */
public class RetrieveParametersForContextInfo implements CustomRequestProcessorTemplate.ProcessPopulateExecutor {
	
    public void execute(HttpServletRequest request, HttpServletResponse response, ActionForm form, ActionMapping mapping) throws ServletException {
        ConditionInfo info = new ConditionInfo();
        ContextInfo.setContextCondition(info);
        
        if (form == null || !(form instanceof BaseActionForm)) {
            return;
        }
        BaseActionForm theForm = (BaseActionForm) form;
        List conditions = new ArrayList(theForm.getConditions().size());
//        conditions.addAll(theForm.getConditions().values());
//        Collections.sort(conditions);
        
        /* ���˵���û��������û��CompositionConditions������ */
        for (Iterator it = theForm.getConditions().values().iterator(); it.hasNext(); ) {
            Condition cond = (Condition) it.next();
            String name = cond.getName();
            /* �ڴ�������ʱ, Condition.value ������Ȼ�� form �ύ�� string */
            if (StringUtils.isNotBlank(name) || cond.getCompositeConditions().length > 0) {
                String value = (String) cond.getValue();
                Class type = cond.getType();
                if (type == null) {
                    type = ConditionConstants.DEFAULT_TYPE;
                }
                try {
                    Object result = BeanUtilsBean.getInstance().getConvertUtils().convert(value, type);
                    cond.setValue(result);
                    conditions.add(cond);
                } catch (Throwable e) {
                    String msg = "�޷�������[" 
                        + cond.getName() 
                        + "]ֵ[" 
                        + cond.getValue() 
                        + "]ת������[" 
                        + type.getName() 
                        + "]";
                    theForm.setException(new UnexpectedException(msg, e));
                    return;
                }
            }
        }
        Collections.sort(conditions);
        info.setAppendConditions((Condition[]) conditions.toArray(new Condition[conditions.size()]));
        
        Paginater paginater = theForm.getPaginater();
        if (paginater.isNotPaginated()) {
            paginater = Paginater.NOT_PAGINATED;
        }
        info.setPaginater(paginater);
        ContextInfo.setContextCondition(info);
    }
}
