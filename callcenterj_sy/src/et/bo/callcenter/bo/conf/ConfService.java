/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-6-10
 */
package et.bo.callcenter.bo.conf;

import java.util.List;

import excellence.common.tools.LabelValueBean;

/**
 * @author zhang feng
 * 
 */
public interface ConfService {

	/**
	 * �õ�������б����Ϣ
	 * 
	 * @return
	 */
	public List confDeployList(String roomno);

	/**
	 * �����ĸ��������������Ϣ�ı�Ϊ��Ӧ��״̬
	 * 
	 * @param id
	 */
	public void operConf(String id,String state);
	
	/**
	 * �õ��������ڽ��еĻ����ҵ��б�
	 * @return �����ҵ��б���Ϣ
	 */
	public List<LabelValueBean> getAllConfList();

}
