package excellence.framework.base.dto.impl;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

import excellence.common.util.CreateConstants;
import excellence.framework.base.dto.IBaseDTO;




/**
 * 利用反射加载数据
 * @author 赵一非
 * @version 2.0 2005-08-10
 */
public abstract class BaseDTO implements IBaseDTO{
	
	//private PageInfo pageInfo;
	//private List list;
	
	

	public void loadOnMe(Object object) {
		// TODO Auto-generated method stub
		try {
			//BeanUtils.copyProperties(this,object);
			Map map=PropertyUtils.describe(object);
			Set set=map.keySet();
			Iterator i=set.iterator();
			while(i.hasNext())
			{
				StringBuffer method=new StringBuffer();
				method.append("get");
				Object key=i.next();
				
				String s=(String)key;
				if(s.equals("class"))
					continue;
				String first=s.substring(0,1);
				method.append(first.toUpperCase());
				method.append(s.substring(1));
				//System.out.println(method);
				//Class clas=PropertyUtils.getPropertyType(dist,s);
				Object value=MethodUtils.invokeExactMethod(object,method.toString(),null);
				if(value!=null)
				{
					
					
					
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Object loadOnYou(Class c) {
		// TODO Auto-generated method stub
		Object dist;
		try {
			dist=c.newInstance();
			//BeanUtils.copyProperties(dist,this);
			Map map=PropertyUtils.describe(dist);
			Set set=map.keySet();
			Iterator i=set.iterator();
			while(i.hasNext())
			{
				StringBuffer method=new StringBuffer();
				method.append("set");
				Object key=i.next();
				//Object value=map.get(key);
				String s=(String)key;
				if(s.equals("class"))
					continue;
				Class clas=PropertyUtils.getPropertyType(dist,s);
				Object arg=null;//=clas.newInstance();
				if(clas== String.class)
					arg=this.get(s);
				if(clas== Long.class)
					arg=Long.getLong((String)this.get(s));
				if(clas== Date.class)
					try {
						arg=CreateConstants.getTheTime((String)this.get(s));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						arg=null;
					}
				if(clas== Double.class)
					arg=Double.valueOf((String)this.get(s));
				if(clas== BigDecimal.class)
				
					arg=new BigDecimal((String)this.get(s));
				
				String first=s.substring(0,1);
				method.append(first.toUpperCase());
				method.append(s.substring(1));
				//System.out.println(key.getClass().getName());
				//if(value!=null)
				//System.out.println(clas.getName());
				//System.out.println(method);
				if(arg!=null)
				MethodUtils.invokeExactMethod(dist,method.toString(),arg);
			}
			return dist;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException n)
		{
			
		}
		return null;
	}
	

	public static void main(String[] args) {
		
	}

}
