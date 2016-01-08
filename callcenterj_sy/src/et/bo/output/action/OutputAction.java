/*
 * OutputAction.java	 2008-05-27
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.output.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.output.service.OutputService;
import et.po.OperBookMedicinfo;
import et.po.OperCaseinfo;
import et.po.OperCorpinfo;
import et.po.OperFocusinfo;
import et.po.OperInquiryCard;
import et.po.OperInquiryinfo;
import et.po.OperMarkanainfo;
import et.po.OperMedicinfo;
import et.po.OperPriceinfo;
import et.po.OperSadinfo;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>文件导出action</p>
 * 
 * @version 2008-03-29
 * @author nie
 */

public class OutputAction extends BaseAction {
	
	static Logger log = Logger.getLogger(OutputAction.class.getName());
	
	private OutputService outputService = null;
	public OutputService getOutputService() {
		return outputService;
	}
	public void setOutputService(OutputService outputService) {
		this.outputService = outputService;
	}

	/**
	 * 根据URL参数执行 toDelMessages 方法，返回要forward页面。
	 * 删除“消息管理”里无用的消息
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 输出文件，关闭当前页面
	 */
	public ActionForward toDelMessages(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException{
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String outputID = (String)dto.get("outputID");
		System.out.println("outputID: "+outputID);
		String dbType = (String)dto.get("dbType");
		PrintWriter out = response.getWriter();
		if(outputID==null || outputID.equals("")){
			out.print("没有选择要删除的消息，请重新选择！");
			return null;
		}else{
			outputService.delMessagesList(outputID);
			out.print("批量删除成功！");
//			return map.findForward("query");
		}
		return map.findForward("query");
	}
	/**
	 * 根据URL参数执行 toOutputFile 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 输出文件，关闭当前页面
	 */
	public ActionForward toOutputFile(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException{
		String submit = request.getParameter("Submit4");

		response.setHeader("Pragrma", "no-cache");
	    response.setHeader("Cache-Control", "no-store");
	    response.setDateHeader("Expires", 0);
	    response.setCharacterEncoding("gbk");
	    response.setContentType("text/html; charset=gbk");
	    PrintWriter out = response.getWriter();
	
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String filetype = (String)dto.get("filetype");
		String outputID = (String)dto.get("outputID");
		String dbType = (String)dto.get("dbType");
			
		if(outputID==null || outputID.equals("")){
			out.print("没有选择要生成的记录，不能生成文档！");
			return null;
		}
		
		String path = request.getRealPath("/");
		String name = new java.text.SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS").format(new java.util.Date());
		String fileName = path + "output/down/" + name + "." + filetype;
		
		if(submit != null && submit.equals("导出")){//如果是用来生成文档地
			if(filetype.equals("doc")){//只有案例库是需要生成doc的
				
				List<OperCaseinfo> list = outputService.getCaseList(outputID);
				outputService.outputWordFile(list, fileName, dbType);
				
			}else if(filetype.equals("txt")){
				
				List<OperCaseinfo> list = outputService.getCaseList(outputID);
				outputService.outputTxtFile(list, fileName, dbType);
				
			}else if(filetype.equals("xls")){
				
				if( dbType.equals("general") || dbType.equals("focus") || dbType.equals("hzinfo") || dbType.equals("effect")){//如果是案例库的某某某格式
					List<OperCaseinfo> list = outputService.getCaseList(outputID);
					outputService.outputExcelFile(list, fileName, dbType);
				}else if(dbType.equals("price")){			//如果是价格库
					List<OperPriceinfo> list = outputService.getPriceList(outputID);
					outputService.outputExcelFile(list, fileName, dbType);
				}else if(dbType.equals("sad")){			//如果是供求库
					List<OperSadinfo> list = outputService.getSadList(outputID);
					outputService.outputExcelFile(list, fileName, dbType);
				}else if(dbType.equals("trace")){	//焦点追踪
					List<OperFocusinfo> list = outputService.getFocusList(outputID);
					try{
//						outputService.outputExcel(list, fileName, dbType);
						outputService.outputExcelFile(list, fileName, dbType);
					}catch(Exception e){
						e.printStackTrace();
					}
				}else if(dbType.equals("mart")){
					List<OperMarkanainfo> list = outputService.getMarkList(outputID);
					try{
						outputService.outputExcelFile(list, fileName, dbType);
					}catch(Exception e){
						e.printStackTrace();
					}
				}else if(dbType.equals("crop")){
					List<OperCorpinfo> list = outputService.getCrop(outputID);
					try {
						outputService.outputExcelFile(list, fileName, dbType);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(dbType.equals("medicinfo")){
					List<OperMedicinfo> list = outputService.getMedical(outputID);
					try {
						outputService.outputExcelFile(list, fileName, dbType);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(dbType.equals("bookmedicinfo")){
					List<OperBookMedicinfo> list = outputService.getbookMedical(outputID);
					try {
						outputService.outputExcelFile(list, fileName, dbType);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(dbType.equals("inquiryresult")){
					List list = outputService.getResultList(outputID);
					try {
						outputService.outputExcelFile(list, fileName, dbType);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(dbType.equals("inquirycard")){
					List<OperInquiryinfo> list = outputService.getInquiryCardList(outputID);
					try {
						outputService.outputExcelFile(list, fileName, dbType);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			try{
				InputStream is = new FileInputStream(fileName);
				response.reset(); 
				response.setContentType("bin");
				response.addHeader("Content-Disposition","attachment;filename="+ name + "." + filetype);
				byte[] b = new byte[1000]; 
				int len; 
				while((len = is.read(b)) >0) 
				response.getOutputStream().write(b,0,len); 
				response.getOutputStream().close();
				is.close();
			}catch(Exception e){
				System.err.println(e);
			}
		
		}else{//如果是用来打印的
			if(filetype.equals("doc")){//只有案例库是需要生成doc的
				List<OperCaseinfo> list = outputService.getCaseList(outputID);
				request.setAttribute("list", list);
				return map.findForward("docFormat");
				
			}else if(filetype.equals("txt")){
				List<OperCaseinfo> list = outputService.getCaseList(outputID);
				request.setAttribute("list", list);
				return map.findForward("txtFormat");
				
			}else if(filetype.equals("xls")){
				if( dbType.equals("general") || dbType.equals("focus") || dbType.equals("hzinfo") || dbType.equals("effect")){//如果是案例库的某某某格式
					
					List<OperCaseinfo> list = outputService.getCaseList(outputID);
					request.setAttribute("list", list);
					
					if(dbType.equals("general") || dbType.equals("focus")){	//普通和焦点格式的
						return map.findForward("xlsCaseFormat1");
					}else if(dbType.equals("hzinfo")){						//会诊格式
						return map.findForward("xlsCaseFormat2");
					}else if(dbType.equals("effect")){						//效果格式
						return map.findForward("xlsCaseFormat3");
					}
					
				}else if(dbType.equals("price")){			//如果是价格库
					List<OperPriceinfo> list = outputService.getPriceList(outputID);
					request.setAttribute("list", list);
					return map.findForward("xlsPriceFormat");
					
				}else if(dbType.equals("sad")){			//如果是供求库
					List<DynaBeanDTO> list = outputService.getSadList2(outputID);
					request.setAttribute("list", list);
					return map.findForward("xlsSadFormat");
					
				}else if(dbType.equals("mart")){
					//List<DynaBeanDTO> list = outputService.getSadList2(outputID);
					List<OperMarkanainfo> l = outputService.getMarkList(outputID);
					request.setAttribute("list", l);
					//request.setAttribute("list", list);
					return map.findForward("xlsMarkanainfoFormat");
				}else if(dbType.equals("trace")){
					//outputService.get
					List<OperFocusinfo> l = outputService.getTraceList(outputID);
					request.setAttribute("list", l);
					return map.findForward("xlsTraceFormat");
				}else if(dbType.equals("inquiryresult")){
					//outputService.get
					List l = outputService.getInquiryResult2List(outputID);
					request.setAttribute("list", l);
					return map.findForward("xlsInquiryResultFormat");
				}else if(dbType.equals("inquirycard")){
					//outputService.get
					List l = outputService.getInquiryResultList(outputID);
					request.setAttribute("resultlist", l);
					
					List typelist = outputService.getDictInquiryType(outputID);
					request.setAttribute("typelist", typelist);					
					
					List cardlist = outputService.getCard(outputID);
					request.setAttribute("cardlist", cardlist);
					return map.findForward("xlsInquiryCardFormat");
				}
				else{
					//System.out.println("######"+dbType);
				}
			}
			return map.findForward("query");
		}
		
		return null;
	}
	

}
