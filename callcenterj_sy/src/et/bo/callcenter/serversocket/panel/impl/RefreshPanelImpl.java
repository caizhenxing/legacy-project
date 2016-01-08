/**
 * 沈阳卓越科技有限公司
 * 2008-4-22
 */
package et.bo.callcenter.serversocket.panel.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import et.bo.callcenter.bo.experter.OperExperterService;
import et.bo.callcenter.serversocket.iconst.ConstRuleI;
import et.bo.callcenter.serversocket.panel.RefreshPanelService;

/**
 * 刷新面板，合成需要的字符串信息
 * 
 * @author zhang feng
 * 
 */
public class RefreshPanelImpl implements RefreshPanelService {
	private OperExperterService experterService;

	/**
	 * 根据交换机的端口号得到对应的IvrBean的信息
	 * 
	 * @param pbxPort
	 * @return
	 */
	public IvrBean getIvrBeanByPbxport(List ivrList, String pbxPort) {
		IvrBean ib = new IvrBean();
		Iterator it = ivrList.iterator();
		while (it.hasNext()) {
			IvrBean ibr = (IvrBean) it.next();
			if (ib.getPbxPort().equals(pbxPort)) {
				ib = ibr;
			}
		}
		return ib;
	}

	/**
	 * 刷新用户座席面板中三方通话的专家列表
	 * 
	 * @param ap
	 * @return 向座席发出的格式化后的字符串
	 */
	public String refreshPanelTExperterList(AgentPanel ap) {
		StringBuffer sb = new StringBuffer();
		sb.append(ConstRuleI.CMD_AGENT_PANEL_EXPERTLIST + ":");
		sb.append(experterService.getOperCustinfoStrs() + ";");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.serversocket.panel.RefreshPanelService#refreshPanel(et.bo.callcenter.serversocket.panel.impl.AgentPanel)
	 */
	public String refreshPanel(AgentPanel ap) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(ConstRuleI.CMD_AGENT_REFRESH_AGENTPANEL);
		sb.append(ConstRuleI.SPLIT_SIGN_COLON);

		// 座席状态
		String txtZXZT = ConstRuleI.CMD_APENT_TXTZHUOXIZHUANGTAI;
		String agentState = ap.getAgentState();
		if (agentState.equals("1")) {
			sb.append(getOnePanelState(txtZXZT, ConstRuleI.TEXT_STATE_ONE));
		} else if (agentState.equals("2")) {
			sb.append(getOnePanelState(txtZXZT, ConstRuleI.TEXT_STATE_ONE));
		} else if (agentState.equals("0")) {
			sb.append(getOnePanelState(txtZXZT, ConstRuleI.CMD_INIT));
		}

		// 登陆时间
		String txtDLSJ = ConstRuleI.CMD_APENT_TXTDENGLUSHIJIAN;
		String agentLogintime = ap.getAgentLoginTime();
		if (agentLogintime.equals("0")) {
			sb.append(getOnePanelState(txtDLSJ, ConstRuleI.CMD_INIT));
		} else if (!agentLogintime.equals("")) {
			sb.append(getOnePanelState(txtDLSJ, agentLogintime));
		}

		// 日咨询量
		String txtRZXL = ConstRuleI.CMD_APENT_TXTRIZHIXUNLIANG;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(txtRZXL, ConstRuleI.CMD_INIT));
		}

		// 栏目咨询量
		String cLMZXL = ConstRuleI.CMD_APENT_CLANMUZHIXUNLIANG;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cLMZXL, ConstRuleI.CMD_INIT));
		}

		// 服务总时
		String tFWZS = ConstRuleI.CMD_APENT_TXTFUWUZHONGSHI;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(tFWZS, ConstRuleI.CMD_INIT));
		}

		// 本次时长
		String tBCSC = ConstRuleI.CMD_APENT_TXTBENCHISHICHANG;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(tBCSC, ConstRuleI.CMD_INIT));
		}

		// 当前排队数
		String tDQPDS = ConstRuleI.CMD_APENT_TXTDANGQIANPAIDUISHU;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(tDQPDS, ConstRuleI.CMD_INIT));
		}

		// 来电记录
		String cLDJL = ConstRuleI.CMD_APENT_COMLAIDIANJILU;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cLDJL, ConstRuleI.CMD_INIT));
		}

		// 在线主持人
		String cZXZCR = ConstRuleI.CMD_APENT_ZAIXIANZHUCHIREN;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cZXZCR, ConstRuleI.CMD_INIT));
		}

		// 在线专家
		String cZXZJ = ConstRuleI.CMD_APENT_ZAIXIANZHUANJIA;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cZXZJ, ConstRuleI.CMD_INIT));
		}

		// 震铃动作
		String iST = ConstRuleI.CMD_image_RINGUP;
		String ringState = ap.getIsRing();
		if (ringState.equals("0")) {
			sb.append(getButtonState(iST, ConstRuleI.IMAGE_STATE_ONE));
		} else if (ringState.equals("1")) {
			sb.append(getButtonState(iST, ConstRuleI.IMAGE_STATE_TWO));
		}

		// 摘机
		String iZJ = ConstRuleI.CMD_image_RINGUP;
		String hookState = ap.getIsoffHook();
		if (hookState.equals("0")) {
			sb.append(getButtonState(iZJ, ConstRuleI.IMAGE_STATE_ONE));
		} else if (hookState.equals("1")) {
			sb.append(getButtonState(iZJ, ConstRuleI.IMAGE_STATE_TWO));
		}

		// 挂机
		String iGJ = ConstRuleI.CMD_image_RINGUP;
		String onhookState = ap.getIsonHook();
		if (onhookState.equals("0")) {
			sb.append(getButtonState(iGJ, ConstRuleI.IMAGE_STATE_ONE));
		} else if (onhookState.equals("1")) {
			sb.append(getButtonState(iGJ, ConstRuleI.IMAGE_STATE_TWO));
		}

		// 转内线
		String bZNX = ConstRuleI.CMD_ZHUANJIENIEXIAN;
		String zhuanNiexian = ap.getBZhuanniexian();
		if (zhuanNiexian.equals("0")) {
			sb.append(getButtonState(bZNX, ConstRuleI.CMD_INIT));
		} else if (zhuanNiexian.equals("1")) {
			sb.append(getButtonState(bZNX, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (zhuanNiexian.equals("2")) {
			sb.append(getButtonState(bZNX, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 三方开始呼叫
		String bThreecall = ConstRuleI.CMD_THREE_CALLING;
		String begincall = ap.getThreeCalling();
		if (begincall.equals("0")) {
			sb.append(getButtonState(bThreecall, ConstRuleI.CMD_INIT));
		} else if (begincall.equals("1")) {
			sb.append(getButtonState(bThreecall, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (begincall.equals("2")) {
			sb.append(getButtonState(bThreecall, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 三方开始通话
		String bThreetalk = ConstRuleI.CMD_THREE_BEGIN;
		String begintalk = ap.getThreeCallbegin();
		if (begintalk.equals("0")) {
			sb.append(getButtonState(bThreetalk, ConstRuleI.CMD_INIT));
		} else if (begintalk.equals("1")) {
			sb.append(getButtonState(bThreetalk, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (begintalk.equals("2")) {
			sb.append(getButtonState(bThreetalk, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 强拆
		String bQiangchai = ConstRuleI.CMD_QIANGCHAI;
		String qiangchai = ap.getQiangchai();
		if (qiangchai.equals("0")) {
			sb.append(getButtonState(bQiangchai, ConstRuleI.CMD_INIT));
		} else if (qiangchai.equals("1")) {
			sb.append(getButtonState(bQiangchai, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (qiangchai.equals("2")) {
			sb.append(getButtonState(bQiangchai, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 强插
		String bQiangcha = ConstRuleI.CMD_QIANGCHA;
		String qiangcha = ap.getQiangcha();
		if (qiangcha.equals("0")) {
			sb.append(getButtonState(bQiangcha, ConstRuleI.CMD_INIT));
		} else if (qiangcha.equals("1")) {
			sb.append(getButtonState(bQiangcha, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (qiangcha.equals("2")) {
			sb.append(getButtonState(bQiangcha, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 专家挂
		String bZhuanjiahold = ConstRuleI.CMD_ZHUANJIAGUA;
		String expertHold = ap.getExpertHold();
		if (expertHold.equals("0")) {
			sb.append(getButtonState(bZhuanjiahold, ConstRuleI.CMD_INIT));
		} else if (expertHold.equals("1")) {
			sb
					.append(getButtonState(bZhuanjiahold,
							ConstRuleI.AGENT_BUTTON_ONE));
		} else if (expertHold.equals("2")) {
			sb
					.append(getButtonState(bZhuanjiahold,
							ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 座席挂
		String bAgenthold = ConstRuleI.CMD_AGENTHOLD;
		String agenthold = ap.getAgentHold();
		if (agenthold.equals("0")) {
			sb.append(getButtonState(bAgenthold, ConstRuleI.CMD_INIT));
		} else if (agenthold.equals("1")) {
			sb.append(getButtonState(bAgenthold, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (agenthold.equals("2")) {
			sb.append(getButtonState(bAgenthold, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 三方挂
		String bThreehold = ConstRuleI.CMD_THREEHOLD;
		String threeHold = ap.getThreeHold();
		if (threeHold.equals("0")) {
			sb.append(getButtonState(bThreehold, ConstRuleI.CMD_INIT));
		} else if (threeHold.equals("1")) {
			sb.append(getButtonState(bThreehold, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (threeHold.equals("2")) {
			sb.append(getButtonState(bThreehold, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 转外线
		String bZWX = ConstRuleI.CMD_ZHUANJIEWAIXIAN;
		String zhuanWaixian = ap.getBZhuanjiewaixian();
		if (zhuanWaixian.equals("0")) {
			sb.append(getButtonState(bZWX, ConstRuleI.CMD_INIT));
		} else if (zhuanWaixian.equals("1")) {
			sb.append(getButtonState(bZWX, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (zhuanWaixian.equals("2")) {
			sb.append(getButtonState(bZWX, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 登陆
		String bDL = ConstRuleI.CMD_APENT_DENGLU;
		String loginState = ap.getLoginState();
		if (loginState.equals("0")) {
			sb.append(getButtonState(bDL, ConstRuleI.CMD_INIT));
		} else if (loginState.equals("1")) {
			sb.append(getButtonState(bDL, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (loginState.equals("2")) {
			sb.append(getButtonState(bDL, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 暂停
		String bZT = ConstRuleI.CMD_APENT_ZHANTING;
		String signState = ap.getSignState();
		if (signState.equals("0")) {
			sb.append(getButtonState(bZT, ConstRuleI.CMD_INIT));
		} else if (signState.equals("1")) {
			sb.append(getButtonState(bZT, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (signState.equals("2")) {
			sb.append(getButtonState(bZT, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 等待
		String bDD = ConstRuleI.CMD_APENT_DENGDAI;
		String waitState = ap.getWaitState();
		if (waitState.equals("0")) {
			sb.append(getButtonState(bDD, ConstRuleI.CMD_INIT));
		} else if (waitState.equals("1")) {
			sb.append(getButtonState(bDD, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (waitState.equals("2")) {
			sb.append(getButtonState(bDD, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 接听
		String bJT = ConstRuleI.CMD_APENT_JIETING;
		String telState = ap.getTelState();
		if (telState.equals("0")) {
			sb.append(getButtonState(bJT, ConstRuleI.CMD_INIT));
		} else if (telState.equals("1")) {
			sb.append(getButtonState(bJT, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (telState.equals("2")) {
			sb.append(getButtonState(bJT, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 转自动
		String bZZD = ConstRuleI.CMD_APENT_ZHUANZHIDONG;
		String toIVR = ap.getToIVR();
		if (toIVR.equals("0")) {
			sb.append(getButtonState(bZZD, ConstRuleI.CMD_INIT));
		} else if (toIVR.equals("1")) {
			sb.append(getButtonState(bZZD, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (toIVR.equals("2")) {
			sb.append(getButtonState(bZZD, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 转移
		String bZY = ConstRuleI.CMD_APENT_ZHUANYI;
		String turnState = ap.getTurnState();
		if (turnState.equals("0")) {
			sb.append(getButtonState(bZY, ConstRuleI.CMD_INIT));
		} else if (turnState.equals("1")) {
			sb.append(getButtonState(bZY, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (turnState.equals("2")) {
			sb.append(getButtonState(bZY, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 电话本
		String bDHB = ConstRuleI.CMD_APENT_DIANHUABU;
		String bookState = ap.getTelBookState();
		if (bookState.equals("0")) {
			sb.append(getButtonState(bDHB, ConstRuleI.CMD_INIT));
		} else if (bookState.equals("1")) {
			sb.append(getButtonState(bDHB, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (bookState.equals("2")) {
			sb.append(getButtonState(bDHB, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 外呼
		String bWH = ConstRuleI.CMD_APENT_WAIHU;
		String outCallState = ap.getOutCallState();
		if (outCallState.equals("0")) {
			sb.append(getButtonState(bWH, ConstRuleI.CMD_INIT));
		} else if (outCallState.equals("1")) {
			sb.append(getButtonState(bWH, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (outCallState.equals("2")) {
			sb.append(getButtonState(bWH, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 收听录音
		String bSTLY = ConstRuleI.CMD_APENT_SHOUTINGLUYIN;
		String listenState = ap.getListenWavState();
		if (listenState.equals("0")) {
			sb.append(getButtonState(bSTLY, ConstRuleI.CMD_INIT));
		} else if (listenState.equals("1")) {
			sb.append(getButtonState(bSTLY, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (listenState.equals("2")) {
			sb.append(getButtonState(bSTLY, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 三方通话
		String bSFTH = ConstRuleI.CMD_APENT_SHANFANFTONGHUA;
		String threeCallState = ap.getThreeCallState();
		if (threeCallState.equals("0")) {
			sb.append(getButtonState(bSFTH, ConstRuleI.CMD_INIT));
		} else if (threeCallState.equals("1")) {
			sb.append(getButtonState(bSFTH, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (threeCallState.equals("2")) {
			sb.append(getButtonState(bSFTH, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 超时
		String bCS = ConstRuleI.CMD_APENT_CHAOSHI;
		String superTimeState = ap.getSuperTimeState();
		if (superTimeState.equals("0")) {
			sb.append(getButtonState(bCS, ConstRuleI.CMD_INIT));
		} else if (superTimeState.equals("1")) {
			sb.append(getButtonState(bCS, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (superTimeState.equals("2")) {
			sb.append(getButtonState(bCS, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 电话会议
		String bDHHY = ConstRuleI.CMD_APENT_DIANHUAHUIYI;
		String phoneConfence = ap.getPhoneConfenceState();
		if (phoneConfence.equals("0")) {
			sb.append(getButtonState(bDHHY, ConstRuleI.CMD_INIT));
		} else if (phoneConfence.equals("1")) {
			sb.append(getButtonState(bDHHY, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (phoneConfence.equals("2")) {
			sb.append(getButtonState(bDHHY, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 结束延时
		String bJSYS = ConstRuleI.CMD_APENT_JIESHUYANSHI;
		String endSuperState = ap.getEndSuperTimeState();
		if (endSuperState.equals("0")) {
			sb.append(getButtonState(bJSYS, ConstRuleI.CMD_INIT));
		} else if (endSuperState.equals("1")) {
			sb.append(getButtonState(bJSYS, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (endSuperState.equals("2")) {
			sb.append(getButtonState(bJSYS, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// 用户评价
		String bYHPJ = ConstRuleI.CMD_APENT_YONGHUPINGJIA;
		String yonghupingjia = ap.getUserEvaluateState();
		sb.append(bYHPJ);
		sb.append(ConstRuleI.SPLIT_SIGN_jing);
		if (yonghupingjia.equals("0")) {
			sb.append(ConstRuleI.CMD_INIT);
		} else if (yonghupingjia.equals("1")) {
			sb.append(ConstRuleI.AGENT_BUTTON_ONE);
		} else if (yonghupingjia.equals("2")) {
			sb.append(ConstRuleI.AGENT_BUTTON_TWO);
		}
		sb.append(ConstRuleI.SPLIT_SIGN_SEMICOLON);

		return sb.toString();
	}

	// 合成字符串，符合规约格式的字符串
	private String getOnePanelState(String name, String value) {
		StringBuilder sbd = new StringBuilder();

		sbd.append(name);
		sbd.append(ConstRuleI.SPLIT_SIGN_AT);
		sbd.append(value);
		sbd.append(ConstRuleI.SPLIT_SIGN_COMMA);

		return sbd.toString();
	}

	// 合成字符串，以#号分割
	private String getButtonState(String name, String value) {
		StringBuilder sbd = new StringBuilder();

		sbd.append(name);
		sbd.append(ConstRuleI.SPLIT_SIGN_jing);
		sbd.append(value);
		sbd.append(ConstRuleI.SPLIT_SIGN_COMMA);

		return sbd.toString();
	}

	/**
	 * 从CtiBeanMap里根据ctiBean的mainKey找到ctiBeanMap的key
	 * 
	 * @param mainKey
	 * @return
	 */
	public String getKeyByMainKey(String mainKey) {
		Map<String, CtiBean> ctiBeanMap = RefreshPanelService.ctiBeanMap;
		Iterator keyIt = ctiBeanMap.keySet().iterator();
		String curKey = null;
		while (keyIt.hasNext()) {
			curKey = (String) keyIt.next();
			CtiBean bean = ctiBeanMap.get(curKey);
			if (mainKey.equals(bean.getMainKey())) {
				return curKey;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		RefreshPanelImpl rp = new RefreshPanelImpl();
		AgentPanel ap = new AgentPanel();
		// ap.setLoginState("1");
		//System.out.println(rp.refreshPanel(ap));
	}

	/**
	 * 从ivrStateMap里找出没有使用的端口号
	 * 
	 * @return
	 */
	public String getNoUseIvrPort() {
		Map<String, String> ivrMap = RefreshPanelService.ivrStateMap;
		Iterator keys = ivrMap.keySet().iterator();
		String curKey = null;
		while (keys.hasNext()) {
			curKey = (String) keys.next();
			if ("nouse".equals(ivrMap.get(curKey))
					|| "NOUSE".equals(ivrMap.get(curKey))) {
				return curKey;
			}
		}
		return null;
	}

	/**
	 * 从正在使用的端口中找到key値 RefreshPanelService ctiBeanMap 循环 当ctiBean usingPort =
	 * pbxPort时 返回key
	 * 
	 * @return String pbxPort
	 */
	public String getKeyFromCtiUsingPort(String pbxPort) {
		Map<String, CtiBean> ctiMap = RefreshPanelService.ctiBeanMap;
		Iterator keys = ctiMap.keySet().iterator();
		String curKey = null;
		while (keys.hasNext()) {
			curKey = (String) keys.next();
			if (pbxPort.equals(ctiMap.get(curKey).getUsingPort())) {
				return curKey;
			}
		}
		return null;
	}

	/**
	 * 从正在使用的端口中找到mainKey値
	 */
	public String getMainKeyFromCtiUsingPort(String pbxPort) {
		Map<String, CtiBean> ctiMap = RefreshPanelService.ctiBeanMap;
		Iterator keys = ctiMap.keySet().iterator();
		String curKey = null;
		while (keys.hasNext()) {
			curKey = (String) keys.next();
			if (pbxPort.equals(ctiMap.get(curKey).getUsingPort())) {
				return ctiMap.get(curKey).getMainKey();
			}
		}
		return null;
	}

	/**
	 * 根据交换机端口号得到对应的AgentBeans
	 */
	public AgentBean getAgentBeanBypbxport(String mainKey, String pbxPort) {
		// TODO Auto-generated method stub
		AgentBean ab = new AgentBean();
		CtiBean cb = (CtiBean) ctiBeanMap.get(getKeyByMainKey(mainKey));
		Iterator it = cb.getAgeBean().iterator();
		while (it.hasNext()) {
			AgentBean agb = (AgentBean) it.next();
			if (agb.getPbxPort().equals(pbxPort)) {
				ab = agb;
			}
		}
		return ab;
	}

	public OperExperterService getExperterService() {
		return experterService;
	}

	public void setExperterService(OperExperterService experterService) {
		this.experterService = experterService;
	}

}
