package com.rakshitlabs.textSummarizer.TextSummarizer.dtos;

public class Edge{
    //v is the end node.
    //w is the weight of the edge.
    int v,w;

    //Node is the target node to be passed.
    public Edge(Node node, int w) {
        this.v = node.getNodeId();
        this.w = w;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
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
