/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KariBangoKyakkaCheckAction.java
 *    Description : ���̈�ԍ����s�p���m�F�O�A�N�V�����N���X
 *
 *    Author      : liuyi
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *   2006/06/14      V1.0        DIS.liuyi        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * ���̈�ԍ����s�p���m�F�O�A�N�V�����N���X�B
 * �p���m�F�Ώۉ��̈�����擾�B�Z�b�V�����ɓo�^����B 
 * ���̈�ԍ����s�p���m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: KariBangoKyakkaCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class KariBangoKyakkaCheckAction extends BaseAction{

    /**
     * Action�N���X�̎�v�ȋ@�\����������B
     * �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //------�p���V�X�e���ԍ��̎擾
        TeisyutuForm teisyutuForm = (TeisyutuForm)form;

        //------�p���ΏۃV�X�e���ԍ��̎擾
        RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();

        //------�L�[���  
        ryoikiPk.setRyoikiSystemNo(teisyutuForm.getRyoikiSystemNo());
        
        //------�L�[�������ɐ\���f�[�^�擾
        RyoikiKeikakushoInfo ryoikiInfo = getSystemServise(
                IServiceName.TEISHUTU_MAINTENANCE_SERVICE).selectRyoikiInfo(
                container.getUserInfo(), ryoikiPk);
        
        //------�p���Ώۏ������N�G�X�g�����ɃZ�b�g
        container.setRyoikikeikakushoInfo(ryoikiInfo);
        
        //-----��ʑJ�ځi��^�����j-----
        return forwardSuccess(mapping);
    }
}