/**
 * @author William Zhang 251215208
 * The purpose of this class is to represent an edge of the graph
 */
public class Edge {
	private Node firstNode;
	private Node secondNode;
	private String type;
	
	public Edge(Node u, Node v, String type) {
		firstNode = u;
		secondNode = v;
		this.type = type;
	}
	
	public Node firstNode() {
		return this.firstNode;
	}
	
	public Node secondNode() {
		return this.secondNode;
	}
	
	public String getType() {
		return this.type;
	}
}