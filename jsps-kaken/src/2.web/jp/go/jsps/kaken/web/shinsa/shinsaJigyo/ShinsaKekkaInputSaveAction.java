/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * �R�����ʓ��͏��l�I�u�W�F�N�g���X�V����B
 * �t�H�[�����A�X�V�����N���A����B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInputSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaInputSaveAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		//2005.10.14 �m�F��ʂƊ�����ʂ𓝍��̂��߁A�t�H�[������f�[�^���擾
		//------�Z�b�V�������X�V���̎擾
//		ShinsaKekkaInputInfo addInfo = container.getShinsaKekkaInputInfo();

		
		//------�V�K�o�^�t�H�[�����̎擾
		ShinsaKekkaForm addForm = (ShinsaKekkaForm) form;

		//-------�� VO�Ƀf�[�^���Z�b�g����B(��Փ�)
		ShinsaKekkaInputInfo addInfo = container.getShinsaKekkaInputInfo();									

		addInfo.setKekkaAbc(addForm.getKekkaAbc());					//�����]���iABC�j
		addInfo.setKekkaTen(addForm.getKekkaTen());					//�����]���i�_���j
		addInfo.setComment1(addForm.getComment1());					//�R�����g1
		addInfo.setComment2(addForm.getComment2());					//�R�����g2
		addInfo.setComment3(addForm.getComment3());					//�R�����g3
		addInfo.setComment4(addForm.getComment4());					//�R�����g4
		addInfo.setComment5(addForm.getComment5());					//�R�����g5
		addInfo.setComment6(addForm.getComment6());					//�R�����g6
		addInfo.setKenkyuNaiyo(addForm.getKenkyuNaiyo());			//�������e
		addInfo.setKenkyuKeikaku(addForm.getKenkyuKeikaku());		//�����v��
		addInfo.setTekisetsuKaigai(addForm.getTekisetsuKaigai());	//�K�ؐ�-�C�O
		addInfo.setTekisetsuKenkyu1(addForm.getTekisetsuKenkyu1());	//�K�ؐ�-����(1)
		addInfo.setTekisetsu(addForm.getTekisetsu());				//�K�ؐ�
		addInfo.setDato(addForm.getDato());							//�Ó���
		addInfo.setShinseisha(addForm.getShinseisha());				//������\��
		addInfo.setKenkyuBuntansha(addForm.getKenkyuBuntansha());	//�������S��
		addInfo.setHitogenomu(addForm.getHitogenomu());				//�q�g�Q�m��
		addInfo.setTokutei(addForm.getTokutei());					//������
		addInfo.setHitoEs(addForm.getHitoEs());						//�q�gES�זE
		addInfo.setKumikae(addForm.getKumikae());					//��`�q�g��������
		addInfo.setChiryo(addForm.getChiryo());						//��`�q���×Տ�����
		addInfo.setEkigaku(addForm.getEkigaku());					//�u�w����
		addInfo.setComments(addForm.getComments());					//�R�����g
		//2005.10.26 kainuma
        //2006.10.26 �k����@�d�l�ύX���A��Վ��@�폜���܂����B
		//addInfo.setRigai(addForm.getRigai());						//���Q�֌W
		addInfo.setWakates(addForm.getWakates());					//���S�̑Ó���2007/5/8
		addInfo.setJuyosei(addForm.getJuyosei());					//�w�p�I�d�v���E�Ó���
		addInfo.setDokusosei(addForm.getDokusosei());				//�Ƒn���E�v�V��
		addInfo.setHakyukoka(addForm.getHakyukoka());				//�g�y���ʁE���Ր�
		addInfo.setSuikonoryoku(addForm.getSuikonoryoku());			//���s�\�́E���̓K�ؐ�
		addInfo.setJinken(addForm.getJinken());						//�l���̕ی�E�@�ߓ��̏���
		addInfo.setBuntankin(addForm.getBuntankin());				//���S���z��
		addInfo.setOtherComment(addForm.getOtherComment());			//���̑��R�����g 
		   
		//------�v���_�E���E���W�I�{�^���̕\�����x�����Z�b�g
		if(addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
			//���Ƌ敪�u1�v��������
			//�����]���iABC�j
//			addInfo.setKekkaAbcLabel(LabelValueManager.getKekkaAbcName(addForm.getKekkaAbc()));
			addInfo.setKekkaAbcLabel(LabelValueManager.getlabelName(addForm.getKekkaAbcList(), addForm.getKekkaAbc()));
//		}else if(addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//2006/04/10 �X�V��������			
		} else if (addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) ||
				   addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) ||
                   addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)) {	
//�c�@�X�V�����܂�			
            //2006.10.26 �k����@�d�l�ύX���A��Վ��@�C����������
			//���Ƌ敪�u4�v�Ɓu6�v��������			
			//�����]���i�_���j
//			if(!StringUtil.isBlank(addForm.getKekkaTen()) && addForm.getRigai().equals(IShinsaKekkaMaintenance.RIGAI_OFF)){
            if(!StringUtil.isBlank(addForm.getKekkaTen())){
				addInfo.setKekkaTenHogaLabel(LabelValueManager.getKekkaTenHogaName(addForm.getKekkaTen()));
				addInfo.setKekkaTenLabel(LabelValueManager.getKekkaTenName(addForm.getKekkaTen()));
			}
			//�������e
//			if(addForm.getKenkyuNaiyo() != null && !addForm.getKenkyuNaiyo().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKenkyuNaiyo() != null && !addForm.getKenkyuNaiyo().equals("")){
//				addInfo.setKenkyuNaiyoLabel(LabelValueManager.getKenkyuNaiyoName(addForm.getKenkyuNaiyo()));
				addInfo.setKenkyuNaiyoLabel(LabelValueManager.getlabelName(addForm.getKenkyuNaiyoList(), addForm.getKenkyuNaiyo()));
			}
			//�����v��
