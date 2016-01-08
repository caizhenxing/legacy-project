package et.test.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTest extends TestCase {

	private final Log logger = LogFactory.getLog(getClass());

	private Configuration freemarker_cfg = null;

	public void testFreeMarker() {
		// @todo �Լ���һ����
		//NewsItem aItem = null;

		// @todo װ������
		// NewsItem = loadNewsItem(1);

		//FreeMarkerTest test = new FreeMarkerTest();

		Map root = new HashMap();
		//root.put("newsitem", aItem);

		String sGeneFilePath = "/tpxw/";

		String sFileName = "1.htm";

		boolean bOK = this.geneHtmlFile("/ftl/view.ftl", root, sGeneFilePath,
				sFileName);

	}

	/**
	 * ��ȡfreemarker������. freemarker����֧��classpath,Ŀ¼�ʹ�ServletContext��ȡ.
	 */
	protected Configuration getFreeMarkerCFG() {
		if (null == freemarker_cfg) {
			// Initialize the FreeMarker configuration;
			// - Create a configuration instance
			freemarker_cfg = new Configuration();

			// - FreeMarker֧�ֶ���ģ��װ�ط�ʽ,���Բ鿴API�ĵ�,���ܼ�:·��,����Servlet������,classpath�ȵ�

			// htmlskin�Ƿ���classpath�µ�һ��Ŀ¼
			freemarker_cfg.setClassForTemplateLoading(this.getClass(),
					"/ftl");
		}

		return freemarker_cfg;
	}

	/**
	 * ���ɾ�̬�ļ�.
	 * 
	 * @param templateFileName
	 *            ģ���ļ���,���htmlskin·��,����"/tpxw/view.ftl"
	 * @param propMap
	 *            ���ڴ���ģ�������Objectӳ��
	 * @param htmlFilePath
	 *            Ҫ���ɵľ�̬�ļ���·��,��������еĸ�·��,���� "/tpxw/1/2005/4/"
	 * @param htmlFileName
	 *            Ҫ���ɵ��ļ���,���� "1.htm"
	 */
	public boolean geneHtmlFile(String templateFileName, Map propMap,
			String htmlFilePath, String htmlFileName) {
		// @todo ��������ȡ��Ҫ��̬�ļ���ŵĸ�·��:��Ҫ��Ϊ�Լ������������
		String sRootDir = "D:/webtest/htmlfile";

		try {
			Template t = getFreeMarkerCFG().getTemplate(templateFileName);

			// �����·������,��ݹ鴴����Ŀ¼
			creatDirs(sRootDir, htmlFilePath);

			File afile = new File(sRootDir + "/" + htmlFilePath + "/"
					+ htmlFileName);

			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(afile)));

			t.process(propMap, out);
		} catch (TemplateException e) {
			logger.error("Error while processing FreeMarker template "
					+ templateFileName, e);
			return false;
		} catch (IOException e) {
			logger.error("Error while generate Static Html File "
					+ htmlFileName, e);
			return false;
		}

		return true;
	}

	/**
	 * �����༶Ŀ¼
	 * 
	 * @param aParentDir
	 *            String
	 * @param aSubDir
	 *            �� / ��ͷ
	 * @return boolean �Ƿ�ɹ�
	 */
	public static boolean creatDirs(String aParentDir, String aSubDir) {
		File aFile = new File(aParentDir);
		if (aFile.exists()) {
			File aSubFile = new File(aParentDir + aSubDir);
			if (!aSubFile.exists()) {
				return aSubFile.mkdirs();
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

}
