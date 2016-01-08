/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchListAction.java
 *    Description : ��o���ވꗗ�\���A�N�V�����N���X
 *
 *    Author      : Dis.lwj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.lwj        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��o���ވꗗ�A�N�V�����N���X�B
 * ��o���ވꗗ��ʂ�\������B
 */
public class SearchListAction extends BaseAction{
    
    /** (��ID:03)�ȍ~�̏�ID */
    private static String[] JIGYO_IDS = new String[]{
        StatusCode.STATUS_GAKUSIN_SHORITYU ,              //�w�U��t��
        StatusCode.STATUS_GAKUSIN_JYURI ,                 //�w�U��
        StatusCode.STATUS_GAKUSIN_FUJYURI,                //�w�U�s��
        StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,      //�R��������U�菈����
        StatusCode.STATUS_WARIFURI_CHECK_KANRYO,          //����U��`�F�b�N����
        StatusCode.STATUS_1st_SHINSATYU,                  //�ꎟ�R����
        StatusCode.STATUS_1st_SHINSA_KANRYO,              //�ꎟ�R���F����
        StatusCode.STATUS_2nd_SHINSA_KANRYO               //�񎟐R������
    };
    
    /** 04(�w�U��t��),06(�w�U��), 07(�w�U�s��) */
    private static String[] JIGYO_IDS1 = new String[]{
        StatusCode.STATUS_GAKUSIN_SHORITYU,               //�w�U��t��
        StatusCode.STATUS_GAKUSIN_JYURI ,                 //�w�U��
        StatusCode.STATUS_GAKUSIN_FUJYURI,                //�w�U�s��
    };
    
    /** 02(�\�������m�F),03(�����@�֎�t��), 05(�����@�֋p��) */
    private static String[] JIGYO_IDS2 = new String[]{
        StatusCode.STATUS_SHINSEISHO_MIKAKUNIN,           //�\�������m�F
        StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,        //�����@�֎�t��
        StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,            //�����@�֋p��   
    };
    
    /** ��ID��06(�w�U��)�̂��̂�\�� */
    private static String[] JOKYO_ID_JYURIZUMI = new String[]{
            StatusCode.STATUS_GAKUSIN_JYURI,              //�w�U��
    };

    /** ��ID��04(�w�U��t���F�󗝑O)�̂��̂�\�� */
    private static String[] JOKYO_ID_JYURIMAE = new String[]{
            StatusCode.STATUS_GAKUSIN_SHORITYU,           //�w�U��t��
    };
   
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
        
        //-------�� VO�Ƀf�[�^���Z�b�g����B
        TeishutsuShoruiSearchInfo searchInfo = new TeishutsuShoruiSearchInfo();
        //�t�H�[�����擾
        TeishutsuShoruiSearchForm searchForm = (TeishutsuShoruiSearchForm)form;

        // ���������̎���CD��ݒ�
        searchInfo.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);

        // ���������̏����R�[�h��ݒ�
        if(!StringUtil.isBlank(searchForm.getShozokuCd())){
            searchInfo.setShozokuCd(searchForm.getShozokuCd().trim());
        }

        // ���������̏����@�֖���ݒ�
        if(!StringUtil.isBlank(searchForm.getShozokuName())){
            searchInfo.setShozokuName(searchForm.getShozokuName().trim());
        }

        // ���������̎󗝏󋵂�ݒ�
        String jokyoKubun = searchForm.getJuriJokyo();
        
        searchInfo.setJokyoKubun(jokyoKubun);
        searchInfo.setSearchJokyoId(JIGYO_IDS);
        searchInfo.setDelFlg("0");
        
        if(jokyoKubun == null || jokyoKubun.equals("") ||jokyoKubun.equals("0")){
            searchInfo.setSearchJokyoId1(JIGYO_IDS1);
            searchInfo.setSearchJokyoId2(JIGYO_IDS2);
        }
        else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_SHORITYU)){
            //��ID��04(�w�U��t��)�̂��̂�\�� 
            searchInfo.setSearchJokyoId1(JOKYO_ID_JYURIMAE);
            searchInfo.setSearchJokyoId2(null);
        }
        else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_JYURI )){
            //��ID��06(�w�U��)�̂��̂�\��
            searchInfo.setSearchJokyoId1(JOKYO_ID_JYURIZUMI);
            searchInfo.setSearchJokyoId2(null);
        }
        else if(jokyoKubun.equals("03")){           
            searchInfo.setSearchJokyoId2(JIGYO_IDS2);
            searchInfo.setSearchJokyoId1(null);
        }
        
        //�y�[�W����
        searchInfo.setStartPosition(searchForm.getStartPosition());
        
        List result = null;
        try{
            //�������s
            result = 
                getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).selectTeishutuShoruiList(
                    container.getUserInfo(),
                    searchInfo);
        }catch(NoDataFoundException e){
            errors.add("�Y���f�[�^�͂���܂���B",new ActionError("errors.5002"));
        }

        //�����������R���e�i�Ɋi�[
        container.setTeishutsuShoruiSearchInfo(searchInfo);

        //�������ʂ��Z�b�g����B
        request.setAttribute(IConstants.RESULT_INFO, result);
        
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        return forwardSuccess(mapping);
    }
}