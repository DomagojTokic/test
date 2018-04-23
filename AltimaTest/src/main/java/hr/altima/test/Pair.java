package hr.altima.test;

public class Pair {
	
	private String parent;
	private String child;
	
	public Pair(String child, String parent) {
		this.child = child;
		this.parent = parent;
	}
	
	public String getParent() {
		return parent;
	}
	
	public String getChild() {
		return child;
	}
}
