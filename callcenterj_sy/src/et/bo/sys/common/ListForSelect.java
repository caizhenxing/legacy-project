/**
 * className TreePropertyService 
 * 
 * �������� 2008-08-21
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.common;

import java.util.List;

import et.bo.stat.service.CorpInfoBySeatService;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * �б���ʾ��
 *
 * @version 	jan 01 2008 
 * @author 
 */
public class ListForSelect {
	/**
	 * ��ѯ��ϯԱ��
	 * @return List<LabelValueBean> userid userid
	 */
	public static List<LabelValueBean> getUserList()
	{
		String sql = SysStaticParameter.QUERY_USER_SQL;
		CorpInfoBySeatService s = (CorpInfoBySeatService)SpringRunningContainer.getInstance().getBean("corpInfoBySeatService");
		
		return s.userLVQuery(sql);
	}

}
