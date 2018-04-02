package com.rakshitlabs.textSummarizer.TextSummarizer.dtos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {

    //Add properties of the GraphDemo
    private UUID id;
    private String name;
    private List<Node> nodes;
    //private Object properties;
    Map<Integer, List<Edge>> G; //Integer is the nodeID

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Map<Integer, List<Edge>> getG() {
        return G;
    }


    //Constructor of the graph accepting parameter as the no. of nodes required.
    public Graph(String name){
        this.id= UUID.randomUUID();
        this.name= name;
        this.nodes= new CopyOnWriteArrayList<>();
        G= new ConcurrentHashMap<>();
    }

 /*   //Adds an edge with u as the start vertex and v as the end vertex and w as the weight.
    public void addEdges(int nodeId, Set<Edge> edges) {
        //if same index is used multiple times, the edge is added in the start position of the list.
        G.merge(nodeId, new LinkedList<>(edges), (edges1, edges2) ->{
            edges1.addAll(edges2);
            return edges1;
        });
    }*/

    public void addNode(Node node){
            this.nodes.add(node);
            this.G.put(node.getNodeId(), new ArrayList<>());
    }




    //Checks if the node and the vertex is connected or not.
    //parameters are u= node of the GraphDemo; v = 2nd node of  the GraphDemo
    //As it is a directed graph, if, Node1 -> Node2, then only Node1 is connected to Node2
    boolean isConnected(int node1, int node2){
        //It iterates through the start node of the graph and checks the edge present in the node if the end node is
        //as mentioned as the 2nd parameter.
        List<Edge> edgeList= G.get(node1);
        for(Edge edge : edgeList){
            if(edge.v == node2 )
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nodes=" + nodes +
                ", G=" + G +
                '}';
    }

  /*  public static void main(String[] args) {
        Graph g= new Graph("TextSummarizerGraph");
        List<Node> nodes= Arrays.asList(new Node( null), new Node( null), new Node( null), new Node( null));

        g.addEdges(0, Stream.of(new Edge(nodes.get(2), 10), new Edge(nodes.get(3), 15)).collect(Collectors.toSet()));
        g.addEdges(2, Collections.singleton(new Edge(nodes.get(3), 3)));
        g.addEdges(3, Collections.singleton(new Edge(nodes.get(0), 4)));
        g.addEdges(1, Collections.singleton(new Edge(nodes.get(0), 5)));
        g.addEdges(4, Collections.singleton(new Edge(nodes.get(1), 7)));
        System.out.println(g);
        System.out.println(g.isConnected(0,2));
    }*/
}
