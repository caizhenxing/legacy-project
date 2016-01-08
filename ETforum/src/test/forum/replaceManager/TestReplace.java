package test.forum.replaceManager;

import et.bo.forum.point.service.PointService;
import et.bo.forum.replaceManager.service.ReplaceManagerService;
import excellence.framework.base.container.SpringContainer;

public class TestReplace {
	public static void main(String[] args) {
		SpringContainer container = SpringContainer.getInstance();	
		ReplaceManagerService rm = (ReplaceManagerService) container.getBean("ReplaceManagerService");
		String postInfo ="fuck.............1212.........shit,,,,,,,,,333,,,,,,hello,,,";
		postInfo = rm.postReplace(postInfo);
		System.out.println(postInfo);
	}

}
