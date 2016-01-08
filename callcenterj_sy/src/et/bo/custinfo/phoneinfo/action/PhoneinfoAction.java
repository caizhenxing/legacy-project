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
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
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
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toPhoneQuery(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// ȡ����ͬ�ͻ����͵ļ��ϣ���loadҳ��ʾ
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteTypeList", telNoteTypeList);// ��ǰ̨JSP��ʾʱ����

		List user = pis.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
		request.setAttribute("user", user);

		return map.findForward("query");
	}

	/**
	 * �����ڵ绰��loadҳ�е������б�ѡ��ͬ�û�����ʱʵ���Զ�load����Ӧ�ͻ����͵ĵ绰��loadҳ������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ������Ӧ�ͻ����͵ĵ绰��loadҳ
	 */
	public ActionForward toPhoneLoad(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String forwardPage = "agengload";// ҳ����ת��־
		/**
		 * ý�壺SYS_TREE_0000002105 ��ҵ:SYS_TREE_0000002104 ר��:SYS_TREE_0000002103 ��ϯԱ:SYS_TREE_0000002102 ��ͨ�û�:SYS_TREE_0000002109 ����Ա:SYS_TREE_0000002108
		 * ����:SYS_TREE_0000002106
		 */
		String type = request.getParameter("type");// ȡ����ͬ�Ĳ�����־
		request.setAttribute("opertype", type);

		// ȡ����ͬ�Ա�ļ��ϣ���loadҳ��ʾ
		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		// ȡ����ͬ�ͻ����͵ļ��ϣ���loadҳ��ʾ
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		telNoteTypeList.remove(0);
		request.setAttribute("telNoteTypeList", telNoteTypeList);// ��ǰ̨JSP��ʾʱ����

		// ������ҵ
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// ��ҵ��ģ
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// ר�����
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		String custType = request.getParameter("custType");// ȡ����ͬ�Ŀͻ�����

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
			return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
		} else
			return map.findForward("agentload");// Ĭ������ϯԱ��loadҳ
	}

	public ActionForward phoneLoad(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String forwardPage = "agengload";// ҳ����ת��־

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		// ȡ����ͬ�Ա�ļ��ϣ���loadҳ��ʾ
		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		// ȡ����ͬ�ͻ����͵ļ��ϣ���loadҳ��ʾ
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		telNoteTypeList.remove(0);
		request.setAttribute("telNoteTypeList", telNoteTypeList);// ��ǰ̨JSP��ʾʱ����

		// ������ҵ
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// ��ҵ��ģ
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		String custType = request.getParameter("custType");// ȡ����ͬ�Ŀͻ�����

		// ר�����
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// ��ҵ��Ϣ�б�
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);

		if (custType == null) {
			custType = "SYS_TREE_0000002103";
		}

		// ����type�����ж�ִ������Ҫ�Ĳ���
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
				return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
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
				return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
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
				return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
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
				return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
			}
		}

		if ("countinsert".equals(type)) {
			return map.findForward("countinsert");
		}

		return map.findForward("load");
	}

	/**
	 * �б�ҳ
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
		// ȡ��list������
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
	 * ����URL����ִ�� toCustinfoOper ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toPhoneOper(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		// ȡ����ͬ�ͻ����͵ļ��ϣ���loadҳ��ʾ
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteTypeList", telNoteTypeList);// ��ǰ̨JSP��ʾʱ����

		// ������ҵ
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// ��ҵ��ģ
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// ר�����
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// ��ҵ��Ϣ�б�
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
				return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
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
						request.setAttribute("operSign", "�޸ĳɹ�");
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
						request.setAttribute("operSign", "�޸ĳɹ�");
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
						request.setAttribute("operSign", "�޸ĳɹ�");
					} else if ("SYS_TREE_0000002102".equals(custType)) {
						forwardPage = "agentload";
						pis.updatePhoneinfo(dto);
						request.setAttribute("operSign", "�޸ĳɹ�");
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
						request.setAttribute("operSign", "�޸ĳɹ�");
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
						request.setAttribute("operSign", "�޸ĳɹ�");
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
						request.setAttribute("operSign", "�޸ĳɹ�");
					}
					return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
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
		// request.setAttribute("operSign", "�޸ĳɹ�");
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
		// return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
		// }
		// } catch (RuntimeException e) {
		// log.error("PortCompareAction : update ERROR");
		// e.printStackTrace();
		// return map.findForward("error");
		// }
		// }

		if (type.equals("delete")) {
			try {
				// custinfoService.delCustinfo((String)dto.get("cust_id"));//���������ɾ���˰���
				// ��������Ǳ��ɾ��
				// pis.delPhoneinfo("");
				String id = dto.get("cust_id").toString();
				pis.delPhoneinfo(id);
				request.setAttribute("operSign", "ɾ���ɹ�");
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
					return map.findForward(forwardPage);// Ĭ������ϯԱ��loadҳ
				}
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("load");
	}

	/**
	 * ����Ա�������
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
			request.setAttribute("operSign", "�޸ĳɹ�");
		return map.findForward("main");
	}

	/* ��ʼ���� */

	/**
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toLinkManQuery(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// ȡ����ͬ�ͻ����͵ļ��ϣ���loadҳ��ʾ
		List telNoteTypeList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteTypeList", telNoteTypeList);// ��ǰ̨JSP��ʾʱ����

		List user = pis.userQuery("select user_id from sys_user where (group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141')");
		request.setAttribute("user", user);

		return map.findForward("linkquery");
	}

	/**
	 * �ϴ���Ƭ
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
	 * �б�ҳ
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
		 // ȡ��list������
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

		// ȡ����ͬ�Ա�ļ��ϣ���loadҳ��ʾ
		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

//		// ȡ����ͬ�ͻ����͵ļ��ϣ���loadҳ��ʾ
//		List telNoteTypeList = cts.getLabelVaList("telNoteType");
//		telNoteTypeList.remove(0);
//		request.setAttribute("telNoteTypeList", telNoteTypeList);// ��ǰ̨JSP��ʾʱ����

		// ������ҵ
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// ��ҵ��ģ
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// ר�����
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// ��ҵ��Ϣ�б�
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);
		
		UserBean ub = (UserBean)request.getSession()
						.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		
//		// �û����
//		List leibielist = cts.getLabelVaList("yonghuleibie");
//		request.setAttribute("leibielist", leibielist);
//		
//		// ������ʽ
//		List fangshilist = cts.getLabelVaList("gongzuofangshi");
//		request.setAttribute("workmethodlist", fangshilist);
		
		// ����type�����ж�ִ������Ҫ�Ĳ���
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
			
			// ��ԭ������Ա�ı�����Ŀ��ʾ����Ŀ�б��У������޸�
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


		// ������ҵ
		List hangyeList = cts.getLabelVaList("chongshihangye");
		request.setAttribute("hangyelist", hangyeList);

		// ��ҵ��ģ
		List hangyeguimoList = cts.getLabelVaList("hangyeguimo");
		request.setAttribute("hangyeguimolist", hangyeguimoList);

		// ר�����
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		
//		// �û����
//		List leibielist = cts.getLabelVaList("yonghuleibie");
//		request.setAttribute("leibielist", leibielist);

		// ��ҵ��Ϣ�б�
		List hangyeby = cts.getLabelVaList("hangyeby");
		request.setAttribute("hangyebyList", hangyeby);
		
//		// ������ʽ
//		List fangshilist = cts.getLabelVaList("gongzuofangshi");
//		request.setAttribute("workmethodlist", fangshilist);

		if (type.equals("insert")) {
			
			///////////�û������ʼ(��ѡ��)
			String cust_way_by = "";
			String[] cust_way_bys = dto.getStrings("cust_way_bys");
			for(int i = 0; i < cust_way_bys.length; i++){
				cust_way_by += cust_way_bys[i]+",";
			}
			if(cust_way_bys.length > 0){//ȥ�����һ������
				cust_way_by = cust_way_by.substring(0, cust_way_by.length()-1);
			}
			dto.set("cust_way_by", cust_way_by);
			///////////�û���������
			
			///////////������ʽ����ʼ(��ѡ��)
			String cust_work_way = "";
			String[] cust_work_ways = dto.getStrings("cust_work_ways");
			for(int i = 0; i < cust_work_ways.length; i++){
				cust_work_way += cust_work_ways[i]+",";
			}
			if(cust_work_ways.length > 0){//ȥ�����һ������
				cust_work_way = cust_work_way.substring(0, cust_work_way.length()-1);
			}
			dto.set("cust_work_way", cust_work_way);
			///////////������ʽ�������
			
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
			return map.findForward("linkmanload");// Ĭ������ϯԱ��loadҳ
		}
		if (type.equals("update")) {
			
			///////////�û������ʼ(��ѡ��)
			String cust_way_by = "";
			String[] cust_way_bys = dto.getStrings("cust_way_bys");
			for(int i = 0; i < cust_way_bys.length; i++){
				cust_way_by += cust_way_bys[i]+",";
			}
			if(cust_way_bys.length > 0){//ȥ�����һ������
				cust_way_by = cust_way_by.substring(0, cust_way_by.length()-1);
			}
			dto.set("cust_way_by", cust_way_by);
			///////////�û���������
			
			///////////������ʽ����ʼ(��ѡ��)
			String cust_work_way = "";
			String[] cust_work_ways = dto.getStrings("cust_work_ways");
			for(int i = 0; i < cust_work_ways.length; i++){
				cust_work_way += cust_work_ways[i]+",";
			}
			if(cust_work_ways.length > 0){//ȥ�����һ������
				cust_work_way = cust_work_way.substring(0, cust_work_way.length()-1);
			}
			dto.set("cust_work_way", cust_work_way);
			///////////������ʽ�������
			
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
				request.setAttribute("operSign", "�޸ĳɹ�");
		}
		if (type.equals("delete")) {
			try {
				String id = dto.get("cust_id").toString();
				pis.delPhoneinfo(id);
				request.setAttribute("operSign", "ɾ���ɹ�");
					return map.findForward("linkmanload");// Ĭ������ϯԱ��loadҳ
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("linkmanload");
	}
	/* �������� */

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
