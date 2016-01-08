/*======================================================================*/
/*    SYSTEM      :                                                     */
/*    Source name : SendMailer.java                                     */
/*    Description : メール送信クラス                                     */
/*                                                                      */
/*    Author      : T.Takano (NEXS)                                     */
/*    Date        : 2003/08/**                                          */
/*                                                                      */
/*    Revision history                                                  */
/*    Date    Revision    Author         Description                    */
/*                                                                      */
/*======================================================================*/

package jp.go.jsps.kaken.util;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.*;


/*************************************************************************
 * メール送信クラス<!--.--><br>
*************************************************************************/
public class SendMailer{
	
	/** SMTPサーバプロパティ */
	private Properties props = null;
	
	/** 文字エンコード */
	private static final String ENCODE = "iso-2022-jp";
	
    /** メール送信ステータスログ */
    protected static Log log = LogFactory.getLog("mail");

	/** 文字コードマッピング補正クラス */
	private static UnicodeCorrector  corrector_;
	
	
	/******************************************************
	 * static initializer<!--.--><br>
	 * 文字コードマッピング補正インスタンスを生成する。
	******************************************************/
	static {
		try {
			corrector_ = UnicodeCorrector.getInstance(ENCODE);
		} catch (UnsupportedEncodingException e) {
			log.warn("【警告】文字コードマッピング補正を行ないません。原因:'" + e.getMessage() + "'");
			e.printStackTrace();
			corrector_ = new NullObjectCorrector();	//NullObject
		}
	}
	
	
	
	/******************************************************
	 * コンストラクタ<!--.--><br>
	 * @param  svAddr                    メールサーバアドレス
	 * @throws IllegalArgumentException  引数がnullの場合
	******************************************************/
	public SendMailer(String svAddr) throws IllegalArgumentException {
		//SMTPサーバアドレスの設定
		this.props = System.getProperties();
		props.put("mail.smtp.host",svAddr);
	}
	
	
	
	/******************************************************
	 * メール送信メソッド<!--.--><br>
	 * CC等を送信しない場合は null をセットする。<br>
	 * @param   from       差出人
	 * @param   to         宛先
	 * @param   cc         CC差出人
	 * @param   bcc        BCC差出人
	 * @param   subject    標題
	 * @param   comment    コメント（本文）
	 * @throws  Exception  例外が発生した場合
	******************************************************/
	public void sendMail(String from,
						 String to,
						 String cc,
						 String bcc,
						 String subject,
						 String comment ) throws Exception
	{
		//１要素の配列を生成してメソッドを呼び出す
		this.sendMail(from,
					  new String[]{ to },
					  new String[]{ cc },
					  new String[]{ bcc },
					  subject,
					  comment);
	}
	
	
	
	/******************************************************
	 * メール送信メソッド<!--.--><br>
	 * 複数人の差出人にメールを送信する。
	 * CC等を送信しない場合は null をセットする。<br>
	 * @param   from       差出人
	 * @param   to         宛先
	 * @param   cc         CC差出人
	 * @param   bcc        BCC差出人
	 * @param   subject    標題
	 * @param   comment    コメント（本文）
	 * @throws  Exception  例外が発生した場合
	******************************************************/
	public void sendMail(String   from,
						 String[] to,
						 String[] cc,
						 String[] bcc,
						 String   subject,
						 String   comment ) throws Exception
	{
        StringBuffer details = new StringBuffer();
        details.append(",宛先:'").append(StringUtils.join(to,",")).append("'");
        details.append(",標題:'").append(subject).append("'");
        
		try{
			//MIMEの設定
			Session     session     = Session.getDefaultInstance(this.props, null);
			MimeMessage mimeMessage = new MimeMessage(session);
			
			//Fromの設定
			InternetAddress fromAddr = new InternetAddress(corrector_.correct(from));
			fromAddr.setPersonal(fromAddr.getPersonal(), ENCODE);
			mimeMessage.setFrom(fromAddr);
			
			//----- TOの設定 -----
			if(to != null && to.length != 0 && to[0] != null){
				InternetAddress[] toAddr = new InternetAddress[to.length];
				for(int i=0; i<toAddr.length; i++){
					toAddr[i] = new InternetAddress(corrector_.correct(to[i]));
					toAddr[i].setPersonal(toAddr[i].getPersonal(), ENCODE);
				}
				mimeMessage.setRecipients(Message.RecipientType.TO, toAddr);
			}
			
			//----- CCの設定 -----
			if(cc != null && cc.length != 0 && cc[0] != null){
				InternetAddress[] ccAddr = new InternetAddress[cc.length];
				for(int i=0; i<ccAddr.length; i++){
					ccAddr[i] = new InternetAddress(corrector_.correct(cc[i]));
					ccAddr[i].setPersonal(ccAddr[i].getPersonal(), ENCODE);
				}
				mimeMessage.setRecipients(Message.RecipientType.CC, ccAddr);
			}
			
			//----- BCCの設定 -----
			if(bcc != null && bcc.length != 0 && bcc[0] != null){
				InternetAddress[] bccAddr = new InternetAddress[bcc.length];
				for(int i=0; i<bccAddr.length; i++){
					bccAddr[i] = new InternetAddress(corrector_.correct(bcc[i]));
					bccAddr[i].setPersonal(bccAddr[i].getPersonal(), ENCODE);
				}
				mimeMessage.setRecipients(Message.RecipientType.BCC, bccAddr);
			}
			
			
			//Subjectの設定
			mimeMessage.setSubject(corrector_.correct(subject), ENCODE);
			mimeMessage.setText(corrector_.correct(comment), ENCODE);
			
			//SendMessageの設定
			Transport.send(mimeMessage);
			
			log.info("【正常終了】" + details);
			
		}catch(AddressException e){
			log.info("【異常終了】" + details + ",原因:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("Address Exception：" + e);
			throw new Exception(e.getMessage());
		}
		catch(MessagingException e){
            log.info("【異常終了】" + details + ",原因:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("Messaging Exception：" + e);
			throw new Exception(e.getMessage());
		}
		catch(UnsupportedEncodingException e){
            log.info("【異常終了】" + details + ",原因:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("UnsupportedEncodingException：" + e);
			throw new Exception(e.getMessage());
		}
		catch(Exception e){
            log.info("【異常終了】" + details + ",原因:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("Exception：" + e);
			throw new Exception(e.getMessage());
		}
		
	}
	
	
}
