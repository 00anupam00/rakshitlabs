package com.feature.example.featureSelection;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.mllib.stat.test.ChiSqTestResult;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


public class ChiSquareTest {

    SparkSession spark;

    public RDD<String> loadDataset(String path){
        this.spark=  SparkCluster.getInstance();
        System.out.println(spark.conf().contains("delimiter"));
        return spark.sparkContext().textFile(path,2);
    }

    private Vector myVector(String path) throws IOException {
        BufferedReader bufferedReader= new BufferedReader(new FileReader(path));
        Vector v= null;
        String line;
        while((line= bufferedReader.readLine()) != null){
            String tokens[]= line.split(",");
            double[] features= new double[tokens.length];
            for (int i = 2; i < features.length; i++) {
                features[i-2]= Double.parseDouble(tokens[i]);
            }
            v= new DenseVector(features);
        }
        return v;
    }


    //With Spark
    private JavaRDD<LabeledPoint> getLabeledPoint(RDD<String> lines, String path){
        return lines.toJavaRDD().map(
                new Function<String, LabeledPoint>() {
                    @Override
                    public LabeledPoint call(String s) throws Exception {
                        String[] tokens= s.split(",");
                        double[] features= new double[tokens.length];
                        for(int i =2; i < features.length; i++){
                            features[i - 2] = Double.parseDouble(tokens[i]);
                        }
                        Vector v= new DenseVector(features);
                        if("R".equalsIgnoreCase(tokens[1])){
                            return new LabeledPoint(1.0, v);
                        }else
                            return new LabeledPoint(0.0, v);
                    }
                }
        );
    }

    public void getGoodnessOfTheFeatures(RDD<String>  lines, String path) throws IOException {
        //ChiSqTestResult chiTestResults= Statistics.chiSqTest(myVector(path));
        ChiSqTestResult[] chiTestResults= Statistics.chiSqTest(getLabeledPoint(lines, path));
        //Statistics.chiSqTest(get)
        int i=1;
        for (ChiSqTestResult chiTestResult: chiTestResults) {
            System.out.println("Column: "+i);
            System.out.println("chiTestResult: "+chiTestResult);
            i++;
        }
    }
}
