/*
 * 创建日期 2004-10-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.util.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SimpleCache {
	private final Map cache = new HashMap();

	  public Object load(String objectName) { 
		// load the object somehow
	  }

	  public void clearCache() { 
		synchronized (cache) { 
		  cache.clear();
		}
	  }

	  public Object getObject(String objectName) {
		Object o =new Object();
		synchronized (cache) { 
		   o = cache.get(objectName);
		  if (o == null) {
			o = load(objectName);
			cache.put(objectName, o);
		  }
		}

		return o;
	  }

}
