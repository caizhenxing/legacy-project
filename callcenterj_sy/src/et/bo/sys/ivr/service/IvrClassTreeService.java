package et.bo.sys.ivr.service;

import java.util.List;

import et.bo.sys.basetree.service.impl.IVRBean;
import excellence.common.classtree.ClassTreeService;

public interface IvrClassTreeService extends ClassTreeService {
	public IVRBean getIVRBeanById(String id);
	public List<IVRBean> getIVRBeanListById(String id);
}
