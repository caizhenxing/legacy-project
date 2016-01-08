/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 28, 20071:29:40 PM
 * 文件名：TestSearchMeta.java
 * 制作者：zhaoyf
 * 
 */
package test.tools.auto.create;

import junit.framework.TestCase;

import com.zyf.tools.auto.create.search.SearchMeta;

/**
 * @author zhaoyf
 *
 */
public class TestSearchMeta extends TestCase {

	/**
	 * Test method for {@link com.zyf.tools.auto.create.search.SearchMeta#getTag()}.
	 */
	public void testGetTag() {
		SearchMeta sm=new SearchMeta();
//		sm.setSearchType(sm.SEARCH_TYPE_DEPT);
//		sm.setCode("AA.BB.CC");
//		sm.setName("AA.BB.DD");
//		sm.setDisPlayName("测试部门标签");
//		sm.setValueDefault("${theForm}");
		
//		sm.setSearchType(sm.SEARCH_TYPE_SELECT);
//		sm.setName("AA.BB.CC");
//		sm.setSelectSubSysCode("HR");
//		sm.setDisPlayName("测试下拉框标签");
//		sm.setSelectModuleCode("MINZ");
		
		
//		sm.setSearchType(sm.SEARCH_TYPE_TEXT);
//		sm.setName("AA.BB.CC");
//		sm.setOper(">=");
//		sm.setDisPlayName("测试文本标签");
//		sm.setValueDefault("${theForm}");
//		sm.setInputClass("colspan='3'");
//		sm.setStyle("style=\"ddddd\"");
		
		
		sm.setSearchType(sm.SEARCH_TYPE_TIME);
		sm.setName("AA.BB.Cc");
		sm.setDisPlayName("测试时间标签");
		sm.setValueDefault("${theForm}");
		
		System.out.println(sm.getTag());
		
	}

}
