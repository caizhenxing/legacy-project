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
package jp.go.jsps.kaken.model.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ConnectException;
import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * サーブレットのメソッドを呼び出すための処理実行ハンドラクラス。
 * 
 * ID RCSfile="$RCSfile: CallServletHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:51 $"
 */
public class CallServletHandler implements InvocationHandler{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(CallServletHandler.class);

	/**
	 * サーブレットURL 
	 */
	private String strURL;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * サービス名 
	 */
	private String serviceName =null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param serviceName	サービス実装名
	 */
	public CallServletHandler(String serviceName) {
		super();
		this.serviceName = serviceName;
		this.strURL = ApplicationSettings.getString(ISettingKeys.GYOMU_SERVLET_URL);
	}

	/**
	 * コンストラクタ。
	 * @param serviceName	サービス実装名
	 * @param serverUrl		サーバURI
	 * 
	 */
	public CallServletHandler(String serviceName,String serverUrl) {
		super();
		this.serviceName = serviceName;
		this.strURL = serverUrl;
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (args == null) {
			args = new Object[0];
		}
		return request(serviceName, method.getName(),method.getParameterTypes(), args);
	}


	/**
	 * サーバに接続し情報を取得する。
	 * @param serviceName	呼び出すサービス名。
	 * @param method		呼び出すメソッド名。
	 * @param paramTypes	呼び出すメソッドのパラメータ型配列。
	 * @param params		呼び出すメソッドのパラメータ配列。
	 * @return				処理結果オブジェクト
	 * @throws Exception	
	 */
	private Object request(
		final String serviceName,
		final String method,
		final Class[] paramTypes,
		final Object[] params)
		throws ApplicationException {

		ObjectOutputStream Out = null;
		ObjectInputStream In = null;
		Object objReturn = new Object();
		try {
			URL aURL;
			try {
				aURL = new URL(strURL);
			} catch (MalformedURLException e) {
				throw new SystemException("サーブレットアドレス '" + strURL + "'が無効です。",e);
			}

			//サーブレットに接続する
			URLConnection uCon = aURL.openConnection();
			uCon.setRequestProperty(
				"Content-type",
				"application/octet-stream");
			uCon.setUseCaches(false);
			//サーバからへの書き込みを許可する
			uCon.setDoOutput(true);
			//サーバからの読み込みを許可する
			uCon.setDoInput(true);

			try {
				//Servletへの出力ストリームを作成する
				Out = new ObjectOutputStream( new BufferedOutputStream(uCon.getOutputStream()));
				//サーブレットにサービス名、メソッド名、パラメータを送る
				Out.writeUTF(serviceName);
				Out.writeUTF(method);
				Out.writeObject(paramTypes);
				Out.writeObject(params);
				Out.flush();
			} finally {
				if (Out != null)
					Out.close();
			}
			
			try {
				//Servletからの入力ストリームを作成する
				In = new ObjectInputStream(	new BufferedInputStream(uCon.getInputStream()));
				//戻り値を取得する
				objReturn = In.readObject();
			} finally {
				if (In != null)
					In.close();
			}
			
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("サーバー接続処理中に例外が発生しました。", e);
			}
			throw new ConnectException("サーバー接続処理中に例外が発生しました。", e);
		}

		//サーブレットから例外が戻ってきていないかチェックする
		if (objReturn instanceof ApplicationException) {
			throw (ApplicationException) objReturn;
		} else if (objReturn instanceof SystemException) {
			throw (SystemException) objReturn;
		} else if (objReturn instanceof IOException) {
			//サーバー接続処理中に例外が発生しました。
			throw new ConnectException("サーバー処理中にIO例外が発生しました。",(IOException) objReturn);
		} else if (objReturn instanceof Throwable) {
			if (log.isErrorEnabled()) {
				log.error("サーバ内部で予期せぬ例外が発生しました。", (Throwable) objReturn);
			}
			throw new SystemException("サーバ内部で予期せぬ例外が発生しました。",(Throwable) objReturn);
		}
		return objReturn;
	}
}
