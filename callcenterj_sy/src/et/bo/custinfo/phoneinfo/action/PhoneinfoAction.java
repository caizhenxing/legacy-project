package et.bo.custinfo.phoneinfo.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.custinfo.action.CustinfoAction;
import et.bo.custinfo.service.PhoneinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class PhoneinfoAction extends BaseAction {
	static Logger log = Logger.getLogger(CustinfoAction.class.getName());

	private ClassTreeService cts = null;

	private PhoneinfoService pis = null;

	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toPhoneMain(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String type = (String) request.getParameter("type");
		request.getSession().setAttribute("type", type);
		if (type != null) {
			try {
				request.getRequestDispatcher("/custinfo/phoneinfo/custLinkManMain.jsp").forward(request, response);
				return null;
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map.findForward("main");
	}

	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toPhoneQuery(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// 取出不同客户类型的集合，供load页显示
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteTypeList", telNoteTypeList);// 供前台JSP显示时所用

		List user = pis.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
		request.setAttribute("user", user);

		return map.findForward("query");
	}

	/**
	 * 根据在电话本load页中的下拉列表选择不同用户类型时实现自动load到相应客户类型的电话本load页，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回相应客户类型的电话本load页
	 */
	public ActionForward toPhoneLoad(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String forwardPage = "agengload";// 页面跳转标志
		/**
		 * 媒体：SYS_TREE_0000002105 企业:SYS_TREE_0000002104 专家:SYS_TREE_0000002103 座席员:SYS_TREE_0000002102 普通用户:SYS_TREE_0000002109 联络员:SYS_TREE_0000002108
		 * 政府:SYS_TREE_0000002106
		 */
		String type = request.getParameter("type");// 取出不同的操作标志
		request.setAttribute("opertype", type);

		// 取出不同性别的集合，供load页显示
		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		// 取出不同客户类型的集合，供load页显示
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		telNoteTypeList.remove(0);
		request.setAttribute("telNoteTypeList", telNoteTypeList);// 供前台JSP显示时所用

		// 从事行业
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// 行业规模
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		String custType = request.getParameter("custType");// 取出不同的客户类型

		if (custType == null) {
			custType = "SYS_TREE_0000002103";
		}

		if (custType != null && !"".equals(custType.trim())) {
			if ("SYS_TREE_0000002105".equals(custType))
				forwardPage = "mediaload";
			else if ("SYS_TREE_0000002104".equals(custType))
				forwardPage = "projectload";
			else if ("SYS_TREE_0000002103".equals(custType))
				forwardPage = "expertload";
			else if ("SYS_TREE_0000002102".equals(custType))
				forwardPage = "agentload";
			else if ("SYS_TREE_0000002109".equals(custType))
				forwardPage = "commonload";
			else if ("SYS_TREE_0000002108".equals(custType))
				forwardPage = "connectload";
			else if ("SYS_TREE_0000002106".equals(custType))
				forwardPage = "govload";
			return map.findForward(forwardPage);// 默认是座席员的load页
		} else
			return map.findForward("agentload");// 默认是座席员的load页
	}

	public ActionForward phoneLoad(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String forwardPage = "agengload";// 页面跳转标志

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		// 取出不同性别的集合，供load页显示
		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		// 取出不同客户类型的集合，供load页显示
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		telNoteTypeList.remove(0);
		request.setAttribute("telNoteTypeList", telNoteTypeList);// 供前台JSP显示时所用

		// 从事行业
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// 行业规模
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		String custType = request.getParameter("custType");// 取出不同的客户类型

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// 行业信息列表
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);

		if (custType == null) {
			custType = "SYS_TREE_0000002103";
		}

		// 根本type参数判断执行所需要的操作
		if (type.equals("insert")) {
				if (custType != null && !"".equals(custType.trim())) {
				if ("SYS_TREE_0000002105".equals(custType))
					forwardPage = "mediaload";
				else if ("SYS_TREE_0000002104".equals(custType))
					forwardPage = "projectload";
				else if ("SYS_TREE_0000002103".equals(custType))
					forwardPage = "expertload";
				else if ("SYS_TREE_0000002102".equals(custType))
					forwardPage = "agentload";
				else if ("SYS_TREE_0000002109".equals(custType))
					forwardPage = "commonload";
				else if ("SYS_TREE_0000002108".equals(custType))
					forwardPage = "connectload";
				else if ("SYS_TREE_0000002106".equals(custType))
					forwardPage = "govload";
				
				System.out.println("forwardPage = "+forwardPage);
				return map.findForward(forwardPage);// 默认是座席员的load页
			}
		}

		if (type.equals("detail")) {

			String id = request.getParameter("id");
			IBaseDTO dto = pis.getPhoneinfo(id);
			request.setAttribute(map.getName(), dto);
			if (custType != null && !"".equals(custType.trim())) {
				if ("SYS_TREE_0000002105".equals(custType))
					forwardPage = "mediaload";
				else if ("SYS_TREE_0000002104".equals(custType))
					forwardPage = "projectload";
				else if ("SYS_TREE_0000002103".equals(custType))
					forwardPage = "expertload";
				else if ("SYS_TREE_0000002102".equals(custType))
					forwardPage = "agentload";
				else if ("SYS_TREE_0000002109".equals(custType))
					forwardPage = "commonload";
				else if ("SYS_TREE_0000002108".equals(custType))
					forwardPage = "connectload";
				else if ("SYS_TREE_0000002106".equals(custType))
					forwardPage = "govload";
				
				System.out.println("forwardPage = "+forwardPage);
				return map.findForward(forwardPage);// 默认是座席员的load页
			}
		}

		if (type.equals("update")) {

			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = pis.getPhoneinfo(id);
			request.setAttribute(map.getName(), dto);
			if (custType != null && !"".equals(custType.trim())) {
				if ("SYS_TREE_0000002105".equals(custType))
					forwardPage = "mediaload";
				else if ("SYS_TREE_0000002104".equals(custType))
					forwardPage = "projectload";
				else if ("SYS_TREE_0000002103".equals(custType))
					forwardPage = "expertload";
				else if ("SYS_TREE_0000002102".equals(custType))
					forwardPage = "agentload";
				else if ("SYS_TREE_0000002109".equals(custType))
					forwardPage = "commonload";
				else if ("SYS_TREE_0000002108".equals(custType))
					forwardPage = "connectload";
				else if ("SYS_TREE_0000002106".equals(custType))
					forwardPage = "govload";
				
				System.out.println("forwardPage = "+forwardPage);
				return map.findForward(forwardPage);// 默认是座席员的load页
			}
		}
		if (type.equals("delete")) {

			String id = request.getParameter("id");
			IBaseDTO dto = pis.getPhoneinfo(id);
			request.setAttribute(map.getName(), dto);
			if (custType != null && !"".equals(custType.trim())) {
				if ("SYS_TREE_0000002105".equals(custType))
					forwardPage = "mediaload";
				else if ("SYS_TREE_0000002104".equals(custType))
					forwardPage = "projectload";
				else if ("SYS_TREE_0000002103".equals(custType))
					forwardPage = "expertload";
				else if ("SYS_TREE_0000002102".equals(custType))
					forwardPage = "agentload";
				else if ("SYS_TREE_0000002109".equals(custType))
					forwardPage = "commonload";
				else if ("SYS_TREE_0000002108".equals(custType))
					forwardPage = "connectload";
				else if ("SYS_TREE_0000002106".equals(custType))
					forwardPage = "govload";
				
				System.out.println("forwardPage = "+forwardPage);
				return map.findForward(forwardPage);// 默认是座席员的load页
			}
		}

		if ("countinsert".equals(type)) {
			return map.findForward("countinsert");
		}

		return map.findForward("load");
	}

	/**
	 * 列表页
	 * 
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPhoneList(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession().getAttribute("phonepageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(15);
		// 取得list及条数
		List list = pis.phoneinfoQuery(dto, pageInfo);
		int size = pis.phoneSize();

		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("phonepageTurning", pt);

		return map.findForward("list");
	}

	/**
	 * 根据URL参数执行 toCustinfoOper 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toPhoneOper(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		// 取出不同客户类型的集合，供load页显示
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteTypeList", telNoteTypeList);// 供前台JSP显示时所用

		// 从事行业
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// 行业规模
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// 行业信息列表
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);

		String custType = dto.get("dict_cust_type").toString();
		String forwardPage = "";

		if (type.equals("insert")) {
			if (custType != null && !"".equals(custType.trim())) {
				if ("SYS_TREE_0000002105".equals(custType)) {
					forwardPage = "mediaload";
					String uploadfile = (String) request.getSession().getAttribute("uploadfile");
					if (uploadfile != null && !uploadfile.equals("")) {
						dto.set("cust_banner", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");

					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				} else if ("SYS_TREE_0000002104".equals(custType)) {
					forwardPage = "projectload";
					String uploadfile = (String) request.getSession().getAttribute("uploadfile");
					if (uploadfile != null && !uploadfile.equals("")) {
						dto.set("cust_banner", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");

					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				} else if ("SYS_TREE_0000002103".equals(custType)) {
					forwardPage = "expertload";
					String uploadfile = (String) request.getSession().getAttribute("uploadfile");
					if (uploadfile != null && !uploadfile.equals("")) {
						dto.set("cust_pic_path", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");

					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				} else if ("SYS_TREE_0000002102".equals(custType)) {
					forwardPage = "agentload";
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");
					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				} else if ("SYS_TREE_0000002109".equals(custType)) {
					forwardPage = "commonload";
					String uploadfile = (String) request.getSession().getAttribute("uploadfile");
					if (uploadfile != null && !uploadfile.equals("")) {
						dto.set("cust_pic_path", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");

					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				} else if ("SYS_TREE_0000002108".equals(custType)) {

					forwardPage = "connectload";
					String uploadfile = (String) request.getSession().getAttribute("uploadfile");
					if (uploadfile != null && !uploadfile.equals("")) {
						dto.set("cust_pic_path", uploadfile);
						request.getSession().removeAttribute("uploadfile");
						
					}
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");
					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				} else if ("SYS_TREE_0000002106".equals(custType)) {
					forwardPage = "govload";
					String uploadfile = (String) request.getSession().getAttribute("uploadfile");
					if (uploadfile != null && !uploadfile.equals("")) {
						dto.set("cust_banner", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}
					try {
						pis.addPhoneinfo(dto);
						request.setAttribute("operSign", "sys.common.operSuccess");

					} catch (RuntimeException e) {
						return map.findForward("error");
					}
				}
				System.out.println("SSSSSSSSSDDDfffDDDSSSSSSSSSSS");
				return map.findForward(forwardPage);// 默认是座席员的load页
			}
		}

		if (type.equals("update")) {
			try {
				// boolean b = cis.updateCustinfo(dto);
				if (custType != null && !"".equals(custType.trim())) {
					if ("SYS_TREE_0000002105".equals(custType)) {
						forwardPage = "mediaload";
						String uploadfile = (String) request.getSession().getAttribute("uploadfile");
						String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
						if (uploadfile != null && !uploadfile.equals("")) {
							if (oldUploadFile != null && !oldUploadFile.equals("")) {
								uploadfile = oldUploadFile + "," + uploadfile;
							}
							dto.set("cust_banner", uploadfile);
						} else {
							dto.set("cust_banner", oldUploadFile);
						}
						request.getSession().removeAttribute("oldUploadFile");
						request.getSession().removeAttribute("uploadfile");
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					} else if ("SYS_TREE_0000002104".equals(custType)) {
						forwardPage = "projectload";
						String uploadfile = (String) request.getSession().getAttribute("uploadfile");
						String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
						if (uploadfile != null && !uploadfile.equals("")) {
							if (oldUploadFile != null && !oldUploadFile.equals("")) {
								uploadfile = oldUploadFile + "," + uploadfile;
							}
							dto.set("cust_banner", uploadfile);
						} else {
							dto.set("cust_banner", oldUploadFile);
						}
						request.getSession().removeAttribute("oldUploadFile");
						request.getSession().removeAttribute("uploadfile");
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					} else if ("SYS_TREE_0000002103".equals(custType)) {
						forwardPage = "expertload";
						String uploadfile = (String) request.getSession().getAttribute("uploadfile");
						String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
						if (uploadfile != null && !uploadfile.equals("")) {
							if (oldUploadFile != null && !oldUploadFile.equals("")) {
								uploadfile = oldUploadFile + "," + uploadfile;
							}
							dto.set("cust_pic_path", uploadfile);
						} else {
							dto.set("cust_pic_path", oldUploadFile);
						}
						request.getSession().removeAttribute("oldUploadFile");
						request.getSession().removeAttribute("uploadfile");
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					} else if ("SYS_TREE_0000002102".equals(custType)) {
						forwardPage = "agentload";
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					} else if ("SYS_TREE_0000002109".equals(custType)) {
						forwardPage = "commonload";
						String uploadfile = (String) request.getSession().getAttribute("uploadfile");
						String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
						if (uploadfile != null && !uploadfile.equals("")) {
							if (oldUploadFile != null && !oldUploadFile.equals("")) {
								uploadfile = oldUploadFile + "," + uploadfile;
							}
							dto.set("cust_pic_path", uploadfile);
						} else {
							dto.set("cust_pic_path", oldUploadFile);
						}
						request.getSession().removeAttribute("oldUploadFile");
						request.getSession().removeAttribute("uploadfile");
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					} else if ("SYS_TREE_0000002108".equals(custType)) {
						forwardPage = "connectload";
						String uploadfile = (String) request.getSession().getAttribute("uploadfile");
						String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
						if (uploadfile != null && !uploadfile.equals("")) {
							if (oldUploadFile != null && !oldUploadFile.equals("")) {
								uploadfile = oldUploadFile + "," + uploadfile;
							}
							dto.set("cust_pic_path", uploadfile);
						} else {
							dto.set("cust_pic_path", oldUploadFile);
						}
						request.getSession().removeAttribute("oldUploadFile");
						request.getSession().removeAttribute("uploadfile");
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					} else if ("SYS_TREE_0000002106".equals(custType)) {
						forwardPage = "govload";
						String uploadfile = (String) request.getSession().getAttribute("uploadfile");
						String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
						if (uploadfile != null && !uploadfile.equals("")) {
							if (oldUploadFile != null && !oldUploadFile.equals("")) {
								uploadfile = oldUploadFile + "," + uploadfile;
							}
							dto.set("cust_banner", uploadfile);
						} else {
							dto.set("cust_banner", oldUploadFile);
						}
						request.getSession().removeAttribute("oldUploadFile");
						request.getSession().removeAttribute("uploadfile");
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "修改成功");
					}
					return map.findForward(forwardPage);// 默认是座席员的load页
				}
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		// String uploadfile = (String)request.getSession().getAttribute("uploadfile");
		// String oldUploadFile = (String)request.getSession().getAttribute("oldUploadFile");
		// if(uploadfile !=null && !uploadfile.equals("")){
		// if(oldUploadFile !=null && !oldUploadFile.equals("")){
		// uploadfile = oldUploadFile + "," + uploadfile;
		// }
		// dto.set("uploadfile", uploadfile);
		// }else{
		// dto.set("uploadfile", oldUploadFile);
		// }
		// request.getSession().removeAttribute("oldUploadFile");
		// request.getSession().removeAttribute("uploadfile");
		// try {
		// // boolean b = cis.updateCustinfo(dto);
		// pis.updatePhoneinfo(dto);
		// request.setAttribute("operSign", "修改成功");
		// if (custType != null && !"".equals(custType.trim())) {
		// if ("SYS_TREE_0000002105".equals(custType))
		// forwardPage = "mediaload";
		// else if ("SYS_TREE_0000002104".equals(custType))
		// forwardPage = "projectload";
		// else if ("SYS_TREE_0000002103".equals(custType))
		// forwardPage = "expertload";
		// else if ("SYS_TREE_0000002102".equals(custType))
		// forwardPage = "agentload";
		// else if ("SYS_TREE_0000002109".equals(custType))
		// forwardPage = "commonload";
		// else if ("SYS_TREE_0000002108".equals(custType))
		// forwardPage = "connectload";
		// else if ("SYS_TREE_0000002106".equals(custType))
		// forwardPage = "govload";
		// return map.findForward(forwardPage);// 默认是座席员的load页
		// }
		// } catch (RuntimeException e) {
		// log.error("PortCompareAction : update ERROR");
		// e.printStackTrace();
		// return map.findForward("error");
		// }
		// }

		if (type.equals("delete")) {
			try {
				// custinfoService.delCustinfo((String)dto.get("cust_id"));//这句可是真的删除了啊！
				// 下面这块是标记删除
				// pis.delPhoneinfo("");
				String id = dto.get("cust_id").toString();
				pis.delPhoneinfo(id);
				request.setAttribute("operSign", "删除成功");
				if (custType != null && !"".equals(custType.trim())) {
					if ("SYS_TREE_0000002105".equals(custType))
						forwardPage = "mediaload";
					else if ("SYS_TREE_0000002104".equals(custType))
						forwardPage = "projectload";
					else if ("SYS_TREE_0000002103".equals(custType))
						forwardPage = "expertload";
					else if ("SYS_TREE_0000002102".equals(custType))
						forwardPage = "agentload";
					else if ("SYS_TREE_0000002109".equals(custType))
						forwardPage = "commonload";
					else if ("SYS_TREE_0000002108".equals(custType))
						forwardPage = "connectload";
					else if ("SYS_TREE_0000002106".equals(custType))
						forwardPage = "govload";
					return map.findForward(forwardPage);// 默认是座席员的load页
				}
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("load");
	}

	/**
	 * 联络员批量添加
	 * 
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toPhoneCountAdd(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String excelPath = (String) dto.get("excelPath");
		boolean flag = pis.addCountPhoneInfo(excelPath);

		if (flag)
			request.setAttribute("operSign", "修改成功");
		return map.findForward("main");
	}

	/* 开始更改 */

	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toLinkManQuery(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// 取出不同客户类型的集合，供load页显示
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteTypeList", telNoteTypeList);// 供前台JSP显示时所用

		List user = pis.userQuery("select user_id from sys_user where (group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141')");
		request.setAttribute("user", user);

		return map.findForward("linkquery");
	}

	/**
	 * 上传照片
	 * 
	 * @param
	 * @version 2006-9-16
	 * @return
	 */
	public ActionForward upload(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String type = (String) request.getParameter("type");
		String id = request.getParameter("id").toString();
		request.setAttribute("id", id);
		return map.findForward("upload");
	}

	/**
	 * 列表页
	 * 
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toLinkManList(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 DynaActionFormDTO dto = (DynaActionFormDTO) form;
		 String pageState = null;
		 PageInfo pageInfo = null;
		 pageState = (String) request.getParameter("pagestate");
		 if (pageState == null) {
		 pageInfo = new PageInfo();
		 } else {
		 PageTurning pageTurning = (PageTurning) request.getSession()
		 .getAttribute("phonepageTurning");
		 pageInfo = pageTurning.getPage();
		 pageInfo.setState(pageState);
		 dto = (DynaActionFormDTO) pageInfo.getQl();
		 }
		 pageInfo.setPageSize(13);
		 // 取得list及条数
		 List list = pis.phoneinfoQuery2(dto, pageInfo);
		 int size = pis.phoneSize();
		
		 pageInfo.setRowCount(size);
		 pageInfo.setQl(dto);
		 request.setAttribute("list", list);
		 PageTurning pt = new PageTurning(pageInfo, "", map, request);
		 request.getSession().setAttribute("phonepageTurning", pt);

		return map.findForward("linklist");
	}
	
	public ActionForward linkmanLoad(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		// 取出不同性别的集合，供load页显示
		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

//		// 取出不同客户类型的集合，供load页显示
//		List telNoteTypeList = cts.getLabelVaList("telNoteType");
//		telNoteTypeList.remove(0);
//		request.setAttribute("telNoteTypeList", telNoteTypeList);// 供前台JSP显示时所用

		// 从事行业
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// 行业规模
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// 行业信息列表
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);
		
		UserBean ub = (UserBean)request.getSession()
						.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		
//		// 用户类别
//		List leibielist = cts.getLabelVaList("yonghuleibie");
//		request.setAttribute("leibielist", leibielist);
//		
//		// 工作方式
//		List fangshilist = cts.getLabelVaList("gongzuofangshi");
//		request.setAttribute("workmethodlist", fangshilist);
		
		// 根本type参数判断执行所需要的操作
		if (type.equals("insert")) {
			IBaseDTO dto = (IBaseDTO)form;
			dto.set("cust_rid", ub.getUserId());
			request.setAttribute(map.getName(), dto);
			return map.findForward("linkmanload");
		}
		if (type.equals("detail")) {
			String id = request.getParameter("id");
			IBaseDTO dto = pis.getPhoneinfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("linkmanload");
		}
		if (type.equals("update")) {
			String id = request.getParameter("id");
			IBaseDTO dto = pis.getPhoneinfo(id);
			request.setAttribute(map.getName(), dto);
			
			// 把原来联络员的报价栏目显示在栏目列表中，方便修改
			Object o = dto.get("cust_job");
			String cust_job = "";
			if(o != null)
				cust_job = o.toString();
			String[] array = cust_job.split(",");
			List<String> list = new ArrayList<String>();
			if(array != null && array.length > 0){
				for(int i=0,size=array.length; i<size; i++){
					list.add(array[i]);
				}
			}
			request.setAttribute("productlist", list);
			return map.findForward("linkmanload");
		}
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = pis.getPhoneinfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("linkmanload");
		}
		return map.findForward("linkmanload");
	}
	

	public ActionForward toLinkManOper(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);


		// 从事行业
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// 行业规模
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		
//		// 用户类别
//		List leibielist = cts.getLabelVaList("yonghuleibie");
//		request.setAttribute("leibielist", leibielist);

		// 行业信息列表
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);
		
//		// 工作方式
//		List fangshilist = cts.getLabelVaList("gongzuofangshi");
//		request.setAttribute("workmethodlist", fangshilist);

		if (type.equals("insert")) {
			
			///////////用户类别处理开始(多选框)
			String cust_way_by = "";
			String[] cust_way_bys = dto.getStrings("cust_way_bys");
			for(int i = 0; i < cust_way_bys.length; i++){
				cust_way_by += cust_way_bys[i]+",";
			}
			if(cust_way_bys.length > 0){//去掉最后一个逗号
				cust_way_by = cust_way_by.substring(0, cust_way_by.length()-1);
			}
			dto.set("cust_way_by", cust_way_by);
			///////////用户类别处理结束
			
			///////////工作方式处理开始(多选框)
			String cust_work_way = "";
			String[] cust_work_ways = dto.getStrings("cust_work_ways");
			for(int i = 0; i < cust_work_ways.length; i++){
				cust_work_way += cust_work_ways[i]+",";
			}
			if(cust_work_ways.length > 0){//去掉最后一个逗号
				cust_work_way = cust_work_way.substring(0, cust_work_way.length()-1);
			}
			dto.set("cust_work_way", cust_work_way);
			///////////工作方式处理结束
			
			String uploadfile = (String) request.getSession().getAttribute("uploadfile");
			if (uploadfile != null && !uploadfile.equals("")) {
				dto.set("cust_pic_path", uploadfile);
				request.getSession().removeAttribute("uploadfile");
			}
			try {
				pis.addLinkManinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
			return map.findForward("linkmanload");// 默认是座席员的load页
		}
		if (type.equals("update")) {
			
			///////////用户类别处理开始(多选框)
			String cust_way_by = "";
			String[] cust_way_bys = dto.getStrings("cust_way_bys");
			for(int i = 0; i < cust_way_bys.length; i++){
				cust_way_by += cust_way_bys[i]+",";
			}
			if(cust_way_bys.length > 0){//去掉最后一个逗号
				cust_way_by = cust_way_by.substring(0, cust_way_by.length()-1);
			}
			dto.set("cust_way_by", cust_way_by);
			///////////用户类别处理结束
			
			///////////工作方式处理开始(多选框)
			String cust_work_way = "";
			String[] cust_work_ways = dto.getStrings("cust_work_ways");
			for(int i = 0; i < cust_work_ways.length; i++){
				cust_work_way += cust_work_ways[i]+",";
			}
			if(cust_work_ways.length > 0){//去掉最后一个逗号
				cust_work_way = cust_work_way.substring(0, cust_work_way.length()-1);
			}
			dto.set("cust_work_way", cust_work_way);
			///////////工作方式处理结束
			
			String uploadfile = (String) request.getSession().getAttribute("uploadfile");
			String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");
			if (uploadfile != null && !uploadfile.equals("")) {
				if (oldUploadFile != null && !oldUploadFile.equals("")) {
					uploadfile = oldUploadFile + "," + uploadfile;
				}
				dto.set("cust_pic_path", uploadfile);
			} else {
				dto.set("cust_pic_path", oldUploadFile);
			}
			request.getSession().removeAttribute("oldUploadFile");
			request.getSession().removeAttribute("uploadfile");
			boolean flag = pis.updateLinkManinfo(dto);
			if(flag)
				request.setAttribute("operSign", "修改成功");
		}
		if (type.equals("delete")) {
			try {
				String id = dto.get("cust_id").toString();
				pis.delPhoneinfo(id);
				request.setAttribute("operSign", "删除成功");
					return map.findForward("linkmanload");// 默认是座席员的load页
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("linkmanload");
	}
	/* 结束更改 */

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public PhoneinfoService getPis() {
		return pis;
	}

	public void setPis(PhoneinfoService pis) {
		this.pis = pis;
	}

}