//			if(addForm.getKenkyuKeikaku() != null && !addForm.getKenkyuKeikaku().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKenkyuKeikaku() != null && !addForm.getKenkyuKeikaku().equals("")){
//				addInfo.setKenkyuKeikakuLabel(LabelValueManager.getKenkyuKeikakuName(addForm.getKenkyuKeikaku()));
				addInfo.setKenkyuKeikakuLabel(LabelValueManager.getlabelName(addForm.getKenkyuKeikakuList(), addForm.getKenkyuKeikaku()));
			}
			//�K�ؐ�-�C�O
//			if(addForm.getTekisetsuKaigai() != null && !addForm.getTekisetsuKaigai().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTekisetsuKaigai() != null && !addForm.getTekisetsuKaigai().equals("")){
//				addInfo.setTekisetsuKaigaiLabel(LabelValueManager.getTekisetsuKaigaiName(addForm.getTekisetsuKaigai()));
				addInfo.setTekisetsuKaigaiLabel(LabelValueManager.getlabelName(addForm.getTekisetsuKaigaiList(), addForm.getTekisetsuKaigai()));
			}
			//�K�ؐ�-����(1)
//			if(addForm.getTekisetsuKenkyu1() != null && !addForm.getTekisetsuKenkyu1().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTekisetsuKenkyu1() != null && !addForm.getTekisetsuKenkyu1().equals("")){
//				addInfo.setTekisetsuKenkyu1Label(LabelValueManager.getTekisetsuKenkyu1Name(addForm.getTekisetsuKenkyu1()));
				addInfo.setTekisetsuKenkyu1Label(LabelValueManager.getlabelName(addForm.getTekisetsuKenkyu1List(),addForm.getTekisetsuKenkyu1()));
			}
			//�K�ؐ�
//			if(addForm.getTekisetsu() != null && !addForm.getTekisetsu().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTekisetsu() != null && !addForm.getTekisetsu().equals("")){
//				addInfo.setTekisetsuLabel(LabelValueManager.getTekisetsuName(addForm.getTekisetsu()));
				addInfo.setTekisetsuLabel(LabelValueManager.getlabelName(addForm.getTekisetsuList(), addForm.getTekisetsu()));
			}
			//�Ó���
//			if(addForm.getDato() != null && !addForm.getDato().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getDato() != null && !addForm.getDato().equals("")){
//				addInfo.setDatoLabel(LabelValueManager.getDatoName(addForm.getDato()));
				addInfo.setDatoLabel(LabelValueManager.getlabelName(addForm.getDatoList(), addForm.getDato()));
			}
			//������\��
