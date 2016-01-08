package et.bo.common.testing;

import org.apache.log4j.xml.DOMConfigurator;

import et.bo.common.ConstantsCommonI;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

public class MyTesting {
	protected SpringContainer springContainer;
	public MyTesting(){
	}
	protected void testing(){
		DOMConfigurator.configure(ConstantsCommonI.TEST_LOG_PATH);
		springContainer = SpringContainer.getInstance();
	}
	
	public SpringContainer getSpringContainer() {
		return springContainer;
	}
	public void setSpringContainer(SpringContainer springContainer) {
		this.springContainer = springContainer;
	}
	
}
