package com.rakshitlabs.textSummarizer.TextSummarizer.utils;



import EDU.oswego.cs.dl.util.concurrent.CopyOnWriteArrayList;
import com.rakshitlabs.textSummarizer.TextSummarizer.dtos.Edge;
import com.rakshitlabs.textSummarizer.TextSummarizer.dtos.Graph;
import com.rakshitlabs.textSummarizer.TextSummarizer.dtos.Node;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TextUtils {

    protected static List<Node> createNodes(List sentences){
        List<Node> nodes= new CopyOnWriteArrayList();


        return nodes;
    }



    protected boolean convertToGraph(List<Node> nodes, List<String> words, Graph g){

        StringBuffer preProcessedSentence= new StringBuffer();
        List<Edge> edgesList= new CopyOnWriteArrayList();
        words.forEach(word -> preProcessedSentence.append(" ").append(word));
        Node node= new Node(preProcessedSentence.toString(), null);
        return false;
    }


    protected static void calculatePageRank(Graph g, Map<Integer, String> summarizedTexts, int max) throws IOException {
        edu.uci.ics.jung.graph.Graph<Integer, String> graph= new DirectedSparseGraph<>();

        //Adding nodes and edges to the  Graph
        g.getNodes().stream().forEach(
               node -> {
                   graph.addVertex(node.getNodeId());
                   node.getEdges().stream().forEach(
                         edge ->   graph.addEdge(node.getNodeId() + "->" +edge.getV(), node.getNodeId(), edge.getV())
                   );
               }
        );

        PageRank<Integer, String> score= new PageRank<>(graph, 0.85);
        score.setMaxIterations(30);
        score.evaluate();


        //Print the sentences according to descending order of the page rank.
        Map<Double, String> scoreMap= new TreeMap<>(Comparator.reverseOrder());
        g.getNodes().forEach(
                node -> scoreMap.put(score.getVertexScore(node.getNodeId()), summarizedTexts.get(node.getNodeId()))
        );

        //Test by printing out
        scoreMap.forEach(
                (k,v) -> System.out.println(k.toString()+" ## "+v.toUpperCase())
        );
        writeOutputToFile(max, scoreMap);
    }
//    public boolean createEdges(Graph g){
//        return false;
//    }

    //Considering the first 3 lines of the text.
    public static void writeOutputToFile(int max, Map<Double, String> map) throws IOException {

        int count=0;
        try (FileWriter fileWriter = new FileWriter("D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\data\\output.txt")) {
            for (Map.Entry<Double, String> entry : map.entrySet()){
                if(count >= max)
                    break;
                fileWriter.write(entry.getValue());
                fileWriter.append("\n");
                count++;
            }
        }

    }


}
