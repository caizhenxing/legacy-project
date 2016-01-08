package com.zyf.persistent.filter.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibatis.sqlmap.engine.mapping.parameter.BasicParameterMap;
import com.ibatis.sqlmap.engine.mapping.parameter.BasicParameterMapping;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
import com.ibatis.sqlmap.engine.scope.RequestScope;
import com.ibatis.sqlmap.engine.type.TypeHandler;
import com.zyf.container.CommonServiceProvider;
import com.zyf.core.ContextInfo;
import com.zyf.persistent.DaoHelper;
import com.zyf.persistent.filter.Condition;
import com.zyf.persistent.filter.ConditionInfo;
import com.zyf.persistent.filter.Conditions;
import com.zyf.persistent.filter.ConfigurableConditionService;
import com.zyf.persistent.filter.Paginater;
import com.zyf.persistent.filter.ReworkingParameter;
import com.zyf.persistent.filter.SqlPopulater;
import com.zyf.persistent.filter.StatementAndParameters;
import com.zyf.utils.MiscUtils;

/**
 * ��Ӱ�ȫ,ͨ�ò�ѯ��������Ҫִ�е�<code>iBATIS's mapping</code>��, ���������<code>
 * SqlExecutorInvocationHandler</code>��<code>executeQuery</code>, ��һ����������
 * ִ���������Ĳ�ѯ�����������, ��ѯ�����ͷ�ҳ�����������ǲ���Ҫ��. �������������Ҫ����
 * <code>ContextInfo.concealQuery</code>�������ε���ѯ�����ͷ�ҳ��Ϣ. һ�������ǵ�����
 * һ���ͻ���Ϣʱ, ��<code>DAO</code>���������ķ���<code>listCustomers</code>, �������
 * �����л�������<code>listOrderByCustomer</code>�ķ���, ��ô�ڵ��õڶ�������֮ǰӦ����ȷ
 * �ص���<code>ContextInfo.concealQuery</code>�����ε����ִ���̵߳�����, �Ƿ�ָ�ȡ����
 * ʵ�ʵ�Ӧ��
 * @author scott
 * @since 2006-5-6
 * @version $Id: ReworkingParameterForCondition.java,v 1.1 2007/11/05 03:16:07 yushn Exp $
 *
 */
public class ReworkingParameterForCondition implements ReworkingParameter {
    private static final ThreadLocal definedParameterMappings = new ThreadLocal();

    private SqlPopulater sqlPopulater = new SimpleSqlPopulater();
    
    public void setSqlPopulater(SqlPopulater sqlPopulater) {
        this.sqlPopulater = sqlPopulater;
    }
    
