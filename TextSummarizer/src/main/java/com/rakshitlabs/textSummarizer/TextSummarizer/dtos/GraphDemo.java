package com.rakshitlabs.textSummarizer.TextSummarizer.dtos;

import java.util.*;

public class GraphDemo {

    class Edge{
        //v is the end node.
        //w is the weight of the edge.
        int v,w;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "v=" + v +
                    ", w=" + w +
                    '}';
        }
    }
    //Add properties of the GraphDemo
    private UUID id;
    private String name;
    //private Object properties;
    List<Edge> G[];

    //Constructor of the graph accepting parameter as the no. of nodes required.
    public GraphDemo(String name, int n){
        this.id= UUID.randomUUID();
        this.name= name;
        G= new LinkedList[n]; //creates only a reference to the array and not actual linked lists[Nodes]
        for(int i=0; i < G.length; i++){
            G[i]= new LinkedList();
        }
    }

    //Adds an edge with u as the start vertex and v as the end vertex and w as the weight.
    void addEdge(int u, int v, int w) {
        //if same index is used multiple times, the edge is added in the start position of the list.
        G[u].add(0, new Edge(v,w));
    }


    //Checks if the node and the vertex is connected or not.
    //parameters are u= node of the GraphDemo; v = 2nd node of  the GraphDemo
    boolean isConnected(int u, int v){
        //It iterates through the start node of the graph and checks the edge present in the node if the end node is
        //as mentioned as the 2nd parameter.
        for(Edge i : G[u]){
            if(i.v == v)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "GraphDemo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", "+"\n\"G=" + Arrays.toString(G) +
                '}';
    }

    public static void main(String[] args) {
        GraphDemo g= new GraphDemo("Demo",10);
        g.addEdge(0,2,10);
        g.addEdge(0,5,15);
        g.addEdge(2,5,3);
        g.addEdge(9,3,4);
        System.out.println(g);
        System.out.println(g.isConnected(0,5));
    }
}
