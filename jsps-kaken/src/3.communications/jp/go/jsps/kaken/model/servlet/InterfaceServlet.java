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

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import jp.go.jsps.kaken.log.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;

import org.apache.commons.logging.*;

/**
 * CallServletHandlerで呼ばれた処理を実行するためのサーブレット。
 * 
 * ID RCSfile="$RCSfile: InterfaceServlet.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:54 $"
 */
public class InterfaceServlet extends HttpServlet {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * クラス名。 
	 */
	private static final String CLASS_NAME = InterfaceServlet.class.getName();

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(CLASS_NAME);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * 処理ハンドラマップ
	 */
	private Map commands = new HashMap();

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {

		//サービス実装クラスの登録
		commands = (Map)getServletContext().getAttribute(IServiceName.APPLICATION_SERVICE);

	}

	/* 
	 * 呼ばれたときにメッセージを表示する。
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		System.out.println(" Hello! " + getClass().getName() + ".");

		//パラメータの確認 (4 DEBUG)
		for (Enumeration e = request.getParameterNames();
			e.hasMoreElements();
			) {
			String sParamName = (String) e.nextElement();
			String[] sParamValues = request.getParameterValues(sParamName);
			System.out.println(
				"パラメータ::'"
					+ sParamName
					+ "'::'"
					+ Arrays.asList(sParamValues)
					+ "'");
		}

		//ヘッダー情報の確認 (4 DEBUG)
		for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();) {
			String sHeaderName = (String) e.nextElement();
			String sHeaderValue = request.getHeader(sHeaderName);
			System.out.println(
				"ヘッダー::'" + sHeaderName + "'::'" + sHeaderValue + "'");
		}

		return;
	}

	/* 
	 * コマンド名とオブジェクトを受け取り、処理した上で、返却されたオブジェクトを返す。
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		if (log.isDebugEnabled()) {
			log.debug(getClass().getName() + " リスクエスト処理を開始します。");
		}

		//クライアントへ返すオブジェクト
		Object objReturn = new Object();

		try {
			//呼び出し元からの引き数用ストリームを作成する
			ObjectInputStream dis =
				new ObjectInputStream(
					new BufferedInputStream(request.getInputStream()));

			//戻り値のMIMEタイプを設定する
			response.setContentType("application/octet-stream");

			//戻り値用のストリームを作成する
			ObjectOutputStream dos =
				new ObjectOutputStream(
					new BufferedOutputStream(response.getOutputStream()));

			//呼び出し元からの呼び出しサービス名を取得する
			String serveceName = dis.readUTF();

			//呼び出し元からの呼び出しメソッド名を取得する
			String strMethod = dis.readUTF();

			//呼び出し元からの呼び出しメソッドのパラメータを取得する
			Class[] paramTypes = (Class[]) dis.readObject();

			//呼び出し元からの呼び出しメソッドのパラメータを取得する
			Object[] params = (Object[]) dis.readObject();

			//実行メソッド				
			Method method = null;
			try {
				if (!commands.containsKey(serveceName)) {
					if (log.isInfoEnabled()) {
						log.info(
							"サービス名 '" + serveceName + "':実行対象のサービスが見つかりません。");
					}
					dos.writeObject(
						new NoSuchMethodException(
							"サービス名 '" + serveceName + "':実行対象のサービスが見つかりません。"));
				} else {
					//メソッドの取得
					method =findPublicMethod(commands.get(serveceName),
							strMethod,
							paramTypes);

					//invoke対象メソッドがみつからないとき。
					if (method == null) {
						if (log.isInfoEnabled()) {
							log.info(
								"処理名 '" + strMethod + "':実行対象のメソッドが見つかりません。");
						}
						dos.writeObject(
							new NoSuchMethodException(
								"処理名 '" + strMethod + "':実行対象のメソッドが見つかりません。"));
					}

					//★★パフォーマンスログ
					PerformanceLogWriter pw = null;
					if (log.isDebugEnabled()) {
						pw = new PerformanceLogWriter();
					}
					
					//実行
					objReturn =
						method.invoke(commands.get(serveceName), params);
					
					//★★パフォーマンスログ
					if(pw != null){
						pw.out("処理名：" + serveceName + "." + strMethod);
					}

					//メソッドからの戻り値を呼び出し元アプレットに渡す
					dos.writeObject(objReturn);
				}
			} catch (InvocationTargetException e) {
				//予期する例外
				if (e.getCause() instanceof ApplicationException) {
					//例外を呼び出し元に渡す
					if (log.isDebugEnabled()) {
						log.debug(
								"処理名 '"
									+ method.getDeclaringClass().getName()
									+ "#"
									+ strMethod
									+ "()':呼び出しで例外が発生しました。\n"
									, e.getCause());
					}
				//予期せぬ例外
				} else {
					//例外を呼び出し元に渡す
					if (log.isInfoEnabled()) {
						log.info(
							"処理名 '"
								+ method.getDeclaringClass().getName()
								+ "#"
								+ strMethod
								+ "()':呼び出しで例外が発生しました。\n"
								, e.getCause());
					}
				}
				dos.writeObject(e.getTargetException());

			} catch (Throwable e) {
				log.info("リスクエスト処理で例外が発生しました。", e);
				//例外を呼び出し元アプレットに渡す
				dos.writeObject(e);
			} finally {
				//処理が終了したら引き数用ストリームの後処理を行う
				dis.close();
				//処理が終了したら戻り値用ストリームの後処理を行う
				dos.flush();
				dos.close();
			}
		} catch (Throwable e) {
			log.info("クライアントとの通信処理に失敗しました。", e);
			throw new ServletException("クライアントとの通信処理に失敗しました。", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug(getClass().getName() + "リスクエスト処理を終了します。");
			}
		}
	}

	/**
	 * コマンドインターフェースを持ったクラスメソッドより、該当するメソッドを取得する。
	 * @param declaringClass	呼び出すメソッドを宣言したクラス。
	 * @param methodName		呼び出すメソッド名
	 * @param argClasses		メソッド引数
	 * @return
	 */
	private static Method findPublicMethod(
		Object declaringClass,
		String methodName,
		Class[] argClasses) {

		Method[] methods = declaringClass.getClass().getMethods();
		ArrayList list = new ArrayList();
		for (int i = 0; i < methods.length; i++) {
			// Collect all the methods which match the signature.
			Method method = methods[i];
			if (method.getName().equals(methodName)) {
				if (matchArguments(argClasses, method.getParameterTypes())) {
					list.add(method);
				}
			}
		}
		if (list.size() > 0) {
			if (list.size() == 1) {
				return (Method) list.get(0);
			} else {
				ListIterator iterator = list.listIterator();
				Method method;
				while (iterator.hasNext()) {
					method = (Method) iterator.next();
					if (matchExplicitArguments(argClasses,
						method.getParameterTypes())) {
						return method;
					}
				}
				// This list is valid. Should return something.
				return (Method) list.get(0);
			}
		}
		return null;
	}

