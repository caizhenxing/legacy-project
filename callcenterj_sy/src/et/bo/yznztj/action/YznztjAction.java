/* ��    ����et.bo.yznztj.action
 * �� �� ����YznztjAction.java
 * ע��ʱ�䣺2008-8-28 15:25:04
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */

package et.bo.yznztj.action;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import et.bo.yznztj.service.YznztjService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * MyEclipse Struts Creation date: 07-21-2008  wang lichun
 * 
 * XDoclet definition:.
 * 
 * @struts.action path="/export" name="YznztjForm" parameter="method"
 * scope="request" validate="true"
 */
public class YznztjAction extends BaseAction {
	/*
	 * Generated Methods
	 * 
	 */

	
static Logger log = Logger.getLogger(YznztjAction.class.getName());
	
	private YznztjService yznztjService = null;
	
	/**
	 * ����URL����ִ�� toYznztjMain ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toYznztjMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		return map.findForward("main");
		
	}
	
	/**
	 * ����URL����ִ�� toYznztjQuery ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toYznztjQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("query");
		
	}
	
	/**
	 * ����URL����ִ�� toYznztjList ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toYznztjList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
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
        pageInfo.setPageSize(15);
       // ȡ��list������
        List list = yznztjService.yznztjQuery(dto,pageInfo);
        int size = yznztjService.getYznztjSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);

		return map.findForward("list");
	}
	
	/**
	 * Ӧ���ڴ���Ļ���б�.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toYznztjList2(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

        List list = yznztjService.yznztjQuery2();
        request.setAttribute("list",list);
       
		return map.findForward("list2");
	}
	
	/**
	 * �������ũ�ʵ���Ƭ.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toYznztjPhoto(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");		
		try {
			IBaseDTO dto=yznztjService.getYznztjInfo(id);
			Blob blob=(Blob)dto.get("photo");
			int length=(int)blob.length();
			byte   []   byte_array     = blob.getBytes(1, length);
            response.setContentType("image/jpeg");  
            ServletOutputStream   sos   =   response.getOutputStream();  
   
          for(int   i=0;i<byte_array.length;i++) {  
        	  sos.write(byte_array[i]);  
          }  
            sos.close();  
           
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * ����URL����ִ�� toYznztjLoad ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */	
	
	public ActionForward toYznztjLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        // ����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){        	
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = yznztjService.getYznztjInfo(id);
        	request.setAttribute(map.getName(),dto);
        	request.setAttribute("id",id);
        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = yznztjService.getYznztjInfo(id);
        	request.setAttribute(map.getName(),dto);
        	request.setAttribute("id",id);
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = yznztjService.getYznztjInfo(id);
        	request.setAttribute(map.getName(), dto);
        	request.setAttribute("id",id);
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	
	/**
	 * ����URL����ִ�� toYznztjOper ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toYznztjOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
        
        if (type.equals("insert")) {
			try {	
				yznztjService.addYznztj(dto);			
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=yznztjService.updateYznztj(dto);
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
				String id=request.getParameter("id");
				// ��������Ǳ��ɾ��
				yznztjService.delYznztj(id);				
				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("load");
			} catch (RuntimeException e) {
				request.setAttribute("operSign","ɾ��ʧ��");
				return map.findForward("load");
			}
		}		
        
        
		return map.findForward("load");
	}
	
	/**
	 * Gets the yznztj service.
	 * The service type is YznztjService.
	 * @return the yznztj service
	 */
	public YznztjService getYznztjService() {
		return yznztjService;
	}
	
	/**
	 * Sets the yznztj service.
	 * The service type is YznztjService.
	 * @param yznztjService the new yznztj service
	 */
	public void setYznztjService(YznztjService yznztjService) {
		this.yznztjService = yznztjService;
	}

}

