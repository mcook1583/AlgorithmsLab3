import java.awt.Color;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class GraphQuestions {

	GraphDisplay gd = new GraphDisplay();
	
	public static void main(String[] args) {
		GraphQuestions q = new GraphQuestions();
		Graph graph = q.randomGraph(10,0.8);
		q.kruskal(graph);
		q.clearGraph(graph);
		q.prim(graph);
	}
	

	public Graph randomGraph(int n, double p){
		Graph g = new Graph();
	    gd.showInWindow(400,400,"A Random Graph");
		for (int i = 0; i < n; i++){
			Node node = new Node(i,Math.random(), Math.random());
			g.addNode(node.identifier,node.x,node.y);
			gd.addNode(node.identifier,node.x,node.y);
		}
		for(int j=0; j<2*n; j++){
			if(Math.random()<p){
				int node1 = (int)(j%n);
				int node2 = (int)(Math.random()*n);
				gd.addEdge(node1,node2,Color.black);
				g.addEdge(g.getAsNode(node1), g.getAsNode(node2));
			}
		}
		return g;
	}
	
	public TreeSet<Edge> prim(Graph graph){
		double[] distances = new double[graph.nodes.size()];
		for(int i=0;i<graph.nodes.size();i++){
			distances[i]=Double.POSITIVE_INFINITY;
		}
		TreeSet<Edge> edges = new TreeSet<Edge>();
		PriorityQueue<Edge> PQ = new PriorityQueue<Edge>();
		Node node = graph.nodes.get(0);
		gd.addNode(0, node.x, node.y, Color.WHITE);
		for(int i=0;i<graph.nodes.size()-1;i++){
			distances[node.identifier]=0;
			for(Node k : graph.getNeighbours(node)){
				if(graph.getWeight(node, k)<distances[k.identifier]){
					distances[k.identifier]=graph.getWeight(node, k);
					PQ.add(graph.getEdge(node,k));
				}
			}
			while(!PQ.isEmpty()){
				Edge nextEdge =  PQ.poll();
				if(distances[nextEdge.end.identifier]>0){
					edges.add(nextEdge);
					try{
						gd.addEdge(nextEdge.start.identifier,nextEdge.end.identifier,Color.red);
						Thread.sleep(1000);
					}catch(InterruptedException e) {}
					node = nextEdge.end;
					break;
				}
			}
		}
		return edges;
	}
	
	public TreeSet<Edge> kruskal(Graph graph){
		PriorityQueue<Edge> PQ = new PriorityQueue<Edge>();
		for(Edge edge : graph.edges){
			PQ.add(edge);
		}
		TreeSet<Edge> edges = new TreeSet<Edge>();
		int edgesAccepted = 0;
		DisjointSets dj = new DisjointSets(graph.nodes.size());
		while(edgesAccepted<graph.nodes.size()-1){
			Edge edge=PQ.poll();
			int findStart = dj.find(edge.start.identifier);
			int findEnd = dj.find(edge.end.identifier);
			if (findStart!=findEnd) {
			    dj.union(findStart, findEnd);
				edges.add(edge);
				edgesAccepted++;
				try{
					gd.addEdge(edge.start.identifier,edge.end.identifier,Color.red);
					Thread.sleep(1000);
				}catch(InterruptedException e) {}
			}
		}
		return edges;
	}
	
	public void clearGraph(Graph graph){
		for(Edge edge : graph.edges){
			gd.addEdge(graph.getIdentifier(edge.start),graph.getIdentifier(edge.end),Color.BLACK);
			}
	}
}