	/**
	 * パラメータ引数とメソッドの引数配列が同じであるかを確認する。
	 * @param argClasses		パラメータ引数配列
	 * @param argTypes			メソッドの引数配列
	 * @return  パラメータ引数配列とメソッドの引数配列が等しいとき true 以外 false
	 */
	private static boolean matchExplicitArguments(
		Class[] argClasses,
		Class[] argTypes) {
		boolean match = (argClasses.length == argTypes.length);
		for (int j = 0; j < argClasses.length && match; j++) {
			Class argType = argTypes[j];
			if (argClasses[j] != argType) {
				match = false;
			}
		}
		return match;
	}

	/**
	 * パラメータ引数とメソッドの引数配列が代入可能(インターフェース）であるかを確認する。
	 * @param argClasses		パラメータ引数配列
	 * @param argTypes			メソッドの引数配列
	 * @return  パラメータ引数配列とメソッドの引数配列が一致するとき true 以外 false
	 */
	private static boolean matchArguments(
		Class[] argClasses,
		Class[] argTypes) {
		boolean match = (argClasses.length == argTypes.length);
		for (int j = 0; j < argClasses.length && match; j++) {
			Class argType = argTypes[j];
			// Consider null an instance of all classes.
			if (argClasses[j] != null
				&& !(argType.isAssignableFrom(argClasses[j]))) {
				match = false;
			}
		}
		return match;
	}
}
