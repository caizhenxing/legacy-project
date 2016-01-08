/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriSaveAction.java
 *    Description : �ꊇ�󗝏����A�N�V����
 *
 *    Author      : yoshikawa_h
 *    Date        : 2005/04/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/21    V1.0                       �V�K�쐬
 *    2005/06/02    V1.1        DIS.dyh  �@�@           �C���i�󗝓o�^�Ώۉ�����ꗗ��ʃ��W�I��ǉ����邩��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ꊇ�󗝏����A�N�V����
 * @author yoshikawa_h
 */
public class IkkatsuJuriSaveAction extends BaseAction {
	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
        
        if (isCancelled(request)) {
//         removeFormBean(mapping,request);
           return forwardCancel(mapping);
        }

//update start dyh 2006/06/02
//        //------���Ə��ێ��N���X
//        JigyoInfo jigyoInfo = new JigyoInfo();
//        
//        //-------�� VO�Ƀf�[�^���Z�b�g����B
//        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
//
//        //-----�ꊇ�󗝓o�^�Ώ�
//        //�������S�����鎖�ƃR�[�h�̂�
//        if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
//            GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
//            searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
//        }
//        
//        //�敪����ՈȊO
//        ArrayList array = new ArrayList();
//        array.add("1");
//        array.add("2");
//        array.add("3");
//        searchInfo.setJigyoKubun(array);
//        
//        //�X�e�[�^�X���󗝑O
//        CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//        String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//                                                         StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
//        statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);                     //�u�w�U�������v:04
//        searchInfo.setStatusSearchInfo(statusInfo);
//
//        ISystemServise servise = getSystemServise(
//                IServiceName.SHINSEI_MAINTENANCE_SERVICE);
//        
//        Page juriTaisho = null;
//        try {
//            juriTaisho =
//                servise.searchApplication(container.getUserInfo(),searchInfo);
//        }
//        catch(ApplicationException e) {
//            //------�Y���S���҂Ȃ����ʏ킠�肦�Ȃ��̂ŋ�\��
//        }
//        
//        if(juriTaisho == null){
//            return forwardFailure(mapping);
//        }
//        //------�V�X�e����t�ԍ���pk�z��
//        ArrayList pks = new ArrayList();
//        
//        //------�Y���������A�J��Ԃ�
//        for(int i = 0; i < juriTaisho.getSize(); i++){
//            
//            //------�e�\�������擾
//            HashMap shinseishaDataMap = (HashMap)juriTaisho.getList().get(i);
//            
//            //�V�X�e����t�ԍ���z��Ɋi�[
//            pks.add(shinseishaDataMap.get("SYSTEM_NO"));
//        }
//
//        //�ꊇ��
//        List lstErrors = new ArrayList();
//        lstErrors = servise.registShinseiJuriAll(container.getUserInfo(),pks);

		ISystemServise servise = getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE);

        JuriListForm listForm = (JuriListForm)form;
        List pks = new ArrayList();
        for (int i = 0; i < listForm.getSystemNos().length; i++) {
            if (!StringUtil.isBlank(listForm.getSystemNos()[i]) ) {
                pks.add(listForm.getSystemNos()[i]);
            }
        }
		//�ꊇ��
		List lstErrors = new ArrayList();
		lstErrors = servise.registShinseiJuriAll(container.getUserInfo(), pks);
//update end dyh 2006/06/02
		for(int count=0; count<lstErrors.size();count++){
			ActionError error = new ActionError("errors.5051", lstErrors.get(count).toString());
			errors.add("�ꊇ�󗝂ŃG���[���������܂����B", error);
		}

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		//------�g�[�N���̍폜
		resetToken(request);
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);
        //------�Z�b�V�������V�K�o�^���̍폜
        container.setShinseishaInfo(null);

		return forwardSuccess(mapping);
	}
}