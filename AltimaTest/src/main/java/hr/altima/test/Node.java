package hr.altima.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Directed graph node
 */
public class Node {
	
	/** Node name */
	private String name;
	
	/** Node parents */
	private List<Node> parents;
	
	/** Node children */
	private List<Node> children;
	
	public Node() {
		name = "";
		children = new ArrayList<>();
		parents = new ArrayList<>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setParents(List<Node> parents) {
		this.parents = parents;
	}
	
	public List<Node> getParents() {
		return parents;
	}
	
	public void addParent(Node parent) {
		if (!parents.contains(parent)) {
			parents.add(parent);
		}
	}
	
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node child) {
		if (!children.contains(child)) {
			children.add(child);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Node && ((Node) obj).getName().equals(name);
	}
	
	public String toString(int depth) {
		String str = "";
		for (int i = 0; i < depth; i++) {
			str += "\t";
		}
		str += name;
		
		for (Node child : children) {
			str += "\n" + child.toString(depth + 1);
		}
		return str;
	}
}
