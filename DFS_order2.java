package opt_SXC180048;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	Graph gr;
	boolean visit[];
	Queue<Vertex> qulist;
    public static class DFSVertex implements Factory {
	int cno;
	
	public DFSVertex(Vertex u) {
	}
	public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
	super(g, new DFSVertex(null));
	gr = g;
	visit = new boolean[gr.size()];
	qulist = new LinkedList<Vertex>();
    }

    public static DFS depthFirstSearch(Graph g) {
	return null;
    }

    public void DFSSort(Vertex v)
    {
    	 visit[v.getIndex()] = true; 
         // Recur for all the vertices adjacent to this  vertex 
        for(Edge u:gr.outEdges(v))
         { 
        	if(v.equals(u.toVertex()))
        			 continue;
             if (!visit[u.toVertex().getIndex()]) 
             {
                 DFSSort(u.toVertex());
             }
         } 
         // Push current vertex to stack which stores result 
        qulist.add(v);
    }
    
    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
    	List<Vertex> list1 = new LinkedList<>();
    	for(Vertex u: gr) {
    	    visit[u.getIndex()] = false;
    	}
    	for(Vertex u:gr)
    	{
    		if(visit[u.getIndex()]== false)
    			DFSSort(u);
    	}
    	Stack<Vertex> st = new Stack<>();
    	while(!qulist.isEmpty())
    	{
    		st.add(qulist.peek());
    		qulist.remove();
    	}
    	while(!st.isEmpty())
    	{
    		qulist.add(st.peek());
    		st.pop();
    	}
    	list1 = (List<Vertex>) qulist;
    	  return list1;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
    	
	return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
	return get(u).cno;
    }
    
    private boolean isCyclicRec(Vertex i, boolean[] visited, 
            boolean[] recStack)  
    {  
    	if (recStack[i.getIndex()]) 
    			return true; 

    	if (visited[i.getIndex()]) 
    			return false; 

    	visited[i.getIndex()] = true; 

    	recStack[i.getIndex()] = true; 
    	for(Edge c:gr.outEdges(i))
    	{
    		if(!c.toVertex().equals(c.fromVertex()))
    		{
    			if(i.equals(c.toVertex()))
    				continue;
    		if (isCyclicRec(c.toVertex(), visited, recStack)) 
    				return true;
    		}
    		else
    			return true;
    	}
    	recStack[i.getIndex()] = false; 
    	return false; 
    }
    
    public boolean isCyclic(Graph g)
    {
    	boolean[] visited = new boolean[g.size()]; 
        boolean[] recStack = new boolean[g.size()];
    	for(Vertex u: g) {
    	    visited[u.getIndex()] = false;
    	    recStack[u.getIndex()] = false;
    	}
        for(Vertex i:g)
        {
        	if(visit[i.getIndex()]== false)
        	{
        		if (isCyclicRec(i, visited, recStack)) 
        			return true;
        	}
        }
    	return false;
    }
    
    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) 
    {
	DFS d = new DFS(g);
	if(d.isCyclic(g))
		return null;
	return d.topologicalOrder1();
    }
public  List<Vertex> topologicalOrder2()
{
	int indegree[] = new int[gr.size()];
	for(Vertex v:gr)
	{
		for(Edge e:gr.outEdges(v))
		{
			if(!e.toVertex().equals(e.fromVertex()))
    		{
    			if(v.equals(e.toVertex()))
    				continue;
			indegree[e.toVertex().getIndex()]++;
    		}
		}
	}
	Queue<Vertex> qu = new LinkedList<Vertex>();
	for(Vertex v:gr)
	{
		if(indegree[v.getIndex()] == 0)
			qu.add(v);
	}
	int cnt =0;
	List<Vertex> result = new LinkedList<>();
	while(!qu.isEmpty())
	{
		Vertex v_poll = qu.poll();
		result.add(v_poll);
		for(Edge e:gr.outEdges(v_poll))
		{
			if(!e.toVertex().equals(e.fromVertex()))
    		{
    			if(v_poll.equals(e.toVertex()))
    				continue;
    			if(--indegree[e.toVertex().getIndex()]==0)
    			{
				qu.add(e.toVertex());
    			}
    		}
		}
		cnt++;
	}
	if(cnt != gr.size())
	{
		System.out.println("Cycle is present");
		return null;
	}	
	return result;
}

// Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
    	DFS d= new DFS(g);
    	if(d.isCyclic(g))
    		return null;
	return d.topologicalOrder2();
    }

    public static void main(String[] args) throws Exception {
    String string = "10 12   1 3 2   1 8 3   2 4 5   3 2 4   4 7 1   8 5 7   8 2 1  5 4 1  5 10 1  6 8 1   6 10 1  10 9 1 0";
	//String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0 ";	//   7 1 1 0";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
	
	// Read graph from input
        Graph g = Graph.readGraph(in);
        System.out.println(g.size());
	g.printGraph(false);

	DFS d = new DFS(g);
	int numcc = d.connectedComponents();
	System.out.println("Number of components: " + numcc + "\nu\tcno");
	for(Vertex u: g) {
	    System.out.println(u + "\t" + d.cno(u));
	}
	List<Vertex> lst = d.topologicalOrder2(g);
	System.out.println("List:"+lst);
    }
}
