/*
 * Created on 2003-10-16
 */
package base.zyf.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 加载spring配置文件到内存中
 * @author zhaoyifei guxiaofeng
 * @version 2007-1-15
 * @see
 */
public final class SpringContainer {

	// ~ Static fields/initializers
	// =============================================
	// spring文件路径
	private static String configPath = "/et/config/spring/";

	// 启始位置
	private static String configFile = "/et/config/spring/applicationContext.xml";

	/**
	 * DOCUMENT ME!
	 */
	private static SpringContainer global;

	/**
	 * DOCUMENT ME!
	 */
	private static ApplicationContext context;

	/**
	 * DOCUMENT ME!
	 */
	private static Log log = LogFactory.getLog(SpringContainer.class);

	static {

		global = new SpringContainer();
	}

	// ~ Constructors
	// ===========================================================

	/**
	 * 
	 */
	private SpringContainer() {

		init();
	}

	// ~ Methods
	// ================================================================

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static SpringContainer getInstance() {

		return global;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param serviceName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getBean(final String beanId) {

		return context.getBean(beanId);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param args
	 *            DOCUMENT ME!
	 */
	public static void main(final String[] args) {

		SpringContainer gg = SpringContainer.getInstance();
	}

	/**
	 * DOCUMENT ME!
	 * 初始化加载spring文件
	 */
	private static void init() {

		String path = SpringContainer.class.getResource(configFile).getPath();
		path = path.substring(1, path.length());

		// for linux or unix
		if (!path.substring(0, 1).equals("/")
				&& !path.substring(1, 2).equals(":")) {

			path = "/" + path;
		}

		File file = new File(path);
		File dir = file.getParentFile();
		String[] files = dir.list();
		List<String> filesa = new ArrayList<String>();
		int sa = 0;
		for (int i = 0, n = files.length; i < n; i++) {
			String filea = files[i];
			String type = filea.substring(filea.lastIndexOf(".") + 1);
			if (type.toLowerCase().equals("xml"))
				filesa.add(configPath + files[i]);
			log.info(files[i].toString());
		}
		String[] temp = new String[filesa.size()];
		temp = filesa.toArray(temp);
		log.info("Spring starting*******************");
		context = new ClassPathXmlApplicationContext(temp);
		log.info("Spring start success*******************");
		/*
		 * */
		SpringRunningContainer.loadContext(context);
	}
}