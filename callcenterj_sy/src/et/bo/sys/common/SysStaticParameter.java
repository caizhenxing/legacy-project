/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.common;

/**
* ����ϵͳ��̬����
*
* @version 	jan 01 2008 
* @author ����Ȩ
*/
public class SysStaticParameter {
	/***##################screen#################**/
	//���screen 
	//��ϯר�� expert_first
	public static final String SCREEN_CONST_EXPERY_FIRST = "expert_first";
	//ֵ��ר�� expert_class
	public static final String SCREEN_CONST_EXPERY_CLASS = "expert_class";
	
	/**
	 * ר�Ҳ�ѯSQL
	 */
	public static final String QUERY_EXPERT_SQL="select cust_name from oper_custinfo where dict_cust_type='SYS_TREE_0000002103' order by cust_name asc";
	/**
	 * ����Ա��ѯSQL
	 */
	public static final String QUERY_LINK_SQL="from OperCustinfo where dict_cust_type='SYS_TREE_0000002108' order by cust_name asc";
	/**
	 * ��ϯ��ѯhql
	 */
	public static final String QUERY_AGENT_SQL = " from SysUser u where (u.sysGroup.id='SYS_GROUP_0000000001' or u.sysGroup.id='SYS_GROUP_0000000141') and isnull(u.deleteMark,'0') != '1' order by u.userName asc ";
	/**
	 * ��ϯ��ѯsql
	 */
	public static final String QUERY_USER_SQL = " select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' and isnull(delete_mark,'0') != '1' order by user_name asc ";
	/**
	 * ϵͳ�����û�ID ������Ȩ�� ���������
	 */
	public static final String SYSTEM_ADMINISTRATOR_ID = "admin";
	/*
	 * �������ڵ�id
	 */
	public static final String PARAMETER_TREE_ROOT_ID = "1";
	/*
	 * ��������session��������
	 */
	public static final String PARAMETER_TREE_INSESSION = "parameterTreeSession";
	/*
	 * ivr�����ڵ�id
	 */
	public static final String IVR_TREE_ROOT_ID = "SYS_TREE_0000000041";
	/*
	 * ivr����session��������
	 */
	public static final String IVR_TREE_INSESSION = "ivrTreeSession";
	
	/*
	 * ivr��������session��������
	 */
	public static final String IVR_YUYIN_TREE_INSESSION = "ivrYuyinTreeSession";
	/*
	 * ivr���ڸ��ڵ�nickName ivrNodeRoot
	 */
	public static final String IVR_TREE_ROOT_NICKNAME = "ivrNodeRoot";
	/*
	 * ��������session��������
	 */
	public static final String DEPARTMENT_TREE_INSESSION = "departmentTreeSession";
	/*
	 * �Բ���������ɾ��ʱ��������session��������
	 */
	public static final String DEPARTMENT_TREE_OPER_INSESSION = "departmentTreeOperSession";
	/**
	 * �û���ɫҶ�ӽڵ�Ȩ����session�������
	 */
	public static final String USER_ROLE_LEAFRIGHT_INSESSION = "userRoleLeafRightInsession";
	/**
	 * Ҫ��������ĸ��ڵ�nickName��session��������
	 */
	public static final String LAYERORDERTREE_ROOTNODE_NICKNAME_INSESSION = "layerOrderTree_rootNode_nickName_inSession";
	/**
	 * Ҫ���������session��������
	 */
	public static final String LAYERORDERTREE_INSESSION = "layerOrderTree_inSession";
	/*
	 * �Բ��������Ա��������Ȩʱ��Ҫ������session��������
	 */
	public static final String DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION = "departmentPersonTreeBatchLeafRightInSession";
	/*
	 * �������ĸ��ڵ��nickName
	 */
	public static final String DEPARTMENT_TREEROOT_NICKNAMW = "departmentRoot";
	/*
	 * �������ĸ��ڵ��id
	 */
	public static final String DEPARTMENT_TREE_ROOT_ID = "SYS_TREE_0000000625";
	/*
	 * ģ��Ҷ�ӽڵ���Ȩ��Ҫ��tree sesson
	 */
	public static final String MODULE_TREE_LEAF_RIGHT_INSESSION = "moduleTreeLeafRightInSession";
	/*
	 * ģ��Ҷ�ӽڵ�oper��ɾ����Ҫ��tree sesson
	 */
	public static final String MODULE_TREE_LEAF_INSESSION = "moduleTreeLeafInSession";
	public static final String SESSION_TIME_OUT="sessionTimeOut";
	/*
	 * �û������е�Ȩ��
	 */
	public static final String MOD_TREE_IN_SESSION="modTreeSession";
	
