package com.rakshitlabs.textSummarizer.TextSummarizer.detectors;

import com.rakshitlabs.textSummarizer.TextSummarizer.utils.TextUtils;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class POSDetector extends TextUtils {

    public String detectPOS(String sentence) {
        StringBuffer processed_sentence= new StringBuffer();
        try (InputStream inputStream = new FileInputStream("D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\models\\en-pos-maxent.bin")) {
            //Load the model
            POSModel posModel= new POSModel(inputStream);

            //Instantiate the PostTaggerME class to load the trained model
            POSTaggerME posTaggerME= new POSTaggerME(posModel);

            //Tokenizing the sentence
            WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
            String[] tokens= whitespaceTokenizer.tokenize(sentence);

            //Generating the tags
            String[] tags= posTaggerME.tag(tokens);

            //Lemmatizing the words
            String[] lemmas= new Lemmantizer().lemmantize(tokens, tags);

            //Printing the tagged sentence
            POSSample sample= new POSSample(tokens, tags);

            for (int i=0; i< sample.getTags().length; i++){
                String tag= sample.getTags()[i];
                if("NNP".equalsIgnoreCase(tag) || "NN".equalsIgnoreCase(tag) || "NNS".equalsIgnoreCase(tag) || "JJ".equalsIgnoreCase(tag)){
                    processed_sentence.append(" ").append(String.valueOf(sample.getSentence()[i]));
                }
            }

            //Filter out all the words other than noun and adjective.
            //Call TextUtils convertToGraph() method to convert each processed sentence to graphs
            //Calculate the pagerank of the processed-Sentence to create the matrix.
            return String.valueOf(processed_sentence);
        } catch(IOException e){
            e.printStackTrace();
            return "";
        }
    }
}
