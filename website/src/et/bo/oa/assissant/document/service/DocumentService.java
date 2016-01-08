package et.bo.oa.assissant.document.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface DocumentService {
//	������¼��Ա����Ϣ
    public boolean addDocInfo(IBaseDTO dto);
    
//	������¼��Ա����Ϣ
    public boolean addConfenceDocInfo(IBaseDTO dto);
    
    //�޸��ĵ���Ϣ
    public boolean updateDocInfo(IBaseDTO dto);
    
    //�����ĵ���Ϣ
    public boolean shenpiDocInfoSign(IBaseDTO dto);
    
    //�����ĵ���Ϣ
    public boolean shenpiDoc(IBaseDTO dto);
    
    //ɾ���ĵ���Ϣ
    public boolean deleteDocInfo(IBaseDTO dto);
    
    //ɾ���ĵ���Ϣ
    public boolean deleteDocInfo4(IBaseDTO dto);
    
    //�õ�����
    public int getDocSize();
    
    //����������ѯ�ĵ�
    public List findDocInfo(IBaseDTO dto,PageInfo pi);
    
    //����������ѯ�ĵ�
    public List findDocInfo2(IBaseDTO dto,PageInfo pi);

    //����������ѯ�ĵ�
    public List findDocInfo4(IBaseDTO dto,PageInfo pi);
    
    //��ѯ��������Ϣ��load
    public IBaseDTO getDocInfo(String id);
}
