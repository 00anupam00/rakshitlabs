package com.feature.example.featureSelection;

import org.apache.spark.sql.SparkSession;

import javax.annotation.PostConstruct;

public class ChiSquareTest {

    SparkSession spark;

    @PostConstruct
    private void init(){
        this.spark=  SparkCluster.getInstance();
    }


    public void loadDataset(String path){
        spark.read().load(path).show(20);
    }
}
