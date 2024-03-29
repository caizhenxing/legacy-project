package et.po;



/**
 * SysTree generated by MyEclipse - Hibernate Tools
 */

public class SysTree  implements java.io.Serializable {


    // Fields    

     private String id;
     private String parentId;
     private String type;
     private String procAlias;
     private String label;
     private String layerOrder;
     private String handleNode;
     private String tagShow;
     private String tagSys;
     private String remark;
     private String tagDel;
     private String isSys;


    // Constructors

    /** default constructor */
    public SysTree() {
    }

	/** minimal constructor */
    public SysTree(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public SysTree(String id, String parentId, String type, String procAlias, String label, String layerOrder, String handleNode, String tagShow, String tagSys, String remark, String tagDel, String isSys) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.procAlias = procAlias;
        this.label = label;
        this.layerOrder = layerOrder;
        this.handleNode = handleNode;
        this.tagShow = tagShow;
        this.tagSys = tagSys;
        this.remark = remark;
        this.tagDel = tagDel;
        this.isSys = isSys;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getProcAlias() {
        return this.procAlias;
    }
    
    public void setProcAlias(String procAlias) {
        this.procAlias = procAlias;
    }

    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLayerOrder() {
        return this.layerOrder;
    }
    
    public void setLayerOrder(String layerOrder) {
        this.layerOrder = layerOrder;
    }

    public String getHandleNode() {
        return this.handleNode;
    }
    
    public void setHandleNode(String handleNode) {
        this.handleNode = handleNode;
    }

    public String getTagShow() {
        return this.tagShow;
    }
    
    public void setTagShow(String tagShow) {
        this.tagShow = tagShow;
    }

    public String getTagSys() {
        return this.tagSys;
    }
    
    public void setTagSys(String tagSys) {
        this.tagSys = tagSys;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTagDel() {
        return this.tagDel;
    }
    
    public void setTagDel(String tagDel) {
        this.tagDel = tagDel;
    }

    public String getIsSys() {
        return this.isSys;
    }
    
    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }
   








}