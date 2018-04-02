package com.rakshitlabs.textSummarizer.TextSummarizer.dtos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Node {
    private int nodeId;
    String sentence;
    private List<Edge> edges;
    //private Map<String, List<String>> words;

    public Node(String sentence, List<Edge> edges) {
        this.nodeId= new Random().nextInt() * 100;
        this.sentence = sentence;
        this.edges = edges != null && edges.size() != 0 ? edges : new LinkedList<>();
        //this.words= null == words || words.isEmpty()? new LinkedHashMap<>() : words;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getSentence() {
        return sentence;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdge(Edge edge) {
        this.edges.add(edge);
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId=" + nodeId +
                ", sentence='" + sentence + '\'' +
                '}';
    }
}
