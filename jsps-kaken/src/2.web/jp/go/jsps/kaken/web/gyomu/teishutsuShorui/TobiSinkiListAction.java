/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : TobiSinkiListAction
 *    Description : ��єԍ����X�g��ʃA�N�V�����N���X
 *
 *    Author      : Dis.zhangt
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.zhangt       �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��єԍ����X�g�A�N�V���� ID RCSfile="$RCSfile: TobiSinkiListAction.java,v $" Revision="$Revision: 1.1 $" Date="$Date: 2007/06/28 02:06:55 $"
 */
public class TobiSinkiListAction extends BaseAction {

    /**
     * Action�N���X�̎�v�ȋ@�\����������B �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        String[] jokyoIds = new String[] { StatusCode.STATUS_SAKUSEITHU, // �쐬��:01
                StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, // �\�������m�F:02
                StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, // �����@�֋p��:05
        };
        // ���������̎擾
        TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo = container
                .getTeishutsuShoruiSearchInfo();
        // 2006/08/01 zhangt add start
        List jigyoIdResult = null;
        try{
            //�������s
            jigyoIdResult = 
                getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).selectTeishutuShoruiList(
                    container.getUserInfo(),
                    teishutsuShoruiSearchInfo);
        }catch(NoDataFoundException e){
            errors.add("�Y���f�[�^�͂���܂���B",new ActionError("errors.5002"));
        }
        String[] jigyoId = new String[jigyoIdResult.size()];
        for(int i=0;i<jigyoIdResult.size();i++){
            HashMap jigyoIdMap=(HashMap)jigyoIdResult.get(i);
            String allJigyoId = (String)jigyoIdMap.get("JIGYO_ID");
            jigyoId[i] = allJigyoId;
            teishutsuShoruiSearchInfo.setJigyoId(jigyoId);
        }
        // 2006/08/01 zhangt add end
        teishutsuShoruiSearchInfo.setRyoikiJokyoId(jokyoIds);
        teishutsuShoruiSearchInfo.setDelFlg("1");
        teishutsuShoruiSearchInfo.setSearchJokyoId1(null);
        teishutsuShoruiSearchInfo.setSearchJokyoId2(null);
        // �T�[�o�T�[�r�X�̌Ăяo���i��єԍ����X�g�f�[�^�擾�j
        List result = null;
        try {
            result = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                    .selectTeisyutusyoTobiSinkiList(container.getUserInfo(),
                            teishutsuShoruiSearchInfo);
        }
        catch (NoDataFoundException e) {
            errors.add("��єԍ����X�g�f�[�^", new ActionError("errors.5002"));
        }

        // �������ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, result);

        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}