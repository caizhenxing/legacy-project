/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.common;

/**
* 定义系统静态参数
*
* @version 	jan 01 2008 
* @author 王文权
*/
public class SysStaticParameter {
	/***##################screen#################**/
	//类别screen 
	//首席专家 expert_first
	public static final String SCREEN_CONST_EXPERY_FIRST = "expert_first";
	//值班专家 expert_class
	public static final String SCREEN_CONST_EXPERY_CLASS = "expert_class";
	
	/**
	 * 专家查询SQL
	 */
	public static final String QUERY_EXPERT_SQL="select cust_name from oper_custinfo where dict_cust_type='SYS_TREE_0000002103' order by cust_name asc";
	/**
	 * 联络员查询SQL
	 */
	public static final String QUERY_LINK_SQL="from OperCustinfo where dict_cust_type='SYS_TREE_0000002108' order by cust_name asc";
	/**
	 * 座席查询hql
	 */
	public static final String QUERY_AGENT_SQL = " from SysUser u where (u.sysGroup.id='SYS_GROUP_0000000001' or u.sysGroup.id='SYS_GROUP_0000000141') and isnull(u.deleteMark,'0') != '1' order by u.userName asc ";
	/**
	 * 座席查询sql
	 */
	public static final String QUERY_USER_SQL = " select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' and isnull(delete_mark,'0') != '1' order by user_name asc ";
	/**
	 * 系统超级用户ID 有所有权限 不对其过滤
	 */
	public static final String SYSTEM_ADMINISTRATOR_ID = "admin";
	/*
	 * 参数根节点id
	 */
	public static final String PARAMETER_TREE_ROOT_ID = "1";
	/*
	 * 参数树在session里存的名字
	 */
	public static final String PARAMETER_TREE_INSESSION = "parameterTreeSession";
	/*
	 * ivr树根节点id
	 */
	public static final String IVR_TREE_ROOT_ID = "SYS_TREE_0000000041";
	/*
	 * ivr树在session里存的名字
	 */
	public static final String IVR_TREE_INSESSION = "ivrTreeSession";
	
	/*
	 * ivr语音树在session里存的名字
	 */
	public static final String IVR_YUYIN_TREE_INSESSION = "ivrYuyinTreeSession";
	/*
	 * ivr树在根节点nickName ivrNodeRoot
	 */
	public static final String IVR_TREE_ROOT_NICKNAME = "ivrNodeRoot";
	/*
	 * 部门树在session里存的名字
	 */
	public static final String DEPARTMENT_TREE_INSESSION = "departmentTreeSession";
	/*
	 * 对部门树做增删改时部门树在session里存的名字
	 */
	public static final String DEPARTMENT_TREE_OPER_INSESSION = "departmentTreeOperSession";
	/**
	 * 用户角色叶子节点权限在session里的名字
	 */
	public static final String USER_ROLE_LEAFRIGHT_INSESSION = "userRoleLeafRightInsession";
	/**
	 * 要排序的树的根节点nickName在session里村的名字
	 */
	public static final String LAYERORDERTREE_ROOTNODE_NICKNAME_INSESSION = "layerOrderTree_rootNode_nickName_inSession";
	/**
	 * 要排序的树在session里村的名字
	 */
	public static final String LAYERORDERTREE_INSESSION = "layerOrderTree_inSession";
	/*
	 * 对部门里的人员做批量授权时需要的树在session里存的名字
	 */
	public static final String DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION = "departmentPersonTreeBatchLeafRightInSession";
	/*
	 * 部门树的根节点的nickName
	 */
	public static final String DEPARTMENT_TREEROOT_NICKNAMW = "departmentRoot";
	/*
	 * 部门树的根节点的id
	 */
	public static final String DEPARTMENT_TREE_ROOT_ID = "SYS_TREE_0000000625";
	/*
	 * 模块叶子节点授权需要的tree sesson
	 */
	public static final String MODULE_TREE_LEAF_RIGHT_INSESSION = "moduleTreeLeafRightInSession";
	/*
	 * 模块叶子节点oper增删改需要的tree sesson
	 */
	public static final String MODULE_TREE_LEAF_INSESSION = "moduleTreeLeafInSession";
	public static final String SESSION_TIME_OUT="sessionTimeOut";
	/*
	 * 用户所具有的权限
	 */
	public static final String MOD_TREE_IN_SESSION="modTreeSession";
	
