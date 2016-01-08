package et.po;

import java.util.Date;


/**
 * NewsArticle generated by MyEclipse - Hibernate Tools
 */

public class NewsArticle  implements java.io.Serializable {


    // Fields    

     private String articleid;
     private String classid;
     private String title;
     private String author;
     private String copyfrom;
     private String editor;
     private String keyvalue;
     private String hits;
     private Date updatetime;
     private String hot;
     private String ontop;
     private String elite;
     private String passed;
     private String content;
     private String includepic;
     private String defaultpicurl;
     private String uploadfiles;
     private String deleted;
     private String remark;


    // Constructors

    /** default constructor */
    public NewsArticle() {
    }

	/** minimal constructor */
    public NewsArticle(String articleid) {
        this.articleid = articleid;
    }
    
    /** full constructor */
    public NewsArticle(String articleid, String classid, String title, String author, String copyfrom, String editor, String keyvalue, String hits, Date updatetime, String hot, String ontop, String elite, String passed, String content, String includepic, String defaultpicurl, String uploadfiles, String deleted, String remark) {
        this.articleid = articleid;
        this.classid = classid;
        this.title = title;
        this.author = author;
        this.copyfrom = copyfrom;
        this.editor = editor;
        this.keyvalue = keyvalue;
        this.hits = hits;
        this.updatetime = updatetime;
        this.hot = hot;
        this.ontop = ontop;
        this.elite = elite;
        this.passed = passed;
        this.content = content;
        this.includepic = includepic;
        this.defaultpicurl = defaultpicurl;
        this.uploadfiles = uploadfiles;
        this.deleted = deleted;
        this.remark = remark;
    }

   
    // Property accessors

    public String getArticleid() {
        return this.articleid;
    }
    
    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getClassid() {
        return this.classid;
    }
    
    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyfrom() {
        return this.copyfrom;
    }
    
    public void setCopyfrom(String copyfrom) {
        this.copyfrom = copyfrom;
    }

    public String getEditor() {
        return this.editor;
    }
    
    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getKeyvalue() {
        return this.keyvalue;
    }
    
    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }

    public String getHits() {
        return this.hits;
    }
    
    public void setHits(String hits) {
        this.hits = hits;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }
    
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getHot() {
        return this.hot;
    }
    
    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getOntop() {
        return this.ontop;
    }
    
    public void setOntop(String ontop) {
        this.ontop = ontop;
    }

    public String getElite() {
        return this.elite;
    }
    
    public void setElite(String elite) {
        this.elite = elite;
    }

    public String getPassed() {
        return this.passed;
    }
    
    public void setPassed(String passed) {
        this.passed = passed;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getIncludepic() {
        return this.includepic;
    }
    
    public void setIncludepic(String includepic) {
        this.includepic = includepic;
    }

    public String getDefaultpicurl() {
        return this.defaultpicurl;
    }
    
    public void setDefaultpicurl(String defaultpicurl) {
        this.defaultpicurl = defaultpicurl;
    }

    public String getUploadfiles() {
        return this.uploadfiles;
    }
    
    public void setUploadfiles(String uploadfiles) {
        this.uploadfiles = uploadfiles;
    }

    public String getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}