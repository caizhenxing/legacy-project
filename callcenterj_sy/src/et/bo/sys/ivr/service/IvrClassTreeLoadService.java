package et.bo.sys.ivr.service;

import java.util.Map;

import et.bo.sys.basetree.service.impl.IVRBean;
import excellence.common.classtree.ClassTreeLoadService;

public interface IvrClassTreeLoadService extends ClassTreeLoadService {
	public Map<String,IVRBean> getIVRBeanMap();
}
