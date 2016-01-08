package et.bo.oa.auditingClass;

public class auditingclass {

	/**
	 * @param 专门用于保存审核类型中的截点的名字,和时间类型的截点的名字.
	 * @version 2007-1-22
	 * @return
	 */
	private static String auditingNo=null;
	private static String auditingYes=null;
	private static String auditingNot=null;
	private static String auditingDel=null;
	private static String planTimeWeek=null;
	private static String planTimeMother=null;
	private static String planTimePhase=null;
	private static String missionselectsign=null;
	
	
	
	private static String userName=null;//获得用户名
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String getAuditingNo() {
		return auditingNo;
	}

	public static void setAuditingNo(String auditingNo) {
		auditingclass.auditingNo = auditingNo;
	}

	public static String getAuditingNot() {
		return auditingNot;
	}

	public static void setAuditingNot(String auditingNot) {
		auditingclass.auditingNot = auditingNot;
	}

	public static String getAuditingYes() {
		return auditingYes;
	}

	public static void setAuditingYes(String auditingYes) {
		auditingclass.auditingYes = auditingYes;
	}

	public static String getAuditingDel() {
		return auditingDel;
	}

	public static void setAuditingDel(String auditingDel) {
		auditingclass.auditingDel = auditingDel;
	}

	public static String getPlanTimeMother() {
		return planTimeMother;
	}

	public static void setPlanTimeMother(String planTimeMother) {
		auditingclass.planTimeMother = planTimeMother;
	}

	public static String getPlanTimePhase() {
		return planTimePhase;
	}

	public static void setPlanTimePhase(String planTimePhase) {
		auditingclass.planTimePhase = planTimePhase;
	}

	public static String getPlanTimeWeek() {
		return planTimeWeek;
	}

	public static void setPlanTimeWeek(String planTimeWeek) {
		auditingclass.planTimeWeek = planTimeWeek;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		auditingclass.userName = userName;
	}

	public static String getMissionselectsign() {
		return missionselectsign;
	}

	public static void setMissionselectsign(String missionselectsign) {
		auditingclass.missionselectsign = missionselectsign;
	}


	


}
