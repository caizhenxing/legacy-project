package ocelot.common.key.impl;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import ocelot.common.key.KeyService;
import ocelot.common.key.SequenceService;
import ocelot.framework.base.container.SpringContainer;


public class KeyServiceImpl implements KeyService {
	private SequenceService sequenceService;// ע��ĳ־û���

	private static HashMap keyGeneratorSet = new HashMap(20);

	private static final int POOL_SIZE = 20;// �ش�С

	private static final int DIGIT = 10;// string key max digit

	private static String[] DIGIT_FORMAT = { "", "0", "00", "000", "0000",
			"00000", "000000", "0000000", "00000000", "000000000",
			"0000000000", "00000000000", "000000000000" };

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public KeyServiceImpl() {
	}

	public synchronized String getNext(String keyName) {
		String keyNameUpp = keyName.toUpperCase();// ��������keyNameת���ɴ�д��Oracle�������Ǵ�д��
		KeyStatus ks = null;
		// ������õ�keyName��HashMap��
		if (keyGeneratorSet.containsKey(keyNameUpp)) {
			ks = (KeyStatus) keyGeneratorSet.get(keyNameUpp);
		} else { // ������õ�keyNameû��HashMap��
			ks = new KeyStatus(keyNameUpp);
			keyGeneratorSet.put(keyNameUpp, ks);
		}
		return formPk(ks);
	}

	// handle pk bisness
	private String formPk(KeyStatus ks) {
		// return pkStr(getNext(ks));
		// return pkStr1(getNext(ks));
		return pkStr2(ks.getName(), getNext(ks));
	}

	// �����ļ򵥵�ҵ���߼�,��po������ֱֵ��ת���ɴ�
	private String pkStr(long l) {
		// ֻ�Ǽ򵥵�����ת����String
		return String.valueOf(l);
	}

	// ������ҵ���߼�,��po������ֵת���ɶ����Ĵ�
	private String pkStr1(long l) {
		// ֻ�Ǽ򵥵�����ת����String
		return String.valueOf(l);
	}

	// ������ҵ���߼�,��po������ֵת���ɶ����Ĵ���ǰ����ϱ�����
	private String pkStr2(String tableName, long l) {
		// add "table name" and "_"
		StringBuffer sb = new StringBuffer(tableName);
		sb.append("_");
		// add sequence,format 10digit
		sb.append(formatNum(l, DIGIT));
		return sb.toString();
	}

	/*
	 * format sequence to 10 digit
	 */
	private String formatNum(long lArg, int digit) {
		NumberFormat nf = new DecimalFormat(DIGIT_FORMAT[digit]);
		return nf.format(lArg).toString();
	}

	/*
	 * handle pk sequence
	 */
	private long getNext(KeyStatus ks) {
		// first time or to max
		if (ks.getMax() == 0 || ks.getNext() > ks.getMax()) {
			ks.setMax(sequenceService.getNext(ks.getName(), POOL_SIZE));
			// reFormat next
			ks.setNext(ks.getMax() - POOL_SIZE + 1);
		}
		long l = ks.getNext();
		ks.setNext(ks.getNext() + 1);
		return l;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// long lBegin0 = System.currentTimeMillis();
		SpringContainer g = SpringContainer.getInstance();
		// long lEnd0 = System.currentTimeMillis();
		

		KeyService key = (KeyService) g.getBean("KeyService");
		long lBegin = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			String ss = key.getNext("biz_store_info");
			System.out.println(ss);
		}

		long lEnd = System.currentTimeMillis();
		System.out.println("--------------------");
		System.out.print(lEnd - lBegin);

		/*
		 * long l=123456789; System.out.println(KeyServiceImpl.formatNum(l));
		 */
	}
}
