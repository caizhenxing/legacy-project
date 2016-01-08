package et.bo.caseinfo.casetype;

import java.util.Map;

import et.bo.caseinfo.casetype.service.CaseTypeService;


import excellence.framework.base.container.SpringRunningContainer;

public class CaseTypeDWR {

	CaseTypeService cts = null;
	
	public CaseTypeDWR(){
		cts= (CaseTypeService)SpringRunningContainer.getInstance().getBean("CaseService");		
	}
	
	public Map addBigType(String bigType){
		return cts.addBigType(bigType);
	}	
	public Map getBigType(){
		return cts.getBigType();
	}
	public Map addSmallType(String bigType,String smallType){
		return cts.addSmallType(bigType,smallType);
	}
	public Map getSmallTypeByBigType(String bigType){
		return cts.getSmallTypeByBigType(bigType);
	}
	public Map updateBigType(String oldBigType,String newBigType){
		return cts.updateBigType(oldBigType,newBigType);
	}
	public int deleteBigType(String bigType){
		return cts.deleteBigType(bigType);
	}
	public Map updateSmallType(String id,String bigType,String smallType){
		return cts.updateSmallType(id,bigType,smallType);
	}
	public int deleteSmallType(String id){
		return cts.deleteSmallType(id);
	}
	public Map getSmallTypeByBigType_app(String bigType){
		return cts.getSmallTypeByBigType_app(bigType);
	}

}
