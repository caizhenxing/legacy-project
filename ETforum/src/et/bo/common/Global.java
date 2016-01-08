package et.bo.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import et.bo.common.ConstantsCommonI;
import excellence.framework.base.container.SpringContainer;
public class Global {
	private final String RUNING_STATE=ConstantsCommonI.RUNING_STATE_APPLICATION;
//	private final String RUNING_STATE=ConstantsI.RUNING_STATE_WEB;
	private static Global global;
	private static ApplicationContext context;
	private static Log log = LogFactory.getLog(Global.class);
	static {
        global = new Global();
    }
	private Global() {
        init();
    }

    //~ Methods ================================================================

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Global getInstance() {

        return global;
    }
    private void init(){
    	if(this.RUNING_STATE.equals(ConstantsCommonI.RUNING_STATE_APPLICATION)){
    		
    	}
    }
}
