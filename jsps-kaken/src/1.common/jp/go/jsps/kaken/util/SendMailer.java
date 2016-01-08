/*======================================================================*/
/*    SYSTEM      :                                                     */
/*    Source name : SendMailer.java                                     */
/*    Description : ���[�����M�N���X                                     */
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
 * ���[�����M�N���X<!--.--><br>
*************************************************************************/
public class SendMailer{
	
	/** SMTP�T�[�o�v���p�e�B */
	private Properties props = null;
	
	/** �����G���R�[�h */
	private static final String ENCODE = "iso-2022-jp";
	
    /** ���[�����M�X�e�[�^�X���O */
    protected static Log log = LogFactory.getLog("mail");

	/** �����R�[�h�}�b�s���O�␳�N���X */
	private static UnicodeCorrector  corrector_;
	
	
	/******************************************************
	 * static initializer<!--.--><br>
	 * �����R�[�h�}�b�s���O�␳�C���X�^���X�𐶐�����B
	******************************************************/
	static {
		try {
			corrector_ = UnicodeCorrector.getInstance(ENCODE);
		} catch (UnsupportedEncodingException e) {
			log.warn("�y�x���z�����R�[�h�}�b�s���O�␳���s�Ȃ��܂���B����:'" + e.getMessage() + "'");
			e.printStackTrace();
			corrector_ = new NullObjectCorrector();	//NullObject
		}
	}
	
	
	
	/******************************************************
	 * �R���X�g���N�^<!--.--><br>
	 * @param  svAddr                    ���[���T�[�o�A�h���X
	 * @throws IllegalArgumentException  ������null�̏ꍇ
	******************************************************/
	public SendMailer(String svAddr) throws IllegalArgumentException {
		//SMTP�T�[�o�A�h���X�̐ݒ�
		this.props = System.getProperties();
		props.put("mail.smtp.host",svAddr);
	}
	
	
	
	/******************************************************
	 * ���[�����M���\�b�h<!--.--><br>
	 * CC���𑗐M���Ȃ��ꍇ�� null ���Z�b�g����B<br>
	 * @param   from       ���o�l
	 * @param   to         ����
	 * @param   cc         CC���o�l
	 * @param   bcc        BCC���o�l
	 * @param   subject    �W��
	 * @param   comment    �R�����g�i�{���j
	 * @throws  Exception  ��O�����������ꍇ
	******************************************************/
	public void sendMail(String from,
						 String to,
						 String cc,
						 String bcc,
						 String subject,
						 String comment ) throws Exception
	{
		//�P�v�f�̔z��𐶐����ă��\�b�h���Ăяo��
		this.sendMail(from,
					  new String[]{ to },
					  new String[]{ cc },
					  new String[]{ bcc },
					  subject,
					  comment);
	}
	
	
	
	/******************************************************
	 * ���[�����M���\�b�h<!--.--><br>
	 * �����l�̍��o�l�Ƀ��[���𑗐M����B
	 * CC���𑗐M���Ȃ��ꍇ�� null ���Z�b�g����B<br>
	 * @param   from       ���o�l
	 * @param   to         ����
	 * @param   cc         CC���o�l
	 * @param   bcc        BCC���o�l
	 * @param   subject    �W��
	 * @param   comment    �R�����g�i�{���j
	 * @throws  Exception  ��O�����������ꍇ
	******************************************************/
	public void sendMail(String   from,
						 String[] to,
						 String[] cc,
						 String[] bcc,
						 String   subject,
						 String   comment ) throws Exception
	{
        StringBuffer details = new StringBuffer();
        details.append(",����:'").append(StringUtils.join(to,",")).append("'");
        details.append(",�W��:'").append(subject).append("'");
        
		try{
			//MIME�̐ݒ�
			Session     session     = Session.getDefaultInstance(this.props, null);
			MimeMessage mimeMessage = new MimeMessage(session);
			
			//From�̐ݒ�
			InternetAddress fromAddr = new InternetAddress(corrector_.correct(from));
			fromAddr.setPersonal(fromAddr.getPersonal(), ENCODE);
			mimeMessage.setFrom(fromAddr);
			
			//----- TO�̐ݒ� -----
			if(to != null && to.length != 0 && to[0] != null){
				InternetAddress[] toAddr = new InternetAddress[to.length];
				for(int i=0; i<toAddr.length; i++){
					toAddr[i] = new InternetAddress(corrector_.correct(to[i]));
					toAddr[i].setPersonal(toAddr[i].getPersonal(), ENCODE);
				}
				mimeMessage.setRecipients(Message.RecipientType.TO, toAddr);
			}
			
			//----- CC�̐ݒ� -----
			if(cc != null && cc.length != 0 && cc[0] != null){
				InternetAddress[] ccAddr = new InternetAddress[cc.length];
				for(int i=0; i<ccAddr.length; i++){
					ccAddr[i] = new InternetAddress(corrector_.correct(cc[i]));
					ccAddr[i].setPersonal(ccAddr[i].getPersonal(), ENCODE);
				}
				mimeMessage.setRecipients(Message.RecipientType.CC, ccAddr);
			}
			
			//----- BCC�̐ݒ� -----
			if(bcc != null && bcc.length != 0 && bcc[0] != null){
				InternetAddress[] bccAddr = new InternetAddress[bcc.length];
				for(int i=0; i<bccAddr.length; i++){
					bccAddr[i] = new InternetAddress(corrector_.correct(bcc[i]));
					bccAddr[i].setPersonal(bccAddr[i].getPersonal(), ENCODE);
				}
				mimeMessage.setRecipients(Message.RecipientType.BCC, bccAddr);
			}
			
			
			//Subject�̐ݒ�
			mimeMessage.setSubject(corrector_.correct(subject), ENCODE);
			mimeMessage.setText(corrector_.correct(comment), ENCODE);
			
			//SendMessage�̐ݒ�
			Transport.send(mimeMessage);
			
			log.info("�y����I���z" + details);
			
		}catch(AddressException e){
			log.info("�y�ُ�I���z" + details + ",����:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("Address Exception�F" + e);
			throw new Exception(e.getMessage());
		}
		catch(MessagingException e){
            log.info("�y�ُ�I���z" + details + ",����:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("Messaging Exception�F" + e);
			throw new Exception(e.getMessage());
		}
		catch(UnsupportedEncodingException e){
            log.info("�y�ُ�I���z" + details + ",����:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("UnsupportedEncodingException�F" + e);
			throw new Exception(e.getMessage());
		}
		catch(Exception e){
            log.info("�y�ُ�I���z" + details + ",����:'" + e.getMessage() + "'");
			e.printStackTrace();
			System.out.println("Exception�F" + e);
			throw new Exception(e.getMessage());
		}
		
	}
	
	
}
