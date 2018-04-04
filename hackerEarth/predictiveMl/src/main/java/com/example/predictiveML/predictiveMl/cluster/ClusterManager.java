package com.example.predictiveML.predictiveMl.cluster;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class ClusterManager {

    private static ClusterManager ourInstance = new ClusterManager();
    private static SparkSession spark;

    public static ClusterManager getInstance() {
        return ourInstance;
    }

    private ClusterManager() {

        spark= SparkSession.builder()
                                        .master("local[*]")
                                        .appName("Predictive Modelling")
                                        .config(new SparkConf())
                                        .getOrCreate();
    }

    public static SparkSession getSparkSession(){
        getInstance();
        return spark;
    }
}
