package sxc180048;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.AdjList;
import rbk.Graph.ArrayIterator;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	int g_size;
	Graph gr;
	boolean visit[];
	List<Vertex> list;
	List<AdjList> adjlist;
    public static class DFSVertex implements Factory {
	int cno;
	
	public DFSVertex(Vertex u) {
	}
	public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
	super(g, new DFSVertex(null));
	g_size = g.size();
	gr = g;
	visit = new boolean[g_size];
	 list = new LinkedList<>();
	 adjlist = new LinkedList<>();
    }

    public static DFS depthFirstSearch(Graph g) {
    	DFS d = new DFS(g);
    	for(Vertex v: g)
    	{
    	d.visit[v.getIndex()] = true;
    	for(Edge e : g.outEdges(v))
    	{
    		if(v.equals(e.toVertex()))
    			continue;
    		 if (!d.visit[e.toVertex().getIndex()]) 
    	            depthFirstSearch(g);
    	}
    	d.list.add(v);
    	}
	return d;
    }

    public void topsort(Vertex v)
    {
    	 visit[v.getIndex()] = true; 
         //Integer i; 
         Vertex i;
   
         // Recur for all the vertices adjacent to this 
         // vertex 
         AdjList adj = gr.adj(v);

        for(Edge u:gr.outEdges(v))
         { 
        		 if(v.equals(u.toVertex()))
        			 continue;
             //i = it.next();
             System.out.println("Next:"+u.toVertex());
             if (!visit[u.toVertex().getIndex()]) 
             {
            	// visit[i.getIndex()] = true;
                 topsort(u.toVertex());
             }
         } 
   
         // Push current vertex to stack which stores result 
         System.out.println("Push :"+v);
         list.add(v);
    }
    
    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
    	List<Vertex> list1 = new LinkedList<>();
    	//boolean visited[]= new boolean[g_size]; 
    	Iterator<Vertex> it = gr.iterator();
    	for(Vertex u: gr) {
    	    System.out.println(u.getIndex() + " : "+ u.getName());
    	    visit[u.getIndex()] = false;
    	}
    	
    	for(Vertex u:gr)
    	{
    		//if(u.inDegree() == 0)
    		{
    		if(visit[u.getIndex()]== false)
    			topsort(u);
    		//break;
    		}
    	}
    	
    	  return list;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
	return 0;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
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
    	//System.out.println("Out Edges:"+gr.outEdges(i));
    	for(Edge c:gr.outEdges(i))
    	{
    		if(!c.toVertex().equals(c.fromVertex()))
    		{
    			if(i.equals(c.toVertex()))
    				continue;
    		//System.out.println("Edges:"+c);
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
        //for (int i = 0; i < g.size(); i++)
    	for(Vertex u: g) {
    	    //System.out.println(u.getIndex() + " : "+ u.getName());
    	    visited[u.getIndex()] = false;
    	    recStack[u.getIndex()] = false;
    	}
        for(Vertex i:g)
        {
        	//System.out.println("Vertex:"+i.getName());
        	if(visit[i.getIndex()]== false)
        	{
        		if (isCyclicRec(i, visited, recStack)) 
        			return true;
        	}
        }
    	return false;
    }
    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
    	
	DFS d = new DFS(g);
	
	if(d.isCyclic(g))
		return null;
	return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	return null;
    }

    public static void main(String[] args) throws Exception {
    String string = "10 12   1 3 2   1 8 3   2 4 5   3 2 4   4 7 1   8 5 7   8 2 1  5 4 1  5 10 1  6 8 1  6 10 1  10 9 1 0";
	//String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
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
	if(!d.isCyclic(g))
	{
	List<Vertex> lst = d.topologicalOrder1(g);
	System.out.println("List:"+lst);
	}
	else
		System.out.println("Cyclic Graph\n");
	DFS d1 = d.depthFirstSearch(g);
	System.out.println("DFS:"+d1.list);
//	for(Vertex x : d.topologicalOrder1())
    }
}