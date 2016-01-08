package excellence.common.key.impl;

public class KeyStatus {
	private String name;
	private long max;
	private long next=1;
	public KeyStatus(String name) {
		this.name = name;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getNext() {
		return next;
	}
	public void setNext(long next) {
		this.next = next;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
