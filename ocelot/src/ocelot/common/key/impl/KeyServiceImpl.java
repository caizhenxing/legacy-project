package ocelot.common.key.impl;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import ocelot.common.key.KeyService;
import ocelot.common.key.SequenceService;
import ocelot.framework.base.container.SpringContainer;


public class KeyServiceImpl implements KeyService {
	private SequenceService sequenceService;// 注入的持久化类

	private static HashMap keyGeneratorSet = new HashMap(20);

	private static final int POOL_SIZE = 20;// 池大小

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
		String keyNameUpp = keyName.toUpperCase();// 将传进的keyName转换成大写（Oracle表名都是大写）
		KeyStatus ks = null;
		// 如果调用的keyName在HashMap中
		if (keyGeneratorSet.containsKey(keyNameUpp)) {
			ks = (KeyStatus) keyGeneratorSet.get(keyNameUpp);
		} else { // 如果调用的keyName没在HashMap中
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

	// 主键的简单的业务逻辑,把po的序列值直接转换成串
	private String pkStr(long l) {
		// 只是简单的数字转换成String
		return String.valueOf(l);
	}

	// 主键的业务逻辑,把po的序列值转换成定长的串
	private String pkStr1(long l) {
		// 只是简单的数字转换成String
		return String.valueOf(l);
	}

	// 主键的业务逻辑,把po的序列值转换成定长的串，前面加上表名称
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
