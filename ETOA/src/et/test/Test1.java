package et.test;

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
		System.out.println(dto==null);
	}

}
