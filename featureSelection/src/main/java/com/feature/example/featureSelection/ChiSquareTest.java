package com.feature.example.featureSelection;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class ChiSquareTest {

    SparkSession spark;

    public Dataset<Row> loadDataset(String path){
        this.spark=  SparkCluster.getInstance();
        System.out.println(spark.conf().contains("delimiter"));
        return spark.read().option("header","true").csv(path);
    }
}
