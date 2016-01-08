/*
 * Created on 2005-3-22
 */
package excellence.common.tree;

public class TreeControlInfo implements TreeControlI{
    private TreeControl treeControl;
    private String key;//refer treeControl
    /**
     * @return Returns the treeControl.
     */
    public TreeControl getTreeControl() {
        return treeControl;
    }
    /**
     * @param treeControl The treeControl to set.
     */
    public void setTreeControl(TreeControl treeControl) {
        this.treeControl = treeControl;
    }

    /**
     * @return Returns the key.
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key The key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }
    public TreeControlInfo(TreeControl treeControl,String key) {
        this.treeControl = treeControl;
        this.key = key;
    }
    
    public static void main(String[] args) {
    }
	public  Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		TreeControlInfo tci=new TreeControlInfo((TreeControl)this.treeControl.clone(),this.key);
		return tci;
	}
}
