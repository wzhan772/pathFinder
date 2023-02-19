/**
 * @author William Zhang 251215208
 * The purpose of this class is to represent a node of the graph
 */
public class Node {
	private int id;
	private boolean mark;
	
	public Node(int id) {
		this.id = id;
		mark = false;
	}
	
	public void markNode(boolean mark) {
		this.mark = mark;
	}
	
	public boolean getMark() {
		return this.mark;
	}
	
	public int getId() {
		return this.id;
	}
}