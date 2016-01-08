/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.commoninfo.affiche.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


public interface AficheService {
    //公告添加
    public boolean addAficheInfo(IBaseDTO dto);
    //公告删除
    public boolean delAficheInfo(String[] str);
    //得到条数
    public int getAficheInfoSize();
    //根据条件查询机器信息
    public List findAficheInfo(IBaseDTO dto,PageInfo pi);
    //查询出条件信息以load
    public IBaseDTO getAficheInfo(String id);
    //公告列表显示
    public List getAficheList();
    //详细列表显示
    public int getIndexAficheSize();
    public List getIndexAficheList(IBaseDTO dto,PageInfo pi);
    
    //公告详细列表显示
    public List getAficheList(String id);
}
