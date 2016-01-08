package et.bo.oa.assissant.document.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface DocumentService {
//	向里面录入员工信息
    public boolean addDocInfo(IBaseDTO dto);
    
//	向里面录入员工信息
    public boolean addConfenceDocInfo(IBaseDTO dto);
    
    //修改文档信息
    public boolean updateDocInfo(IBaseDTO dto);
    
    //审批文档信息
    public boolean shenpiDocInfoSign(IBaseDTO dto);
    
    //审批文档信息
    public boolean shenpiDoc(IBaseDTO dto);
    
    //删除文档信息
    public boolean deleteDocInfo(IBaseDTO dto);
    
    //删除文档信息
    public boolean deleteDocInfo4(IBaseDTO dto);
    
    //得到条数
    public int getDocSize();
    
    //根据条件查询文档
    public List findDocInfo(IBaseDTO dto,PageInfo pi);
    
    //根据条件查询文档
    public List findDocInfo2(IBaseDTO dto,PageInfo pi);

    //根据条件查询文档
    public List findDocInfo4(IBaseDTO dto,PageInfo pi);
    
    //查询出条件信息以load
    public IBaseDTO getDocInfo(String id);
}
