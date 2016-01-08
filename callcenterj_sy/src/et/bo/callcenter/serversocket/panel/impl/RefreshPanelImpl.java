/**
 * ����׿Խ�Ƽ����޹�˾
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
 * ˢ����壬�ϳ���Ҫ���ַ�����Ϣ
 * 
 * @author zhang feng
 * 
 */
public class RefreshPanelImpl implements RefreshPanelService {
	private OperExperterService experterService;

	/**
	 * ���ݽ������Ķ˿ںŵõ���Ӧ��IvrBean����Ϣ
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
	 * ˢ���û���ϯ���������ͨ����ר���б�
	 * 
	 * @param ap
	 * @return ����ϯ�����ĸ�ʽ������ַ���
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

		// ��ϯ״̬
		String txtZXZT = ConstRuleI.CMD_APENT_TXTZHUOXIZHUANGTAI;
		String agentState = ap.getAgentState();
		if (agentState.equals("1")) {
			sb.append(getOnePanelState(txtZXZT, ConstRuleI.TEXT_STATE_ONE));
		} else if (agentState.equals("2")) {
			sb.append(getOnePanelState(txtZXZT, ConstRuleI.TEXT_STATE_ONE));
		} else if (agentState.equals("0")) {
			sb.append(getOnePanelState(txtZXZT, ConstRuleI.CMD_INIT));
		}

		// ��½ʱ��
		String txtDLSJ = ConstRuleI.CMD_APENT_TXTDENGLUSHIJIAN;
		String agentLogintime = ap.getAgentLoginTime();
		if (agentLogintime.equals("0")) {
			sb.append(getOnePanelState(txtDLSJ, ConstRuleI.CMD_INIT));
		} else if (!agentLogintime.equals("")) {
			sb.append(getOnePanelState(txtDLSJ, agentLogintime));
		}

		// ����ѯ��
		String txtRZXL = ConstRuleI.CMD_APENT_TXTRIZHIXUNLIANG;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(txtRZXL, ConstRuleI.CMD_INIT));
		}

		// ��Ŀ��ѯ��
		String cLMZXL = ConstRuleI.CMD_APENT_CLANMUZHIXUNLIANG;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cLMZXL, ConstRuleI.CMD_INIT));
		}

		// ������ʱ
		String tFWZS = ConstRuleI.CMD_APENT_TXTFUWUZHONGSHI;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(tFWZS, ConstRuleI.CMD_INIT));
		}

		// ����ʱ��
		String tBCSC = ConstRuleI.CMD_APENT_TXTBENCHISHICHANG;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(tBCSC, ConstRuleI.CMD_INIT));
		}

		// ��ǰ�Ŷ���
		String tDQPDS = ConstRuleI.CMD_APENT_TXTDANGQIANPAIDUISHU;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(tDQPDS, ConstRuleI.CMD_INIT));
		}

		// �����¼
		String cLDJL = ConstRuleI.CMD_APENT_COMLAIDIANJILU;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cLDJL, ConstRuleI.CMD_INIT));
		}

		// ����������
		String cZXZCR = ConstRuleI.CMD_APENT_ZAIXIANZHUCHIREN;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cZXZCR, ConstRuleI.CMD_INIT));
		}

		// ����ר��
		String cZXZJ = ConstRuleI.CMD_APENT_ZAIXIANZHUANJIA;
		if (ap.getAgentState() == null || ap.getAgentState().equals("")) {
			sb.append(getOnePanelState(cZXZJ, ConstRuleI.CMD_INIT));
		}

		// ���嶯��
		String iST = ConstRuleI.CMD_image_RINGUP;
		String ringState = ap.getIsRing();
		if (ringState.equals("0")) {
			sb.append(getButtonState(iST, ConstRuleI.IMAGE_STATE_ONE));
		} else if (ringState.equals("1")) {
			sb.append(getButtonState(iST, ConstRuleI.IMAGE_STATE_TWO));
		}

		// ժ��
		String iZJ = ConstRuleI.CMD_image_RINGUP;
		String hookState = ap.getIsoffHook();
		if (hookState.equals("0")) {
			sb.append(getButtonState(iZJ, ConstRuleI.IMAGE_STATE_ONE));
		} else if (hookState.equals("1")) {
			sb.append(getButtonState(iZJ, ConstRuleI.IMAGE_STATE_TWO));
		}

		// �һ�
		String iGJ = ConstRuleI.CMD_image_RINGUP;
		String onhookState = ap.getIsonHook();
		if (onhookState.equals("0")) {
			sb.append(getButtonState(iGJ, ConstRuleI.IMAGE_STATE_ONE));
		} else if (onhookState.equals("1")) {
			sb.append(getButtonState(iGJ, ConstRuleI.IMAGE_STATE_TWO));
		}

		// ת����
		String bZNX = ConstRuleI.CMD_ZHUANJIENIEXIAN;
		String zhuanNiexian = ap.getBZhuanniexian();
		if (zhuanNiexian.equals("0")) {
			sb.append(getButtonState(bZNX, ConstRuleI.CMD_INIT));
		} else if (zhuanNiexian.equals("1")) {
			sb.append(getButtonState(bZNX, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (zhuanNiexian.equals("2")) {
			sb.append(getButtonState(bZNX, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ������ʼ����
		String bThreecall = ConstRuleI.CMD_THREE_CALLING;
		String begincall = ap.getThreeCalling();
		if (begincall.equals("0")) {
			sb.append(getButtonState(bThreecall, ConstRuleI.CMD_INIT));
		} else if (begincall.equals("1")) {
			sb.append(getButtonState(bThreecall, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (begincall.equals("2")) {
			sb.append(getButtonState(bThreecall, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ������ʼͨ��
		String bThreetalk = ConstRuleI.CMD_THREE_BEGIN;
		String begintalk = ap.getThreeCallbegin();
		if (begintalk.equals("0")) {
			sb.append(getButtonState(bThreetalk, ConstRuleI.CMD_INIT));
		} else if (begintalk.equals("1")) {
			sb.append(getButtonState(bThreetalk, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (begintalk.equals("2")) {
			sb.append(getButtonState(bThreetalk, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ǿ��
		String bQiangchai = ConstRuleI.CMD_QIANGCHAI;
		String qiangchai = ap.getQiangchai();
		if (qiangchai.equals("0")) {
			sb.append(getButtonState(bQiangchai, ConstRuleI.CMD_INIT));
		} else if (qiangchai.equals("1")) {
			sb.append(getButtonState(bQiangchai, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (qiangchai.equals("2")) {
			sb.append(getButtonState(bQiangchai, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ǿ��
		String bQiangcha = ConstRuleI.CMD_QIANGCHA;
		String qiangcha = ap.getQiangcha();
		if (qiangcha.equals("0")) {
			sb.append(getButtonState(bQiangcha, ConstRuleI.CMD_INIT));
		} else if (qiangcha.equals("1")) {
			sb.append(getButtonState(bQiangcha, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (qiangcha.equals("2")) {
			sb.append(getButtonState(bQiangcha, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ר�ҹ�
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

		// ��ϯ��
		String bAgenthold = ConstRuleI.CMD_AGENTHOLD;
		String agenthold = ap.getAgentHold();
		if (agenthold.equals("0")) {
			sb.append(getButtonState(bAgenthold, ConstRuleI.CMD_INIT));
		} else if (agenthold.equals("1")) {
			sb.append(getButtonState(bAgenthold, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (agenthold.equals("2")) {
			sb.append(getButtonState(bAgenthold, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ������
		String bThreehold = ConstRuleI.CMD_THREEHOLD;
		String threeHold = ap.getThreeHold();
		if (threeHold.equals("0")) {
			sb.append(getButtonState(bThreehold, ConstRuleI.CMD_INIT));
		} else if (threeHold.equals("1")) {
			sb.append(getButtonState(bThreehold, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (threeHold.equals("2")) {
			sb.append(getButtonState(bThreehold, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ת����
		String bZWX = ConstRuleI.CMD_ZHUANJIEWAIXIAN;
		String zhuanWaixian = ap.getBZhuanjiewaixian();
		if (zhuanWaixian.equals("0")) {
			sb.append(getButtonState(bZWX, ConstRuleI.CMD_INIT));
		} else if (zhuanWaixian.equals("1")) {
			sb.append(getButtonState(bZWX, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (zhuanWaixian.equals("2")) {
			sb.append(getButtonState(bZWX, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ��½
		String bDL = ConstRuleI.CMD_APENT_DENGLU;
		String loginState = ap.getLoginState();
		if (loginState.equals("0")) {
			sb.append(getButtonState(bDL, ConstRuleI.CMD_INIT));
		} else if (loginState.equals("1")) {
			sb.append(getButtonState(bDL, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (loginState.equals("2")) {
			sb.append(getButtonState(bDL, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ��ͣ
		String bZT = ConstRuleI.CMD_APENT_ZHANTING;
		String signState = ap.getSignState();
		if (signState.equals("0")) {
			sb.append(getButtonState(bZT, ConstRuleI.CMD_INIT));
		} else if (signState.equals("1")) {
			sb.append(getButtonState(bZT, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (signState.equals("2")) {
			sb.append(getButtonState(bZT, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// �ȴ�
		String bDD = ConstRuleI.CMD_APENT_DENGDAI;
		String waitState = ap.getWaitState();
		if (waitState.equals("0")) {
			sb.append(getButtonState(bDD, ConstRuleI.CMD_INIT));
		} else if (waitState.equals("1")) {
			sb.append(getButtonState(bDD, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (waitState.equals("2")) {
			sb.append(getButtonState(bDD, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ����
		String bJT = ConstRuleI.CMD_APENT_JIETING;
		String telState = ap.getTelState();
		if (telState.equals("0")) {
			sb.append(getButtonState(bJT, ConstRuleI.CMD_INIT));
		} else if (telState.equals("1")) {
			sb.append(getButtonState(bJT, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (telState.equals("2")) {
			sb.append(getButtonState(bJT, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ת�Զ�
		String bZZD = ConstRuleI.CMD_APENT_ZHUANZHIDONG;
		String toIVR = ap.getToIVR();
		if (toIVR.equals("0")) {
			sb.append(getButtonState(bZZD, ConstRuleI.CMD_INIT));
		} else if (toIVR.equals("1")) {
			sb.append(getButtonState(bZZD, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (toIVR.equals("2")) {
			sb.append(getButtonState(bZZD, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ת��
		String bZY = ConstRuleI.CMD_APENT_ZHUANYI;
		String turnState = ap.getTurnState();
		if (turnState.equals("0")) {
			sb.append(getButtonState(bZY, ConstRuleI.CMD_INIT));
		} else if (turnState.equals("1")) {
			sb.append(getButtonState(bZY, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (turnState.equals("2")) {
			sb.append(getButtonState(bZY, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// �绰��
		String bDHB = ConstRuleI.CMD_APENT_DIANHUABU;
		String bookState = ap.getTelBookState();
		if (bookState.equals("0")) {
			sb.append(getButtonState(bDHB, ConstRuleI.CMD_INIT));
		} else if (bookState.equals("1")) {
			sb.append(getButtonState(bDHB, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (bookState.equals("2")) {
			sb.append(getButtonState(bDHB, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ���
		String bWH = ConstRuleI.CMD_APENT_WAIHU;
		String outCallState = ap.getOutCallState();
		if (outCallState.equals("0")) {
			sb.append(getButtonState(bWH, ConstRuleI.CMD_INIT));
		} else if (outCallState.equals("1")) {
			sb.append(getButtonState(bWH, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (outCallState.equals("2")) {
			sb.append(getButtonState(bWH, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ����¼��
		String bSTLY = ConstRuleI.CMD_APENT_SHOUTINGLUYIN;
		String listenState = ap.getListenWavState();
		if (listenState.equals("0")) {
			sb.append(getButtonState(bSTLY, ConstRuleI.CMD_INIT));
		} else if (listenState.equals("1")) {
			sb.append(getButtonState(bSTLY, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (listenState.equals("2")) {
			sb.append(getButtonState(bSTLY, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ����ͨ��
		String bSFTH = ConstRuleI.CMD_APENT_SHANFANFTONGHUA;
		String threeCallState = ap.getThreeCallState();
		if (threeCallState.equals("0")) {
			sb.append(getButtonState(bSFTH, ConstRuleI.CMD_INIT));
		} else if (threeCallState.equals("1")) {
			sb.append(getButtonState(bSFTH, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (threeCallState.equals("2")) {
			sb.append(getButtonState(bSFTH, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ��ʱ
		String bCS = ConstRuleI.CMD_APENT_CHAOSHI;
		String superTimeState = ap.getSuperTimeState();
		if (superTimeState.equals("0")) {
			sb.append(getButtonState(bCS, ConstRuleI.CMD_INIT));
		} else if (superTimeState.equals("1")) {
			sb.append(getButtonState(bCS, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (superTimeState.equals("2")) {
			sb.append(getButtonState(bCS, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// �绰����
		String bDHHY = ConstRuleI.CMD_APENT_DIANHUAHUIYI;
		String phoneConfence = ap.getPhoneConfenceState();
		if (phoneConfence.equals("0")) {
			sb.append(getButtonState(bDHHY, ConstRuleI.CMD_INIT));
		} else if (phoneConfence.equals("1")) {
			sb.append(getButtonState(bDHHY, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (phoneConfence.equals("2")) {
			sb.append(getButtonState(bDHHY, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// ������ʱ
		String bJSYS = ConstRuleI.CMD_APENT_JIESHUYANSHI;
		String endSuperState = ap.getEndSuperTimeState();
		if (endSuperState.equals("0")) {
			sb.append(getButtonState(bJSYS, ConstRuleI.CMD_INIT));
		} else if (endSuperState.equals("1")) {
			sb.append(getButtonState(bJSYS, ConstRuleI.AGENT_BUTTON_ONE));
		} else if (endSuperState.equals("2")) {
			sb.append(getButtonState(bJSYS, ConstRuleI.AGENT_BUTTON_TWO));
		}

		// �û�����
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

	// �ϳ��ַ��������Ϲ�Լ��ʽ���ַ���
	private String getOnePanelState(String name, String value) {
		StringBuilder sbd = new StringBuilder();

		sbd.append(name);
		sbd.append(ConstRuleI.SPLIT_SIGN_AT);
		sbd.append(value);
		sbd.append(ConstRuleI.SPLIT_SIGN_COMMA);

		return sbd.toString();
	}

	// �ϳ��ַ�������#�ŷָ�
	private String getButtonState(String name, String value) {
		StringBuilder sbd = new StringBuilder();

		sbd.append(name);
		sbd.append(ConstRuleI.SPLIT_SIGN_jing);
		sbd.append(value);
		sbd.append(ConstRuleI.SPLIT_SIGN_COMMA);

		return sbd.toString();
	}

	/**
	 * ��CtiBeanMap�����ctiBean��mainKey�ҵ�ctiBeanMap��key
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
	 * ��ivrStateMap���ҳ�û��ʹ�õĶ˿ں�
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
	 * ������ʹ�õĶ˿����ҵ�key�� RefreshPanelService ctiBeanMap ѭ�� ��ctiBean usingPort =
	 * pbxPortʱ ����key
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
	 * ������ʹ�õĶ˿����ҵ�mainKey��
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
	 * ���ݽ������˿ںŵõ���Ӧ��AgentBeans
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
