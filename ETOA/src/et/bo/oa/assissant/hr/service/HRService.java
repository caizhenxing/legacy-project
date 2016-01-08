package et.bo.oa.assissant.hr.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface HRService {
    //������¼��Ա����Ϣ
    public boolean addHrInfo(IBaseDTO dto);
    
    //�޸�Ա����Ϣ
    public boolean updateHrInfo(IBaseDTO dto);
    
    //ɾ��Ա����Ϣ
    public boolean deleteHrInfo(IBaseDTO dto);
    
    //�õ�����
    public int getHrSize();
    
    //����������ѯԱ��
    public List findHrInfo(IBaseDTO dto,PageInfo pi);
    
    //��ѯ��������Ϣ��load
    public IBaseDTO getHrInfo(String id);
    
    /**
     * �޸���Ƭ
     * @param
     * @version 2006-9-16
     * @return
     */
    public void updatePhoto(String id,String path);
    
}
