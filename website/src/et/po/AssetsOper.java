package et.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * AssetsOper generated by MyEclipse - Hibernate Tools
 */

public class AssetsOper  implements java.io.Serializable {


    // Fields    

     private String operId;
     private String operCode;
     private String operName;
     private String operType;
     private Date operTime;
     private Double assetsPrice;
     private Integer assetsNum;
     private String inCompany;
     private String inPeople;
     private String outCompany;
     private String outPeople;
     private String sign;
     private String remark;
     private Set assetsInfos = new HashSet(0);


    // Constructors

    /** default constructor */
    public AssetsOper() {
    }

	/** minimal constructor */
    public AssetsOper(String operId) {
        this.operId = operId;
    }
    
    /** full constructor */
    public AssetsOper(String operId, String operCode, String operName, String operType, Date operTime, Double assetsPrice, Integer assetsNum, String inCompany, String inPeople, String outCompany, String outPeople, String sign, String remark, Set assetsInfos) {
        this.operId = operId;
        this.operCode = operCode;
        this.operName = operName;
        this.operType = operType;
        this.operTime = operTime;
        this.assetsPrice = assetsPrice;
        this.assetsNum = assetsNum;
        this.inCompany = inCompany;
        this.inPeople = inPeople;
        this.outCompany = outCompany;
        this.outPeople = outPeople;
        this.sign = sign;
        this.remark = remark;
        this.assetsInfos = assetsInfos;
    }

   
    // Property accessors

    public String getOperId() {
        return this.operId;
    }
    
    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperCode() {
        return this.operCode;
    }
    
    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getOperName() {
        return this.operName;
    }
    
    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperType() {
        return this.operType;
    }
    
    public void setOperType(String operType) {
        this.operType = operType;
    }

    public Date getOperTime() {
        return this.operTime;
    }
    
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public Double getAssetsPrice() {
        return this.assetsPrice;
    }
    
    public void setAssetsPrice(Double assetsPrice) {
        this.assetsPrice = assetsPrice;
    }

    public Integer getAssetsNum() {
        return this.assetsNum;
    }
    
    public void setAssetsNum(Integer assetsNum) {
        this.assetsNum = assetsNum;
    }

    public String getInCompany() {
        return this.inCompany;
    }
    
    public void setInCompany(String inCompany) {
        this.inCompany = inCompany;
    }

    public String getInPeople() {
        return this.inPeople;
    }
    
    public void setInPeople(String inPeople) {
        this.inPeople = inPeople;
    }

    public String getOutCompany() {
        return this.outCompany;
    }
    
    public void setOutCompany(String outCompany) {
        this.outCompany = outCompany;
    }

    public String getOutPeople() {
        return this.outPeople;
    }
    
    public void setOutPeople(String outPeople) {
        this.outPeople = outPeople;
    }

    public String getSign() {
        return this.sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set getAssetsInfos() {
        return this.assetsInfos;
    }
    
    public void setAssetsInfos(Set assetsInfos) {
        this.assetsInfos = assetsInfos;
    }
   








}