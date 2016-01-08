package com.zyf.persistent.search;

import com.zyf.persistent.filter.Paginater;

/**
 * ����ϵͳ�в�ѯ���ݵ���Ϣ, ������������, ��ҳ����Ϣ. �������ͨ�ò�ѯ�ܹ��е�һ����, ����
 * �ռ�����
 * @author scott
 * @since 2006-4-29
 * @version $Id: SearchInformation.java,v 1.1 2007/11/05 03:16:16 yushn Exp $
 *
 */
public class SearchInformation {
    /** �־û���Ĳ�ѯ���� */
    private SearchCondition[] searchConditions;
    
    /** �־û���ķ�ҳ��Ϣ */
    private Paginater paginater;
    
    /** �Ƿ���������ѯ����, ����������ĳЩ����²�ʹ�����еĲ�ѯ���� */
    private boolean ignoreSearchCondition = false;
    
    /** �Ƿ���Է�ҳ��Ϣ */
    private boolean ignorePaginater = false;
    
    public SearchInformation() {
        super();
    }
    
    public SearchInformation(SearchCondition[] searchConditions, Paginater paginater) {
        this.searchConditions = searchConditions;
        this.paginater = paginater;
    }

    public boolean isIgnorePaginater() {
        return ignorePaginater;
    }

    public void setIgnorePaginater(boolean ignorePaginater) {
        this.ignorePaginater = ignorePaginater;
    }

    public boolean isIgnoreSearchCondition() {
        return ignoreSearchCondition;
    }

    public void setIgnoreSearchCondition(boolean ignoreSearchCondition) {
        this.ignoreSearchCondition = ignoreSearchCondition;
    }

    public Paginater getPaginater() {
        return paginater;
    }

    public void setPaginater(Paginater paginater) {
        this.paginater = paginater;
    }

    public SearchCondition[] getSearchConditions() {
        return searchConditions;
    }

    public void setSearchConditions(SearchCondition[] searchConditions) {
        this.searchConditions = searchConditions;
    }
}
