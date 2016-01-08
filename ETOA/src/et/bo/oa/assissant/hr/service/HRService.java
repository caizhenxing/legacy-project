package et.bo.oa.assissant.hr.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface HRService {
    //向里面录入员工信息
    public boolean addHrInfo(IBaseDTO dto);
    
    //修改员工信息
    public boolean updateHrInfo(IBaseDTO dto);
    
    //删除员工信息
    public boolean deleteHrInfo(IBaseDTO dto);
    
    //得到条数
    public int getHrSize();
    
    //根据条件查询员工
    public List findHrInfo(IBaseDTO dto,PageInfo pi);
    
    //查询出条件信息以load
    public IBaseDTO getHrInfo(String id);
    
    /**
     * 修改照片
     * @param
     * @version 2006-9-16
     * @return
     */
    public void updatePhoto(String id,String path);
    
}
