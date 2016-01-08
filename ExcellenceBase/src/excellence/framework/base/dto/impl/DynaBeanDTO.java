package excellence.framework.base.dto.impl;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import excellence.common.util.CreateConstants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
/**
 * 
 * @author ’‘“ª∑«
 * @version 1.0 2005-08-09
 */
public class DynaBeanDTO extends ActionForm implements IBaseDTO {

	LazyDynaBean bdb=new LazyDynaBean(new LazyDynaClass());
	private static Log log = LogFactory.getLog(DynaBeanDTO.class);
	//Map bdb=new HashMap();
	public boolean contains(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return this.bdb.contains(arg0,arg1);
	}

	public Object get(String arg0) {
		// TODO Auto-generated method stub
		return this.bdb.get(arg0);
	}

	public Object get(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return this.bdb.get(arg0,arg1);
	}

	public Object get(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return this.get(arg0,arg1);
	}

	public DynaClass getDynaClass() {
		// TODO Auto-generated method stub
		return this.bdb.getDynaClass();
	}

	public void remove(String arg0, String arg1) {
		// TODO Auto-generated method stub
		this.bdb.remove(arg0,arg1);
	}

	public void set(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1==null)
			this.bdb.set(arg0,"");
		this.bdb.set(arg0,arg1);
	}

	public void set(String arg0, int arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.bdb.set(arg0,arg1,arg2);
	}

	public void set(String arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.bdb.set(arg0,arg1,arg2);
	}

	public void setObject(Object object) {
		// TODO Auto-generated method stub
		if(object==null)
			return;
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
				Object value=null;
				boolean po=false;
				Class clas=PropertyUtils.getPropertyType(object,s);
				
				if(clas==String.class)
				{
					value=MethodUtils.invokeExactMethod(object,method.toString(),null);
					po=true;
				}
				if(clas==Long.class)
				{
					Object temp=MethodUtils.invokeExactMethod(object,method.toString(),null);
					value=temp.toString();
					po=true;
				}
				if(clas== Date.class)
				{
					Object temp=MethodUtils.invokeExactMethod(object,method.toString(),null);
					Date d=(Date)temp;
					value=TimeUtil.getTheTimeStr(d);
					po=true;
				}
				if(clas== Double.class)
				{
					Object temp=MethodUtils.invokeExactMethod(object,method.toString(),null);
					value=temp.toString();
					po=true;
				}
				if(clas== BigDecimal.class)
				{
					Object temp=MethodUtils.invokeExactMethod(object,method.toString(),null);
					value=temp.toString();
					po=true;
				}
				if(clas==Set.class)
					continue;
				if(clas==List.class)
					continue;
				if(clas==Map.class)
					continue;
				if(clas.isAssignableFrom(Collection.class))
					continue;
				if(!po)
				{
					Object temp=MethodUtils.invokeExactMethod(object,method.toString(),null);
					if(temp!=null)
					value=MethodUtils.invokeExactMethod(temp,"getId",null);
				}
				if(value!=null)
				{
					this.set(s,value);
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
	public void loadValue(Object dist)
	{
		//Object dist;
		if(dist==null)
			return;
		try {
			//dist=c.newInstance();
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
				
				if(clas==Set.class)
					continue;
				if(clas==List.class)
					continue;
				if(clas==Map.class)
					continue;
				if(clas.isAssignableFrom(Collection.class))
					continue;
				Object arg=null;//=clas.newInstance();
				boolean po=false;
				try {
					if(clas== String.class)
					{
						arg=this.get(s);
						po=true;
					}
					if(clas== Long.class)
					{
						arg=Long.getLong((String)this.get(s));
						po=true;
					}
					if(clas== Date.class)
					{
						arg=TimeUtil.getTimeByStr((String)this.get(s));
						po=true;
					}
							
						
					if(clas== Double.class)
					{
						arg=Double.valueOf((String)this.get(s));
						po=true;
					}
					if(clas== BigDecimal.class)
					{
						arg=new BigDecimal((String)this.get(s));
						po=true;
					}
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					continue;
				}
				
				String first=s.substring(0,1);
				method.append(first.toUpperCase());
				method.append(s.substring(1));
				
				if(arg!=null&&po)
				{
					MethodUtils.invokeExactMethod(dist,method.toString(),arg);
					
				}
			}
			
		}  catch (IllegalAccessException e) {
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
		
	}
	public static void main(String[] arg0)
	{
		
	}
}
