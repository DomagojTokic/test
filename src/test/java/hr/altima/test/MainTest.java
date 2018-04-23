package hr.altima.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MainTest {

	@Test
	public void createNodesTest() {
		Optional<Node> optionalNode;
		Node node;
		List<Pair> pairs = getPairs();
		
		Collection<Node> nodes = Main.createNodes(pairs);
		Assert.assertEquals(7, nodes.size());
		
		optionalNode = nodes.stream().filter(n -> n.getName().equals("Ivan")).findFirst();
		Assert.assertTrue(optionalNode.isPresent());
		
		node = optionalNode.get();
		Assert.assertEquals(3, node.getChildren().size());
		
		optionalNode = nodes.stream().filter(n -> n.getName().equals("Stjepan")).findFirst();
		Assert.assertTrue(optionalNode.isPresent());
		
		node = optionalNode.get();
		Assert.assertEquals(2, node.getParents().size());
	}
	
	@Test
	public void checkForCyclesFalse() {
		List<Pair> pairs = getPairs();
		
		Collection<Node> nodes = Main.createNodes(pairs);
		for(Node n : nodes) {
			Main.checkForCycles(n);
		}
	}
	
	@Test(expected = AltimaException.class)
	public void checkForCyclesTrue() {
		List<Pair> pairs = getPairs();
		pairs.add(new Pair("Ivan", "Stjepan"));
		
		Collection<Node> nodes = Main.createNodes(pairs);
		for(Node n : nodes) {
			Main.checkForCycles(n);
		}
	}
	
	private List<Pair> getPairs() {
		List<Pair> pairs = new ArrayList<>();
		
		pairs.add(new Pair("Marko", "Ivan"));
		pairs.add(new Pair("Stjepan", "Ivan"));
		pairs.add(new Pair("Ante", "Ivan"));
		
		pairs.add(new Pair("Ante", "Karlo"));
		pairs.add(new Pair("Branko", "Karlo"));
		pairs.add(new Pair("Dino", "Karlo"));
		
		pairs.add(new Pair("Stjepan", "Ante"));
		
		return pairs;
	}
}
