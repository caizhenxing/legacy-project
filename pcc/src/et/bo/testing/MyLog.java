package et.bo.testing;

import org.apache.log4j.xml.DOMConfigurator;

import et.bo.police.callcenter.Constants;

public class MyLog {
	public MyLog(){
		DOMConfigurator.configure(Constants.TEST_LOG_PATH);
	}
}