	/*
	 * 模块跟节点 唯一名moduleRoot
	 */
	public static String MOD_ROOT_NICKNAME = "moduleRoot";
	/*
	 * 模块节点 类型 moduleParam
	 */
	public static String MOD_NODE_TYPE = "moduleParam";
	/*
	 * 子树在session里存的名字
	 */
	public static String PARAMETER_SUBTREE_INSESSION = "parameterSubTreeInsession";
	/*
	 * 子树nickNaME在session里存的名字
	 */
	public static String PARAMETER_SUBTREE_NICKNAME_INSESSION = "parameterSubTreeNickNameInsession";
	/*
	 * 记录日志的树节点在session里的key
	 */
	public static String LOG_TREE_ID_IN_SESSION = "logTreeIDINSession";
	public static String TREE_ICON_GROUP="";
	
	public static String TREE_ICON_USER="";
	
	public static String TREE_TARGET="tree";
	
	public static String MODULE_TARGET="contents";
	
	public static boolean TREE_EXPANDED=false; 
	
	public static String TREE_ICON="";
	/*
	 * 登录成功了将user信息存在session里
	 */
	public static String USER_IN_SESSION="userInfoSession";
	public static String USERBEAN_IN_SESSION="userBeanInSession";
	/*
	 * 记录日志所需其他信息在session里的值
	 */
	public static String LOG_OTHER_INFO_MAP_INSESSION = "logOtherInfoMapInSession";
	public static String GICON="GroupRight.gif";
	
	public static String NICON="NoRight.gif";
	
	public static String UICON="UserRight.gif";
	/**
	 * 用户批量授权的图标
	 */
	public static String UBATCHICON="renicon.gif";
	
	public static String RICON="RoleRight.gif";
	
	public static String GRANT_TREE="grantTree";
	
	public static String GRANT_ID="grantId";
	
	public static String WORKFLOW_IN_SESSION="workflowSession";
	
	public static String CSS_IN_SESSION="cssinsession";
	
	public static String IMAGES_IN_SESSION="imagesinsession";
	
	/////////////////////////////////////////与短消息相关的处理字符信息///////////////////
	
	public static final String COMMON_MESSAGE = "common";//普通短消息
	public static final String DIANXINGANLI_MESSAGE = "dianxing";//典型案例库的短消息
	public static final String XIAOGUOANLI_MESSAGE = "xiaoguo";//效果案例库的短消息
	public static final String HUIZHEN_MESSAGE = "huizhen";//会诊案例库的短消息
	public static final String JIAODIAN_MESSAGE = "jiaodian";//焦点案例库的短消息
	public static final String QIYE_MESSAGE = "qiye";//企业信息库的短消息	
	public static final String GONGQIU_MESSAGE = "gongqiu";  //农产品供求库的短消息
	public static final String JIAGE_MESSAGE = "jiage";  //农产品价格库的短消息
	public static final String PUTONGYILIAOFUWU_MESSAGE = "putongyiliao";  //普通医疗服务信息库的短消息
	public static final String YUYUEYILIAOFUWU_MESSAGE = "yuyueyiliao";  //预约医疗服务信息库的短消息
	public static final String WENJUANSHEJI_MESSAGE = "wenjuansheji";  //调查问卷设计库的短消息
	public static final String SHICHANGFENXI_MESSAGE = "shichangfenxi";  //市场分析库的短消息	
	public static final String JIAODIANZHUIZONG_MESSAGE = "jiaodianzhuizong";  //焦点追踪库的短消息
	
	////////////////////////////////////////////////////////////////////////////////
}
