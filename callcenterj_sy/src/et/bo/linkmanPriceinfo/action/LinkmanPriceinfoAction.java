package et.bo.linkmanPriceinfo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.linkmanPriceinfo.service.LinkmanPriceinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class LinkmanPriceinfoAction extends BaseAction {

	private ClassTreeService cts=null;
	private LinkmanPriceinfoService opis = null;
	
		/**
		 * @describe 进入市场价格主页面
		 */
		public ActionForward toOperPriceinfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {

			
			return map.findForward("toMain");
	    }
		/**
		 * @describe 市场价格查询页
		 */
		public ActionForward toOperPriceinfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			List priceList =  cts.getLabelVaList("priceType");
			
			request.setAttribute("priceList", priceList);
			
			return map.findForward("toQuery");
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
	        	
	        	return map.findForward("toLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = opis.getOperPriceinfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("priceRid").toString());
	        	return map.findForward("toLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = opis.getOperPriceinfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("priceRid").toString());
	        	return map.findForward("toLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = opis.getOperPriceinfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("priceRid").toString());
	        	return map.findForward("toLoad");
	        }
			return map.findForward("toLoad");
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
	        pageInfo.setPageSize(18);
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
			return map.findForward("toList");
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
							dto.set("cust_id", (String)dto.get("cust_id"));
							
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
					return map.findForward("toLoad");
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
					return map.findForward("toLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toLoad");
	    }
		
		
		
		public ActionForward popIntersave(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return map.findForward("popIntersave");
		}
		
		/**
		 * 以下几个方法为添加联络员时用到的方法
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward add(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// String receivers = request.getParameter("value");
			String select = request.getParameter("select");

			List l = (List) request.getSession().getAttribute("userList2");
			List<LabelValueBean> ul = (List) request.getSession().getAttribute(
					"userList");
			System.out.println("userList size is "+ul.size());
			for (int i = 0, size = ul.size(); i < size; i++) {
				LabelValueBean lvb = (LabelValueBean)ul.get(i);
				if (lvb.getValue().equals(select)) {
					l.add(lvb);
					ul.remove(lvb);
					break;
				}
			}
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");

		}

		public ActionForward addall(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// String receivers = request.getParameter("value");
			List l = (List) request.getSession().getAttribute("userList2");
			List ul = (List) request.getSession().getAttribute("userList");
			l.addAll(ul);
			ul.clear();
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");
		}

		public ActionForward suball(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// String receivers = request.getParameter("value");
			List l = (List) request.getSession().getAttribute("userList2");
			List ul = (List) request.getSession().getAttribute("userList");
			ul.addAll(l);
			l.clear();
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");
		}

		/**
		 * Sub.
		 * 
		 * @param mapping the mapping
		 * @param form the form
		 * @param request the request
		 * @param response the response
		 * 
		 * @return the action forward
		 * 
		 * @throws Exception the exception
		 */
		public ActionForward sub(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String select = request.getParameter("select");

			List<LabelValueBean> l = (List<LabelValueBean>) request.getSession().getAttribute(
					"userList2");
			List ul = (List) request.getSession().getAttribute("userList");
			for (int i = 0, size = l.size(); i < size; i++) {
				LabelValueBean lvb = l.get(i);
				if (lvb.getValue().equals(select)) {
					ul.add(lvb);
					l.remove(lvb);
					break;
				}
			}
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");

		}
		
		/**
		 * 跳转到选择短消息接收人页面
		 * @param map
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward toSelectOperator(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response){
			List list = opis.getUserList();
			List<LabelValueBean> userList2 = new ArrayList<LabelValueBean>();
			request.getSession().setAttribute("userList", list);
			request.getSession().setAttribute("userList2", userList2);
			return map.findForward("select");
		}
		
		public ActionForward select(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response){
//			List list = messagesService.getUserList();
//			request.setAttribute("userList", list);
			return map.findForward("selectFrame");
		}
		
		
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		public LinkmanPriceinfoService getOpis() {
			return opis;
		}
		public void setOpis(LinkmanPriceinfoService opis) {
			this.opis = opis;
		}
		

	    
}
