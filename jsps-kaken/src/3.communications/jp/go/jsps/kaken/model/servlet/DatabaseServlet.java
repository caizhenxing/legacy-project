/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;

import jp.go.jsps.kaken.model.vo.ServiceInfo;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * 設定情報を取得するサーブレット。
 * 
 * ID RCSfile="$RCSfile: DatabaseServlet.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:54 $"
 */
public final class DatabaseServlet extends HttpServlet {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(DatabaseServlet.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * コマンドを保持するマップ。
	 */
	private Map commands = new HashMap();

	/**
	 * 設定情報XML
	 */
	private String pathname ="/WEB-INF/service-setting.xml";

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * Gracefully shut down this database servlet, releasing any resources
	 * that were allocated at initialization.
	 */
	public void destroy() {
		getServletContext().removeAttribute(IServiceName.APPLICATION_SERVICE);
	}

	/**
	 * 初期化処理を行う。
	 * 設定ファイルを読込む。
	 */
	public void init() throws ServletException {

		try {
			load();
		} catch (Exception e) {
			log.error(pathname + "(設定ファイル):読込に失敗しました。", e);
			throw new UnavailableException(pathname + "(設定ファイル):読込に失敗しました。");
		}

		//アプリケーションに登録
		getServletContext().setAttribute(IServiceName.APPLICATION_SERVICE,commands);

		//for debug
		if (log.isDebugEnabled()) {
			for (Iterator iter = commands.keySet().iterator();
				iter.hasNext();
				) {
				Object serviceName = iter.next();
				Object serviceClass = commands.get(serviceName);
				log.debug(
					"サービス名:'"
						+ serviceName
						+ "' \t 実装クラス名:'"
						+ serviceClass.getClass().getName());
			}
		}
	}



	/**
	 * サービスを追加する。
	 * サービスクラスのインスタンスを作成する。
	 * @param info	サービス情報オブジェクト
	 */
	public void addService(ServiceInfo info) {
		try {
			Object aInstance = Class.forName(info.getType()).newInstance();
			commands.put(info.getName(), aInstance);
		} catch (Exception e) {
			throw new SystemException(
				"サービス'"+info.getName()+ "'のクラス'" + info.getType() + "'のインスタンスの作成に失敗しました。",e);
		 }
	}
	

	/**
	 *	設定情報ファイルを読み込む。
	 */
	private synchronized void load() throws Exception {

		//初期化
		InputStream is = getServletContext().getResourceAsStream(pathname);
		if (is == null) {
			log.error(pathname + "(設定情報ファイル):見つかりません。");
			return;
		}

		BufferedInputStream bis = new BufferedInputStream(is);

		// Construct a digester to use for parsing
		Digester digester = new Digester();
		digester.push(this);
		digester.setValidating(false);

		digester.addObjectCreate(
			"service-settings/service",
			"jp.go.jsps.kaken.model.vo.ServiceInfo");
		digester.addSetProperties("service-settings/service");
		digester.addSetNext("service-settings/service", "addService","jp.go.jsps.kaken.model.vo.ServiceInfo");

		// Parse the input stream to initialize our database
		try {
			digester.parse(bis);
		} catch (IOException e) {
			log.error(pathname + "(設定情報ファイル):読込に失敗しました。", e);
			throw e;
		} catch (SAXException e) {
			log.error(pathname + "(設定情報ファイル):解析に失敗しました。", e);
			throw e;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ioe) {
					log.error(
						pathname + "(設定情報ファイル):ファイルIOエラーです。",
						ioe);
				}
			}
		}
	}
}
