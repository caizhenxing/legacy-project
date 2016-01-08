package et.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * WorkParentPlan generated by MyEclipse - Hibernate Tools
 */

public class WorkParentPlan  implements java.io.Serializable {


    // Fields    

     private String id;
     private String title;
     private String createUser;
     private Date beginDate;
     private Date endDate;
     private String sign;
     private Date createDate;
     private String remark;
     private Set workNewPlans = new HashSet(0);


    // Constructors

    /** default constructor */
    public WorkParentPlan() {
    }

	/** minimal constructor */
    public WorkParentPlan(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public WorkParentPlan(String id, String title, String createUser, Date beginDate, Date endDate, String sign, Date createDate, String remark, Set workNewPlans) {
        this.id = id;
        this.title = title;
        this.createUser = createUser;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sign = sign;
        this.createDate = createDate;
        this.remark = remark;
        this.workNewPlans = workNewPlans;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getBeginDate() {
        return this.beginDate;
    }
    
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSign() {
        return this.sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set getWorkNewPlans() {
        return this.workNewPlans;
    }
    
    public void setWorkNewPlans(Set workNewPlans) {
        this.workNewPlans = workNewPlans;
    }
   








}