//			if(addForm.getShinseisha() != null && !addForm.getShinseisha().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getShinseisha() != null && !addForm.getShinseisha().equals("")){
//				addInfo.setShinseishaLabel(LabelValueManager.getShinseishaName(addForm.getShinseisha()));
				addInfo.setShinseishaLabel(LabelValueManager.getlabelName(addForm.getShinseishaList(), addForm.getShinseisha()));
			}
			//�������S��
//			if(addForm.getKenkyuBuntansha() != null && !addForm.getKenkyuBuntansha().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKenkyuBuntansha() != null && !addForm.getKenkyuBuntansha().equals("")){
//				addInfo.setKenkyuBuntanshaLabel(LabelValueManager.getKenkyuBuntanshaName(addForm.getKenkyuBuntansha()));
				addInfo.setKenkyuBuntanshaLabel(LabelValueManager.getlabelName(addForm.getKenkyuBuntanshaList(), addForm.getKenkyuBuntansha()));
			}		
			//�q�g�Q�m��
//			if(addForm.getHitogenomu() != null && !addForm.getHitogenomu().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getHitogenomu() != null && !addForm.getHitogenomu().equals("")){
//				addInfo.setHitogenomuLabel(LabelValueManager.getHitogenomuName(addForm.getHitogenomu()));
				addInfo.setHitogenomuLabel(LabelValueManager.getlabelName(addForm.getHitogenomuList(), addForm.getHitogenomu()));
			}
			//������
//			if(addForm.getTokutei() != null && !addForm.getTokutei().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTokutei() != null && !addForm.getTokutei().equals("")){
//				addInfo.setTokuteiLabel(LabelValueManager.getTokuteiName(addForm.getTokutei()));
				addInfo.setTokuteiLabel(LabelValueManager.getlabelName(addForm.getTokuteiList(), addForm.getTokutei()));
			}		
			//�q�g�d�r�זE
//			if(addForm.getHitoEs() != null && !addForm.getHitoEs().equals("") && addForm.getRigai().equals("0")) {
            if(addForm.getHitoEs() != null && !addForm.getHitoEs().equals("")){
//				addInfo.setHitoEsLabel(LabelValueManager.getHitoEsName(addForm.getHitoEs()));
				addInfo.setHitoEsLabel(LabelValueManager.getlabelName(addForm.getHitoEsList(), addForm.getHitoEs()));
			}
			//��`�q�g��������
//			if(addForm.getKumikae() != null && !addForm.getKumikae().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKumikae() != null && !addForm.getKumikae().equals("")){
//				addInfo.setKumikaeLabel(LabelValueManager.getKumikaeName(addForm.getKumikae()));
				addInfo.setKumikaeLabel(LabelValueManager.getlabelName(addForm.getKumikaeList(), addForm.getKumikae()));
			}
			//��`�q���×Տ�����
//			if(addForm.getChiryo() != null && !addForm.getChiryo().equals("") && addForm.getRigai().equals("0")){	
            if(addForm.getChiryo() != null && !addForm.getChiryo().equals("")){
//				addInfo.setChiryoLabel(LabelValueManager.getChiryoName(addForm.getChiryo()));	
				addInfo.setChiryoLabel(LabelValueManager.getlabelName(addForm.getChiryoList(), addForm.getChiryo()));
			}
			//�u�w����
//			if(addForm.getEkigaku() != null && !addForm.getEkigaku().equals("") && addForm.getRigai().equals("0")){	
            if(addForm.getEkigaku() != null && !addForm.getEkigaku().equals("")){
//				addInfo.setEkigakuLabel(LabelValueManager.getEkigakuName(addForm.getEkigaku()));
				addInfo.setEkigakuLabel(LabelValueManager.getlabelName(addForm.getEkigakuList(), addForm.getEkigaku()));
			}

//			//2005.10.27 kainuma
//			//���Q�֌W
//		    if(addForm.getRigai() != null && !addForm.getRigai().equals("")){
//			//addInfo.setRigaiLabel(LabelValueManager.getRigaiName(addForm.getRigai()));
//			    addInfo.setRigaiLabel(LabelValueManager.getlabelName(addForm.getRigaiList(), addForm.getRigai()));
//		    }

            //���S�Ƃ��Ă̑Ó������x����ݒ肷�� 2007/5/8
            if (addForm.getWakates() != null && !addForm.getWakates().equals("")) {
                addInfo.setWakatesLabel(LabelValueManager.getlabelName(addForm.getJuyoseiList(), addForm.getWakates()));
            }
            
            //�w�p�I�d�v���E�Ó��� 
			//���Q�֌W������ꍇ�͓o�^���Ȃ�
