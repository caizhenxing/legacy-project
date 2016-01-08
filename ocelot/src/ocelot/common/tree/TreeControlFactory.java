/*
 * Created on 2005-3-22
 */
package ocelot.common.tree;

import java.util.List;

import org.apache.log4j.Logger;

public class TreeControlFactory {
    private static Logger log=Logger.getLogger(TreeControlFactory.class.getName());
    /*
     * keyInSession:如其名称
     * 
     */
     public static TreeControlI creator(String keyInSession,TreeInfoI tii){
    	TreeControl treeControl= new TreeControl(tii.getRoot());
		addTheNode(treeControl.getRoot(),tii.getTreeNodeList());
		TreeControlI tci = new TreeControlInfo(treeControl,keyInSession);
    	return tci;
    }
    private static void addTheNode(TreeControlNode treeControlNode,List list){
    	/*
    	 * for debug
    	 */
	    //print(treeControlNode);
    	
		//list IS node SET
		String name=treeControlNode.getName();
		for(int i=0;i<list.size();i++){
			TreeNodeI tni=(TreeNodeI)list.get(i);
			//加节点
			if(name.equals(tni.getParentName())){//
				TreeControlNode tcn =
					new TreeControlNode(tni.getName(),
										tni.getIcon(),
										tni.getLabel(),
										tni.getAction(),
										tni.getTarget(),
										tni.isExpanded(),
										tni.getDomain());
				treeControlNode.addChild(tcn);
				addTheNode(tcn,list);
			}
		}
	}
    public static void print(TreeControlNode treeControlNode){
	    String name=treeControlNode.getName();
		String label = treeControlNode.getLabel();
		String icon = treeControlNode.getIcon();
		String target = treeControlNode.getTarget();
		String action = treeControlNode.getAction();
		StringBuffer sb = new StringBuffer("name:["+name+"]");
		sb.append("---");sb.append("label:["+label+"]");
		sb.append("---");sb.append("icon:["+icon+"]");
		sb.append("---");sb.append("target:["+target+"]");
		sb.append("---");sb.append("action:["+action+"]");
		log.debug(sb.toString());
		
	}
    public static void main(String[] args) {
        /*
        InitLog4j il = new InitLog4j();
        TreeControlFactory.creator(new DbSys(),
                Constants.CONFIG_File,
                Constants.TREE_NICK_NAME_MOD);
                */
    }
}
