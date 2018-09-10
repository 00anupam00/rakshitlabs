package com.feature.example.featureSelection;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class SparkCluster {

    public static SparkSession getInstance() {
        return SparkSession.builder().master("local").appName("Feature Selection").config(new SparkConf()).getOrCreate();
    }

}
