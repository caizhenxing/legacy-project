/**
 * 沈阳卓越科技有限公司
 * 2008-5-5
 */
package et.bo.callcenter.pbx2ivr.service;

import java.util.List;

import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author 梁云锋
 *
 */
public interface Pbx2IvrService {

	/**
	 * 检索所有的交换机到IVR端口映射关系
	 * @return
	 */
	public List<DynaBeanDTO> query();
	public IBaseDTO getPortMapInfo(String id);
	public void add(IBaseDTO dto);
	public void update(IBaseDTO dto);
	public void delete(IBaseDTO dto);
}