//			if(addForm.getJuyosei() != null && !addForm.getJuyosei().equals("") && addForm.getRigai().equals("0")){
// 2006.10.27 �k���� format�C����������
            if (addForm.getJuyosei() != null
                    && !addForm.getJuyosei().equals("")) {
                // addInfo.setJuyoseiLabel(LabelValueManager.getJuyoseiName(addForm.getJuyosei()));
                addInfo.setJuyoseiLabel(LabelValueManager.getlabelName(addForm
                        .getJuyoseiList(), addForm.getJuyosei()));
            }
            // �Ƒn���E�v�V��
            // ���Q�֌W������ꍇ�͓o�^���Ȃ�
            // if(addForm.getDokusosei() != null &&
            // !addForm.getDokusosei().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getDokusosei() != null
                    && !addForm.getDokusosei().equals("")) {
                // addInfo.setDokusoseiLabel(LabelValueManager.getDokusoseiName(addForm.getDokusosei()));
                addInfo.setDokusoseiLabel(LabelValueManager.getlabelName(
                        addForm.getDokusoseiList(), addForm.getDokusosei()));
            }
            // �g�y���ʁE���Ր�
            // ���Q�֌W������ꍇ�͓o�^���Ȃ�
            // if(addForm.getHakyukoka() != null &&
            // !addForm.getHakyukoka().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getHakyukoka() != null
                    && !addForm.getHakyukoka().equals("")) {
                // addInfo.setHakyukokaLabel(LabelValueManager.getHakyukokaName(addForm.getHakyukoka()));
                addInfo.setHakyukokaLabel(LabelValueManager.getlabelName(
                        addForm.getHakyukokaList(), addForm.getHakyukoka())); 
            }
            // ���s�\�́E���̓K�ؐ�
            // ���Q�֌W������ꍇ�͓o�^���Ȃ�
            // if(addForm.getSuikonoryoku() != null &&
            // !addForm.getSuikonoryoku().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getSuikonoryoku() != null
                    && !addForm.getSuikonoryoku().equals("")) {
                // addInfo.setSuikonoryokuLabel(LabelValueManager.getSuikonoryokuName(addForm.getSuikonoryoku()));
                addInfo.setSuikonoryokuLabel(LabelValueManager.getlabelName(
                        addForm.getSuikonoryokuList(), addForm
                                .getSuikonoryoku()));
            }
            // �l���̕ی�E�@�ߓ��̏���
            // ���Q�֌W������ꍇ�͓o�^���Ȃ�
            // if(addForm.getJinken() != null && !addForm.getJinken().equals("")
            // && addForm.getRigai().equals("0")){
            if (addForm.getJinken() != null && !addForm.getJinken().equals("")) {
                // addInfo.setJinkenLabel(LabelValueManager.getJinkenName(addForm.getJinken()));
                addInfo.setJinkenLabel(LabelValueManager.getlabelName(addForm
                        .getJinkenList(), addForm.getJinken()));
            }
            // ���S��
            // ���Q�֌W������ꍇ�͓o�^���Ȃ�
            // if(addForm.getBuntankin() != null &&
            // !addForm.getBuntankin().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getBuntankin() != null
                    && !addForm.getBuntankin().equals("")) {
                // addInfo.setBuntankinLabel(LabelValueManager.getBuntankinhaibunName(addForm.getBuntankin()));
                addInfo.setBuntankinLabel(LabelValueManager.getlabelName(
                        addForm.getBuntankinList(), addForm.getBuntankin()));
            }
// 2006.10.27 format�C�������܂�

