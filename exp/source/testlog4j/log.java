/*
 * 创建日期 2005-1-10
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testlog4j;

import org.apache.log4j.*;
import org.apache.log4j.xml.*;

/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class log {

	static Logger l = Logger.getLogger(log.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("e:\\test.properties");
		//DOMConfigurator.configure("e:\\log4j.xml");
		//l.setLevel((Level)Level.ERROR);
		l.debug("esdfd");
		l.warn("fdsafds");
		l.info("fefsdfds");
		l.error("fsdfdsf");
	}
}
