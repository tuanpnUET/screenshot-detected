package app.detect.screenshot;

public class Item {
	private int id;
	private int val1;
	private int val2;
	private int timedown;
	private String win;
	public Item(int val1, int val2, int timedown, String win) {
		super();
		this.val1 = val1;
		this.val2 = val2;
		this.timedown = timedown;
		this.win = win;
	}
	
	public Item(int id, int val1, int val2, int timedown, String win) {
		super();
		this.id = id;
		this.val1 = val1;
		this.val2 = val2;
		this.timedown = timedown;
		this.win = win;
	}

	public int getVal1() {
		return val1;
	}
	public void setVal1(int val1) {
		this.val1 = val1;
	}
	public int getVal2() {
		return val2;
	}
	public void setVal2(int val2) {
		this.val2 = val2;
	}
	public int getTimedown() {
		return timedown;
	}
	public void setTimedown(int timedown) {
		this.timedown = timedown;
	}
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
	
	public String toInsertData() {
		return "(" + val1 + "," + val2 + "," + timedown + "," + "'"+win + "')";
	}
	
	@Override
	public String toString() {
		return "(id=" + id + ", val1=" + val1 + ", val2=" + val2 + ", timedown=" + timedown + ", win=" + win + ")";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
