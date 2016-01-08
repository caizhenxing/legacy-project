/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-5-5
 */
package et.bo.callcenter.pbx2ivr.service;

import java.util.List;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author ���Ʒ�
 *
 */
public interface Pbx2IvrService {

	/**
	 * �������еĽ�������IVR�˿�ӳ���ϵ
	 * @return
	 */
	public List<DynaBeanDTO> query();
	public IBaseDTO getPortMapInfo(String id);
	public void add(IBaseDTO dto);
	public void update(IBaseDTO dto);
	public void delete(IBaseDTO dto);
}
