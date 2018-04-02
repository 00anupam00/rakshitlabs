package com.rakshitlabs.textSummarizer.TextSummarizer.detectors;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Lemmantizer {

    public String[] lemmantize(String[] tokens, String posTag[]){
        try(InputStream in= new FileInputStream("D:\\study\\rakshitlabs\\TextSummarizer\\src\\main\\resources\\models\\en-lemmatizer.dict")){
            DictionaryLemmatizer simpleLemmatizer= new DictionaryLemmatizer(in);
            return simpleLemmatizer.lemmatize(tokens, posTag);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
