package com.rakshitlabs.textSummarizer.TextSummarizer.detectors;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameDetector {

    public List<String> getNames(String[] sentences){
        List<String> nameList= new ArrayList<>();
        try(InputStream inputStream= new FileInputStream("D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\models\\en-ner-person.bin")){
            //Load the model
            TokenNameFinderModel nameFinderModel= new TokenNameFinderModel(inputStream);
            NameFinderME nameFinderME= new NameFinderME(nameFinderModel);
            Span[] names= nameFinderME.find(sentences);
            for (int i = 0; i < names.length; i++) {
                nameList.add(names[i].toString());
            }
            return nameList;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        String filePath= "D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\data\\sentence.txt";
        String[] sentences= new SentenceDetector().getSentences(filePath);
        List<String> names= new NameDetector().getNames(sentences);
        names.forEach(System.out::println);
    }
}