	/*
	 * ģ����ڵ� Ψһ��moduleRoot
	 */
	public static String MOD_ROOT_NICKNAME = "moduleRoot";
	/*
	 * ģ��ڵ� ���� moduleParam
	 */
	public static String MOD_NODE_TYPE = "moduleParam";
	/*
	 * ������session��������
	 */
	public static String PARAMETER_SUBTREE_INSESSION = "parameterSubTreeInsession";
	/*
	 * ����nickNaME��session��������
	 */
	public static String PARAMETER_SUBTREE_NICKNAME_INSESSION = "parameterSubTreeNickNameInsession";
	/*
	 * ��¼��־�����ڵ���session���key
	 */
	public static String LOG_TREE_ID_IN_SESSION = "logTreeIDINSession";
	public static String TREE_ICON_GROUP="";
	
	public static String TREE_ICON_USER="";
	
	public static String TREE_TARGET="tree";
	
	public static String MODULE_TARGET="contents";
	
	public static boolean TREE_EXPANDED=false; 
	
	public static String TREE_ICON="";
	/*
	 * ��¼�ɹ��˽�user��Ϣ����session��
	 */
	public static String USER_IN_SESSION="userInfoSession";
	public static String USERBEAN_IN_SESSION="userBeanInSession";
	/*
	 * ��¼��־����������Ϣ��session���ֵ
	 */
	public static String LOG_OTHER_INFO_MAP_INSESSION = "logOtherInfoMapInSession";
	public static String GICON="GroupRight.gif";
	
	public static String NICON="NoRight.gif";
	
	public static String UICON="UserRight.gif";
	/**
	 * �û�������Ȩ��ͼ��
	 */
	public static String UBATCHICON="renicon.gif";
	
	public static String RICON="RoleRight.gif";
	
	public static String GRANT_TREE="grantTree";
	
	public static String GRANT_ID="grantId";
	
	public static String WORKFLOW_IN_SESSION="workflowSession";
	
	public static String CSS_IN_SESSION="cssinsession";
	
	public static String IMAGES_IN_SESSION="imagesinsession";
	
	/////////////////////////////////////////�����Ϣ��صĴ����ַ���Ϣ///////////////////
	
	public static final String COMMON_MESSAGE = "common";//��ͨ����Ϣ
	public static final String DIANXINGANLI_MESSAGE = "dianxing";//���Ͱ�����Ķ���Ϣ
	public static final String XIAOGUOANLI_MESSAGE = "xiaoguo";//Ч��������Ķ���Ϣ
	public static final String HUIZHEN_MESSAGE = "huizhen";//���ﰸ����Ķ���Ϣ
	public static final String JIAODIAN_MESSAGE = "jiaodian";//���㰸����Ķ���Ϣ
	public static final String QIYE_MESSAGE = "qiye";//��ҵ��Ϣ��Ķ���Ϣ	
	public static final String GONGQIU_MESSAGE = "gongqiu";  //ũ��Ʒ�����Ķ���Ϣ
	public static final String JIAGE_MESSAGE = "jiage";  //ũ��Ʒ�۸��Ķ���Ϣ
	public static final String PUTONGYILIAOFUWU_MESSAGE = "putongyiliao";  //��ͨҽ�Ʒ�����Ϣ��Ķ���Ϣ
	public static final String YUYUEYILIAOFUWU_MESSAGE = "yuyueyiliao";  //ԤԼҽ�Ʒ�����Ϣ��Ķ���Ϣ
	public static final String WENJUANSHEJI_MESSAGE = "wenjuansheji";  //�����ʾ���ƿ�Ķ���Ϣ
	public static final String SHICHANGFENXI_MESSAGE = "shichangfenxi";  //�г�������Ķ���Ϣ	
	public static final String JIAODIANZHUIZONG_MESSAGE = "jiaodianzhuizong";  //����׷�ٿ�Ķ���Ϣ
	
	////////////////////////////////////////////////////////////////////////////////
}
