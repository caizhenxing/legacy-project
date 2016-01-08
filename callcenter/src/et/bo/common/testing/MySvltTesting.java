package et.bo.common.testing;

import org.apache.log4j.xml.DOMConfigurator;

import et.bo.common.ConstantsCommonI;
import excellence.framework.base.container.SpringRunningContainer;

public class MySvltTesting {
	protected SpringRunningContainer springContainer;
	public MySvltTesting(){
	}
	protected void testing(){
//		DOMConfigurator.configure(ConstantsCommonI.TEST_LOG_PATH);
		springContainer = SpringRunningContainer.getInstance();
	}
	
	public SpringRunningContainer getSpringContainer() {
		return springContainer;
	}
	public void setSpringContainer(SpringRunningContainer springContainer) {
		this.springContainer = springContainer;
	}
	
}
