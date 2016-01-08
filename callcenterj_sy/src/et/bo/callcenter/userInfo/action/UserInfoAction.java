/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.callcenter.userInfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.userInfo.service.UserInfoService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>�ͻ�����action</p>
 * 
 * @version 2008-03-28
 * @author nie
 */

public class UserInfoAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserInfoAction.class.getName());
	
	private UserInfoService uis = null;
	private ClassTreeService cts = null;
	//ע��service
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
	/**
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toCustinfoMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		//System.out.println(".........."+dto.get("outputID"));
		
		return map.findForward("main");
		
	}
	/**
	 * ����URL����ִ�� toCustinfoQuery ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toCustinfoQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		List typeList =  cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
		
		return map.findForward("query");
		
	}
	/**
	 * ����URL����ִ�� toCustinfoList ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCustinfoList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String telType = dto.get("tel_type").toString();
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("userpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
       //ȡ��list������
        List list = uis.custinfoQuery(dto,pageInfo);
        int size = uis.getCustinfoSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);
        
        if("mobile".equals(telType))
        	request.getSession().setAttribute("telType", "mobile");
        else if("workphone".equals(telType))
        	request.getSession().setAttribute("telType", "workphone");
        else if("homephone".equals(telType))
        	request.getSession().setAttribute("telType", "homephone");

		return map.findForward("list");
	}
	
	/**
	 * ����URL����ִ�� toAllList ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toAllList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

        List list = uis.custinfoAllQuery();
        request.setAttribute("list",list);

		return map.findForward("alllist");
	}
	
	/**
	 * ����URL����ִ�� toCustinfoLoad ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCustinfoLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        
        List sexList =  cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);
		
		List typeList =  cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
        
        //����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = uis.getCustinfoInfo(id);
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = uis.getCustinfoInfo(id);
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = uis.getCustinfoInfo(id);
        	request.setAttribute(map.getName(), dto);
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	/**
	 * ����URL����ִ�� toCustinfoOper ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCustinfoOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		
		List sexList =  cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);
		
		List typeList =  cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
        
        if (type.equals("insert")) {
			try {

				uis.addCustinfo(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=uis.updateCustinfo(dto);
				if(b==true){
					request.setAttribute("operSign", "�޸ĳɹ�");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","�޸�ʧ��");
					return map.findForward("load");
				}
				
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
        
        if (type.equals("delete")){
			try {
				
				//custinfoService.delCustinfo((String)dto.get("cust_id"));//���������ɾ���˰���
				
				//��������Ǳ��ɾ��
				boolean b=uis.isDelete((String)dto.get("cust_id"));
				if(b==true){
					request.setAttribute("operSign", "ɾ���ɹ�");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","ɾ��ʧ��");
					return map.findForward("load");
				}
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}		
        
        
		return map.findForward("load");
	}
	public UserInfoService getUis() {
		return uis;
	}
	public void setUis(UserInfoService uis) {
		this.uis = uis;
	}

}
