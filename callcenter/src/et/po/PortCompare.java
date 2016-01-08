package et.po;

import java.util.Date;


/**
 * PortCompare generated by MyEclipse - Hibernate Tools
 */

public class PortCompare  implements java.io.Serializable {


    // Fields    

     private String id;
     private String physicsPort;
     private String logicPort;
     private String ip;
     private Date addDate;


    // Constructors

    /** default constructor */
    public PortCompare() {
    }

	/** minimal constructor */
    public PortCompare(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public PortCompare(String id, String physicsPort, String logicPort, String ip, Date addDate) {
        this.id = id;
        this.physicsPort = physicsPort;
        this.logicPort = logicPort;
        this.ip = ip;
        this.addDate = addDate;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getPhysicsPort() {
        return this.physicsPort;
    }
    
    public void setPhysicsPort(String physicsPort) {
        this.physicsPort = physicsPort;
    }

    public String getLogicPort() {
        return this.logicPort;
    }
    
    public void setLogicPort(String logicPort) {
        this.logicPort = logicPort;
    }

    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getAddDate() {
        return this.addDate;
    }
    
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
   








}