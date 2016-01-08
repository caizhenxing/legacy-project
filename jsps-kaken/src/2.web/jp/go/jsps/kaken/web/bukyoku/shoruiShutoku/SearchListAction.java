/*
 * �쐬��: 2005/03/24
 *
 */
package jp.go.jsps.kaken.web.bukyoku.shoruiShutoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ފǗ���񌟍��A�N�V�����N���X�B
 * ���ވꗗ��ʂ�\������B
 * 
 */
public class SearchListAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
			//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shoruiKanriList.�v���p�e�B��
			//##

			//-----ActionErrors�̐錾�i��^�����j-----
			ActionErrors errors = new ActionErrors();
						
			//���������̎擾
			BaseSearchForm searchForm = (BaseSearchForm)form;
			
			//-------�� VO�Ƀf�[�^���Z�b�g����B
			ShoruiKanriSearchInfo searchInfo = new ShoruiKanriSearchInfo();
			try {
				PropertyUtils.copyProperties(searchInfo, searchForm);
			} catch (Exception e) {
				log.error(e);
				throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
			}

			
			//2005/04/30 �C�� ----------------------------------------------��������
			//���R ���ǒS���҂������@�֒S���҂̏���\������
			//�Ώہi���ǒS����)���Z�b�g
			//searchInfo.setTaishoId(ITaishoId.BUKYOKUTANTO);
			//�Ώہi�����@�֒S����)���Z�b�g
			searchInfo.setTaishoId(ITaishoId.SHOZOKUTANTO);
			//2005/04/30 �C�� ----------------------------------------------�����܂�
			
			//���X�g�̂ݕ\�������邽�߁A�y�[�W������1���ɂ���B
			searchInfo.setPageSize(0);
			searchInfo.setMaxSize(0);
			//-------��		
						
			//------�L�[�������Ɏ��ƃf�[�^�擾	
			//DB�o�^
			ISystemServise servise = getSystemServise(
							IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
			
			Page result = null;
			try{		
				result = servise.searchShoruiList(container.getUserInfo(), searchInfo);	
			}catch(NoDataFoundException e){
				//0���̃y�[�W�I�u�W�F�N�g�𐶐�
				result = Page.EMPTY_PAGE;
			}
			
			List shoruiList = new ArrayList();//���ރ��X�g
			if(result.getList() != null && result.getList().size() != 0){
				//------����ID�ʂ̃��X�g�ɂ���B	
				Iterator iterator = result.getList().iterator();
				//1���R�[�h���̃f�[�^���ڐ����肩����			
				String jigyoId = "";
				Map shoruiMap = null;//���ރ}�b�v			
				Map shoruiNameMap = null;//���ޖ��}�b�v
				List shoruiNameList = null;//���ޖ����X�g
				int count = 0;
				while(iterator.hasNext()){
					Map map = (Map)iterator.next();
					if(count <= 0){
						shoruiMap = new HashMap();	
/* 2005/03/25 �폜 ��������------------------------------------------------------------------------
 * ���R private���\�b�h��
						//���ރ}�b�v�Ɏ���ID�A���Ɩ��A�N�x�A��t���ԁi�J�n���j�A��t���ԁi�I�����j���Z�b�g
						shoruiMap.put("JIGYO_ID", map.get("JIGYO_ID"));
						shoruiMap.put("JIGYO_NAME", map.get("JIGYO_NAME"));
						shoruiMap.put("NENDO", map.get("NENDO"));
						shoruiMap.put("KAISU", map.get("KAISU"));						
						shoruiMap.put("UKETUKEKIKAN_START", map.get("UKETUKEKIKAN_START"));							
						shoruiMap.put("UKETUKEKIKAN_END", map.get("UKETUKEKIKAN_END"));
						
   �폜 �����܂�----------------------------------------------------------------------------------- */
						
/* 2005/03/25 �ǉ� ��������------------------------------------------------------------------------
 * ���R private���\�b�h�� */
						
						//���ރ}�b�v�Ɏ���ID�A���Ɩ��A�N�x�A��t���ԁi�J�n���j�A��t���ԁi�I�����j���Z�b�g
						shoruiMap = getShoruiMapFromResult(map);
						
/* �ǉ� �����܂�----------------------------------------------------------------------------------- */
						
						//���ޖ��}�b�v�ɏ��ޖ��A�V�X�e����t�ԍ����Z�b�g
						shoruiNameMap = new HashMap();					
						shoruiNameMap.put("SHORUI_NAME", map.get("SHORUI_NAME"));
						shoruiNameMap.put("SYSTEM_NO", map.get("SYSTEM_NO"));
						
						//���ޖ����X�g�ɏ��ޖ��}�b�v���Z�b�g
						shoruiNameList = new ArrayList();
						shoruiNameList.add(shoruiNameMap);
						
						//����ID�i�[�p�ɃZ�b�g
						jigyoId = (String)map.get("JIGYO_ID");
					}else{
						if(jigyoId.equals((String) map.get("JIGYO_ID"))){
							//--------����ID��������������					
							//���ޖ��}�b�v�ɏ��ޖ��A�V�X�e����t�ԍ����Z�b�g
							shoruiNameMap = new HashMap();	
							shoruiNameMap.put("SHORUI_NAME", map.get("SHORUI_NAME"));
							shoruiNameMap.put("SYSTEM_NO", map.get("SYSTEM_NO"));
							//���ޖ����X�g�ɏ��ޖ��}�b�v���Z�b�g
							shoruiNameList.add(shoruiNameMap);
						}else{
							//--------����ID���قȂ�����				
							//���ރ}�b�v�ɏ��ޖ����X�g���i�[����
							shoruiMap.put("SHORUI_NAME", shoruiNameList);
							//���ޖ����X�g��������
							shoruiNameList = new ArrayList();	
							//���ރ��X�g�ɏ��ރ}�b�v��ǉ�
							shoruiList.add(shoruiMap);
							
							shoruiMap = new HashMap();	
							
/* 2005/03/25 �폜 ��������------------------------------------------------------------------------
 * ���R private���\�b�h��
							//���ރ}�b�v�Ɏ���ID�A���Ɩ��A�N�x�A��t���ԁi�J�n���j�A��t���ԁi�I�����j���Z�b�g
							shoruiMap.put("JIGYO_ID", map.get("JIGYO_ID"));
							shoruiMap.put("JIGYO_NAME", map.get("JIGYO_NAME"));
							shoruiMap.put("NENDO", map.get("NENDO"));
							shoruiMap.put("KAISU", map.get("KAISU"));				
							shoruiMap.put("UKETUKEKIKAN_START", map.get("UKETUKEKIKAN_START"));							
							shoruiMap.put("UKETUKEKIKAN_END", map.get("UKETUKEKIKAN_END"));
   �폜 �����܂�----------------------------------------------------------------------------------- */
							
/* 2005/03/25 �ǉ� ��������------------------------------------------------------------------------
 * ���R private���\�b�h�� */
													
							//���ރ}�b�v�Ɏ���ID�A���Ɩ��A�N�x�A��t���ԁi�J�n���j�A��t���ԁi�I�����j���Z�b�g
							shoruiMap = getShoruiMapFromResult(map);
													
/* �ǉ� �����܂�----------------------------------------------------------------------------------- */
							
							//���ޖ��}�b�v�ɏ��ޖ��A�V�X�e����t�ԍ����Z�b�g
							shoruiNameMap = new HashMap();	
							shoruiNameMap.put("SHORUI_NAME", map.get("SHORUI_NAME"));
							shoruiNameMap.put("SYSTEM_NO", map.get("SYSTEM_NO"));
							//���ޖ����X�g�ɏ��ޖ��}�b�v���Z�b�g
							shoruiNameList.add(shoruiNameMap);
							//����ID�i�[�p�ɃZ�b�g
							jigyoId = (String)map.get("JIGYO_ID");
						}
					}

					count++;
				}
				//�㏈��
				//���ރ}�b�v�ɏ��ޖ����i�[����
				shoruiMap.put("SHORUI_NAME", shoruiNameList);			
				//���ރ��X�g�ɏ��ރ}�b�v��ǉ�
				shoruiList.add(shoruiMap);
				result.setList(shoruiList);
			}
			
			//�������ʂ����N�G�X�g�����ɃZ�b�g
			request.setAttribute(IConstants.RESULT_INFO,result);

			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
		}
	
	private Map getShoruiMapFromResult(Map map){
		Map shoruiMap = null;//���ރ}�b�v

		//���ރ}�b�v�Ɏ���ID�A���Ɩ��A�N�x�A��t���ԁi�J�n���j�A��t���ԁi�I�����j���Z�b�g
		shoruiMap = new HashMap();
		shoruiMap.put("JIGYO_ID", map.get("JIGYO_ID"));
		shoruiMap.put("JIGYO_NAME", map.get("JIGYO_NAME"));
		shoruiMap.put("NENDO", map.get("NENDO"));
		shoruiMap.put("KAISU", map.get("KAISU"));
		shoruiMap.put("UKETUKEKIKAN_START", map.get("UKETUKEKIKAN_START"));
		shoruiMap.put("UKETUKEKIKAN_END", map.get("UKETUKEKIKAN_END"));
		
		return shoruiMap;
	}

}
