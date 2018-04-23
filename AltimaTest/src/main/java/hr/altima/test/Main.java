package hr.altima.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates directed graphs for child-parent pairs and prints result. Exception is thrown if any of the graphs have
 * cycle.
 */
public class Main {
	
	public static void main(String[] args) {
		if (args.length > 1) {
			System.out.println("Please enter path to input file as an argument");
			return;
			
		} else if (args.length == 0) {
			System.out.println("This is a program for printing the structure of a directed graph. Please enter input " +
					"file as a first argument of program. Input file has format of multiple <child> <parent> lines which " +
					"will be used to create a directed graph.");
			return;
		}
		
		List<Pair> childParentPairs = readInputFile(args[0]);
		Collection<Node> nodes = createNodes(childParentPairs);
		
		for (Node node : nodes) {
			checkForCycles(node);
		}
		
		printResult(nodes);
	}
	
	/**
	 * Creates graph nodes from list of child-parent pairs.
	 *
	 * @param childParentPairs child-parent pairs
	 * @return list of graph nodes
	 */
	public static Collection<Node> createNodes(List<Pair> childParentPairs) {
		Map<String, Node> nodeMap = new HashMap<>();
		
		for (Pair pair : childParentPairs) {
			Node parentNode;
			if (nodeMap.containsKey(pair.getParent())) {
				parentNode = nodeMap.get(pair.getParent());
			} else {
				parentNode = new Node();
				parentNode.setName(pair.getParent());
				nodeMap.put(pair.getParent(), parentNode);
			}
			
			Node childNode;
			if (nodeMap.containsKey(pair.getChild())) {
				childNode = nodeMap.get(pair.getChild());
			} else {
				childNode = new Node();
				childNode.setName(pair.getChild());
				nodeMap.put(pair.getChild(), childNode);
			}
			
			parentNode.addChild(childNode);
			childNode.addParent(parentNode);
		}
		
		return nodeMap.values();
	}
	
	/**
	 * Checks if a node is a part of the cycle. If it is, exception is thrown.
	 *
	 * @param node Node for cycle checking
	 */
	public static void checkForCycles(Node node) {
		Stack<Node> stack = new Stack<>();
		
		stack.push(node);
		visitChildren(node, stack);
	}
	
	/**
	 * Recursive visit of children to check if they're part of the cycle.
	 *
	 * @param node  Child node
	 * @param stack Stack of already visited nodes
	 */
	public static void visitChildren(Node node, Stack<Node> stack) {
		List<Node> children = node.getChildren();
		for (Node child : children) {
			if (!stack.contains(child)) {
				stack.push(child);
				visitChildren(child, stack);
				stack.pop();
			} else {
				throw new AltimaException("Found cycle in parent-child relationships file!");
			}
		}
	}
	
	/**
	 * Prints out the resulting graph.
	 *
	 * @param nodes Graph nodes
	 */
	public static void printResult(Collection<Node> nodes) {
		List<Node> roots = new ArrayList<>();
		for (Node node : nodes) {
			if (node.getParents().isEmpty())
				roots.add(node);
		}
		
		for (Node root : roots) {
			System.out.println(root.toString(0));
		}
	}
	
	/**
	 * Reads input file and creates list of pairs.
	 *
	 * @param filePath Path to input file
	 * @return list of child-parent pairs
	 */
	private static List<Pair> readInputFile(String filePath) {
		List<Pair> pairs = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			List<String> lines = stream.collect(Collectors.toList());
			
			String[] names;
			for (String line : lines) {
				names = line.split(" ");
				
				if (names.length == 2) {
					pairs.add(new Pair(names[0], names[1]));
				} else if (line.trim().length() != 0) {
					throw new AltimaException("Found invalid input: " + line);
				}
			}
		} catch (IOException e) {
			System.err.println("Unable to read input file " + filePath);
		}
		return pairs;
	}
	
}
