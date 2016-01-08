package et.bo.priceinfo.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import et.bo.priceinfo.service.PriceinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class PriceinfoAction extends BaseAction {

	private ClassTreeService cts=null;
	private PriceinfoService opis = null;
	
	/**
	 * 价格看板中的数据信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward priceBoard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
//		GeneralCaseinfoService gcs = (GeneralCaseinfoService)SpringRunningContainer.getInstance().getBean("GeneralCaseinfoService");
		JSONArray jsonArray = JSONArray.fromObject(opis.screenList());
		outJsonString(response,jsonArray.toString());
		return null;
	}
	
		/**
		 * @describe 进入市场价格主页面
		 */
		public ActionForward toOperPriceinfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
			String str_state=request.getParameter("state");
			opis.clearMessage(ub.getUserId(), str_state);
			request.setAttribute("state", str_state);
			
			return map.findForward("toOperPriceinfoMain");
	    }
		/**
		 * @describe 市场价格查询页
		 */
		public ActionForward toOperPriceinfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			List priceList =  cts.getLabelVaList("priceType");
			
			request.setAttribute("priceList", priceList);
			request.setAttribute("userList", opis.userQuery());
			return map.findForward("toOperPriceinfoQuery");
	    }
		
		/**
		   * @describe 农产品价格库统计类型选择页
		   */
		public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			return map.findForward("topriceinfoStatisticQuery");
		}

		/**
		   * @describe 农产品价格库统计类型跳转Action
		   */
		public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			String type = request.getParameter("statisticType").toString();
			//System.out.println("type : "+type);
			if(type!=null&&!"".equals(type)){
				if("priceMMA".equals(type)) return new ActionForward("/stat/productPriceForDate.do?method=toMain");
				if("priceCount".equals(type)) return new ActionForward("/stat/priceStatByProduct.do?method=toMain");
				if("addressPrice".equals(type)) return new ActionForward("/stat/priceStatForProduct.do?method=toMain");
				if("agent".equals(type)) return new ActionForward("/stat/priceInfo.do?method=toMain");
				if("priceType".equals(type)) return new ActionForward("/stat/priceInfoForType.do?method=toMain");
				if("StatisticsByServiceType".equals(type)) return new ActionForward("/stat/productPriceStatForServiceType.do?method=toMain");
			}
			return null;
		}
		
		/**
		 * @describe 页面Load
		 */
		public ActionForward toOperPriceinfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        List priceList =  cts.getLabelVaList("priceType");
			
			request.setAttribute("priceList", priceList);
			

	        if(type.equals("insertList")){
	        	IBaseDTO dto = (IBaseDTO) form;
	        	
	        	UserBean ub = (UserBean)request.getSession().getAttribute(
						SysStaticParameter.USERBEAN_IN_SESSION);
				
				dto.set("priceRid", ub.getUserId());
	        	
//	        	dto.set("caseTime", TimeUtil.getNowTimeSr());
				request.setAttribute(map.getName(), dto);
	        	return map.findForward("toOperPriceinfoLoadList");
	        }
	        if(type.equals("insert")){
	        	
	        	return map.findForward("toOperPriceinfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = opis.getOperPriceinfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("priceRid").toString());
	        	return map.findForward("toOperPriceinfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = opis.getOperPriceinfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("priceRid").toString());
	        	return map.findForward("toOperPriceinfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = opis.getOperPriceinfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("priceRid").toString());
	        	return map.findForward("toOperPriceinfoLoad");
	        }
			return map.findForward("toOperPriceinfoLoad");
	    }
		/**
		 * @describe 市场价格列表页
		 */
		public ActionForward toOperPriceinfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {

			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("operpriceinfopageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);
	        try {
	        	list = opis.operPriceinfoQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = opis.getOperPriceinfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("operpriceinfopageTurning",pt);       
			return map.findForward("toOperPriceinfoList");
	    }
		/**
		 * @describe 市场价格列表页
		 */
		public ActionForward toOperPriceinfoExcelList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {

			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;

	        try {
	        	list = opis.operPriceinfoExcelQuery(dto);
	        	request.setAttribute("cells", list);
	        	request.setAttribute("sheetName", "农产品价格库");
	        	request.setAttribute("type", "List<List<String>>");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	        request.setAttribute("list", list);   
			return map.findForward("toOperPriceinfoExcelList");
	    }
		/**
		 * @describe 市场价格添加,修改,删除页
		 */
		public ActionForward toOperPriceinfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        List priceList =  cts.getLabelVaList("priceType");
	        
			request.setAttribute("priceList", priceList);
			
			if (type.equals("insertList")) {
				try {
					String dictProductType1[] = dto.getStrings("dict_product_type1");
					String dictProductType2[] = dto.getStrings("dict_product_type2");
					String productName[] = dto.getStrings("product_name");
					String dictPriceType[] = dto.getStrings("dict_price_type");
					String productPrice[] = dto.getStrings("product_price");
					String remark[] = dto.getStrings("remarkj");
					
					for(int i = 0; i<dictProductType2.length; i++){
						if(productPrice[i]!=null && !productPrice[i].equals("")){
							
							dto.set("dictProductType1", dictProductType1[i]);
							dto.set("dictProductType2", dictProductType2[i]);
							dto.set("productName", productName[i]);
							dto.set("dictPriceType", dictPriceType[i]);
							dto.set("productPrice", productPrice[i]);
							dto.set("remark", remark[i]);
							dto.set("priceRid", (String)dto.get("priceRid"));
							dto.set("deployTime", (String)dto.get("deployTime"));
							dto.set("custAddr", (String)dto.get("custAddr"));
							
							opis.addOperPriceinfoSad(dto);
						}
					}
					
//					request.setAttribute("addok", "保存成功！");
					request.setAttribute("operSign", "success");
					return map.findForward("toOperPriceinfoLoadList");
					
				} catch (RuntimeException e) {
					return map.findForward("error");
				}
			}
	        
			if (type.equals("insert")) {
				try {
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					
					opis.addOperPriceinfoSad(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					dto.set("accid", "admin");//受理人id
					
					boolean b=opis.updateOperPriceinfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toOperPriceinfoLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toOperPriceinfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					opis.delOperPriceinfo((String)dto.get("priceId"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toOperPriceinfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toOperPriceinfoLoad");
	    }
		
		
		
		public ActionForward popIntersave(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return map.findForward("popIntersave");
		}
		

		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		public PriceinfoService getOpis() {
			return opis;
		}
		public void setOpis(PriceinfoService opis) {
			this.opis = opis;
		}

	    
}