// 2006.10.26 �k����@�d�l�ύX���A��Վ��@�폜��������
//			//���Q�֌W������ꍇ�A�R�����ʈȊO�̓��͍��ڂ̓N���A����
//			//if(addForm.getRigai().equals("1"))
//			if("1".equals(addForm.getRigai()))	//2005/11/14
//			{		
//				//�w�p�I�d�v���E�Ó���
//				addInfo.setJuyosei(null);		 
//				addInfo.setJuyoseiLabel(null); 
//				//�����v��
//				addInfo.setKenkyuKeikaku(null);
//				addInfo.setKenkyuKeikakuLabel(null);
//				//�Ƒn���E�v�V��
//				addInfo.setDokusosei(null);
//				addInfo.setDokusoseiLabel(null);
//				//�g�y���ʁE���Ր�
//				addInfo.setHakyukoka(null);
//				addInfo.setHakyukokaLabel(null);
//				//���s�\�́E���̓K�ؐ�
//				addInfo.setSuikonoryoku(null);
//				addInfo.setSuikonoryokuLabel(null);
//				//�K�ؐ�-�C�O�A�G��
//				addInfo.setTekisetsuKaigai(null);
//				addInfo.setTekisetsuKaigaiLabel(null);
//				//�����]���i�_���j
//				//2005/11/15 ���Q�֌W����ꍇ�́u-�v���Z�b�g�Ƃ���
//				//addInfo.setKekkaTen(null);
//				addInfo.setKekkaTen("-");
//				addInfo.setKekkaTenLabel("-");
//				addInfo.setKekkaTenHogaLabel("-");
//				//�Ó���
//				addInfo.setDato(null);
//				addInfo.setDatoLabel(null);
//				//���S��
//				addInfo.setBuntankin(null);
//				addInfo.setBuntankinLabel(null);  
//				//�l��
//				addInfo.setJinken(null);
//				addInfo.setJinkenLabel(null);
//				//���̑��̃R�����g
//				addInfo.setOtherComment(null);
//			}
// 2006.10.26 �k����@�d�l�ύX���A��Վ��@�폜�����܂�  
        }

		//-------�A�b�v���[�h�t�@�C�����Z�b�g
		//---�Y�t�t�@�C��
		//���Q�֌W������ꍇ�̓t�@�C�����Z�b�g���Ȃ�
//     �@2006.10.26 �k����@�d�l�ύX���A��Վ��@�C����������       
		//if(addForm.getRigai().equals("0"))
//	    if("0".equals(addForm.getRigai()) || 
//			addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO))
		//2005/11/16 �w�n�̏ꍇ���Y�t�t�@�C�����Z�b�g
//       2006.10.27 format�C����������       
        if (addForm.getJigyoKubun().equals(
                IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)) {
            FormFile tenpuFile = addForm.getTenpuUploadFile();
            if (tenpuFile != null && tenpuFile.getFileSize() != 0) {
                FileResource tenpuFileRes = createFileResorce(tenpuFile);
                addInfo.setHyokaFileRes(tenpuFileRes);
            } else {
                addInfo.setHyokaFileRes(null);
            }
        }
//      2006.10.27 format�C�������܂�           
//     �@2006.10.26 �k����@�d�l�ύX���A��Վ��@�C�������܂�        
		//-------��

		//DB�o�^
		ISystemServise service = getSystemServise(
						IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE);
			service.regist1stShinsaKekka(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("�R�����ʓ��͏��@�o�^��� '"+ addInfo);
		}
		
//		//-----�Z�b�V�����̐R�����ʓ��͏������Z�b�g
//		container.setShinsaKekkaInputInfo(null);
		
//2006/04/10 �X�V��������	
		//-----�Z�b�V�����̐R�����ʓ��͏������Z�b�g
		container.setShinsaKekkaInputInfo(addInfo);
//�c�@�X�V�����܂�
		
		//-----�t�H�[�����폜
		removeFormBean(mapping, request);

//		������ʂɓ��͏���\�����邽�߂����ŃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,addInfo);

		//------�g�[�N���̍폜	
		resetToken(request);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

	/**
     * �Y�t�t�@�C������
	 * @param file �A�b�v���[�h�t�@�C��
	 * @return �t�@�C�����\�[�X
	 */
	private FileResource createFileResorce(FormFile file)
            throws ApplicationException {
		FileResource fileRes = null;
		try{	
			if(file != null){
				fileRes = new FileResource();
				fileRes.setPath(file.getFileName());	//�t�@�C����
				fileRes.setBinary(file.getFileData());	//�t�@�C���T�C�Y
			}
			return fileRes;
		}catch(IOException e){
			throw new ApplicationException(
				"�Y�t�t�@�C���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.7000"),
				e);
		}
	}
}
