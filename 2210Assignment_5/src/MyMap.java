/**
 * @author William Zhang 251215208
 * The purpose of this class is to represent the roadmap where a graph will be used to store
 * the map and a path will be found from the starting point to the destination
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;

public class MyMap {
    //set variables and stack
    private Graph graph;
    private Stack<Node> path;
    private int start, destination, width, length;
    private int privateRoads, constructionRoads;

    //constructor for building a graph from the input file
    public MyMap(String inputFile) throws MapException {
    	//set Strings and counters
    	String currentLine;
        String prevLine;
    	int counterH = 0;
    	int counterV = 0;
        try {
			BufferedReader r = new BufferedReader(new FileReader(inputFile));
            //since scale is not used, just read the first line and do not do anything with it
            r.readLine();
            //read the starting and ending nodes
            start = Integer.parseInt(r.readLine());
            destination = Integer.parseInt(r.readLine());
            //read the length and width
            width = Integer.parseInt(r.readLine());
            length = Integer.parseInt(r.readLine());
            //read the number of maximum private and construction roads
            privateRoads = Integer.parseInt(r.readLine());
            constructionRoads = Integer.parseInt(r.readLine());
            //initialize the graph
            graph = new Graph(length * width);
            //while there is still a line in the file, loop
            currentLine = r.readLine();
            while (currentLine != null) {
            	//loop through each iteration
                for (int i = 0; i < currentLine.length(); i = i + 2) {
                    if (currentLine.charAt(i) == '+') {
                    	try {
                    		int k = i + 1;
                    		int j = i + 2;
	                        if (j < currentLine.length()) {
	                            if (currentLine.charAt(k) != 'B') {
	                            	//find value of the character in the file
	                                String string = String.valueOf(currentLine.charAt(k));
	                                //add to the edge
	                                graph.addEdge(graph.getNode(counterH), graph.getNode(counterH+1), string);
                            }
                        }
	                    //increase the counter to get the next node
                        counterH++;
                    } catch (GraphException e) {
						//if there is an error, throw exception
					}
                    }
                }
                //while there is still a line in the file, loop
                prevLine = r.readLine();
                if (prevLine != null) {
                	//loop through each iteration
                    for (int j = 0; j < prevLine.length(); j = j + 2) {
                    	try {
	                        if (prevLine.charAt(j) != 'B') {
	                        	//find value of the character in the file
	                            String string = String.valueOf(prevLine.charAt(j));
	                            //add to the edge
	                            graph.addEdge(graph.getNode(counterV), graph.getNode(counterV + width), string);
                        }
	                    //increase the counter to get the next node
                        counterV++;
                    } catch (GraphException e) {
						//if there is an error, throw exception
					}
                    }
                //otherwise break out of the program
                } else {
                	break;
                }
                //set the current line to be read
                currentLine = r.readLine();
            } 
        //catch all of the other exceptions if applicable
        } catch (FileNotFoundException e) {
        	throw new MapException("file does not exist");
        } catch (IOException e) {
        	System.out.println("cannot read file");
        }
    }
    //returns the graph representing the roadmap
    public Graph getGraph() {
        return graph;
    }
    //returns the id of the starting node
    public int getStartingNode() {
        return start;
    }
    //returns the id of the destination node
    public int getDestinationNode() {
        return destination;
    }
    //returns the max number allowed of private roads
    public int maxPrivateRoads() {
        return privateRoads;
    }
    //returns the max number of allowed construction roads
    public int maxConstructionRoads() {
        return constructionRoads;
    }
    //function returns a java iterator containing the nodes of a path from the start node to the destination node
    public Iterator findPath(int start, int destination, int maxPrivate, int maxConstruction) {
    	try {
    		//create new stack
    		path = new Stack<Node>();
    		//get the first and last nodes
	        Node first = graph.getNode(start);
	        Node last = graph.getNode(destination);
	        //return iterator using depth first search
	        return dfs(first, last, maxPrivate, maxConstruction);
    	} catch (GraphException e) {
    		//return null if exception is caught
    		return null;
		}
    }
    //helper function to find the iterator nodes using depth first search
    private Iterator dfs(Node first, Node last, int maxPrivate, int maxConstruction) {
    	//set private and construction road variables
    	int privateRoads = maxPrivate;
        int constructionRoads = maxConstruction;
        //mark the first node as true and push it into the stack
        first.markNode(true);
        path.push(first);
        //if the first node is not equal to the last one
        if (first.getId() != last.getId()) {
            try {
    	        Iterator iterate = graph.incidentEdges(first);
    	        //while there is a next node
    	        while (iterate.hasNext()) {
    	            Edge e = (Edge) iterate.next();
    	            Node nextNode = e.secondNode();
    	            //if the next node is equal to the first node, set it to be the first node
    	            if (nextNode == first) {
    	                nextNode = e.firstNode();
    	            }
    	            //if the next node has not been marked
    	            if(nextNode.getMark() == false) {
    	            	//if the type is a public road
    		            if (e.getType().contains("P")) {
    		                Iterator outputPath = dfs(nextNode, last, privateRoads, constructionRoads);
    		                //if the path is not null return it
    		                if (outputPath != null) {
    		                    return outputPath;
    		                }
    		            }
    		            //if the type is a private road
    		            else if (e.getType().contains("V")) {
    		            	if (privateRoads != 0) {
    		            		//take one off the max tally
	    		                privateRoads--;
	    		                Iterator outputPath = dfs(nextNode, last, privateRoads, constructionRoads);
	    		                //if the path is not null return it
	    		                if (outputPath != null) {
	    		                    return outputPath;
	    		                }
    		            	}
    		            }
    		            //if the type is a construction road
    		            else if (e.getType().contains("C")) {
    		            	if (constructionRoads != 0) {
    		            		//take one off the max tally
	    		                constructionRoads--;
	    		                Iterator outputPath = dfs(nextNode, last, privateRoads, constructionRoads);
	    		                //if the path is not null return it
	    		                if (outputPath != null) {
	    		                    return outputPath;
	    		                }
    		            	}
    		            }
    	            }
    	        }
    	        //mark the first node as false
    	        first.markNode(false);
    	        //pop the top of the stack
    	        path.pop();
            } catch (GraphException e) {
            	//catch any exceptions
    		}
        }
        //otherwise, return the path iterator
        else {
        	return path.iterator();
        }
        //return null if all else fails
        return null;
    }
}