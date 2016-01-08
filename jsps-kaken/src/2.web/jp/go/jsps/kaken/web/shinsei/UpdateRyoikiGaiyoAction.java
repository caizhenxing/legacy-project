/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RyoikiGaiyoApplicationAction.java
 *    Description : ������C�����(�̈�v�揑�T�v�C��)
 *
 *    Author      : �c
 *    Date        : 2006/06/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/28    v1.1        �c�c�@�@�@�@�@�@�@�@�@�@ �@ 
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �̈�v�揑�C���O�A�N�V�����N���X�B
 * �t�H�[���ɐ\�����C����ʂɕK�v�ȃf�[�^���Z�b�g����B
 * �̈�v�揑�C����ʂ�\������B
 */

public class UpdateRyoikiGaiyoAction extends BaseAction{

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
        
        //-----�̈�T�v�t�H�[���̎擾
        RyoikiGaiyoForm ryoikiGaiyoForm = getInputNewForm(container, (RyoikiGaiyoForm)form);
       
        //�����̈�ŏI�N�x�O�N�x�̉���L�����X�g
        ryoikiGaiyoForm.setZenNendoOuboFlgList(LabelValueManager.getBuntankinList());
        
        ryoikiGaiyoForm.setRyoikiSystemNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getRyoikiSystemNo());
        ryoikiGaiyoForm.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());
        
        //-----�̈�T�v�t�H�[���ɃZ�b�g����B
        updateFormBean(mapping, request, ryoikiGaiyoForm);

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        //�g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);
        
        return forwardSuccess(mapping);
    }
    
    /**
     * �̈�v�揑���͗p�t�H�[���擾���\�b�h�B
     * @param container �\���ҏ��
     * @param rgForm �̈�v�揑���̓t�H�[��
     * @return �V�K�p�̈�v�揑���̓t�H�[��
     * @throws ApplicationException
     */
    private RyoikiGaiyoForm getInputNewForm(UserContainer container, RyoikiGaiyoForm rgForm)
        throws ApplicationException {
        
        if(!StringUtil.isBlank(rgForm.getJigyoId()) && !StringUtil.isBlank(rgForm.getRyoikiSystemNo())){
            rgForm.getRyoikikeikakushoInfo().setJigyoId(rgForm.getJigyoId());
            rgForm.getRyoikikeikakushoInfo().setRyoikiSystemNo(rgForm.getRyoikiSystemNo());
        }
        
        //����ID���擾����
        String jigyoId = rgForm.getRyoikikeikakushoInfo().getJigyoId();
        String ryoikiSystemNo = rgForm.getRyoikikeikakushoInfo().getRyoikiSystemNo();
        
        //���ƊǗ���L�[�I�u�W�F�N�g�̐���
        JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(jigyoId);
        
        //DB��背�R�[�h���擾
        ISystemServise servise = getSystemServise(
                        IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        Map resultMap = servise.selectRyoikiKeikakushoInfoForInput(container.getUserInfo(), jigyoKanriPk, ryoikiSystemNo);
            
        //�̈�v�揑�i�T�v�j���A�e�v���_�E�����j���[���X�g�擾
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = (RyoikiKeikakushoInfo)resultMap.get(ISystemServise.KEY_RYOIKIKEIKAKUSHO_INFO);
        //�R����]���僊�X�g
        List kiboubumonList = (List)resultMap.get(ISystemServise.KEY_KIBOUBUMON_LIST);
        //���O�������X�g
        List jizenchousaList = (List)resultMap.get(ISystemServise.KEY_JIZENCHOUSA_LIST);
        //�����̕K�v�����X�g
        List kenkyuHitsuyouseiList = (List)resultMap.get(ISystemServise.KEY_KENKYUHITSUYOUSEI_LIST);
        //15���ރ��X�g
        List kanrenbunyaBunruiList = (List)resultMap.get(ISystemServise.KEY_KANRENBUNYABUNRUI_LIST);
        //�E�����X�g
        List shokushuList = (List)resultMap.get(ISystemServise.KEY_SHOKUSHU_LIST);
        //�Q�l�����t�@�C���I�����X�g
        List tenpuFileList = (List)resultMap.get(ISystemServise.KEY_RYOIKITENPUFLAG_LIST);
        
        //�̈�v�揑�i�T�v�j�����̓t�H�[���̐���
        RyoikiGaiyoForm ryoikiGaiyoForm = new RyoikiGaiyoForm();
        
        ShinseishaInfo shinseishaInfo = container.getUserInfo().getShinseishaInfo();
        //�̈�v�揑�i�T�v�j���̕���CD�Ɖ���ҏ��̕���CD���قȂ�ꍇ
        if(!shinseishaInfo.getBukyokuCd().equals(ryoikikeikakushoInfo.getBukyokuCd())){
            //����CD�A���ǖ��̓Z�b�V�����̉���ҏ�񂩂�ݒ肷��
            ryoikikeikakushoInfo.setBukyokuCd(shinseishaInfo.getBukyokuCd());
            ryoikikeikakushoInfo.setBukyokuName(shinseishaInfo.getBukyokuName());
        }
        
        //�̈�v�揑�i�T�v�j���̐E��CD�Ɖ���ҏ��̐E��CD���قȂ�ꍇ
        if(!shinseishaInfo.getShokushuCd().equals(ryoikikeikakushoInfo.getShokushuCd())){
            //�E��CD�A�E���i�a���j�̓Z�b�V�����̉���ҏ�񂩂�ݒ肷��
            ryoikikeikakushoInfo.setShokushuCd(shinseishaInfo.getShokushuCd());
            ryoikikeikakushoInfo.setShokushuNameKanji(shinseishaInfo.getShokushuNameKanji());
        }
        
        ryoikiGaiyoForm.setRyoikikeikakushoInfo(ryoikikeikakushoInfo);
        ryoikiGaiyoForm.setKiboubumonList(kiboubumonList);
        ryoikiGaiyoForm.setJizenchousaList(jizenchousaList);
        ryoikiGaiyoForm.setKenkyuHitsuyouseiList(kenkyuHitsuyouseiList);
        ryoikiGaiyoForm.setKanrenbunyaBunruiList(kanrenbunyaBunruiList);
        ryoikiGaiyoForm.setShokushuList(shokushuList);
        ryoikiGaiyoForm.setTenpuFileList(tenpuFileList);        
        
        //�����̕K�v���̐ݒ�
        List kenkyuHitsuyouseiValues = new ArrayList();
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei1())){
            kenkyuHitsuyouseiValues.add("1");
        } 
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei2())){
            kenkyuHitsuyouseiValues.add("2");
        }
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei3())){
            kenkyuHitsuyouseiValues.add("3");
        }
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei4())){
            kenkyuHitsuyouseiValues.add("4");
        }
        if ("1".equals(ryoikikeikakushoInfo.getKenkyuHitsuyousei5())){
            kenkyuHitsuyouseiValues.add("5");
        }
        ryoikiGaiyoForm.setValuesList(kenkyuHitsuyouseiValues);

        return ryoikiGaiyoForm;
    }
}
