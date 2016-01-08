/**
 * @(#)LeaveWordService.java 1.0 //
 * 
 *  ��  
 * 
 */
package et.bo.oa.commoninfo.leaveWord.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe <code>LeaveWordService</code> is interface
 * @author ��Ҷ����
 * @version 1.0, //
 * @see
 * @see
 */
public interface LeaveWordService {
    /**
	 * @describe��¼�����԰���Ϣ
	 * @param 
	 * @return 
	 * 
	 */
    public boolean addLeaveWordInfo(IBaseDTO dto);
    /**
	 * @describe �޸����԰���Ϣ
	 * @param
	 * @return
	 * 
	 */  
//    public boolean updateLeaveWordInfo(IBaseDTO dto);
    /**
	 * @describe ɾ�����԰���Ϣ
	 * @param
	 * @return
	 * 
	 */
    public boolean deleteLeaveWordInfo(IBaseDTO dto);
    /**
	 * @describe �õ�����
	 * @param
	 * @return
	 * 
	 */    
    public int getLeaveWordSize();
    /**
	 * @describe ����������ѯ���԰���Ϣ
	 * @param
	 * @return
	 * 
	 */    
    public List findLeaveWordInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe ����������ѯ���԰���ϸ��Ϣ
	 * @param
	 * @return
	 * 
	 */    
    public List findSeeLeaveWordInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * @describe ����id��ѯ��Ϣload
	 * @param
	 * @return
	 * 
	 */
    public IBaseDTO getLeaveWordInfo(String id);
    /**
	 * @describe �ж��Ƿ���ͬ�����
	 * @param
	 * @return
	 * 
	 */
//    public boolean isHaveSameName (IBaseDTO dto);
    /**
	 * @describe ȡ�����LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
//    public  List<LabelValueBean> getLabelList(String userId, String sign);
    /**
	 * @describe �������Idȡ�������
	 * @param
	 * @return   �����
	 * 
	 */
//    public String getSortNameById (String Id);

}
