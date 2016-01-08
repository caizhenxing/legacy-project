package test.client;

import java.net.MalformedURLException;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import test.ITestSer;

public class TestMain {

	/**
	 * @param
	 * @version 2006-11-30
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Service srvcModel = new
        ObjectServiceFactory().create(ITestSer.class);
        XFireProxyFactory factory =
           new XFireProxyFactory(XFireFactory.newInstance().getXFire());
        String url =
           "http://192.168.1.3:8081/TestService/services/TestSer";
        try {
        	ITestSer srvc = (ITestSer)factory.create(srvcModel, url);
           String result = srvc.example("hello world");
           System.out.print(result);
        } catch (MalformedURLException e){
           e.printStackTrace();
        }

	}

}
