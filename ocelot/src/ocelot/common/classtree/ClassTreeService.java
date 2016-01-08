/**
 * @(#)ClassTreeService.java	 06/04/13
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package ocelot.common.classtree;




import java.util.List;

import ocelot.common.tools.LabelValueBean;
import ocelot.common.tree.TreeControlI;
import ocelot.common.tree.TreeNode;


/**<code>ClassTreeService</code> is interface 
 * which contains a series of action about <code>TreeControlI</code>
 * 
 * @author  yifei zhao
 * @version 06/04/13
 * @since   2.0
 *
 */
public interface ClassTreeService {

	
	public static String ROOT="treeRoot";
	/**
	 * @param root a <code>String</code> which is the tree's root id
	 * @return <code>TreeControlI</code> is a type of tree .
	 * @since 1.0
	 */
	public TreeControlI getTree(String root);
	/**
	 * @param root a <code>String</code> which is the tree's root id
	 * @return <code>TreeControlI</code> is a type of tree .
	 * @since 1.0
	 */
	public TreeControlI getTree(String root,String action,String target);
	
	/**
	 * @param root a <code>String</code> which is the tree's root id
	 * @return <code>TreeControlI</code> is a type of tree .
	 * @since 1.0
	 */
	public TreeControlI getTree(String root,boolean show);
	/**
	 * @param root a <code>String</code> which is the tree's root id
	 * @return <code>TreeControlI</code> is a type of tree .
	 * @since 1.0
	 */
	public TreeControlI getTree(String root,boolean show,String action,String target);
	
	/**
	 * @param nodes a <code>List<String></code> which is the tree's root id
	 * @return <code>TreeControlI</code> is a type of tree .
	 * @since 1.0
	 */
	public TreeControlI getTree(List<String> nodes);
	
	/**
	 * @param root a <code>String</code> which is the tree's root id
	 * @return <code>TreeControlI</code> is a type of tree .
	 * @since 1.0
	 */
	public TreeControlI getTreeByType(String type);
	
	
	
	/**
	 * 
	 * @param root a <code>String</code> which is the tree's id
	 * @return <code>List</code> contains a list of label value bean
	 *        
	 * @since 1.0
	 */
	public  List<LabelValueBean> getLabelList(String root);
	
	/**
	 * @param root a <code>String</code> which is the tree's nickname
	 * @return <code>List</code> contains a list of label value bean
	 *        
	 * @since 1.0
	 */
	public  List<LabelValueBean> getLabelVaList(String nickName);
	
	
	/**
	 * @param root a <code>String</code> which is the tree's id
	 * @return <code>List</code> contains a list of label value bean
	 *        
	 * @since 1.0
	 */
	public  List<LabelValueBean> getLabelList1st(String root);
	
	/**
	 * @param root a <code>String</code> which is the tree's nickname
	 * @return <code>List</code> contains a list of label value bean
	 *        
	 * @since 1.0
	 */
	public  List<LabelValueBean> getLabelVaList1st(String nickName);
	
	
	/**
	 * @param parent a <code>String</code> which is the tree's parent id
	 * @param child a <code>String</code> which is the tree's child id
	 * @return <code>boolean</code> if parent is child's parent node return true,
	 * 			else return false
	 *        
	 * @since 1.0
	 */
	
	
	public boolean isRela(String parent,String child);
	/**
	 * @param nickname a <code>String</code> which is the tree's unique name
	 * @return <code>String</code> is the tree's node label which proc_alias is param,
	 * 			
	 *        
	 * @since 1.0
	 */
	
	public String getvaluebyNickName(String nickname);
	/**
	 * @param nickname a <code>String</code> which is the tree's unique name
	 * @return <code>String</code> is the tree's node name which proc_alias is param,
	 * 			
	 *        
	 * @since 1.0
	 */
	
	public String getIdbyNickName(String nickname);
	/**
	 * @param id a <code>String</code> which is the tree's node id
	 * @return <code>String</code> is the tree's node label which id is param,
	 * 			
	 *        
	 * @since 1.0
	 */
	public String getvaluebyId(String id);
	/**
	 * @param id a <code>String</code> which is the tree's node id
	 * @return <code>String</code> is the tree's node proc_alias which id is param,
	 * 			
	 *        
	 * @since 1.0
	 */
	public String getNickNamebyId(String id);
	/**
	 * reload tree form <code>ClassTreeLoadService</code>
	 * 
	 * @since 1.0
	 */
	public void reload();
	
	/**
	 * @param id a <code>String</code> which is the tree's node id
	 * @return <code>Object</code> is the tree's node assistant object that contains assistant data
	 * 			
	 *        
	 * @since 1.0
	 */
	public Object getAssistantObject(String id);
	
	/**
	 * 从当前位置向上找节点
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<TreeNode> getParentTree(String id);
}
