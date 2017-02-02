import java.util.ArrayList;
import java.util.Vector;

public class Graph {

	protected Vector<Node> nodes;
	protected Vector<Edge> edges;

	public Graph() {
		nodes = new Vector<Node>();
		edges = new Vector<Edge>();
	}

	public void addNode(int identifier, double x, double y) {
		nodes.add(new Node(identifier, x, y));
	}
	
	public void addEdge(Node node1, Node node2){
		edges.add(new Edge(node1,node2));
		edges.add(new Edge(node2,node1));
	}
	
	public boolean edgeExist(Node node1, Node node2){
		for(Edge edge : edges){
			if(edge.start.equals(node1)&&edge.end.equals(node2)){
				return true;
			}
		}
		return false;
	}
	
	public Edge getEdge(Node node1, Node node2){
		for(Edge edge : edges){
			if(edge.start.equals(node1)&&edge.end.equals(node2)){
				return edge;
			}
		}
		throw new NullPointerException();
	}
	
	public double getWeight(Node node1, Node node2){
		if(edgeExist(node1,node2)){
			return getEdge(node1,node2).getWeight();
		}
		throw new NullPointerException();
	}
	
	public ArrayList<Node> getNeighbours(Node node){
		ArrayList<Node> neighbours = new ArrayList<Node>();
		for(Edge edge: edges){
			if(edge.start.equals(node)){
				neighbours.add(edge.end);
			}
			if(edge.end.equals(node)){
				neighbours.add(edge.start);
			}
		}
		return neighbours;
	}
	
	public Node getAsNode(int identifier){
		for(Node n : nodes){
			if(n.identifier == identifier){
				return n;
			}
		}
		throw new NullPointerException();
	}
	
	public int getIdentifier(Node node){
		for(Node n : nodes){
			if(node.equals(n)){
				return n.identifier;
			}
		}
		throw new NullPointerException();
	}
}

class Node {

	public int identifier;
	public double x, y;
	
	public Node(int identifier, double x, double y) {
		this.identifier = identifier;
		this.x = x;
		this.y = y;
	}
}

class Edge implements Comparable<Edge>{

	protected double weight;
	protected Node start;
	protected Node end;
	
	public Edge(Node start, Node end) {
		this.start=start;
		this.end=end;
		this.weight=calculateWeight();
	}
	
	private double calculateWeight(){
		double x = start.x - end.x;
		double y = start.y - end.y;
		return Math.sqrt((x*x)+(y*y));
	}
	
	public double getWeight(){
		return weight;
	}

	public int compareTo(Edge e) {
		if(this.weight>e.weight){
			return 1;
		}else{
			if(this.weight==e.weight){
				return 0;
			}else{
				return -1;
			}
		}
		
	}
}
