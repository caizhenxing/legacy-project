package et.test;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Test {
	private static Log log = LogFactory.getLog(Test.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String action="ddddddddd@ddddd";
		if(action.indexOf('@')==-1)
			System.out.println(action);
		else
			System.out.println(action.replaceFirst("@","aa"));
		/*List<String> a=new ArrayList<String>();
		String[] s=null;
		a.toArray(s);
		
		Object[] o={"a","b","c","d"};
		s=(String[])o;*/
		//
		/*SpringContainer s=SpringContainer.getInstance();
		LogService ls=(LogService)s.getBean("logService");
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		for(int i=0;i<1;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.set("user", "zhaoyifei");
			dto.set("actionType", "comein");
			dto.set("ip", "192.168.1.3");
			dto.set("mod", "henhao");
			dto.set("remark", "aa");
			l.add(dto);
		}
		ls.addLog(l);*/
		//DOMConfigurator.configure("e:\\log4j.xml");
		//log.debug("-----------------------------");
		/*Map<String,String> map=System.getenv();
		Iterator<String> i=map.keySet().iterator();
		while(i.hasNext())
		{
			String key=i.next();
			
			
			
		}*/
		/*
		
		
		
		*/
	}
}
