/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 28, 20071:29:40 PM
 * �ļ�����TestSearchMeta.java
 * �����ߣ�zhaoyf
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
//		sm.setDisPlayName("���Բ��ű�ǩ");
//		sm.setValueDefault("${theForm}");
		
//		sm.setSearchType(sm.SEARCH_TYPE_SELECT);
//		sm.setName("AA.BB.CC");
//		sm.setSelectSubSysCode("HR");
//		sm.setDisPlayName("�����������ǩ");
//		sm.setSelectModuleCode("MINZ");
		
		
//		sm.setSearchType(sm.SEARCH_TYPE_TEXT);
//		sm.setName("AA.BB.CC");
//		sm.setOper(">=");
//		sm.setDisPlayName("�����ı���ǩ");
//		sm.setValueDefault("${theForm}");
//		sm.setInputClass("colspan='3'");
//		sm.setStyle("style=\"ddddd\"");
		
		
		sm.setSearchType(sm.SEARCH_TYPE_TIME);
		sm.setName("AA.BB.Cc");
		sm.setDisPlayName("����ʱ���ǩ");
		sm.setValueDefault("${theForm}");
		
		System.out.println(sm.getTag());
		
	}

}