    /**
     * �޸Ĳ���, ����<code>params</code>�����������������<ol>
     * <li>RequestScope</li>
     * <li>Connection</li>
     * <li>sql statement</li>
     * <li>parameters array</li>
     * <li>skipResults</li>
     * <li>maxResults</li>
     * <li>RowHandlerCallback</li>
     * </ol>
     */
    public void rework(Object[] params) {
        if (ContextInfo.isConcealQuery()) {
            return;
        } else {
            ContextInfo.concealQuery();
        }
        
        Conditions conds = ((ConfigurableConditionService) CommonServiceProvider.getConditionService())
            .populateCondition(null);
        if (conds != Conditions.EMPTY_CONDITIONS && ((DefaultConditions) conds).getResult() != StatementAndParameters.EMPTY_CONDITION) {
            DefaultConditions dc = (DefaultConditions) conds;
            StatementAndParameters sp = dc.getResult();
            Condition[] con = dc.getAppliedConditions();
            
            /* set parameterMap */
            RequestScope request = (RequestScope) params[0];
            BasicParameterMap parameterMap = (BasicParameterMap) request.getParameterMap();
            ParameterMapping[] parameterMappings = parameterMap.getParameterMappings();
            Object[] saved = new Object[2];
            saved[0] = request;
            saved[1] = parameterMap;
            definedParameterMappings.set(saved);
            
            parameterMap = reconstruct(parameterMap);
            request.setParameterMap(parameterMap);
            /* saves original parameter mappings */
            List mappings = new ArrayList(parameterMappings.length);
            mappings.addAll(Arrays.asList(parameterMappings));

            ParameterMapping[] newMappings = new ParameterMapping[parameterMappings.length + con.length];
            for (int i = 0; i < con.length; i++) {
                BasicParameterMapping mapping = new BasicParameterMapping();
                mapping.setPropertyName(dc.getDtoMetadata().getColumnName(con[i].getName()));
                mapping.setJavaType(con[i].getType());
                TypeHandler typeHandler = parameterMap.getDelegate().getTypeHandlerFactory().getTypeHandler(con[i].getType());
                if (typeHandler == null) {
                    typeHandler = parameterMap.getDelegate().getTypeHandlerFactory().getUnkownTypeHandler();
                }
                mapping.setTypeHandler(typeHandler);
                newMappings[i] = mapping;
            }
            System.arraycopy(parameterMappings, 0, newMappings, con.length, parameterMappings.length);
            List list = Arrays.asList(newMappings);
            parameterMap.setParameterMappingList(list);
            request.setParameterMap(parameterMap);
            
            /* set sql */
            String sql = sqlPopulater.where((String) params[2], sp.getStatement());
            params[2] = sql;
            
            /* set parameters */
            Object[] argus = sp.getParams();
            Object[] orig = (Object[]) params[3];
            Object[] newParams = new Object[argus.length + orig.length];
            System.arraycopy(argus, 0, newParams, 0, argus.length);
            System.arraycopy(orig, 0, newParams, argus.length, orig.length);
            params[3] = newParams;
        }
        
        /* set pagination */
        ConditionInfo info = ContextInfo.getContextCondition();
        Paginater p = info.getPaginater();
        if (p != Paginater.NOT_PAGINATED) {
            params[4] = new Integer(p.getPage() * p.getPageSize());
            params[5] = new Integer(p.getPageSize());
            /* if paginated, counting to the count of row */
            String countSql = sqlPopulater.count((String) params[2], null);
            int rowcount = 0;
            /*
             * �����й����з�������������, ÿ�β���params[3]�еĲ���������һ��nullԪ��,
             * ��ʹû�в�ѯ������������һ��null, �������� queryForInt ����ִ�д���. ����
             * ������˵�nullԪ��
             */
            Object[] param = null;
            if (params[3] == null || ((Object[]) params[3]).length == 0) {
                param = new Object[0];
            } else {
                param = new Object[((Object[]) params[3]).length];
                System.arraycopy(params[3], 0, param, 0, param.length);
                param = MiscUtils.removeNullElements(param);
            }
            if (param.length == 0) {
                rowcount = DaoHelper.getJdbcTemplate().queryForInt(countSql);
            } else {
                rowcount = DaoHelper.getJdbcTemplate().queryForInt(countSql, param);
            }
            p.setCount(rowcount);
        }
    }
    
    /**
     * ���¹���<code>BasicParameterMap</code>��, ��Ϊ�����ѻ�<code>parameterMappingIndex</code>
     * ������. �ڽ�������ִ����Ҫ����<code>setParameterMappingList</code>�����������еĲ���
     */
    private BasicParameterMap reconstruct(BasicParameterMap oldp) {
        BasicParameterMap newp = new BasicParameterMap(oldp.getDelegate());
        newp.setId(oldp.getId());
        newp.setParameterClass(oldp.getParameterClass());
        newp.setResource(oldp.getResource());
        return newp;
    }

    public void restore() {
        if (definedParameterMappings.get() != null) {
            Object[] saved = (Object[]) definedParameterMappings.get();
            RequestScope request = (RequestScope) saved[0];
            ParameterMap parameterMap = (ParameterMap) saved[1];
            request.setParameterMap(parameterMap);
            definedParameterMappings.set(null);
        }
    }
}
