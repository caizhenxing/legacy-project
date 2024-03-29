package et.po;

import java.util.Date;


/**
 * ForumForbidden generated by MyEclipse - Hibernate Tools
 */

public class ForumForbidden  implements java.io.Serializable {


    // Fields    

     private String id;
     private String postsId;
     private String userkey;
     private Date beginTime;
     private Long timeLength;
     private Long isValidate;
     private String remark;


    // Constructors

    /** default constructor */
    public ForumForbidden() {
    }

	/** minimal constructor */
    public ForumForbidden(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public ForumForbidden(String id, String postsId, String userkey, Date beginTime, Long timeLength, Long isValidate, String remark) {
        this.id = id;
        this.postsId = postsId;
        this.userkey = userkey;
        this.beginTime = beginTime;
        this.timeLength = timeLength;
        this.isValidate = isValidate;
        this.remark = remark;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getPostsId() {
        return this.postsId;
    }
    
    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }

    public String getUserkey() {
        return this.userkey;
    }
    
    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public Date getBeginTime() {
        return this.beginTime;
    }
    
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Long getTimeLength() {
        return this.timeLength;
    }
    
    public void setTimeLength(Long timeLength) {
        this.timeLength = timeLength;
    }

    public Long getIsValidate() {
        return this.isValidate;
    }
    
    public void setIsValidate(Long isValidate) {
        this.isValidate = isValidate;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}