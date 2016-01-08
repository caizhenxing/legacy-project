/*
 * 创建日期 2005-1-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package txtToDB;

import java.util.*;
/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class DynamicBean {
	private HashMap hm=new HashMap();
	
	public Object getVar(Object k)
	{
		if(!hm.containsKey(k))
		return null;
		return hm.get(k);
	}
	public Set getMember()
	{
		return hm.keySet();
	}
	public int getMemberSize()
	{
		return hm.size();
	}
	public void setValue(Object k,Object v)throws Exception
	{
		if(hm.containsKey(k))
		{
			hm.remove(k);
			hm.put(k,v);
		}
		else throw new Exception();
		//hm.put();
	}
	public static void main(String[] args) {
	}
}
