/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.kenkyusoshiki;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsei.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �����g�D�\�ǉ��A�N�V�����N���X�B
 * �����g�D���X�g�ɐV�K�I�u�W�F�N�g��ǉ�����B 
 * ID RCSfile="$RCSfile: AddKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/21 08:39:46 $"
 */
public class AddKenkyuSoshikiAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(AddKenkyuSoshikiAction.class);

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

		//-----�\�������̓t�H�[���̎擾
		ShinseiForm shinseiForm = (ShinseiForm)form;
		

		
		
		//-----�����g�D�Ǘ����X�g�ɐV�K�I�u�W�F�N�g��ǉ�
		ShinseiDataInfo shinseiDataInfo = shinseiForm.getShinseiDataInfo();
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();

//		2005/04/14 �C�� ��������----------
//		���R:�ǂ��炩���ő吔�ɒB���Ă��܂��ƁA�ő吔�łȂ����܂Œǉ��ł��Ȃ��Ȃ��Ă��܂���
		
		//���X�g�̍ő吔�𒴂��Ă����ꍇ
		//2005/03/30 �C�� ---------------------------------------------��������
		////���R �����g�D�\�Ɍ������͎҂��ǉ����ꂽ����
		//if(kenkyushaList.size() >= 99){
		//	String msg = "�����g�D���X�g�̍ő吔�𒴂��܂����B";
		//	errors.add("addKenkyuSoshiki", new ActionError("errors.5016"));
		//}else{
		//	//�V�K�I�u�W�F�N�g
		//	KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
		//	kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
		//	kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());
		//
		//	//���X�g�ǉ�
		//	kenkyushaList.add(kenkyushaInfo);
		//}


//		//���R �����g�D�\�Ɍ������͎҂��ǉ����ꂽ����
//		if(shinseiDataInfo.getKenkyuNinzuInt() >= 99 || shinseiDataInfo.getKyoryokushaNinzuInt() >= 10){
//			String msg = "�����g�D���X�g�̍ő吔�𒴂��܂����B";
//			errors.add("addKenkyuSoshiki", new ActionError("errors.5016"));
//		}else{
//			//�V�K�I�u�W�F�N�g
//			KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
//			kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
//			kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());
//
//			kenkyushaInfo.setBuntanFlag(shinseiForm.getAddBuntanFlg());
//
//			if("2".equals(shinseiForm.getAddBuntanFlg())){
//				//���S��
//				shinseiDataInfo.setKenkyuNinzuInt(shinseiDataInfo.getKenkyuNinzuInt()+1);
//			} else {
//				//���͎�
//				shinseiDataInfo.setKyoryokushaNinzuInt(shinseiDataInfo.getKyoryokushaNinzuInt()+1);
//			}
//			
//			//���X�g�ǉ�
//			kenkyushaList.add(kenkyushaInfo);
//		}
		//2005/03/29 �ǉ� ---------------------------------------------�����܂�
		

		//TODO 2005.09.29 iso ���S�t���O�ɋ󂪓���Ή��ňꎞ�ǉ�
		if(shinseiForm.getAddBuntanFlg() == null || "".equals(shinseiForm.getAddBuntanFlg())) {
			shinseiForm.setAddBuntanFlg("2");
			log.info("�y�����z���S�t���O��FAddKenkyuSoshikiAction");
		}
