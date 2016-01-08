package excellence.common.tree;

public class TreeNode implements TreeNodeI, Cloneable, Comparable {
	// node interface
	private String order;

	private Object extender;

	private String name;

	private String icon;

	private String label;

	private String action;

	private boolean expanded;

	private String target;

	private String domain;

	private String nickname;

	private boolean tagShow;

	// nod other info
	private String parentName;

	public TreeNode() {
		super();
	}

	public TreeNode(TreeNode tn) {
		super();
		this.action = tn.action;
		this.domain = tn.domain;
		this.expanded = tn.expanded;
		this.icon = tn.icon;
		this.label = tn.label;
		this.name = tn.name;
		this.parentName = tn.parentName;
		this.target = tn.target;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (order == null)
			this.order = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		TreeNode tn = new TreeNode(this);
		return tn;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Object getExtender() {
		return extender;
	}

	public void setExtender(Object extender) {
		this.extender = extender;
	}

	public boolean isTagShow() {
		return tagShow;
	}

	public void setTagShow(boolean tagShow) {
		this.tagShow = tagShow;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		TreeNode tn = (TreeNode) o;
		return this.order.compareTo(tn.getOrder());
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}