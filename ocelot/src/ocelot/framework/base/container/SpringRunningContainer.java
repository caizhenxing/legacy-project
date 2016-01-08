package ocelot.framework.base.container;

import org.springframework.context.ApplicationContext;

 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public final class SpringRunningContainer {

	private static ApplicationContext context;
	
	private static SpringRunningContainer suc;
	
	
	private SpringRunningContainer (ApplicationContext context)
	{
		this.context=context;
		
	}
	public static void loadContext(ApplicationContext context)
	{
		suc=new SpringRunningContainer(context);
	}
	public static SpringRunningContainer getInstance()
	{
		return suc;
	}
	public ApplicationContext getContext()
	{
		return context;
	}
	public Object getBean(String name)
	{
		return context.getBean(name);
	}
}
