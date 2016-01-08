/*
 * <p>Title:       �򵥵ı���</p>
 * <p>Description: ����ϸ��˵��</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.news.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface ArticleService {
    // ������¼��������Ϣ
    public boolean addArticleInfo(IBaseDTO dto);

    // �޸�������Ϣ
    public boolean updateArticleInfo(IBaseDTO dto);

    // ɾ��������Ϣ
    public boolean deleteArticleInfo(IBaseDTO dto);

    // �õ�����
    public int getArticleSize();

    // ����������ѯ������Ϣ
    public List findArticleInfo(IBaseDTO dto, PageInfo pi);

    // ��ѯ��������Ϣ��load
    public IBaseDTO getArticleInfo(String id);

    //�����Ƽ�
    public boolean putPink(String state,String articleid);
    
    //�����ö�
    public boolean putTop(String state,String articleid);
    
    //ִ�в���
    public boolean putArticleOper(String type,String[] str);
    
    //�ƶ�����
    public boolean moveAll(String classid,String[] str);
    
    //  �õ�����
    public int getRecycleSize();

    // ����������ѯ������Ϣ
    public List findRecycleInfo(IBaseDTO dto, PageInfo pi);
    
    //��ԭ����
    public boolean revertNews(String articleid,String type);
    
    //��ԭѡ��������
    public boolean revertNews(String[] str);
    
    //ɾ�����ţ���ջ���վ
    public boolean delNews(String articleid,String type);
    
    //ɾ��ѡ��������
    public boolean delNews(String[] str);
    //��ҳ����
    public List getIndexList();
    
    //��ҳmore
    public int getIndexSize();
    public List findIndexInfo(IBaseDTO dto, PageInfo pi);
    //������ϸ��Ϣ��ʾ
    public List getNewsList(String id);
}
