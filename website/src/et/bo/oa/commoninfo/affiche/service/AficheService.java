/*
 * <p>Title:       �򵥵ı���</p>
 * <p>Description: ����ϸ��˵��</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.commoninfo.affiche.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


public interface AficheService {
    //�������
    public boolean addAficheInfo(IBaseDTO dto);
    //����ɾ��
    public boolean delAficheInfo(String[] str);
    //�õ�����
    public int getAficheInfoSize();
    //����������ѯ������Ϣ
    public List findAficheInfo(IBaseDTO dto,PageInfo pi);
    //��ѯ��������Ϣ��load
    public IBaseDTO getAficheInfo(String id);
    //�����б���ʾ
    public List getAficheList();
    //��ϸ�б���ʾ
    public int getIndexAficheSize();
    public List getIndexAficheList(IBaseDTO dto,PageInfo pi);
    
    //������ϸ�б���ʾ
    public List getAficheList(String id);
}
