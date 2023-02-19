/**
 * @author William Zhang 251215208
 * The purpose of this class is to represent an undirected graph
 */

import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT {
	//initialize nodes
	private int nodeCount;
	private Node nodes[];
	private Edge edges[][];
	
	//function that creates a graph with n nodes and no edges
	public Graph(int n) {
		//create nodes and edges
		nodes = new Node[n];
		edges = new Edge[n][n];	
		nodeCount = n;
		//loop through to create new nodes
		for (int i = 0; i < n; i++){
            Node temp = new Node(i);
            nodes[i] = temp;
            for (int j = 0; j < n; j++) {
				edges[i][j] = null;
			}
        }	
	}
	
	//function that returns the node with the specified id
	public Node getNode(int id) throws GraphException{
		//if no node with this id exists, an exception is thrown
		if(id < 0 || id >= nodeCount) {
            throw new GraphException("a node with this name does not exist in the graph");
		}
		//return node with id
		else {
            return nodes[id];
        }
	}
	
	//adds an edge of the given type connecting u and v
	public void addEdge(Node u, Node v, String edgeType) throws GraphException{
		//create boolean to represent validity of both nodes
		boolean valid;
		try {
			//get each id from nodes
            getNode(u.getId());
            getNode(v.getId());
            //if the id exists for both nodes, set to true
            valid = true;
        }
        catch (GraphException e) {
        	//if the id does not exist, set to false
            valid = false;
        }
		//if both nodes exist and therefore are true
		if(valid == true) {
			//set variables
			int nodeFirst = u.getId();
			int nodeSecond = v.getId();
			Edge placeholder = edges[nodeFirst][nodeSecond];
			//if there is already an edge connecting the given nodes
			if(placeholder != null) {
				throw new GraphException("an edge already exists for these nodes");
	        }
			else {
				edges[nodeFirst][nodeSecond] = new Edge(u,v,edgeType);
                edges[nodeSecond][nodeFirst] = new Edge(u,v,edgeType);			}
		}
		//if the node(s) does not exist
		else {
			throw new GraphException("no node associated with u or v exists in the graph");
		}		
	}
	
	//function returns a java iterator storing all the edges incident on node u
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		//nullChecker
		boolean nullCheck = false;
		//if the node does not exist, throw an exception
		if (u.getId() >= nodeCount || u.getId() < 0 || nullCheck == true) {
			throw new GraphException("the node is not in the graph");
		}
		//if the nodes exist
		else {
            getNode(u.getId());
            //stack to store all the edges
            Stack<Edge> e = new Stack<>();
            for(int i = 0; i < nodeCount; i++){
            	if(edges[u.getId()][i] == null) {
            		nullCheck = true;
            	}
            	else {
            		e.push(edges[u.getId()][i]);
            	}
            }
            return e.iterator();
        }
	}
	
	//function returns the edge connecting nodes u and v
	public Edge getEdge(Node u, Node v) throws GraphException {
		//create boolean to represent validity of both nodes
		boolean valid;
		try {
			//get each id from nodes
            getNode(u.getId());
            getNode(v.getId());
            //if the id exists for both nodes, set to true
            valid = true;
        }
        catch (GraphException e){
        	//if the id does not exist, set to false
            valid = false;
        }
		//if the nodes do not exist throw an exception
		if(valid == false) {
			throw new GraphException("the node is not in this graph");
		}
		//if the nodes do exist
		else
		{
			//find and return the edge connecting nodes u and v
            int nodeFirst = u.getId();
            int nodeSecond = v.getId();
            if(edges[nodeFirst][nodeSecond] != null) {
            	return edges[nodeFirst][nodeSecond];
            }
            //if the edge does not exist
            else {
            	throw new GraphException("this edge does not exist");
            }
        }
	}
	
	//function returns true if nodes u and v are adjacent
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		//create boolean to represent validity of both nodes
		boolean valid;
		try {
			//get each id from nodes
            getNode(u.getId());
            getNode(v.getId());
            //if the id exists for both nodes, set to true
            valid = true;
        }
        catch (GraphException e){
        	//if the id does not exist, set to false
            valid = false;
        }
		//if the id does not exist, throw an exception
		if(valid == false) {
			throw new GraphException("the node is not in this graph");
		}
		//if the nodes do exist
		else {
            int nodeFirst = u.getId();
            int nodeSecond = v.getId();
            //return outcome of nodes
            return (edges[nodeFirst][nodeSecond] != null);
        }
	}
}