//UPDATE�@START 2007/07/10 BIS �����_ 		//��\�ҕ��S�ҕʂɁ@add  �i4�F�A�g�����ҁj
		//if("2".equals(shinseiForm.getAddBuntanFlg())){
		if("2".equals(shinseiForm.getAddBuntanFlg())
				||"4".equals(shinseiForm.getAddBuntanFlg())
				||"5".equals(shinseiForm.getAddBuntanFlg())){
//UPDATE�@END�@ 2007/07/10 BIS �����_ 			
			

			//������\�ҋy�ь������S�҂�99�l�̏ꍇ�͒ǉ��ł��Ȃ�
			if(shinseiDataInfo.getKenkyuNinzuInt() >= 99){
//UPDATE�@START 2007/07/10 BIS �����_ 		//��\�ҕ��S�ҕʂɁ@add  �i4�F�A�g�����ҁj				
				//errors.add("addKenkyuSoshiki", new ActionError("errors.5016","������\�ҋy�ь������S��","99"));
				errors.add("addKenkyuSoshiki", new ActionError("errors.5016","������\�ҁA�������S�ҋy�јA�g������","99"));
//UPDATE�@END�@ 2007/07/10 BIS �����_ 				
			}else{
				//���S��
				shinseiDataInfo.setKenkyuNinzuInt(shinseiDataInfo.getKenkyuNinzuInt()+1);
				
				KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
				kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
				kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());

				kenkyushaInfo.setBuntanFlag(shinseiForm.getAddBuntanFlg());
				
				//���X�g�ǉ�
				kenkyushaList.add(kenkyushaInfo);
			}
		}else{
			//�������͎҂�10�l�̏ꍇ�͒ǉ��ł��Ȃ�
			if(shinseiDataInfo.getKyoryokushaNinzuInt() >= 10){
				errors.add("addKenkyuSoshiki", new ActionError("errors.5016","�������͎�","10"));
			}else{
				//���͎�
				shinseiDataInfo.setKyoryokushaNinzuInt(shinseiDataInfo.getKyoryokushaNinzuInt()+1);
				
				KenkyuSoshikiKenkyushaInfo kenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();
				kenkyushaInfo.setSystemNo(shinseiDataInfo.getSystemNo());
				kenkyushaInfo.setJigyoID(shinseiDataInfo.getJigyoId());

				kenkyushaInfo.setBuntanFlag(shinseiForm.getAddBuntanFlg());
				
				//���X�g�ǉ�
				kenkyushaList.add(kenkyushaInfo);
			}
		}

		//2005/04/14 �C�� �����܂�----------

		
		//-----�\�������̓t�H�[���ɃZ�b�g����B
		
//		ADD�@START 2007/07/11 BIS �����_   (�����g�D�\�̒��@�F�@�\�[�g���@�@��\�ҕ��S�ҕʁ@�����A�A�V�[�P���X�ԍ��i��ʂ���̓o�^���j�@����)
		List kenkyusoshikiIfo = shinseiForm.getShinseiDataInfo().getKenkyuSoshikiInfoList();
		if(kenkyusoshikiIfo != null && kenkyusoshikiIfo.size() > 0){
			List outList = new ArrayList();
			List daihyoList = new ArrayList();    //������\�� : 1 
			List bunfanList = new ArrayList();    //�@���S��   : 2
			List kyouryokuList = new ArrayList(); //�������͎� : 3
			List renkeiList = new ArrayList();    //�A�g������ : 4
			List nullList = new ArrayList();      //    �� �@�@: 5
			
			for(int i = 0 ; i <kenkyusoshikiIfo.size() ; i ++ ){
				KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo = (KenkyuSoshikiKenkyushaInfo) kenkyusoshikiIfo.get(i);
				String buntan = kenkyuSoshikiKenkyushaInfo.getBuntanFlag();
				if("1".equals(buntan)){
					daihyoList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("2".equals(buntan)){
					bunfanList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("3".equals(buntan)){
					kyouryokuList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("4".equals(buntan)){
					renkeiList.add(kenkyuSoshikiKenkyushaInfo);
				}else if("5".equals(buntan)){
					nullList.add(kenkyuSoshikiKenkyushaInfo);
				}
				
			}
			if(daihyoList.size()>0){
				outList.addAll(daihyoList)	;
			}
			if(bunfanList.size()>0){
				outList.addAll(bunfanList)	;
			}
			if(kyouryokuList.size()>0){
				outList.addAll(kyouryokuList)	;
			}
			if(renkeiList.size()>0){
				outList.addAll(renkeiList)	;	
			}
			if(nullList.size()>0){
				outList.addAll(nullList)	;				}
			shinseiForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(outList);			
		}	
//ADD�@END�@ 2007/07/11 BIS �����_		
		
		
		
		updateFormBean(mapping, request, shinseiForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
