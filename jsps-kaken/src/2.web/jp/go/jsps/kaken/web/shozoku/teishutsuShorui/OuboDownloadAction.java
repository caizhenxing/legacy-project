/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : OuboDownloadAction.java
 *    Description : ���发�ނ̒�o���o��(����̈挤���i�V�K�̈�j)�A�N�V����
 *
 *    Author      : DIS.liuYi
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.liuYi      �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���发�ނ̒�o���o��(����̈挤���i�V�K�̈�j)�A�N�V����
 * ID RCSfile="$RCSfile: OuboDownloadAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class OuboDownloadAction extends BaseAction {

    
    /** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
    private static String[] JIGYO_IDS = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,          //�w�U��t��
            StatusCode.STATUS_GAKUSIN_JYURI,             //�w�U��
            StatusCode.STATUS_GAKUSIN_FUJYURI,           //�w�U�s��
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, //�R��������U�菈����
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,     //����U��`�F�b�N����
            StatusCode.STATUS_1st_SHINSATYU,             //�ꎟ�R����
            StatusCode.STATUS_1st_SHINSA_KANRYO,         //�ꎟ�R���F����
            StatusCode.STATUS_2nd_SHINSA_KANRYO,         //�񎟐R������
            StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE     //�̈��\�ҏ��������@�֎�t��
    };

    /** �̈��ID */
    private static String[] RYOIKI_JOKYO_IDS=new String[]{
             StatusCode.STATUS_GAKUSIN_SHORITYU,         //�����@�֎�t��
             StatusCode.STATUS_GAKUSIN_JYURI,            //�����@�֋p��
             StatusCode.STATUS_GAKUSIN_FUJYURI           //�w�U�s��
    };
    
    /**
     * Action�N���X�̎�v�ȋ@�\����������B
     * �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
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

        //-------�� VO�Ƀf�[�^���Z�b�g����B
        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

        //��ID�������@�֎�t���ȍ~�̂��̂ɂ��ĕ\������悤�C��
        ryoikiInfo.setJokyoIds(JIGYO_IDS);
        ryoikiInfo.setRyoikiJokyoIds(RYOIKI_JOKYO_IDS);

        //-----�r�W�l�X���W�b�N���s-----
        FileResource fileRes =
                getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).createOuboTeishutusho(
                    container.getUserInfo(),
                    ryoikiInfo);

        //-----�t�@�C���̃_�E�����[�h
        DownloadFileUtil.downloadFile(response, fileRes);   

        return forwardSuccess(mapping);
    }
}