package ocelot.common.classtree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ocelot.common.classtree.ClassTreeLoadAssistantService;
import ocelot.common.classtree.ClassTreeLoadService;
import ocelot.common.classtree.ClassTreeService;
import ocelot.common.tools.LabelValueBean;
import ocelot.common.tree.TreeControlFactory;
import ocelot.common.tree.TreeControlI;
import ocelot.common.tree.TreeControlNode;
import ocelot.common.tree.TreeInLove;
import ocelot.common.tree.TreeNode;





 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class ClassTreeServiceImpl implements ClassTreeService {

	
	
	private List label=null;
	private List<TreeNode> treeList=null;
	private List<TreeNode> treeListAll=null;
	private HashMap<String,TreeNode> typeMap=null;
	private HashMap<String,TreeNode> treeId=null;
	private HashMap<String ,TreeNode> treeNickname=null;
	
	private static String space=" ©¦ ";
	private static String labelRoot="#©Ð";
	private static String labelBegin="©Ð";
	//String middleHead="©¦©°";//©³
	private static String middleCenter="©À";//©Ç
	private static String middleTail="©¸";//©»
	private static String middle="©¦";
	
	private ClassTreeLoadService ctls=null;
	private ClassTreeLoadAssistantService ctlas=null;

	public TreeControlI getTree(String root) {
		// TODO Auto-generated method stub
		
		String sessionName="";
		if(treeList==null)
			reload();
		TreeInLove til=new TreeInLove();
		til.setL(treeList);
		TreeControlNode tcn=new TreeControlNode(root,null,null,null,null,true,"");
		til.setTcn(tcn);
		TreeControlI tci=TreeControlFactory.creator(sessionName,til);
		return tci;
	}
	public  List getLabelList(String root)
	{
		if(treeList==null)
			reload();
		
		label=new ArrayList();
		recursion(root,middleCenter);
		return label;
	}
	/*private void load()
	{
		
		String target="bottomm";
		String gicon=Constants.GICON;
		String uicon=Constants.UICON;
		String domain="";
		String action="";
		boolean exp=Constants.EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=baseDao.findEntity(query);
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			CommonClass sm=(CommonClass)commonClass[i];
			tn.setIcon(Constants.NICON);
			tn.setExpanded(exp);
			tn.setLabel(sm.getName());
			tn.setName(sm.getId().toString());
			tn.setParentName(sm.getParentId().toString());
			tn.setTarget(target);
			tn.setDomain(domain);
			treeList.add(tn);
		}
	}*/
	private boolean createNicknameIndex()
	{
		if(treeList==null)
			reload();
			treeNickname=new HashMap<String ,TreeNode>();
			for(TreeNode tn :treeList)
			{
				//if(tn.getParentName().equals(ClassTreeService.ROOT))
				treeNickname.put(tn.getNickname(),tn);
			}
			return true;
	}
	private boolean createIdIndex()
	{
		if(treeList==null)
			reload();
		treeId=new HashMap<String ,TreeNode>();
		for(TreeNode tn :treeList)
		{
			treeId.put(tn.getName(),tn);
		}
		return true;
	}
	private boolean createTypeMap()
	{
		if(treeList==null)
			reload();
		typeMap=new HashMap<String ,TreeNode>();
		for(TreeNode tn :treeList)
		{
			if(tn.getParentName().equals(ROOT));
			typeMap.put(tn.getDomain(),tn);
		}
		return true;
	}
	private void recursion (String paid,String labe)
	{
		try
		{
		
		List temp=new ArrayList();
		int size=treeList.size();
		for(int i=0;i<size;i++)
		{
			TreeNode tn=(TreeNode)treeList.get(i);
			if(tn.getParentName().equals(paid))
				temp.add(tn);
		}
		int tempsize=temp.size();
		for(int i=0;i<tempsize;i++)
		{
			String val=((TreeNode)temp.get(i)).getName();
			String lab=((TreeNode)temp.get(i)).getLabel();
			if(lab==null)
				lab=((TreeNode)temp.get(i)).getDomain();
			StringBuffer sb=new StringBuffer();
			sb.append(labe);
			sb.append(lab);
			LabelValueBean lvb=new LabelValueBean(sb.toString(),val);
			
			label.add(lvb);
			
			recursion(val,space+labe);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


	


	public boolean isRela(String parent, String child) {
		// TODO Auto-generated method stub
		if(treeId==null)
		{
			if(!createNicknameIndex())
				return false;
		}
		if(parent.equals(child))
			return false;
		TreeNode tn=treeId.get(child);
		if(tn==null)
			return false;
		if(tn.getParentName()==null)
			return false;
		if(!tn.getParentName().equals(parent))	
			return isRela(parent,tn.getParentName());
		return true;
		
	}


	public String getvaluebyNickName(String nickname) {
		// TODO Auto-generated method stub
		if(treeNickname==null)
		{
			treeNickname=new HashMap<String,TreeNode>();
			if(!createNicknameIndex())
				return null;
		}
		TreeNode tn=treeNickname.get(nickname);
		if(tn==null)
		return null;
		return tn.getLabel();
	}
	public void reload() {
		// TODO Auto-generated method stub
		
		treeListAll=ctls.load();
		treeList=show(treeListAll);
		Collections.sort(treeListAll);
		Collections.sort(treeList);
		createNicknameIndex();
		createIdIndex();
		createTypeMap();
	}
	private List<TreeNode> show(List<TreeNode> t)
	{
		List<TreeNode> temp=new ArrayList<TreeNode>();
		for(TreeNode tn:t)
		{
			if(tn.isTagShow())
				temp.add(tn);
		}
		return temp;
	}
	public ClassTreeLoadService getCtls() {
		return ctls;
	}
	public void setCtls(ClassTreeLoadService ctls) {
		this.ctls = ctls;
	}
	public String getvaluebyId(String id) {
		// TODO Auto-generated method stub
		if(treeId==null)
		{
			treeId=new HashMap<String,TreeNode>();
			if(!createIdIndex())
				return "";
		}
		TreeNode tn=treeId.get(id);
		if(tn==null)
		return "";
		return tn.getLabel();
	}
	public Object getAssistantObject(String id) {
		// TODO Auto-generated method stub
		return ctlas.getAssistantById(id);
	}
	public ClassTreeLoadAssistantService getCtlas() {
		return ctlas;
	}
	public void setCtlas(ClassTreeLoadAssistantService ctlas) {
		this.ctlas = ctlas;
	}
	public TreeControlI getTree(List<String> nodes) {
		// TODO Auto-generated method stub
		String sessionName="";
		TreeNode root=null;
		if(treeList==null)
			reload();
		if(treeId==null)
			createIdIndex();
		List<TreeNode> temp=new ArrayList<TreeNode>();
		for(String s : nodes)
		{

			TreeNode tn=treeId.get(s);
			if(tn==null)
				continue;
			if(!tn.getParentName().equals(ROOT))
			temp.add(tn);
			else
				root=tn;
		}
		
		TreeInLove til=new TreeInLove();
		Collections.sort(temp);
		til.setL(temp);
		TreeControlNode tcn=new TreeControlNode(root);
		til.setTcn(tcn);
		TreeControlI tci=TreeControlFactory.creator(sessionName,til);
		return tci;
		
	}
	public TreeControlI getTreeByType(String type) {
		// TODO Auto-generated method stub
		String sessionName="";
		if(typeMap==null)
			createTypeMap();
		TreeNode root=typeMap.get(type);
		TreeInLove til=new TreeInLove();
		til.setL(treeList);
		TreeControlNode tcn=new TreeControlNode(root);
		til.setTcn(tcn);
		TreeControlI tci=TreeControlFactory.creator(sessionName,til);
		return tci;
	}
	public List<LabelValueBean> getLabelVaList(String nickName) {
		// TODO Auto-generated method stub
		if(treeList==null)
			reload();
		if(treeNickname==null)
		{
			treeNickname=new HashMap<String,TreeNode>();
			if(!createNicknameIndex())
				return new ArrayList<LabelValueBean>();
		}
		TreeNode tn=treeNickname.get(nickName);
		if(tn==null)
			return new ArrayList<LabelValueBean>();
		String id=tn.getName();
		label=new ArrayList();
		recursion(id,middleCenter);
		return label;
	}
	public TreeControlI getTree(String root, boolean show) {
		// TODO Auto-generated method stub
		List<TreeNode> temp=null;
		if(show)
		{
			temp=treeListAll;
		}
		else
		{
			temp=treeList;
		}
		String sessionName="";
		if(temp==null)
		{
			reload();
			if(show)
			{
				temp=treeListAll;
			}
			else
			{
				temp=treeList;
			}
		}
		TreeInLove til=new TreeInLove();
		til.setL(temp);
		TreeControlNode tcn=new TreeControlNode(root,null,null,null,null,true,"");
		til.setTcn(tcn);
		TreeControlI tci=TreeControlFactory.creator(sessionName,til);
		return tci;
		
	}
	public List<TreeNode> getParentTree(String id) {
		// TODO Auto-generated method stub
		List<TreeNode> l=new LinkedList<TreeNode>();
		if(treeId==null)
		{
			treeId=new HashMap<String,TreeNode>();
			if(!createIdIndex())
				return l;
		}
		TreeNode tn=treeId.get(id);
		if(tn==null)
			return l;
		while(!tn.getParentName().equals(this.ROOT))
		{
			l.add(0,tn);
			tn=treeId.get(tn.getParentName());
		}
		return l;
	}
	public TreeControlI getTree(String root, String action,String target) {
		// TODO Auto-generated method stub
		String sessionName="";
		if(treeList==null)
			reload();
		TreeInLove til=new TreeInLove();
		til.setL(changeAction(treeList,action,target));
		TreeControlNode tcn=new TreeControlNode(root,null,null,null,null,true,"");
		til.setTcn(tcn);
		TreeControlI tci=TreeControlFactory.creator(sessionName,til);
		return tci;
	}
	private List<TreeNode> changeAction(List<TreeNode> tl,String action,String target)
	{
		List<TreeNode> l=new ArrayList<TreeNode>(tl.size());
		
		for(TreeNode tn:tl)
		{
			TreeNode tn1=new TreeNode(tn);
			if(action.indexOf('@')==-1)
			tn1.setAction(action+tn.getName());
			else
				tn.setAction(action.replaceFirst("@", tn.getName()));
			tn1.setTarget(target);
			l.add(tn1);
		}
		return l;
	}
	public TreeControlI getTree(String root, boolean show, String action,String target) {
		// TODO Auto-generated method stub
		List<TreeNode> temp=null;
		if(show)
		{
			temp=treeListAll;
		}
		else
		{
			temp=treeList;
		}
		String sessionName="";
		if(temp==null)
		{
			reload();
			if(show)
			{
				temp=treeListAll;
			}
			else
			{
				temp=treeList;
			}
		}
		TreeInLove til=new TreeInLove();
		til.setL(changeAction(temp,action,target));
		TreeControlNode tcn=new TreeControlNode(root,null,null,null,null,true,"");
		til.setTcn(tcn);
		TreeControlI tci=TreeControlFactory.creator(sessionName,til);
		return tci;
	}
	public List<LabelValueBean> getLabelList1st(String root) {
		// TODO Auto-generated method stub
		if(treeList==null)
			reload();
		
		List<LabelValueBean> temp=new ArrayList<LabelValueBean>();
		int size=treeList.size();
		for(int i=0;i<size;i++)
		{
			TreeNode tn=(TreeNode)treeList.get(i);
			if(tn.getParentName().equals(root))
				temp.add(new LabelValueBean(tn.getLabel(),tn.getName()));
		}
		return temp;
	}
	public List<LabelValueBean> getLabelVaList1st(String nickName) {
		// TODO Auto-generated method stub
		if(treeList==null)
			reload();
		if(treeNickname==null)
		{
			treeNickname=new HashMap<String,TreeNode>();
			if(!createNicknameIndex())
				return new ArrayList<LabelValueBean>();
		}
		TreeNode tn=treeNickname.get(nickName);
		if(tn==null)
			return new ArrayList<LabelValueBean>();
		String id=tn.getName();
		List<LabelValueBean> temp=new ArrayList<LabelValueBean>();
		int size=treeList.size();
		for(int i=0;i<size;i++)
		{
			TreeNode tn1=(TreeNode)treeList.get(i);
			if(tn.getParentName().equals(id))
				temp.add(new LabelValueBean(tn1.getLabel(),tn1.getName()));
		}
		return temp;
	}
	public String getIdbyNickName(String nickname) {
		// TODO Auto-generated method stub
		if(treeNickname==null)
		{
			treeNickname=new HashMap<String,TreeNode>();
			if(!createNicknameIndex())
				return null;
		}
		TreeNode tn=treeNickname.get(nickname);
		if(tn==null)
		return null;
		return tn.getName();
	}
	public String getNickNamebyId(String id) {
		// TODO Auto-generated method stub
		if(treeId==null)
		{
			treeId=new HashMap<String,TreeNode>();
			if(!createIdIndex())
				return "";
		}
		TreeNode tn=treeId.get(id);
		if(tn==null)
		return "";
		return tn.getNickname();
	}


}
