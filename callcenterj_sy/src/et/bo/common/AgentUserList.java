/**
 * 沈阳卓越科技有限公司
 */
package et.bo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangfeng
 *
 */
public class AgentUserList {
	//内线用户列表的信息，在此处操作
	private static final Map<String,String> innerAgentMap = new HashMap<String,String>();
	
	//将一个座席员的信息加到内存中
	public static void addInAgentList(String agentNum,String agentName){
		innerAgentMap.put(agentNum, agentName);
	}
	
	//将一个座席员的信息从内存中删除
	public static void removeAgentInList(String agentNum){
		innerAgentMap.remove(agentNum);
	}
	
	//得到所有在线的用户列表，当用户登陆的时候，加入队列
	public static Map<String,String> getAgentList(){
		return innerAgentMap;
	}

}
