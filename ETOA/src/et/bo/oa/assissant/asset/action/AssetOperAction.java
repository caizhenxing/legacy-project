package et.bo.oa.assissant.asset.action;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


import et.bo.common.ListValueService;
import et.bo.oa.assissant.asset.service.AssetOperService;
import et.bo.oa.assissant.asset.service.AssetService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class AssetOperAction extends BaseAction {

	private AssetService assetService =null;
	private AssetOperService assetOperService =null;
	private ListValueService listValueService =null;
	private KeyService ks = null;
	
	/**
	 * @return Returns the listValueService.
	 */
	public ListValueService getListValueService() {
		return listValueService;
	}

	/**
	 * @param listValueService The listValueService to set.
	 */
	public void setListValueService(ListValueService listValueService) {
		this.listValueService = listValueService;
	}
	
	private boolean isOwner(HttpServletRequest request, String userId)
	{
		return false;
	}

	public ActionForward toMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		return new ActionForward("/oa/assissant/asset/manage/main.jsp");
    }
	
	public ActionForward toOperMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		return new ActionForward("/oa/assissant/asset/manage/operMain.jsp");
    }
	
	
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
//		List bl =this.listValueService.getLabelValue("AssetsOper","operId","operId","-1");
		List bl =this.assetOperService.listLVBatch();
		request.setAttribute("bl",bl);
		return new ActionForward("/oa/assissant/asset/manage/toSearch.jsp");
    }
	
	public ActionForward toOperSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		List bl =this.assetOperService.listLVBatch();
		request.setAttribute("bl",bl);
		return new ActionForward("/oa/assissant/asset/manage/toOperSearch.jsp");
    }
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("AssetInfoTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(3);
        List l = this.assetService.list(dform,pageInfo);
//      
      int size = this.assetService.listSize(dform,pageInfo);
//      
      pageInfo.setRowCount(size);
      pageInfo.setQl(dform);
      request.setAttribute("list",l);
      PageTurning pt = new PageTurning(pageInfo,"/ETOA/",mapping,request);
      request.getSession().setAttribute("AssetInfoTurning",pt);		
//		
		return new ActionForward("/oa/assissant/asset/manage/infoSearchResult.jsp");
    }
	
	public ActionForward searchOper(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		//TODO
		List dl =this.listValueService.getLabelValue("SysDepartment","name","id","-1");
		request.setAttribute("dl",dl);
		//TODO
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("AssetOperTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(3);
        List l = this.assetOperService.list(dform,pageInfo);
//      
        int size = this.assetOperService.listSize(dform,pageInfo);
//      
	    pageInfo.setRowCount(size);
	    pageInfo.setQl(dform);
	    request.setAttribute("list",l);
	    PageTurning pt = new PageTurning(pageInfo,"/ETOA/",mapping,request);
	    request.getSession().setAttribute("AssetOperTurning",pt);		
//		
		return new ActionForward("/oa/assissant/asset/manage/operSearchResult.jsp");
    }
	
	public ActionForward toAddAssetOper(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		
		return new ActionForward("/oa/assissant/asset/manage/info.jsp");
    }
	
	public ActionForward addAssetOper(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		boolean flag =false;
		try {
			dform.set("sign","1");
			String operCode =(String)dform.get("operCode");
			flag =this.assetOperService.existOperCode(operCode);
			if(flag)
			{
				return new ActionForward("/oa/assissant/asset/message/error.jsp");
			}
			//TODO
			String key =ks.getNext("assets_oper");
			dform.set("operId",key);
			flag =this.assetOperService.insert(dform);
			if(flag)
			{
				request.setAttribute("AssetPC",key);
				return new ActionForward("/oa/assissant/asset/message/assetOperSuccess.jsp");
			}
			else
				return new ActionForward("/oa/assissant/asset/message/error.jsp");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ActionForward("/oa/assissant/asset/message/error.jsp");
		}
		
    }
	
	public ActionForward addAssetInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		try {
			//TODO
			IBaseDTO odto =this.assetOperService.load((String)dform.get("assetsOper"));
			int onum =new Integer(odto.get("operassetsNum").toString()).intValue();
			int inum =new Integer(dform.get("assetsNum").toString());
			int numInDb =this.assetService.howMany((String)dform.get("assetsOper"));
			int rnum =onum-(inum+numInDb);
			if(rnum <0)
			{
				ActionMessages errors =new ActionMessages();
				errors.add("errorValue",new ActionMessage("error value of assetsNum"));
				saveErrors(request,errors);
				return new ActionForward("/oa/assissant/asset/message/error.jsp");
			}
			//TODO
			boolean flag =this.assetService.insert(dform);
			if(flag)
			{
				request.setAttribute("rnum",rnum);
				request.setAttribute("assetsOper",(String)dform.get("assetsOper"));
				return new ActionForward("/oa/assissant/asset/message/assetInfoSuccess.jsp");
			}
			else
				return new ActionForward("/oa/assissant/asset/message/error.jsp");
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ActionForward("/oa/assissant/asset/message/error.jsp");
		}
    }
	
	public ActionForward toAssetOperLoad(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String type =request.getParameter("type");
		request.setAttribute("type",type);
		if("i".equals(type))
		{	
			String time =TimeUtil.getTheTimeStr(new Date(),"yyyy-MM-dd");
			dform.set("operTime",time);
			return new ActionForward("/oa/assissant/asset/manage/assetOperInfo.jsp");
		}
		else if("v".equals(type))
		{
			String did =request.getParameter("did");
			IBaseDTO dto =this.assetOperService.load(did);
			request.setAttribute(mapping.getName(),dto);
			return new ActionForward("/oa/assissant/asset/manage/assetOperInfo.jsp");
		}
		else if("u".equals(type) || "d".equals(type))
		{
			String did =request.getParameter("did");
			IBaseDTO dto =this.assetOperService.load(did);
			request.setAttribute(mapping.getName(),dto);
			return new ActionForward("/oa/assissant/asset/manage/assetOperInfo.jsp");
		}
		return new ActionForward("/oa/assissant/asset/manage/assetOperInfo.jsp");
    }
	
	/**
	 * 资产信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAssetInfoLoad(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String type =request.getParameter("type");
		request.setAttribute("type",type);
		
//		List bl =this.listValueService.getLabelValue("AssetsOper","operId","operId","-1");
		List bl =this.assetOperService.listLVBatch();
		request.setAttribute("bl",bl);
		List dl =this.listValueService.getLabelValue("SysDepartment","name","id","-1");
		request.setAttribute("dl",dl);
		if("i".equals(type))
		{			
			return new ActionForward("/oa/assissant/asset/manage/assetInfo.jsp");
		}
		else if("v".equals(type))
		{				
			String did =request.getParameter("did");
			IBaseDTO dto =this.assetService.load(did);
			request.setAttribute(mapping.getName(),dto);
			
			return new ActionForward("/oa/assissant/asset/manage/assetInfo.jsp");
		}
		
		return new ActionForward("/oa/assissant/asset/manage/assetInfo.jsp");
    }
	/**
	 * 资产数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkBatchNum(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;		
		String AssetPC =request.getParameter("AssetPC");
		if(null ==AssetPC || "".equals(AssetPC))
			return new ActionForward("/oa/assissant/asset/assetsOperAction.do?method=toAssetInfoLoad");
		IBaseDTO odto =this.assetOperService.load(AssetPC);
		//
		int onum =new Integer(odto.get("operassetsNum").toString()).intValue();
		int inum =this.assetService.howMany(AssetPC);
		int rnum =onum -inum;
//		
		if(null !=AssetPC && !"".equals(AssetPC))
		{
			dform.set("assetsOper",AssetPC);
			dform.set("assetsName",odto.get("operName"));
			request.setAttribute("show","show");
			
			request.setAttribute("onum",onum);
			request.setAttribute("inum",inum);
			request.setAttribute("rnum",rnum);
			if(rnum <=0)
			{
				return new ActionForward("/oa/assissant/asset/assetsOperAction.do?method=toAssetInfoLoad");
			}
		}
		return new ActionForward("/oa/assissant/asset/assetsOperAction.do?method=toAssetInfoLoad&type=i");
    }

	public ActionForward toOperAsset(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String time =TimeUtil.getTheTimeStr(new Date(),"yyyy-MM-dd");		
		//TODO
		String did =request.getParameter("did");
		IBaseDTO dto =this.assetService.load(did);
		//TODO
		IBaseDTO odto =this.assetService.getOperDtoByInfoId(did);
		dto.set("assetsPrice",odto.get("assetsPrice"));
//		dto.set("operassetsNum",dto.get("assetsNum"));//
		dto.set("operCode",odto.get("operCode"));
		dto.set("operName",odto.get("operName"));
		dto.set("operTime",time);
		request.setAttribute(mapping.getName(),dto);
		//TODO
		List dl =this.listValueService.getLabelValue("SysDepartment","name","id","-1");
		request.setAttribute("dl",dl);
		return new ActionForward("/oa/assissant/asset/manage/operAsset.jsp");
    }
	
	public ActionForward operAsset(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		int inum =new Integer(dform.get("assetsNum").toString()).intValue();
		int onum =new Integer(dform.get("operassetsNum").toString()).intValue();
		int rnum =inum -onum;
		//TODO 数值验证
		if(onum <=0 || rnum <0)
		{
			return new ActionForward("/oa/assissant/asset/message/error.jsp");
		}
		boolean flag =false;
		try {
			String operType =(String)dform.get("operType");
			String assetType =(String)dform.get("assetsType");
			
			//TODO 操作类型
			if("fenpei".equals(operType))
			{				
				//TODO 资产类型
				if("-1".equals(assetType))
				{
					dform.set("assetsNum",new Integer(rnum).toString());
					flag =this.assetService.update(dform);
					flag =this.assetOperService.insert(dform);
					//TODO 
					if(flag)
						return new ActionForward("/oa/assissant/asset/message/success.jsp");
					else
						return new ActionForward("/oa/assissant/asset/message/error.jsp");		
					
				}
				else
				{
					flag =this.assetService.update(dform);
					flag =this.assetOperService.insert(dform);
					if(flag)
						return new ActionForward("/oa/assissant/asset/message/success.jsp");
					else
						return new ActionForward("/oa/assissant/asset/message/error.jsp");
				}
			}
			else if("maichu".equals(operType) || "baofei".equals(operType) || "zhuanchu".equals(operType))
			{
				String infoId =(String)dform.get("assetsId");
				flag =this.assetService.delete(infoId);
				flag =this.assetOperService.insert(dform);
				if(flag)
					return new ActionForward("/oa/assissant/asset/message/success.jsp");
				else
					return new ActionForward("/oa/assissant/asset/message/error.jsp");
			}
			else
			{
				return new ActionForward("/oa/assissant/asset/message/error.jsp");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ActionForward("/oa/assissant/asset/message/error.jsp");
		}
    }
	/**
	 * @return Returns the assetOperService.
	 */
	public AssetOperService getAssetOperService() {
		return assetOperService;
	}

	/**
	 * @param assetOperService The assetOperService to set.
	 */
	public void setAssetOperService(AssetOperService assetOperService) {
		this.assetOperService = assetOperService;
	}

	/**
	 * @return Returns the assetService.
	 */
	public AssetService getAssetService() {
		return assetService;
	}

	/**
	 * @param assetService The assetService to set.
	 */
	public void setAssetService(AssetService assetService) {
		this.assetService = assetService;
	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
}
