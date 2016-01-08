package test.tools.auto.create;

import junit.framework.TestCase;

import com.zyf.tools.auto.create.info.InfoMeta;

public class InfoMetaTest extends TestCase {

	public void testGetTag() {
		InfoMeta im=new InfoMeta();
		im.setCode("bean.tblHrDept.id");
		im.setDisplayName("部门/班组");
		//im.setInfoType(im.INFO_TYPE_DEPT);
		//im.setInfoType(im.INFO_TYPE_SELECT);
		//im.setInfoType(im.INFO_TYPE_TEXT);
		im.setInfoType(im.INFO_TYPE_TIME);
		im.setIsMust("true");
		im.setName("bean.tblHrDept.deptName");
		im.setPattern("yyyy-MM-dd");
		im.setSelectModuleCode("GWEI");
		im.setSelectSubSysCode("HR");
		im.setStyle("aaa");
		im.setValueDefault("theForm");
		im.setViewClass("class=\"attribute\"");
		im.setWidth(1);
		im.setValid("Require|Double");
		im.setValidMsg("必须填写部门");
		try {
			System.out.println(im.getTag());
			System.out.println(im.validate());
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}
	
}
