package com.rakshitlabs.textSummarizer.TextSummarizer.detectors;

import com.rakshitlabs.textSummarizer.TextSummarizer.dtos.Edge;
import com.rakshitlabs.textSummarizer.TextSummarizer.dtos.Graph;
import com.rakshitlabs.textSummarizer.TextSummarizer.dtos.Node;
import com.rakshitlabs.textSummarizer.TextSummarizer.utils.TextUtils;
import opennlp.tools.postag.POSSample;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SentenceDetector extends TextUtils {

    public String[] getSentences(String filePath){
        try (InputStream inputStream= new FileInputStream("D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\models\\en-sent.bin")){
            //Load the model
            SentenceModel sentenceModel= new SentenceModel(inputStream);

            //Instantiating the sentenceMR class with the model
            SentenceDetectorME sentenceDetectorME= new SentenceDetectorME(sentenceModel);

            //Detecting and printing the sentences
            String[] sentences= sentenceDetectorME.sentDetect(
                    new BufferedReader(new InputStreamReader(
                            new FileInputStream(filePath))).lines().collect(Collectors.joining("\n")));
            //todo check the . and the prefixed word
           /* for (int i = 0; i < sentences.length; i++) {
                System.out.println(sentences[i]);
            }
*/
            return sentences;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }


    private Graph createEdges(Graph g) {
        List<Node> lookUpNodes= g.getNodes();
        for(int i=0; i < lookUpNodes.size() - 1; i++){
            Set<String> words= Arrays.stream(lookUpNodes.get(i).getSentence().trim().toLowerCase().split(" ")).collect(Collectors.toSet());
                for (int j = 0; j < lookUpNodes.size(); j++) {
                    if(i == j)
                        continue;
                    Map<Integer, Integer> edgeMap= new HashMap<>();
                        int weight = 0;
                        for (String word : words) {
                            word = word.replaceAll("[.,;$]", "").trim();
                            if (lookUpNodes.get(j).getSentence().toLowerCase().contains(word)) {
                                weight++;
                            }
                        }
                        if(weight > 0) {
                            edgeMap.merge(lookUpNodes.get(j).getNodeId(), weight, (w1, w2) ->{
                                w1 += w2;
                                return w1;
                            });
                        }
                        for(Map.Entry<Integer, Integer> edgeEntry : edgeMap.entrySet()){
                            lookUpNodes.get(i).setEdge(new Edge(lookUpNodes.get(j), edgeEntry.getValue()));
                    }
                }
        }

          g.getNodes().forEach(
                   node -> g.getG().put(node.getNodeId(), node.getEdges())
            );
        return g;
    }



    public static void main(String[] args) {
        Graph G= new Graph("Text Summarizer");
        POSDetector posDetector= new POSDetector();
        SentenceDetector sentenceDetector= new SentenceDetector();
        Map<Integer, String> summarizedTexts= new HashMap<>();
        try {
            System.out.println("Detecting Sentences...");
        String[] sentences= sentenceDetector.getSentences("D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\data\\sentence.txt");
            System.out.println("Getting the POS tag for each words in the sentences and creating nodes...");
        for (int i = 0; i < sentences.length; i++) {
            String processedSentence= posDetector.detectPOS(sentences[i]);
            Node node= new Node(processedSentence.trim(), null);
            G.addNode(node);
            summarizedTexts.put(node.getNodeId(),sentences[i]); //put the original sentences and the node ID in a map.
        //    System.out.println("Processed_sentences: "+processedSentence);
        }
            System.out.println("Creating Edges of the Graph...");
        G= sentenceDetector.createEdges(G);
            System.out.println("Calculating the pageRank of each sentences in the text, with max iterations : 30");
        calculatePageRank(G, summarizedTexts, 6);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
