package et.test;

import java.util.Date;

import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class Test1 {

	/**
	 * @param
	 * @version 2006-9-26
	 * @return
	 */
	public static void a(DynaBeanDTO dto)
	{
		DynaBeanDTO dto1=new DynaBeanDTO();
		dto1.set("aa","aa");
		dto=dto1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DynaBeanDTO dto=null;
		Test1.a(dto);
		System.out.println(TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd"));
		Date aa = TimeUtil.getTimeByStr("2009-01-01", "yyyy-MM-dd HH:mm:ss");
		System.out.println(aa.toString());
		TimeUtil tu = null;

	}

}
