package excellence.common.tree;

import java.util.ArrayList;
import java.util.List;




public class TreeInLove implements TreeInfoI {
	private List l=new ArrayList();
	private TreeControlNode tcn=null;
	
	
	public void setL(List l) {
		this.l = l;
	}

	public void setTcn(TreeControlNode tcn) {
		this.tcn = tcn;
	}

	public List getTreeNodeList() {
		// TODO Auto-generated method stub
		return l;
	}

	public TreeControlNode getRoot() {
		// TODO Auto-generated method stub
		return tcn;
	}

}
